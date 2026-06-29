from __future__ import annotations

import argparse
import hashlib
import json
import os
import re
import shutil
import subprocess
import sys
import time
import traceback
from dataclasses import dataclass, field
from datetime import date, timedelta
from pathlib import Path
from typing import Any

import pymysql
import bcrypt
from playwright.sync_api import sync_playwright

import run_preview
from screenshot_project import get_frontend_url, load_runtime, wait_for_page_settle


ROOT = Path(__file__).resolve().parents[2]
ASSETS_DIR = ROOT / "docs" / "previews" / "assets"
PUBLIC_ASSETS_DIR = ROOT / "docs-site" / "public" / "previews" / "assets"
QA_DIR = ROOT / "docs" / "previews" / "qa"
LOG_DIR = ROOT / "logs" / "project-preview"
BACKUP_DIR = LOG_DIR / "fine-backups"
VIEWPORT = {"width": 1440, "height": 900}
os.environ.setdefault("PW_TEST_SCREENSHOT_NO_FONTS_READY", "1")
PROJECT_SPECIFIC_ONLY_IDS = {"016", "017", "018", "019", "020", "021"}


@dataclass
class ShotRecord:
    file: str
    label: str
    url: str
    text_length: int
    size: int
    warnings: list[str] = field(default_factory=list)


@dataclass
class RoleRecord:
    role: str
    username: str
    login_ok: bool
    file_prefix: str
    visible_menus: list[dict[str, str]] = field(default_factory=list)
    screenshots: list[ShotRecord] = field(default_factory=list)
    warnings: list[str] = field(default_factory=list)


def ensure_dirs() -> None:
    for directory in [ASSETS_DIR, PUBLIC_ASSETS_DIR, QA_DIR, LOG_DIR]:
        directory.mkdir(parents=True, exist_ok=True)


def assert_inside_root(path: Path) -> Path:
    resolved = path.resolve()
    root = ROOT.resolve()
    if resolved != root and root not in resolved.parents:
        raise RuntimeError(f"Refusing to operate outside workspace: {resolved}")
    return resolved


def image_files(directory: Path) -> list[Path]:
    if not directory.exists():
        return []
    return sorted(
        [
            file
            for file in directory.iterdir()
            if file.is_file() and file.suffix.lower() in {".png", ".jpg", ".jpeg", ".webp"}
        ],
        key=lambda file: file.name,
    )


def clear_images(directory: Path) -> int:
    directory = assert_inside_root(directory)
    directory.mkdir(parents=True, exist_ok=True)
    count = 0
    for file in image_files(directory):
        file.unlink()
        count += 1
    return count


def backup_images(project_id: str, source: Path) -> Path | None:
    files = image_files(source)
    if not files:
        return None
    backup = BACKUP_DIR / project_id / time.strftime("%Y%m%d-%H%M%S")
    backup = assert_inside_root(backup)
    backup.mkdir(parents=True, exist_ok=True)
    for file in files:
        shutil.copy2(file, backup / file.name)
    return backup


def restore_images(backup: Path | None, target: Path) -> int:
    if not backup or not backup.exists():
        return 0
    clear_images(target)
    count = 0
    for file in image_files(backup):
        shutil.copy2(file, target / file.name)
        count += 1
    return count


def sync_public_assets(project_id: str) -> None:
    source = ASSETS_DIR / project_id
    target = PUBLIC_ASSETS_DIR / project_id
    target = assert_inside_root(target)
    target.mkdir(parents=True, exist_ok=True)
    clear_images(target)
    for file in image_files(source):
        shutil.copy2(file, target / file.name)


def run_logged(project_id: str, step: str, command: list[str]) -> tuple[int, Path]:
    LOG_DIR.mkdir(parents=True, exist_ok=True)
    log_path = LOG_DIR / f"{project_id}-{step}.fine.log"
    env = os.environ.copy()
    env["PYTHONIOENCODING"] = "utf-8"
    with log_path.open("w", encoding="utf-8", errors="ignore") as log:
        log.write(f"[{time.strftime('%Y-%m-%d %H:%M:%S')}] {' '.join(command)}\n\n")
        log.flush()
        process = subprocess.run(
            command,
            cwd=ROOT,
            stdout=log,
            stderr=subprocess.STDOUT,
            text=True,
            env=env,
        )
        log.write(f"\n[{time.strftime('%Y-%m-%d %H:%M:%S')}] exit={process.returncode}\n")
    return process.returncode, log_path


def normalize_role(account: dict[str, Any]) -> str:
    raw_key = str(account.get("role_key") or "").strip()
    raw_label = str(account.get("role_label") or "").strip()
    role_source = raw_label if raw_key.lower() in {"", "user", "guest"} and raw_label else raw_key or raw_label
    role = role_source.strip().lower()
    aliases = {
        "管理员": "admin",
        "教师": "teacher",
        "老师": "teacher",
        "学生": "student",
        "用户": "user",
        "医生": "doctor",
        "患者": "patient",
        "居民": "resident",
        "会员": "member",
    }
    role = aliases.get(role, role)
    role = re.sub(r"[^a-z0-9]+", "-", role).strip("-")
    return role or "user"


def discover_accounts_from_login_page(frontend_dir: Path | None) -> list[dict[str, str]]:
    if not frontend_dir:
        return []
    login_file = frontend_dir / "src" / "views" / "Login.vue"
    if not login_file.exists():
        return []
    text = login_file.read_text(encoding="utf-8", errors="ignore")
    pairs = re.findall(r">([A-Za-z0-9_@.\-]+)\s*/\s*([A-Za-z0-9_@.\-]+)<", text)
    accounts: list[dict[str, str]] = []
    seen: set[tuple[str, str]] = set()
    for username, password in pairs:
        key = (username, password)
        if key in seen:
            continue
        seen.add(key)
        accounts.append(
            {
                "role_key": username.lower(),
                "role_label": username,
                "username": username,
                "password": password,
            }
        )
    default_match = re.search(
        r"reactive\s*\(\s*\{\s*username:\s*['\"]([^'\"]+)['\"]\s*,\s*password:\s*['\"]([^'\"]+)['\"]",
        text,
        flags=re.S,
    )
    if default_match and (default_match.group(1), default_match.group(2)) not in seen:
        accounts.insert(
            0,
            {
                "role_key": default_match.group(1).lower(),
                "role_label": default_match.group(1),
                "username": default_match.group(1),
                "password": default_match.group(2),
            },
        )
    return accounts


def account_list(runtime: dict[str, Any]) -> list[dict[str, Any]]:
    accounts = runtime.get("project", {}).get("accounts") or runtime.get("capture_hints", {}).get("roles") or []
    if accounts:
        return dedupe_accounts(accounts)
    frontend_value = runtime.get("project", {}).get("frontend_dir")
    frontend_dir = ROOT / frontend_value if frontend_value else None
    return dedupe_accounts(discover_accounts_from_login_page(frontend_dir))


def role_capture_accounts(project_id: str, accounts: list[dict[str, Any]]) -> list[dict[str, Any]]:
    if project_requires_specific_capture(project_id):
        return []
    if project_id not in {"014", "015"}:
        return accounts
    return [
        account for account in accounts
        if normalize_role(account) != "admin" and str(account.get("username") or "").lower() != "admin"
    ]


def project_requires_specific_capture(project_id: str) -> bool:
    return project_id in PROJECT_SPECIFIC_ONLY_IDS


def add_login_coverage_warnings(result: dict[str, Any], accounts: list[dict[str, Any]]) -> list[str]:
    failed_roles = [role["role"] for role in result["roles"] if not role.get("login_ok")]
    has_role_success = any(role.get("login_ok") for role in result["roles"])
    has_project_specific = bool(result.get("project_specific"))
    if accounts and not has_role_success and not has_project_specific:
        result["warnings"].append("no role login succeeded")
    if failed_roles:
        result["warnings"].append(f"role login failed: {', '.join(failed_roles)}")
    return failed_roles


def role_specificity(account: dict[str, Any]) -> int:
    role = normalize_role(account)
    return 0 if role in {"user", "guest"} else 1


def dedupe_accounts(accounts: list[dict[str, Any]]) -> list[dict[str, Any]]:
    by_username: dict[str, dict[str, Any]] = {}
    order: list[str] = []
    for account in accounts:
        username = str(account.get("username") or "")
        if not username:
            continue
        if username not in by_username:
            by_username[username] = account
            order.append(username)
            continue
        existing = by_username[username]
        if role_specificity(account) > role_specificity(existing):
            by_username[username] = account
    return [by_username[username] for username in order]


def slugify(value: str, fallback: str = "page") -> str:
    value = value.strip().lower()
    value = re.sub(r"([A-Z])", r"-\1", value).lower()
    value = re.sub(r"[^a-z0-9\u4e00-\u9fff]+", "-", value).strip("-")
    return value or fallback


def role_file_prefix(account: dict[str, Any]) -> str:
    role = normalize_role(account)
    username = slugify(str(account.get("username") or ""), "")
    if username and username != role:
        return f"{role}-{username}"
    return role


def best_button_text(text: str) -> bool:
    keywords = [
        "新增",
        "添加",
        "创建",
        "发布",
        "提交",
        "申请",
        "登记",
        "预约",
        "上报",
        "审核",
    ]
    negative = ["查询", "搜索", "重置", "取消", "退出", "返回", "删除", "编辑", "保存"]
    return any(word in text for word in keywords) and not any(word in text for word in negative)


def current_database_name(runtime: dict[str, Any]) -> str | None:
    sql_file_value = runtime.get("sql_file")
    if sql_file_value:
        sql_file = ROOT / sql_file_value
        if sql_file.exists():
            text = sql_file.read_text(encoding="utf-8", errors="ignore")
            for pattern in [
                r"CREATE\s+DATABASE(?:\s+IF\s+NOT\s+EXISTS)?\s+`?([A-Za-z0-9_]+)`?",
                r"USE\s+`?([A-Za-z0-9_]+)`?",
            ]:
                match = re.search(pattern, text, flags=re.I)
                if match:
                    return match.group(1)
    backend_value = runtime.get("project", {}).get("backend_dir")
    if backend_value:
        backend_dir = ROOT / backend_value
        config_text = run_preview.read_backend_config_text(backend_dir)
        match = re.search(r"jdbc:mysql://[^/]+/([A-Za-z0-9_]+)", config_text)
        if match:
            return match.group(1)
    return None


def make_preview_password_hash(existing_hash: str | None, password: str) -> str:
    value = existing_hash or ""
    if value.startswith(("$2a$", "$2b$", "$2y$")):
        return bcrypt.hashpw(password.encode(), bcrypt.gensalt(rounds=10)).decode()
    return hashlib.md5(password.encode()).hexdigest()


def patch_preview_password(runtime: dict[str, Any], username: str, password: str) -> bool:
    database = current_database_name(runtime)
    if not database:
        return False
    connection = pymysql.connect(
        host=run_preview.MYSQL_HOST,
        port=run_preview.MYSQL_PORT,
        user=run_preview.MYSQL_USER,
        password=run_preview.MYSQL_PASSWORD,
        database=database,
        charset="utf8mb4",
        autocommit=True,
    )
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT `password` FROM `user` WHERE `username`=%s LIMIT 1", (username,))
            row = cursor.fetchone()
            existing_hash = row[0] if row else None
            digest = make_preview_password_hash(existing_hash, password)
            cursor.execute("UPDATE `user` SET `password`=%s WHERE `username`=%s", (digest, username))
            return cursor.rowcount > 0
    except Exception:
        return False
    finally:
        connection.close()


def api_request_json(
    page,
    method: str,
    url: str,
    token: str | None = None,
    data: dict[str, Any] | None = None,
) -> dict[str, Any] | None:
    request_method = getattr(page.request, method.lower())

    def do_request(auth_value: str | None) -> tuple[dict[str, Any] | None, bool]:
        headers = {"Content-Type": "application/json"}
        if auth_value:
            headers["Authorization"] = auth_value
        try:
            if data is None:
                response = request_method(url, headers=headers, timeout=10000)
            else:
                response = request_method(
                    url,
                    data=json.dumps(data, ensure_ascii=False),
                    headers=headers,
                    timeout=10000,
                )
        except Exception:
            return None, False
        if not response.ok:
            return None, False
        try:
            payload = response.json()
        except Exception:
            return None, response.ok
        return payload if isinstance(payload, dict) else None, response.ok

    bearer_value = f"Bearer {token}" if token else None
    payload, ok = do_request(bearer_value)
    if token and (not ok or (payload and str(payload.get("code")) == "401")):
        payload, _ = do_request(token)
    return payload


def api_request_json_retry(
    page,
    method: str,
    url: str,
    token: str | None = None,
    data: dict[str, Any] | None = None,
    attempts: int = 5,
    delay_ms: int = 800,
) -> dict[str, Any] | None:
    payload: dict[str, Any] | None = None
    for attempt in range(max(1, attempts)):
        payload = api_request_json(page, method, url, token=token, data=data)
        if isinstance(payload, dict):
            return payload
        if attempt < attempts - 1:
            page.wait_for_timeout(delay_ms)
    return payload


def login_payloads(username: str, password: str) -> list[dict[str, str]]:
    return [
        {"username": username, "password": password},
        {"studentNo": username, "password": password},
        {"account": username, "password": password},
    ]


def api_login_payload(page, base_url: str, username: str, password: str) -> dict[str, Any] | None:
    paths = ["/api/user/login", "/api/auth/login"]
    payloads = login_payloads(username, password)
    for path in paths:
        for data in payloads:
            payload = api_request_json(
                page,
                "post",
                f"{base_url}{path}",
                data=data,
            )
            if not payload or payload.get("code") not in {0, 200, "0", "200"}:
                continue
            response_data = payload.get("data")
            if isinstance(response_data, dict):
                return response_data
    return None


def api_login_payload_retry(
    page,
    base_url: str,
    username: str,
    password: str,
    attempts: int = 6,
    delay_ms: int = 800,
) -> dict[str, Any] | None:
    payload: dict[str, Any] | None = None
    for attempt in range(max(1, attempts)):
        payload = api_login_payload(page, base_url, username, password)
        if isinstance(payload, dict):
            return payload
        if attempt < attempts - 1:
            page.wait_for_timeout(delay_ms)
    return payload


def login_user_from_payload(data: dict[str, Any]) -> dict[str, Any] | None:
    for key in ["user", "userInfo", "account"]:
        value = data.get(key)
        if isinstance(value, dict):
            user = dict(value)
            if "id" not in user and user.get("userId") is not None:
                user["id"] = user["userId"]
            if "userId" not in user and user.get("id") is not None:
                user["userId"] = user["id"]
            return user
    user_id = data.get("userId") or data.get("id")
    if user_id is None and not any(data.get(key) for key in ["username", "realName", "avatar", "creditScore"]):
        return None
    user = {
        "id": user_id,
        "userId": user_id,
        "username": data.get("username"),
        "realName": data.get("realName"),
        "avatar": data.get("avatar"),
        "creditScore": data.get("creditScore"),
    }
    return {key: value for key, value in user.items() if value is not None}


