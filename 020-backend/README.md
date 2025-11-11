# 校园学习资源共享平台 - 后端

基于 Spring Boot 3 开发的校园学习资源共享平台后端项目

## 技术栈

- Spring Boot 3.2.0
- MyBatis Plus 3.5.5
- MySQL 8.0
- JWT 认证
- Hutool 工具库

## 快速启动

### 1. 数据库配置

创建数据库并导入初始化脚本：

```bash
mysql -u root -p < src/main/resources/schema.sql
```

或者手动创建数据库：

```sql
CREATE DATABASE campus_resource_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

然后执行 `src/main/resources/schema.sql` 中的SQL语句。

### 2. 修改配置

修改 `src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_resource_platform?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

或在IDE中运行 `ResourcePlatformApplication.java`

访问地址：http://localhost:8020

## 测试账户

查看 `ACCOUNTS.md` 文件获取测试账户信息。

默认密码为 `123456`，MD5加密后为 `e10adc3949ba59abbe56e057f20f883e`

## 密码加密测试

运行测试类验证密码加密：

```bash
mvn test -Dtest=PasswordTest
```

## API接口文档

### 用户模块 `/api/user`

- POST `/register` - 用户注册
- POST `/login` - 用户登录
- GET `/info` - 获取用户信息
- PUT `/update` - 更新用户信息
- GET `/points` - 获取用户积分

### 资源模块 `/api/resource`

- POST `/upload` - 上传资源
- GET `/list` - 资源列表
- GET `/{id}` - 资源详情
- POST `/download/{id}` - 下载资源
- POST `/rate` - 评价资源
- GET `/ratings/{resourceId}` - 获取评价列表
- GET `/my` - 我的资源

### 学习小组模块 `/api/group`

- POST `/create` - 创建小组
- GET `/list` - 小组列表
- GET `/{id}` - 小组详情
- POST `/join` - 加入小组
- POST `/leave` - 退出小组
- GET `/members/{groupId}` - 小组成员列表
- GET `/my` - 我的小组

### 题库模块 `/api/question`

- POST `/add` - 添加题目
- GET `/list` - 题目列表
- GET `/{id}` - 题目详情
- POST `/random` - 随机获取题目
- POST `/generate-paper` - 生成试卷
- POST `/wrong/add` - 添加错题
- GET `/wrong/list` - 错题列表
- DELETE `/wrong/{id}` - 删除错题

### 答疑模块 `/api/qa`

- POST `/ask` - 提问
- GET `/list` - 问题列表
- GET `/{id}` - 问题详情
- GET `/my` - 我的提问
- POST `/answer` - 回答问题
- GET `/answers/{questionId}` - 获取回答列表
- POST `/accept` - 采纳答案
- POST `/like/{answerId}` - 点赞回答

### 笔记模块 `/api/note`

- POST `/create` - 创建笔记
- PUT `/update` - 更新笔记
- GET `/list` - 笔记列表
- GET `/{id}` - 笔记详情
- GET `/my` - 我的笔记
- DELETE `/{id}` - 删除笔记
- POST `/like/{id}` - 点赞笔记

## 项目结构

```
020-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/xiaou/resource/
│   │   │       ├── common/          # 通用类
│   │   │       ├── config/          # 配置类
│   │   │       ├── controller/      # 控制器
│   │   │       ├── entity/          # 实体类
│   │   │       ├── interceptor/     # 拦截器
│   │   │       ├── mapper/          # Mapper接口
│   │   │       ├── service/         # 服务层
│   │   │       ├── utils/           # 工具类
│   │   │       └── ResourcePlatformApplication.java
│   │   └── resources/
│   │       ├── application.yml      # 配置文件
│   │       └── schema.sql          # 数据库脚本
│   └── test/                       # 测试类
├── ACCOUNTS.md                     # 测试账户
├── pom.xml
└── README.md
```

## 核心功能

### 1. 完善的积分系统
- 上传资源获得积分
- 下载资源消耗积分
- 回答问题获得积分
- 积分记录查询

### 2. 知识付费
- 优质资源可设置积分下载
- 激励内容创作

### 3. 协作学习
- 学习小组功能
- 促进学生交流合作

### 4. 智能题库
- 随机组卷
- 错题本管理
- 帮助学生高效复习

### 5. 悬赏答疑
- 付费提问
- 快速获得优质答案

### 6. Markdown笔记
- 支持Markdown格式
- 便于记录和分享学习笔记

