from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_151.py"
dst = ROOT / "scripts" / "develop_154.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "151-backend": "154-backend",
    "151-frontend": "154-frontend",
    "culturevenue": "pethospital",
    "CultureVenueApplication": "PetHospitalApplication",
    "culture-venue-ticket-151": "pet-hospital-vaccine-154",
    "culture_venue_151": "pet_hospital_154",
    "culturevenue:token:": "pethospital:token:",
    "culture-venue-ticket-151-secret": "pet-hospital-vaccine-154-secret",
    "文旅场馆讲解预约与票务核销管理平台": "宠物医院接诊排班与疫苗随访管理系统",
    "文旅场馆 151": "宠物医院 154",
    "文旅场馆运营中心": "宠物医院接诊中心",
    "文旅场馆": "宠物医院",
    "文旅服务": "宠物医疗",
    "票务预约、讲解排期、扫码核销、客流统计": "接诊建档、排班预约、疫苗提醒、随访记录",
    "票务预约、讲解排期、扫码核销、客流统计一体化管理": "宠物建档、医生排班、接诊预约、疫苗随访一体化管理",
    "3151": "3154",
    "8151": "8154",
    "database: 54": "database: 57",
    "139151": "139154",
    "151-": "154-",
    "151": "154",
    "culture154.local": "pet154.local",
    "culture151.local": "pet154.local",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("RECEPTION", "前台接诊员", "reception", "/appointment"),
    ("DOCTOR", "宠物医生", "doctor", "/visit"),
    ("NURSE", "护理接种员", "nurse", "/vaccine-plan"),
    ("OWNER", "宠物主人", "owner", "/follow-up"),
]'''

modules_block = '''MODULES = [
    ("PetOwner", "pet_owner", "owner", "PetOwner", "宠主档案", "宠主编号、宠主姓名、联系方式、常住区域、建档时间和账号状态维护", ["ADMIN", "RECEPTION", "OWNER"], ["宠主编号", "宠主姓名", "联系方式", "常住区域", "建档时间", "账号状态", "备注信息"]),
    ("PetProfile", "pet_profile", "pet", "PetProfile", "宠物档案", "宠物编号、宠物昵称、宠物类型、宠主姓名、出生日期和健康状态维护", ["ADMIN", "RECEPTION", "DOCTOR", "OWNER"], ["宠物编号", "宠物昵称", "宠物类型", "宠主姓名", "出生日期", "健康状态", "过敏备注"]),
    ("DoctorProfile", "doctor_profile", "doctor", "DoctorProfile", "医生档案", "医生工号、医生姓名、擅长科室、所属诊室、坐诊时间和在岗状态维护", ["ADMIN", "RECEPTION", "DOCTOR"], ["医生工号", "医生姓名", "擅长科室", "所属诊室", "坐诊时间", "在岗状态", "专长说明"]),
    ("ClinicSchedule", "clinic_schedule", "schedule", "ClinicSchedule", "接诊排班", "排班编号、坐诊医生、诊室区域、班次类型、排班时间和排班状态维护", ["ADMIN", "RECEPTION", "DOCTOR"], ["排班编号", "坐诊医生", "诊室区域", "班次类型", "排班时间", "排班状态", "排班说明"]),
    ("VisitAppointment", "visit_appointment", "appointment", "VisitAppointment", "接诊预约", "预约编号、宠物名称、接诊类型、预约人、预约时间和预约状态维护", ["ADMIN", "RECEPTION", "DOCTOR", "OWNER"], ["预约编号", "宠物名称", "接诊类型", "预约人", "预约时间", "预约状态", "症状描述"]),
    ("VisitRecord", "visit_record", "visit", "VisitRecord", "接诊记录", "接诊编号、关联预约、疾病类型、接诊医生、接诊时间和接诊状态维护", ["ADMIN", "DOCTOR", "NURSE"], ["接诊编号", "关联预约", "疾病类型", "接诊医生", "接诊时间", "接诊状态", "诊疗建议"]),
    ("VaccinePlan", "vaccine_plan", "vaccine-plan", "VaccinePlan", "疫苗计划", "计划编号、宠物名称、疫苗类型、负责护士、计划时间和提醒状态维护", ["ADMIN", "DOCTOR", "NURSE", "OWNER"], ["计划编号", "宠物名称", "疫苗类型", "负责护士", "计划时间", "提醒状态", "接种说明"]),
    ("VaccineRecord", "vaccine_record", "vaccine-record", "VaccineRecord", "疫苗接种", "接种编号、疫苗计划、疫苗批次、接种护士、接种时间和接种状态维护", ["ADMIN", "DOCTOR", "NURSE"], ["接种编号", "疫苗计划", "疫苗批次", "接种护士", "接种时间", "接种状态", "反应记录"]),
    ("FollowUpRecord", "follow_up_record", "follow-up", "FollowUpRecord", "随访记录", "随访编号、关联接诊、随访方式、随访人、随访时间和随访状态维护", ["ADMIN", "DOCTOR", "NURSE", "OWNER"], ["随访编号", "关联接诊", "随访方式", "随访人", "随访时间", "随访状态", "随访结论"]),
    ("MedicineStock", "medicine_stock", "medicine", "MedicineStock", "药品库存", "药品编号、药品名称、药品类型、管理人、有效日期和库存状态维护", ["ADMIN", "DOCTOR", "NURSE"], ["药品编号", "药品名称", "药品类型", "管理人", "有效日期", "库存状态", "存储说明"]),
    ("BillingRecord", "billing_record", "billing", "BillingRecord", "费用结算", "结算编号、关联接诊、费用类型、收费员、结算时间和结算状态维护", ["ADMIN", "RECEPTION", "OWNER"], ["结算编号", "关联接诊", "费用类型", "收费员", "结算时间", "结算状态", "费用说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "RECEPTION"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

text = text.replace("['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR']", "['ADMIN', 'RECEPTION', 'DOCTOR', 'NURSE', 'OWNER']")
text = text.replace('["ADMIN", "MANAGER", "GUIDE", "CHECKER", "VISITOR"]', '["ADMIN", "RECEPTION", "DOCTOR", "NURSE", "OWNER"]')
text = text.replace("const home = { ADMIN: '/dashboard', MANAGER: '/dashboard', GUIDE: '/schedule', CHECKER: '/verification', VISITOR: '/ticket-order' }", "const home = { ADMIN: '/dashboard', RECEPTION: '/appointment', DOCTOR: '/visit', NURSE: '/vaccine-plan', OWNER: '/follow-up' }")
text = text.replace("ADMIN: '/dashboard',\n  MANAGER: '/dashboard',\n  GUIDE: '/schedule',\n  CHECKER: '/verification',\n  VISITOR: '/ticket-order'", "ADMIN: '/dashboard',\n  RECEPTION: '/appointment',\n  DOCTOR: '/visit',\n  NURSE: '/vaccine-plan',\n  OWNER: '/follow-up'")
text = text.replace("<span>admin</span><span>manager</span><span>guide</span><span>checker</span><span>visitor</span>", "<span>admin</span><span>reception</span><span>doctor</span><span>nurse</span><span>owner</span>")
text = text.replace("场馆主管", "前台接诊员")
text = text.replace("讲解员", "宠物医生")
text = text.replace("票务核销员", "护理接种员")
text = text.replace("预约游客", "宠物主人")
text = text.replace("游客", "宠主")
text = text.replace("博物馆、非遗馆、展览馆等宠物医院，提供接诊建档、排班预约、疫苗提醒、随访记录、宠主评价和公告发布的一体化后台管理能力。", "宠物医院门诊运营场景，提供宠主建档、宠物档案、医生排班、接诊预约、疫苗计划、接种记录、随访记录和费用结算的一体化后台管理能力。")
text = text.replace("首页看板展示票务预约、讲解排期、扫码核销、客流统计指标和状态分布", "首页看板展示宠物档案、接诊预约、疫苗计划、随访记录指标和状态分布")
text = text.replace("票务预约", "接诊预约")
text = text.replace("讲解排期", "接诊排班")
text = text.replace("扫码核销", "疫苗接种")
text = text.replace("客流统计", "随访记录")
text = text.replace("已排期", "已排班")
text = text.replace("已核销", "已接种")
text = text.replace("近7日预约与核销趋势", "近7日接诊与疫苗趋势")
text = text.replace('data.put("pie", Arrays.asList(map("预约待确认", 32), map("已核销入馆", 58), map("讲解服务中", 25), map("客流预警", 8)));', 'data.put("pie", Arrays.asList(map("预约待接诊", 32), map("治疗进行中", 58), map("疫苗待提醒", 25), map("随访待回访", 8)));')
text = text.replace("平台运营、寄卖学生、核销员、学生账号与状态维护", "前台接诊员、宠物医生、护理接种员、宠物主人账号与状态维护")
text = text.replace("前台接诊员、宠物医生、核销员、宠主账号与状态维护", "前台接诊员、宠物医生、护理接种员、宠物主人账号与状态维护")
text = text.replace("- 前台接诊员：场馆档案、票种、排期、活动、公告和统计看板维护", "- 前台接诊员：宠主档案、宠物档案、接诊预约、费用结算和统计看板维护")
text = text.replace("- 宠物医生：宠物医生档案、接诊排班、讲解预约和宠主评价处理", "- 宠物医生：医生档案、接诊记录、疫苗计划和随访记录维护")
text = text.replace("- 护理接种员：接诊预约、疫苗接种和随访记录维护", "- 护理接种员：疫苗计划、疫苗接种、药品库存和随访记录维护")
text = text.replace("- 宠物主人：接诊预约、讲解预约和评价反馈提交", "- 宠物主人：宠物档案、接诊预约、疫苗提醒、随访记录和费用结算查询")
text = text.replace("按 ADMIN / MANAGER / GUIDE / CHECKER / VISITOR 收口页面操作权限", "按 ADMIN / RECEPTION / DOCTOR / NURSE / OWNER 收口页面操作权限")
text = text.replace('role_list = ", ".join(f"\'{role}\'" for role in ["ADMIN", "RECEPTION", "DOCTOR", "NURSE", "OWNER"])', 'role_list = ", ".join(f"\'{role}\'" for role in roles)')
text = text.replace('role_list = ", ".join(f"\'{r}\'" for r in ["ADMIN", "RECEPTION", "DOCTOR", "NURSE", "OWNER"])', 'role_list = ", ".join(f"\'{r}\'" for r in roles)')
text = text.replace("- 源码不再残留 `com.p154`、`BizRecord` 泛化模板命名", "- 源码不再残留批量脚手架的通用包名与通用业务命名")

dst.write_text(text, encoding="utf-8")
print(dst)
