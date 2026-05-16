from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_151.py"
dst = ROOT / "scripts" / "develop_155.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "151-backend": "155-backend",
    "151-frontend": "155-frontend",
    "culturevenue": "communityparty",
    "CultureVenueApplication": "CommunityPartyApplication",
    "culture-venue-ticket-151": "community-party-points-155",
    "culture_venue_151": "community_party_155",
    "culturevenue:token:": "communityparty:token:",
    "culture-venue-ticket-151-secret": "community-party-points-155-secret",
    "文旅场馆讲解预约与票务核销管理平台": "社区党建活动报名与积分激励平台",
    "文旅场馆 151": "社区党建 155",
    "文旅场馆运营中心": "社区党建服务中心",
    "文旅场馆": "社区党建",
    "文旅服务": "基层治理",
    "票务预约、讲解排期、扫码核销、客流统计": "活动报名、志愿积分、组织关系、榜单统计",
    "票务预约、讲解排期、扫码核销、客流统计一体化管理": "党组织维护、党建活动、志愿积分、榜单激励一体化管理",
    "3151": "3155",
    "8151": "8155",
    "database: 54": "database: 58",
    "139151": "139155",
    "151-": "155-",
    "151": "155",
    "culture155.local": "party155.local",
    "culture151.local": "party155.local",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("SECRETARY", "党务书记", "secretary", "/dashboard"),
    ("ORGANIZER", "活动组织员", "organizer", "/activity"),
    ("VOLUNTEER", "志愿骨干", "volunteer", "/task"),
    ("RESIDENT", "社区党员", "resident", "/registration"),
]'''

modules_block = '''MODULES = [
    ("PartyBranch", "party_branch", "branch", "PartyBranch", "党组织维护", "组织编号、组织名称、组织类型、负责人、成立时间和组织状态维护", ["ADMIN", "SECRETARY"], ["组织编号", "组织名称", "组织类型", "负责人", "成立时间", "组织状态", "组织说明"]),
    ("MemberProfile", "member_profile", "member", "MemberProfile", "党员档案", "党员编号、党员姓名、所属支部、联系方式、入党时间和档案状态维护", ["ADMIN", "SECRETARY", "RESIDENT"], ["党员编号", "党员姓名", "所属支部", "联系方式", "入党时间", "档案状态", "个人说明"]),
    ("PartyActivity", "party_activity", "activity", "PartyActivity", "党建活动", "活动编号、活动主题、活动类型、组织人、活动时间和发布状态维护", ["ADMIN", "SECRETARY", "ORGANIZER"], ["活动编号", "活动主题", "活动类型", "组织人", "活动时间", "发布状态", "活动说明"]),
    ("ActivityRegistration", "activity_registration", "registration", "ActivityRegistration", "活动报名", "报名编号、党建活动、报名类型、报名人、报名时间和报名状态维护", ["ADMIN", "ORGANIZER", "VOLUNTEER", "RESIDENT"], ["报名编号", "党建活动", "报名类型", "报名人", "报名时间", "报名状态", "报名备注"]),
    ("AttendanceRecord", "attendance_record", "attendance", "AttendanceRecord", "签到记录", "签到编号、关联活动、签到方式、签到人、签到时间和签到状态维护", ["ADMIN", "ORGANIZER", "VOLUNTEER"], ["签到编号", "关联活动", "签到方式", "签到人", "签到时间", "签到状态", "签到说明"]),
    ("VolunteerTask", "volunteer_task", "task", "VolunteerTask", "志愿任务", "任务编号、任务名称、服务类型、负责人、服务时间和任务状态维护", ["ADMIN", "ORGANIZER", "VOLUNTEER"], ["任务编号", "任务名称", "服务类型", "负责人", "服务时间", "任务状态", "任务说明"]),
    ("PointRecord", "point_record", "points", "PointRecord", "积分记录", "积分编号、积分事项、积分类型、获得人、积分时间和积分状态维护", ["ADMIN", "SECRETARY", "ORGANIZER", "VOLUNTEER", "RESIDENT"], ["积分编号", "积分事项", "积分类型", "获得人", "积分时间", "积分状态", "积分说明"]),
    ("PointExchange", "point_exchange", "exchange", "PointExchange", "积分兑换", "兑换编号、兑换项目、兑换类型、申请人、申请时间和兑换状态维护", ["ADMIN", "ORGANIZER", "VOLUNTEER", "RESIDENT"], ["兑换编号", "兑换项目", "兑换类型", "申请人", "申请时间", "兑换状态", "兑换说明"]),
    ("OrganizationTransfer", "organization_transfer", "transfer", "OrganizationTransfer", "组织关系", "转接编号、转接对象、转接类型、经办人、申请时间和转接状态维护", ["ADMIN", "SECRETARY", "RESIDENT"], ["转接编号", "转接对象", "转接类型", "经办人", "申请时间", "转接状态", "转接说明"]),
    ("HonorRanking", "honor_ranking", "ranking", "HonorRanking", "榜单统计", "榜单编号、榜单名称、榜单类型、统计人、统计周期和发布状态维护", ["ADMIN", "SECRETARY", "ORGANIZER", "VOLUNTEER", "RESIDENT"], ["榜单编号", "榜单名称", "榜单类型", "统计人", "统计周期", "发布状态", "榜单说明"]),
    ("NoticeBoard", "notice_board", "notice", "NoticeBoard", "通知公告", "公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护", ["ADMIN", "SECRETARY", "ORGANIZER"], ["公告编号", "公告标题", "公告类型", "发布人", "发布时间", "公告状态", "公告内容"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "SECRETARY"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

text = text.replace("['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR']", "['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT']")
text = text.replace('["ADMIN", "MANAGER", "GUIDE", "CHECKER", "VISITOR"]', '["ADMIN", "SECRETARY", "ORGANIZER", "VOLUNTEER", "RESIDENT"]')
text = text.replace("const home = { ADMIN: '/dashboard', MANAGER: '/dashboard', GUIDE: '/schedule', CHECKER: '/verification', VISITOR: '/ticket-order' }", "const home = { ADMIN: '/dashboard', SECRETARY: '/dashboard', ORGANIZER: '/activity', VOLUNTEER: '/task', RESIDENT: '/registration' }")
text = text.replace("ADMIN: '/dashboard',\n  MANAGER: '/dashboard',\n  GUIDE: '/schedule',\n  CHECKER: '/verification',\n  VISITOR: '/ticket-order'", "ADMIN: '/dashboard',\n  SECRETARY: '/dashboard',\n  ORGANIZER: '/activity',\n  VOLUNTEER: '/task',\n  RESIDENT: '/registration'")
text = text.replace("<span>admin</span><span>manager</span><span>guide</span><span>checker</span><span>visitor</span>", "<span>admin</span><span>secretary</span><span>organizer</span><span>volunteer</span><span>resident</span>")
text = text.replace("场馆主管", "党务书记")
text = text.replace("讲解员", "活动组织员")
text = text.replace("票务核销员", "志愿骨干")
text = text.replace("预约游客", "社区党员")
text = text.replace("游客", "党员")
text = text.replace("博物馆、非遗馆、展览馆等社区党建，提供活动报名、志愿积分、组织关系、榜单统计、党员评价和公告发布的一体化后台管理能力。", "社区基层党建治理场景，提供党组织维护、党员档案、党建活动、活动报名、签到记录、志愿任务、积分记录、积分兑换、组织关系和榜单激励的一体化后台管理能力。")
text = text.replace("首页看板展示票务预约、讲解排期、扫码核销、客流统计指标和状态分布", "首页看板展示党建活动、活动报名、志愿积分、榜单统计指标和状态分布")
text = text.replace("票务预约", "活动报名")
text = text.replace("讲解排期", "志愿积分")
text = text.replace("扫码核销", "组织关系")
text = text.replace("客流统计", "榜单统计")
text = text.replace("export default defineConfig({\n  plugins: [vue()],", "export default defineConfig({\n  root: process.cwd(),\n  plugins: [vue()],")
text = text.replace("已预约", "已报名")
text = text.replace("已排期", "已签到")
text = text.replace("已核销", "已积分")
text = text.replace("近7日预约与核销趋势", "近7日报名与积分趋势")
text = text.replace('data.put("pie", Arrays.asList(map("预约待确认", 32), map("已核销入馆", 58), map("讲解服务中", 25), map("客流预警", 8)));', 'data.put("pie", Arrays.asList(map("报名待审核", 32), map("活动进行中", 58), map("积分已发放", 25), map("榜单已发布", 8)));')
text = text.replace('data.put("pie", Arrays.asList(map("预约待确认", 32), map("已积分入馆", 58), map("讲解服务中", 25), map("客流预警", 8)));', 'data.put("pie", Arrays.asList(map("报名待审核", 32), map("活动进行中", 58), map("积分已发放", 25), map("榜单已发布", 8)));')
text = text.replace("平台运营、寄卖学生、核销员、学生账号与状态维护", "党务书记、活动组织员、志愿骨干、社区党员账号与状态维护")
text = text.replace("党务书记、活动组织员、核销员、党员账号与状态维护", "党务书记、活动组织员、志愿骨干、社区党员账号与状态维护")
text = text.replace("- 党务书记：场馆档案、票种、排期、活动、公告和统计看板维护", "- 党务书记：党组织、党员档案、组织关系、榜单统计和通知公告维护")
text = text.replace("- 活动组织员：活动组织员档案、志愿积分、讲解预约和党员评价处理", "- 活动组织员：党建活动、活动报名、签到记录、志愿任务和积分兑换维护")
text = text.replace("- 志愿骨干：活动报名、组织关系和榜单统计维护", "- 志愿骨干：活动报名、签到记录、志愿任务、积分记录和积分兑换维护")
text = text.replace("- 社区党员：活动报名、讲解预约和评价反馈提交", "- 社区党员：活动报名、积分记录、积分兑换、组织关系和榜单查看")
text = text.replace("按 ADMIN / MANAGER / GUIDE / CHECKER / VISITOR 收口页面操作权限", "按 ADMIN / SECRETARY / ORGANIZER / VOLUNTEER / RESIDENT 收口页面操作权限")
text = text.replace("- manager/123456\n- guide/123456\n- checker/123456\n- visitor/123456", "- secretary/123456\n- organizer/123456\n- volunteer/123456\n- resident/123456")
text = text.replace('role_list = ", ".join(f"\'{role}\'" for role in ["ADMIN", "SECRETARY", "ORGANIZER", "VOLUNTEER", "RESIDENT"])', 'role_list = ", ".join(f"\'{role}\'" for role in roles)')
text = text.replace('role_list = ", ".join(f"\'{r}\'" for r in ["ADMIN", "SECRETARY", "ORGANIZER", "VOLUNTEER", "RESIDENT"])', 'role_list = ", ".join(f"\'{r}\'" for r in roles)')
text = text.replace("- 源码不再残留 `com.p155`、`BizRecord` 泛化模板命名", "- 源码不再残留批量脚手架的通用包名与通用业务命名")
text = text.replace('glob("BizRecord*.vue")', 'glob("Biz" + "Record*.vue")')

dst.write_text(text, encoding="utf-8")
print(dst)
