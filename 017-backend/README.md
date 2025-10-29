# 高校自助点餐系统

基于 Spring Boot + Thymeleaf + jQuery + Bootstrap 5 的一体化校园点餐系统。

## 项目简介

本系统是一个完整的校园自助点餐平台，采用前后端一体化架构，支持学生在线点餐、商家接单管理、平台后台管理等功能。

### 技术栈

- **后端**: Spring Boot 3.2.0 + MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0
- **前端**: Thymeleaf + jQuery 3.7.0 + Bootstrap 5.3.0
- **工具**: Hutool + JWT + Lombok

## 功能特性

### 三端架构

1. **学生端**
   - 用户注册登录
   - 浏览商家和菜品
   - 购物车管理
   - 在线下单支付
   - 订单管理
   - 评价系统

2. **商家端**
   - 商家登录
   - 菜品管理
   - 订单处理
   - 营业状态管理
   - 数据统计

3. **管理端**
   - 管理员登录
   - 用户管理
   - 商家审核
   - 订单监控
   - 数据统计

## 快速开始

### 1. 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置

#### 创建数据库

```sql
CREATE DATABASE campus_ordering DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 执行SQL脚本

按顺序执行以下SQL文件：

```bash
1. src/main/resources/sql/schema.sql  # 创建表结构
2. src/main/resources/sql/data.sql    # 初始化数据
```

#### 修改数据库配置

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_ordering?useUnicode=true&characterEncoding=utf-8
    username: root
    password: root  # 修改为你的数据库密码
```

### 3. 运行项目

```bash
# 编译项目
mvn clean install

# 运行项目
mvn spring-boot:run
```

或者直接运行主类：`com.xiaou.ordering.OrderingApplication`

### 4. 访问系统

项目启动后访问：http://localhost:8080

## 测试账号

详细测试账号请查看 [ACCOUNTS.md](ACCOUNTS.md)

### 快速登录

**学生账号**:
- 学号/手机号: `20210001` 或 `13800000001`
- 密码: `123456`

**商家账号**:
- 联系电话: `13900000001`
- 密码: `123456`

**管理员账号**:
- 用户名: `admin`
- 密码: `admin123`

## 项目结构

```
017-backend/
├── src/main/
│   ├── java/com/xiaou/ordering/
│   │   ├── OrderingApplication.java    # 主启动类
│   │   ├── common/                     # 公共类
│   │   │   ├── Result.java
│   │   │   ├── BusinessException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── config/                     # 配置类
│   │   │   └── WebConfig.java
│   │   ├── controller/                 # 控制器
│   │   │   ├── IndexController.java
│   │   │   ├── AuthController.java
│   │   │   ├── MerchantController.java
│   │   │   └── CartController.java
│   │   ├── dto/                        # 数据传输对象
│   │   │   ├── LoginRequest.java
│   │   │   └── RegisterRequest.java
│   │   ├── entity/                     # 实体类（12个）
│   │   │   ├── User.java
│   │   │   ├── Merchant.java
│   │   │   ├── Dish.java
│   │   │   ├── Orders.java
│   │   │   └── ...
│   │   ├── mapper/                     # Mapper接口
│   │   ├── service/                    # 业务逻辑
│   │   │   ├── AuthService.java
│   │   │   ├── MerchantService.java
│   │   │   ├── DishService.java
│   │   │   └── CartService.java
│   │   └── util/                       # 工具类
│   │       ├── JwtUtil.java
│   │       └── UserContext.java
│   └── resources/
│       ├── application.yml             # 配置文件
│       ├── sql/                        # SQL脚本
│       │   ├── schema.sql
│       │   └── data.sql
│       └── templates/                  # Thymeleaf模板
│           ├── index.html
│           ├── user/
│           │   ├── login.html
│           │   └── register.html
│           ├── merchant/
│           │   ├── list.html
│           │   ├── detail.html
│           │   └── login.html
│           └── admin/
│               └── login.html
├── pom.xml                             # Maven配置
├── README.md                           # 项目说明
└── ACCOUNTS.md                         # 测试账号
```

## 核心功能说明

