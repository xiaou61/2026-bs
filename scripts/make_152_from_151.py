from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_151.py"
dst = ROOT / "scripts" / "develop_152.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "151-backend": "152-backend",
    "151-frontend": "152-frontend",
    "culturevenue": "hazardwork",
    "CultureVenueApplication": "HazardWorkApplication",
    "culture-venue-ticket-151": "hazard-work-permit-152",
    "culture_venue_151": "hazard_work_152",
    "culturevenue:token:": "hazardwork:token:",
    "culture-venue-ticket-151-secret": "hazard-work-permit-152-secret",
    "文旅场馆讲解预约与票务核销管理平台": "工厂危险作业审批与监护巡检管理系统",
    "文旅场馆 151": "危险作业 152",
    "文旅场馆运营中心": "工厂安全作业中心",
    "文旅场馆": "危险作业",
    "文旅服务": "工业安全",
    "票务预约、讲解排期、扫码核销、客流统计": "作业票、审批链路、监护记录、隐患闭环",
    "票务预约、讲解排期、扫码核销、客流统计一体化管理": "作业票审批、现场监护、气体检测、隐患整改闭环管理",
    "3151": "3152",
    "8151": "8152",
    "database: 54": "database: 55",
    "139151": "139152",
    "151-": "152-",
    "151": "152",
    "culture152.local": "hazard152.local",
    "culture151.local": "hazard152.local",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("SAFETY", "安全主管", "safety", "/dashboard"),
    ("APPROVER", "审批负责人", "approver", "/approval"),
    ("GUARDIAN", "现场监护人", "guardian", "/monitor"),
    ("WORKER", "作业申请人", "worker", "/work-ticket"),
]'''

modules_block = '''MODULES = [
    ("WorkArea", "work_area", "area", "WorkArea", "作业区域", "区域编码、区域名称、危险等级、负责人、管控时段和区域状态维护", ["ADMIN", "SAFETY"], ["区域编码", "区域名称", "危险等级", "负责人", "管控时段", "区域状态", "管控说明"]),
    ("HazardSource", "hazard_source", "hazard", "HazardSource", "风险源台账", "风险编号、风险源名称、风险类型、责任人、巡检周期和管控状态维护", ["ADMIN", "SAFETY", "GUARDIAN"], ["风险编号", "风险源名称", "风险类型", "责任人", "巡检周期", "管控状态", "风险说明"]),
    ("WorkerProfile", "worker_profile", "worker", "WorkerProfile", "作业人员档案", "工号、姓名、资质等级、所属班组、有效期和资质状态维护", ["ADMIN", "SAFETY", "WORKER"], ["工号", "姓名", "资质等级", "所属班组", "有效期", "资质状态", "资质说明"]),
    ("WorkPermit", "work_permit", "work-ticket", "WorkPermit", "作业票申请", "作业票号、作业项目、作业类型、申请人、作业时间和申请状态维护", ["ADMIN", "SAFETY", "WORKER"], ["作业票号", "作业项目", "作业类型", "申请人", "作业时间", "申请状态", "作业说明"]),
    ("PermitApproval", "permit_approval", "approval", "PermitApproval", "审批链路", "审批编号、作业票号、审批节点、审批人、审批时间和审批状态维护", ["ADMIN", "SAFETY", "APPROVER"], ["审批编号", "作业票号", "审批节点", "审批人", "审批时间", "审批状态", "审批意见"]),
    ("SafetyBriefing", "safety_briefing", "briefing", "SafetyBriefing", "安全交底", "交底编号、作业项目、交底类型、交底人、交底时间和确认状态维护", ["ADMIN", "SAFETY", "GUARDIAN", "WORKER"], ["交底编号", "作业项目", "交底类型", "交底人", "交底时间", "确认状态", "交底内容"]),
    ("GuardianAssignment", "guardian_assignment", "guardian", "GuardianAssignment", "监护安排", "安排编号、作业票号、监护类型、监护人、监护时段和监护状态维护", ["ADMIN", "SAFETY", "GUARDIAN"], ["安排编号", "作业票号", "监护类型", "监护人", "监护时段", "监护状态", "安排说明"]),
    ("MonitorRecord", "monitor_record", "monitor", "MonitorRecord", "监护记录", "记录编号、作业票号、监护项目、记录人、记录时间和现场状态维护", ["ADMIN", "SAFETY", "GUARDIAN"], ["记录编号", "作业票号", "监护项目", "记录人", "记录时间", "现场状态", "监护说明"]),
    ("HiddenDanger", "hidden_danger", "danger", "HiddenDanger", "隐患闭环", "隐患编号、隐患位置、隐患类型、发现人、整改期限和整改状态维护", ["ADMIN", "SAFETY", "GUARDIAN"], ["隐患编号", "隐患位置", "隐患类型", "发现人", "整改期限", "整改状态", "隐患描述"]),
    ("GasDetection", "gas_detection", "gas", "GasDetection", "气体检测", "检测编号、作业票号、检测类型、检测人、检测时间和检测状态维护", ["ADMIN", "SAFETY", "GUARDIAN"], ["检测编号", "作业票号", "检测类型", "检测人", "检测时间", "检测状态", "检测结果"]),
    ("EmergencyPlan", "emergency_plan", "plan", "EmergencyPlan", "应急预案", "预案编号、预案名称、适用场景、负责人、演练时间和启用状态维护", ["ADMIN", "SAFETY"], ["预案编号", "预案名称", "适用场景", "负责人", "演练时间", "启用状态", "预案说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "SAFETY"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

text = text.replace("['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR']", "['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER']")
text = text.replace('["ADMIN", "MANAGER", "GUIDE", "CHECKER", "VISITOR"]', '["ADMIN", "SAFETY", "APPROVER", "GUARDIAN", "WORKER"]')
text = text.replace("ADMIN: '/dashboard',\n  MANAGER: '/dashboard',\n  GUIDE: '/schedule',\n  CHECKER: '/verification',\n  VISITOR: '/ticket-order'", "ADMIN: '/dashboard',\n  SAFETY: '/dashboard',\n  APPROVER: '/approval',\n  GUARDIAN: '/monitor',\n  WORKER: '/work-ticket'")
text = text.replace("<span>admin</span><span>manager</span><span>guide</span><span>checker</span><span>visitor</span>", "<span>admin</span><span>safety</span><span>approver</span><span>guardian</span><span>worker</span>")
text = text.replace("预约游客", "作业申请人")
text = text.replace("票务核销员", "现场监护人")
text = text.replace("讲解员", "审批负责人")
text = text.replace("场馆主管", "安全主管")
text = text.replace("场馆/票种/活动/公告主要由 `ADMIN / MANAGER` 管理，讲解档案和排期支持 `GUIDE`，核销与客流支持 `CHECKER`，预约与评价支持 `VISITOR`", "作业区域、风险源、应急预案主要由 `ADMIN / SAFETY` 管理，审批链路支持 `APPROVER`，监护安排、监护记录、气体检测、隐患闭环支持 `GUARDIAN`，作业票申请和安全交底支持 `WORKER`")
text = text.replace("博物馆、非遗馆、展览馆等危险作业，提供作业票、审批链路、监护记录、隐患闭环、游客评价和公告发布的一体化后台管理能力。", "化工、制造、能源等工厂危险作业场景，提供作业票申请、分级审批、安全交底、现场监护、气体检测和隐患整改闭环的一体化后台管理能力。")
text = text.replace("游客评价", "隐患闭环")
text = text.replace("公告发布", "应急预案")
text = text.replace("游客", "作业人员")
text = text.replace("首页看板展示票务预约、讲解排期、扫码核销、客流统计指标和状态分布", "首页看板展示作业票、审批链路、监护记录、隐患闭环指标和状态分布")
text = text.replace("- 安全主管：场馆档案、票种、排期、活动、公告和统计看板维护", "- 安全主管：作业区域、风险源台账、作业票、隐患闭环、应急预案和统计看板维护")
text = text.replace("- 审批负责人：审批负责人档案、讲解排期、讲解预约和隐患闭环处理", "- 审批负责人：作业票审批、审批链路流转和审批意见维护")
text = text.replace("- 现场监护人：票务预约、扫码核销和客流统计维护", "- 现场监护人：监护安排、监护记录、气体检测和隐患上报维护")
text = text.replace("- 作业申请人：票务预约、讲解预约和评价反馈提交", "- 作业申请人：作业票申请、安全交底确认和整改反馈提交")

dst.write_text(text, encoding="utf-8")
print(dst)
