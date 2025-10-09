# 助农精准扶贫平台

## 项目简介

基于 Spring Boot 3 + MyBatis-Plus + MySQL + jQuery + Bootstrap 开发的一体化农产品交易系统，实现农户直销、消费者直购、管理监管的完整闭环。

## 技术栈

- 后端：Spring Boot 3.2.0 + MyBatis-Plus 3.5.5
- 数据库：MySQL 8.0
- 前端：jQuery 3.6.0 + Bootstrap 5.1.3 + Thymeleaf
- 安全：JWT Token 认证
- 构建：Maven

## 功能模块

### 1. 用户系统
- 用户注册（支持农户/消费者角色选择）
- 用户登录（JWT Token 认证）
- 修改密码
- 个人信息管理
- 用户状态管理（启用/禁用）

### 2. 农产品管理
- 农户发布商品
- 商品信息编辑
- 商品上下架
- 商品删除
- 商品搜索（支持关键词、分类、价格排序）
- 商品详情展示
- 商品库存管理

### 3. 商品审核
- 管理员审核待发布商品
- 审核通过/拒绝
- 审核状态查询

### 4. 分类管理
- 商品分类增删改查
- 分类排序
- 分类树形展示

### 5. 购物车
- 添加商品到购物车
- 修改购物车数量
- 删除购物车商品
- 购物车结算

### 6. 订单管理
- 创建订单
- 订单支付（模拟）
- 订单发货
- 订单收货
- 订单状态管理（待支付/已支付/已发货/已完成/已取消）
- 买家查看订单
- 农户查看订单
- 订单详情查询

### 7. 评价系统
- 商品评价（1-5 星评分）
- 评价内容展示
- 评价时间记录

### 8. 数据统计
- 总用户数统计
- 农户数量统计
- 商品数量统计
- 订单总额统计
- 农户销售额排行榜
- 扶贫效果数据分析

### 9. 公告系统
- 平台公告发布
- 公告类型管理（公告/新闻/政策）
- 公告状态控制（发布/隐藏）
- 首页公告展示
- 公告列表查询
- 公告增删改查

### 10. 权限控制
- 基于 JWT 的认证机制
- 路径拦截器
- 角色权限验证（农户/消费者/管理员）

## 项目结构

```
003-backend/
├── src/main/
│   ├── java/com/xiaou/
│   │   ├── common/              # 通用类
│   │   │   ├── Result.java      # 统一返回结果
│   │   │   ├── BusinessException.java  # 业务异常
│   │   │   └── GlobalExceptionHandler.java  # 全局异常处理
│   │   ├── config/              # 配置类
│   │   │   ├── MyBatisPlusConfig.java
│   │   │   ├── MyMetaObjectHandler.java
│   │   │   ├── JwtInterceptor.java
│   │   │   └── WebConfig.java
│   │   ├── controller/          # 控制器
│   │   │   ├── AuthController.java
│   │   │   ├── UserController.java
│   │   │   ├── ProductController.java
│   │   │   ├── OrderController.java
│   │   │   ├── CommentController.java
│   │   │   ├── CategoryController.java
│   │   │   ├── StatisticsController.java
│   │   │   └── ViewController.java
│   │   ├── entity/              # 实体类
│   │   │   ├── User.java
│   │   │   ├── Product.java
│   │   │   ├── Order.java
│   │   │   ├── OrderItem.java
│   │   │   ├── Category.java
│   │   │   └── Comment.java
│   │   ├── mapper/              # 数据访问层
│   │   ├── service/             # 业务逻辑层
│   │   │   ├── impl/
│   │   │   ├── UserService.java
│   │   │   ├── ProductService.java
│   │   │   ├── OrderService.java
│   │   │   ├── CommentService.java
│   │   │   ├── CategoryService.java
│   │   │   └── OrderItemService.java
│   │   ├── utils/               # 工具类
│   │   │   ├── JwtUtil.java
│   │   │   └── PasswordUtil.java
│   │   └── FarmPlatformApplication.java  # 启动类
│   └── resources/
│       ├── application.yml      # 配置文件
│       ├── sql/init.sql         # 数据库初始化脚本
│       ├── static/              # 静态资源
│       └── templates/           # 前端页面
│           ├── index.html
│           ├── login.html
│           ├── register.html
│           ├── products.html
│           ├── product_detail.html
│           ├── cart.html
│           ├── order.html
│           ├── farmer_dashboard.html
│           └── admin_dashboard.html
└── pom.xml
```

## 数据库设计

### 核心表
- `user` - 用户表（用户名、密码、角色、真实姓名、电话、地址、状态）
- `category` - 分类表（分类名称、父级分类、排序）
- `product` - 商品表（商品名称、分类、价格、库存、图片、描述、农户ID、审核状态、产地）
- `order` - 订单表（订单号、买家、总金额、状态、收货信息）
- `order_item` - 订单项表（订单ID、商品ID、数量、单价、农户ID）
- `comment` - 评论表（商品ID、用户ID、评分、内容）
- `notice` - 公告表（标题、内容、类型、状态）

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置
```bash
# 创建数据库
mysql -u root -p

# 执行初始化脚本
source src/main/resources/sql/init.sql
```

