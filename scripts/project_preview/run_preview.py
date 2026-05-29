from __future__ import annotations

import argparse
import json
import os
import re
import socket
import subprocess
import sys
import time
from dataclasses import dataclass, asdict
from pathlib import Path
from typing import Any

import pymysql


ROOT = Path(__file__).resolve().parents[2]
README_FILE = ROOT / "readme.md"
PREVIEW_ROOT = ROOT / "docs" / "previews"
GROUPS_DIR = PREVIEW_ROOT / "groups"
PROJECTS_DIR = PREVIEW_ROOT / "projects"
ASSETS_DIR = PREVIEW_ROOT / "assets"
RUNTIME_DIR = PREVIEW_ROOT / "runtime"
LOG_DIR = ROOT / "logs" / "project-preview"

MYSQL_HOST = "127.0.0.1"
MYSQL_PORT = 3306
MYSQL_USER = "root"
MYSQL_PASSWORD = "1234"

README_PREVIEW_START = "<!-- PROJECT_PREVIEW_START -->"
README_PREVIEW_END = "<!-- PROJECT_PREVIEW_END -->"


@dataclass
class Account:
    role_key: str
    role_label: str
    username: str
    password: str


@dataclass
class ProjectMeta:
    project_id: str
    name: str
    components: list[str]
    backend_dir: str | None
    frontend_dir: str | None
    miniapp_dir: str | None
    capture_urls: dict[str, str]
    accounts: list[Account]
    routes: list[str]
    readme_sources: list[str]


def ensure_dirs() -> None:
    for path in [GROUPS_DIR, PROJECTS_DIR, ASSETS_DIR, RUNTIME_DIR, LOG_DIR]:
        path.mkdir(parents=True, exist_ok=True)


def load_readme_project_names() -> dict[str, str]:
    content = README_FILE.read_text(encoding="utf-8")
    mapping: dict[str, str] = {}
    for match in re.finditer(r"^###\s*(\d{3})\s*-\s*(.+?)\s*$", content, re.M):
        mapping[match.group(1)] = match.group(2).strip()
    return mapping


def scan_projects() -> dict[str, ProjectMeta]:
    names = load_readme_project_names()
    project_ids = sorted(
        {
            match.group(1)
            for item in ROOT.iterdir()
            if item.is_dir()
            for match in [re.fullmatch(r"(\d{3})-(backend|frontend|miniprogram|miniapp)", item.name)]
            if match
        }
    )

    result: dict[str, ProjectMeta] = {}
    for project_id in project_ids:
        backend_dir = ROOT / f"{project_id}-backend"
        frontend_dir = ROOT / f"{project_id}-frontend"
        miniapp_dir = None
        for name in [f"{project_id}-miniprogram", f"{project_id}-miniapp"]:
            candidate = ROOT / name
            if candidate.exists():
                miniapp_dir = candidate
                break

        components = []
        if backend_dir.exists():
            components.append("backend")
        if frontend_dir.exists():
            components.append("frontend")
        if miniapp_dir:
            components.append("miniapp")

        accounts, readme_sources = discover_accounts(project_id, backend_dir, frontend_dir, miniapp_dir)
        routes = discover_routes(frontend_dir)
        capture_urls = build_capture_urls(backend_dir, frontend_dir)

        result[project_id] = ProjectMeta(
            project_id=project_id,
            name=names.get(project_id, f"项目 {project_id}"),
            components=components,
            backend_dir=str(backend_dir.relative_to(ROOT)) if backend_dir.exists() else None,
            frontend_dir=str(frontend_dir.relative_to(ROOT)) if frontend_dir.exists() else None,
            miniapp_dir=str(miniapp_dir.relative_to(ROOT)) if miniapp_dir else None,
            capture_urls=capture_urls,
            accounts=accounts,
            routes=routes,
            readme_sources=readme_sources,
        )
    return result


def role_key_from_label(label: str) -> str:
    text = label.lower()
    if "管理" in label or "admin" in text or "系统" in label or "店长" in label:
        return "admin"
    if "农户" in label or "farmer" in text or "商家" in label:
        return "farmer"
    if "消费" in label or "买家" in label or "buyer" in text:
        return "buyer"
    if "教师" in label or "老师" in label or "doctor" in text or "医生" in label:
        return "teacher"
    if "学生" in label or "用户" in label or "会员" in label or "患者" in label or "家长" in label:
        return "user"
    return re.sub(r"[^a-z0-9]+", "-", text).strip("-") or "user"


