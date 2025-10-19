# 校园表白墙与匿名社交平台

## 项目简介

这是一个基于Spring Boot 3的校园表白墙与匿名社交平台，提供匿名发帖、评论互动、点赞、私信等功能，内置敏感词过滤和举报审核机制。

## 核心功能

### 用户端
- ✅ 用户注册/登录（实名认证）
- ✅ 匿名发帖（表白/吐槽/求助/树洞/校园/闲聊）
- ✅ 话题广场（分类浏览、热门/最新排序）
- ✅ 帖子详情（浏览、点赞、评论）
- ✅ 评论互动（楼中楼）
- ✅ 敏感词自动过滤
- ✅ 私信功能（匿名对话）
- ✅ 举报功能（举报帖子/评论）
- ✅ 积分等级系统
- ✅ 个人中心（我的发帖、评论、举报、积分）
- ✅ 搜索功能

### 管理端
- ✅ 数据概览统计
- ✅ 用户管理（禁言/封号）
- ✅ 实名认证审核
- ✅ 帖子内容审核
- ✅ 举报处理
- ✅ 敏感词管理
- ✅ 帖子置顶/删除

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.2.0 | 基础框架 |
| MyBatis-Plus | 3.5.5 | ORM框架 |
| MySQL | 8.0 | 数据库 |
| JWT | 0.12.3 | 身份认证 |
| BCrypt | - | 密码加密 |
| Lombok | - | 简化代码 |

## 数据库设计

项目使用8张表的精简设计：

1. **user** - 用户表（包含积分、等级）
2. **post** - 帖子表（直接存储匿名昵称和头像）
3. **comment** - 评论表（支持楼中楼）
4. **like_record** - 点赞表
5. **message** - 私信表（简化版）
6. **report** - 举报表
7. **sensitive_word** - 敏感词表
8. **admin** - 管理员表

## 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 2. 数据库初始化

```sql
# 创建数据库和表结构
mysql -u root -p < src/main/resources/sql/schema.sql

# 插入初始数据
mysql -u root -p < src/main/resources/sql/init_data.sql
```

### 3. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_confession?...
    username: root
    password: root  # 修改为你的密码
```

### 4. 运行项目

```bash
# 方式1: Maven命令
mvn spring-boot:run

# 方式2: IDE运行
直接运行 ConfessionApplication.java
```

### 5. 访问地址

- 用户端首页: http://localhost:8080
- 用户登录: http://localhost:8080/login.html
- 管理员登录: http://localhost:8080/admin-login.html
- 后端API: http://localhost:8080/api

## 测试账号

详见 `ACCOUNTS.md` 文件

| 类型 | 账号 | 密码 |
|------|------|------|
| 管理员 | admin | admin123 |
| 测试用户1 | 20210001 | 123456 |
| 测试用户2 | 20210002 | 123456 |

## 密码加密测试

运行测试验证密码加密：

```bash
mvn test -Dtest=PasswordUtilTest
```

## API接口示例

### 用户注册
```bash
POST /api/auth/register
Content-Type: application/json

{
    "studentId": "20240001",
    "realName": "张三",
    "phone": "13800138000",
    "password": "123456",
    "school": "某某大学",
    "college": "计算机学院",
    "grade": 2024
}
```

### 用户登录
```bash
POST /api/auth/login
Content-Type: application/json

{
    "account": "20210001",
    "password": "123456"
}
```

### 发帖（需要token）
```bash
POST /api/posts
Authorization: Bearer {token}
Content-Type: application/json

