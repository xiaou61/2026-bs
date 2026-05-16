from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_159.py"
dst = ROOT / "scripts" / "develop_160.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "159-backend": "160-backend",
    "159-frontend": "160-frontend",
    "medicalwaste": "clubfinance",
    "MedicalWasteApplication": "ClubFinanceApplication",
    "medical-waste-manifest-159": "campus-club-budget-160",
    "medical_waste_159": "club_finance_160",
    "medicalwaste:token:": "clubfinance:token:",
    "medical-waste-manifest-159-secret": "campus-club-budget-160-secret",
    "医疗废弃物收运联单与闭环监管系统": "校园社团活动预算报销与物资借用系统",
    "医废联单 159": "社团财资 160",
    "医废监管中心": "校团委社团中心",
    "医疗废弃物分类收集、转运交接与处置监管场景": "高校学生社团活动预算、报销票据与物资借还协同场景",
    "医疗监管": "校园社团",
    "医废来源、废物类别、包装赋码、收集预约、称重记录、暂存交接、转运联单、运输轨迹、处置确认、异常追溯和监管抽查的一体化后台管理能力": "社团档案、成员档案、活动立项、预算申请、预算明细、审批记录、报销申请、票据归档、物资台账、物资借用和归还验收的一体化后台管理能力",
    "分类收集、称重赋码、转运联单、处置确认一体化监管": "活动立项、预算报销、票据归档、物资借还一体化管理",
    "分类收集、称重赋码、转运联单、处置确认": "活动立项、预算报销、票据归档、物资借还",
    "3159": "3160",
    "8159": "8160",
    "database: 62": "database: 63",
    "139159": "139160",
    "waste159.local": "club160.local",
    "159-": "160-",
    "159": "160",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("UNION", "团委审核员", "union", "/budget"),
    ("CLUB", "社团负责人", "club", "/activity"),
    ("TREASURER", "社团财务员", "treasurer", "/reimbursement"),
    ("WAREHOUSE", "物资管理员", "warehouse", "/asset"),
    ("MEMBER", "社团成员", "member", "/borrow"),
]'''

modules_block = '''MODULES = [
    ("ClubProfile", "club_profile", "club", "ClubProfile", "社团档案", "社团编号、社团名称、社团类型、指导老师、成立时间和社团状态维护", ["ADMIN", "UNION"], ["社团编号", "社团名称", "社团类型", "指导老师", "成立时间", "社团状态", "社团说明"]),
    ("MemberProfile", "member_profile", "member", "MemberProfile", "成员档案", "成员编号、成员姓名、所属社团、负责人、入社时间和成员状态维护", ["ADMIN", "UNION", "CLUB"], ["成员编号", "成员姓名", "所属社团", "负责人", "入社时间", "成员状态", "成员说明"]),
    ("ActivityPlan", "activity_plan", "activity", "ActivityPlan", "活动立项", "活动编号、活动名称、活动类型、负责人、活动时间和立项状态维护", ["ADMIN", "UNION", "CLUB", "MEMBER"], ["活动编号", "活动名称", "活动类型", "负责人", "活动时间", "立项状态", "活动说明"]),
    ("BudgetRequest", "budget_request", "budget", "BudgetRequest", "预算申请", "预算编号、活动名称、预算类型、申请人、申请时间和预算状态维护", ["ADMIN", "UNION", "CLUB", "TREASURER"], ["预算编号", "活动名称", "预算类型", "申请人", "申请时间", "预算状态", "预算说明"]),
    ("BudgetLineItem", "budget_line_item", "lineitem", "BudgetLineItem", "预算明细", "明细编号、费用项目、费用类型、填报人、填报时间和明细状态维护", ["ADMIN", "UNION", "CLUB", "TREASURER"], ["明细编号", "费用项目", "费用类型", "填报人", "填报时间", "明细状态", "明细说明"]),
    ("ApprovalRecord", "approval_record", "approval", "ApprovalRecord", "审批记录", "审批编号、审批事项、审批类型、审批人、审批时间和审批状态维护", ["ADMIN", "UNION"], ["审批编号", "审批事项", "审批类型", "审批人", "审批时间", "审批状态", "审批说明"]),
    ("ReimbursementClaim", "reimbursement_claim", "reimbursement", "ReimbursementClaim", "报销申请", "报销编号、报销活动、报销类型、申请人、申请时间和报销状态维护", ["ADMIN", "UNION", "CLUB", "TREASURER", "MEMBER"], ["报销编号", "报销活动", "报销类型", "申请人", "申请时间", "报销状态", "报销说明"]),
    ("ReceiptArchive", "receipt_archive", "receipt", "ReceiptArchive", "票据归档", "票据编号、票据事项、票据类型、归档人、归档时间和票据状态维护", ["ADMIN", "UNION", "TREASURER"], ["票据编号", "票据事项", "票据类型", "归档人", "归档时间", "票据状态", "票据说明"]),
    ("MaterialAsset", "material_asset", "asset", "MaterialAsset", "物资台账", "物资编号、物资名称、物资类型、保管人、入库时间和物资状态维护", ["ADMIN", "UNION", "WAREHOUSE"], ["物资编号", "物资名称", "物资类型", "保管人", "入库时间", "物资状态", "物资说明"]),
    ("BorrowRequest", "borrow_request", "borrow", "BorrowRequest", "物资借用", "借用编号、借用物资、借用类型、借用人、借用时间和借用状态维护", ["ADMIN", "UNION", "CLUB", "WAREHOUSE", "MEMBER"], ["借用编号", "借用物资", "借用类型", "借用人", "借用时间", "借用状态", "借用说明"]),
    ("ReturnInspection", "return_inspection", "returncheck", "ReturnInspection", "归还验收", "验收编号、归还物资、验收类型、验收人、验收时间和验收状态维护", ["ADMIN", "UNION", "WAREHOUSE", "CLUB"], ["验收编号", "归还物资", "验收类型", "验收人", "验收时间", "验收状态", "验收说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "UNION"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

old_all = "['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR']"
new_all = "['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'WAREHOUSE', 'MEMBER']"
text = text.replace(old_all, new_all)
text = text.replace('["ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER", "DISPOSAL", "REGULATOR"]', '["ADMIN", "UNION", "CLUB", "TREASURER", "WAREHOUSE", "MEMBER"]')
text = text.replace("<span>admin</span><span>hospital</span><span>collector</span><span>transporter</span><span>disposal</span><span>regulator</span>", "<span>admin</span><span>union</span><span>club</span><span>treasurer</span><span>warehouse</span><span>member</span>")
text = text.replace("HOSPITAL: '/waste', COLLECTOR: '/order', TRANSPORTER: '/manifest', DISPOSAL: '/disposal', REGULATOR: '/audit'", "UNION: '/budget', CLUB: '/activity', TREASURER: '/reimbursement', WAREHOUSE: '/asset', MEMBER: '/borrow'")
text = text.replace("HOSPITAL: '/waste',\n  COLLECTOR: '/order',\n  TRANSPORTER: '/manifest',\n  DISPOSAL: '/disposal',\n  REGULATOR: '/audit'", "UNION: '/budget',\n  CLUB: '/activity',\n  TREASURER: '/reimbursement',\n  WAREHOUSE: '/asset',\n  MEMBER: '/borrow'")
text = text.replace("医院交接员", "团委审核员")
text = text.replace("收运调度员", "社团负责人")
text = text.replace("转运司机", "社团财务员")
text = text.replace("处置厂审核员", "物资管理员")
text = text.replace("监管人员", "社团成员")
text = text.replace("已封包", "已立项")
text = text.replace("已转运", "已报销")
text = text.replace("已处置", "已归还")
text = text.replace("近7日收运与处置趋势", "近7日预算与借用趋势")
text = text.replace('data.put("pie", Arrays.asList(map("待收集", 32), map("转运中", 58), map("待处置", 25), map("闭环完成", 8)));', 'data.put("pie", Arrays.asList(map("待审批", 32), map("预算通过", 58), map("报销处理中", 25), map("物资已归还", 8)));')
text = text.replace("医废来源、包装赋码、暂存交接和院内出库维护", "社团档案、活动立项、预算审批和经费使用复核")
text = text.replace("收集预约、称重记录、暂存交接和转运联单调度", "活动立项、预算申请、报销申请和成员协同维护")
text = text.replace("转运联单、运输轨迹、车辆交接和异常上报维护", "预算明细、报销申请、票据归档和费用核对维护")
text = text.replace("处置确认、入厂复核、处置批次和闭环回执维护", "物资台账、物资借用、归还验收和库存状态维护")
text = text.replace("监管抽查、异常追溯、联单链路和闭环报表查看", "活动申请、物资借用、归还反馈和报销进度查看")
text = text.replace("按 ADMIN / HOSPITAL / COLLECTOR / TRANSPORTER / DISPOSAL / REGULATOR 收口页面操作权限", "按 ADMIN / UNION / CLUB / TREASURER / WAREHOUSE / MEMBER 收口页面操作权限")
text = text.replace("- hospital/123456\n- collector/123456\n- transporter/123456\n- disposal/123456\n- regulator/123456", "- union/123456\n- club/123456\n- treasurer/123456\n- warehouse/123456\n- member/123456")
text = text.replace("团委审核员、社团负责人、社团财务员、物资管理员、社团成员账号与状态维护", "团委审核员、社团负责人、社团财务员、物资管理员、社团成员账号与状态维护")

dst.write_text(text, encoding="utf-8")
print(dst)
