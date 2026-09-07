# 014 - 校园社团与兴趣圈层平台 项目总结

## 项目概述
校园社团与兴趣圈层平台是一个前后端一体化的Web应用，旨在促进校园文化建设，提供社团管理、活动组织、话题讨论、兴趣匹配等功能。

## 技术栈

### 后端
- **框架**: Spring Boot 3.2.0
- **ORM**: MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0
- **认证**: JWT (jjwt 0.11.5)
- **加密**: jBCrypt 0.4
- **工具**: Hutool 5.8.23

### 前端
- **基础库**: jQuery 3.7.1
- **UI框架**: Bootstrap 5.3.2
- **图标**: Bootstrap Icons 1.11.2
- **部署方式**: 集成在后端 resources/static 目录

## 项目结构

```
014-backend/
├── src/main/
│   ├── java/com/xiaou/campusclub/
│   │   ├── CampusClubApplication.java     # 主应用类
│   │   ├── common/                        # 通用类
│   │   │   └── Result.java               # 统一响应
│   │   ├── config/                        # 配置类
│   │   │   ├── JwtInterceptor.java       # JWT拦截器
│   │   │   └── WebConfig.java            # Web配置
│   │   ├── controller/                    # 控制器（9个）
│   │   ├── dto/                           # 数据传输对象（7个）
│   │   ├── entity/                        # 实体类（17个）
│   │   ├── exception/                     # 异常处理
│   │   ├── mapper/                        # Mapper接口（17个）
│   │   ├── service/                       # 服务接口（6个）
│   │   │   └── impl/                     # 服务实现（6个）
│   │   ├── util/                          # 工具类
│   │   └── vo/                            # 视图对象（8个）
│   ├── resources/
│   │   ├── static/                        # 静态资源（前端）
│   │   │   ├── index.html                # 首页
│   │   │   ├── login.html                # 登录页
│   │   │   ├── register.html             # 注册页
│   │   │   ├── clubs.html                # 社团广场
│   │   │   ├── club-detail.html          # 社团详情
│   │   │   ├── css/style.css             # 样式文件
│   │   │   ├── js/
│   │   │   │   ├── common.js             # 通用JS
│   │   │   │   ├── index.js              # 首页JS
│   │   │   │   └── clubs.js              # 社团JS
│   │   │   └── README.md                 # 前端说明
│   │   ├── sql/
│   │   │   ├── schema.sql                # 建表SQL
│   │   │   └── init_data.sql             # 初始数据
│   │   └── application.yml               # 配置文件
│   └── test/java/                         # 测试类
├── ACCOUNTS.md                            # 测试账户
├── PROJECT_SUMMARY.md                     # 项目总结
└── pom.xml                                # Maven配置
```

## 核心功能模块

### 1. 用户模块
- ✅ 用户注册/登录
- ✅ 个人资料管理
- ✅ 积分系统
- ✅ 勋章系统
- ✅ 兴趣标签管理

### 2. 社团模块
- ✅ 社团创建（需审核）
- ✅ 社团列表（分类/搜索）
- ✅ 社团详情
- ✅ 成员管理
- ✅ 招新管理
- ✅ 申请加入

### 3. 活动模块
- ✅ 活动发布
- ✅ 活动列表
- ✅ 活动报名/取消
- ✅ 活动签到（签到码）
- ✅ 活动评价

### 4. 话题讨论模块
- ✅ 话题发布
- ✅ 话题列表（热门/最新）
- ✅ 话题评论（一级/二级）
- ✅ 点赞功能

### 5. 兴趣圈子模块
- ✅ 圈子创建
- ✅ 圈子列表
- ✅ 加入/退出圈子
- ✅ 圈子推荐

### 6. 积分勋章模块
- ✅ 积分规则
  - 注册：+10
  - 完善资料：+20
  - 加入社团：+5
  - 发布话题：+3
  - 评论：+1
  - 参加活动：+10
  - 活动签到：+5
- ✅ 勋章体系（6种勋章）
- ✅ 等级系统

### 7. 消息通知模块
- ✅ 系统通知
- ✅ 互动通知
- ✅ 社团通知
- ✅ 未读消息提醒

### 8. 管理后台模块
- ✅ 用户管理
- ✅ 社团审核
- ✅ 话题管理
- ✅ 数据统计

## 数据库设计

