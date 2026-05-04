# 云原生成本分析与资源优化平台

## 项目概述
云原生成本分析与资源优化平台面向企业云资源 FinOps 治理场景，围绕云账号、资源空间、账单明细、预算策略、成本分摊、闲置识别、优化建议、节省计划和异常事件构建闭环管理能力，帮助运维和成本团队完成成本透明、责任分摊、异常发现和优化执行。

## 技术栈
- 后端：Spring Boot 2.7.18、MyBatis 2.3.1、PageHelper 1.4.7、MySQL 8.0、Redis、JWT
- 前端：Vue 3.4.0、Element Plus 2.4.4、Vite 5、Pinia 2.1.7、Axios 1.6.2、ECharts 5.4.3
- 数据库：MySQL 8.0，数据库名 cloud_cost_108
- 后端端口：8108
- 前端端口：3108

## 用户角色
- 管理员：维护账号、云账号、基础字典和操作日志
- 成本分析师：维护账单、成本明细、预算策略和报告快照
- 云运维工程师：处理闲置资源、优化规则、优化建议和异常事件
- 成本主管：审批成本分摊、查看节省计划和成本趋势

## 默认账号
- admin/123456
- finops/123456
- devops/123456
- manager/123456

## 功能需求
### 1. 账号权限
- 账号权限维护与云原生成本分析闭环管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 2. 云账号
- 云厂商账号、预算额度和负责人管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 3. 资源命名空间
- 业务线、环境和资源归属空间管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 4. 成本账单
- 云账号账期、应付实付和支付状态管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 5. 成本明细
- 资源级成本、用量和归属空间明细管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 6. 预算策略
- 空间预算、告警比例和周期策略管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 7. 成本分摊
- 业务线成本分摊、归属人和月度金额管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 8. 闲置资源
- 闲置资源识别、月成本和确认状态管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 9. 优化规则
- 指标阈值、资源类型和优化动作规则管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 10. 优化建议
- 资源优化建议、预计节省和处理状态管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 11. 节省计划
- 承诺消费、覆盖范围和节省计划执行管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 12. 成本异常
- 成本突增、异常资源和处置状态管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 13. 报告快照
- 月度成本报告、优化报告和发布归档管理
- 支持分页查询、新增、编辑、删除和状态筛选
### 14. 操作日志
- 关键操作、目标对象和审计详情记录
- 支持分页查询、新增、编辑、删除和状态筛选

## 数据库设计
- sys_user: 账号权限数据表
- cloud_account: 云账号数据表
- resource_namespace: 资源命名空间数据表
- cost_bill: 成本账单数据表
- cost_item: 成本明细数据表
- budget_policy: 预算策略数据表
- cost_allocation: 成本分摊数据表
- idle_resource: 闲置资源数据表
- optimization_rule: 优化规则数据表
- optimization_advice: 优化建议数据表
- saving_plan: 节省计划数据表
- anomaly_event: 成本异常数据表
- report_snapshot: 报告快照数据表
- operation_log: 操作日志数据表

## API接口设计
- `/api/user/page`、`/api/user`、`/api/user/{id}`: 账号权限管理接口
- `/api/account/page`、`/api/account`、`/api/account/{id}`: 云账号管理接口
- `/api/namespace/page`、`/api/namespace`、`/api/namespace/{id}`: 资源命名空间管理接口
- `/api/bill/page`、`/api/bill`、`/api/bill/{id}`: 成本账单管理接口
- `/api/cost-item/page`、`/api/cost-item`、`/api/cost-item/{id}`: 成本明细管理接口
- `/api/budget/page`、`/api/budget`、`/api/budget/{id}`: 预算策略管理接口
- `/api/allocation/page`、`/api/allocation`、`/api/allocation/{id}`: 成本分摊管理接口
- `/api/idle-resource/page`、`/api/idle-resource`、`/api/idle-resource/{id}`: 闲置资源管理接口
- `/api/optimization-rule/page`、`/api/optimization-rule`、`/api/optimization-rule/{id}`: 优化规则管理接口
- `/api/advice/page`、`/api/advice`、`/api/advice/{id}`: 优化建议管理接口
- `/api/saving-plan/page`、`/api/saving-plan`、`/api/saving-plan/{id}`: 节省计划管理接口
- `/api/anomaly/page`、`/api/anomaly`、`/api/anomaly/{id}`: 成本异常管理接口
- `/api/report/page`、`/api/report`、`/api/report/{id}`: 报告快照管理接口
- `/api/log/page`、`/api/log`、`/api/log/{id}`: 操作日志管理接口

## 核心流程
1. 管理员维护云账号、资源命名空间和系统账号
2. 成本分析师导入成本账单和成本明细
3. 系统按预算策略识别预算超限和异常事件
4. 云运维工程师确认闲置资源并采纳优化建议
5. 成本主管审批成本分摊并跟踪节省计划
6. 成本分析师发布报告快照形成月度复盘材料
