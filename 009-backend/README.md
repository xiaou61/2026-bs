# 校园快递代收管理系统 - 后端

## 项目简介
基于 Spring Boot 3 + MyBatis-Plus 开发的校园快递代收管理系统后端服务。

## 技术栈
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT 认证
- Lombok
- Hutool 工具库

## 项目结构
```
009-backend/
├── src/main/java/com/xiaou/
│   ├── ExpressSystemApplication.java  # 启动类
│   ├── common/
│   │   └── Result.java                # 统一返回结果
│   ├── config/
│   │   └── WebConfig.java             # Web配置（CORS、拦截器）
│   ├── controller/                    # 控制器层
│   │   ├── AuthController.java        # 认证接口
│   │   ├── UserController.java        # 用户管理
│   │   ├── ExpressController.java     # 快递管理
│   │   ├── StationController.java     # 代收点管理
│   │   ├── ExpressCompanyController.java  # 快递公司管理
│   │   ├── NotificationController.java    # 通知管理
│   │   ├── OverdueController.java     # 超期管理
│   │   ├── StatsController.java       # 统计分析
│   │   └── SystemConfigController.java    # 系统配置
│   ├── entity/                        # 实体类
│   │   ├── User.java                  # 用户
│   │   ├── Express.java               # 快递
│   │   ├── Station.java               # 代收点
│   │   ├── ExpressCompany.java        # 快递公司
│   │   ├── OverdueRecord.java         # 超期记录
│   │   ├── Notification.java          # 通知
│   │   ├── SystemConfig.java          # 系统配置
│   │   └── OperationLog.java          # 操作日志
│   ├── mapper/                        # Mapper接口
│   ├── service/                       # Service层
│   ├── interceptor/
│   │   └── JwtInterceptor.java        # JWT拦截器
│   └── utils/
│       ├── JwtUtil.java               # JWT工具类
│       └── MD5Util.java               # MD5加密工具
├── src/main/resources/
│   ├── application.yml                # 配置文件
│   └── sql/
│       └── express_system.sql         # 数据库初始化脚本
├── ACCOUNTS.md                        # 测试账号文档
├── PRD/
│   └── PRD.md                         # 产品需求文档
├── pom.xml                            # Maven配置
└── README.md                          # 项目说明
```

## 快速启动

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置
```bash
# 创建数据库并导入数据
mysql -u root -p < src/main/resources/sql/express_system.sql
```

### 3. 修改配置
修改 `src/main/resources/application.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/express_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root  # 修改为你的数据库密码
```

### 4. 启动项目
```bash
# 方式一：Maven命令
mvn spring-boot:run

# 方式二：IDEA运行
右键点击 ExpressSystemApplication.java -> Run
```

### 5. 访问接口
启动成功后访问: http://localhost:8009

## 核心功能

### 1. 认证模块 (/api/auth)
- POST /api/auth/register - 用户注册
- POST /api/auth/login - 用户登录
- GET /api/auth/info - 获取当前用户信息

### 2. 用户管理 (/api/user)
- GET /api/user/list - 用户列表
- GET /api/user/{id} - 用户详情
- PUT /api/user/{id} - 更新用户
- PUT /api/user/{id}/status - 修改用户状态
- PUT /api/user/{id}/role - 修改用户角色
- PUT /api/user/{id}/password - 修改密码

### 3. 快递管理 (/api/express)
- POST /api/express/in - 快递入库
- GET /api/express/list - 快递列表
- GET /api/express/{id} - 快递详情
- POST /api/express/verify-pickup - 验证取件码
- POST /api/express/pickup - 取件核销
- GET /api/express/my-packages - 我的待取快递
- GET /api/express/my-history - 我的取件历史

### 4. 代收点管理 (/api/station)
- GET /api/station/list - 代收点列表
- GET /api/station/all - 所有代收点
- GET /api/station/{id} - 代收点详情
- POST /api/station - 添加代收点
- PUT /api/station/{id} - 更新代收点
- DELETE /api/station/{id} - 删除代收点
- GET /api/station/{id}/stats - 代收点统计

### 5. 快递公司管理 (/api/company)
- GET /api/company/list - 快递公司列表
- GET /api/company/all - 所有快递公司
- GET /api/company/{id} - 快递公司详情
- POST /api/company - 添加快递公司
- PUT /api/company/{id} - 更新快递公司
- DELETE /api/company/{id} - 删除快递公司

### 6. 通知管理 (/api/notification)
- GET /api/notification/list - 通知列表
- GET /api/notification/unread-count - 未读消息数量
- PUT /api/notification/{id}/read - 标记已读
- PUT /api/notification/read-all - 全部标记已读

### 7. 超期管理 (/api/overdue)
- GET /api/overdue/list - 超期快递列表
- GET /api/overdue/my - 我的超期快递
- GET /api/overdue/records - 超期记录

### 8. 统计分析 (/api/stats)
- GET /api/stats/overview - 数据概览
- GET /api/stats/trend - 趋势分析
- GET /api/stats/company-rank - 快递公司排行
- GET /api/stats/station-rank - 代收点排行

### 9. 系统配置 (/api/config)
- GET /api/config/list - 配置列表
- GET /api/config/{key} - 获取配置
- PUT /api/config - 更新配置
- POST /api/config - 添加配置

## 测试账号
详见 [ACCOUNTS.md](./ACCOUNTS.md)

常用测试账号：
- 管理员: admin / 123456
- 代收点管理员: station1 / 123456
- 学生: student1 / 123456

## 接口调用说明

### 1. 登录获取Token
```bash
curl -X POST http://localhost:8009/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

返回：
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "id": 1,
      "username": "admin",
      "realName": "系统管理员",
      "role": "ADMIN"
    }
  }
}
```

### 2. 携带Token访问接口
```bash
curl -X GET http://localhost:8009/api/express/my-packages \
  -H "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 业务规则

### 超期规则
- 免费保管期: 3天
- 超期4-7天: 1元/天
- 超期8天及以上: 2元/天
- 超期15天: 标记为长期滞留

### 取件流程
1. 快递入库，生成6位取件码
2. 系统自动发送通知给收件人
3. 收件人到代收点出示取件码
4. 工作人员验证取件码
5. 系统计算超期费用（如有）
6. 确认取件，更新快递状态

### 权限说明
- ADMIN: 所有权限
- STATION_ADMIN: 快递入库、取件、代收点管理
- COURIER: 快递入库
- STUDENT: 查看快递、取件

## 开发说明

### 数据库设计
- 8张核心表
- 使用MyBatis-Plus自动维护时间字段
- 所有表添加了必要的索引

### JWT认证
- Token有效期: 24小时
- 密钥: express-system-secret-key-2024
- 登录接口和部分公开接口不需要Token

### 跨域配置
- 已配置CORS，允许前端跨域访问
- 支持的请求方法: GET、POST、PUT、DELETE、OPTIONS

## 常见问题

### 1. 数据库连接失败
- 检查MySQL服务是否启动
- 检查数据库配置是否正确
- 确认数据库已创建

### 2. 端口冲突
修改 `application.yml` 中的端口：
```yaml
server:
  port: 8009  # 修改为其他端口
```

### 3. Token过期
重新登录获取新Token

## 后续优化建议
1. 集成短信服务实现真实短信通知
2. 添加文件上传功能（快递图片）
3. 实现批量导入Excel功能
4. 添加定时任务自动计算超期
5. 完善操作日志记录

## 联系方式
如有问题，请查看 [PRD.md](./PRD/PRD.md) 获取详细文档

