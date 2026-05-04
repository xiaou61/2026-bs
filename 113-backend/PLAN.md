# 区块链农产品质量溯源与监管平台实施计划

## 问题陈述
农产品质量溯源需要把生产过程、检测报告、链上存证、物流节点和监管处置串成可信闭环，系统通过区块链存证编号和批次追踪记录提升数据可信度。

## 当前状态
113项目从111的MyBatis-Plus成熟模板生成，保持Spring Boot、Vue3、Redis、JWT、Element Plus、ECharts技术栈一致。

## 实施方案
1. 创建 agri_trace_113 数据库和14张业务表。
2. 后端生成实体、Mapper、Service、Controller、统计看板、登录鉴权和Redis Token校验。
3. 前端生成登录页、布局页、看板页、14个业务页面、路由和API封装。
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

## 验证标准
1. 核对实体、Mapper、业务页面和CREATE TABLE数量。
2. 核对 agri_trace_113、root/1234、8113、3113 和 Redis database 16。
3. 静态扫描旧项目字符串、乱码和注释。
