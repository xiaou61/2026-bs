import hashlib
import sys
import unittest
from pathlib import Path

import bcrypt

sys.path.insert(0, str(Path(__file__).resolve().parent))
import fine_capture


class FineCapturePasswordHashTest(unittest.TestCase):
    def test_uses_md5_when_existing_hash_is_md5(self):
        self.assertEqual(
            fine_capture.make_preview_password_hash("e10adc3949ba59abbe56e057f20f883e", "123456"),
            hashlib.md5("123456".encode()).hexdigest(),
        )

    def test_uses_bcrypt_when_existing_hash_is_bcrypt(self):
        existing = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi"

        generated = fine_capture.make_preview_password_hash(existing, "123456")

        self.assertTrue(generated.startswith("$2"))
        self.assertTrue(bcrypt.checkpw(b"123456", generated.encode()))


class FineCaptureRoleAccountTest(unittest.TestCase):
    def test_project_014_excludes_admin_accounts_from_generic_role_capture(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "admin123"},
            {"role_key": "admin", "role_label": "管理员", "username": "manager", "password": "manager123"},
            {"role_key": "user", "role_label": "学生", "username": "student1", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("014", accounts)

        self.assertEqual([account["username"] for account in role_accounts], ["student1"])

    def test_project_015_excludes_admin_accounts_from_generic_role_capture(self):
        accounts = [
            {"role_key": "user", "role_label": "用户", "username": "admin", "password": "admin123"},
            {"role_key": "user", "role_label": "用户", "username": "20210001", "password": "123456"},
            {"role_key": "admin", "role_label": "超级管理员", "username": "admin", "password": "admin123"},
        ]

        role_accounts = fine_capture.role_capture_accounts("015", accounts)

        self.assertEqual([account["username"] for account in role_accounts], ["20210001"])

    def test_project_016_uses_project_specific_capture_only(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "admin123"},
            {"role_key": "user", "role_label": "用户", "username": "user001", "password": "123456"},
            {"role_key": "user", "role_label": "用户", "username": "user002", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("016", accounts)

        self.assertEqual(role_accounts, [])

    def test_project_017_uses_project_specific_capture_only(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "admin123"},
            {"role_key": "user", "role_label": "学生", "username": "20210001", "password": "123456"},
            {"role_key": "merchant", "role_label": "商家", "username": "13900000001", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("017", accounts)

        self.assertEqual(role_accounts, [])

    def test_project_018_uses_project_specific_capture_only(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "123456"},
            {"role_key": "student", "role_label": "学生", "username": "student1", "password": "123456"},
            {"role_key": "company", "role_label": "企业", "username": "company1", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("018", accounts)

        self.assertEqual(role_accounts, [])

    def test_project_019_uses_project_specific_capture_only(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "admin123"},
            {"role_key": "coach", "role_label": "教练", "username": "coach1", "password": "coach123"},
            {"role_key": "student", "role_label": "学生", "username": "student1", "password": "student123"},
        ]

        role_accounts = fine_capture.role_capture_accounts("019", accounts)

        self.assertEqual(role_accounts, [])

    def test_project_020_uses_project_specific_capture_only(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "123456"},
            {"role_key": "teacher", "role_label": "老师", "username": "teacher1", "password": "123456"},
            {"role_key": "student", "role_label": "学生", "username": "student1", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("020", accounts)

        self.assertEqual(role_accounts, [])

    def test_project_021_uses_project_specific_capture_only(self):
        accounts = [
            {"role_key": "seller", "role_label": "卖家", "username": "preview_seller", "password": "123456"},
            {"role_key": "buyer", "role_label": "买家", "username": "preview_buyer", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("021", accounts)

        self.assertEqual(role_accounts, [])

    def test_other_projects_keep_admin_accounts_for_generic_role_capture(self):
        accounts = [
            {"role_key": "admin", "role_label": "管理员", "username": "admin", "password": "admin123"},
            {"role_key": "user", "role_label": "学生", "username": "student1", "password": "123456"},
        ]

        role_accounts = fine_capture.role_capture_accounts("013", accounts)

        self.assertEqual([account["username"] for account in role_accounts], ["admin", "student1"])


class FineCaptureLoginPayloadTest(unittest.TestCase):
    def test_login_payloads_include_account_for_static_auth_projects(self):
        payloads = fine_capture.login_payloads("20210001", "123456")

        self.assertIn({"account": "20210001", "password": "123456"}, payloads)

    def test_login_user_from_payload_supports_flat_user_fields(self):
        user = fine_capture.login_user_from_payload(
            {
                "token": "token-value",
                "userId": 21,
                "username": "preview_buyer",
                "realName": "预览买家",
                "creditScore": 100,
            }
        )

        self.assertEqual(user["id"], 21)
        self.assertEqual(user["userId"], 21)
        self.assertEqual(user["realName"], "预览买家")


class FineCaptureLoginStorageTest(unittest.TestCase):
    def test_browser_login_script_sets_user_type_from_role_hint(self):
        script = fine_capture.browser_login_storage_script()

        self.assertIn("localStorage.setItem('userType', userType)", script)
        self.assertIn("role", script)

    def test_browser_login_script_supports_merchant_user_type(self):
        script = fine_capture.browser_login_storage_script()

        self.assertIn("role === 'merchant'", script)
        self.assertIn("userType = 'merchant'", script)


class FineCaptureWarningLogicTest(unittest.TestCase):
    def test_no_role_login_warning_is_not_needed_when_project_specific_screenshots_exist(self):
        result = {
            "roles": [],
            "project_specific": [{"file": "user-user001-10-home.png"}],
            "warnings": [],
        }

        fine_capture.add_login_coverage_warnings(result, accounts=[{"username": "user001"}])

        self.assertNotIn("no role login succeeded", result["warnings"])


if __name__ == "__main__":
    unittest.main()