def discover_accounts(
    project_id: str,
    backend_dir: Path,
    frontend_dir: Path,
    miniapp_dir: Path | None,
) -> tuple[list[Account], list[str]]:
    files: list[Path] = []
    for base in [backend_dir, frontend_dir, miniapp_dir]:
        if not base or not base.exists():
            continue
        for name in ["README.md", "readme.md", "README_SIMPLE.md", "ACCOUNTS.md", "TEST_ACCOUNTS.md"]:
            candidate = base / name
            if candidate.exists():
                files.append(candidate)

    found: list[Account] = []
    sources: list[str] = []

    def is_database_info_block(block_text: str) -> bool:
        lowered = block_text.lower()
        markers = [
            "数据库信息",
            "数据库名",
            "修改数据库配置",
            "datasource",
            "jdbc:mysql",
            "mysql",
            "端口：3306",
            "端口:3306",
        ]
        return any(marker in block_text or marker in lowered for marker in markers)

    def add_account(account: Account, source_file: Path) -> None:
        if any(
            item.role_key == account.role_key
            and item.username == account.username
            and item.password == account.password
            for item in found
        ):
            return
        found.append(account)
        sources.append(str(source_file.relative_to(ROOT)))

    patterns = [
        re.compile(
            r"(管理员|农户|消费者|买家|教师|老师|学生|用户|商家|骑手|患者|医生|会员|家长|园长|审核员|申请人|客服|居民|调度员|员工)\s*[:：]\s*([A-Za-z0-9_@.\-]+)\s*/\s*([^\s`]+)"
        ),
        re.compile(
            r"\*\*(管理员|农户|消费者|买家|教师|老师|学生|用户|商家|骑手|患者|医生|会员|家长|园长|审核员|申请人|客服|居民|调度员|员工)\*\*\s*[:：]\s*([A-Za-z0-9_@.\-]+)\s*/\s*([^\s`]+)"
        ),
    ]

    for file in files:
        text = file.read_text(encoding="utf-8", errors="ignore")
        for pattern in patterns:
            for match in pattern.finditer(text):
                add_account(
                    Account(
                        role_key=role_key_from_label(match.group(1)),
                        role_label=match.group(1),
                        username=match.group(2).strip(),
                        password=match.group(3).strip().strip("，。,.;；"),
                    ),
                    file,
                )

        for block in re.split(r"\n\s*\n", text):
            if is_database_info_block(block):
                continue
            username_match = re.search(r"-\s*(?:\*\*)?用户名(?:\*\*)?\s*[:：]\s*`?([A-Za-z0-9_@.\-]+)`?", block)
            password_match = re.search(r"-\s*(?:\*\*)?密码(?:\*\*)?\s*[:：]\s*`?([^\s`]+)`?", block)
            role_match = re.search(r"-\s*(?:\*\*)?角色(?:\*\*)?\s*[:：]\s*([^\n`]+)", block)
            if username_match and password_match:
                role_label = role_match.group(1).strip() if role_match else ("管理员" if username_match.group(1).lower() == "admin" else "用户")
                add_account(
                    Account(
                        role_key=role_key_from_label(role_label),
                        role_label=role_label,
                        username=username_match.group(1).strip(),
                        password=password_match.group(1).strip().strip("，。,.;；"),
                    ),
                    file,
                )

        for raw_line in text.splitlines():
            line = raw_line.strip()
            if line.count("|") < 4:
                continue
            columns = [column.strip().strip("`").strip("*") for column in line.split("|")[1:-1]]
            if len(columns) < 3:
                continue
            role_label, username, password = columns[:3]
            if not role_label or not username or not password:
                continue
            if set(role_label) <= {"-", " "} or "角色" in role_label:
                continue
            if not re.fullmatch(r"[A-Za-z0-9_@.\-]+", username):
                continue
            if not re.fullmatch(r"[A-Za-z0-9_@.\-]+", password):
                continue
            if role_key_from_label(role_label) == "user" and "学生" not in role_label and "用户" not in role_label:
                continue
            add_account(
                Account(
                    role_key=role_key_from_label(role_label),
                    role_label=role_label,
                    username=username,
                    password=password.strip("，。,.;；"),
                ),
                file,
            )

        if file.name.upper() == "TEST_ACCOUNTS.MD":
            for block in text.split("#### ")[1:]:
                username_match = re.search(r"-\s*\*\*用户名\*\*\s*[:：]\s*`?([A-Za-z0-9_@.\-]+)`?", block)
                password_match = re.search(r"-\s*\*\*密码\*\*\s*[:：]\s*`?([^\s`]+)`?", block)
                role_match = re.search(r"-\s*\*\*角色\*\*\s*[:：]\s*([^\n`]+)", block)
                if not username_match or not password_match or not role_match:
                    continue
                add_account(
                    Account(
                        role_key=role_key_from_label(role_match.group(1).strip()),
                        role_label=role_match.group(1).strip(),
                        username=username_match.group(1).strip(),
                        password=password_match.group(1).strip().strip("，。,.;；"),
                    ),
                    file,
                )

        common_password_match = re.search(r"所有账号密码均为\s*[:：]\s*`?([A-Za-z0-9_@.\-]+)`?", text)
        if common_password_match:
            common_password = common_password_match.group(1).strip()
            tail_text = text[common_password_match.end():]
            for raw_line in tail_text.splitlines():
                line = raw_line.strip()
                bullet_match = re.fullmatch(r"-\s*([A-Za-z0-9_@.\-]+)", line)
                if not bullet_match:
                    continue
                username = bullet_match.group(1).strip()
                role_label = "管理员" if username.lower() == "admin" else "用户"
                add_account(
                    Account(
                        role_key=role_key_from_label(role_label),
                        role_label=role_label,
                        username=username,
                        password=common_password,
                    ),
                    file,
                )

    if not found and backend_dir.exists():
        sql_accounts = discover_accounts_from_sql(project_id, backend_dir)
        if sql_accounts:
            found.extend(sql_accounts)
            sources.append(str(find_sql_file(backend_dir).relative_to(ROOT)))

    return found, sorted(set(sources))


