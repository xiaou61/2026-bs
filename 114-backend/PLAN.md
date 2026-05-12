# 供应链冷链温控追踪与异常预警平台实施计划

## 问题陈述
冷链运输需要持续采集温湿度和车辆轨迹，在异常发生后快速预警、派单处置并沉淀责任追溯证据。

## 当前状态
`114-backend` 与 `114-frontend` 已完成冷链追踪平台的基础实现，当前进入精修阶段，重点核对认证安全、异常预警、处置闭环和文档准确性。

## 实施方案
1. 创建 `cold_chain_114` 数据库和 14 张业务表。
2. 实现实体、Mapper 注解 SQL、Service、Controller、统计看板、登录鉴权和 Redis Token 校验。
3. 实现登录页、布局页、看板页、14 个业务页面、路由和 API 封装。
4. 更新合集 README 和候选清单。

## 模块清单
1. SysUser：账号权限
2. WarehouseNode：冷链仓点
3. CarrierCompany：承运企业
4. ColdDevice：温控设备
5. ProductCargo：冷链货品
6. TransportOrder：运输订单
7. TemperatureRecord：温控记录
8. GpsTrack：运输轨迹
9. AlertRule：告警规则
10. ExceptionAlert：异常告警
11. DisposalTask：处置任务
12. ResponsibilityTrace：责任追溯
13. MaintenanceRecord：设备维护
14. OperationLog：操作日志

## 完成标准
1. 后端结构完整。
2. 前端结构完整。
3. SQL 包含演示数据。
4. 认证返回不暴露敏感字段。
5. 温控记录、异常告警、处置任务和责任追溯口径一致。
6. 文档描述与实际实现保持一致。
7. 不执行编译验证。
