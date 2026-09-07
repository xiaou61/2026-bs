# 法律咨询案件进度与智能文书管理系统

## 项目概述

本项目面向律师事务所、法律咨询机构和高校法律援助中心，提供委托人档案、律师档案、案件台账、咨询记录、案件进度、证据材料、文书模板、智能文书、版本留痕、预约咨询、费用记录和审计统计能力。系统采用 Spring Boot + MyBatis + MySQL + Redis + Vue3 技术栈，围绕“咨询建档-案件分配-进度跟踪-证据归档-文书生成-预约沟通-费用结算-审计留痕”形成闭环。

## 功能需求

### 1. 账号权限
- 管理员维护律师、助理、委托人账号
- JWT 登录鉴权和 Redis 登录态缓存
- 按角色控制业务操作

### 2. 委托人档案
- 维护姓名、证件号、联系方式、地址和偏好案件类型
- 支持按姓名、电话和状态筛选

### 3. 律师档案
- 维护律师执业证号、擅长领域、经验年限和服务状态
- 支持按擅长领域检索

### 4. 案件台账
- 维护案件编号、委托人、承办律师、案件类型、优先级、状态、当前阶段和下一步动作
- 支持推进案件和结案

### 5. 进度节点
- 维护案件阶段、计划时间、完成时间和节点备注
- 支持节点完成和重开

### 6. 咨询记录
- 记录咨询方式、咨询问题、律师答复、风险等级和跟进动作
- 支持按案件、委托人、律师和风险等级查询

### 7. 文书模板
- 管理咨询记录、委托合同、起诉状、律师函等模板
- 支持启用和停用模板

### 8. 法律文书
- 按案件和模板生成文书编号、标题、内容和状态
- 支持生成、复核通过和复核驳回

### 9. 文书版本
- 文书每次修订形成版本号、内容、变更说明和操作人
- 支持按文书查询版本历史

### 10. 咨询预约
- 管理案件预约时间、方式、地点和状态
- 支持确认、取消和完成预约

### 11. 证据材料
- 管理证据名称、类型、文件地址、提交人和核验状态
- 支持材料核验通过和驳回

### 12. 费用记录
- 维护咨询费、代理费、文书费、差旅费等费用
- 支持标记已支付和退款

### 13. 统计审计
- 首页展示案件、咨询、文书、预约、费用和待处理数据
- 操作日志记录关键业务动作

## 技术设计

### 数据库设计
- sys_user
- client_profile
- lawyer_profile
- legal_case
- case_stage
- consultation_record
- document_template
- legal_document
- document_version
- appointment_record
- evidence_material
- fee_record
- operation_log

### API接口设计
- POST /api/auth/login
- GET /api/auth/info
- POST /api/auth/logout
- GET /api/user/page
- GET /api/client/page
- GET /api/lawyer/page
- GET /api/case/page
- PUT /api/case/advance/{id}
- PUT /api/case/close/{id}
- GET /api/stage/page
- PUT /api/stage/finish/{id}
- GET /api/consultation/page
- GET /api/template/page
- PUT /api/template/enable/{id}
- GET /api/document/page
- PUT /api/document/generate/{id}
- PUT /api/document/review
- GET /api/version/page
- GET /api/appointment/page
- PUT /api/appointment/confirm/{id}
- GET /api/evidence/page
- PUT /api/evidence/verify/{id}
- GET /api/fee/page
- PUT /api/fee/pay/{id}
- GET /api/statistics/dashboard
- GET /api/log/page

## 用户角色

- 管理员：账号、律师档案、模板和审计管理
- 律师：案件处理、咨询答复、文书复核和预约管理
- 助理：案件资料、进度节点、证据材料和费用协同维护
- 委托人：查看案件进度、咨询预约和材料状态

## 默认账号

- admin/123456
- lawyer/123456
- assistant/123456
- client/123456
