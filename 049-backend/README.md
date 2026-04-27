# 基于微信小程序的考研学习系统

## 项目简介

本系统是一个面向考研学生的学习平台，基于微信小程序前端 + Spring Boot后端开发。系统提供课程学习、题库刷题、错题本、学习计划、每日打卡、笔记管理、考研社区等功能，帮助考研学生高效备考。

## 技术栈

### 后端
- Spring Boot 3.2
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT认证
- Knife4j接口文档

### 前端（微信小程序）
- 微信小程序原生开发
- WXML + WXSS + JavaScript

## 功能模块

### 学生端
- 用户注册/登录
- 首页：科目分类、热门课程、学习统计
- 课程中心：课程列表、课程详情、章节学习
- 刷题中心：按科目/分类刷题、随机练习
- 错题本：记录错题、标记已掌握
- 学习计划：制定学习计划、更新进度
- 每日打卡：打卡记录、连续打卡统计
- 我的笔记：创建笔记、管理笔记
- 收藏功能：收藏课程、题目、笔记
- 考研社区：发帖讨论、评论互动

### 管理端
- 用户管理
- 科目管理
- 课程管理
- 题库管理
- 公告管理
- 数据统计

## 数据库设计

系统包含16张核心表：
- `sys_user` - 用户表（学生/教师/管理员）
- `subject` - 科目分类
- `course` - 课程
- `chapter` - 章节
- `study_record` - 学习记录
- `question_category` - 题库分类
- `question` - 题目
- `answer_record` - 做题记录
- `wrong_question` - 错题本
- `study_plan` - 学习计划
- `daily_checkin` - 每日打卡
- `note` - 笔记
- `favorite` - 收藏
- `post` - 讨论帖子
- `comment` - 评论
- `notice` - 公告

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+（可选，默认 H2 内存库可直接启动）
- 微信开发者工具

### 后端启动
默认使用 H2 内存数据库自举，适合本地巡检、答辩演示和快速体验：

```bash
mvn spring-boot:run
```

启动后访问：

- 后端接口：`http://localhost:8049/api`
- Knife4j 文档：`http://localhost:8049/doc.html`
- H2 控制台：`http://localhost:8049/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:kaoyan_study`

如需切换 MySQL：

1. 创建数据库 `kaoyan_study`
2. 执行 `sql/init.sql` 初始化数据
3. 按需修改 `src/main/resources/application-mysql.yml`
4. 运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`

### 小程序启动
1. 使用微信开发者工具打开 `049-miniprogram` 目录
2. 默认 `app.js` 中的 `baseUrl` 已指向 `http://localhost:8049/api`
3. 编译运行

### 验证命令

```bash
mvn clean test
```

当前已补充后端冒烟测试，覆盖 H2 自举、登录鉴权、公开课程/题库、管理员仪表盘、刷题错题、学习计划、笔记、收藏、社区发帖评论和每日打卡等核心链路。

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 教师 | teacher1 | 123456 |
| 学生 | student1 | 123456 |

## 项目结构

```
049-backend/
├── src/main/java/com/xiaou/
│   ├── common/         # 通用类
│   ├── config/         # 配置类
│   ├── controller/     # 控制器
│   ├── dto/            # 数据传输对象
│   ├── entity/         # 实体类
│   ├── mapper/         # MyBatis映射
│   └── service/        # 业务逻辑
├── src/main/resources/
│   └── application.yml # 配置文件
└── sql/
    └── init.sql        # 数据库初始化脚本

049-miniprogram/
├── pages/              # 页面
│   ├── index/          # 首页
│   ├── course/         # 课程
│   ├── question/       # 刷题
│   ├── community/      # 社区
│   └── my/             # 我的
├── utils/              # 工具类
├── app.js              # 应用入口
├── app.json            # 应用配置
└── app.wxss            # 全局样式
```

## 接口文档
启动后端后访问: http://localhost:8049/doc.html

## 巡检修复说明

本轮巡检已修复默认运行强依赖 MySQL/Redis、端口与其他项目冲突、JWT 登录 token 不含角色导致鉴权失效、请求上下文缺少 `userId`、登录响应泄露密码字段、题目选项与小程序字段不匹配、小程序 tabBar 和占位图片资源断链、学习计划/打卡/答题提交字段不一致，以及后端缺少可执行自动化测试等问题。默认环境现在可直接启动并完成核心链路验证。