### 3. 修改配置
编辑 `src/main/resources/application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/farm_platform
    username: root
    password: your_password
```

### 4. 启动项目
```bash
mvn clean install
mvn spring-boot:run
```

### 5. 访问系统
浏览器访问：http://localhost:8080

## 测试账号

### 管理员账号
- 用户名：`admin`
- 密码：`admin123`
- 权限：完整的系统管理权限

### 农户账号
- 用户名：`farmer1`
- 密码：`admin123`
- 权限：发布商品、管理订单

- 用户名：`farmer2`
- 密码：`admin123`
- 权限：发布商品、管理订单

### 消费者账号
- 用户名：`buyer1`
- 密码：`admin123`
- 权限：购买商品、下单、评价

- 用户名：`buyer2`
- 密码：`admin123`
- 权限：购买商品、下单、评价

## API 接口

### 认证接口
- POST `/api/auth/login` - 用户登录
- POST `/api/auth/register` - 用户注册
- POST `/api/auth/change-password` - 修改密码
- GET `/api/auth/current` - 获取当前用户信息

### 用户管理
- GET `/api/users/list` - 用户列表（管理员）
- PUT `/api/users/{id}` - 更新用户信息（管理员）
- PUT `/api/users/{id}/status` - 更新用户状态（管理员）
- GET `/api/users/{id}` - 获取用户信息
- PUT `/api/users/profile` - 更新个人信息

### 商品管理
- GET `/api/products/list` - 商品列表（支持搜索、分类、排序）
- GET `/api/products/detail/{id}` - 商品详情
- GET `/api/products/my-products` - 我的商品（农户）
- POST `/api/products` - 发布商品（农户）
- PUT `/api/products/{id}` - 更新商品（农户）
- DELETE `/api/products/{id}` - 删除商品（农户）
- GET `/api/products/pending` - 待审核商品（管理员）
- PUT `/api/products/{id}/approve` - 审核商品（管理员）

### 分类管理
- GET `/api/categories/tree` - 分类树
- POST `/api/categories` - 创建分类（管理员）
- PUT `/api/categories/{id}` - 更新分类（管理员）
- DELETE `/api/categories/{id}` - 删除分类（管理员）

### 订单管理
- POST `/api/orders` - 创建订单
- GET `/api/orders/my-orders` - 我的订单
- GET `/api/orders/{id}` - 订单详情
- PUT `/api/orders/{id}/status` - 更新订单状态

### 评论管理
- POST `/api/comments` - 发表评论
- GET `/api/comments/product/{productId}` - 商品评论列表

### 数据统计
- GET `/api/statistics/overview` - 数据概览（管理员）
- GET `/api/statistics/farmer-sales` - 农户销售排行（管理员）

### 公告管理
- GET `/api/notices/list` - 公告列表（公开）
- GET `/api/notices/{id}` - 公告详情（公开）
- GET `/api/notices/all` - 所有公告（管理员）
- POST `/api/notices` - 发布公告（管理员）
- PUT `/api/notices/{id}` - 更新公告（管理员）
- DELETE `/api/notices/{id}` - 删除公告（管理员）

## 功能特色

1. **三端分离角色**：农户、消费者、管理员三种角色，各司其职
2. **完整交易流程**：从商品发布、审核、购买、支付到发货、收货
3. **商品审核机制**：管理员审核保证商品质量
4. **实时库存管理**：下单自动扣减库存，防止超卖
5. **评价反馈系统**：消费者可对商品进行评分和评价
6. **数据统计分析**：实时统计平台数据，分析扶贫效果
7. **公告系统**：支持平台公告、新闻、政策发布
8. **响应式设计**：支持 PC 和移动端访问
9. **安全认证**：基于 JWT 的无状态认证机制

## 业务流程

### 农户发布商品
1. 农户登录系统
2. 进入"发布商品"页面
3. 填写商品信息（名称、分类、价格、库存、产地、描述等）
4. 提交审核
5. 等待管理员审核通过
6. 商品上架展示

### 消费者购物
1. 浏览商品列表
2. 搜索/筛选商品
3. 查看商品详情
4. 加入购物车
5. 填写收货信息
6. 提交订单
7. 支付订单
8. 等待发货
9. 确认收货
10. 评价商品

### 订单处理
1. 消费者提交订单（状态：待支付）
2. 消费者完成支付（状态：已支付）
3. 农户查看订单并发货（状态：已发货）
4. 消费者确认收货（状态：已完成）

## 项目亮点

1. **助农扶贫**：直接连接农户与消费者，减少中间环节，增加农户收入
2. **信息透明**：商品信息、价格、产地公开透明
3. **质量保障**：管理员审核机制保证商品质量
4. **数据驱动**：通过数据统计分析扶贫效果
5. **操作简便**：界面简洁，操作流畅，易于上手

## 开发者

李子凡

## 许可证

本项目仅用于毕业设计学习用途。

