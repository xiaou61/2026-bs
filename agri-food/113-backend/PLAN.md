# 区块链农产品质量溯源与监管平台实施计划

## 问题陈述
农产品质量溯源需要把生产过程、检测报告、链上存证、物流节点和监管处置串成可信闭环，系统通过区块链存证编号和批次追踪记录提升数据可信度。

## 当前状态
`113-backend` 与 `113-frontend` 已完成农产品溯源平台的基础实现，当前进入精修阶段，重点核对认证安全、链路追溯、监管闭环和文档准确性。

## 实施方案
1. 创建 `agri_trace_113` 数据库和 14 张业务表。
2. 实现实体、Mapper、Service、Controller、统计看板、登录鉴权和 Redis Token 校验。
3. 实现登录页、布局页、看板页、14 个业务页面、路由和 API 封装。
4. 更新合集 README 和候选清单。

## 模块清单
1. SysUser：账号权限
2. FarmBase：种植基地
3. FarmerProfile：农户档案
4. ProductCategory：产品分类
5. ProductBatch：产品批次
6. PlantingRecord：种植记录
7. InputMaterial：农资投入
8. QualityInspection：质检报告
9. ChainBlock：区块存证
10. TraceNode：流通节点
11. LogisticsRecord：物流记录
12. RecallEvent：召回事件
13. RegulationCheck：监管检查
14. OperationLog：操作日志

## 完成标准
1. 后端结构完整。
2. 前端结构完整。
3. SQL 包含演示数据。
4. 认证返回不暴露敏感字段。
5. 批次追溯、质检报告、链上存证和监管处置口径一致。
6. 文档描述与实际实现保持一致。
7. 不执行编译验证。
