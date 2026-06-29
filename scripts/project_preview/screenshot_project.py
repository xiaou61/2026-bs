"""Generic project screenshot script using Playwright.

Usage:
    python screenshot_project.py <project_id> [--backend-port PORT] [--frontend-port PORT]

The script will:
1. Read runtime JSON from docs/previews/runtime/<project_id>.json
2. Login with each role's account
3. Navigate all routes and take screenshots
4. Save screenshots to docs/previews/assets/<project_id>/<role>-<seq>-<page>.png
"""
from __future__ import annotations

import argparse
import json
import os
import re
import sys
import time
from pathlib import Path

from playwright.sync_api import sync_playwright

ROOT = Path(__file__).resolve().parents[2]
RUNTIME_DIR = ROOT / "docs" / "previews" / "runtime"
ASSETS_DIR = ROOT / "docs" / "previews" / "assets"
VIEWPORT = {"width": 1440, "height": 900}
PAGE_WAIT = 1  # seconds to wait after navigation
ROLE_KEYWORDS = {
    "admin": ["admin", "管理", "系统"],
    "teacher": ["teacher", "教师", "老师"],
    "student": ["student", "学生"],
    "volunteer": ["volunteer", "志愿"],
    "courier": ["courier", "快递", "配送", "骑手"],
    "merchant": ["merchant", "seller", "商家", "店铺", "卖家"],
    "seller": ["seller", "merchant", "商家", "卖家"],
    "buyer": ["buyer", "买家", "消费"],
    "doctor": ["doctor", "医生", "医师"],
    "patient": ["patient", "患者", "病人"],
    "resident": ["resident", "居民", "住户"],
    "staff": ["staff", "员工", "工作人员"],
    "driver": ["driver", "司机", "驾驶"],
    "user": ["user", "用户", "会员", "普通"],
}


def load_runtime(project_id: str) -> dict:
    path = RUNTIME_DIR / f"{project_id}.json"
    if not path.exists():
        print(f"Runtime file not found: {path}")
        sys.exit(1)
    return json.loads(path.read_text(encoding="utf-8"))


def get_frontend_url(runtime: dict) -> str:
    frontend = runtime.get("services", {}).get("frontend", {})
    if frontend.get("url"):
        return frontend["url"]
    backend = runtime.get("services", {}).get("backend", {})
    return backend.get("url", "http://127.0.0.1:3000")


def get_accounts(runtime: dict) -> list[dict]:
    return runtime.get("project", {}).get("accounts", runtime.get("capture_hints", {}).get("roles", []))


def get_routes_from_router(frontend_dir: Path) -> dict[str, list[str]]:
    """Parse router file and return {layout_prefix: [child_paths]}."""
    if not frontend_dir or not frontend_dir.exists():
        return {}

    router_candidates = [
        frontend_dir / "src" / "router" / "index.js",
        frontend_dir / "src" / "router" / "index.ts",
        frontend_dir / "src" / "router.js",
        frontend_dir / "src" / "router.ts",
    ]
    routes: dict[str, list[str]] = {}
    for candidate in router_candidates:
        if not candidate.exists():
            continue
        text = candidate.read_text(encoding="utf-8", errors="ignore")

        def find_matching_bracket(start_index: int) -> int:
            depth = 0
            quote = ""
            escaped = False
            for index in range(start_index, len(text)):
                ch = text[index]
                if quote:
                    if escaped:
                        escaped = False
                    elif ch == "\\":
                        escaped = True
                    elif ch == quote:
                        quote = ""
                    continue
                if ch in ("'", '"', "`"):
                    quote = ch
                elif ch == "[":
                    depth += 1
                elif ch == "]":
                    depth -= 1
                    if depth == 0:
                        return index
            return -1

        for children_match in re.finditer(r"children\s*:\s*\[", text, flags=re.I):
            parent_matches = list(re.finditer(r"path:\s*['\"]([^'\"]+)['\"]", text[: children_match.start()]))
            if not parent_matches:
                continue
            parent_path = parent_matches[-1].group(1)
            start_index = text.find("[", children_match.start())
            end_index = find_matching_bracket(start_index)
            if end_index == -1:
                continue
            children_text = text[start_index + 1 : end_index]
            child_paths = re.findall(r"path:\s*['\"]([^'\"]+)['\"]", children_text)
            child_paths = [p for p in child_paths if ":" not in p]
            if child_paths:
                full_paths = []
                for cp in child_paths:
                    if cp.startswith("/"):
                        full_paths.append(cp)
                    elif parent_path == "/":
                        full_paths.append(f"/{cp.lstrip('/')}")
                    elif parent_path.endswith("/"):
                        full_paths.append(f"{parent_path}{cp.lstrip('/')}")
                    else:
                        full_paths.append(f"{parent_path}/{cp}")
                routes[parent_path] = full_paths

        standalone_pattern = re.compile(
            r"path:\s*['\"]([^'\"]+)['\"][\s\S]*?component",
        )
        for match in standalone_pattern.finditer(text):
            path_text = match.group(1)
            if not path_text.startswith("/"):
                continue
            if ":" in path_text or path_text in routes:
                continue
            routes[path_text] = [path_text]

        break
    return routes