### 1. 用户注册登录

- 学生注册需要提供学号、姓名、手机号、院系、专业、宿舍等信息
- 支持学号或手机号登录
- 密码使用 Hutool BCrypt 加密

### 2. 商家浏览

- 首页展示推荐商家和热门菜品
- 支持按分类筛选商家（中餐、西餐、小吃、饮品）
- 显示商家评分、月销量、起送价、配送费等信息

### 3. 菜品点餐

- 查看商家详情和菜品列表
- 推荐菜品标记
- 显示原价和现价（折扣信息）
- 添加菜品到购物车

### 4. 购物车管理

- 添加、修改数量、删除菜品
- 实时计算总价
- 支持清空购物车

### 5. 多商家支持

- 系统支持多个商家入驻
- 每个商家独立管理菜品
- 独立的评分和销量统计

## 数据库设计

系统包含 12 张数据表：

1. `user` - 用户表
2. `merchant` - 商家表
3. `category` - 菜品分类表
4. `dish` - 菜品表
5. `orders` - 订单表
6. `order_detail` - 订单明细表
7. `review` - 评价表
8. `favorite` - 收藏表
9. `cart` - 购物车表
10. `coupon` - 优惠券表
11. `user_coupon` - 用户优惠券表
12. `admin` - 管理员表

详细表结构请查看 `src/main/resources/sql/schema.sql`

## API接口

### 认证接口

- `POST /api/auth/user/register` - 用户注册
- `POST /api/auth/user/login` - 用户登录
- `POST /api/auth/merchant/login` - 商家登录
- `POST /api/auth/admin/login` - 管理员登录

### 购物车接口

- `POST /api/cart/add` - 添加到购物车
- `GET /api/cart/list/{userId}` - 获取购物车
- `PUT /api/cart/{id}` - 更新数量
- `DELETE /api/cart/{id}` - 删除商品
- `DELETE /api/cart/clear/{userId}` - 清空购物车

### 页面路由

- `GET /` 或 `/index` - 首页
- `GET /user/login` - 学生登录页
- `GET /user/register` - 学生注册页
- `GET /merchant/list` - 商家列表页
- `GET /merchant/detail/{id}` - 商家详情页
- `GET /merchant/login` - 商家登录页
- `GET /admin/login` - 管理员登录页

## 初始化数据

系统已预置测试数据：

- 3个测试商家（中餐、西餐、小吃）
- 20+个测试菜品
- 10个菜品分类
- 2个测试用户（已充值余额）
- 1个管理员账号

## 特色功能

1. **取餐号系统** - 自动生成取餐号，避免排队拥挤
2. **多商家支持** - 类似美团外卖的多商家模式
3. **推荐系统** - 推荐商家和热门菜品展示
4. **实时购物车** - jQuery实现的实时购物车功能
5. **响应式设计** - Bootstrap 5 响应式布局，适配移动端
6. **优惠活动** - 支持满减券、折扣券等优惠

## 开发说明

### 添加新功能

1. 在 `entity` 包中创建实体类
2. 在 `mapper` 包中创建 Mapper 接口
3. 在 `service` 包中编写业务逻辑
4. 在 `controller` 包中创建控制器
5. 在 `templates` 中创建页面模板

### 密码加密测试

运行测试类验证密码加密：

```bash
mvn test -Dtest=PasswordTest
```

## 注意事项

1. 首次运行请确保已执行 SQL 初始化脚本
2. 数据库连接信息需要根据实际环境修改
3. JWT密钥建议在生产环境中修改
4. 图片URL为示例数据，实际使用需对接OSS或本地存储

## 扩展功能（PRD规划）

完整功能请参考 [PRD/PRD.md](PRD/PRD.md)，包括：

- 订单支付流程
- 取餐号管理
- 评价系统
- 优惠券系统
- 收藏功能
- 搜索功能
- 数据统计
- 商家端完整功能
- 管理后台完整功能

## 许可证

MIT License

## 技术支持

如有问题，请查看：
- 项目文档：`PRD/PRD.md`
- 测试账号：`ACCOUNTS.md`
- 代码注释

