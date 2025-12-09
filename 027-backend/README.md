# 027 - 线上理发预约系统

一个完整的在线理发预约系统，采用前后端一体化架构（Spring Boot + Vue 3），支持用户预约、会员积分、订单支付、评价等核心功能。

## 技术栈

### 后端
- **Spring Boot 3.2.0** - 主框架
- **MyBatis-Plus 3.5.5** - ORM框架
- **MySQL 8.0** - 数据库
- **JWT 0.12.3** - 认证授权
- **Hutool** - 工具类库

### 前端
- **Vue 3.4.0** - 前端框架
- **Element Plus 2.5.1** - UI组件库
- **Vite 5.0** - 构建工具
- **Pinia** - 状态管理
- **Vue Router 4.2.5** - 路由管理
- **Axios 1.6.2** - HTTP客户端

## 核心功能

### 用户端
1. **用户注册登录** - 手机号注册、JWT认证
2. **门店管理** - 门店列表、详情、搜索、排序
3. **理发师管理** - 理发师列表、详情、评分展示
4. **服务项目** - 服务分类、价格、时长展示
5. **在线预约** - 4步预约流程、时段冲突检测、7天提前预约
6. **预约管理** - 我的预约、取消预约、状态查询
7. **会员体系** - 四级会员（普通/银卡/金卡/钻石）、自动升级、专享折扣
8. **积分系统** - 消费积分、签到积分、评价积分
9. **订单支付** - 余额支付、会员折扣、订单列表
10. **评价系统** - 5星评价、图片上传、标签选择

### 管理端（核心后端接口）
- 预约确认/取消
- 预约状态流转（6种状态）
- 订单管理
- 评价管理

## 项目结构

```
027-backend/                    # 后端项目
├── src/main/
│   ├── java/com/xiaou/hair/
│   │   ├── HairSalonApplication.java    # 启动类
│   │   ├── common/                      # 公共类
│   │   │   ├── Result.java             # 统一响应
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── config/                      # 配置类
│   │   │   └── WebConfig.java          # Web配置
│   │   ├── controller/                  # 控制器（8个）
│   │   ├── dto/                         # 数据传输对象
│   │   ├── entity/                      # 实体类（7个）
│   │   ├── interceptor/                 # 拦截器
│   │   │   └── JwtInterceptor.java
│   │   ├── mapper/                      # Mapper接口（7个）
│   │   ├── service/                     # 服务类（7个）
│   │   ├── util/                        # 工具类
│   │   │   ├── JwtUtil.java
│   │   │   └── UserContext.java
│   │   └── vo/                          # 视图对象
│   └── resources/
│       ├── application.yml              # 配置文件
│       ├── mapper/                      # MyBatis映射文件
│       └── static/                      # 前端打包输出目录
├── sql/
│   ├── schema.sql                       # 数据库表结构（12张表）
│   └── data.sql                         # 测试数据
└── pom.xml

027-frontend/                   # 前端项目
├── src/
│   ├── api/                    # API接口（4个模块）
│   │   ├── auth.js
│   │   ├── store.js
│   │   ├── hairdresser.js
│   │   └── appointment.js
│   ├── router/                 # 路由配置
│   │   └── index.js
│   ├── store/                  # 状态管理
│   │   └── user.js
│   ├── utils/                  # 工具类
│   │   └── request.js          # Axios封装
│   ├── views/                  # 页面组件（8个页面）
│   │   ├── Login.vue           # 登录注册
│   │   ├── Home.vue            # 首页
│   │   ├── Profile.vue         # 个人中心
│   │   ├── store/              # 门店相关
│   │   │   ├── StoreList.vue
│   │   │   └── StoreDetail.vue
│   │   ├── hairdresser/        # 理发师相关
│   │   │   ├── HairdresserList.vue
│   │   │   └── HairdresserDetail.vue
│   │   └── appointment/        # 预约相关
│   │       ├── CreateAppointment.vue
│   │       └── MyAppointments.vue
│   ├── App.vue
│   └── main.js
├── index.html
├── vite.config.js              # Vite配置（打包到backend）
└── package.json
```

## 数据库设计

12张核心数据表：
1. **user** - 用户表
2. **store** - 门店表
3. **hairdresser** - 理发师表
4. **service** - 服务项目表
5. **appointment** - 预约表
6. **orders** - 订单表
7. **review** - 评价表
8. **schedule** - 排班表
9. **points_log** - 积分记录表
10. **balance_log** - 余额记录表
11. **notification** - 消息通知表
12. **admin** - 管理员表