def browser_login_storage_script() -> str:
    return """([token, user]) => {
        localStorage.setItem('token', token);
        localStorage.setItem('Authorization', token);
        let userType = '';
        if (user) {
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.setItem('userInfo', JSON.stringify(user));
            const role = String(user.userType || user.role || '').toLowerCase();
            if (role === 'admin' || user.isAdmin === true) {
                userType = 'admin';
            } else if (role === 'merchant' || role === 'seller' || role === 'shop') {
                userType = 'merchant';
            } else if (role === 'user' || user.isAdmin === false || user.id || user.userId) {
                userType = 'user';
            }
        }
        if (userType) {
            localStorage.setItem('userType', userType);
        }
    }"""


def set_browser_login(page, base_url: str, token: str, user: dict[str, Any] | None) -> None:
    seed_url = f"{base_url}/__codex_login_seed__"
    try:
        page.goto(seed_url, wait_until="domcontentloaded", timeout=15000)
    except Exception:
        page.goto(f"{base_url}/", wait_until="domcontentloaded", timeout=15000)
    page.evaluate(browser_login_storage_script(), [token, user])


def api_login(page, base_url: str, username: str, password: str) -> bool:
    data = api_login_payload(page, base_url, username, password)
    if not data:
        return False
    token = data.get("token") or data.get("accessToken") or data.get("jwt")
    user = login_user_from_payload(data)
    if not token:
        return False
    set_browser_login(page, base_url, token, user)
    role = str(user.get("role") if isinstance(user, dict) else "").upper()
    start_paths = [
        "/",
        "/dashboard",
        "/home",
        "/index",
        "/admin",
        "/pages/admin/index.html" if role == "ADMIN" else "/pages/user/index.html",
        "/admin/index.html" if role == "ADMIN" else "/index.html",
        "/index.html",
    ]
    for path in start_paths:
        if not path:
            continue
        try:
            page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
            page.wait_for_timeout(800)
        except Exception:
            continue
        current = page.url.lower()
        text = page_text(page)
        if "/login" not in current and "no static resource" not in text.lower() and len(text.strip()) >= 20:
            return True
    return False


def find_account(accounts: list[dict[str, Any]], role: str | None = None, username: str | None = None) -> dict[str, Any] | None:
    for account in accounts:
        if username and str(account.get("username") or "") != username:
            continue
        if role and normalize_role(account) != role:
            continue
        return account
    return None


def capture_project_005(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")
    if not admin:
        return []
    username = str(admin.get("username") or "")
    password = str(admin.get("password") or "")
    login_data = api_login_payload(page, base_url, username, password)
    if not login_data:
        return []
    token = str(login_data.get("token") or "")
    user = login_data.get("user") if isinstance(login_data.get("user"), dict) else None
    if not token:
        return []

    stamp = time.strftime("%H%M%S")
    title = f"毕业设计满意度问卷 {stamp}"
    api_request_json(
        page,
        "post",
        f"{base_url}/api/survey/create",
        token=token,
        data={"title": title, "description": "用于项目预览截图的样例问卷，覆盖编辑、发布、填写和统计流程。"},
    )
    list_payload = api_request_json(page, "get", f"{base_url}/api/survey/list", token=token)
    surveys = list_payload.get("data") if list_payload else []
    survey = None
    if isinstance(surveys, list):
        for item in surveys:
            if isinstance(item, dict) and item.get("title") == title:
                survey = item
                break
        if not survey and surveys:
            survey = surveys[0]
    if not isinstance(survey, dict) or not survey.get("id"):
        return []
    survey_id = survey["id"]

    question_specs = [
        ("radio", "你对系统界面清晰度的评价", ["非常清晰", "基本清晰", "需要改进"]),
        ("checkbox", "你最关注哪些功能", ["问卷发布", "数据统计", "分享链接", "Excel导出"]),
        ("input", "请写下对系统的建议", None),
        ("rate", "整体满意度评分", None),
        ("select", "你的使用角色", ["管理员", "教师", "学生", "游客"]),
    ]
    question_ids: list[int] = []
    for index, (question_type, question_title, options) in enumerate(question_specs):
        api_request_json(
            page,
            "post",
            f"{base_url}/api/question/add",
            token=token,
            data={
                "surveyId": survey_id,
                "type": question_type,
                "title": question_title,
                "options": json.dumps(options, ensure_ascii=False) if options else None,
                "required": 1,
                "orderNo": index,
            },
        )
    questions_payload = api_request_json(page, "get", f"{base_url}/api/question/list/{survey_id}", token=token)
    questions = questions_payload.get("data") if questions_payload else []
    if isinstance(questions, list):
        question_ids = [int(item["id"]) for item in questions if isinstance(item, dict) and item.get("id")]

    records: list[ShotRecord] = []
    set_browser_login(page, base_url, token, user)
    for filename, label, path in [
        ("admin-10-create-form.png", "project 005: create survey form", "/survey/create"),
        ("admin-11-dashboard-draft.png", "project 005: dashboard with draft survey", "/dashboard"),
        ("admin-12-edit-questions.png", "project 005: edit survey questions", f"/survey/edit?id={survey_id}"),
    ]:
        page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=10000)
        records.append(capture_current_page(page, asset_dir / filename, label))

    api_request_json(page, "put", f"{base_url}/api/survey/publish/{survey_id}", token=token)
    page.goto(f"{base_url}/dashboard", wait_until="domcontentloaded", timeout=15000)
    wait_for_page_settle(page, timeout=10000)
    records.append(
        capture_current_page(page, asset_dir / "admin-13-dashboard-published.png", "project 005: published survey")
    )

    page.goto(f"{base_url}/survey/fill?id={survey_id}", wait_until="domcontentloaded", timeout=15000)
    wait_for_page_settle(page, timeout=10000)
    records.append(capture_current_page(page, asset_dir / "guest-10-fill-form.png", "project 005: fill survey form"))

    if question_ids:
        detail_values = ["非常清晰", "问卷发布,数据统计", "页面完整，适合演示。", "5", "学生"]
        api_request_json(
            page,
            "post",
            f"{base_url}/api/answer/submit",
            data={
                "surveyId": survey_id,
                "details": [
                    {"questionId": question_id, "value": detail_values[index % len(detail_values)]}
                    for index, question_id in enumerate(question_ids)
                ],
            },
        )

    page.goto(f"{base_url}/survey/stat?id={survey_id}", wait_until="domcontentloaded", timeout=15000)
    wait_for_page_settle(page, timeout=12000)
    records.append(capture_current_page(page, asset_dir / "admin-14-statistics.png", "project 005: statistics"))

    user_account = find_account(accounts, username="user1") or find_account(accounts, role="user")
    if user_account:
        user_login = api_login_payload(
            page,
            base_url,
            str(user_account.get("username") or ""),
            str(user_account.get("password") or ""),
        )
        if user_login and user_login.get("token"):
            user_payload = user_login.get("user") if isinstance(user_login.get("user"), dict) else None
            set_browser_login(page, base_url, str(user_login["token"]), user_payload)
            page.goto(f"{base_url}/dashboard", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-user1-10-dashboard-after-publish.png",
                    "project 005: user dashboard after publish",
                )
            )
    return records


def login_as_account(page, base_url: str, account: dict[str, Any]) -> bool:
    try:
        page.evaluate("localStorage.clear(); sessionStorage.clear();")
    except Exception:
        pass
    return login(page, base_url, str(account.get("username") or ""), str(account.get("password") or ""))


def capture_path(page, base_url: str, asset_dir: Path, file_name: str, label: str, path: str) -> ShotRecord | None:
    try:
        page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=10000)
        page.wait_for_timeout(800)
        return capture_current_page(page, asset_dir / file_name, label)
    except Exception:
        return None


def capture_project_006(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")
    user1 = find_account(accounts, username="user1") or find_account(accounts, role="user")
    user2 = find_account(accounts, username="user2")

    if user1 and login_as_account(page, base_url, user1):
        pages = [
            ("user-user1-10-lost-list.png", "project 006: lost list", "/lost"),
            ("user-user1-11-lost-detail.png", "project 006: lost detail", "/lost/detail?id=1"),
            ("user-user1-12-publish-lost.png", "project 006: publish lost", "/lost/publish"),
            ("user-user1-13-found-list.png", "project 006: found list", "/found"),
            ("user-user1-14-found-detail.png", "project 006: found detail", "/found/detail?id=1"),
            ("user-user1-15-publish-found.png", "project 006: publish found", "/found/publish"),
            ("user-user1-16-my-lost.png", "project 006: my lost", "/my/lost"),
            ("user-user1-17-my-found.png", "project 006: my found", "/my/found"),
            ("user-user1-18-claims-sent.png", "project 006: sent claims", "/claims/sent"),
            ("user-user1-19-claims-received.png", "project 006: received claims", "/claims/received"),
            ("user-user1-20-favorites.png", "project 006: favorites", "/favorites"),
        ]
        for file_name, label, path in pages:
            record = capture_path(page, base_url, asset_dir, file_name, label, path)
            if record:
                records.append(record)

        try:
            page.goto(f"{base_url}/lost/detail?id=1", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            button = page.locator("button:visible").filter(has_text=re.compile("我要认领|认领"))
            if button.count() > 0:
                button.first.click(timeout=3000)
                page.wait_for_timeout(600)
                records.append(
                    capture_current_page(
                        page,
                        asset_dir / "user-user1-21-lost-claim-dialog.png",
                        "project 006: lost claim dialog",
                    )
                )
        except Exception:
            pass

    if user2 and login_as_account(page, base_url, user2):
        for file_name, label, path in [
            ("user-user2-10-my-found.png", "project 006: user2 my found", "/my/found"),
            ("user-user2-11-claims-received.png", "project 006: user2 received claims", "/claims/received"),
        ]:
            record = capture_path(page, base_url, asset_dir, file_name, label, path)
            if record:
                records.append(record)

    if admin and login_as_account(page, base_url, admin):
        for file_name, label, path in [
            ("admin-10-users.png", "project 006: admin users", "/admin/users"),
            ("admin-11-categories.png", "project 006: admin categories", "/admin/categories"),
        ]:
            record = capture_path(page, base_url, asset_dir, file_name, label, path)
            if record:
                records.append(record)

    return records


def capture_project_008(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")
    user = find_account(accounts, role="user") or find_account(accounts, username="user1")

    if admin and api_login(page, base_url, str(admin.get("username") or ""), str(admin.get("password") or "")):
        for file_name, label, path in [
            ("admin-10-dashboard.png", "project 008: admin dashboard", "/pages/admin/index.html"),
            ("admin-11-recipes.png", "project 008: admin recipes", "/pages/admin/recipes.html"),
            ("admin-12-ingredients.png", "project 008: admin ingredients", "/pages/admin/ingredients.html"),
            ("admin-13-users.png", "project 008: admin users", "/pages/admin/users.html"),
        ]:
            record = capture_path(page, base_url, asset_dir, file_name, label, path)
            if record:
                records.append(record)

    if user and api_login(page, base_url, str(user.get("username") or ""), str(user.get("password") or "")):
        for file_name, label, path in [
            ("user-user1-10-home.png", "project 008: user home", "/pages/user/index.html"),
            ("user-user1-11-recipes.png", "project 008: recipes", "/pages/user/recipes.html"),
            ("user-user1-12-recommend.png", "project 008: recommendations", "/pages/user/recommend.html"),
            ("user-user1-13-ingredients.png", "project 008: user ingredients", "/pages/user/ingredients.html"),
            ("user-user1-14-shopping.png", "project 008: shopping list", "/pages/user/shopping.html"),
            ("user-user1-15-cooking.png", "project 008: cooking records", "/pages/user/cooking.html"),
            ("user-user1-16-collect.png", "project 008: collections", "/pages/user/collect.html"),
            ("user-user1-17-profile.png", "project 008: profile", "/pages/user/profile.html"),
            ("user-user1-18-recipe-detail.png", "project 008: recipe detail", "/pages/user/recipe-detail.html?id=1"),
        ]:
            record = capture_path(page, base_url, asset_dir, file_name, label, path)
            if record:
                records.append(record)
    return records


def capture_project_010(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")
    student = find_account(accounts, role="student") or find_account(accounts, role="user")

    if student and login_as_account(page, base_url, student):
        try:
            page.goto(f"{base_url}/booking.html", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
            page.wait_for_function(
                """() => {
                    const timeSlot = document.querySelector('#timeSlot');
                    const floor = document.querySelector('#floor');
                    return timeSlot && floor && timeSlot.options.length > 1 && floor.options.length > 1;
                }""",
                timeout=10000,
            )
            page.locator("#timeSlot").select_option(index=1)
            page.locator("#floor").select_option(index=1)
            page.wait_for_function(
                "document.querySelector('#area') && document.querySelector('#area').options.length > 1",
                timeout=10000,
            )
            page.locator("#area").select_option(index=1)
            page.wait_for_selector(".seat-item", timeout=10000)
            page.wait_for_timeout(500)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "student-20240001-10-seat-map-selected.png",
                    "project 010: selected seat map",
                )
            )
            page.locator(".seat-item").first.click(timeout=3000)
            page.wait_for_timeout(500)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "student-20240001-11-seat-confirm-dialog.png",
                    "project 010: seat booking confirm dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

        try:
            page.goto(f"{base_url}/profile.html", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
            page.locator("button").filter(has_text="修改密码").first.click(timeout=3000)
            page.wait_for_timeout(500)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "student-20240001-12-password-dialog.png",
                    "project 010: password dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

    if admin and login_as_account(page, base_url, admin):
        try:
            page.goto(f"{base_url}/admin/seats.html", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
            page.locator('a[href="#seatTab"]').click(timeout=3000)
            page.wait_for_timeout(500)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "admin-20-seat-tab.png",
                    "project 010: admin seat tab",
                )
            )
            page.locator("#seatTab button").filter(has_text="添加座位").first.click(timeout=3000)
            page.wait_for_timeout(500)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "admin-21-seat-dialog.png",
                    "project 010: admin seat dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

    return records


def seed_project_011(runtime: dict[str, Any]) -> None:
    database = current_database_name(runtime)
    if not database:
        return
    connection = pymysql.connect(
        host=run_preview.MYSQL_HOST,
        port=run_preview.MYSQL_PORT,
        user=run_preview.MYSQL_USER,
        password=run_preview.MYSQL_PASSWORD,
        database=database,
        charset="utf8mb4",
        autocommit=True,
    )
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT id FROM `user` WHERE username=%s LIMIT 1", ("test1",))
            test1 = cursor.fetchone()
            cursor.execute("SELECT id FROM `user` WHERE username=%s LIMIT 1", ("test2",))
            test2 = cursor.fetchone()
            if not test1 or not test2:
                return
            test1_id = test1[0]
            test2_id = test2[0]
            videos = [
                (
                    test2_id,
                    "图书馆晨读打卡",
                    "清晨七点的自习室很安静，适合背单词和做计划。",
                    "/videos/preview-library.mp4",
                    "/covers/preview-library.jpg",
                    "图书馆二楼",
                    1280,
                    720,
                    126,
                    420,
                    32,
                    8,
                    5,
                    18,
                    "168.50",
                ),
                (
                    test1_id,
                    "操场夜跑记录",
                    "和舍友一起完成三公里，顺手记录一下晚风。",
                    "/videos/preview-running.mp4",
                    "/covers/preview-running.jpg",
                    "东区操场",
                    1080,
                    1920,
                    88,
                    260,
                    21,
                    4,
                    7,
                    11,
                    "142.00",
                ),
            ]
            for video in videos:
                cursor.execute(
                    """
                    INSERT INTO video (
                        user_id, title, description, video_url, cover_url, location,
                        width, height, duration, play_count, like_count, comment_count,
                        share_count, collect_count, heat_score, status, permission, is_top
                    )
                    SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,1,1,0
                    WHERE NOT EXISTS (SELECT 1 FROM video WHERE title=%s)
                    """,
                    (*video, video[1]),
                )

            cursor.execute("SELECT id FROM video WHERE title=%s LIMIT 1", ("图书馆晨读打卡",))
            video_row = cursor.fetchone()
            if not video_row:
                return
            video_id = video_row[0]
            for topic_id in [1, 2]:
                cursor.execute(
                    """
                    INSERT IGNORE INTO video_topic (video_id, topic_id)
                    VALUES (%s, %s)
                    """,
                    (video_id, topic_id),
                )
            cursor.execute(
                """
                INSERT IGNORE INTO user_follow (user_id, follow_user_id)
                VALUES (%s, %s)
                """,
                (test1_id, test2_id),
            )
            cursor.execute(
                """
                INSERT IGNORE INTO video_like (user_id, video_id)
                VALUES (%s, %s)
                """,
                (test1_id, video_id),
            )
            cursor.execute(
                """
                INSERT IGNORE INTO video_collect (user_id, video_id)
                VALUES (%s, %s)
                """,
                (test1_id, video_id),
            )
            cursor.execute(
                """
                INSERT INTO notification (user_id, from_user_id, type, content, related_id, is_read)
                SELECT %s, %s, 'LIKE', '小红点赞了你的操场夜跑记录', %s, 0
                WHERE NOT EXISTS (
                    SELECT 1 FROM notification WHERE user_id=%s AND content='小红点赞了你的操场夜跑记录'
                )
                """,
                (test1_id, test2_id, video_id, test1_id),
            )
            cursor.execute(
                """
                INSERT INTO user_points_log (
                    user_id, change_type, change_points, before_points, after_points, reason, related_id
                )
                SELECT %s, 'VIDEO', 10, 490, 500, '发布视频', %s
                WHERE NOT EXISTS (
                    SELECT 1 FROM user_points_log WHERE user_id=%s AND reason='发布视频'
                )
                """,
                (test1_id, video_id, test1_id),
            )
            cursor.execute(
                """
                INSERT INTO video_draft (
                    user_id, title, description, video_url, cover_url, topic_ids, location
                )
                SELECT %s, '校园食堂探店草稿', '准备补拍几个菜品特写后发布。', '', '', '1,4', '南区食堂'
                WHERE NOT EXISTS (
                    SELECT 1 FROM video_draft WHERE user_id=%s AND title='校园食堂探店草稿'
                )
                """,
                (test1_id, test1_id),
            )
            cursor.execute(
                """
                UPDATE `user`
                SET video_count = (SELECT COUNT(*) FROM video WHERE user_id=%s AND status=1),
                    follow_count = (SELECT COUNT(*) FROM user_follow WHERE user_id=%s),
                    points = 500
                WHERE id=%s
                """,
                (test1_id, test1_id, test1_id),
            )
            cursor.execute(
                """
                UPDATE `user`
                SET fans_count = (SELECT COUNT(*) FROM user_follow WHERE follow_user_id=%s)
                WHERE id=%s
                """,
                (test2_id, test2_id),
            )
    finally:
        connection.close()


def capture_project_011(
    page,
    runtime: dict[str, Any],
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    seed_project_011(runtime)
    records: list[ShotRecord] = []
    user = find_account(accounts, username="test1") or find_account(accounts, role="user")
    if not user or not login_as_account(page, base_url, user):
        return records

    for file_name, label, path in [
        ("user-test1-10-recommend-seeded.png", "project 011: seeded recommend feed", "/"),
        ("user-test1-11-following-feed.png", "project 011: following feed", "/following"),
        ("user-test1-12-video-detail.png", "project 011: video detail", "/video/1"),
        ("user-test1-13-creator-center.png", "project 011: creator center", "/creator-center"),
        ("user-test1-14-drafts.png", "project 011: draft box", "/drafts"),
        ("user-test1-15-points-mall.png", "project 011: points mall", "/points-mall"),
    ]:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    try:
        page.goto(f"{base_url}/search", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=8000)
        page.locator("input").first.fill("小红")
        page.keyboard.press("Enter")
        page.wait_for_timeout(1000)
        user_tab = page.locator(".el-tabs__item").filter(has_text="用户")
        if user_tab.count() > 0:
            user_tab.first.click(timeout=3000)
            page.wait_for_timeout(800)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-16-search-users.png",
                "project 011: search users",
            )
        )
    except Exception:
        pass

    try:
        page.goto(f"{base_url}/profile", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=8000)
        page.locator("button").filter(has_text="编辑资料").first.click(timeout=3000)
        page.wait_for_timeout(500)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-17-profile-edit-dialog.png",
                "project 011: profile edit dialog",
            )
        )
        close_dialog(page)
    except Exception:
        pass

    return records


