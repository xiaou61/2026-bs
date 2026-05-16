from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_151.py"
dst = ROOT / "scripts" / "develop_153.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "151-backend": "153-backend",
    "151-frontend": "153-frontend",
    "culturevenue": "campusresale",
    "CultureVenueApplication": "CampusResaleApplication",
    "culture-venue-ticket-151": "campus-resale-credit-153",
    "culture_venue_151": "campus_resale_153",
    "culturevenue:token:": "campusresale:token:",
    "culture-venue-ticket-151-secret": "campus-resale-credit-153-secret",
    "文旅场馆讲解预约与票务核销管理平台": "校园二手物品寄卖与信用评价系统",
    "文旅场馆 151": "校园寄卖 153",
    "文旅场馆运营中心": "校园二手寄卖中心",
    "文旅场馆": "校园寄卖",
    "文旅服务": "校园生活",
    "票务预约、讲解排期、扫码核销、客流统计": "寄卖订单、担保交易、评价体系、违约处理",
    "票务预约、讲解排期、扫码核销、客流统计一体化管理": "寄卖上架、担保交易、交接确认、信用评价一体化管理",
    "3151": "3153",
    "8151": "8153",
    "database: 54": "database: 56",
    "139151": "139153",
    "151-": "153-",
    "151": "153",
    "culture153.local": "resale153.local",
    "culture151.local": "resale153.local",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("OPERATOR", "平台运营", "operator", "/dashboard"),
    ("SELLER", "寄卖学生", "seller", "/consignment"),
    ("BUYER", "购买学生", "buyer", "/order"),
    ("ARBITER", "信用仲裁员", "arbiter", "/complaint"),
]'''

modules_block = '''MODULES = [
    ("ItemCategory", "item_category", "category", "ItemCategory", "物品分类", "分类编码、分类名称、适用品类、维护人、更新时间和启用状态维护", ["ADMIN", "OPERATOR"], ["分类编码", "分类名称", "适用品类", "维护人", "更新时间", "启用状态", "分类说明"]),
    ("StudentProfile", "student_profile", "student", "StudentProfile", "学生档案", "学号、姓名、学院班级、联系方式、认证时间和账号状态维护", ["ADMIN", "OPERATOR"], ["学号", "姓名", "学院班级", "联系方式", "认证时间", "账号状态", "认证说明"]),
    ("ConsignmentItem", "consignment_item", "consignment", "ConsignmentItem", "寄卖物品", "寄卖编号、物品名称、物品类型、寄卖人、上架时间和寄卖状态维护", ["ADMIN", "OPERATOR", "SELLER"], ["寄卖编号", "物品名称", "物品类型", "寄卖人", "上架时间", "寄卖状态", "物品描述"]),
    ("ItemAudit", "item_audit", "audit", "ItemAudit", "上架审核", "审核编号、寄卖物品、审核类型、审核人、审核时间和审核状态维护", ["ADMIN", "OPERATOR"], ["审核编号", "寄卖物品", "审核类型", "审核人", "审核时间", "审核状态", "审核意见"]),
    ("EscrowOrder", "escrow_order", "order", "EscrowOrder", "担保订单", "订单编号、寄卖物品、交易类型、买家、下单时间和订单状态维护", ["ADMIN", "OPERATOR", "SELLER", "BUYER"], ["订单编号", "寄卖物品", "交易类型", "买家", "下单时间", "订单状态", "订单说明"]),
    ("PaymentRecord", "payment_record", "payment", "PaymentRecord", "担保支付", "支付编号、关联订单、支付渠道、付款人、支付时间和支付状态维护", ["ADMIN", "OPERATOR", "BUYER"], ["支付编号", "关联订单", "支付渠道", "付款人", "支付时间", "支付状态", "支付说明"]),
    ("HandoverRecord", "handover_record", "handover", "HandoverRecord", "交接确认", "交接编号、关联订单、交接方式、确认人、交接时间和确认状态维护", ["ADMIN", "OPERATOR", "SELLER", "BUYER"], ["交接编号", "关联订单", "交接方式", "确认人", "交接时间", "确认状态", "交接说明"]),
    ("CreditEvaluation", "credit_evaluation", "credit", "CreditEvaluation", "信用评价", "评价编号、评价对象、评价类型、评价人、评价时间和处理状态维护", ["ADMIN", "OPERATOR", "SELLER", "BUYER", "ARBITER"], ["评价编号", "评价对象", "评价类型", "评价人", "评价时间", "处理状态", "评价内容"]),
    ("BreachRecord", "breach_record", "breach", "BreachRecord", "违约记录", "违约编号、关联订单、违约类型、责任人、记录时间和处置状态维护", ["ADMIN", "OPERATOR", "ARBITER"], ["违约编号", "关联订单", "违约类型", "责任人", "记录时间", "处置状态", "违约说明"]),
    ("ComplaintTicket", "complaint_ticket", "complaint", "ComplaintTicket", "纠纷申诉", "申诉编号、关联订单、申诉类型、申诉人、提交时间和处理状态维护", ["ADMIN", "OPERATOR", "ARBITER", "SELLER", "BUYER"], ["申诉编号", "关联订单", "申诉类型", "申诉人", "提交时间", "处理状态", "申诉内容"]),
    ("PlatformNotice", "platform_notice", "notice", "PlatformNotice", "平台公告", "公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护", ["ADMIN", "OPERATOR"], ["公告编号", "公告标题", "公告类型", "发布人", "发布时间", "公告状态", "公告内容"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "OPERATOR"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

text = text.replace("['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR']", "['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER']")
text = text.replace('["ADMIN", "MANAGER", "GUIDE", "CHECKER", "VISITOR"]', '["ADMIN", "OPERATOR", "SELLER", "BUYER", "ARBITER"]')
text = text.replace("const home = { ADMIN: '/dashboard', MANAGER: '/dashboard', GUIDE: '/schedule', CHECKER: '/verification', VISITOR: '/ticket-order' }", "const home = { ADMIN: '/dashboard', OPERATOR: '/dashboard', SELLER: '/consignment', BUYER: '/order', ARBITER: '/complaint' }")
text = text.replace("ADMIN: '/dashboard',\n  MANAGER: '/dashboard',\n  GUIDE: '/schedule',\n  CHECKER: '/verification',\n  VISITOR: '/ticket-order'", "ADMIN: '/dashboard',\n  OPERATOR: '/dashboard',\n  SELLER: '/consignment',\n  BUYER: '/order',\n  ARBITER: '/complaint'")
text = text.replace("<span>admin</span><span>manager</span><span>guide</span><span>checker</span><span>visitor</span>", "<span>admin</span><span>operator</span><span>seller</span><span>buyer</span><span>arbiter</span>")
text = text.replace("场馆主管", "平台运营")
text = text.replace("讲解员", "寄卖学生")
text = text.replace("票务核销员", "购买学生")
text = text.replace("预约游客", "信用仲裁员")
text = text.replace("游客", "学生")
text = text.replace("博物馆、非遗馆、展览馆等校园寄卖，提供寄卖订单、担保交易、评价体系、违约处理、学生评价和公告发布的一体化后台管理能力。", "校园二手物品交易场景，提供寄卖上架、上架审核、担保订单、支付交接、信用评价、违约记录和纠纷申诉的一体化后台管理能力。")
text = text.replace("首页看板展示票务预约、讲解排期、扫码核销、客流统计指标和状态分布", "首页看板展示寄卖物品、担保订单、信用评价、违约处理指标和状态分布")
text = text.replace("票务预约", "寄卖订单")
text = text.replace("讲解排期", "担保交易")
text = text.replace("扫码核销", "评价体系")
text = text.replace("客流统计", "违约处理")
text = text.replace('data.put("pie", Arrays.asList(map("预约待确认", 32), map("已核销入馆", 58), map("讲解服务中", 25), map("客流预警", 8)));', 'data.put("pie", Arrays.asList(map("待上架审核", 32), map("担保交易中", 58), map("交接确认中", 25), map("纠纷处理中", 8)));')
text = text.replace("平台运营、寄卖学生、核销员、学生账号与状态维护", "平台运营、寄卖学生、购买学生、信用仲裁员账号与状态维护")
text = text.replace("- 平台运营：场馆档案、票种、排期、活动、公告和统计看板维护", "- 平台运营：物品分类、学生档案、上架审核、担保订单、公告和统计看板维护")
text = text.replace("- 寄卖学生：寄卖学生档案、担保交易、讲解预约和学生评价处理", "- 寄卖学生：寄卖物品、担保订单、交接确认和信用评价维护")
text = text.replace("- 购买学生：寄卖订单、评价体系和违约处理维护", "- 购买学生：担保订单、担保支付、交接确认和信用评价维护")
text = text.replace("- 信用仲裁员：寄卖订单、讲解预约和评价反馈提交", "- 信用仲裁员：纠纷申诉、违约记录和信用仲裁处理")
text = text.replace("按 ADMIN / MANAGER / GUIDE / CHECKER / VISITOR 收口页面操作权限", "按 ADMIN / OPERATOR / SELLER / BUYER / ARBITER 收口页面操作权限")

dst.write_text(text, encoding="utf-8")
print(dst)