## 快速开始

### 1. 数据库初始化
```sql
-- 执行schema.sql创建表结构
mysql -u root -p < sql/schema.sql

-- 执行data.sql导入测试数据
mysql -u root -p < sql/data.sql
```

### 2. 后端启动
```bash
cd 027-backend

# 修改application.yml中的数据库配置
# 然后启动项目
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动

### 3. 前端开发模式
```bash
cd 027-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端将在 http://localhost:5173 启动，API请求会代理到后端8080端口

### 4. 生产部署
```bash
cd 027-frontend

# 打包前端（自动输出到027-backend/src/main/resources/static）
npm run build

# 构建后端JAR包
cd ../027-backend
mvn clean package

# 运行JAR包（包含前端静态文件）
java -jar target/hair-salon-1.0.0.jar
```

访问 http://localhost:8080 即可使用完整系统

## 核心业务逻辑

### 预约流程
1. 用户选择门店、理发师、服务项目
2. 选择预约日期和时间（最多提前7天）
3. 系统检查用户未完成预约数（最多3个）
4. 系统检查时段是否冲突
5. 创建预约（状态：待确认）
6. 管理员确认预约（状态：已确认）
7. 服务开始（状态：服务中）
8. 服务完成：
   - 自动创建订单
   - 应用会员折扣
   - 完成支付后添加积分
   - 用户可以评价

### 会员等级系统
- **普通会员**：0-99积分，无折扣
- **银卡会员**：100-499积分，9.5折
- **金卡会员**：500-1999积分，9折
- **钻石会员**：2000+积分，8.5折

积分自动升级，消费1元=1积分

### 状态流转
预约支持6种状态：
1. 待确认 → 已确认（管理员操作）
2. 已确认 → 服务中（开始服务）
3. 服务中 → 已完成（完成服务）
4. 待确认/已确认 → 已取消（用户/管理员取消）
5. 已确认 → 超时取消（超过预约时间15分钟未到店）

## 测试账号

### 用户账号
- 手机号：13800138001
- 密码：123456

### 管理员账号
- 用户名：admin
- 密码：admin123

## 技术亮点

1. **前后端一体化部署** - Vite打包输出到Spring Boot的static目录，单JAR部署
2. **JWT无状态认证** - Token认证，支持用户登录状态保持
3. **智能预约系统** - 时段冲突检测、自动取消超时预约
4. **会员积分体系** - 消费积分、自动升级、会员折扣
5. **订单支付流程** - 余额支付、会员折扣、积分奖励
6. **评价系统** - 自动更新理发师和门店评分
7. **响应式设计** - Element Plus组件适配各种屏幕
8. **RESTful API** - 统一的API设计规范

## API接口文档

主要API接口：

### 认证相关
- POST /api/auth/register - 用户注册
- POST /api/auth/login - 用户登录
- GET /api/auth/info - 获取用户信息
- PUT /api/auth/profile - 更新个人信息

### 门店相关
- GET /api/store/list - 门店列表
- GET /api/store/{id} - 门店详情
- GET /api/store/{id}/hairdressers - 门店理发师列表
- GET /api/store/{id}/services - 门店服务列表

### 预约相关
- POST /api/appointment/create - 创建预约
- GET /api/appointment/my-list - 我的预约
- PUT /api/appointment/{id}/cancel - 取消预约
- GET /api/appointment/available-times - 可预约时段

### 订单相关
- GET /api/order/list - 订单列表
- GET /api/order/{id} - 订单详情
- POST /api/order/{id}/pay - 订单支付

### 评价相关
- POST /api/review/create - 创建评价
- GET /api/review/hairdresser/{id} - 理发师评价
- GET /api/review/store/{id} - 门店评价

## 项目特色

✅ 完整的预约业务流程
✅ 四级会员体系和积分系统
✅ 智能时段冲突检测
✅ 前后端一体化部署
✅ 响应式UI设计
✅ JWT认证授权
✅ MyBatis-Plus简化开发
✅ Element Plus美观UI

## 开发者

本项目由Antigravity AI开发完成，包含：
- 后端7大核心模块
- 前端8个核心页面
- 12张数据库表设计
- 40+个RESTful API接口
- 完整的业务逻辑实现

## 许可证

MIT License