def unwrap_api_data(payload: dict[str, Any] | None) -> Any:
    if not isinstance(payload, dict):
        return None
    return payload.get("data") if "data" in payload else payload


def first_record_id(payload: dict[str, Any] | None) -> int | None:
    data = unwrap_api_data(payload)
    if isinstance(data, dict) and isinstance(data.get("records"), list) and data["records"]:
        record_id = data["records"][0].get("id")
        if record_id is not None:
            return int(record_id)
    return None


def create_project_012_document(
    page,
    base_url: str,
    token: str,
    title: str,
    doc_type: str,
    content: str,
) -> int | None:
    payload = api_request_json(
        page,
        "post",
        f"{base_url}/api/document/create",
        token=token,
        data={"title": title, "docType": doc_type},
    )
    document = unwrap_api_data(payload)
    if not isinstance(document, dict) or not document.get("id"):
        return None
    document_id = int(document["id"])
    api_request_json(
        page,
        "put",
        f"{base_url}/api/document/{document_id}",
        token=token,
        data={"title": title, "content": content},
    )
    return document_id


def capture_project_012(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    user = find_account(accounts, username="test1") or find_account(accounts, role="user")
    if not user:
        return []
    username = str(user.get("username") or "")
    password = str(user.get("password") or "")
    login_data = api_login_payload(page, base_url, username, password)
    if not login_data:
        return []
    token = str(login_data.get("token") or "")
    browser_user = login_data.get("user") if isinstance(login_data.get("user"), dict) else None
    if not token:
        return []

    stamp = time.strftime("%H%M%S")
    note_id = create_project_012_document(
        page,
        base_url,
        token,
        f"精修协作笔记 {stamp}",
        "NOTE",
        "# 精修协作笔记\n\n## 会议纪要\n- 明确截图范围\n- 验证版本、协作、分享和附件弹窗\n\n> 这是一份用于预览截图的 Markdown 内容。",
    )
    board_id = create_project_012_document(
        page,
        base_url,
        token,
        f"精修白板画布 {stamp}",
        "BOARD",
        json.dumps(
            {
                "version": 1,
                "type": "BOARD",
                "background": "#ffffff",
                "strokes": [
                    {
                        "color": "#2b59ff",
                        "size": 7,
                        "points": [
                            {"x": 0.18, "y": 0.28},
                            {"x": 0.28, "y": 0.18},
                            {"x": 0.42, "y": 0.32},
                            {"x": 0.55, "y": 0.22},
                        ],
                    },
                    {
                        "color": "#12b886",
                        "size": 5,
                        "points": [
                            {"x": 0.2, "y": 0.58},
                            {"x": 0.38, "y": 0.48},
                            {"x": 0.62, "y": 0.62},
                            {"x": 0.78, "y": 0.42},
                        ],
                    },
                ],
            },
            ensure_ascii=False,
        ),
    )
    mindmap_id = create_project_012_document(
        page,
        base_url,
        token,
        f"精修脑图方案 {stamp}",
        "MINDMAP",
        json.dumps(
            {
                "version": 1,
                "type": "MINDMAP",
                "root": {
                    "id": "root",
                    "text": "毕业设计截图复核",
                    "note": "围绕一个项目做精修验证",
                    "children": [
                        {
                            "id": "node-login",
                            "text": "登录角色",
                            "note": "测试用户进入工作台",
                            "children": [],
                        },
                        {
                            "id": "node-flow",
                            "text": "核心流程",
                            "note": "创建、编辑、版本、分享",
                            "children": [],
                        },
                    ],
                },
            },
            ensure_ascii=False,
        ),
    )
    if not note_id:
        return []

    records: list[ShotRecord] = []
    set_browser_login(page, base_url, token, browser_user)

    try:
        page.goto(f"{base_url}/documents", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=10000)
        page.get_by_text("精修协作笔记").first.wait_for(timeout=8000)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-10-documents-created.png",
                "project 012: created documents list",
            )
        )
        page.locator("button").filter(has_text="新建文档").first.click(timeout=3000)
        page.wait_for_selector(".modal.show", timeout=5000)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-11-create-document-dialog.png",
                "project 012: create document dialog",
            )
        )
        close_dialog(page)
    except Exception:
        pass

    try:
        page.goto(f"{base_url}/document/{note_id}", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=12000)
        page.wait_for_selector("#noteInput", timeout=10000)
        page.wait_for_selector("#notePreview", timeout=10000)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-12-note-editor.png",
                "project 012: markdown note editor",
            )
        )

        api_request_json(
            page,
            "post",
            f"{base_url}/api/document/{note_id}/versions",
            token=token,
            data={"versionName": "精修截图版本", "changeLog": "记录 Markdown 编辑器预览状态"},
        )
        page.locator("#versionBtn").click(timeout=3000)
        page.wait_for_selector("#versionModal.show", timeout=6000)
        page.wait_for_timeout(800)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-13-version-dialog.png",
                "project 012: version history dialog",
            )
        )
        close_dialog(page)

        page.locator("#collabBtn").click(timeout=3000)
        page.wait_for_selector("#collabModal.show", timeout=6000)
        page.locator("#collabKeywordInput").fill("test2")
        page.locator("#collabSearchBtn").click(timeout=3000)
        page.wait_for_timeout(1000)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-14-collaboration-dialog.png",
                "project 012: collaborator search dialog",
            )
        )
        close_dialog(page)

        page.locator("#shareBtn").click(timeout=3000)
        page.wait_for_selector("#shareModal.show", timeout=6000)
        page.locator("#sharePasswordInput").fill("share")
        page.locator("#createShareBtn").click(timeout=3000)
        page.get_by_text("分享链接已生成").first.wait_for(timeout=8000)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-15-share-dialog.png",
                "project 012: share link dialog",
            )
        )
        close_dialog(page)

        page.locator("#attachmentBtn").click(timeout=3000)
        page.wait_for_selector("#attachmentModal.show", timeout=6000)
        page.wait_for_timeout(800)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-16-attachment-dialog.png",
                "project 012: attachment dialog",
            )
        )
        close_dialog(page)
    except Exception:
        pass

    if board_id:
        try:
            page.goto(f"{base_url}/document/{board_id}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=12000)
            page.wait_for_selector("#boardCanvas", timeout=10000)
            page.wait_for_timeout(800)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-17-board-editor.png",
                    "project 012: board editor",
                )
            )
        except Exception:
            pass

    if mindmap_id:
        try:
            page.goto(f"{base_url}/document/{mindmap_id}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=12000)
            page.wait_for_selector("#mindmapTree", timeout=10000)
            page.wait_for_timeout(800)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-18-mindmap-editor.png",
                    "project 012: mindmap editor",
                )
            )
        except Exception:
            pass

    try:
        page.goto(f"{base_url}/templates", wait_until="domcontentloaded", timeout=15000)
        wait_for_page_settle(page, timeout=10000)
        page.get_by_text("会议纪要").first.wait_for(timeout=8000)
        records.append(
            capture_current_page(
                page,
                asset_dir / "user-test1-19-template-market-loaded.png",
                "project 012: template market loaded",
            )
        )
        button = page.locator("button").filter(has_text=re.compile("^\\s*使用\\s*$|使用模板"))
        if button.count() > 0:
            button.first.click(timeout=3000)
            wait_for_page_settle(page, timeout=10000)
            page.wait_for_selector("#docTitle", timeout=10000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-20-template-used-editor.png",
                    "project 012: template used editor",
                )
            )
    except Exception:
        pass

    share_payload = api_request_json(
        page,
        "post",
        f"{base_url}/api/document/{note_id}/share",
        token=token,
        data={"password": "", "expireHours": 24},
    )
    share_data = unwrap_api_data(share_payload)
    if isinstance(share_data, dict) and share_data.get("shareLink"):
        try:
            page.goto(f"{base_url}/share/{share_data['shareLink']}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.wait_for_selector("#shareContent:not(.d-none), #shareGate:not(.d-none)", timeout=10000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "guest-10-share-view.png",
                    "project 012: public share view",
                )
            )
        except Exception:
            pass

    return records