def get_routes_from_static_backend(backend_dir: Path | None) -> dict[str, list[str]]:
    """Discover HTML pages from Spring Boot static resources."""
    if not backend_dir or not backend_dir.exists():
        return {}

    static_dir = backend_dir / "src" / "main" / "resources" / "static"
    if not static_dir.exists():
        return {}

    routes: dict[str, list[str]] = {}
    if (static_dir / "index.html").exists():
        routes["/"] = ["/"]

    for html_file in sorted(static_dir.rglob("*.html")):
        rel = "/" + html_file.relative_to(static_dir).as_posix()
        if rel == "/index.html":
            continue
        lowered = rel.lower()
        if "/admin/" in lowered:
            routes.setdefault("/admin", []).append(rel)
        elif "/user/" in lowered:
            routes.setdefault("/user", []).append(rel)
        else:
            routes.setdefault("/pages", []).append(rel)

    return routes


def determine_role_from_path(path: str, accounts: list[dict], app_hint: str = "") -> str:
    """Determine the role based on the URL path prefix."""
    hint = app_hint.lower()
    if "admin" in hint and any(str(acc.get("role_key", "")).lower() == "admin" for acc in accounts):
        return "admin"
    if "user" in hint and any(str(acc.get("role_key", "")).lower() == "user" for acc in accounts):
        return "user"

    for role in ROLE_KEYWORDS:
        if f"/{role}" in path:
            return role
    if any(str(acc.get("role_key", "")).lower() == "admin" for acc in accounts):
        return "admin"
    for acc in accounts:
        return accounts[0].get("role_key", "user")
    normalized = path.strip("/").split("/", 1)[0]
    return normalized or "guest"


def get_credentials_for_role(role: str, accounts: list[dict]) -> tuple[str, str]:
    """Get username/password for a given role."""
    role_text = role.lower()
    for acc in accounts:
        if str(acc.get("role_key", "")).lower() == role_text:
            return acc.get("username", ""), acc.get("password", "")

    keywords = ROLE_KEYWORDS.get(role_text, [role_text])
    for acc in accounts:
        haystack = " ".join(
            [
                str(acc.get("role_key", "")),
                str(acc.get("role_label", "")),
                str(acc.get("username", "")),
            ]
        ).lower()
        if any(keyword.lower() in haystack for keyword in keywords):
            return acc.get("username", ""), acc.get("password", "")

    if role_text == "student":
        for acc in accounts:
            if str(acc.get("role_key", "")).lower() == "user":
                return acc.get("username", ""), acc.get("password", "")

    # Fallback to any account
    if accounts:
        return accounts[0].get("username", ""), accounts[0].get("password", "")
    return "", ""


def wait_for_page_settle(page, timeout: int = 10000) -> None:
    try:
        page.wait_for_load_state("networkidle", timeout=timeout)
    except Exception as exc:
        print(f"  networkidle wait skipped: {exc}")