def discover_accounts_from_sql(project_id: str, backend_dir: Path) -> list[Account]:
    sql_file = find_sql_file(backend_dir)
    if not sql_file:
        return []

    text = sql_file.read_text(encoding="utf-8", errors="ignore")
    pairs = re.findall(
        r"(管理员|教师|老师|学生|用户|商家|骑手|患者|医生|会员|家长|园长|审核员|申请人|客服|居民|调度员|员工)[^\\n]*密码[:：]\s*([A-Za-z0-9_@.\-]+)",
        text,
    )

    usernames = re.findall(r"\('([A-Za-z0-9_@.\-]+)'\s*,\s*'[^']+'\s*,\s*'(admin|teacher|student|user)'", text)
    username_by_role = {role: username for username, role in usernames}

    results: list[Account] = []
    for label, password in pairs:
        role_key = role_key_from_label(label)
        username = username_by_role.get(role_key) or username_by_role.get("user") or ""
        if username:
            results.append(Account(role_key=role_key, role_label=label, username=username, password=password))
    return results


def discover_routes(frontend_dir: Path) -> list[str]:
    if not frontend_dir or not frontend_dir.exists():
        return []

    router_candidates = [
        frontend_dir / "src" / "router" / "index.js",
        frontend_dir / "src" / "router" / "index.ts",
        frontend_dir / "src" / "router.js",
        frontend_dir / "src" / "router.ts",
    ]
    routes: list[str] = []
    for candidate in router_candidates:
        if not candidate.exists():
            continue
        text = candidate.read_text(encoding="utf-8", errors="ignore")
        for match in re.finditer(
            r"path:\s*'([^']+)'.*?meta:\s*\{.*?title:\s*'([^']+)'.*?\}",
            text,
            re.S,
        ):
            path_text, title = match.groups()
            if ":" in path_text:
                continue
            if title not in routes:
                routes.append(title)
        if routes:
            break
    return routes


def detect_backend_port(backend_dir: Path) -> int:
    candidates = [
        backend_dir / "src" / "main" / "resources" / "application.yml",
        backend_dir / "src" / "main" / "resources" / "application.yaml",
    ]
    for candidate in candidates:
        if not candidate.exists():
            continue
        text = candidate.read_text(encoding="utf-8", errors="ignore")
        match = re.search(r"server:\s*\n(?:[ \t].*\n)*?[ \t]+port:\s*(\d+)", text)
        if match:
            return int(match.group(1))
    return 8080


