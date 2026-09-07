# 企业知识库智能问答与文档权限管理系统实现计划

## 问题陈述

企业内部制度、产品、项目和运营知识通常分散在多个文档中，普通员工难以快速定位可信答案，管理者也难以控制不同文档的访问范围。本项目建设一个带文档权限控制的智能问答平台，提供知识空间、文档入库、分段索引、权限组授权、问答记录、来源追踪和反馈审计闭环。

## 当前状态

- 当前仓库已完成 `001`-`097` 项目。
- 新项目编号为 `098`。
- 后端目录：`098-backend`
- 前端目录：`098-frontend`
- 技术栈遵循仓库规则：Spring Boot、Vue、MyBatis、PageHelper、MySQL、Redis、JWT。

## 实施方案

### 第一阶段：后端开发

1. 基础架构
   - `pom.xml`
   - `application.yml`
   - 启动类
   - `Result`
   - `BusinessException`
   - `GlobalExceptionHandler`
   - `JwtInterceptor`
   - `WebMvcConfig`
   - `RedisConfig`
   - `JwtUtils`

2. 数据库
   - 创建 `sql/init.sql`
   - 建立 12 张业务表
   - 插入管理员、编辑员、员工和业务演示数据

3. 实体类
   - `SysUser`
   - `KnowledgeSpace`
   - `DocumentCategory`
   - `KnowledgeDocument`
   - `DocumentChunk`
   - `PermissionGroup`
   - `GroupMember`
   - `DocumentPermission`
   - `QaSession`
   - `QaRecord`
   - `QaFeedback`
   - `AccessLog`

4. Mapper 接口
   - 使用 MyBatis 注解 SQL
   - 配合 PageHelper 完成分页查询

5. Service 层
   - 登录鉴权
   - 用户管理
   - 文档空间、分类、文档和分段管理
   - 权限组、成员和授权管理
   - 问答会话、问答记录和反馈处理
   - 统计看板和访问日志

6. Controller 层
   - 认证接口
   - 用户接口
   - 空间接口
   - 分类接口
   - 文档接口
   - 分段接口
   - 权限组接口
   - 组成员接口
   - 授权接口
   - 会话接口
   - 问答记录接口
   - 反馈接口
   - 统计接口
   - 日志接口

### 第二阶段：前端开发

1. 项目结构
   - `package.json`
   - `vite.config.js`
   - `index.html`
   - `src/main.js`
   - `src/App.vue`
   - `src/router/index.js`
   - `src/api/request.js`
   - `src/api/index.js`
   - `src/store/user.js`

2. 页面开发
   - 登录页
   - 布局页
   - 仪表盘
   - 用户管理
   - 知识空间
   - 文档分类
   - 知识文档
   - 文档分段
   - 权限组
   - 组成员
   - 文档授权
   - 问答会话
   - 问答记录
   - 问答反馈
   - 访问日志

### 第三阶段：合集文档更新

1. 更新 `readme.md`
2. 更新 `readme_simple.md`
3. 更新候选题状态记录

## 验收标准

- 后端目录和前端目录完整
- 后端包含 12 张表、实体、Mapper、Service、Controller
- 前端包含登录、布局、仪表盘和全部业务页面
- 默认账号可用于演示系统功能
- README 合集新增 `098` 项目说明