def capture_project_013(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")
    user = find_account(accounts, username="test1") or find_account(accounts, role="user")

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    if user and login_as_account(page, base_url, user):
        for file_name, label, path in [
            ("user-test1-10-map-items.png", "project 013: shared item map", "/map"),
            ("user-test1-11-idle-list.png", "project 013: idle item list", "/idle"),
            ("user-test1-12-idle-detail.png", "project 013: idle item detail", "/idle/1"),
            ("user-test1-13-idle-publish-form.png", "project 013: idle publish form", "/idle/publish"),
            ("user-test1-14-skill-list.png", "project 013: skill list", "/skill"),
            ("user-test1-15-skill-detail.png", "project 013: skill detail", "/skill/1"),
            ("user-test1-16-skill-publish-form.png", "project 013: skill publish form", "/skill/publish"),
            ("user-test1-17-order-list.png", "project 013: order list", "/order"),
            ("user-test1-18-order-detail.png", "project 013: order detail", "/order/1"),
            ("user-test1-19-message-list.png", "project 013: message list", "/message"),
            ("user-test1-20-profile.png", "project 013: profile", "/profile"),
            ("user-test1-21-auth-status.png", "project 013: auth status", "/auth"),
            ("user-test1-22-wallet.png", "project 013: wallet", "/wallet"),
            ("user-test1-23-credit.png", "project 013: credit center", "/credit"),
            ("user-test1-24-favorite.png", "project 013: favorite list", "/favorite"),
            ("user-test1-25-my-publish.png", "project 013: my publish", "/my-publish"),
            ("user-test1-26-income.png", "project 013: income center", "/income"),
        ]:
            add_path(file_name, label, path)

        try:
            page.goto(f"{base_url}/map", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator(".item-card").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible", timeout=5000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-27-rent-confirm-dialog.png",
                    "project 013: shared item rent dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

        try:
            page.goto(f"{base_url}/skill/1", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button").filter(has_text="立即预约").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible", timeout=5000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-28-skill-book-dialog.png",
                    "project 013: skill booking dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

        try:
            page.goto(f"{base_url}/wallet", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button").filter(has_text="充值").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible", timeout=5000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-29-deposit-dialog.png",
                    "project 013: deposit charge dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

        try:
            page.goto(f"{base_url}/income", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button").filter(has_text="申请提现").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible", timeout=5000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-test1-30-withdraw-dialog.png",
                    "project 013: withdraw dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

    if admin and login_as_account(page, base_url, admin):
        for file_name, label, path in [
            ("admin-10-dashboard.png", "project 013: admin dashboard", "/admin/dashboard"),
            ("admin-11-users.png", "project 013: admin users", "/admin/users"),
            ("admin-12-auth-list.png", "project 013: auth review list", "/admin/auth-list"),
            ("admin-13-idle-audit.png", "project 013: idle audit", "/admin/idle-audit"),
            ("admin-14-shared-items.png", "project 013: shared item management", "/admin/shared-items"),
        ]:
            add_path(file_name, label, path)

        try:
            page.goto(f"{base_url}/admin/shared-items", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button").filter(has_text="添加物品").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible", timeout=5000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "admin-15-shared-item-dialog.png",
                    "project 013: add shared item dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

        try:
            page.goto(f"{base_url}/admin/auth-list", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            button = page.locator("button").filter(has_text="拒绝")
            if button.count() > 0:
                button.first.click(timeout=4000)
                page.wait_for_selector(".el-dialog:visible", timeout=5000)
                records.append(
                    capture_current_page(
                        page,
                        asset_dir / "admin-16-auth-reject-dialog.png",
                        "project 013: auth reject dialog",
                    )
                )
                close_dialog(page)
        except Exception:
            pass

    return records


def capture_project_014(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    student = find_account(accounts, username="student1") or find_account(accounts, role="student")
    admin = find_account(accounts, username="admin") or find_account(accounts, role="admin")

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    if student:
        login_data = api_login_payload(page, base_url, str(student.get("username") or ""), str(student.get("password") or ""))
        if login_data:
            token = str(login_data.get("token") or "")
            user = login_data.get("user") if isinstance(login_data.get("user"), dict) else None
            if token:
                set_browser_login(page, base_url, token, user)
                for file_name, label, path in [
                    ("student-student1-10-home.png", "project 014: home with seeded cards", "/index.html"),
                    ("student-student1-11-clubs.png", "project 014: clubs list", "/clubs.html"),
                    ("student-student1-12-club-detail.png", "project 014: club detail", "/club-detail.html?id=1"),
                    ("student-student1-13-create-club.png", "project 014: create club", "/create-club.html"),
                    ("student-student1-14-activities.png", "project 014: activities list", "/activities.html"),
                    ("student-student1-15-activity-detail.png", "project 014: activity detail", "/activity-detail.html?id=1"),
                    ("student-student1-16-topics.png", "project 014: topics list", "/topics.html"),
                    ("student-student1-17-topic-detail.png", "project 014: topic detail", "/topic-detail.html?id=1"),
                    ("student-student1-18-create-topic.png", "project 014: create topic", "/create-topic.html"),
                    ("student-student1-19-circles.png", "project 014: circles list", "/circles.html"),
                    ("student-student1-20-create-circle.png", "project 014: create circle", "/create-circle.html"),
                    ("student-student1-21-profile.png", "project 014: profile", "/profile.html"),
                    ("student-student1-22-my-clubs.png", "project 014: my clubs", "/my-clubs.html"),
                    ("student-student1-23-my-activities.png", "project 014: my activities", "/my-activities.html"),
                ]:
                    add_path(file_name, label, path)

                try:
                    page.goto(f"{base_url}/activity-detail.html?id=1", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    button = page.locator("button").filter(has_text=re.compile("签到|立即报名|取消报名"))
                    if button.count() > 0:
                        button.first.click(timeout=3000)
                        page.wait_for_timeout(600)
                        records.append(
                            capture_current_page(
                                page,
                                asset_dir / "student-student1-24-activity-action.png",
                                "project 014: activity action state",
                            )
                        )
                        close_dialog(page)
                except Exception:
                    pass

                try:
                    page.goto(f"{base_url}/topic-detail.html?id=1", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator("#commentContent").fill("这个话题很适合项目预览截图。")
                    page.wait_for_timeout(300)
                    records.append(
                        capture_current_page(
                            page,
                            asset_dir / "student-student1-25-topic-comment-form.png",
                            "project 014: topic comment form",
                        )
                    )
                except Exception:
                    pass

    if admin:
        login_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/admin/login",
            data={
                "username": str(admin.get("username") or ""),
                "password": str(admin.get("password") or ""),
            },
        )
        admin_data = unwrap_api_data(login_payload)
        if isinstance(admin_data, dict) and admin_data.get("token"):
            set_browser_login(
                page,
                base_url,
                str(admin_data["token"]),
                {
                    "username": admin.get("username") or "admin",
                    "role": "admin",
                },
            )
            add_path("admin-10-dashboard.png", "project 014: admin dashboard statistics", "/admin-dashboard.html")
            tab_specs = [
                ("users", "admin-11-users.png", "project 014: admin users"),
                ("clubs", "admin-12-club-audit.png", "project 014: admin club audit"),
                ("topics", "admin-13-topic-management.png", "project 014: admin topic management"),
            ]
            for tab, file_name, label in tab_specs:
                try:
                    page.goto(f"{base_url}/admin-dashboard.html", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator(f'[data-tab="{tab}"]').click(timeout=3000)
                    page.wait_for_timeout(1200)
                    records.append(capture_current_page(page, asset_dir / file_name, label))
                except Exception:
                    pass

    return records


def capture_project_015(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    user = find_account(accounts, username="20210001") or find_account(accounts, role="user")
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    if user:
        login_data = api_login_payload(page, base_url, str(user.get("username") or ""), str(user.get("password") or ""))
        if login_data:
            token = str(login_data.get("token") or "")
            user_payload = login_data.get("user") or login_data.get("userInfo")
            if token:
                set_browser_login(page, base_url, token, user_payload if isinstance(user_payload, dict) else None)
                for file_name, label, path in [
                    ("user-20210001-10-home.png", "project 015: confession wall feed", "/home.html"),
                    ("user-20210001-11-post-detail.png", "project 015: post detail with comments", "/post-detail.html?id=1"),
                    ("user-20210001-12-create-post.png", "project 015: create anonymous post", "/create-post.html"),
                    ("user-20210001-13-search.png", "project 015: search results", "/search.html?keyword=食堂"),
                    ("user-20210001-14-messages.png", "project 015: messages", "/messages.html"),
                    ("user-20210001-15-profile.png", "project 015: profile and points", "/profile.html"),
                    ("user-20210001-16-my-posts.png", "project 015: my posts", "/my-posts.html"),
                    ("user-20210001-17-my-comments.png", "project 015: my comments", "/my-comments.html"),
                    ("user-20210001-18-my-reports.png", "project 015: my reports", "/my-reports.html"),
                ]:
                    add_path(file_name, label, path)

                try:
                    page.goto(f"{base_url}/create-post.html", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator("#postTitle").fill("项目预览里的匿名心事")
                    page.locator("#postContent").fill("今天把校园表白墙的发帖、评论和个人中心都截图验证一遍。")
                    page.locator("#postTags").fill("预览,截图")
                    page.wait_for_timeout(300)
                    records.append(
                        capture_current_page(
                            page,
                            asset_dir / "user-20210001-19-create-post-filled.png",
                            "project 015: filled anonymous post form",
                        )
                    )
                except Exception:
                    pass

                try:
                    page.goto(f"{base_url}/profile.html", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator("a, button").filter(has_text="修改密码").first.click(timeout=3000)
                    page.wait_for_selector(".modal:visible", timeout=5000)
                    records.append(
                        capture_current_page(
                            page,
                            asset_dir / "user-20210001-20-password-dialog.png",
                            "project 015: change password dialog",
                        )
                    )
                    close_dialog(page)
                except Exception:
                    pass

                try:
                    page.goto(f"{base_url}/post-detail.html?id=1", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator("#commentInput").fill("这条评论用于精细截图验证。")
                    page.wait_for_timeout(300)
                    records.append(
                        capture_current_page(
                            page,
                            asset_dir / "user-20210001-21-comment-form.png",
                            "project 015: post comment form",
                        )
                    )
                except Exception:
                    pass

    if admin:
        login_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/auth/admin/login",
            data={
                "username": str(admin.get("username") or ""),
                "password": str(admin.get("password") or ""),
            },
        )
        admin_data = unwrap_api_data(login_payload)
        if isinstance(admin_data, dict) and admin_data.get("token"):
            admin_payload = admin_data.get("admin") or admin_data.get("adminInfo") or {}
            if not isinstance(admin_payload, dict):
                admin_payload = {}
            browser_admin = dict(admin_payload)
            browser_admin["isAdmin"] = True
            browser_admin.setdefault("username", admin.get("username") or "admin")
            set_browser_login(page, base_url, str(admin_data["token"]), browser_admin)
            add_path("admin-10-dashboard.png", "project 015: admin dashboard statistics", "/admin-dashboard.html")
            tab_specs = [
                ("loadUsers", "admin-11-users.png", "project 015: admin users"),
                ("loadPendingAuth", "admin-12-auth-audit.png", "project 015: admin auth audit"),
                ("loadPendingPosts", "admin-13-post-audit.png", "project 015: admin post audit"),
                ("loadReports", "admin-14-report-management.png", "project 015: admin reports"),
                ("loadSensitiveWords", "admin-15-sensitive-words.png", "project 015: admin sensitive words"),
            ]
            for function_name, file_name, label in tab_specs:
                try:
                    page.goto(f"{base_url}/admin-dashboard.html", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.evaluate(f"() => window.{function_name} && window.{function_name}()")
                    page.wait_for_timeout(1200)
                    records.append(capture_current_page(page, asset_dir / file_name, label))
                except Exception:
                    pass

    return records


def capture_project_016(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    user = find_account(accounts, username="user001") or find_account(accounts, username="20210001") or find_account(accounts, role="user")
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin")
    student_aliases = {
        "user001": "20210001",
        "user002": "20210002",
    }

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    if user:
        username = str(user.get("username") or "")
        login_username = student_aliases.get(username, username)
        login_data = api_login_payload(page, base_url, login_username, str(user.get("password") or ""))
        if login_data:
            token = str(login_data.get("token") or "")
            browser_user = {
                "id": login_data.get("userId") or login_data.get("id"),
                "userId": login_data.get("userId") or login_data.get("id"),
                "username": login_data.get("username") or username,
                "realName": login_data.get("realName") or "",
                "role": "user",
                "userType": "user",
                "isAdmin": False,
            }
            if token:
                set_browser_login(page, base_url, token, browser_user)
                for file_name, label, path in [
                    ("user-user001-10-home.png", "project 016: order square", "/home"),
                    ("user-user001-11-publish.png", "project 016: publish order", "/publish"),
                    ("user-user001-12-my-orders.png", "project 016: my orders", "/my-orders"),
                    ("user-user001-13-wallet.png", "project 016: wallet", "/wallet"),
                    ("user-user001-14-notifications.png", "project 016: notifications", "/notifications"),
                    ("user-user001-15-profile.png", "project 016: profile", "/profile"),
                ]:
                    add_path(file_name, label, path)

                try:
                    page.goto(f"{base_url}/publish", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator(".el-select").first.click(timeout=3000)
                    page.wait_for_timeout(400)
                    page.locator(".el-select-dropdown__item").filter(has_text="菜鸟驿站").first.click(timeout=3000)
                    page.locator("input[placeholder*='取件码']").fill("A-1024")
                    page.locator("input[placeholder*='请选择物品类型']").click(timeout=3000)
                    page.wait_for_timeout(300)
                    page.locator(".el-select-dropdown__item").filter(has_text="文件").first.click(timeout=3000)
                    page.locator("input[placeholder*='取件地址']").fill("菜鸟驿站东区店")
                    page.locator("input[placeholder='宿舍楼栋']").fill("东区1号楼")
                    page.locator("input[placeholder='房间号']").fill("101")
                    page.locator("input[placeholder*='联系电话']").fill("13800138001")
                    page.locator("textarea[placeholder*='备注']").fill("项目预览截图用的代领订单。")
                    page.wait_for_timeout(300)
                    records.append(
                        capture_current_page(
                            page,
                            asset_dir / "user-user001-16-publish-filled.png",
                            "project 016: filled publish order form",
                        )
                    )
                except Exception:
                    pass

                try:
                    page.goto(f"{base_url}/wallet", wait_until="domcontentloaded", timeout=15000)
                    wait_for_page_settle(page, timeout=10000)
                    page.locator("button").filter(has_text="充值").first.click(timeout=3000)
                    page.wait_for_selector(".el-dialog:visible", timeout=5000)
                    records.append(
                        capture_current_page(
                            page,
                            asset_dir / "user-user001-17-recharge-dialog.png",
                            "project 016: recharge dialog",
                        )
                    )
                    close_dialog(page)
                except Exception:
                    pass

    if admin:
        login_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/admin/login",
            data={
                "username": str(admin.get("username") or ""),
                "password": str(admin.get("password") or ""),
            },
        )
        admin_data = unwrap_api_data(login_payload)
        if isinstance(admin_data, dict) and admin_data.get("token"):
            browser_admin = {
                "id": admin_data.get("userId") or admin_data.get("id"),
                "userId": admin_data.get("userId") or admin_data.get("id"),
                "username": admin_data.get("username") or admin.get("username") or "admin",
                "realName": admin_data.get("realName") or "管理员",
                "role": "admin",
                "userType": "admin",
                "isAdmin": True,
            }
            set_browser_login(page, base_url, str(admin_data["token"]), browser_admin)
            for file_name, label, path in [
                ("admin-10-dashboard.png", "project 016: admin dashboard", "/admin/dashboard"),
                ("admin-11-users.png", "project 016: admin users", "/admin/users"),
                ("admin-12-orders.png", "project 016: admin orders", "/admin/orders"),
                ("admin-13-complaints.png", "project 016: admin complaints", "/admin/complaints"),
                ("admin-14-transactions.png", "project 016: admin transactions", "/admin/transactions"),
            ]:
                add_path(file_name, label, path)

    return records


def capture_project_017(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin") or {
        "username": "admin",
        "password": "admin123",
    }
    user = find_account(accounts, username="20210001") or {
        "username": "20210001",
        "password": "123456",
    }
    merchant = find_account(accounts, username="13900000001") or {
        "username": "13900000001",
        "password": "123456",
    }
    user_token = ""
    merchant_token = ""
    admin_token = ""
    created_order_id: int | None = None

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    def login_identity(account: dict[str, Any], identity: str) -> str:
        login_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/auth/{identity}/login",
            data={
                "username": str(account.get("username") or ""),
                "password": str(account.get("password") or ""),
            },
        )
        login_data = unwrap_api_data(login_payload)
        if not isinstance(login_data, dict) or not login_data.get("token"):
            return ""
        token = str(login_data["token"])
        browser_user = {
            "username": account.get("username") or identity,
            "role": identity,
            "userType": identity,
            "isAdmin": identity == "admin",
        }
        set_browser_login(page, base_url, token, browser_user)
        return token

    for file_name, label, path in [
        ("guest-10-index.png", "project 017: guest index", "/index"),
        ("guest-11-merchant-list.png", "project 017: guest merchant list", "/merchant/list"),
        ("guest-12-merchant-detail.png", "project 017: guest merchant detail", "/merchant/detail/1"),
        ("guest-13-user-login.png", "project 017: user login", "/user/login"),
        ("guest-14-user-register.png", "project 017: user register", "/user/register"),
        ("guest-15-merchant-login.png", "project 017: merchant login", "/merchant/login"),
        ("guest-16-admin-login.png", "project 017: admin login", "/admin/login"),
    ]:
        add_path(file_name, label, path)

    user_token = login_identity(user, "user")
    if user_token:
        api_request_json(
            page,
            "post",
            f"{base_url}/api/cart/add",
            token=user_token,
            data={"dishId": 1, "quantity": 2},
        )
        api_request_json(
            page,
            "post",
            f"{base_url}/api/cart/add",
            token=user_token,
            data={"dishId": 2, "quantity": 1},
        )
        for file_name, label, path in [
            ("user-20210001-10-merchant-list.png", "project 017: user merchant list", "/merchant/list"),
            ("user-20210001-11-merchant-detail.png", "project 017: user merchant detail", "/merchant/detail/1"),
            ("user-20210001-12-cart-with-items.png", "project 017: cart with seeded items", "/user/cart"),
        ]:
            add_path(file_name, label, path)

        order_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/orders",
            token=user_token,
            data={
                "merchantId": 1,
                "pickupType": 1,
                "pickupTime": None,
                "userRemark": "项目预览截图订单，少辣，堂食。",
            },
        )
        created_order = unwrap_api_data(order_payload)
        if isinstance(created_order, dict) and created_order.get("id"):
            created_order_id = int(created_order["id"])

        add_path("user-20210001-13-orders-pending.png", "project 017: user pending orders", "/user/orders")
        try:
            page.locator("button:visible").filter(has_text="查看详情").first.click(timeout=3000)
            page.wait_for_timeout(800)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "user-20210001-14-order-detail.png",
                    "project 017: user order detail",
                )
            )
        except Exception:
            pass

    merchant_token = login_identity(merchant, "merchant")
    if merchant_token:
        add_path("merchant-13900000001-10-manage-pending.png", "project 017: merchant manage pending order", "/merchant/manage")
        if created_order_id is not None:
            api_request_json(
                page,
                "post",
                f"{base_url}/api/merchant/orders/{created_order_id}/accept",
                token=merchant_token,
            )
            add_path(
                "merchant-13900000001-11-manage-accepted.png",
                "project 017: merchant manage accepted order",
                "/merchant/manage",
            )
            api_request_json(
                page,
                "post",
                f"{base_url}/api/merchant/orders/{created_order_id}/ready",
                token=merchant_token,
            )
            add_path(
                "merchant-13900000001-12-manage-ready.png",
                "project 017: merchant manage ready order",
                "/merchant/manage",
            )

    if user_token:
        set_browser_login(
            page,
            base_url,
            user_token,
            {
                "username": user.get("username") or "20210001",
                "role": "user",
                "userType": "user",
                "isAdmin": False,
            },
        )
        add_path("user-20210001-15-orders-ready.png", "project 017: user ready orders", "/user/orders")
        if created_order_id is not None:
            api_request_json(
                page,
                "post",
                f"{base_url}/api/orders/{created_order_id}/confirm",
                token=user_token,
            )
            add_path(
                "user-20210001-16-orders-completed.png",
                "project 017: user completed orders",
                "/user/orders",
            )

    admin_token = login_identity(admin, "admin")
    if admin_token:
        add_path("admin-10-dashboard.png", "project 017: admin dashboard statistics", "/admin/dashboard")

    return records


def capture_project_018(
    page,
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    stamp = time.strftime("%H%M%S")
    admin = find_account(accounts, role="admin") or find_account(accounts, username="admin") or {
        "username": "admin",
        "password": "123456",
    }
    student = find_account(accounts, role="student") or find_account(accounts, username="student1") or {
        "username": "student1",
        "password": "123456",
    }
    company = find_account(accounts, role="company") or find_account(accounts, username="company1") or {
        "username": "company1",
        "password": "123456",
    }
    pending_company_id: int | None = None
    job_id: int | None = None
    resume_id: int | None = None
    application_id: int | None = None
    experience_id: int | None = None

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    def login_user(account: dict[str, Any]) -> tuple[str, dict[str, Any]]:
        login_data = api_login_payload(page, base_url, str(account.get("username") or ""), str(account.get("password") or ""))
        if not isinstance(login_data, dict) or not login_data.get("token"):
            return "", {}
        token = str(login_data["token"])
        user_payload = login_data.get("user") if isinstance(login_data.get("user"), dict) else {}
        set_browser_login(page, base_url, token, user_payload)
        return token, dict(user_payload)

    def capture_button_dialog(path: str, button_text: str, file_name: str, label: str) -> None:
        try:
            page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button:visible").filter(has_text=button_text).first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible, [role='dialog']:visible", timeout=6000)
            records.append(capture_current_page(page, asset_dir / file_name, label))
            close_dialog(page)
        except Exception:
            pass

    for file_name, label, path in [
        ("guest-10-login.png", "project 018: guest login", "/login"),
        ("guest-11-register.png", "project 018: guest register", "/register"),
    ]:
        add_path(file_name, label, path)

    pending_register = api_request_json(
        page,
        "post",
        f"{base_url}/api/user/register",
        data={
            "username": f"preview_hr_{stamp}",
            "password": "123456",
            "realName": "待审核HR",
            "companyName": f"预览科技{stamp}",
            "email": f"preview{stamp}@example.com",
            "phone": f"139{stamp[-8:].rjust(8, '0')}",
            "role": "company",
        },
    )
    pending_user = unwrap_api_data(pending_register)
    if isinstance(pending_user, dict) and pending_user.get("companyId"):
        pending_company_id = int(pending_user["companyId"])

    company_token, company_user = login_user(company)
    if company_token:
        job_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/job/publish",
            token=company_token,
            data={
                "title": f"项目预览产品实习生{stamp}",
                "jobType": "internship",
                "category": "产品运营",
                "location": "北京",
                "salaryMin": 180,
                "salaryMax": 260,
                "requirement": "熟悉校园招聘流程，具备沟通和数据分析能力。",
                "description": "负责实习岗位运营、候选人沟通和项目数据复盘。",
                "major": "软件工程",
                "skills": "Vue,Spring Boot,数据分析",
                "education": "本科",
                "headcount": 3,
            },
        )
        job = unwrap_api_data(job_payload)
        if isinstance(job, dict) and job.get("id"):
            job_id = int(job["id"])

    student_token, student_user = login_user(student)
    if student_token:
        resume_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/resume/create",
            token=student_token,
            data={
                "name": "张三",
                "gender": "男",
                "birthDate": "2002-09-01",
                "phone": "13800000001",
                "email": "student1@example.com",
                "university": "示例大学",
                "major": "软件工程",
                "education": "本科",
                "graduationDate": "2027-06-30",
                "skills": "Vue,Spring Boot,MySQL",
                "internshipExperience": "参与校园项目管理系统前端开发。",
                "projectExperience": "负责毕业设计项目的招聘平台模块联调。",
                "selfIntroduction": "沟通积极，关注业务闭环和用户体验。",
            },
        )
        resume = unwrap_api_data(resume_payload)
        if isinstance(resume, dict) and resume.get("id"):
            resume_id = int(resume["id"])

        if job_id and resume_id:
            set_browser_login(page, base_url, student_token, student_user)
            add_path("student-student1-10-jobs.png", "project 018: student job list", "/student/jobs")
            add_path("student-student1-11-job-detail.png", "project 018: student job detail", f"/student/job/{job_id}")
            capture_button_dialog(
                f"/student/job/{job_id}",
                "投递简历",
                "student-student1-12-apply-dialog.png",
                "project 018: student apply dialog",
            )
            application_payload = api_request_json(
                page,
                "post",
                f"{base_url}/api/application/submit",
                token=student_token,
                data={"jobId": job_id, "resumeId": resume_id},
            )
            application = unwrap_api_data(application_payload)
            if isinstance(application, dict) and application.get("id"):
                application_id = int(application["id"])

        experience_payload = api_request_json(
            page,
            "post",
            f"{base_url}/api/experience/publish",
            token=student_token,
            data={
                "companyName": "字节跳动",
                "jobTitle": "产品运营实习生",
                "type": "interview",
                "title": f"项目预览面经{stamp}",
                "content": "一面重点问项目经历、实习动机和数据分析方法。",
                "tags": "面经,产品,实习",
            },
        )
        experience = unwrap_api_data(experience_payload)
        if isinstance(experience, dict) and experience.get("id"):
            experience_id = int(experience["id"])

        api_request_json(
            page,
            "post",
            f"{base_url}/api/referral/publish",
            token=student_token,
            data={
                "companyName": "字节跳动",
                "jobTitle": "产品运营实习生",
                "description": "校友可帮忙内推产品运营和前端实习岗位。",
                "requirement": "本科在读，有项目经历优先。",
                "referralCode": f"REF{stamp}",
                "contactWay": "preview@example.com",
            },
        )

    if company_token and application_id:
        api_request_json(
            page,
            "post",
            f"{base_url}/api/interview/create",
            token=company_token,
            data={
                "applicationId": application_id,
                "interviewType": "online",
                "interviewTime": "2026-06-18 10:00:00",
                "location": "腾讯会议：123-456-789",
                "interviewer": "李经理",
            },
        )

    if student_token:
        set_browser_login(page, base_url, student_token, student_user)
        for file_name, label, path in [
            ("student-student1-13-resume.png", "project 018: student resume", "/student/resume"),
            ("student-student1-14-applications.png", "project 018: student applications", "/student/applications"),
            ("student-student1-15-interviews.png", "project 018: student interviews", "/student/interviews"),
            ("student-student1-16-experiences.png", "project 018: student experiences", "/student/experiences"),
            ("student-student1-18-referrals.png", "project 018: student referrals", "/student/referrals"),
            ("student-student1-20-profile.png", "project 018: student profile", "/student/profile"),
        ]:
            add_path(file_name, label, path)
        capture_button_dialog(
            "/student/experiences",
            "发布经验",
            "student-student1-17-experience-dialog.png",
            "project 018: publish experience dialog",
        )
        capture_button_dialog(
            "/student/referrals",
            "发布内推",
            "student-student1-19-referral-dialog.png",
            "project 018: publish referral dialog",
        )
        if experience_id:
            add_path(
                "student-student1-21-experience-detail.png",
                "project 018: experience detail",
                f"/student/experience/{experience_id}",
            )

    if company_token:
        set_browser_login(page, base_url, company_token, company_user)
        for file_name, label, path in [
            ("company-company1-10-jobs.png", "project 018: company job management", "/company/jobs"),
            ("company-company1-12-applications.png", "project 018: company received resumes", "/company/applications"),
            ("company-company1-16-interviews.png", "project 018: company interviews", "/company/interviews"),
            ("company-company1-18-company-info.png", "project 018: company profile", "/company/company-info"),
            ("company-company1-19-profile.png", "project 018: company user profile", "/company/profile"),
        ]:
            add_path(file_name, label, path)
        capture_button_dialog(
            "/company/jobs",
            "发布岗位",
            "company-company1-11-job-dialog.png",
            "project 018: company publish job dialog",
        )
        for button_text, file_name, label in [
            ("查看简历", "company-company1-13-resume-dialog.png", "project 018: company resume detail dialog"),
            ("更新状态", "company-company1-14-status-dialog.png", "project 018: company application status dialog"),
            ("安排面试", "company-company1-15-interview-dialog.png", "project 018: company arrange interview dialog"),
        ]:
            capture_button_dialog("/company/applications", button_text, file_name, label)
        capture_button_dialog(
            "/company/interviews",
            "编辑反馈",
            "company-company1-17-feedback-dialog.png",
            "project 018: company feedback dialog",
        )

    admin_token, admin_user = login_user(admin)
    if admin_token:
        set_browser_login(page, base_url, admin_token, admin_user)
        add_path("admin-10-dashboard-pending.png", "project 018: admin pending company dashboard", "/admin/dashboard")
        if pending_company_id is not None:
            api_request_json(
                page,
                "put",
                f"{base_url}/api/admin/company/review",
                token=admin_token,
                data={"id": pending_company_id, "status": 1},
            )
            add_path("admin-11-dashboard-reviewed.png", "project 018: admin reviewed company dashboard", "/admin/dashboard")

    return records


def capture_project_019(
    page,
    runtime: dict[str, Any],
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    stamp = time.strftime("%H%M%S")
    today = date.today()
    tomorrow = today + timedelta(days=1)
    next_week = today + timedelta(days=7)
    student = find_account(accounts, username="student1") or find_account(accounts, role="student") or {
        "username": "student1",
        "password": "student123",
        "role_key": "student",
        "role_label": "学生",
    }
    coach = find_account(accounts, username="coach1") or find_account(accounts, role="coach")
    admin = find_account(accounts, username="admin") or find_account(accounts, role="admin")

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    def login_for_capture(account: dict[str, Any]) -> tuple[str, dict[str, Any]]:
        username = str(account.get("username") or "")
        password = str(account.get("password") or "")
        if not username or not password:
            return "", {}
        login_data = api_login_payload(page, base_url, username, password)
        if not login_data and patch_preview_password(runtime, username, password):
            login_data = api_login_payload(page, base_url, username, password)
        if not isinstance(login_data, dict) or not login_data.get("token"):
            return "", {}
        token = str(login_data["token"])
        user_payload = login_data.get("user") if isinstance(login_data.get("user"), dict) else {}
        set_browser_login(page, base_url, token, user_payload)
        return token, dict(user_payload)

    def open_button_dialog(path: str, button_text: str, file_name: str, label: str) -> None:
        try:
            page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button:visible").filter(has_text=button_text).first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible, [role='dialog']:visible", timeout=6000)
            records.append(capture_current_page(page, asset_dir / file_name, label))
            close_dialog(page)
        except Exception:
            pass

    for file_name, label, path in [
        ("guest-10-login.png", "project 019: guest login", "/login"),
        ("guest-11-register.png", "project 019: guest register", "/register"),
    ]:
        add_path(file_name, label, path)

    student_token, student_user = login_for_capture(student)
    if not student_token:
        return records

    api_request_json(
        page,
        "post",
        f"{base_url}/api/sport/record",
        token=student_token,
        data={
            "sportType": "running",
            "duration": 46,
            "distance": 6.2,
            "calories": 420,
            "steps": 7800,
            "remark": f"项目预览晨跑打卡 {stamp}",
            "sportDate": today.isoformat(),
        },
    )
    api_request_json(
        page,
        "post",
        f"{base_url}/api/sport/record",
        token=student_token,
        data={
            "sportType": "basketball",
            "duration": 68,
            "distance": 0,
            "calories": 560,
            "steps": 5200,
            "remark": "班级篮球训练，完成投篮和折返跑。",
            "sportDate": (today - timedelta(days=1)).isoformat(),
        },
    )
    api_request_json(
        page,
        "post",
        f"{base_url}/api/plan/create",
        token=student_token,
        data={
            "planName": f"精修减脂塑形计划 {stamp}",
            "planType": "lose_weight",
            "targetDesc": "每周三次有氧，两次力量训练，保持饮食记录。",
            "durationDays": 30,
            "planContent": "周一跑步，周三力量，周五篮球，周末拉伸恢复。",
            "startDate": today.isoformat(),
            "endDate": (today + timedelta(days=29)).isoformat(),
        },
    )
    plan_payload = api_request_json(
        page,
        "get",
        f"{base_url}/api/plan/list?page=1&size=5",
        token=student_token,
    )
    plan_records = unwrap_api_data(plan_payload)
    plan_id = None
    if isinstance(plan_records, dict) and isinstance(plan_records.get("records"), list) and plan_records["records"]:
        plan_id = plan_records["records"][0].get("id")
    if plan_id:
        api_request_json(
            page,
            "put",
            f"{base_url}/api/plan/{plan_id}/progress",
            token=student_token,
            data={"completedDays": 7},
        )
    for offset, weight, body_fat in [(0, 65.2, 18.6), (3, 65.8, 19.1), (6, 66.1, 19.4)]:
        api_request_json(
            page,
            "post",
            f"{base_url}/api/health/record",
            token=student_token,
            data={
                "recordDate": (today - timedelta(days=offset)).isoformat(),
                "weight": weight,
                "bodyFat": body_fat,
                "muscleMass": 49.5 + offset / 10,
                "waterIntake": 2200,
                "sleepHours": 7.5,
                "dietCalories": 2100,
                "dietRecord": "早餐燕麦，午餐鸡胸肉和米饭，晚餐蔬菜沙拉。",
            },
        )
    api_request_json(
        page,
        "post",
        f"{base_url}/api/activity/create",
        token=student_token,
        data={
            "activityName": f"周末羽毛球双打 {stamp}",
            "sportType": "badminton",
            "activityTime": f"{next_week.isoformat()} 19:30:00",
            "maxParticipants": 8,
            "levelRequirement": "intermediate",
            "description": "面向校园同学的双打约球活动，自带球拍，现场分组。",
        },
    )
    api_request_json(
        page,
        "post",
        f"{base_url}/api/booking/create",
        token=student_token,
        data={
            "venueId": 1,
            "bookingDate": tomorrow.isoformat(),
            "startTime": "19:00:00",
            "endTime": "20:30:00",
            "companionCount": 3,
        },
    )

    set_browser_login(page, base_url, student_token, student_user)
    for file_name, label, path in [
        ("student-student1-10-home-seeded.png", "project 019: student dashboard with seeded stats", "/home"),
        ("student-student1-11-sport-records.png", "project 019: sport records with points", "/sport/record"),
        ("student-student1-12-create-record.png", "project 019: create sport record form", "/sport/create"),
        ("student-student1-13-plans.png", "project 019: fitness plans with progress", "/plan/list"),
        ("student-student1-15-create-plan.png", "project 019: create plan form", "/plan/create"),
        ("student-student1-16-activities.png", "project 019: activity list", "/activity/list"),
        ("student-student1-17-create-activity.png", "project 019: create activity form", "/activity/create"),
        ("student-student1-18-health-profile.png", "project 019: health profile charts and records", "/health/profile"),
        ("student-student1-19-venues.png", "project 019: venue list", "/venue/list"),
        ("student-student1-21-bookings.png", "project 019: my bookings", "/venue/booking"),
        ("student-student1-23-rank.png", "project 019: points rank", "/rank"),
        ("student-student1-24-profile.png", "project 019: profile", "/profile"),
    ]:
        add_path(file_name, label, path)

    open_button_dialog(
        "/plan/list",
        "更新进度",
        "student-student1-14-plan-progress-dialog.png",
        "project 019: plan progress dialog",
    )
    open_button_dialog(
        "/venue/list",
        "立即预约",
        "student-student1-20-venue-booking-dialog.png",
        "project 019: venue booking dialog",
    )
    open_button_dialog(
        "/venue/booking",
        "取消",
        "student-student1-22-booking-cancel-dialog.png",
        "project 019: booking cancellation dialog",
    )

    for account, prefix in [(coach, "coach-coach1"), (admin, "admin-admin")]:
        if not account:
            continue
        token, user_payload = login_for_capture(account)
        if not token:
            continue
        set_browser_login(page, base_url, token, user_payload)
        add_path(f"{prefix}-10-home.png", f"project 019: {prefix} dashboard", "/home")
        add_path(f"{prefix}-11-rank.png", f"project 019: {prefix} rank", "/rank")

    return records


def capture_project_020(
    page,
    runtime: dict[str, Any],
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    stamp = time.strftime("%H%M%S")
    student = find_account(accounts, username="student1") or find_account(accounts, role="student") or {
        "username": "student1",
        "password": "123456",
    }
    teacher = find_account(accounts, username="teacher1") or find_account(accounts, role="teacher") or {
        "username": "teacher1",
        "password": "123456",
    }
    admin = find_account(accounts, username="admin") or find_account(accounts, role="admin")

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    def login_for_capture(account: dict[str, Any]) -> tuple[str, dict[str, Any]]:
        username = str(account.get("username") or "")
        password = str(account.get("password") or "")
        if not username or not password:
            return "", {}
        login_data = api_login_payload(page, base_url, username, password)
        if not login_data and patch_preview_password(runtime, username, password):
            login_data = api_login_payload(page, base_url, username, password)
        if not isinstance(login_data, dict) or not login_data.get("token"):
            return "", {}
        token = str(login_data["token"])
        user_payload = login_data.get("user") if isinstance(login_data.get("user"), dict) else {}
        set_browser_login(page, base_url, token, user_payload)
        return token, dict(user_payload)

    for file_name, label, path in [
        ("guest-10-login.png", "project 020: guest login", "/login"),
        ("guest-11-register.png", "project 020: guest register", "/register"),
    ]:
        add_path(file_name, label, path)

    student_token, student_user = login_for_capture(student)
    if not student_token:
        return records

    api_request_json(
        page,
        "post",
        f"{base_url}/api/resource/upload",
        token=student_token,
        data={
            "title": f"精修学习资源包 {stamp}",
            "description": "用于项目预览截图的课程资料，包含课堂讲义、练习题和复习清单。",
            "category": "编程",
            "fileUrl": "/files/preview-resource.pdf",
            "fileName": f"preview-resource-{stamp}.pdf",
            "fileSize": 1536000,
            "fileType": "pdf",
            "points": 8,
        },
    )
    resource_id = first_record_id(
        api_request_json(page, "get", f"{base_url}/api/resource/list?page=1&size=5", token=student_token)
    ) or 1
    api_request_json(
        page,
        "post",
        f"{base_url}/api/resource/rate",
        token=student_token,
        data={
            "resourceId": resource_id,
            "rating": 5,
            "comment": f"资料结构清楚，适合复习和课堂演示 {stamp}",
        },
    )
    api_request_json(page, "post", f"{base_url}/api/resource/download/{resource_id}", token=student_token)

    api_request_json(
        page,
        "post",
        f"{base_url}/api/group/create",
        token=student_token,
        data={
            "name": f"精修项目学习小组 {stamp}",
            "description": "围绕毕业设计截图复核、接口联调和资料整理的小组。",
            "category": "编程",
            "maxMembers": 20,
        },
    )
    group_id = first_record_id(
        api_request_json(page, "get", f"{base_url}/api/group/list?page=1&size=5", token=student_token)
    ) or 1

    api_request_json(
        page,
        "post",
        f"{base_url}/api/question/add",
        token=student_token,
        data={
            "subject": "编程",
            "difficulty": "简单",
            "type": "选择题",
            "content": f"精修题：Vue 组件通信常用方式是哪一个？ {stamp}",
            "options": "A. props|B. cookie|C. DNS|D. BIOS",
            "answer": "A",
            "analysis": "父子组件通信常用 props 和 emit。",
        },
    )
    question_id = first_record_id(
        api_request_json(page, "get", f"{base_url}/api/question/list?page=1&size=5", token=student_token)
    ) or 1
    api_request_json(
        page,
        "post",
        f"{base_url}/api/question/wrong/add",
        token=student_token,
        data={"questionId": question_id, "userAnswer": "B"},
    )

    api_request_json(
        page,
        "post",
        f"{base_url}/api/qa/ask",
        token=student_token,
        data={
            "title": f"精修提问：资源评价如何影响积分 {stamp}",
            "content": "想确认资源下载、评价、回答和采纳之间的积分变化规则，方便做课程资料运营。",
            "category": "编程",
            "bounty": 20,
        },
    )
    qa_id = first_record_id(
        api_request_json(page, "get", f"{base_url}/api/qa/list?page=1&size=5", token=student_token)
    ) or 1

    api_request_json(
        page,
        "post",
        f"{base_url}/api/note/create",
        token=student_token,
        data={
            "title": f"精修学习笔记 {stamp}",
            "content": "# 精修学习笔记\n\n- 资源下载需要积分\n- 题库练习会沉淀错题\n- 问答采纳可以完成知识闭环",
            "category": "编程",
            "tags": "Vue,Spring Boot,截图复核",
            "isPublic": 1,
        },
    )
    note_id = first_record_id(
        api_request_json(page, "get", f"{base_url}/api/note/list?page=1&size=5&isPublic=1", token=student_token)
    ) or 1

    teacher_token, teacher_user = login_for_capture(teacher)
    if teacher_token:
        api_request_json(
            page,
            "post",
            f"{base_url}/api/qa/answer",
            token=teacher_token,
            data={
                "questionAnswerId": qa_id,
                "content": "资源下载会扣除下载者积分并奖励上传者，评价会更新资源评分；问答采纳会把悬赏发给回答者。",
            },
        )

    if student_token:
        set_browser_login(page, base_url, student_token, student_user)
        for file_name, label, path in [
            ("student-student1-10-home.png", "project 020: student dashboard", "/home"),
            ("student-student1-11-resources.png", "project 020: resource list", "/resource"),
            ("student-student1-12-resource-detail.png", "project 020: resource detail with rating", f"/resource/{resource_id}"),
            ("student-student1-13-resource-upload.png", "project 020: resource upload form", "/resource/upload"),
            ("student-student1-14-groups.png", "project 020: group list", "/group"),
            ("student-student1-16-group-detail.png", "project 020: group detail and members", f"/group/{group_id}"),
            ("student-student1-17-question-list.png", "project 020: question bank", "/question"),
            ("student-student1-19-question-practice.png", "project 020: question practice", "/question/practice"),
            ("student-student1-20-wrong-questions.png", "project 020: wrong question list", "/question/wrong"),
            ("student-student1-21-qa-list.png", "project 020: Q&A list", "/qa"),
            ("student-student1-22-qa-detail.png", "project 020: Q&A detail with answer", f"/qa/{qa_id}"),
            ("student-student1-23-qa-ask.png", "project 020: ask question form", "/qa/ask"),
            ("student-student1-24-notes.png", "project 020: note list", "/note"),
            ("student-student1-25-note-detail.png", "project 020: note detail", f"/note/{note_id}"),
            ("student-student1-26-note-edit.png", "project 020: note edit form", "/note/edit"),
            ("student-student1-27-profile.png", "project 020: profile", "/profile"),
        ]:
            add_path(file_name, label, path)

        try:
            page.goto(f"{base_url}/group", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button:visible").filter(has_text="创建小组").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible, [role='dialog']:visible", timeout=6000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "student-student1-15-group-create-dialog.png",
                    "project 020: group create dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

        try:
            page.goto(f"{base_url}/question", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            page.locator("button:visible").filter(has_text="查看").first.click(timeout=4000)
            page.wait_for_selector(".el-dialog:visible, [role='dialog']:visible", timeout=6000)
            records.append(
                capture_current_page(
                    page,
                    asset_dir / "student-student1-18-question-detail-dialog.png",
                    "project 020: question detail dialog",
                )
            )
            close_dialog(page)
        except Exception:
            pass

    if teacher_token:
        set_browser_login(page, base_url, teacher_token, teacher_user)
        add_path("teacher-teacher1-10-home.png", "project 020: teacher dashboard", "/home")
        add_path("teacher-teacher1-11-qa-detail.png", "project 020: teacher Q&A detail", f"/qa/{qa_id}")

    if admin:
        admin_token, admin_user = login_for_capture(admin)
        if admin_token:
            set_browser_login(page, base_url, admin_token, admin_user)
            add_path("admin-admin-10-home.png", "project 020: admin dashboard", "/home")
            add_path("admin-admin-11-profile.png", "project 020: admin profile", "/profile")

    return records


def capture_project_021(
    page,
    runtime: dict[str, Any],
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    suffix = f"{int(time.time() * 1000) % 100000000:08d}"
    display_suffix = suffix[-4:]
    password = "123456"

    seller_username = f"preview_seller_{suffix}"
    buyer_username = f"preview_buyer_{suffix}"
    seller_profile = {
        "username": seller_username,
        "password": password,
        "studentId": f"21S{suffix}",
        "realName": "预览卖家",
        "college": "信息工程学院",
        "dorm": "明德楼 3-216",
        "phone": f"139{suffix}",
    }
    buyer_profile = {
        "username": buyer_username,
        "password": password,
        "studentId": f"21B{suffix}",
        "realName": "预览买家",
        "college": "经济管理学院",
        "dorm": "致远楼 5-418",
        "phone": f"138{suffix}",
    }

    def success_data(payload: dict[str, Any] | None) -> Any:
        if not isinstance(payload, dict):
            return None
        if str(payload.get("code")) not in {"0", "200"}:
            return None
        return unwrap_api_data(payload)

    def login_for_capture(username: str, password_value: str) -> tuple[str, dict[str, Any]]:
        login_data = api_login_payload_retry(page, base_url, username, password_value)
        if not isinstance(login_data, dict):
            return "", {}
        token = str(login_data.get("token") or login_data.get("accessToken") or login_data.get("jwt") or "")
        user_payload = login_user_from_payload(login_data) or {}
        if token:
            info = success_data(api_request_json_retry(page, "get", f"{base_url}/api/user/info", token=token))
            if isinstance(info, dict):
                user_payload.update(info)
                if "id" not in user_payload and user_payload.get("userId") is not None:
                    user_payload["id"] = user_payload["userId"]
                if "userId" not in user_payload and user_payload.get("id") is not None:
                    user_payload["userId"] = user_payload["id"]
        return token, user_payload

    def add_path(file_name: str, label: str, path: str) -> None:
        record = capture_path(page, base_url, asset_dir, file_name, label, path)
        if record:
            records.append(record)

    def add_dialog(file_name: str, label: str, path: str, button_text: str, enabled_only: bool = False) -> None:
        try:
            page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=10000)
            selector = "button:visible:not([disabled])" if enabled_only else "button:visible"
            page.locator(selector).filter(has_text=button_text).first.click(timeout=5000)
            page.wait_for_selector(".el-dialog:visible, [role='dialog']:visible", timeout=6000)
            records.append(capture_current_page(page, asset_dir / file_name, label))
            close_dialog(page)
        except Exception:
            pass

    def first_category_id(categories: list[dict[str, Any]], keyword: str, fallback: int) -> int:
        for category in categories:
            name = str(category.get("categoryName") or category.get("name") or "")
            if keyword in name and category.get("id") is not None:
                return int(category["id"])
        for category in categories:
            if category.get("id") is not None:
                return int(category["id"])
        return fallback

    def publish_product(token: str, payload: dict[str, Any]) -> int | None:
        product_id = success_data(
            api_request_json_retry(page, "post", f"{base_url}/api/product/publish", token=token, data=payload)
        )
        if product_id is not None:
            return int(product_id)
        list_payload = api_request_json_retry(
            page,
            "get",
            f"{base_url}/api/product/my?current=1&size=20",
            token=token,
        )
        data = success_data(list_payload)
        if isinstance(data, dict):
            for product in data.get("records", []):
                if isinstance(product, dict) and product.get("title") == payload["title"] and product.get("id") is not None:
                    return int(product["id"])
        return None

    category_payload = api_request_json_retry(page, "get", f"{base_url}/api/category/list", attempts=10, delay_ms=1000)
    categories = success_data(category_payload)
    category_list = categories if isinstance(categories, list) else []
    if not category_list:
        return records

    seller_register = api_request_json_retry(page, "post", f"{base_url}/api/user/register", data=seller_profile)
    buyer_register = api_request_json_retry(page, "post", f"{base_url}/api/user/register", data=buyer_profile)
    if success_data(seller_register) is None or success_data(buyer_register) is None:
        return records
    seller_token, seller_user = login_for_capture(seller_username, password)
    buyer_token, buyer_user = login_for_capture(buyer_username, password)
    if not seller_token or not buyer_token:
        return records

    seller_id = int(seller_user.get("id") or seller_user.get("userId") or 0)
    buyer_id = int(buyer_user.get("id") or buyer_user.get("userId") or 0)
    if not seller_id or not buyer_id:
        return records

    book_category = first_category_id(category_list, "教材", 1)
    electronic_category = first_category_id(category_list, "数码", book_category)
    daily_category = first_category_id(category_list, "生活", book_category)

    showcase_id = publish_product(
        seller_token,
        {
            "categoryId": book_category,
            "title": f"精修流转高数教材 {display_suffix}",
            "description": "第七版高等数学教材，课堂笔记完整，适合期末复习和二次流转演示。",
            "price": "38.00",
            "originalPrice": "69.00",
            "condition": "九成新",
            "images": [f"https://placehold.co/720x520/fef3c7/7c2d12.png?text=Book+{display_suffix}"],
        },
    )
    pending_product_id = publish_product(
        seller_token,
        {
            "categoryId": electronic_category,
            "title": f"精修议价蓝牙耳机 {display_suffix}",
            "description": "降噪耳机功能正常，买家发起议价后保留待处理状态，用于展示消息中心。",
            "price": "128.00",
            "originalPrice": "299.00",
            "condition": "八成新",
            "images": [f"https://placehold.co/720x520/dbeafe/1e3a8a.png?text=Headset+{display_suffix}"],
        },
    )
    accepted_product_id = publish_product(
        seller_token,
        {
            "categoryId": electronic_category,
            "title": f"精修成交摄影脚架 {display_suffix}",
            "description": "轻量三脚架，议价后生成订单并完成交易，用于展示买入和卖出订单状态。",
            "price": "86.00",
            "originalPrice": "169.00",
            "condition": "九成新",
            "images": [f"https://placehold.co/720x520/e0f2fe/075985.png?text=Tripod+{display_suffix}"],
        },
    )
    direct_product_id = publish_product(
        seller_token,
        {
            "categoryId": daily_category,
            "title": f"精修待完成宿舍台灯 {display_suffix}",
            "description": "可调光宿舍台灯，买家直接下单后保持待完成状态，用于展示订单操作按钮。",
            "price": "45.00",
            "originalPrice": "89.00",
            "condition": "七成新",
            "images": [f"https://placehold.co/720x520/fce7f3/831843.png?text=Desk+Lamp+{display_suffix}"],
        },
    )

    product_ids = [showcase_id, pending_product_id, accepted_product_id, direct_product_id]
    if any(product_id is None for product_id in product_ids):
        return records
    showcase_id = int(showcase_id)
    pending_product_id = int(pending_product_id)
    accepted_product_id = int(accepted_product_id)
    direct_product_id = int(direct_product_id)

    api_request_json_retry(
        page,
        "post",
        f"{base_url}/api/favorite/add",
        token=buyer_token,
        data={"productId": showcase_id},
    )
    api_request_json_retry(
        page,
        "post",
        f"{base_url}/api/chat/send",
        token=buyer_token,
        data={
            "productId": showcase_id,
            "receiverId": seller_id,
            "content": "你好，这本教材还在吗？今晚可以在图书馆门口交易。",
        },
    )
    api_request_json_retry(
        page,
        "post",
        f"{base_url}/api/chat/bargain",
        token=buyer_token,
        data={
            "productId": pending_product_id,
            "receiverId": seller_id,
            "bargainPrice": "98.00",
            "content": "耳机可以 98 元成交吗？我可以今天自提。",
        },
    )
    accepted_bargain_id = success_data(
        api_request_json_retry(
            page,
            "post",
            f"{base_url}/api/chat/bargain",
            token=buyer_token,
            data={
                "productId": accepted_product_id,
                "receiverId": seller_id,
                "bargainPrice": "72.00",
                "content": "脚架 72 元可以吗？我想配合相机社团练习使用。",
            },
        )
    )
    if accepted_bargain_id is not None:
        api_request_json_retry(
            page,
            "put",
            f"{base_url}/api/chat/bargain/{int(accepted_bargain_id)}/accept",
            token=seller_token,
        )
    direct_order_id = success_data(
        api_request_json_retry(
            page,
            "post",
            f"{base_url}/api/order/create",
            token=buyer_token,
            data={"productId": direct_product_id},
        )
    )

    buy_orders = success_data(
        api_request_json_retry(page, "get", f"{base_url}/api/order/my/buy?current=1&size=20", token=buyer_token)
    )
    accepted_order_id = None
    if isinstance(buy_orders, dict):
        for order in buy_orders.get("records", []):
            if isinstance(order, dict) and int(order.get("productId") or 0) == accepted_product_id:
                accepted_order_id = int(order["id"])
                break
    if accepted_order_id is not None:
        api_request_json_retry(page, "put", f"{base_url}/api/order/{accepted_order_id}/complete", token=buyer_token)

    for file_name, label, path in [
        ("guest-10-login.png", "project 021: guest login", "/login"),
        ("guest-11-register.png", "project 021: guest register", "/register"),
        ("guest-12-home-seeded.png", "project 021: guest home with seeded products", "/"),
    ]:
        add_path(file_name, label, path)

    set_browser_login(page, base_url, buyer_token, buyer_user)
    for file_name, label, path in [
        ("buyer-10-home-seeded.png", "project 021: buyer home with products", "/"),
        ("buyer-11-product-detail-favorited.png", "project 021: buyer product detail favorited", f"/product/{showcase_id}"),
        ("buyer-12-favorites.png", "project 021: buyer favorites", "/favorites"),
        ("buyer-13-chat-list.png", "project 021: buyer chat list", "/chat"),
        ("buyer-14-chat-detail-pending.png", "project 021: buyer pending bargain chat", f"/chat/{seller_id}/{pending_product_id}"),
        ("buyer-15-chat-detail-accepted.png", "project 021: buyer accepted bargain chat", f"/chat/{seller_id}/{accepted_product_id}"),
        ("buyer-16-profile.png", "project 021: buyer profile", "/profile"),
        ("buyer-17-profile-buy-orders.png", "project 021: buyer buy orders", "/profile?tab=buy"),
        ("buyer-18-public-seller-profile.png", "project 021: buyer public seller profile", f"/user/{seller_id}"),
        ("buyer-19-publish-form.png", "project 021: buyer publish form", "/publish"),
    ]:
        add_path(file_name, label, path)
    add_dialog(
        "buyer-20-bargain-dialog.png",
        "project 021: buyer bargain dialog",
        f"/product/{showcase_id}",
        "发起议价",
    )
    add_dialog(
        "buyer-21-rate-dialog.png",
        "project 021: buyer order rating dialog",
        "/profile?tab=buy",
        "评价卖家",
        enabled_only=True,
    )

    set_browser_login(page, base_url, seller_token, seller_user)
    for file_name, label, path in [
        ("seller-10-home.png", "project 021: seller home", "/"),
        ("seller-11-my-products.png", "project 021: seller product management", "/my-products"),
        ("seller-12-product-detail-self.png", "project 021: seller self product detail", f"/product/{showcase_id}"),
        ("seller-13-chat-list.png", "project 021: seller chat list", "/chat"),
        ("seller-14-chat-detail-pending.png", "project 021: seller pending bargain chat", f"/chat/{buyer_id}/{pending_product_id}"),
        ("seller-15-chat-detail-accepted.png", "project 021: seller accepted bargain chat", f"/chat/{buyer_id}/{accepted_product_id}"),
        ("seller-16-profile-sell-orders.png", "project 021: seller sell orders", "/profile?tab=sell"),
        ("seller-17-publish-form.png", "project 021: seller publish form", "/publish"),
        ("seller-18-edit-product-form.png", "project 021: seller edit product form", f"/publish?edit={showcase_id}"),
    ]:
        add_path(file_name, label, path)

    if direct_order_id is not None:
        api_request_json_retry(
            page,
            "get",
            f"{base_url}/api/order/{int(direct_order_id)}",
            token=seller_token,
        )

    return records


def capture_project_specific_pages(
    project_id: str,
    page,
    runtime: dict[str, Any],
    base_url: str,
    accounts: list[dict[str, Any]],
    asset_dir: Path,
) -> list[ShotRecord]:
    if project_id == "005":
        return capture_project_005(page, base_url, accounts, asset_dir)
    if project_id == "006":
        return capture_project_006(page, base_url, accounts, asset_dir)
    if project_id == "008":
        return capture_project_008(page, base_url, accounts, asset_dir)
    if project_id == "010":
        return capture_project_010(page, base_url, accounts, asset_dir)
    if project_id == "011":
        return capture_project_011(page, runtime, base_url, accounts, asset_dir)
    if project_id == "012":
        return capture_project_012(page, base_url, accounts, asset_dir)
    if project_id == "013":
        return capture_project_013(page, base_url, accounts, asset_dir)
    if project_id == "014":
        return capture_project_014(page, base_url, accounts, asset_dir)
    if project_id == "015":
        return capture_project_015(page, base_url, accounts, asset_dir)
    if project_id == "016":
        return capture_project_016(page, base_url, accounts, asset_dir)
    if project_id == "017":
        return capture_project_017(page, base_url, accounts, asset_dir)
    if project_id == "018":
        return capture_project_018(page, base_url, accounts, asset_dir)
    if project_id == "019":
        return capture_project_019(page, runtime, base_url, accounts, asset_dir)
    if project_id == "020":
        return capture_project_020(page, runtime, base_url, accounts, asset_dir)
    if project_id == "021":
        return capture_project_021(page, runtime, base_url, accounts, asset_dir)
    return []


def login(page, base_url: str, username: str, password: str) -> bool:
    if api_login(page, base_url, username, password):
        return True

    for login_path in ["/login", "/login.html", "/", "/index.html"]:
        try:
            page.goto(f"{base_url}{login_path}", wait_until="domcontentloaded", timeout=20000)
            wait_for_page_settle(page, timeout=8000)
        except Exception:
            continue

        inputs = page.locator("input:visible")
        if inputs.count() < 2:
            continue
        filled_user = False
        for index in range(inputs.count()):
            item = inputs.nth(index)
            attrs = " ".join(
                [
                    item.get_attribute("type") or "",
                    item.get_attribute("placeholder") or "",
                    item.get_attribute("name") or "",
                    item.get_attribute("aria-label") or "",
                ]
            ).lower()
            if any(token in attrs for token in ["text", "user", "account", "账号", "用户名", "login"]):
                item.fill(username)
                filled_user = True
                break
        if not filled_user:
            inputs.nth(0).fill(username)

        filled_password = False
        for index in range(inputs.count()):
            item = inputs.nth(index)
            attrs = " ".join(
                [
                    item.get_attribute("type") or "",
                    item.get_attribute("placeholder") or "",
                    item.get_attribute("name") or "",
                    item.get_attribute("aria-label") or "",
                ]
            ).lower()
            if "password" in attrs or "密码" in attrs:
                item.fill(password)
                filled_password = True
                break
        if not filled_password:
            inputs.nth(1).fill(password)

        submit = page.locator("button, input[type='submit']").filter(
            has_text=re.compile("登录|登陆|login|sign", re.I)
        )
        if submit.count() > 0:
            submit.first.click()
        else:
            page.locator("button, input[type='submit']").first.click()

        try:
            page.wait_for_timeout(1500)
            wait_for_page_settle(page, timeout=8000)
        except Exception:
            pass
        if "/login" not in page.url.lower() and not page.url.lower().endswith("/login.html"):
            return True
        try:
            token = page.evaluate("localStorage.getItem('token') || localStorage.getItem('Authorization')")
            if token:
                return True
        except Exception:
            pass
    return False


def page_text_length(page) -> int:
    try:
        text = page.locator("body").inner_text(timeout=3000)
        return len(text.strip())
    except Exception:
        return 0


def page_text(page) -> str:
    try:
        return page.locator("body").inner_text(timeout=3000)
    except Exception:
        return ""


def screenshot_warnings(page, text_length: int, file_size: int) -> list[str]:
    warnings: list[str] = []
    if text_length < 20:
        warnings.append("visible text is very short")
    if file_size < 20_000:
        warnings.append("image file is unusually small")
    current = page.url.lower()
    if "/login" in current:
        warnings.append("current URL is still login page")
    try:
        body = page.locator("body").inner_text(timeout=3000)
        if "404" in body or "not found" in body.lower():
            warnings.append("page text suggests 404/not found")
        if "No static resource" in body or "系统异常" in body:
            warnings.append("page text suggests backend error")
    except Exception:
        pass
    return warnings


def capture_current_page(page, target: Path, label: str) -> ShotRecord:
    target.parent.mkdir(parents=True, exist_ok=True)
    wait_for_page_settle(page, timeout=8000)
    page.wait_for_timeout(800)
    try:
        page.evaluate("document.fonts && document.fonts.ready.catch(() => undefined)")
    except Exception:
        pass
    try:
        page.screenshot(path=str(target), full_page=True, timeout=10000)
    except Exception:
        page.screenshot(path=str(target), full_page=False, timeout=5000)
    text_length = page_text_length(page)
    file_size = target.stat().st_size
    return ShotRecord(
        file=target.name,
        label=label,
        url=page.url,
        text_length=text_length,
        size=file_size,
        warnings=screenshot_warnings(page, text_length, file_size),
    )


def visible_menu_items(page) -> list[dict[str, str]]:
    locator = page.locator(".el-menu-item, [role='menuitem'], aside a, nav a, .nav-link, .menu-item")
    items: list[dict[str, str]] = []
    seen: set[tuple[str, str]] = set()
    for index in range(min(locator.count(), 60)):
        item = locator.nth(index)
        try:
            if not item.is_visible():
                continue
            label = item.inner_text(timeout=1500).strip()
            if not label:
                continue
            if any(token in label for token in ["退出", "注销", "登录", "注册"]):
                continue
            route = item.get_attribute("index") or item.get_attribute("href") or ""
            key = (label, route)
            if key in seen:
                continue
            seen.add(key)
            items.append({"label": label, "route": route})
        except Exception:
            continue
    return items


def navigate_menu_item(page, menu: dict[str, str], base_url: str) -> None:
    label = menu.get("label", "")
    route = menu.get("route", "")
    if route.startswith("http://") or route.startswith("https://"):
        page.goto(route, wait_until="domcontentloaded", timeout=20000)
        return
    if route.startswith("/"):
        page.goto(f"{base_url}{route}", wait_until="domcontentloaded", timeout=20000)
        return
    item = page.locator(".el-menu-item, [role='menuitem'], aside a, nav a, .nav-link, .menu-item").filter(has_text=label)
    if item.count() > 0:
        item.first.click()
        return
    raise RuntimeError(f"cannot navigate menu item: {label}")


def open_first_meaningful_dialog(page) -> str | None:
    buttons = page.locator("button:visible")
    button_count = min(buttons.count(), 80)
    for index in range(button_count):
        button = buttons.nth(index)
        try:
            text = button.inner_text(timeout=1000).strip()
        except Exception:
            continue
        if not best_button_text(text):
            continue
        before_forms = visible_form_count(page)
        try:
            button.click(timeout=3000)
            page.wait_for_timeout(800)
        except Exception:
            continue
        dialog = page.locator(".el-dialog:visible, [role='dialog']:visible, .modal:visible, .ant-modal:visible")
        if dialog.count() > 0:
            return text
        if visible_form_count(page) > before_forms:
            return text
    return None


def capture_top_actions(page, base_url: str, asset_dir: Path, prefix: str, start_seq: int) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    buttons = page.locator("a:visible, button:visible")
    count = min(buttons.count(), 40)
    seq = start_seq
    for index in range(count):
        try:
            item = buttons.nth(index)
            text = item.inner_text(timeout=1000).strip()
        except Exception:
            continue
        if not text or any(word in text for word in ["退出", "登录", "注册", "取消", "删除"]):
            continue
        if not any(word in text for word in ["创建", "新增", "添加", "编辑", "统计", "填写", "发布"]):
            continue
        try:
            before = page.url
            item.click(timeout=3000)
            page.wait_for_timeout(1000)
            wait_for_page_settle(page, timeout=8000)
            if page.url == before:
                continue
            slug = slugify(text, "action")
            records.append(
                capture_current_page(page, asset_dir / f"{prefix}-{seq:02d}-{slug}.png", f"action: {text}")
            )
            seq += 1
            page.goto(before, wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
        except Exception:
            try:
                page.goto(f"{base_url}/dashboard", wait_until="domcontentloaded", timeout=15000)
                wait_for_page_settle(page, timeout=8000)
            except Exception:
                pass
            continue
    return records


def visible_form_count(page) -> int:
    forms = page.locator("form:visible, .el-form:visible")
    try:
        return forms.count()
    except Exception:
        return 0


def close_dialog(page) -> None:
    icon_closers = page.locator(".el-dialog__close:visible, [aria-label='Close']:visible, .btn-close:visible")
    if icon_closers.count() > 0:
        try:
            icon_closers.first.click(timeout=2000)
            page.wait_for_timeout(400)
            return
        except Exception:
            pass
    closers = page.locator("button:visible").filter(
        has_text=re.compile("取消|关闭|返回", re.I)
    )
    if closers.count() > 0:
        try:
            closers.first.click(timeout=2000)
            page.wait_for_timeout(400)
            return
        except Exception:
            pass
    try:
        page.keyboard.press("Escape")
        page.wait_for_timeout(300)
    except Exception:
        pass


def capture_role(page, base_url: str, account: dict[str, Any], asset_dir: Path, max_pages: int) -> RoleRecord:
    role = normalize_role(account)
    prefix = role_file_prefix(account)
    username = str(account.get("username") or "")
    password = str(account.get("password") or "")
    record = RoleRecord(role=role, username=username, login_ok=False, file_prefix=prefix)
    if not username or not password:
        record.warnings.append("account missing username/password")
        return record

    try:
        page.evaluate("localStorage.clear(); sessionStorage.clear();")
    except Exception:
        pass

    record.login_ok = login(page, base_url, username, password)
    if not record.login_ok:
        record.warnings.append("login failed")
        return record
    if "no static resource" in page_text(page).lower() or "系统异常" in page_text(page):
        role_hint = str(account.get("role_key") or account.get("role_label") or "").lower()
        target = "/pages/admin/index.html" if "admin" in role_hint or role == "admin" else "/pages/user/index.html"
        try:
            page.goto(f"{base_url}{target}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
        except Exception:
            pass

    record.screenshots.append(
        capture_current_page(page, asset_dir / f"{prefix}-01-dashboard.png", "post-login dashboard")
    )
    record.visible_menus = visible_menu_items(page)
    if not record.visible_menus:
        record.warnings.append("no visible menu items detected")
        record.screenshots.extend(capture_top_actions(page, base_url, asset_dir, prefix, 2))
        return record

    seq = 2
    for menu in record.visible_menus[:max_pages]:
        label = menu["label"]
        try:
            navigate_menu_item(page, menu, base_url)
            wait_for_page_settle(page, timeout=8000)
            page.wait_for_timeout(800)
        except Exception as exc:
            record.warnings.append(f"menu navigation failed: {label}: {exc}")
            continue

        slug = slugify(label, "menu")
        shot = capture_current_page(page, asset_dir / f"{prefix}-{seq:02d}-{slug}.png", f"menu: {label}")
        record.screenshots.append(shot)
        seq += 1

        action = open_first_meaningful_dialog(page)
        if action:
            dialog_slug = slugify(f"{label}-{action}", "dialog")
            shot = capture_current_page(
                page,
                asset_dir / f"{prefix}-{seq:02d}-{dialog_slug}-dialog.png",
                f"dialog: {label} / {action}",
            )
            record.screenshots.append(shot)
            seq += 1
            close_dialog(page)

    return record


def capture_role_with_retry(
    page,
    runtime: dict[str, Any],
    base_url: str,
    account: dict[str, Any],
    asset_dir: Path,
    max_pages: int,
) -> RoleRecord:
    record = capture_role(page, base_url, account, asset_dir, max_pages)
    if record.login_ok:
        return record
    username = str(account.get("username") or "")
    password = str(account.get("password") or "")
    if username and password and patch_preview_password(runtime, username, password):
        retry_record = capture_role(page, base_url, account, asset_dir, max_pages)
        retry_record.warnings.append("preview database password hash patched to match documented account")
        return retry_record
    return record


def capture_guest_pages(page, base_url: str, asset_dir: Path) -> list[ShotRecord]:
    records: list[ShotRecord] = []
    seen_urls: set[str] = set()
    for path in ["/index.html", "/login", "/login.html", "/register", "/register.html", "/", "/products.html"]:
        try:
            page.goto(f"{base_url}{path}", wait_until="domcontentloaded", timeout=15000)
            wait_for_page_settle(page, timeout=8000)
        except Exception:
            continue
        current_url = page.url.split("#", 1)[0]
        if current_url in seen_urls:
            continue
        seen_urls.add(current_url)
        if "register" in path and "/login" in page.url.lower():
            continue
        label = path.strip("/").replace(".html", "") or "home"
        text = ""
        try:
            text = page.locator("body").inner_text(timeout=3000)
        except Exception:
            pass
        if "404" in text or "not found" in text.lower():
            continue
        if "No static resource" in text or "系统异常" in text:
            continue
        target = asset_dir / f"guest-{len(records) + 1:02d}-{label}.png"
        record = capture_current_page(
            page,
            target,
            f"guest: {label}",
        )
        if "visible text is very short" in record.warnings and "image file is unusually small" in record.warnings:
            try:
                target.unlink()
            except FileNotFoundError:
                pass
            continue
        records.append(record)
    return records


def write_qa(project_id: str, payload: dict[str, Any]) -> Path:
    QA_DIR.mkdir(parents=True, exist_ok=True)
    qa_path = QA_DIR / f"{project_id}.json"
    qa_path.write_text(json.dumps(payload, ensure_ascii=False, indent=2), encoding="utf-8")
    return qa_path


def run_docs_generate(project_id: str) -> tuple[int, Path]:
    return run_logged(project_id, "docs-generate", ["npm.cmd", "run", "docs:generate"])


def run_fine_capture(project_id: str, max_pages: int, skip_docs_generate: bool) -> dict[str, Any]:
    ensure_dirs()
    asset_dir = ASSETS_DIR / project_id
    backup_dir = backup_images(project_id, asset_dir)
    project_removed = clear_images(asset_dir)
    result: dict[str, Any] = {
        "project_id": project_id,
        "mode": "single-project-fine-capture",
        "started_at": time.strftime("%Y-%m-%d %H:%M:%S"),
        "old_images_removed": project_removed,
        "backup_dir": str(backup_dir.relative_to(ROOT)) if backup_dir else None,
        "logs": {},
        "roles": [],
        "guest": [],
        "warnings": [],
    }

    code, log_path = run_logged(project_id, "prepare", [sys.executable, "scripts/project_preview/run_preview.py", "prepare", project_id])
    result["logs"]["prepare"] = str(log_path.relative_to(ROOT))
    try:
        if code != 0:
            result["status"] = "failed"
            result["failed_step"] = "prepare"
            return result

        runtime = load_runtime(project_id)
        base_url = get_frontend_url(runtime)
        accounts = account_list(runtime)
        role_accounts = role_capture_accounts(project_id, accounts)
        accounts_warning = "no accounts discovered; only guest pages will be captured"
        if not accounts:
            result["warnings"].append(accounts_warning)

        with sync_playwright() as playwright:
            browser = playwright.chromium.launch(headless=True)
            context = browser.new_context(viewport=VIEWPORT)
            context.set_default_timeout(8000)
            context.set_default_navigation_timeout(15000)
            context.route(
                re.compile(r"https?://(fonts\.googleapis\.com|fonts\.gstatic\.com)/.*"),
                lambda route: route.abort(),
            )
            page = context.new_page()
            page.on("dialog", lambda dialog: dialog.accept())
            try:
                result["guest"] = [record.__dict__ for record in capture_guest_pages(page, base_url, asset_dir)]
                for account in role_accounts:
                    role_record = capture_role_with_retry(page, runtime, base_url, account, asset_dir, max_pages)
                    result["roles"].append(
                        {
                            "role": role_record.role,
                            "username": role_record.username,
                            "file_prefix": role_record.file_prefix,
                            "login_ok": role_record.login_ok,
                            "visible_menus": role_record.visible_menus,
                            "screenshots": [shot.__dict__ for shot in role_record.screenshots],
                            "warnings": role_record.warnings,
                        }
                    )
                specific_records = capture_project_specific_pages(project_id, page, runtime, base_url, accounts, asset_dir)
                if specific_records:
                    result["project_specific"] = [record.__dict__ for record in specific_records]
                    if not role_accounts and accounts_warning in result["warnings"]:
                        result["warnings"].remove(accounts_warning)
            finally:
                context.close()
                browser.close()

        images = image_files(asset_dir)
        result["image_count"] = len(images)
        if not images:
            result["warnings"].append("no screenshots produced")
        if project_requires_specific_capture(project_id) and not result.get("project_specific"):
            result["warnings"].append("project-specific screenshots were not produced")
            result["status"] = "failed"
            result["failed_step"] = "project_specific"
            return result
        failed_roles = add_login_coverage_warnings(result, accounts)

        code, log_path = run_logged(project_id, "render", [sys.executable, "scripts/project_preview/run_preview.py", "render", project_id])
        result["logs"]["render"] = str(log_path.relative_to(ROOT))
        if code != 0:
            result["status"] = "failed"
            result["failed_step"] = "render"
            return result

        sync_public_assets(project_id)
        if not skip_docs_generate:
            code, log_path = run_docs_generate(project_id)
            result["logs"]["docs_generate"] = str(log_path.relative_to(ROOT))
            if code != 0:
                result["status"] = "failed"
                result["failed_step"] = "docs:generate"
                return result

        result["status"] = "completed" if result["image_count"] > 0 and not failed_roles else "failed"
        result["finished_at"] = time.strftime("%Y-%m-%d %H:%M:%S")
        return result
    except Exception as exc:
        result["status"] = "failed"
        result["failed_step"] = "capture"
        result["error"] = str(exc)
        result["traceback"] = traceback.format_exc()
        return result
    finally:
        if result.get("status") == "failed":
            result["restored_images"] = restore_images(backup_dir, asset_dir)
        code, log_path = run_logged(project_id, "stop", [sys.executable, "scripts/project_preview/run_preview.py", "stop", project_id])
        result["logs"]["stop"] = str(log_path.relative_to(ROOT))
        result["stop_exit_code"] = code
        code, log_path = run_logged(project_id, "cleanup", [sys.executable, "scripts/project_preview/run_preview.py", "cleanup", project_id])
        result["logs"]["cleanup"] = str(log_path.relative_to(ROOT))
        result["cleanup_exit_code"] = code
        write_qa(project_id, result)


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Single-project fine screenshot capture. This intentionally does not accept ranges."
    )
    parser.add_argument("project_id", help="Project ID, e.g. 001")
    parser.add_argument("--max-pages", type=int, default=14, help="Maximum visible menu pages to inspect per role.")
    parser.add_argument("--skip-docs-generate", action="store_true", help="Skip npm run docs:generate after capture.")
    args = parser.parse_args()

    project_id = args.project_id.strip()
    if not re.fullmatch(r"\d{3}", project_id):
        raise SystemExit("project_id must be a three digit ID, for example 001")

    result = run_fine_capture(project_id, max_pages=args.max_pages, skip_docs_generate=args.skip_docs_generate)
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0 if result.get("status") == "completed" else 1


if __name__ == "__main__":
    raise SystemExit(main())
