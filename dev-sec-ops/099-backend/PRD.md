# AIGC 图片内容审核与版权存证平台

## 项目概述

AIGC 图片内容审核与版权存证平台面向 AI 图片生成、设计素材管理和版权确权场景，提供图片作品入库、内容审核、风险标签、版权登记、存证编号、授权记录、申诉处理和审计统计能力。系统帮助平台方对 AIGC 图片进行合规审核、版权归属登记和后续争议处理。

核心功能包括：
- 用户登录与角色权限
- 图片作品管理
- 审核规则管理
- 图片审核任务
- 审核结果记录
- 风险标签管理
- 版权登记管理
- 版权存证记录
- 授权使用记录
- 侵权线索管理
- 申诉处理管理
- 操作日志与统计看板

技术栈：
- 后端：Spring Boot 2.7.18、MyBatis-Plus、MySQL 8.0、Redis、JWT
- 前端：Vue 3、Element Plus、Vue Router、Pinia、Axios、ECharts、Vite

## 功能需求

### 1. 用户与权限模块

- 用户登录
- 当前用户信息查询
- 用户分页查询
- 用户新增、编辑、禁用
- 管理员负责用户、规则、标签、审计和全部数据
- 审核员负责内容审核、风险判定和侵权线索处理
- 创作者负责作品入库、版权登记、授权和申诉提交

### 2. 图片作品模块

- 图片作品分页查询
- 按创作者、分类、状态、关键词筛选
- 新增作品标题、图片地址、生成提示词、模型名称、分类和说明
- 编辑作品信息
- 删除作品

### 3. 审核规则模块

- 审核规则分页查询
- 新增、编辑、删除规则
- 维护规则类型、风险级别、关键词和启用状态
- 用于模拟审核评分

### 4. 图片审核任务模块

- 审核任务分页查询
- 为图片作品创建审核任务
- 启动审核并生成审核结果
- 完成或退回审核任务

### 5. 审核结果模块

- 审核结果分页查询
- 查看命中规则、风险等级、审核结论和建议
- 审核员复核并填写意见

### 6. 风险标签模块

- 风险标签分页查询
- 新增、编辑、删除标签
- 维护标签类型、颜色和说明

### 7. 版权登记模块

- 版权登记分页查询
- 为作品登记作者、权利类型、声明内容和登记状态
- 支持提交、通过和驳回登记

### 8. 存证记录模块

- 存证记录分页查询
- 对通过登记的版权生成存证编号
- 维护存证哈希、存证平台、存证状态

### 9. 授权使用模块

- 授权记录分页查询
- 维护被授权方、用途、授权期限、授权状态
- 支持到期和撤销

### 10. 侵权线索模块

- 侵权线索分页查询
- 提交疑似侵权链接、平台、描述和证据
- 审核员处理线索并更新状态

### 11. 申诉处理模块

- 申诉分页查询
- 创作者针对审核结果或版权登记提交申诉
- 审核员处理申诉并维护处理意见

### 12. 审计统计模块

- 操作日志分页查询
- 记录用户、模块、动作、描述和时间
- 首页统计作品数、审核任务数、版权登记数、存证数、申诉数和高风险数量

## 技术设计

### 数据库设计

表1：sys_user
- id、username、password、nickname、role、phone、email、status、create_time、update_time

表2：image_asset
- id、title、image_url、prompt_text、model_name、category、description、creator_id、status、create_time、update_time

表3：audit_rule
- id、rule_name、rule_type、risk_level、keywords、description、status、create_time、update_time

表4：audit_task
- id、task_no、asset_id、task_name、priority、status、auditor_id、create_time、finish_time

表5：audit_result
- id、task_id、asset_id、matched_rules、risk_level、score、conclusion、suggestion、review_status、review_comment、create_time、update_time

表6：risk_tag
- id、tag_name、tag_type、color、description、status、create_time、update_time

表7：copyright_register
- id、asset_id、author_name、right_type、declaration、register_status、review_comment、create_time、update_time

表8：evidence_record
- id、register_id、evidence_no、hash_value、platform_name、evidence_status、create_time、update_time

表9：license_record
- id、register_id、licensee、purpose、start_date、end_date、status、create_time、update_time

表10：infringement_clue
- id、asset_id、platform_name、clue_url、description、evidence_url、status、handler_id、handle_comment、create_time、update_time

表11：appeal_record
- id、target_type、target_id、user_id、reason、status、handle_comment、create_time、update_time

表12：operation_log
- id、user_id、username、role、module_name、action_type、description、create_time

### API接口设计

#### 1.1 登录
- 请求方式：POST
- 路径：`/api/auth/login`
- 参数：`{ "username": "admin", "password": "123456" }`
- 返回：token 和用户信息

#### 1.2 当前用户
- 请求方式：GET
- 路径：`/api/auth/info`
- 返回：当前登录用户

#### 2.1 图片作品分页
- 请求方式：GET
- 路径：`/api/asset/page`
- 参数：pageNum、pageSize、keyword、category、status
- 返回：分页数据

#### 3.1 审核任务分页
- 请求方式：GET
- 路径：`/api/task/page`
- 参数：pageNum、pageSize、keyword、status、priority
- 返回：分页数据

#### 3.2 启动审核
- 请求方式：PUT
- 路径：`/api/task/run/{id}`
- 返回：生成审核结果

#### 4.1 版权登记分页
- 请求方式：GET
- 路径：`/api/register/page`
- 参数：pageNum、pageSize、assetId、registerStatus
- 返回：分页数据

#### 4.2 通过版权登记
- 请求方式：PUT
- 路径：`/api/register/approve/{id}`
- 返回：成功

#### 5.1 生成版权存证
- 请求方式：POST
- 路径：`/api/evidence/generate/{registerId}`
- 返回：存证记录

#### 6.1 申诉处理
- 请求方式：PUT
- 路径：`/api/appeal/handle`
- 参数：id、status、handleComment
- 返回：成功

#### 7.1 统计看板
- 请求方式：GET
- 路径：`/api/statistics/dashboard`
- 返回：作品数、审核任务数、登记数、存证数和高风险数

### 项目结构

后端：
```
099-backend/
├── sql/
├── src/main/java/com/aigccopyright/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
```

前端：
```
099-frontend/
├── src/
│   ├── api/
│   ├── router/
│   ├── store/
│   └── views/
```

## 用户角色

- 管理员：用户、规则、标签、审计和全部业务管理
- 审核员：内容审核、审核复核、侵权线索和申诉处理
- 创作者：图片作品入库、版权登记、授权使用和申诉提交

## 默认账号

- admin / 123456
- auditor / 123456
- creator / 123456