{
    "category": "CONFESS",
    "title": "表白墙",
    "content": "想对图书馆的那个女生说...",
    "images": null,
    "tags": "#表白 #校园"
}
```

## 项目结构

```
015-backend/
├── src/main/java/com/xiaou/confession/
│   ├── common/              # 公共类
│   ├── config/              # 配置类
│   ├── controller/          # 控制器
│   │   ├── AuthController.java
│   │   ├── PostController.java
│   │   ├── CommentController.java
│   │   ├── LikeController.java
│   │   ├── MessageController.java      # 私信
│   │   ├── ReportController.java       # 举报
│   │   ├── UserController.java
│   │   └── AdminController.java
│   ├── entity/              # 实体类（8个）
│   ├── mapper/              # Mapper接口
│   ├── service/             # 服务层
│   │   ├── AuthService.java
│   │   ├── PostService.java
│   │   ├── CommentService.java
│   │   ├── LikeService.java
│   │   ├── MessageService.java         # 私信
│   │   ├── ReportService.java          # 举报
│   │   ├── UserService.java
│   │   └── AdminService.java
│   ├── util/                # 工具类
│   ├── interceptor/         # 拦截器
│   ├── exception/           # 异常处理
│   └── ConfessionApplication.java
├── src/main/resources/
│   ├── application.yml      # 配置文件
│   ├── sql/                 # SQL脚本
│   └── static/              # 前端文件
│       ├── css/
│       │   └── style.css    # 公共样式
│       ├── js/
│       │   └── common.js    # 公共JS
│       ├── login.html       # 用户登录
│       ├── home.html        # 话题广场
│       ├── post-detail.html # 帖子详情
│       ├── create-post.html # 发帖页
│       ├── messages.html    # 消息列表
│       ├── chat.html        # 私信对话
│       ├── profile.html     # 个人中心
│       ├── my-posts.html    # 我的发帖
│       ├── my-comments.html # 我的评论
│       ├── my-reports.html  # 我的举报
│       ├── search.html      # 搜索
│       ├── admin-login.html # 管理员登录
│       └── admin-dashboard.html # 管理后台
├── pom.xml
├── README.md
└── ACCOUNTS.md
```

## 核心特性说明

### 1. 匿名保护机制
- 每个帖子/评论使用随机生成的匿名昵称（如：可爱的小熊1234）
- 匿名头像从20个默认头像中随机选择
- 真实身份与匿名身份完全隔离
- 管理员可查看真实身份（用于违规处理）

### 2. 敏感词过滤
- 使用DFA算法实现高效敏感词过滤
- 三级敏感词库（轻度/中度/重度）
- 自动将敏感词替换为***
- 支持敏感词的增删改查

### 3. 积分等级系统
```
注册认证：+10分
发布帖子：+5分
评论互动：+2分
收到点赞：+1分
违规处罚：-10分
```

### 4. 举报审核流程
```
用户举报 → 管理员审核 → 处理结果（通过/驳回）
处罚措施：警告/禁言/封号
```

## 开发注意事项

1. 所有LocalDateTime字段已添加 `@JsonFormat` 注解
2. 密码使用BCrypt加密，不可逆
3. JWT token有效期为7天
4. 敏感词在项目启动时自动加载到内存
5. 文件上传路径为项目根目录的uploads文件夹

## 前端页面说明

### 用户端页面
| 页面 | 路径 | 说明 |
|------|------|------|
| 登录注册 | /login.html | 用户登录和注册 |
| 话题广场 | /home.html | 浏览帖子、分类筛选 |
| 帖子详情 | /post-detail.html | 查看帖子、评论互动 |
| 发帖 | /create-post.html | 匿名发布帖子 |
| 消息中心 | /messages.html | 私信列表 |
| 私信对话 | /chat.html | 匿名聊天 |
| 个人中心 | /profile.html | 个人信息、积分 |
| 我的发帖 | /my-posts.html | 查看我的发帖 |
| 我的评论 | /my-comments.html | 查看我的评论 |
| 我的举报 | /my-reports.html | 查看举报记录 |
| 搜索 | /search.html | 搜索帖子 |

### 管理端页面
| 页面 | 路径 | 说明 |
|------|------|------|
| 管理员登录 | /admin-login.html | 管理员登录 |
| 管理后台 | /admin-dashboard.html | 数据概览、用户管理、内容审核、举报处理、敏感词管理 |

## 常见问题

### Q: 启动报错找不到数据库？
A: 请先执行schema.sql创建数据库和表

### Q: 登录一直提示密码错误？
A: 请确保执行了init_data.sql插入初始数据

### Q: 前端页面显示空白？
A: 检查浏览器控制台是否有跨域错误，确保后端已启动

### Q: 如何添加新的敏感词？
A: 登录管理后台，在敏感词管理页面添加

### Q: 私信功能如何使用？
A: 在帖子详情页点击用户头像或评论中发起私信

## 许可证

MIT License