def build_capture_urls(backend_dir: Path, frontend_dir: Path) -> dict[str, str]:
    urls: dict[str, str] = {}
    if backend_dir.exists():
        urls["backend"] = f"http://127.0.0.1:{detect_backend_port(backend_dir)}"
    if frontend_dir.exists():
        urls["frontend"] = "http://127.0.0.1:3000"
    return urls


def find_sql_file(backend_dir: Path) -> Path | None:
    candidates = [
        backend_dir / "src" / "main" / "resources" / "sql" / "init.sql",
        backend_dir / "src" / "main" / "resources" / "sql" / "schema.sql",
        backend_dir / "sql" / "init.sql",
        backend_dir / "sql" / "schema.sql",
    ]
    for candidate in candidates:
        if candidate.exists():
            return candidate
    return None


def split_sql_statements(text: str) -> list[str]:
    statements: list[str] = []
    current: list[str] = []
    in_single = False
    in_double = False
    i = 0
    while i < len(text):
        char = text[i]
        next_char = text[i + 1] if i + 1 < len(text) else ""

        if not in_single and not in_double and char == "-" and next_char == "-":
            while i < len(text) and text[i] != "\n":
                i += 1
            continue

        if not in_single and not in_double and char == "/" and next_char == "*":
            i += 2
            while i + 1 < len(text) and not (text[i] == "*" and text[i + 1] == "/"):
                i += 1
            i += 2
            continue

        if char == "'" and not in_double:
            in_single = not in_single
        elif char == '"' and not in_single:
            in_double = not in_double

        if char == ";" and not in_single and not in_double:
            statement = "".join(current).strip()
            if statement and not statement.upper().startswith("DELIMITER"):
                statements.append(statement)
            current = []
        else:
            current.append(char)
        i += 1

    tail = "".join(current).strip()
    if tail and not tail.upper().startswith("DELIMITER"):
        statements.append(tail)
    return statements


def import_sql_file(sql_file: Path) -> None:
    text = sql_file.read_text(encoding="utf-8", errors="ignore")
    statements = split_sql_statements(text)
    database_name = discover_database_name(text)
    connection = pymysql.connect(
        host=MYSQL_HOST,
        port=MYSQL_PORT,
        user=MYSQL_USER,
        password=MYSQL_PASSWORD,
        charset="utf8mb4",
        autocommit=True,
    )
    try:
        with connection.cursor() as cursor:
            if database_name:
                cursor.execute(f"DROP DATABASE IF EXISTS `{database_name}`")
            for statement in statements:
                cursor.execute(statement)
    finally:
        connection.close()


def discover_database_name(text: str) -> str | None:
    for pattern in [
        r"CREATE\s+DATABASE(?:\s+IF\s+NOT\s+EXISTS)?\s+([A-Za-z0-9_]+)",
        r"USE\s+([A-Za-z0-9_]+)",
    ]:
        match = re.search(pattern, text, re.I)
        if match:
            return match.group(1)
    return None


def run_command(command: list[str], cwd: Path, stdout_path: Path, stderr_path: Path) -> int:
    stdout_file = stdout_path.open("w", encoding="utf-8")
    stderr_file = stderr_path.open("w", encoding="utf-8")
    process = subprocess.Popen(
        command,
        cwd=str(cwd),
        stdout=stdout_file,
        stderr=stderr_file,
        creationflags=getattr(subprocess, "CREATE_NEW_PROCESS_GROUP", 0),
    )
    return process.pid


def detect_backend_port(backend_dir: Path) -> int:
    candidates = [
        backend_dir / "src" / "main" / "resources" / "application.yml",
        backend_dir / "src" / "main" / "resources" / "application.yaml",
        backend_dir / "src" / "main" / "resources" / "application.properties",
    ]
    for candidate in candidates:
        if not candidate.exists():
            continue
        text = candidate.read_text(encoding="utf-8", errors="ignore")
        if candidate.suffix == ".properties":
            match = re.search(r"^\s*server\.port\s*=\s*(\d+)\s*$", text, re.M)
        else:
            match = re.search(r"server:\s*.*?port:\s*(\d+)", text, re.S)
            if not match:
                match = re.search(r"^\s*server\.port\s*:\s*(\d+)\s*$", text, re.M)
        if match:
            return int(match.group(1))
    return 8080


def find_available_port(preferred_port: int, max_attempts: int = 20) -> int:
    for port in range(preferred_port, preferred_port + max_attempts):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
            sock.settimeout(1)
            if sock.connect_ex(("127.0.0.1", port)) != 0:
                return port
    raise RuntimeError(f"无法为预览找到可用端口，起始端口为 {preferred_port}")


