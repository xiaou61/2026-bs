from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_155.py"
dst = ROOT / "scripts" / "develop_156.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "155-backend": "156-backend",
    "155-frontend": "156-frontend",
    "communityparty": "dormenergy",
    "CommunityPartyApplication": "DormEnergyApplication",
    "community-party-points-155": "dorm-energy-ranking-156",
    "community_party_155": "dorm_energy_156",
    "communityparty:token:": "dormenergy:token:",
    "community-party-points-155-secret": "dorm-energy-ranking-156-secret",
    "社区党建活动报名与积分激励平台": "校园宿舍能耗监测与节能排名系统",
    "社区党建 155": "宿舍能耗 156",
    "社区党建服务中心": "后勤能源中心",
    "社区基层党建治理场景": "高校宿舍能源管理场景",
    "基层党建治理场景": "高校宿舍能源管理场景",
    "基层治理": "智慧校园",
    "党组织维护、党员档案、党建活动、活动报名、签到记录、志愿任务、积分记录、积分兑换、组织关系和榜单激励的一体化后台管理能力": "宿舍楼栋、宿舍房间、能耗表计、用电读数、能耗账单、预警策略、异常预警、节能任务、节能排行和巡查记录的一体化后台管理能力",
    "活动报名、志愿积分、组织关系、榜单统计一体化管理": "能耗监测、异常预警、节能任务、节能排名一体化管理",
    "活动报名、志愿积分、组织关系、榜单统计": "能耗监测、异常预警、节能任务、节能排名",
    "3155": "3156",
    "8155": "8156",
    "database: 58": "database: 59",
    "139155": "139156",
    "party155.local": "energy156.local",
    "155-": "156-",
    "155": "156",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("LOGISTICS", "后勤管理员", "logistics", "/dashboard"),
    ("COUNSELOR", "宿舍辅导员", "counselor", "/alert"),
    ("ENERGY", "能耗专员", "energy", "/meter"),
    ("STUDENT", "学生代表", "student", "/ranking"),
]'''

modules_block = '''MODULES = [
    ("DormBuilding", "dorm_building", "building", "DormBuilding", "宿舍楼栋", "楼栋编号、楼栋名称、楼栋类型、管理员、启用时间和楼栋状态维护", ["ADMIN", "LOGISTICS"], ["楼栋编号", "楼栋名称", "楼栋类型", "管理员", "启用时间", "楼栋状态", "楼栋说明"]),
    ("DormRoom", "dorm_room", "room", "DormRoom", "宿舍房间", "房间编号、宿舍房间、所属楼栋、责任人、入住时间和房间状态维护", ["ADMIN", "LOGISTICS", "COUNSELOR", "STUDENT"], ["房间编号", "宿舍房间", "所属楼栋", "责任人", "入住时间", "房间状态", "房间说明"]),
    ("MeterDevice", "meter_device", "meter", "MeterDevice", "能耗表计", "表计编号、表计名称、表计类型、维护人、安装时间和表计状态维护", ["ADMIN", "LOGISTICS", "ENERGY"], ["表计编号", "表计名称", "表计类型", "维护人", "安装时间", "表计状态", "表计说明"]),
    ("EnergyReading", "energy_reading", "reading", "EnergyReading", "用电读数", "读数编号、表计房间、读数类型、采集人、采集时间和读数状态维护", ["ADMIN", "LOGISTICS", "ENERGY", "STUDENT"], ["读数编号", "表计房间", "读数类型", "采集人", "采集时间", "读数状态", "读数说明"]),
    ("ConsumptionBill", "consumption_bill", "bill", "ConsumptionBill", "能耗账单", "账单编号、账单名称、费用类型、核算人、账期时间和账单状态维护", ["ADMIN", "LOGISTICS", "ENERGY", "STUDENT"], ["账单编号", "账单名称", "费用类型", "核算人", "账期时间", "账单状态", "账单说明"]),
    ("AlertRule", "alert_rule", "rule", "AlertRule", "预警策略", "策略编号、策略名称、策略类型、配置人、生效时间和策略状态维护", ["ADMIN", "LOGISTICS", "ENERGY"], ["策略编号", "策略名称", "策略类型", "配置人", "生效时间", "策略状态", "策略说明"]),
    ("AbnormalAlert", "abnormal_alert", "alert", "AbnormalAlert", "异常预警", "预警编号、预警宿舍、预警类型、处理人、预警时间和预警状态维护", ["ADMIN", "LOGISTICS", "COUNSELOR", "ENERGY"], ["预警编号", "预警宿舍", "预警类型", "处理人", "预警时间", "预警状态", "预警说明"]),
    ("SavingTask", "saving_task", "task", "SavingTask", "节能任务", "任务编号、任务名称、节能类型、负责人、执行时间和任务状态维护", ["ADMIN", "LOGISTICS", "COUNSELOR", "ENERGY", "STUDENT"], ["任务编号", "任务名称", "节能类型", "负责人", "执行时间", "任务状态", "任务说明"]),
    ("SavingRanking", "saving_ranking", "ranking", "SavingRanking", "节能排行", "排行编号、排行名称、排行类型、统计人、统计周期和发布状态维护", ["ADMIN", "LOGISTICS", "COUNSELOR", "ENERGY", "STUDENT"], ["排行编号", "排行名称", "排行类型", "统计人", "统计周期", "发布状态", "排行说明"]),
    ("InspectionRecord", "inspection_record", "inspection", "InspectionRecord", "巡查记录", "巡查编号、巡查区域、巡查类型、巡查人、巡查时间和巡查状态维护", ["ADMIN", "LOGISTICS", "COUNSELOR", "ENERGY"], ["巡查编号", "巡查区域", "巡查类型", "巡查人", "巡查时间", "巡查状态", "巡查说明"]),
    ("EnergyNotice", "energy_notice", "notice", "EnergyNotice", "通知公告", "公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护", ["ADMIN", "LOGISTICS", "COUNSELOR"], ["公告编号", "公告标题", "公告类型", "发布人", "发布时间", "公告状态", "公告内容"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "LOGISTICS"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

text = text.replace("['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT']", "['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT']")
text = text.replace('["ADMIN", "SECRETARY", "ORGANIZER", "VOLUNTEER", "RESIDENT"]', '["ADMIN", "LOGISTICS", "COUNSELOR", "ENERGY", "STUDENT"]')
text = text.replace("<span>admin</span><span>secretary</span><span>organizer</span><span>volunteer</span><span>resident</span>", "<span>admin</span><span>logistics</span><span>counselor</span><span>energy</span><span>student</span>")
text = text.replace("SECRETARY: '/dashboard', ORGANIZER: '/activity', VOLUNTEER: '/task', RESIDENT: '/registration'", "LOGISTICS: '/dashboard', COUNSELOR: '/alert', ENERGY: '/meter', STUDENT: '/ranking'")
text = text.replace("SECRETARY: '/dashboard',\n  ORGANIZER: '/activity',\n  VOLUNTEER: '/task',\n  RESIDENT: '/registration'", "LOGISTICS: '/dashboard',\n  COUNSELOR: '/alert',\n  ENERGY: '/meter',\n  STUDENT: '/ranking'")
text = text.replace("党务书记", "后勤管理员")
text = text.replace("活动组织员", "宿舍辅导员")
text = text.replace("志愿骨干", "能耗专员")
text = text.replace("社区党员", "学生代表")
text = text.replace("党员", "学生")
text = text.replace("已报名", "已读数")
text = text.replace("已签到", "已巡查")
text = text.replace("已积分", "已节能")
text = text.replace("近7日报名与积分趋势", "近7日能耗与预警趋势")
text = text.replace('data.put("pie", Arrays.asList(map("报名待审核", 32), map("活动进行中", 58), map("积分已发放", 25), map("榜单已发布", 8)));', 'data.put("pie", Arrays.asList(map("读数待复核", 32), map("能耗正常", 58), map("异常预警", 25), map("排行已发布", 8)));')
text = text.replace("党组织、学生档案、组织关系、榜单统计和通知公告维护", "宿舍楼栋、宿舍房间、能耗账单、节能排行和通知公告维护")
text = text.replace("党建活动、活动报名、签到记录、志愿任务和积分兑换维护", "异常预警、巡查记录、节能任务和学生沟通维护")
text = text.replace("活动报名、签到记录、志愿任务、积分记录和积分兑换维护", "能耗表计、用电读数、预警策略、异常预警和巡查记录维护")
text = text.replace("活动报名、积分记录、积分兑换、组织关系和榜单查看", "用电读数、能耗账单、节能任务和节能排行查看")
text = text.replace("按 ADMIN / SECRETARY / ORGANIZER / VOLUNTEER / RESIDENT 收口页面操作权限", "按 ADMIN / LOGISTICS / COUNSELOR / ENERGY / STUDENT 收口页面操作权限")
text = text.replace("- secretary/123456\n- organizer/123456\n- volunteer/123456\n- resident/123456", "- logistics/123456\n- counselor/123456\n- energy/123456\n- student/123456")
text = text.replace("党务书记、宿舍辅导员、能耗专员、学生代表账号与状态维护", "后勤管理员、宿舍辅导员、能耗专员、学生代表账号与状态维护")

dst.write_text(text, encoding="utf-8")
print(dst)
