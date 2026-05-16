from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_158.py"
dst = ROOT / "scripts" / "develop_159.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "158-backend": "159-backend",
    "158-frontend": "159-frontend",
    "trainingrefund": "medicalwaste",
    "TrainingRefundApplication": "MedicalWasteApplication",
    "training-consumption-refund-158": "medical-waste-manifest-159",
    "training_refund_158": "medical_waste_159",
    "trainingrefund:token:": "medicalwaste:token:",
    "training-consumption-refund-158-secret": "medical-waste-manifest-159-secret",
    "校外培训机构课消统计与退费审批系统": "医疗废弃物收运联单与闭环监管系统",
    "教培课消 158": "医废联单 159",
    "教培运营中心": "医废监管中心",
    "校外培训机构教务与财务协同场景": "医疗废弃物分类收集、转运交接与处置监管场景",
    "教培管理": "医疗监管",
    "校区档案、课程产品、学员档案、教师档案、班级台账、排课计划、上课考勤、课消记录、退费申请、退费审批和财务流水的一体化后台管理能力": "医废来源、废物类别、包装赋码、收集预约、称重记录、暂存交接、转运联单、运输轨迹、处置确认、异常追溯和监管抽查的一体化后台管理能力",
    "排课签到、课消统计、退费审批、财务流水一体化管理": "分类收集、称重赋码、转运联单、处置确认一体化监管",
    "排课签到、课消统计、退费审批、财务流水": "分类收集、称重赋码、转运联单、处置确认",
    "3158": "3159",
    "8158": "8159",
    "database: 61": "database: 62",
    "139158": "139159",
    "edu158.local": "waste159.local",
    "158-": "159-",
    "158": "159",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("HOSPITAL", "医院交接员", "hospital", "/waste"),
    ("COLLECTOR", "收运调度员", "collector", "/order"),
    ("TRANSPORTER", "转运司机", "transporter", "/manifest"),
    ("DISPOSAL", "处置厂审核员", "disposal", "/disposal"),
    ("REGULATOR", "监管人员", "regulator", "/audit"),
]'''

modules_block = '''MODULES = [
    ("WasteSource", "waste_source", "waste", "WasteSource", "医废来源", "来源编号、来源名称、来源类型、责任人、启用时间和来源状态维护", ["ADMIN", "HOSPITAL", "REGULATOR"], ["来源编号", "来源名称", "来源类型", "责任人", "启用时间", "来源状态", "来源说明"]),
    ("WasteCategory", "waste_category", "category", "WasteCategory", "废物类别", "类别编号、类别名称、危险类型、管理人、启用时间和类别状态维护", ["ADMIN", "HOSPITAL", "COLLECTOR", "REGULATOR"], ["类别编号", "类别名称", "危险类型", "管理人", "启用时间", "类别状态", "类别说明"]),
    ("WastePackage", "waste_package", "package", "WastePackage", "包装赋码", "包装编号、废物名称、包装类型、交接人、封包时间和包装状态维护", ["ADMIN", "HOSPITAL", "COLLECTOR"], ["包装编号", "废物名称", "包装类型", "交接人", "封包时间", "包装状态", "包装说明"]),
    ("CollectionOrder", "collection_order", "order", "CollectionOrder", "收集预约", "预约编号、预约机构、收集类型、联系人、预约时间和预约状态维护", ["ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER"], ["预约编号", "预约机构", "收集类型", "联系人", "预约时间", "预约状态", "预约说明"]),
    ("WeighingRecord", "weighing_record", "weighing", "WeighingRecord", "称重记录", "称重编号、医废批次、称重类型、称重人、称重时间和称重状态维护", ["ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER"], ["称重编号", "医废批次", "称重类型", "称重人", "称重时间", "称重状态", "称重说明"]),
    ("StorageHandover", "storage_handover", "storage", "StorageHandover", "暂存交接", "交接编号、暂存点位、交接类型、交接人、交接时间和交接状态维护", ["ADMIN", "HOSPITAL", "COLLECTOR", "REGULATOR"], ["交接编号", "暂存点位", "交接类型", "交接人", "交接时间", "交接状态", "交接说明"]),
    ("TransferManifest", "transfer_manifest", "manifest", "TransferManifest", "转运联单", "联单编号、医废批次、转运类型、承运人、转运时间和联单状态维护", ["ADMIN", "COLLECTOR", "TRANSPORTER", "REGULATOR"], ["联单编号", "医废批次", "转运类型", "承运人", "转运时间", "联单状态", "联单说明"]),
    ("TransportTrack", "transport_track", "track", "TransportTrack", "运输轨迹", "轨迹编号、转运车辆、轨迹类型、驾驶员、定位时间和轨迹状态维护", ["ADMIN", "COLLECTOR", "TRANSPORTER", "REGULATOR"], ["轨迹编号", "转运车辆", "轨迹类型", "驾驶员", "定位时间", "轨迹状态", "轨迹说明"]),
    ("DisposalConfirm", "disposal_confirm", "disposal", "DisposalConfirm", "处置确认", "处置编号、处置批次、处置方式、确认人、处置时间和处置状态维护", ["ADMIN", "TRANSPORTER", "DISPOSAL", "REGULATOR"], ["处置编号", "处置批次", "处置方式", "确认人", "处置时间", "处置状态", "处置说明"]),
    ("ExceptionTrace", "exception_trace", "exception", "ExceptionTrace", "异常追溯", "异常编号、异常对象、异常类型、处置人、发现时间和异常状态维护", ["ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER", "DISPOSAL", "REGULATOR"], ["异常编号", "异常对象", "异常类型", "处置人", "发现时间", "异常状态", "异常说明"]),
    ("ComplianceAudit", "compliance_audit", "audit", "ComplianceAudit", "监管抽查", "抽查编号、抽查对象、抽查类型、监管人、抽查时间和抽查状态维护", ["ADMIN", "REGULATOR"], ["抽查编号", "抽查对象", "抽查类型", "监管人", "抽查时间", "抽查状态", "抽查说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "REGULATOR"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

old_all = "['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT']"
new_all = "['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR']"
text = text.replace(old_all, new_all)
text = text.replace('["ADMIN", "PRINCIPAL", "ACADEMIC", "TEACHER", "FINANCE", "PARENT"]', '["ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER", "DISPOSAL", "REGULATOR"]')
text = text.replace("<span>admin</span><span>principal</span><span>academic</span><span>teacher</span><span>finance</span><span>parent</span>", "<span>admin</span><span>hospital</span><span>collector</span><span>transporter</span><span>disposal</span><span>regulator</span>")
text = text.replace("PRINCIPAL: '/dashboard', ACADEMIC: '/schedule', TEACHER: '/attendance', FINANCE: '/refund', PARENT: '/consumption'", "HOSPITAL: '/waste', COLLECTOR: '/order', TRANSPORTER: '/manifest', DISPOSAL: '/disposal', REGULATOR: '/audit'")
text = text.replace("PRINCIPAL: '/dashboard',\n  ACADEMIC: '/schedule',\n  TEACHER: '/attendance',\n  FINANCE: '/refund',\n  PARENT: '/consumption'", "HOSPITAL: '/waste',\n  COLLECTOR: '/order',\n  TRANSPORTER: '/manifest',\n  DISPOSAL: '/disposal',\n  REGULATOR: '/audit'")
text = text.replace("校区校长", "医院交接员")
text = text.replace("教务主管", "收运调度员")
text = text.replace("任课老师", "转运司机")
text = text.replace("财务审核员", "处置厂审核员")
text = text.replace("学员家长", "监管人员")
text = text.replace("已排课", "已封包")
text = text.replace("已消课", "已转运")
text = text.replace("已退费", "已处置")
text = text.replace("近7日课消与退费趋势", "近7日收运与处置趋势")
text = text.replace('data.put("pie", Arrays.asList(map("待上课", 32), map("已消课", 58), map("退费审批中", 25), map("财务已结清", 8)));', 'data.put("pie", Arrays.asList(map("待收集", 32), map("转运中", 58), map("待处置", 25), map("闭环完成", 8)));')
text = text.replace('data.put("pie", Arrays.asList(map("待上课", 32), map("已转运", 58), map("退费审批中", 25), map("财务已结清", 8)));', 'data.put("pie", Arrays.asList(map("待收集", 32), map("转运中", 58), map("待处置", 25), map("闭环完成", 8)));')
text = text.replace("校区档案、课程产品、班级台账、课消统计和财务总览维护", "医废来源、包装赋码、暂存交接和院内出库维护")
text = text.replace("排课计划、上课考勤、课消记录和调课补课处理", "收集预约、称重记录、暂存交接和转运联单调度")
text = text.replace("上课考勤、课堂反馈、课消确认和学员沟通维护", "转运联单、运输轨迹、车辆交接和异常上报维护")
text = text.replace("退费审批、财务流水、收入核对和退款结算维护", "处置确认、入厂复核、处置批次和闭环回执维护")
text = text.replace("课消记录、退费申请、剩余课时和财务流水查看", "监管抽查、异常追溯、联单链路和闭环报表查看")
text = text.replace("按 ADMIN / PRINCIPAL / ACADEMIC / TEACHER / FINANCE / PARENT 收口页面操作权限", "按 ADMIN / HOSPITAL / COLLECTOR / TRANSPORTER / DISPOSAL / REGULATOR 收口页面操作权限")
text = text.replace("- principal/123456\n- academic/123456\n- teacher/123456\n- finance/123456\n- parent/123456", "- hospital/123456\n- collector/123456\n- transporter/123456\n- disposal/123456\n- regulator/123456")
text = text.replace("医院交接员、收运调度员、转运司机、处置厂审核员、监管人员账号与状态维护", "医院交接员、收运调度员、转运司机、处置厂审核员、监管人员账号与状态维护")

dst.write_text(text, encoding="utf-8")
print(dst)
