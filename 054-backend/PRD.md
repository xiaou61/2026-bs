# 基于SpringBoot的农业生产技术管理系统设计与实现

## 项目概述

### 项目简介
农业生产技术管理系统是一个面向农业生产管理的综合信息化平台，旨在帮助农业生产者、技术员和管理员实现对农作物种植、病虫害防治、农技推广、生产计划等全流程的数字化管理。系统通过信息化手段提升农业生产效率，促进农业技术推广应用。

### 核心功能
- 农作物信息管理（品种、生长周期、种植要求）
- 农技知识库（种植技术、病虫害防治、施肥指导）
- 生产计划管理（种植计划、生产任务、进度跟踪）
- 病虫害预警与防治记录
- 农资管理（种子、肥料、农药库存）
- 农技咨询与问答社区
- 专家在线指导预约
- 生产数据统计与分析

### 技术栈
**后端**
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis
- JWT认证

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vite 5.0
- Pinia 2.1.7
- ECharts 5.4.3

---

## 功能需求

### 1. 用户管理模块
- 用户注册登录（农户/技术员/专家/管理员）
- 角色权限管理（四种角色权限隔离）
- 个人信息管理（头像、联系方式、种植区域）
- 密码修改与找回

### 2. 农作物管理模块
- 农作物品种信息维护（名称、分类、生长周期、适宜环境）
- 作物种植知识库（种植技术、管理要点、采收标准）
- 作物分类管理（粮食作物、经济作物、蔬菜、水果）
- 品种查询与筛选

### 3. 农技知识库模块
- 知识分类（种植技术、病虫害防治、施肥技术、灌溉技术）
- 知识文章发布（标题、内容、图片、分类、标签）
- 知识查询与搜索
- 知识收藏与点赞
- 知识评论与互动

### 4. 生产计划模块
- 种植计划制定（作物、面积、预计产量、时间安排）
- 生产任务分配（任务名称、负责人、截止日期）
- 生产进度跟踪（进度更新、完成情况）
- 计划变更与调整
- 计划统计与分析

### 5. 病虫害管理模块
- 病虫害信息库（名称、症状、危害、防治方法）
- 病虫害预警发布（地区、作物、病虫害类型、预警等级）
- 防治记录管理（时间、作物、措施、效果）
- 病虫害识别与诊断（图片上传、AI识别建议）
- 防治方案推荐

### 6. 农资管理模块
- 农资分类（种子、肥料、农药、农具）
- 农资入库管理（名称、数量、单价、供应商）
- 农资出库记录（用途、数量、领用人）
- 库存预警（低库存提醒）
- 农资使用统计

### 7. 农技咨询模块
- 问题发布（标题、描述、图片、作物类型）
- 专家/技术员回答
- 问题分类与标签
- 热门问题推荐
- 采纳答案与评价

### 8. 专家预约模块
- 专家信息展示（姓名、擅长领域、评分）
- 在线预约（选择专家、时间、咨询主题）
- 预约状态管理（待确认/已确认/已完成/已取消）
- 预约记录查询
- 专家评价

### 9. 数据统计模块
- 首页数据概览（用户数、作物数、知识数、计划数）
- 种植面积统计（按作物、按地区）
- 生产计划统计（完成率、进度分析）
- 病虫害发生趋势
- 农资使用统计

---

## 技术设计

### 数据库设计

#### 1. user（用户表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
username VARCHAR(50) UNIQUE NOT NULL - 用户名
password VARCHAR(255) NOT NULL - 密码
real_name VARCHAR(50) - 真实姓名
role ENUM('ADMIN','EXPERT','TECHNICIAN','FARMER') - 角色
phone VARCHAR(20) - 手机号
email VARCHAR(100) - 邮箱
avatar VARCHAR(255) - 头像
region VARCHAR(100) - 种植区域
status TINYINT DEFAULT 1 - 状态(0禁用1启用)
create_time DATETIME - 创建时间
update_time DATETIME - 更新时间
```

#### 2. crop（农作物表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(100) NOT NULL - 作物名称
category_id BIGINT - 分类ID
variety VARCHAR(100) - 品种
growth_cycle INT - 生长周期(天)
suitable_temp VARCHAR(50) - 适宜温度
suitable_soil VARCHAR(100) - 适宜土壤
planting_season VARCHAR(50) - 种植季节
yield_per_mu DECIMAL(10,2) - 亩产量(kg)
description TEXT - 描述
image VARCHAR(255) - 图片
status TINYINT DEFAULT 1 - 状态
create_time DATETIME
update_time DATETIME
```

