from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
src = ROOT / "scripts" / "develop_156.py"
dst = ROOT / "scripts" / "develop_157.py"

text = src.read_text(encoding="utf-8")

replacements = {
    "156-backend": "157-backend",
    "156-frontend": "157-frontend",
    "dormenergy": "logisticspark",
    "DormEnergyApplication": "LogisticsParkApplication",
    "dorm-energy-ranking-156": "logistics-yard-gate-157",
    "dorm_energy_156": "logistics_park_157",
    "dormenergy:token:": "logisticspark:token:",
    "dorm-energy-ranking-156-secret": "logistics-yard-gate-157-secret",
    "校园宿舍能耗监测与节能排名系统": "物流园区车辆入场预约与道口调度平台",
    "宿舍能耗 156": "园区调度 157",
    "后勤能源中心": "物流园区调度中心",
    "高校宿舍能源管理场景": "物流园区车辆入场与月台调度场景",
    "智慧校园": "智慧物流",
    "宿舍楼栋、宿舍房间、能耗表计、用电读数、能耗账单、预警策略、异常预警、节能任务、节能排行和巡查记录的一体化后台管理能力": "承运商预约、车辆档案、司机档案、时段计划、门岗核验、排队叫号、道口资源、道口分配、装卸任务和周转统计的一体化后台管理能力",
    "能耗监测、异常预警、节能任务、节能排名一体化管理": "入场预约、门岗核验、排队叫号、道口调度一体化管理",
    "能耗监测、异常预警、节能任务、节能排名": "入场预约、门岗核验、排队叫号、道口调度",
    "3156": "3157",
    "8156": "8157",
    "database: 59": "database: 60",
    "139156": "139157",
    "energy156.local": "park157.local",
    "156-": "157-",
    "156": "157",
}

for old, new in replacements.items():
    text = text.replace(old, new)

roles_block = '''ROLES = [
    ("ADMIN", "系统管理员", "admin", "/dashboard"),
    ("DISPATCHER", "调度主管", "dispatcher", "/dashboard"),
    ("GATEKEEPER", "门岗核验员", "gatekeeper", "/checkin"),
    ("YARDMASTER", "场内调度员", "yardmaster", "/assignment"),
    ("CARRIER", "承运商代表", "carrier", "/appointment"),
]'''

modules_block = '''MODULES = [
    ("CarrierCompany", "carrier_company", "carrier", "CarrierCompany", "承运商档案", "承运商编号、承运商名称、业务类型、联系人、合作时间和合作状态维护", ["ADMIN", "DISPATCHER", "CARRIER"], ["承运商编号", "承运商名称", "业务类型", "联系人", "合作时间", "合作状态", "承运商说明"]),
    ("VehicleProfile", "vehicle_profile", "vehicle", "VehicleProfile", "车辆档案", "车辆编号、车牌号码、车辆类型、司机姓名、备案时间和车辆状态维护", ["ADMIN", "DISPATCHER", "GATEKEEPER", "CARRIER"], ["车辆编号", "车牌号码", "车辆类型", "司机姓名", "备案时间", "车辆状态", "车辆说明"]),
    ("DriverProfile", "driver_profile", "driver", "DriverProfile", "司机档案", "司机编号、司机姓名、证件类型、承运商、备案时间和司机状态维护", ["ADMIN", "DISPATCHER", "GATEKEEPER", "CARRIER"], ["司机编号", "司机姓名", "证件类型", "承运商", "备案时间", "司机状态", "司机说明"]),
    ("GateAppointment", "gate_appointment", "appointment", "GateAppointment", "入场预约", "预约编号、预约车辆、预约类型、预约人、预约时间和预约状态维护", ["ADMIN", "DISPATCHER", "GATEKEEPER", "CARRIER"], ["预约编号", "预约车辆", "预约类型", "预约人", "预约时间", "预约状态", "预约说明"]),
    ("TimeSlotPlan", "time_slot_plan", "slot", "TimeSlotPlan", "时段计划", "时段编号、时段名称、作业类型、计划人、计划时间和开放状态维护", ["ADMIN", "DISPATCHER", "YARDMASTER"], ["时段编号", "时段名称", "作业类型", "计划人", "计划时间", "开放状态", "时段说明"]),
    ("GateCheckin", "gate_checkin", "checkin", "GateCheckin", "门岗核验", "核验编号、入场车辆、核验类型、核验人、核验时间和核验状态维护", ["ADMIN", "DISPATCHER", "GATEKEEPER"], ["核验编号", "入场车辆", "核验类型", "核验人", "核验时间", "核验状态", "核验说明"]),
    ("QueueTicket", "queue_ticket", "queue", "QueueTicket", "排队叫号", "叫号编号、排队车辆、作业类型、叫号人、叫号时间和排队状态维护", ["ADMIN", "DISPATCHER", "GATEKEEPER", "YARDMASTER", "CARRIER"], ["叫号编号", "排队车辆", "作业类型", "叫号人", "叫号时间", "排队状态", "叫号说明"]),
    ("DockDoor", "dock_door", "dock", "DockDoor", "道口资源", "道口编号、道口名称、道口类型、负责人、启用时间和道口状态维护", ["ADMIN", "DISPATCHER", "YARDMASTER"], ["道口编号", "道口名称", "道口类型", "负责人", "启用时间", "道口状态", "道口说明"]),
    ("DockAssignment", "dock_assignment", "assignment", "DockAssignment", "道口分配", "分配编号、分配车辆、作业类型、调度人、分配时间和分配状态维护", ["ADMIN", "DISPATCHER", "YARDMASTER", "CARRIER"], ["分配编号", "分配车辆", "作业类型", "调度人", "分配时间", "分配状态", "分配说明"]),
    ("LoadingTask", "loading_task", "loading", "LoadingTask", "装卸任务", "任务编号、货物批次、装卸类型、负责人、作业时间和任务状态维护", ["ADMIN", "DISPATCHER", "YARDMASTER", "CARRIER"], ["任务编号", "货物批次", "装卸类型", "负责人", "作业时间", "任务状态", "任务说明"]),
    ("TurnaroundRecord", "turnaround_record", "turnaround", "TurnaroundRecord", "周转统计", "统计编号、统计车辆、统计类型、统计人、统计周期和统计状态维护", ["ADMIN", "DISPATCHER", "YARDMASTER"], ["统计编号", "统计车辆", "统计类型", "统计人", "统计周期", "统计状态", "统计说明"]),
    ("OperationLog", "operation_log", "log", "OperationLog", "操作日志", "日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护", ["ADMIN", "DISPATCHER"], ["日志编号", "操作模块", "操作类型", "操作人", "操作时间", "执行结果", "操作详情"]),
]'''