### 数据表（17张）
1. **user** - 用户表
2. **interest_tag** - 兴趣标签表
3. **user_interest** - 用户兴趣关联表
4. **club** - 社团表
5. **club_member** - 社团成员表
6. **activity** - 活动表
7. **activity_registration** - 活动报名表
8. **circle** - 圈子表
9. **circle_member** - 圈子成员表
10. **topic** - 话题表
11. **topic_comment** - 话题评论表
12. **like_record** - 点赞记录表
13. **badge** - 勋章表
14. **user_badge** - 用户勋章表
15. **points_record** - 积分记录表
16. **notification** - 消息通知表
17. **admin** - 管理员表

## API接口

### 用户接口 (/api/user)
- POST /register - 注册
- POST /login - 登录
- GET /info - 获取用户信息
- PUT /profile - 更新个人资料
- POST /interests - 添加兴趣标签
- DELETE /interests/{id} - 删除兴趣标签

### 社团接口 (/api/clubs)
- GET / - 获取社团列表
- GET /{id} - 获取社团详情
- POST / - 创建社团
- PUT /{id} - 更新社团信息
- POST /{id}/join - 申请加入社团
- PUT /{id}/members/{userId}/approve - 审核成员申请
- GET /my - 我加入的社团

### 活动接口 (/api/activities)
- GET / - 获取活动列表
- GET /{id} - 获取活动详情
- POST / - 创建活动
- POST /{id}/register - 报名活动
- DELETE /{id}/register - 取消报名
- POST /{id}/sign - 活动签到
- POST /{id}/rate - 评价活动
- GET /my - 我的活动

### 话题接口 (/api/topics)
- GET / - 获取话题列表
- GET /{id} - 获取话题详情
- POST / - 发布话题
- POST /{id}/like - 点赞话题
- GET /{id}/comments - 获取评论
- POST /{id}/comments - 发表评论

### 圈子接口 (/api/circles)
- GET / - 获取圈子列表
- GET /{id} - 获取圈子详情
- POST / - 创建圈子
- POST /{id}/join - 加入圈子
- GET /recommend - 推荐圈子
- GET /my - 我的圈子

### 其他接口
- GET /api/interests - 获取兴趣标签
- GET /api/notifications - 获取通知列表
- POST /api/upload/image - 上传图片
- POST /api/admin/login - 管理员登录

## 测试账户

### 学生用户
- **账号**: student1 / student2 / student3
- **密码**: 123456

### 管理员
- **账号**: admin
- **密码**: 123456

## 部署说明

### 1. 数据库初始化
```bash
# 执行建表脚本
mysql -u root -p < src/main/resources/sql/schema.sql

# 导入初始数据
mysql -u root -p < src/main/resources/sql/init_data.sql
```

### 2. 配置文件
修改 `application.yml` 中的数据库配置

### 3. 启动应用
```bash
mvn clean package
java -jar target/014-backend-1.0-SNAPSHOT.jar
```

### 4. 访问应用
浏览器访问：http://localhost:8080/index.html

## 特色功能

### 🎯 兴趣匹配推荐
基于用户兴趣标签，智能推荐相关社团和圈子

### 🏆 积分勋章体系
完整的积分规则和勋章系统，激励用户参与

### 📱 响应式设计
支持PC端和移动端访问

### 🔐 JWT认证
安全的Token认证机制

### 📊 数据统计
管理后台提供完整的数据统计功能

## 项目亮点

1. **前后端一体化** - 使用Spring Boot提供静态资源服务，简化部署
2. **jQuery + Bootstrap** - 使用传统技术栈，降低学习成本
3. **完整的权限控制** - JWT认证 + 拦截器
4. **丰富的功能模块** - 涵盖社团管理的各个方面
5. **良好的代码结构** - 分层清晰，易于维护
6. **详细的文档** - 包含API文档、测试账户、部署说明

## 注意事项

1. 项目默认使用 CDN 引入前端资源，需要联网
2. 文件上传路径需要预先创建：`D:/uploads/campus-club/`
3. 密码使用 BCrypt 加密，不可逆
4. Token 默认有效期 7 天

## 待扩展功能

- [ ] 活动相册功能
- [ ] 私信功能
- [ ] 社团动态发布
- [ ] 更多数据可视化图表
- [ ] 移动端APP
- [ ] 社团认证系统
- [ ] 活动直播功能

---

**开发时间**: 2024年
**技术支持**: Spring Boot + jQuery + Bootstrap

