# 101 后端 - 多模态招聘材料解析与岗位匹配系统

## 项目说明

`101-backend` 是 101 号项目的 Spring Boot 后端，提供认证鉴权、候选人档案、简历与证书、岗位与岗位要求、解析任务、匹配任务、面试排期、面试反馈、统计看板和操作日志接口。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- MySQL 8
- Redis
- JWT
- Lombok

## 目录说明

- 启动类：`src/main/java/com/recruitmatch/RecruitMatchApplication.java`
- 配置文件：`src/main/resources/application.yml`
- 演示数据：`sql/init.sql`
- 需求文档：`PRD.md`
- 实施计划：`PLAN.md`

## 默认端口

- 后端端口：`8101`

## 默认账号

- 管理员：`admin / 123456`
- HR：`hr / 123456`
- 候选人：`candidate / 123456`
- 面试官：`interviewer / 123456`

## 数据准备

1. 创建数据库：`recruit_match_101`
2. 执行脚本：`101-backend/sql/init.sql`
3. 确认本机 MySQL 连接与 `application.yml` 一致：
   - URL：`jdbc:mysql://localhost:3306/recruit_match_101`
   - 用户名：`root`
   - 密码：`1234`
4. 确认本机 Redis 已启动：
   - Host：`localhost`
   - Port：`6379`
   - DB：`4`

## 启动方式

在 `101-backend` 目录执行：

```bash
mvn spring-boot:run
```

如需做最小验证，可执行：

```bash
mvn test
```

## 关键接口

- `POST /api/auth/login`
- `GET /api/auth/info`
- `GET /api/user/page`
- `GET /api/candidate/page`
- `GET /api/resume/page`
- `GET /api/certificate/page`
- `GET /api/job/page`
- `GET /api/requirement/page`
- `GET /api/parse-task/page`
- `GET /api/parse-result/page`
- `GET /api/match-task/page`
- `GET /api/match-result/page`
- `GET /api/interview/page`
- `GET /api/feedback/page`
- `GET /api/statistics/dashboard`
- `GET /api/log/page`

## 角色边界

- 管理员：用户管理、首页看板、操作日志
- HR：招聘业务管理，包括候选人、岗位、解析、匹配、面试安排
- 候选人：仅维护自己的档案、简历、证书
- 面试官：仅查看分配给自己的排期并填写反馈