#### 3. crop_category（作物分类表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(50) NOT NULL - 分类名称
parent_id BIGINT DEFAULT 0 - 父分类ID
sort INT DEFAULT 0 - 排序
description TEXT - 描述
create_time DATETIME
```

#### 4. knowledge（农技知识表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
title VARCHAR(200) NOT NULL - 标题
category VARCHAR(50) - 分类(种植技术/病虫害/施肥/灌溉)
content TEXT - 内容
images TEXT - 图片(多张,逗号分隔)
tags VARCHAR(200) - 标签
author_id BIGINT - 作者ID
view_count INT DEFAULT 0 - 浏览量
like_count INT DEFAULT 0 - 点赞数
collect_count INT DEFAULT 0 - 收藏数
status TINYINT DEFAULT 1 - 状态
create_time DATETIME
update_time DATETIME
```

#### 5. production_plan（生产计划表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
plan_name VARCHAR(200) NOT NULL - 计划名称
crop_id BIGINT - 作物ID
area DECIMAL(10,2) - 种植面积(亩)
expected_yield DECIMAL(10,2) - 预计产量(kg)
start_date DATE - 开始日期
end_date DATE - 结束日期
status TINYINT DEFAULT 0 - 状态(0待开始1进行中2已完成)
progress INT DEFAULT 0 - 进度(0-100)
creator_id BIGINT - 创建人ID
remark TEXT - 备注
create_time DATETIME
update_time DATETIME
```

#### 6. production_task（生产任务表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
plan_id BIGINT - 所属计划ID
task_name VARCHAR(200) NOT NULL - 任务名称
task_type VARCHAR(50) - 任务类型(播种/施肥/除草/灌溉/采收)
assignee_id BIGINT - 负责人ID
start_date DATE - 开始日期
end_date DATE - 截止日期
status TINYINT DEFAULT 0 - 状态(0待开始1进行中2已完成)
completion_time DATETIME - 完成时间
remark TEXT - 备注
create_time DATETIME
update_time DATETIME
```

#### 7. pest_disease（病虫害表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(100) NOT NULL - 名称
type TINYINT - 类型(1病害2虫害)
crop_type VARCHAR(100) - 作物类型
symptom TEXT - 症状描述
harm TEXT - 危害描述
prevention TEXT - 预防措施
treatment TEXT - 治疗方案
images TEXT - 图片
status TINYINT DEFAULT 1 - 状态
create_time DATETIME
update_time DATETIME
```

#### 8. pest_warning（病虫害预警表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
title VARCHAR(200) NOT NULL - 标题
pest_disease_id BIGINT - 病虫害ID
region VARCHAR(100) - 预警地区
crop_type VARCHAR(100) - 作物类型
warning_level TINYINT - 预警等级(1低2中3高)
warning_date DATE - 预警日期
content TEXT - 预警内容
publisher_id BIGINT - 发布人ID
status TINYINT DEFAULT 1 - 状态
create_time DATETIME
update_time DATETIME
```