def login(page, base_url: str, username: str, password: str, login_paths: list[str] | None = None) -> bool:
    """Navigate to login page and attempt to login."""
    for login_path in login_paths or ["/login"]:
        try:
            page.goto(f"{base_url}{login_path}", timeout=15000)
            wait_for_page_settle(page)
            time.sleep(1)
        except Exception:
            continue

        # Find and fill username field
        inputs = page.query_selector_all("input")
        if len(inputs) < 2:
            continue

        username_filled = False
        for inp in inputs:
            inp_type = (inp.get_attribute("type") or "").lower()
            inp_placeholder = (inp.get_attribute("placeholder") or "").lower()
            inp_name = (inp.get_attribute("name") or "").lower()
            if (
                "text" in inp_type
                or "用户名" in inp_placeholder
                or "账号" in inp_placeholder
                or "username" in inp_name
                or "username" in inp_placeholder
                or "account" in inp_placeholder
            ):
                inp.fill(username)
                username_filled = True
                break

        if not username_filled:
            inputs[0].fill(username)

        # Find and fill password field
        password_filled = False
        for inp in inputs:
            inp_type = (inp.get_attribute("type") or "").lower()
            if "password" in inp_type:
                inp.fill(password)
                password_filled = True
                break

        if not password_filled:
            inputs[1].fill(password)

        # Click login button
        clicked = False
        submit_button = page.query_selector("button[type='submit'], input[type='submit']")
        if submit_button:
            submit_button.click()
            clicked = True
        buttons = page.query_selector_all("button")
        if not clicked:
            for btn in buttons:
                txt = btn.inner_text()
                if "登录" in txt or "login" in txt.lower() or "sign" in txt.lower():
                    btn.click()
                    clicked = True
                    break
        if not clicked and buttons:
            buttons[0].click()

        time.sleep(3)
        wait_for_page_settle(page)

        # Verify login succeeded - check if redirected away from login page
        current_url = page.url
        if login_path not in current_url and "/login" not in current_url:
            return True

        # Check if token exists in localStorage
        token = page.evaluate("localStorage.getItem('token')")
        if token is not None:
            return True

    return False


def take_screenshot(page, path: str, name: str, screenshot_dir: str, wait: int = PAGE_WAIT) -> None:
    """Navigate to path and take a full-page screenshot."""
    base_url = page.url.split("/")[0] + "//" + page.url.split("/")[2]
    normalized_path = "/" + path.lstrip("/")
    full_url = f"{base_url}{normalized_path}"
    try:
        page.goto(full_url, timeout=15000)
        wait_for_page_settle(page)
        time.sleep(wait)
        filepath = os.path.join(screenshot_dir, name)
        page.screenshot(path=filepath, full_page=True)
        print(f"  Saved: {name}")
    except Exception as e:
        print(f"  Error capturing {path}: {e}")


def slugify_page_name(path: str) -> str:
    """Convert a URL path to a readable slug."""
    # Remove leading slash and layout prefix
    parts = path.strip("/").split("/")
    # Take the last meaningful part
    name = parts[-1] if parts else "home"
    # Convert camelCase to hyphen-case
    name = re.sub(r"([A-Z])", r"-\1", name).lower().lstrip("-")
    # Clean up
    name = re.sub(r"[^a-z0-9-]", "-", name).strip("-")
    return name or "page"


