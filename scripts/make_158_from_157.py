from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_157.py"
dst = ROOT / "scripts" / "develop_158.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "157-backend": "158-backend",
    "157-frontend": "158-frontend",
    "logisticspark": "trainingrefund",
    "LogisticsParkApplication": "TrainingRefundApplication",
    "logistics-yard-gate-157": "training-consumption-refund-158",
    "logistics_park_157": "training_refund_158",
    "logisticspark:token:": "trainingrefund:token:",
    "logistics-yard-gate-157-secret": "training-consumption-refund-158-secret",
    "物流园区车辆入场预约与道口调度平台": "校外培训机构课消统计与退费审批系统",
    "园区调度 157": "教培课消 158",
    "物流园区调度中心": "教培运营中心",
    "物流园区车辆入场与月台调度场景": "校外培训机构教务与财务协同场景",
    "智慧物流": "教培管理",
    "承运商预约、车辆档案、司机档案、时段计划、门岗核验、排队叫号、道口资源、道口分配、装卸任务和周转统计的一体化后台管理能力": "校区档案、课程产品、学员档案、教师档案、班级台账、排课计划、上课考勤、课消记录、退费申请、退费审批和财务流水的一体化后台管理能力",
    "入场预约、门岗核验、排队叫号、道口调度一体化管理": "排课签到、课消统计、退费审批、财务流水一体化管理",
    "入场预约、门岗核验、排队叫号、道口调度": "排课签到、课消统计、退费审批、财务流水",
    "3157": "3158",
    "8157": "8158",
    "database: 60": "database: 61",
    "139157": "139158",
    "park157.local": "edu158.local",
    "157-": "158-",
    "157": "158",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("PRINCIPAL", "校区校长", "principal", "/dashboard"),
    ("ACADEMIC", "教务主管", "academic", "/schedule"),
    ("TEACHER", "任课老师", "teacher", "/attendance"),
    ("FINANCE", "财务审核员", "finance", "/refund"),
    ("PARENT", "学员家长", "parent", "/consumption"),
]'''

modules_block = '''MODULES = [
    ("CampusBranch", "campus_branch", "branch", "CampusBranch", "校区档案", "校区编号、校区名称、校区类型、负责人、启用时间和校区状态维护", ["ADMIN", "PRINCIPAL"], ["校区编号", "校区名称", "校区类型", "负责人", "启用时间", "校区状态", "校区说明"]),
    ("CourseCatalog", "course_catalog", "course", "CourseCatalog", "课程产品", "课程编号、课程名称、课程类型、负责人、上架时间和课程状态维护", ["ADMIN", "PRINCIPAL", "ACADEMIC", "FINANCE"], ["课程编号", "课程名称", "课程类型", "负责人", "上架时间", "课程状态", "课程说明"]),
    ("StudentProfile", "student_profile", "student", "StudentProfile", "学员档案", "学员编号、学员姓名、报名课程、监护人、报名时间和学籍状态维护", ["ADMIN", "PRINCIPAL", "ACADEMIC", "PARENT"], ["学员编号", "学员姓名", "报名课程", "监护人", "报名时间", "学籍状态", "学员说明"]),
    ("TeacherProfile", "teacher_profile", "teacher", "TeacherProfile", "教师档案", "教师编号、教师姓名、授课类型、所属校区、入职时间和教师状态维护", ["ADMIN", "PRINCIPAL", "ACADEMIC", "TEACHER"], ["教师编号", "教师姓名", "授课类型", "所属校区", "入职时间", "教师状态", "教师说明"]),
    ("ClassGroup", "class_group", "classgroup", "ClassGroup", "班级台账", "班级编号、班级名称、班级类型、班主任、开班时间和班级状态维护", ["ADMIN", "PRINCIPAL", "ACADEMIC", "TEACHER"], ["班级编号", "班级名称", "班级类型", "班主任", "开班时间", "班级状态", "班级说明"]),
    ("LessonSchedule", "lesson_schedule", "schedule", "LessonSchedule", "排课计划", "排课编号、课程班级、排课类型、授课老师、上课时间和排课状态维护", ["ADMIN", "PRINCIPAL", "ACADEMIC", "TEACHER"], ["排课编号", "课程班级", "排课类型", "授课老师", "上课时间", "排课状态", "排课说明"]),
    ("AttendanceRecord", "attendance_record", "attendance", "AttendanceRecord", "上课考勤", "考勤编号、上课班级、考勤类型、点名老师、考勤时间和考勤状态维护", ["ADMIN", "ACADEMIC", "TEACHER", "PARENT"], ["考勤编号", "上课班级", "考勤类型", "点名老师", "考勤时间", "考勤状态", "考勤说明"]),
    ("ConsumptionRecord", "consumption_record", "consumption", "ConsumptionRecord", "课消记录", "课消编号、课消课程、课消类型、确认人、课消时间和课消状态维护", ["ADMIN", "ACADEMIC", "TEACHER", "FINANCE", "PARENT"], ["课消编号", "课消课程", "课消类型", "确认人", "课消时间", "课消状态", "课消说明"]),
    ("RefundApplication", "refund_application", "refund", "RefundApplication", "退费申请", "申请编号、申请学员、退费类型、申请人、申请时间和申请状态维护", ["ADMIN", "PRINCIPAL", "FINANCE", "PARENT"], ["申请编号", "申请学员", "退费类型", "申请人", "申请时间", "申请状态", "申请说明"]),
    ("RefundApproval", "refund_approval", "approval", "RefundApproval", "退费审批", "审批编号、退费申请、审批类型、审批人、审批时间和审批状态维护", ["ADMIN", "PRINCIPAL", "FINANCE"], ["审批编号", "退费申请", "审批类型", "审批人", "审批时间", "审批状态", "审批说明"]),
    ("FinanceLedger", "finance_ledger", "ledger", "FinanceLedger", "财务流水", "流水编号、流水事项、收支类型、经办人、发生时间和流水状态维护", ["ADMIN", "PRINCIPAL", "FINANCE"], ["流水编号", "流水事项", "收支类型", "经办人", "发生时间", "流水状态", "流水说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "PRINCIPAL"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

old_all = "['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER']"
new_all = "['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT']"
text = text.replace(old_all, new_all)
text = text.replace('["ADMIN", "DISPATCHER", "GATEKEEPER", "YARDMASTER", "CARRIER"]', '["ADMIN", "PRINCIPAL", "ACADEMIC", "TEACHER", "FINANCE", "PARENT"]')
text = text.replace("<span>admin</span><span>dispatcher</span><span>gatekeeper</span><span>yardmaster</span><span>carrier</span>", "<span>admin</span><span>principal</span><span>academic</span><span>teacher</span><span>finance</span><span>parent</span>")
text = text.replace("DISPATCHER: '/dashboard', GATEKEEPER: '/checkin', YARDMASTER: '/assignment', CARRIER: '/appointment'", "PRINCIPAL: '/dashboard', ACADEMIC: '/schedule', TEACHER: '/attendance', FINANCE: '/refund', PARENT: '/consumption'")
text = text.replace("DISPATCHER: '/dashboard',\n  GATEKEEPER: '/checkin',\n  YARDMASTER: '/assignment',\n  CARRIER: '/appointment'", "PRINCIPAL: '/dashboard',\n  ACADEMIC: '/schedule',\n  TEACHER: '/attendance',\n  FINANCE: '/refund',\n  PARENT: '/consumption'")
text = text.replace("调度主管", "校区校长")
text = text.replace("门岗核验员", "教务主管")
text = text.replace("场内调度员", "任课老师")
text = text.replace("承运商代表", "学员家长")
text = text.replace("已预约", "已排课")
text = text.replace("已核验", "已消课")
text = text.replace("已分配", "已退费")
text = text.replace("近7日预约与周转趋势", "近7日课消与退费趋势")
text = text.replace('data.put("pie", Arrays.asList(map("预约待入场", 32), map("门岗已核验", 58), map("道口已分配", 25), map("装卸已完成", 8)));', 'data.put("pie", Arrays.asList(map("待上课", 32), map("已消课", 58), map("退费审批中", 25), map("财务已结清", 8)));')
text = text.replace('data.put("pie", Arrays.asList(map("预约待入场", 32), map("门岗已消课", 58), map("道口已退费", 25), map("装卸已完成", 8)));', 'data.put("pie", Arrays.asList(map("待上课", 32), map("已消课", 58), map("退费审批中", 25), map("财务已结清", 8)));')
text = text.replace("承运商档案、车辆档案、时段计划、道口资源和周转统计维护", "校区档案、课程产品、班级台账、课消统计和财务总览维护")
text = text.replace("门岗核验、车辆放行、排队叫号和异常入场处理", "排课计划、上课考勤、课消记录和调课补课处理")
text = text.replace("时段计划、排队叫号、道口资源、道口分配和装卸任务维护", "上课考勤、课堂反馈、课消确认和学员沟通维护")
text = text.replace("入场预约、排队叫号、道口分配和装卸任务查看", "课消记录、退费申请、剩余课时和财务流水查看")
text = text.replace("- 任课老师：上课考勤、课堂反馈、课消确认和学员沟通维护\n- 学员家长：课消记录、退费申请、剩余课时和财务流水查看", "- 任课老师：上课考勤、课堂反馈、课消确认和学员沟通维护\n- 财务审核员：退费审批、财务流水、收入核对和退款结算维护\n- 学员家长：课消记录、退费申请、剩余课时和财务流水查看")
text = text.replace("按 ADMIN / DISPATCHER / GATEKEEPER / YARDMASTER / CARRIER 收口页面操作权限", "按 ADMIN / PRINCIPAL / ACADEMIC / TEACHER / FINANCE / PARENT 收口页面操作权限")
text = text.replace("- dispatcher/123456\n- gatekeeper/123456\n- yardmaster/123456\n- carrier/123456", "- principal/123456\n- academic/123456\n- teacher/123456\n- finance/123456\n- parent/123456")
text = text.replace("校区校长、教务主管、任课老师、学员家长账号与状态维护", "校区校长、教务主管、任课老师、财务审核员、学员家长账号与状态维护")

dst.write_text(text, encoding="utf-8")
print(dst)
