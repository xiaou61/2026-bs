# 校园事务管理系统

基于 Spring Boot 3 + MyBatis-Plus + JWT 的校园事务管理系统后端

## 项目简介

本系统是一个毕业设计级别的校园事务管理系统，提供学生请假、宿舍报修、公告管理、活动报名等功能。采用前后端分离架构，使用JWT进行身份认证。

## 技术栈

- **框架**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0
- **ORM**: MyBatis-Plus 3.5.5
- **认证**: JWT (jjwt 0.11.5)
- **工具**: Lombok, Fastjson2

## 功能模块

### 1. 用户管理
- 用户注册/登录
- JWT身份认证
- 角色权限控制（管理员/教师/学生）
- 个人信息管理

### 2. 请假管理
- 学生提交请假申请
- 教师/管理员审批请假
- 请假记录查询
- 请假统计数据

### 3. 报修管理
- 学生提交报修申请
- 管理员处理报修请求
- 报修状态跟踪
- 报修统计数据

### 4. 公告管理
- 教师/管理员发布公告
- 公告分类管理
- 公告浏览统计
- 公告置顶功能

### 5. 活动管理
- 教师发布校园活动
- 学生报名参加活动
- 报名人数限制
- 活动状态管理

## 项目结构

```
src/main/java/com/xiaou/
├── CampusAffairsApplication.java    # 启动类
├── common/                          # 通用类
│   ├── Result.java                  # 统一响应结果
│   ├── BusinessException.java       # 业务异常
│   └── GlobalExceptionHandler.java  # 全局异常处理
├── config/                          # 配置类
│   ├── WebConfig.java              # Web配置（CORS、拦截器）
│   ├── JwtInterceptor.java         # JWT拦截器
│   ├── MyBatisPlusConfig.java      # MyBatis-Plus配置
│   └── MyMetaObjectHandler.java    # 字段自动填充
├── controller/                      # 控制器层
│   ├── AuthController.java         # 认证接口
│   ├── UserController.java         # 用户管理
│   ├── LeaveRequestController.java # 请假管理
│   ├── RepairRequestController.java# 报修管理
│   ├── NoticeController.java       # 公告管理
│   └── ActivityController.java     # 活动管理
├── entity/                          # 实体类
│   ├── User.java                   # 用户实体
│   ├── LeaveRequest.java           # 请假申请
│   ├── RepairRequest.java          # 报修申请
│   ├── Notice.java                 # 公告实体
│   ├── Activity.java               # 活动实体
│   └── ActivitySignup.java         # 活动报名
├── mapper/                          # 数据访问层
├── service/                         # 业务逻辑层
│   ├── impl/                       # 实现类
└── utils/                           # 工具类
    └── JwtUtil.java                # JWT工具

src/main/resources/
├── application.yml                  # 应用配置
└── sql/
    └── init.sql                    # 数据库初始化脚本
```

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置
1. 创建数据库：`campus_affairs`
2. 执行初始化脚本：`src/main/resources/sql/init.sql`
3. 修改 `application.yml` 中的数据库连接信息

### 3. 运行项目
```bash
# 克隆项目
git clone <repository-url>

# 进入项目目录
cd 001

# 运行项目
mvn spring-boot:run
```

### 4. 默认账号
- **管理员**: admin / admin123
- **教师**: teacher001 / teacher123  
- **学生**: student001 / student123

## API接口

### 认证接口
- POST `/api/auth/login` - 用户登录
- POST `/api/auth/register` - 用户注册
- GET `/api/auth/userinfo` - 获取当前用户信息
- POST `/api/auth/change-password` - 修改密码

### 用户管理
- GET `/api/user/list` - 用户列表（管理员）
- POST `/api/user/add` - 添加用户（管理员）
- PUT `/api/user/update` - 更新用户信息
- DELETE `/api/user/{id}` - 删除用户（管理员）

### 请假管理
- POST `/api/leave/apply` - 提交请假申请（学生）
- GET `/api/leave/list` - 请假申请列表
- POST `/api/leave/approve` - 审批请假（教师/管理员）
- GET `/api/leave/statistics` - 请假统计（管理员）

### 报修管理
- POST `/api/repair/submit` - 提交报修申请（学生）
- GET `/api/repair/list` - 报修申请列表
- POST `/api/repair/handle` - 处理报修（管理员）
- GET `/api/repair/statistics` - 报修统计（管理员）

### 公告管理
- POST `/api/notice/add` - 发布公告（教师/管理员）
- GET `/api/notice/list` - 公告列表
- GET `/api/notice/{id}` - 公告详情
- PUT `/api/notice/update` - 更新公告
- DELETE `/api/notice/{id}` - 删除公告
- POST `/api/notice/toggle-top/{id}` - 置顶公告（管理员）

### 活动管理
- POST `/api/activity/add` - 发布活动（教师/管理员）
- GET `/api/activity/list` - 活动列表
- GET `/api/activity/{id}` - 活动详情
- POST `/api/activity/signup` - 报名活动（学生）
- POST `/api/activity/cancel-signup` - 取消报名（学生）
- GET `/api/activity/{id}/signups` - 报名列表
- PUT `/api/activity/update` - 更新活动
- DELETE `/api/activity/{id}` - 删除活动

## 权限说明

- **学生**: 可提交请假/报修申请，查看公告，报名活动
- **教师**: 可审批请假，发布公告/活动，查看相关数据
- **管理员**: 拥有所有权限，可管理用户，处理报修，查看统计数据

## 开发说明

1. 所有接口都需要JWT认证（除登录/注册接口）
2. 使用统一的Result返回格式
3. 支持分页查询（默认第1页，每页10条）
4. 使用MyBatis-Plus的逻辑删除
5. 所有实体类自动填充创建时间和更新时间

## 注意事项

1. JWT密钥建议配置在环境变量中
2. 生产环境需要修改数据库连接信息
3. 建议开启HTTPS保护JWT传输安全
4. 可根据需要调整JWT过期时间（默认7天）

## 许可证

MIT License
