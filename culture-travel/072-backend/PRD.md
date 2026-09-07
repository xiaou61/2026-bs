# 072 - 基于SpringBoot和Vue的哈尔滨文旅系统

## 项目概述

### 项目简介
哈尔滨文旅综合服务平台，聚焦冰雪之城哈尔滨的旅游文化推广。涵盖冰雪景点、历史建筑、特色美食、住宿推荐、旅游路线、活动赛事、游记分享等全方位文旅服务，为游客提供一站式哈尔滨旅游攻略与预订服务。

### 核心功能
- 景点信息浏览与门票预订
- 特色旅游路线规划
- 酒店住宿推荐
- 哈尔滨美食攻略
- 文旅活动报名
- 游记分享社区
- 评价互动体系
- 管理员运营后台

### 技术栈
**后端**: Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL 8.0 + Redis + JWT 0.9.1 + Hutool 5.8.25
**前端**: Vue 3.4.0 + Element Plus 2.4.4 + Pinia 2.1.7 + Vue Router 4.2.5 + Axios 1.6.2 + ECharts 5.4.3

---

## 用户角色

- **管理员 (admin)**: 全平台管理权限，景点/酒店/餐厅/活动/游记审核，数据统计看板
- **普通用户 (user)**: 浏览景点路线、门票预订、游记发布、评价互动、收藏

---

## 默认账号

- admin / 123456 （管理员）
- user / 123456 （普通用户，余额200元）
- tourist / 123456 （普通用户，余额50元）

---

## 功能需求

### 1. 用户模块
- 注册/登录，JWT Token认证
- 个人信息管理（头像、昵称、手机、邮箱）
- 钱包余额充值与消费记录
- 我的订单列表
- 我的收藏（景点/路线/酒店/餐厅）
- 我的游记

### 2. 景点管理
- 景点CRUD（名称、简介、详情、位置、分类、封面图、图片集、开放时间、门票价格、评分）
- 景点分类：冰雪景观/历史建筑/自然风光/博物馆/主题公园/特色街区
- 景点搜索与筛选
- 景点详情展示（图片轮播、评价列表、相关路线）
- 景点收藏
- 浏览量统计

### 3. 旅游路线
- 路线CRUD（名称、描述、天数、难度、分类、封面图、预估花费）
- 路线包含景点列表（有序，含游玩时长建议）
- 路线搜索与筛选（按天数/分类）
- 路线详情展示
- 路线收藏
- 路线点赞

### 4. 门票预订
- 票种管理（景点关联、票种名称、价格、库存、每人限购数量）
- 在线预订（选择日期、票种、数量）
- 余额支付
- 订单状态：待支付/已支付/已使用/已取消/已退款
- 订单列表与详情
- 申请退款

### 5. 住宿推荐
- 酒店CRUD（名称、地址、类型、价格区间、简介、封面图、设施标签、评分）
- 酒店类型：豪华酒店/经济型/特色民宿/青年旅社
- 酒店搜索与筛选
- 酒店收藏

### 6. 美食攻略
- 餐厅CRUD（名称、地址、菜系类型、人均消费、简介、封面图、推荐菜品、评分）
- 菜系类型：东北菜/烧烤/冰品/西餐/日韩料理/火锅
- 哈尔滨特色标签：锅包肉/红肠/马迭尔冰棍/杀猪菜/哈啤
- 餐厅搜索与筛选
- 餐厅收藏

### 7. 活动与赛事
- 活动CRUD（名称、描述、地点、开始/结束时间、封面图、报名截止时间、名额）
- 活动状态：报名中/进行中/已结束/已取消
- 用户报名活动
- 活动报名记录

### 8. 游记分享
- 游记发布（标题、内容、封面图、标签、关联景点）
- 游记审核（待审核/已发布/已拒绝）
- 游记列表展示（按发布时间/点赞数）
- 游记详情（内容渲染、点赞、评论）
- 游记收藏

### 9. 评价系统
- 对景点/酒店/餐厅发表评价（评分1-5星、文字内容、图片）
- 评价审核（管理员）
- 评价列表展示
- 自动更新目标对象的平均评分

### 10. 公告管理
- 系统公告发布（标题、内容、类型）
- 公告列表展示
- 首页置顶公告

### 11. 数据统计（管理员）
- 用户注册趋势
- 景点访问量Top10
- 订单收入统计（按月）
- 游记数量统计
- 活动报名统计

---

## 技术设计

### 数据库设计（12张表）

**user**（用户表）
- id, username, password, nickname, avatar, phone, email, role(admin/user), balance, status, created_at

**scenic_spot**（景点表）
- id, name, description, detail, location, category, cover_img, images, open_time, close_time, ticket_price, rating, view_count, status, created_at

**route**（旅游路线表）
- id, title, description, days, difficulty(easy/medium/hard), category, cover_img, estimated_cost, like_count, status, created_at, user_id

**route_spot**（路线景点关联表）
- id, route_id, spot_id, order_num, stay_hours

**ticket_type**（票种表）
- id, spot_id, name, price, stock, max_per_order, description, status

**ticket_order**（门票订单表）
- id, user_id, spot_id, ticket_type_id, ticket_date, quantity, total_price, status(pending/paid/used/cancelled/refunded), created_at

**hotel**（酒店表）
- id, name, address, type, price_min, price_max, description, cover_img, facilities, rating, status, created_at

**restaurant**（餐厅表）
- id, name, address, cuisine_type, price_per_person, description, cover_img, recommended_dish, tags, rating, status, created_at

**activity**（活动表）
- id, title, description, location, start_time, end_time, cover_img, register_deadline, max_participants, current_participants, status, created_at

**activity_registration**（活动报名表）
- id, activity_id, user_id, register_time, status

**travel_note**（游记表）
- id, user_id, title, content, cover_img, tags, spot_id, like_count, view_count, status(pending/published/rejected), created_at

**review**（评价表）
- id, user_id, target_id, target_type(spot/hotel/restaurant), rating, content, images, status, created_at

**announcement**（公告表）
- id, title, content, type, is_top, status, created_at

---

### 项目结构

后端（072-backend）:
```
src/main/java/com/harbin/tourism/
├── TourismApplication.java
├── common/
│   ├── Result.java
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
├── config/
│   ├── JwtInterceptor.java
│   ├── WebMvcConfig.java
│   ├── MybatisPlusConfig.java
│   └── RedisConfig.java
├── utils/
│   └── JwtUtils.java
├── entity/
│   ├── User.java, ScenicSpot.java, Route.java, RouteSpot.java
│   ├── TicketType.java, TicketOrder.java
│   ├── Hotel.java, Restaurant.java
│   ├── Activity.java, ActivityRegistration.java
│   ├── TravelNote.java, Review.java, Announcement.java
├── mapper/
├── service/
└── controller/
```

前端（072-frontend）:
```
src/views/
├── Login.vue
├── Layout.vue
├── Dashboard.vue
├── Spot/SpotList.vue, SpotDetail.vue
├── Route/RouteList.vue, RouteDetail.vue
├── Ticket/TicketOrder.vue, MyOrders.vue
├── Hotel/HotelList.vue
├── Restaurant/RestaurantList.vue
├── Activity/ActivityList.vue
├── Note/NoteList.vue, NoteDetail.vue, NoteEdit.vue
├── Review/ReviewList.vue
├── User/Profile.vue, MyFavorites.vue, MyWallet.vue
└── Announcement/AnnouncementList.vue
```
