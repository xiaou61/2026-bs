from __future__ import annotations

import argparse
import json
import os
import subprocess
import sys
import time
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
ASSETS_DIR = ROOT / "docs" / "previews" / "assets"
LOG_DIR = ROOT / "logs" / "project-preview"
SUMMARY_PATH = LOG_DIR / "batch-capture-summary.json"


def image_count(project_id: str) -> int:
    asset_dir = ASSETS_DIR / project_id
    if not asset_dir.exists():
        return 0
    return sum(1 for file in asset_dir.iterdir() if file.suffix.lower() in {".png", ".jpg", ".jpeg", ".webp"})


def clear_project_images(project_id: str) -> None:
    asset_dir = ASSETS_DIR / project_id
    if not asset_dir.exists():
        return
    for file in asset_dir.iterdir():
        if file.is_file() and file.suffix.lower() in {".png", ".jpg", ".jpeg", ".webp"}:
            file.unlink()


def project_exists(project_id: str) -> bool:
    return any((ROOT / f"{project_id}-{suffix}").exists() for suffix in ["backend", "frontend", "miniprogram", "miniapp"])


def run_step(project_id: str, step: str, command: list[str]) -> tuple[int, Path]:
    LOG_DIR.mkdir(parents=True, exist_ok=True)
    log_path = LOG_DIR / f"{project_id}-{step}.batch.log"
    started = time.strftime("%Y-%m-%d %H:%M:%S")
    with log_path.open("w", encoding="utf-8", errors="ignore") as log:
        log.write(f"[{started}] {' '.join(command)}\n\n")
        log.flush()
        env = os.environ.copy()
        env["PYTHONIOENCODING"] = "utf-8"
        proc = subprocess.run(
            command,
            cwd=ROOT,
            stdout=log,
            stderr=subprocess.STDOUT,
            env=env,
            text=True,
        )
        log.write(f"\n[{time.strftime('%Y-%m-%d %H:%M:%S')}] exit={proc.returncode}\n")
    return proc.returncode, log_path


def stop_project(project_id: str) -> None:
    run_step(project_id, "stop", [sys.executable, "scripts/project_preview/run_preview.py", "stop", project_id])


def cleanup_project(project_id: str) -> tuple[int, Path]:
    return run_step(project_id, "cleanup", [sys.executable, "scripts/project_preview/run_preview.py", "cleanup", project_id])


def capture_project(project_id: str, force: bool) -> dict:
    before_count = image_count(project_id)
    if before_count > 0 and not force:
        return {"project": project_id, "status": "skipped", "images": before_count, "reason": "already has screenshots"}
    if not project_exists(project_id):
        return {"project": project_id, "status": "skipped", "images": 0, "reason": "project directory missing"}
    if force and before_count > 0:
        clear_project_images(project_id)

    result = {"project": project_id, "status": "running", "images_before": before_count, "logs": {}}
    print(f"\n=== {project_id} prepare ===", flush=True)
    code, log_path = run_step(project_id, "prepare", [sys.executable, "scripts/project_preview/run_preview.py", "prepare", project_id])
    result["logs"]["prepare"] = str(log_path.relative_to(ROOT))
    if code != 0:
        result.update({"status": "failed", "failed_step": "prepare", "exit_code": code, "images": image_count(project_id)})
        stop_project(project_id)
        cleanup_code, cleanup_log = cleanup_project(project_id)
        result["logs"]["cleanup"] = str(cleanup_log.relative_to(ROOT))
        result["cleanup_exit_code"] = cleanup_code
        return result

    try:
        print(f"=== {project_id} screenshot ===", flush=True)
        code, log_path = run_step(project_id, "screenshot", [sys.executable, "scripts/project_preview/screenshot_project.py", project_id])
        result["logs"]["screenshot"] = str(log_path.relative_to(ROOT))
        if code != 0:
            result.update({"status": "failed", "failed_step": "screenshot", "exit_code": code, "images": image_count(project_id)})
            return result

        print(f"=== {project_id} render ===", flush=True)
        code, log_path = run_step(project_id, "render", [sys.executable, "scripts/project_preview/run_preview.py", "render", project_id])
        result["logs"]["render"] = str(log_path.relative_to(ROOT))
        final_count = image_count(project_id)
        if code != 0:
            result.update({"status": "failed", "failed_step": "render", "exit_code": code, "images": final_count})
            return result
        if final_count == 0:
            result.update({"status": "failed", "failed_step": "screenshot", "exit_code": 0, "images": 0, "reason": "no screenshots produced"})
            return result
        result.update({"status": "completed", "images": final_count})
        return result
    finally:
        print(f"=== {project_id} stop ===", flush=True)
        stop_project(project_id)
        print(f"=== {project_id} cleanup ===", flush=True)
        cleanup_code, cleanup_log = cleanup_project(project_id)
        result["logs"]["cleanup"] = str(cleanup_log.relative_to(ROOT))
        result["cleanup_exit_code"] = cleanup_code


def save_summary(results: list[dict]) -> None:
    LOG_DIR.mkdir(parents=True, exist_ok=True)
    SUMMARY_PATH.write_text(
        json.dumps(
            {
                "updated_at": time.strftime("%Y-%m-%d %H:%M:%S"),
                "total": len(results),
                "completed": sum(1 for item in results if item["status"] == "completed"),
                "failed": sum(1 for item in results if item["status"] == "failed"),
                "skipped": sum(1 for item in results if item["status"] == "skipped"),
                "results": results,
            },
            ensure_ascii=False,
            indent=2,
        ),
        encoding="utf-8",
    )


def main() -> int:
    parser = argparse.ArgumentParser(description="Batch capture project screenshots.")
    parser.add_argument("--start", default="001")
    parser.add_argument("--end", default="200")
    parser.add_argument("--force", action="store_true")
    args = parser.parse_args()

    start = int(args.start)
    end = int(args.end)
    results: list[dict] = []
    for index in range(start, end + 1):
        project_id = f"{index:03d}"
        result = capture_project(project_id, args.force)
        results.append(result)
        save_summary(results)
        print(
            f"--- {project_id} {result['status']} images={result.get('images', 0)} "
            f"completed={sum(1 for item in results if item['status'] == 'completed')} "
            f"failed={sum(1 for item in results if item['status'] == 'failed')} "
            f"skipped={sum(1 for item in results if item['status'] == 'skipped')} ---",
            flush=True,
        )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
