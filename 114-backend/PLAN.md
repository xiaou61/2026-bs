# 供应链冷链温控追踪与异常预警平台实施计划

## 问题陈述
冷链运输需要持续采集温湿度和车辆轨迹，在异常发生后快速预警、派单处置并沉淀责任追溯证据。

## 当前状态
114项目从112的MyBatis成熟模板生成，保持Spring Boot、Vue3、Redis、JWT、Element Plus、ECharts技术栈一致。

## 实施方案
1. 创建 cold_chain_114 数据库和14张业务表。
2. 后端生成实体、Mapper注解SQL、Service、Controller、统计看板、登录鉴权和Redis Token校验。
3. 前端生成登录页、布局页、看板页、14个业务页面、路由和API封装。
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

## 验证标准
1. 核对实体、Mapper、业务页面和CREATE TABLE数量。
2. 核对 cold_chain_114、root/1234、8114、3114 和 Redis database 17。
3. 静态扫描旧项目字符串、乱码和注释。
