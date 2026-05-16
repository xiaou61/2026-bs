from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_160.py"
dst = ROOT / "scripts" / "develop_161.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "160-backend": "161-backend",
    "160-frontend": "161-frontend",
    "clubfinance": "lostfound",
    "ClubFinanceApplication": "ScenicLostFoundApplication",
    "campus-club-budget-160": "scenic-lost-found-161",
    "club_finance_160": "scenic_lost_found_161",
    "clubfinance:token:": "lostfound:token:",
    "campus-club-budget-160-secret": "scenic-lost-found-161-secret",
    "校园社团活动预算报销与物资借用系统": "景区失物招领与游客寻回协同平台",
    "社团财资 160": "景区寻回 161",
    "校团委社团中心": "景区游客服务中心",
    "高校学生社团活动预算、报销票据与物资借还协同场景": "景区游客失物登记、拾物上报、身份核验与寻回协同场景",
    "校园社团": "旅游服务",
    "社团档案、成员档案、活动立项、预算申请、预算明细、审批记录、报销申请、票据归档、物资台账、物资借用和归还验收的一体化后台管理能力": "景区区域、失物登记、拾物上报、游客认领、身份核验、位置追踪、暂存保管、通知协同、归还交接、寻回任务和回访评价的一体化后台管理能力",
    "活动立项、预算报销、票据归档、物资借还一体化管理": "失物登记、游客认领、身份核验、归还交接一体化协同",
    "活动立项、预算报销、票据归档、物资借还": "失物登记、游客认领、身份核验、归还交接",
    "3160": "3161",
    "8160": "8161",
    "database: 63": "database: 64",
    "139160": "139161",
    "club160.local": "scenic161.local",
    "160-": "161-",
    "160": "161",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("SERVICE", "游客服务员", "service", "/lost"),
    ("SECURITY", "安保巡查员", "security", "/found"),
    ("SCENIC", "景区运营员", "scenic", "/area"),
    ("BROADCAST", "通知协同员", "broadcast", "/notice"),
    ("VISITOR", "游客用户", "visitor", "/claim"),
]'''

modules_block = '''MODULES = [
    ("ScenicArea", "scenic_area", "area", "ScenicArea", "景区区域", "区域编号、区域名称、区域类型、负责人、启用时间和区域状态维护", ["ADMIN", "SCENIC", "SERVICE"], ["区域编号", "区域名称", "区域类型", "负责人", "启用时间", "区域状态", "区域说明"]),
    ("LostItem", "lost_item", "lost", "LostItem", "失物登记", "失物编号、失物名称、失物类型、登记人、丢失时间和失物状态维护", ["ADMIN", "SERVICE", "SECURITY", "VISITOR"], ["失物编号", "失物名称", "失物类型", "登记人", "丢失时间", "失物状态", "失物说明"]),
    ("FoundReport", "found_report", "found", "FoundReport", "拾物上报", "拾物编号、拾物名称、拾物类型、上报人、发现时间和拾物状态维护", ["ADMIN", "SERVICE", "SECURITY", "VISITOR"], ["拾物编号", "拾物名称", "拾物类型", "上报人", "发现时间", "拾物状态", "拾物说明"]),
    ("ClaimApplication", "claim_application", "claim", "ClaimApplication", "游客认领", "认领编号、认领物品、认领类型、申请人、申请时间和认领状态维护", ["ADMIN", "SERVICE", "VISITOR"], ["认领编号", "认领物品", "认领类型", "申请人", "申请时间", "认领状态", "认领说明"]),
    ("IdentityVerify", "identity_verify", "verify", "IdentityVerify", "身份核验", "核验编号、核验对象、核验类型、核验人、核验时间和核验状态维护", ["ADMIN", "SERVICE"], ["核验编号", "核验对象", "核验类型", "核验人", "核验时间", "核验状态", "核验说明"]),
    ("LocationTrace", "location_trace", "trace", "LocationTrace", "位置追踪", "追踪编号、关联物品、追踪类型、巡查人、定位时间和追踪状态维护", ["ADMIN", "SECURITY", "SCENIC", "SERVICE"], ["追踪编号", "关联物品", "追踪类型", "巡查人", "定位时间", "追踪状态", "追踪说明"]),
    ("CustodyRecord", "custody_record", "custody", "CustodyRecord", "暂存保管", "保管编号、保管物品、保管类型、保管人、入库时间和保管状态维护", ["ADMIN", "SERVICE", "SECURITY"], ["保管编号", "保管物品", "保管类型", "保管人", "入库时间", "保管状态", "保管说明"]),
    ("NoticeBroadcast", "notice_broadcast", "notice", "NoticeBroadcast", "通知协同", "通知编号、通知对象、通知类型、发布人、发布时间和通知状态维护", ["ADMIN", "SERVICE", "BROADCAST", "SCENIC"], ["通知编号", "通知对象", "通知类型", "发布人", "发布时间", "通知状态", "通知说明"]),
    ("HandoverRecord", "handover_record", "handover", "HandoverRecord", "归还交接", "交接编号、交接物品、交接类型、交接人、交接时间和交接状态维护", ["ADMIN", "SERVICE", "VISITOR"], ["交接编号", "交接物品", "交接类型", "交接人", "交接时间", "交接状态", "交接说明"]),
    ("SearchTask", "search_task", "search", "SearchTask", "寻回任务", "任务编号、任务物品、任务类型、执行人、执行时间和任务状态维护", ["ADMIN", "SERVICE", "SECURITY", "SCENIC"], ["任务编号", "任务物品", "任务类型", "执行人", "执行时间", "任务状态", "任务说明"]),
    ("FeedbackRecord", "feedback_record", "feedback", "FeedbackRecord", "回访评价", "回访编号、回访游客、回访类型、回访人、回访时间和回访状态维护", ["ADMIN", "SERVICE", "VISITOR", "BROADCAST"], ["回访编号", "回访游客", "回访类型", "回访人", "回访时间", "回访状态", "回访说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "SCENIC"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

old_all = "['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'WAREHOUSE', 'MEMBER']"
new_all = "['ADMIN', 'SERVICE', 'SECURITY', 'SCENIC', 'BROADCAST', 'VISITOR']"
text = text.replace(old_all, new_all)
text = text.replace('["ADMIN", "UNION", "CLUB", "TREASURER", "WAREHOUSE", "MEMBER"]', '["ADMIN", "SERVICE", "SECURITY", "SCENIC", "BROADCAST", "VISITOR"]')
text = text.replace("<span>admin</span><span>union</span><span>club</span><span>treasurer</span><span>warehouse</span><span>member</span>", "<span>admin</span><span>service</span><span>security</span><span>scenic</span><span>broadcast</span><span>visitor</span>")
text = text.replace("UNION: '/budget', CLUB: '/activity', TREASURER: '/reimbursement', WAREHOUSE: '/asset', MEMBER: '/borrow'", "SERVICE: '/lost', SECURITY: '/found', SCENIC: '/area', BROADCAST: '/notice', VISITOR: '/claim'")
text = text.replace("UNION: '/budget',\n  CLUB: '/activity',\n  TREASURER: '/reimbursement',\n  WAREHOUSE: '/asset',\n  MEMBER: '/borrow'", "SERVICE: '/lost',\n  SECURITY: '/found',\n  SCENIC: '/area',\n  BROADCAST: '/notice',\n  VISITOR: '/claim'")
text = text.replace("团委审核员", "游客服务员")
text = text.replace("社团负责人", "安保巡查员")
text = text.replace("社团财务员", "景区运营员")
text = text.replace("物资管理员", "通知协同员")
text = text.replace("社团成员", "游客用户")
text = text.replace("已立项", "已登记")
text = text.replace("已报销", "已认领")
text = text.replace("已归还", "已寻回")
text = text.replace("近7日预算与借用趋势", "近7日登记与寻回趋势")
text = text.replace('data.put("pie", Arrays.asList(map("待审批", 32), map("预算通过", 58), map("报销处理中", 25), map("物资已归还", 8)));', 'data.put("pie", Arrays.asList(map("待认领", 32), map("核验中", 58), map("待归还", 25), map("已寻回", 8)));')
text = text.replace('data.put("pie", Arrays.asList(map("待审批", 32), map("预算通过", 58), map("报销处理中", 25), map("物资已寻回", 8)));', 'data.put("pie", Arrays.asList(map("待认领", 32), map("核验中", 58), map("待归还", 25), map("已寻回", 8)));')
text = text.replace("社团档案、活动立项、预算审批和经费使用复核", "失物登记、游客认领、身份核验和归还交接维护")
text = text.replace("活动立项、预算申请、报销申请和成员协同维护", "拾物上报、位置追踪、寻回任务和现场巡查维护")
text = text.replace("预算明细、报销申请、票据归档和费用核对维护", "景区区域、位置追踪、寻回任务和服务资源维护")
text = text.replace("物资台账、物资借用、归还验收和库存状态维护", "通知协同、广播寻物、短信提醒和回访评价维护")
text = text.replace("活动申请、物资借用、归还反馈和报销进度查看", "失物登记、拾物上报、游客认领和寻回进度查看")
text = text.replace("按 ADMIN / UNION / CLUB / TREASURER / WAREHOUSE / MEMBER 收口页面操作权限", "按 ADMIN / SERVICE / SECURITY / SCENIC / BROADCAST / VISITOR 收口页面操作权限")
text = text.replace("- union/123456\n- club/123456\n- treasurer/123456\n- warehouse/123456\n- member/123456", "- service/123456\n- security/123456\n- scenic/123456\n- broadcast/123456\n- visitor/123456")
text = text.replace("游客服务员、安保巡查员、景区运营员、通知协同员、游客用户账号与状态维护", "游客服务员、安保巡查员、景区运营员、通知协同员、游客用户账号与状态维护")

dst.write_text(text, encoding="utf-8")
print(dst)