#### 9. treatment_record（防治记录表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
plan_id BIGINT - 关联生产计划
pest_disease_id BIGINT - 病虫害ID
treatment_date DATE - 防治日期
method TEXT - 防治方法
medicine VARCHAR(200) - 使用药剂
dosage VARCHAR(100) - 用量
effect TINYINT - 效果(1差2一般3好4很好5优秀)
cost DECIMAL(10,2) - 成本
operator_id BIGINT - 操作人ID
remark TEXT - 备注
create_time DATETIME
```

#### 10. agricultural_material（农资表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(100) NOT NULL - 名称
category VARCHAR(50) - 分类(种子/肥料/农药/农具)
specification VARCHAR(100) - 规格
unit VARCHAR(20) - 单位
stock INT DEFAULT 0 - 库存数量
warning_stock INT DEFAULT 0 - 预警库存
unit_price DECIMAL(10,2) - 单价
supplier VARCHAR(100) - 供应商
description TEXT - 描述
status TINYINT DEFAULT 1 - 状态
create_time DATETIME
update_time DATETIME
```

#### 11. material_record（农资记录表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
material_id BIGINT - 农资ID
type TINYINT - 类型(1入库2出库)
quantity INT - 数量
unit_price DECIMAL(10,2) - 单价
total_price DECIMAL(10,2) - 总价
purpose VARCHAR(200) - 用途/来源
operator_id BIGINT - 操作人ID
record_date DATE - 记录日期
remark TEXT - 备注
create_time DATETIME
```

#### 12. consultation（咨询问答表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
title VARCHAR(200) NOT NULL - 标题
content TEXT - 内容
images TEXT - 图片
crop_type VARCHAR(100) - 作物类型
tags VARCHAR(200) - 标签
questioner_id BIGINT - 提问人ID
status TINYINT DEFAULT 0 - 状态(0待回答1已回答2已采纳)
view_count INT DEFAULT 0 - 浏览量
create_time DATETIME
update_time DATETIME
```

#### 13. consultation_answer（咨询回答表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
consultation_id BIGINT - 问题ID
content TEXT - 回答内容
images TEXT - 图片
answerer_id BIGINT - 回答人ID
is_adopted TINYINT DEFAULT 0 - 是否采纳
like_count INT DEFAULT 0 - 点赞数
create_time DATETIME
```

#### 14. expert_appointment（专家预约表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
expert_id BIGINT - 专家ID
farmer_id BIGINT - 农户ID
appointment_date DATE - 预约日期
appointment_time VARCHAR(50) - 预约时间段
topic VARCHAR(200) - 咨询主题
description TEXT - 问题描述
status TINYINT DEFAULT 0 - 状态(0待确认1已确认2已完成3已取消)
rating TINYINT - 评分(1-5)
feedback TEXT - 反馈
create_time DATETIME
update_time DATETIME
```

