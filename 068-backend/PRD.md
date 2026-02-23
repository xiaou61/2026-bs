# 068 周边游平台个人管理模块

## 项目概述
- 项目简介：面向周边游平台用户的个人中心管理模块，覆盖资料维护、常用出行人、收藏、订单、评价与投诉全流程。
- 核心功能：账号体系、个人资产管理、订单履约管理、用户反馈闭环、运营看板。
- 技术栈：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + Redis + MySQL 8 + Vue3 + Element Plus + Vite。

## 功能需求
### 1. 账号与权限模块
- 用户登录、注册、退出、获取当前用户信息、修改密码
- 用户角色：管理员 ADMIN、普通用户 USER
- 管理员可分页管理用户和账号状态

### 2. 个人资料模块
- 用户维护昵称、手机号、邮箱、头像、个人简介
- 用户在个人中心独立修改密码

### 3. 常用出行人模块
- 用户新增、编辑、删除、分页查询常用出行人
- 字段包括姓名、证件类型、证件号、手机号、是否默认

### 4. 收藏模块
- 用户收藏/取消收藏景点
- 用户分页查看收藏清单

### 5. 景点运营模块
- 管理员分页管理景点资料并维护上架状态
- 支持景点新增、编辑、删除、上下架切换
- 支持上传景点封面图片

### 6. 订单模块
- 用户创建订单、查看我的订单、取消订单、支付订单、完成订单
- 管理员可查看全部订单与状态分布
- 支持按筛选条件导出订单CSV

### 7. 评价与投诉模块
- 用户对已完成订单提交评价并打分
- 评价包含敏感词时进入待审核状态
- 管理员可审核通过/驳回评价并回复已通过评价
- 用户发起投诉并查看处理状态
- 用户提交投诉时支持上传附件图片
- 管理员可处理投诉并更新状态

### 8. 看板模块
- 用户看板：我的订单总数、待支付、已完成、收藏数、投诉数
- 管理员看板：用户总数、订单总数、评价总数、投诉待处理数
- 支持近7日订单趋势与订单状态分布图表

## 技术设计
### 数据库设计
1. `sys_user`
- 字段：id, username, password, nickname, phone, email, avatar, profile, role, status, last_login_time, create_time, update_time
- 索引：username 唯一索引

2. `scenic_spot`
- 字段：id, name, city, tags, price, cover, intro, status, create_time, update_time
- 说明：cover支持base64图片存储

3. `traveler`
- 字段：id, user_id, name, cert_type, cert_no, phone, is_default, create_time, update_time
- 索引：user_id

4. `user_favorite`
- 字段：id, user_id, spot_id, create_time
- 索引：user_id + spot_id 唯一索引

5. `travel_order`
- 字段：id, order_no, user_id, traveler_id, spot_id, travel_date, quantity, total_amount, status, contact_name, contact_phone, remark, pay_time, finish_time, create_time, update_time
- 索引：order_no 唯一索引，user_id，spot_id

6. `travel_review`
- 字段：id, order_id, user_id, spot_id, score, content, status, reply_content, create_time, update_time
- 索引：order_id 唯一索引，spot_id

7. `user_complaint`
- 字段：id, user_id, order_id, type, content, attachment_urls, status, handle_result, handle_by, handle_time, create_time, update_time
- 索引：user_id，status

### API接口设计
#### 1. 认证接口
- POST `/api/auth/login`
- POST `/api/auth/register`
- GET `/api/auth/info`
- PUT `/api/auth/password`
- POST `/api/auth/logout`

#### 2. 用户接口
- GET `/api/user/page`
- POST `/api/user`
- PUT `/api/user`
- PUT `/api/user/status`
- PUT `/api/user/profile`
- DELETE `/api/user/{id}`

#### 3. 景点与收藏接口
- GET `/api/spot/list`
- GET `/api/spot/page`
- POST `/api/spot`
- PUT `/api/spot`
- PUT `/api/spot/status`
- DELETE `/api/spot/{id}`
- GET `/api/favorite/page`
- POST `/api/favorite/toggle`

#### 4. 出行人接口
- GET `/api/traveler/page`
- POST `/api/traveler`
- PUT `/api/traveler`
- DELETE `/api/traveler/{id}`

#### 5. 订单接口
- GET `/api/order/page`
- GET `/api/order/my-page`
- GET `/api/order/export`
- POST `/api/order`
- PUT `/api/order/cancel`
- PUT `/api/order/pay`
- PUT `/api/order/finish`

#### 6. 评价接口
- GET `/api/review/page`
- GET `/api/review/my-page`
- POST `/api/review`
- PUT `/api/review/reply`
- PUT `/api/review/status`

#### 7. 投诉接口
- GET `/api/complaint/page`
- GET `/api/complaint/my-page`
- POST `/api/complaint`
- PUT `/api/complaint/handle`

#### 8. 看板接口
- GET `/api/dashboard/stats`
- GET `/api/dashboard/trend`

### 项目结构
后端：
```
068-backend/
├── sql/
├── src/main/java/com/travel/
│   ├── common/
│   ├── config/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── controller/
│   ├── utils/
│   └── vo/
└── src/main/resources/
```

前端：
```
068-frontend/
├── src/
│   ├── views/
│   ├── api/
│   ├── router/
│   └── store/
```

## 用户角色
- 管理员：用户管理、景点管理、订单全局查看与导出、评价审核回复、投诉处理、运营统计与趋势分析
- 普通用户：个人资料、出行人、收藏、订单、评价、投诉（支持附件）

## 默认账号
- admin / 123456
- user / 123456
