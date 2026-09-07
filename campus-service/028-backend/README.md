# 校园共享自行车租赁系统 - 后端

基于SpringBoot的校园场景下共享自行车租赁系统

## 技术栈

- **后端框架**: Spring Boot 3.2.0
- **ORM框架**: MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0
- **缓存**: Redis 6.x
- **身份认证**: JWT
- **工具类**: Hutool 5.8.23

## 项目结构

```
src/main/java/com/xiaou/bike/
├── CampusBikeApplication.java  # 启动类
├── common/                     # 通用类
│   ├── Result.java            # 统一响应
│   ├── BusinessException.java # 业务异常
│   └── GlobalExceptionHandler.java # 全局异常处理
├── config/                     # 配置类
│   ├── MyBatisPlusConfig.java # MyBatis-Plus配置
│   └── WebConfig.java         # Web配置
├── controller/                 # 控制器
│   ├── UserController.java    # 用户接口
│   ├── RentalController.java  # 租借接口
│   ├── OrderController.java   # 订单接口
│   ├── StationController.java # 停车点接口
│   ├── WalletController.java  # 钱包接口
│   ├── FaultController.java   # 故障上报接口
│   └── AdminController.java   # 管理端接口
├── dto/                        # 数据传输对象
├── entity/                     # 实体类
├── interceptor/                # 拦截器
│   └── JwtInterceptor.java    # JWT认证拦截器
├── mapper/                     # Mapper接口
├── service/                    # 服务类
├── util/                       # 工具类
│   ├── JwtUtil.java           # JWT工具类
│   └── UserContext.java       # 用户上下文
└── vo/                         # 视图对象
```

## 快速开始

### 1. 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 6.x+
- Maven 3.6+

### 2. 数据库初始化

```sql
-- 执行SQL脚本
source sql/schema.sql
source sql/data.sql
```

### 3. 修改配置

修改 `src/main/resources/application.yml` 中的数据库和Redis连接配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_bike
    username: root
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
```

### 4. 启动项目

```bash
mvn spring-boot:run
```

## API接口

### 用户端接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户注册 | POST | /user/register | 新用户注册 |
| 用户登录 | POST | /user/login | 用户登录 |
| 获取用户信息 | GET | /user/info | 获取当前用户信息 |
| 停车点列表 | GET | /station/list | 获取所有停车点 |
| 扫码租车 | POST | /rental/start | 开始租车 |
| 还车 | POST | /rental/end | 确认还车 |
| 订单列表 | GET | /order/list | 获取用户订单 |
| 钱包余额 | GET | /wallet/balance | 获取钱包信息 |
| 充值 | POST | /wallet/recharge | 钱包充值 |
| 故障上报 | POST | /fault/report | 上报车辆故障 |

### 管理端接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 管理员登录 | POST | /admin/login | 管理员登录 |
| 数据统计 | GET | /admin/stats/overview | 获取统计概览 |
| 用户列表 | GET | /admin/user/list | 获取用户列表 |
| 车辆列表 | GET | /admin/bike/list | 获取车辆列表 |
| 停车点列表 | GET | /admin/station/list | 获取停车点列表 |
| 订单列表 | GET | /admin/order/list | 获取订单列表 |
| 故障列表 | GET | /admin/fault/list | 获取故障列表 |

## 默认账号

### 管理员
- 用户名: admin
- 密码: admin123

### 测试用户
- 用户名: testuser
- 密码: 123456

## 计费规则

- 押金: 99元（信用分≥600可免押金）
- 收费标准: 1元/30分钟，不足30分钟按30分钟计算
- 日封顶: 20元
- 夜间优惠: 22:00-06:00 享8折

## 信用规则

| 行为 | 信用分变动 |
|------|-----------|
| 初始信用分 | 100分 |
| 正常还车 | +1分 |
| 规范停车 | +2分 |
| 上报故障 | +5分 |
| 超时未还 | -10分 |
| 违规停车 | -5分 |
| 损坏车辆 | -20分 |