text = re.sub(r"ROLES = \[.*?\]\n\nMODULES =", roles_block + "\n\nMODULES =", text, flags=re.S)
text = re.sub(r"MODULES = \[.*?\]\n\n\ndef write", modules_block + "\n\n\ndef write", text, flags=re.S)

text = text.replace("['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT']", "['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER']")
text = text.replace('["ADMIN", "LOGISTICS", "COUNSELOR", "ENERGY", "STUDENT"]', '["ADMIN", "DISPATCHER", "GATEKEEPER", "YARDMASTER", "CARRIER"]')
text = text.replace("<span>admin</span><span>logistics</span><span>counselor</span><span>energy</span><span>student</span>", "<span>admin</span><span>dispatcher</span><span>gatekeeper</span><span>yardmaster</span><span>carrier</span>")
text = text.replace("LOGISTICS: '/dashboard', COUNSELOR: '/alert', ENERGY: '/meter', STUDENT: '/ranking'", "DISPATCHER: '/dashboard', GATEKEEPER: '/checkin', YARDMASTER: '/assignment', CARRIER: '/appointment'")
text = text.replace("LOGISTICS: '/dashboard',\n  COUNSELOR: '/alert',\n  ENERGY: '/meter',\n  STUDENT: '/ranking'", "DISPATCHER: '/dashboard',\n  GATEKEEPER: '/checkin',\n  YARDMASTER: '/assignment',\n  CARRIER: '/appointment'")
text = text.replace("后勤管理员", "调度主管")
text = text.replace("宿舍辅导员", "门岗核验员")
text = text.replace("能耗专员", "场内调度员")
text = text.replace("学生代表", "承运商代表")
text = text.replace("已读数", "已预约")
text = text.replace("已巡查", "已核验")
text = text.replace("已节能", "已分配")
text = text.replace("近7日能耗与预警趋势", "近7日预约与周转趋势")
text = text.replace('data.put("pie", Arrays.asList(map("读数待复核", 32), map("能耗正常", 58), map("异常预警", 25), map("排行已发布", 8)));', 'data.put("pie", Arrays.asList(map("预约待入场", 32), map("门岗已核验", 58), map("道口已分配", 25), map("装卸已完成", 8)));')
text = text.replace("宿舍楼栋、宿舍房间、能耗账单、节能排行和通知公告维护", "承运商档案、车辆档案、时段计划、道口资源和周转统计维护")
text = text.replace("异常预警、巡查记录、节能任务和学生沟通维护", "门岗核验、车辆放行、排队叫号和异常入场处理")
text = text.replace("能耗表计、用电读数、预警策略、异常预警和巡查记录维护", "时段计划、排队叫号、道口资源、道口分配和装卸任务维护")
text = text.replace("用电读数、能耗账单、节能任务和节能排行查看", "入场预约、排队叫号、道口分配和装卸任务查看")
text = text.replace("按 ADMIN / LOGISTICS / COUNSELOR / ENERGY / STUDENT 收口页面操作权限", "按 ADMIN / DISPATCHER / GATEKEEPER / YARDMASTER / CARRIER 收口页面操作权限")
text = text.replace("- logistics/123456\n- counselor/123456\n- energy/123456\n- student/123456", "- dispatcher/123456\n- gatekeeper/123456\n- yardmaster/123456\n- carrier/123456")
text = text.replace("调度主管、门岗核验员、场内调度员、承运商代表账号与状态维护", "调度主管、门岗核验员、场内调度员、承运商代表账号与状态维护")

dst.write_text(text, encoding="utf-8")
print(dst)