def screenshot_project(project_id: str, backend_port: int = None, frontend_port: int = None) -> None:
    runtime = load_runtime(project_id)
    base_url = get_frontend_url(runtime)
    if frontend_port:
        base_url = f"http://127.0.0.1:{frontend_port}"
    accounts = get_accounts(runtime)
    project_info = runtime.get("project", {})
    frontend_dir_value = project_info.get("frontend_dir")
    backend_dir_value = project_info.get("backend_dir")
    frontend_dir = ROOT / frontend_dir_value if frontend_dir_value else None
    backend_dir = ROOT / backend_dir_value if backend_dir_value else None
    is_static_backend = frontend_dir is None or not frontend_dir.exists()

    screenshot_dir = str(ASSETS_DIR / project_id)
    os.makedirs(screenshot_dir, exist_ok=True)

    # Parse routes from router
    route_map = get_routes_from_router(frontend_dir)
    if not route_map:
        route_map = get_routes_from_static_backend(backend_dir)
    if not route_map:
        route_map = {"/": ["/"]}
    app_hint = frontend_dir.name if frontend_dir and frontend_dir.exists() else ""
    print(f"Found routes: {route_map}")

    # Also add standalone pages (login, register)
    standalone_pages = []
    for path, children in route_map.items():
        normalized_children = ["/" + child.lstrip("/") for child in children]
        if path in ("/login", "/register"):
            standalone_pages.extend(normalized_children)
        elif path in ("/", "/index.html") and normalized_children == ["/"]:
            standalone_pages.extend(normalized_children)

    # Group routes by role
    role_routes: dict[str, list[str]] = {}
    for layout_path, child_paths in route_map.items():
        normalized_children = ["/" + child.lstrip("/") for child in child_paths]
        if layout_path in ("/login", "/register") or normalized_children == ["/"]:
            continue
        role = determine_role_from_path(layout_path, accounts, app_hint=app_hint)
        role_routes.setdefault(role, []).extend(normalized_children)

    # If no routes found from router, try common paths
    if not role_routes and frontend_dir and frontend_dir.exists():
        print("No routes parsed from router, scanning views directory...")
        views_dir = frontend_dir / "src" / "views"
        if views_dir.exists():
            for item in views_dir.iterdir():
                if item.is_dir() and item.name not in ("__pycache__"):
                    role_routes.setdefault(item.name.lower(), [])
                    for vue_file in item.glob("*.vue"):
                        page_name = vue_file.stem.lower()
                        role_routes[item.name.lower()].append(f"/{item.name.lower()}/{page_name}")

    print(f"Role routes: {role_routes}")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport=VIEWPORT)
        page = context.new_page()

        # Capture standalone pages (login, register)
        for index, standalone in enumerate(standalone_pages, start=1):
            page.goto(f"{base_url}{standalone}", timeout=15000)
            wait_for_page_settle(page)
            time.sleep(1)
            # Determine filename
            page_name = standalone.strip("/").replace("/", "-") or "home"
            take_screenshot(page, standalone, f"guest-{index:02d}-{page_name}.png", screenshot_dir)

        # Capture login page if not already captured
        if not is_static_backend and "/login" not in standalone_pages:
            page.goto(f"{base_url}/login", timeout=15000)
            wait_for_page_settle(page)
            time.sleep(1)
            take_screenshot(page, "/login", "guest-01-login.png", screenshot_dir)

        # Capture register page
        if not is_static_backend and "/register" not in standalone_pages:
            try:
                page.goto(f"{base_url}/register", timeout=15000)
                wait_for_page_settle(page)
                time.sleep(1)
                take_screenshot(page, "/register", "guest-02-register.png", screenshot_dir)
            except Exception:
                pass

        # For each role, login and capture all routes
        seq_counter: dict[str, int] = {}
        for role, paths in role_routes.items():
            username, password = get_credentials_for_role(role, accounts)
            if not username:
                print(f"  No account for role {role}, capturing routes as guest")
                role_prefix = "guest" if role == "guest" else f"guest-{role}"
                seq = 1
                for path in paths:
                    page_name = slugify_page_name(path)
                    filename = f"{role_prefix}-{seq:02d}-{page_name}.png"
                    take_screenshot(page, path, filename, screenshot_dir)
                    seq += 1
                continue

            print(f"\n=== Role: {role} ({username}) ===")

            # Clear localStorage and login
            page.evaluate("localStorage.clear()")
            login_paths = ["/", "/index.html", "/login"] if is_static_backend else ["/login"]
            success = login(page, base_url, username, password, login_paths=login_paths)
            if not success:
                print(f"  Login failed for {username}, skipping role {role}")
                continue

            print(f"  Login succeeded, current URL: {page.url}")
            seq = 1

            for path in paths:
                page_name = slugify_page_name(path)
                filename = f"{role}-{seq:02d}-{page_name}.png"
                take_screenshot(page, path, filename, screenshot_dir)
                seq += 1

        browser.close()

    # List results
    files = sorted(os.listdir(screenshot_dir))
    png_files = [f for f in files if f.endswith(".png")]
    print(f"\n=== Total screenshots for {project_id}: {len(png_files)} ===")
    for f in png_files:
        size = os.path.getsize(os.path.join(screenshot_dir, f))
        print(f"  {size:>8} bytes  {f}")


def main():
    parser = argparse.ArgumentParser(description="Screenshot a project using Playwright")
    parser.add_argument("project_id", help="Project ID, e.g. 007")
    parser.add_argument("--backend-port", type=int, default=None)
    parser.add_argument("--frontend-port", type=int, default=None)
    args = parser.parse_args()
    screenshot_project(args.project_id, args.backend_port, args.frontend_port)


if __name__ == "__main__":
    main()
