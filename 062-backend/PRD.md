# 反欺诈平台

## 项目概述
基于 Spring Boot + Vue3 的反欺诈平台，面向交易风控场景，支持风险事件上报、规则命中计算、预警处置、案件流转、黑名单管控、申诉复核与数据看板。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL 8.0 + Redis + JWT + Hutool
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：平台管理员，维护用户、黑名单、规则与系统公告，参与预警与案件处理
- ANALYST：风控分析员，处理预警、跟进案件、回复申诉、查看看板
- USER：业务用户，提交风险事件、查看事件与申诉进度

## 默认账号
- admin / 123456
- analyst / 123456
- user1 / 123456

## 功能需求

### 1. 认证与用户
- 登录注册
- JWT 鉴权与会话失效控制
- 个人资料维护、密码修改
- 管理员分页管理用户与状态

### 2. 黑名单管理
- 账号/设备/IP 黑名单维护
- 黑名单等级与状态管理
- 风险事件实时命中黑名单

### 3. 规则管理
- 规则新增、编辑、删除
- 金额阈值、频次阈值等规则配置
- 规则命中次数统计

### 4. 风险事件
- 用户上报风险事件
- 系统自动计算风险分
- 风险等级与事件状态跟踪

### 5. 风险预警
- 中高风险事件自动生成预警
- 预警指派、处置、结论记录
- 事件状态与预警结果联动
- 用户侧查看预警状态并发起申诉

### 6. 案件管理
- 由预警创建案件
- 案件优先级、负责人、处理结论
- 案件关闭归档

### 7. 申诉复核
- 用户针对预警发起申诉
- 管理员/分析员回复复核意见
- 复核结果联动预警状态

### 8. 公告与看板
- 风控公告发布管理
- 风险总览、趋势、等级分布、规则命中排行

### 9. 操作审计
- 关键业务操作日志记录
- 管理员按模块/动作/操作人查询日志
- 操作轨迹可追溯

## 数据库设计

### user
- id BIGINT PK
- username VARCHAR(50) UNIQUE
- password VARCHAR(100)
- nickname VARCHAR(50)
- avatar VARCHAR(255)
- phone VARCHAR(20)
- email VARCHAR(100)
- role VARCHAR(20)
- status INT
- last_login_time DATETIME
- create_time DATETIME
- update_time DATETIME

### blacklist
- id BIGINT PK
- type VARCHAR(20)
- value VARCHAR(100)
- reason VARCHAR(255)
- level INT
- status INT
- creator_id BIGINT
- create_time DATETIME
- update_time DATETIME

### risk_rule
- id BIGINT PK
- rule_name VARCHAR(80)
- rule_code VARCHAR(50) UNIQUE
- rule_type VARCHAR(30)
- threshold DECIMAL(12,2)
- weight INT
- hit_count INT
- description VARCHAR(255)
- status INT
- create_time DATETIME
- update_time DATETIME

### risk_event
- id BIGINT PK
- event_no VARCHAR(50) UNIQUE
- user_id BIGINT
- account_no VARCHAR(80)
- device_id VARCHAR(80)
- ip VARCHAR(50)
- amount DECIMAL(12,2)
- event_type VARCHAR(40)
- channel VARCHAR(40)
- risk_score INT
- risk_level VARCHAR(20)
- status INT
- hit_rules VARCHAR(500)
- remark VARCHAR(255)
- create_time DATETIME
- update_time DATETIME

### fraud_alert
- id BIGINT PK
- alert_no VARCHAR(50) UNIQUE
- event_id BIGINT
- user_id BIGINT
- risk_score INT
- risk_level VARCHAR(20)
- status INT
- assignee_id BIGINT
- handle_result VARCHAR(100)
- handle_remark VARCHAR(500)
- handle_time DATETIME
- create_time DATETIME
- update_time DATETIME

### fraud_case
- id BIGINT PK
- case_no VARCHAR(50) UNIQUE
- alert_id BIGINT
- case_type VARCHAR(40)
- status INT
- priority INT
- owner_id BIGINT
- summary VARCHAR(500)
- result VARCHAR(500)
- close_time DATETIME
- create_time DATETIME
- update_time DATETIME

### appeal
- id BIGINT PK
- alert_id BIGINT
- user_id BIGINT
- content VARCHAR(1000)
- status INT
- reply VARCHAR(1000)
- reply_admin_id BIGINT
- reply_time DATETIME
- create_time DATETIME

### announcement
- id BIGINT PK
- title VARCHAR(100)
- content TEXT
- admin_id BIGINT
- status INT
- create_time DATETIME
- update_time DATETIME

### operation_log
- id BIGINT PK
- module VARCHAR(40)
- action VARCHAR(40)
- operator_id BIGINT
- operator_role VARCHAR(20)
- biz_no VARCHAR(120)
- content VARCHAR(500)
- create_time DATETIME

## API 设计
- POST /api/auth/login
- POST /api/auth/register
- GET /api/auth/info
- PUT /api/auth/password
- POST /api/auth/logout

- GET /api/user/page
- GET /api/user/risk-list
- POST /api/user
- PUT /api/user
- PUT /api/user/status
- PUT /api/user/profile
- DELETE /api/user/{id}

- GET /api/blacklist/list
- GET /api/blacklist/page
- POST /api/blacklist
- PUT /api/blacklist
- DELETE /api/blacklist/{id}

- GET /api/rule/list
- GET /api/rule/page
- POST /api/rule
- PUT /api/rule
- DELETE /api/rule/{id}

- GET /api/event/page
- GET /api/event/my
- GET /api/event/{id}
- POST /api/event/report
- PUT /api/event/status

- GET /api/alert/page
- GET /api/alert/my
- GET /api/alert/{id}
- PUT /api/alert/assign
- PUT /api/alert/handle

- GET /api/case/page
- GET /api/case/{id}
- POST /api/case
- PUT /api/case
- PUT /api/case/close/{id}

- GET /api/appeal/page
- GET /api/appeal/my
- POST /api/appeal
- PUT /api/appeal/reply

- GET /api/announcement/list
- GET /api/announcement/page
- POST /api/announcement
- PUT /api/announcement
- DELETE /api/announcement/{id}

- GET /api/dashboard/stats
- GET /api/dashboard/riskTrend
- GET /api/dashboard/riskLevelDist
- GET /api/dashboard/topRules
- GET /api/dashboard/topUsers

- GET /api/log/page
- GET /api/log/export