#### 15. notice（公告表）
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
title VARCHAR(200) NOT NULL - 标题
content TEXT - 内容
type VARCHAR(50) - 类型(系统公告/政策通知/技术推广)
publisher_id BIGINT - 发布人ID
status TINYINT DEFAULT 1 - 状态(0草稿1已发布)
create_time DATETIME
update_time DATETIME
```

---

### API接口设计

#### 用户模块
- POST /api/user/login - 用户登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/password - 修改密码
- GET /api/user/page - 用户分页列表
- POST /api/user - 添加用户
- PUT /api/user - 更新用户
- DELETE /api/user/{id} - 删除用户

#### 农作物模块
- GET /api/crop/page - 作物分页列表
- GET /api/crop/{id} - 作物详情
- POST /api/crop - 添加作物
- PUT /api/crop - 更新作物
- DELETE /api/crop/{id} - 删除作物
- GET /api/crop/category/list - 分类列表
- GET /api/crop/category/tree - 分类树
- POST /api/crop/category - 添加分类
- PUT /api/crop/category - 更新分类
- DELETE /api/crop/category/{id} - 删除分类

#### 农技知识模块
- GET /api/knowledge/page - 知识分页列表
- GET /api/knowledge/{id} - 知识详情
- POST /api/knowledge - 发布知识
- PUT /api/knowledge - 更新知识
- DELETE /api/knowledge/{id} - 删除知识
- POST /api/knowledge/{id}/like - 点赞
- POST /api/knowledge/{id}/collect - 收藏

#### 生产计划模块
- GET /api/plan/page - 计划分页列表
- GET /api/plan/{id} - 计划详情
- POST /api/plan - 创建计划
- PUT /api/plan - 更新计划
- DELETE /api/plan/{id} - 删除计划
- PUT /api/plan/{id}/status - 更新状态
- GET /api/task/page - 任务分页列表
- POST /api/task - 创建任务
- PUT /api/task - 更新任务
- DELETE /api/task/{id} - 删除任务
- PUT /api/task/{id}/status - 更新任务状态

#### 病虫害模块
- GET /api/pest/page - 病虫害分页列表
- GET /api/pest/{id} - 病虫害详情
- POST /api/pest - 添加病虫害
- PUT /api/pest - 更新病虫害
- DELETE /api/pest/{id} - 删除病虫害
- GET /api/warning/page - 预警分页列表
- POST /api/warning - 发布预警
- PUT /api/warning - 更新预警
- DELETE /api/warning/{id} - 删除预警
- GET /api/treatment/page - 防治记录分页
- POST /api/treatment - 添加防治记录
- PUT /api/treatment - 更新防治记录

#### 农资管理模块
- GET /api/material/page - 农资分页列表
- POST /api/material - 添加农资
- PUT /api/material - 更新农资
- DELETE /api/material/{id} - 删除农资
- GET /api/material/record/page - 出入库记录分页
- POST /api/material/in - 入库
- POST /api/material/out - 出库
- GET /api/material/warning - 库存预警列表

#### 咨询问答模块
- GET /api/consultation/page - 问题分页列表
- GET /api/consultation/{id} - 问题详情
- POST /api/consultation - 发布问题
- PUT /api/consultation - 更新问题
- DELETE /api/consultation/{id} - 删除问题
- GET /api/consultation/{id}/answers - 问题回答列表
- POST /api/consultation/{id}/answer - 回答问题
- PUT /api/consultation/answer/{id}/adopt - 采纳答案

#### 专家预约模块
- GET /api/appointment/page - 预约分页列表
- GET /api/appointment/my - 我的预约
- POST /api/appointment - 创建预约
- PUT /api/appointment/{id}/confirm - 确认预约
- PUT /api/appointment/{id}/complete - 完成预约
- PUT /api/appointment/{id}/cancel - 取消预约
- POST /api/appointment/{id}/rate - 评价预约

#### 数据统计模块
- GET /api/stats/overview - 数据概览
- GET /api/stats/plan - 计划统计
- GET /api/stats/area - 种植面积统计
- GET /api/stats/pest - 病虫害统计

#### 公告模块
- GET /api/notice/page - 公告分页列表
- GET /api/notice/published - 已发布公告列表
- POST /api/notice - 发布公告
- PUT /api/notice - 更新公告
- DELETE /api/notice/{id} - 删除公告

---

### 项目结构

**后端**
```
054-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/agriculture/
│   ├── AgricultureApplication.java
│   ├── common/
│   │   ├── Result.java
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   ├── config/
│   │   ├── JwtInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── MybatisPlusConfig.java
│   │   └── RedisConfig.java
│   ├── utils/
│   │   └── JwtUtils.java
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── controller/
│   └── dto/
└── src/main/resources/
    └── application.yml
```

**前端**
```
054-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── crop/
        ├── knowledge/
        ├── plan/
        ├── pest/
        ├── material/
        ├── consultation/
        ├── appointment/
        ├── notice/
        └── user/
```

---

## 用户角色

### ADMIN（管理员）
- 全部功能权限
- 用户管理
- 系统配置
- 数据统计

### EXPERT（专家）
- 回答咨询问题
- 发布农技知识
- 接受预约
- 发布病虫害预警

### TECHNICIAN（技术员）
- 发布农技知识
- 回答咨询问题
- 管理生产计划
- 记录病虫害防治

### FARMER（农户）
- 浏览知识库
- 提问咨询
- 预约专家
- 管理自己的生产计划
- 查看病虫害预警

---

## 默认账号

- admin/123456 - 管理员
- expert/123456 - 专家
- tech/123456 - 技术员
- farmer/123456 - 农户