def wait_for_port(port: int, timeout: int = 180) -> None:
    start = time.time()
    while time.time() - start < timeout:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
            sock.settimeout(1)
            if sock.connect_ex(("127.0.0.1", port)) == 0:
                return
        time.sleep(1)
    raise TimeoutError(f"端口 {port} 在 {timeout} 秒内未就绪")


def load_package_scripts(frontend_dir: Path) -> dict[str, Any]:
    package_json = frontend_dir / "package.json"
    if not package_json.exists():
        return {}
    return json.loads(package_json.read_text(encoding="utf-8"))


def prepare_project(project_id: str) -> None:
    ensure_dirs()
    if runtime_file(project_id).exists():
        try:
            payload = load_runtime(project_id)
            terminate_services(payload)
        except FileNotFoundError:
            pass
    projects = scan_projects()
    meta = projects[project_id]
    backend_dir = ROOT / meta.backend_dir if meta.backend_dir else None
    frontend_dir = ROOT / meta.frontend_dir if meta.frontend_dir else None

    runtime: dict[str, Any] = {
        "project": asdict(meta),
        "services": {},
        "logs": {},
        "status": "preparing",
        "prepared_at": time.strftime("%Y-%m-%d %H:%M:%S"),
    }

    if backend_dir:
        sql_file = find_sql_file(backend_dir)
        if sql_file:
            import_sql_file(sql_file)
            runtime["sql_file"] = str(sql_file.relative_to(ROOT))

        backend_port = find_available_port(detect_backend_port(backend_dir))
        backend_out = LOG_DIR / f"{project_id}-backend.out.log"
        backend_err = LOG_DIR / f"{project_id}-backend.err.log"
        backend_pid = run_command(
            ["mvn.cmd", "spring-boot:run", f"-Dspring-boot.run.arguments=--server.port={backend_port}"],
            backend_dir,
            backend_out,
            backend_err,
        )
        wait_for_port(backend_port)
        runtime["services"]["backend"] = {"url": f"http://127.0.0.1:{backend_port}", "pid": backend_pid}
        runtime["logs"]["backend"] = {
            "stdout": str(backend_out.relative_to(ROOT)),
            "stderr": str(backend_err.relative_to(ROOT)),
        }

    if frontend_dir:
        package_info = load_package_scripts(frontend_dir)
        scripts = package_info.get("scripts", {})
        if "dev" in scripts:
            dev_script = str(scripts.get("dev", ""))
            if "vite" in dev_script:
                command = ["npx.cmd", "--yes", "vite", "--host", "127.0.0.1"]
            else:
                command = ["npm.cmd", "run", "dev", "--", "--host", "127.0.0.1"]
        elif "start" in scripts:
            command = ["npm.cmd", "start"]
        else:
            raise RuntimeError(f"{project_id} 前端缺少可用启动脚本")

        frontend_out = LOG_DIR / f"{project_id}-frontend.out.log"
        frontend_err = LOG_DIR / f"{project_id}-frontend.err.log"
        frontend_pid = run_command(command, frontend_dir, frontend_out, frontend_err)
        frontend_port = wait_for_frontend_port(frontend_out, detect_frontend_port(frontend_dir))
        wait_for_port(frontend_port)
        runtime["services"]["frontend"] = {
            "url": f"http://127.0.0.1:{frontend_port}",
            "pid": frontend_pid,
        }
        runtime["logs"]["frontend"] = {
            "stdout": str(frontend_out.relative_to(ROOT)),
            "stderr": str(frontend_err.relative_to(ROOT)),
        }

    runtime["status"] = "running"
    runtime["capture_hints"] = {
        "roles": [asdict(account) for account in meta.accounts],
        "routes": meta.routes,
        "screenshot_dir": str((ASSETS_DIR / project_id).relative_to(ROOT)),
        "naming_rule": "建议使用 role-序号-页面名.png，例如 admin-01-dashboard.png",
    }
    save_runtime(project_id, runtime)
    print(json.dumps(runtime, ensure_ascii=True, indent=2))


def detect_frontend_port(frontend_dir: Path) -> int:
    vite_config = frontend_dir / "vite.config.js"
    if vite_config.exists():
        text = vite_config.read_text(encoding="utf-8", errors="ignore")
        match = re.search(r"port\s*:\s*(\d+)", text)
        if match:
            return int(match.group(1))
    return 3000


def wait_for_frontend_port(stdout_path: Path, fallback_port: int, timeout: int = 60) -> int:
    start = time.time()
    last_text = ""
    while time.time() - start < timeout:
        if stdout_path.exists():
            last_text = stdout_path.read_text(encoding="utf-8", errors="ignore")
            matches = re.findall(r"http://127\.0\.0\.1:(\d+)/", last_text)
            if matches:
                return int(matches[-1])
        time.sleep(1)
    if last_text:
        return fallback_port
    raise TimeoutError(f"前端在 {timeout} 秒内未输出可访问地址")


def runtime_file(project_id: str) -> Path:
    return RUNTIME_DIR / f"{project_id}.json"


def save_runtime(project_id: str, payload: dict[str, Any]) -> None:
    runtime_file(project_id).write_text(
        json.dumps(payload, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )


def load_runtime(project_id: str) -> dict[str, Any]:
    path = runtime_file(project_id)
    if not path.exists():
        raise FileNotFoundError(f"未找到 {project_id} 的运行清单：{path}")
    return json.loads(path.read_text(encoding="utf-8"))


def stop_project(project_id: str) -> None:
    payload = load_runtime(project_id)
    terminate_services(payload)
    payload["status"] = "stopped"
    payload["stopped_at"] = time.strftime("%Y-%m-%d %H:%M:%S")
    save_runtime(project_id, payload)
    print(f"{project_id} 已停止")


def terminate_services(payload: dict[str, Any]) -> None:
    for service in payload.get("services", {}).values():
        pid = service.get("pid")
        if not pid:
            continue
        subprocess.run(
            ["taskkill", "/PID", str(pid), "/T", "/F"],
            capture_output=True,
            text=True,
        )


def render_project(project_id: str) -> None:
    ensure_dirs()
    projects = scan_projects()
    meta = projects[project_id]
    runtime = load_runtime(project_id) if runtime_file(project_id).exists() else {"project": asdict(meta)}

    asset_dir = ASSETS_DIR / project_id
    asset_dir.mkdir(parents=True, exist_ok=True)
    images = sorted(
        [
            file
            for file in asset_dir.iterdir()
            if file.suffix.lower() in {".png", ".jpg", ".jpeg", ".webp"}
        ]
    )
    role_groups: dict[str, list[Path]] = {}
    for image in images:
        role = image.stem.split("-", 1)[0]
        role_groups.setdefault(role, []).append(image)

    lines = [
        f"# {project_id} - {meta.name}",
        "",
        "## 项目信息",
        "",
        f"- 项目编号：`{project_id}`",
        f"- 组件类型：`{', '.join(meta.components)}`",
        f"- 后端入口：`{runtime.get('services', {}).get('backend', {}).get('url', '未启动')}`",
        f"- 前端入口：`{runtime.get('services', {}).get('frontend', {}).get('url', '未启动')}`",
        f"- 账号来源：{', '.join(meta.readme_sources) if meta.readme_sources else '未识别'}",
        f"- 已收录截图：`{len(images)}` 张",
        "",
        "## 默认账号",
        "",
    ]

    if meta.accounts:
        for account in meta.accounts:
            lines.append(
                f"- `{account.role_label}`：`{account.username}` / `{account.password}`"
            )
    else:
        lines.append("- 暂未自动识别到默认账号")

    lines.extend(["", "## 预览截图", ""])
    if not images:
        lines.append("- 当前还没有截图，请先在 `docs/previews/assets/%s/` 下补充图片。" % project_id)
    else:
        for role, files in role_groups.items():
            lines.extend([f"### {role}", ""])
            for file in files:
                relative = f"../assets/{project_id}/{file.name}"
                lines.append(f"#### {file.stem}")
                lines.append("")
                lines.append(f"![{file.stem}]({relative})")
                lines.append("")

    output = PROJECTS_DIR / f"{project_id}.md"
    output.write_text("\n".join(lines).strip() + "\n", encoding="utf-8")
    render_group(project_id, projects)
    update_readme(projects)
    print(f"已生成 {output.relative_to(ROOT)}")


def render_group(project_id: str, projects: dict[str, ProjectMeta]) -> None:
    value = int(project_id)
    group_start = ((value - 1) // 10) * 10 + 1
    group_end = min(group_start + 9, max(int(item) for item in projects))
    ids = [f"{index:03d}" for index in range(group_start, group_end + 1) if f"{index:03d}" in projects]
    title = f"{group_start:03d}-{group_end:03d}"
    lines = [f"# 项目预览 {title}", "", "## 项目索引", ""]

    for current_id in ids:
        meta = projects[current_id]
        project_file = PROJECTS_DIR / f"{current_id}.md"
        asset_dir = ASSETS_DIR / current_id
        cover = ""
        if asset_dir.exists():
            covers = sorted(
                [file for file in asset_dir.iterdir() if file.suffix.lower() in {".png", ".jpg", ".jpeg", ".webp"}]
            )
            if covers:
                cover = f"../assets/{current_id}/{covers[0].name}"

        lines.append(f"### {current_id} - {meta.name}")
        lines.append("")
        lines.append(f"- 组件类型：`{', '.join(meta.components)}`")
        lines.append(f"- 详览页：[{current_id}.md](../projects/{current_id}.md)")
        if cover:
            lines.append(f"- 封面图：")
            lines.append("")
            lines.append(f"![{current_id}]({cover})")
        else:
            lines.append("- 封面图：待补充")
        lines.append("")

    output = GROUPS_DIR / f"{title}.md"
    output.write_text("\n".join(lines).strip() + "\n", encoding="utf-8")


def update_readme(projects: dict[str, ProjectMeta]) -> None:
    existing_groups = sorted(GROUPS_DIR.glob("*.md"))
    completed_projects = sorted(PROJECTS_DIR.glob("*.md"))
    group_lines = []
    for file in existing_groups:
        group_lines.append(f"- [{file.stem}](docs/previews/groups/{file.name})")

    section = [
        "## 项目预览",
        "",
        "项目预览已经拆分为“分组索引页 + 单项目详览页”的结构：",
        "",
        "1. 先看分组页，例如 `001-010`。",
        "2. 再进入对应项目详览页查看多张截图。",
        "",
        f"- 已生成项目详览：`{len(completed_projects)}` 个",
        f"- 已生成分组索引：`{len(existing_groups)}` 个",
        "",
        "### 分组入口",
        "",
    ]
    section.extend(group_lines or ["- 暂无分组页"])
    block = "\n".join([README_PREVIEW_START, *section, README_PREVIEW_END])

    content = README_FILE.read_text(encoding="utf-8")
    if README_PREVIEW_START in content and README_PREVIEW_END in content:
        content = re.sub(
            f"{re.escape(README_PREVIEW_START)}.*?{re.escape(README_PREVIEW_END)}",
            block,
            content,
            flags=re.S,
        )
    else:
        insert_at = content.find("## 📚 项目列表")
        if insert_at == -1:
            content = content + "\n\n" + block + "\n"
        else:
            content = content[:insert_at] + block + "\n\n" + content[insert_at:]
    README_FILE.write_text(content, encoding="utf-8")


def write_project_index() -> None:
    projects = scan_projects()
    payload = {project_id: asdict(meta) for project_id, meta in projects.items()}
    (RUNTIME_DIR / "projects.json").write_text(
        json.dumps(payload, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )
    print(f"已写入 {RUNTIME_DIR.relative_to(ROOT) / 'projects.json'}")


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="毕业设计项目预览流水线")
    subparsers = parser.add_subparsers(dest="command", required=True)

    subparsers.add_parser("scan", help="扫描真实项目并写入索引")

    prepare_parser = subparsers.add_parser("prepare", help="导入数据库并启动项目")
    prepare_parser.add_argument("project_id", help="项目编号，例如 001")

    render_parser = subparsers.add_parser("render", help="根据截图生成项目页和分组页")
    render_parser.add_argument("project_id", help="项目编号，例如 001")

    stop_parser = subparsers.add_parser("stop", help="停止项目运行")
    stop_parser.add_argument("project_id", help="项目编号，例如 001")

    return parser


def main() -> int:
    ensure_dirs()
    parser = build_parser()
    args = parser.parse_args()

    if args.command == "scan":
        write_project_index()
        return 0
    if args.command == "prepare":
        prepare_project(args.project_id)
        return 0
    if args.command == "render":
        render_project(args.project_id)
        return 0
    if args.command == "stop":
        stop_project(args.project_id)
        return 0
    parser.print_help()
    return 1


if __name__ == "__main__":
    sys.exit(main())
