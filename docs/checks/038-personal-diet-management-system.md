# 038 基于SpringBoot的个人饮食管理系统检查报告

## 1. 检查结论

- 项目编号：`038`
- 项目名称：`基于SpringBoot的个人饮食管理系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复 Spring Boot 3.2 与旧 MyBatis-Plus starter 不兼容导致默认启动失败、默认环境强依赖 MySQL/Redis、Spring Security 默认登录页阻断接口、登录服务未校验密码、初始化 SQL 中 admin 密码哈希断行损坏、核心业务接口过少导致演示链路不足等问题。当前后端可在默认 H2 环境下通过测试并真实启动，管理员登录、食物库、饮食记录、营养汇总、健康数据、营养目标、食谱详情接口抽测通过；前端可构建并通过 Vite 代理完成登录接口联调。`

## 2. 项目形态

- 后端目录：`038-backend`
- 前端目录：`038-frontend`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + Spring Security + H2/MySQL + Knife4j`
- 前端技术栈：`Vue 3 + Vite + Element Plus + ECharts`
- 默认后端上下文路径：`/api`
- 默认后端端口：`8038`
- 默认前端端口：`3038`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将 `mybatis-plus-boot-starter` 切换为 `mybatis-plus-spring-boot3-starter`，修复 Spring Boot 3.2 启动时报 `Invalid value type for attribute 'factoryBeanObjectType'` 的兼容性问题。
2. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免本地巡检必须预先安装 MySQL。
3. 新增 `application-mysql.yml`，保留 MySQL + Druid 部署配置。
4. 新增 `sql/schema-h2.sql` 与 `sql/data-h2.sql`，提供用户、食物、饮食记录、营养目标、健康数据与食谱的演示数据。
5. 修复原 `data.sql` 中 `admin` 密码哈希被断行破坏的问题，并统一测试账号密码为 `123456`。
6. 调整启动提示，默认模式下展示 H2 控制台与 MySQL profile 启动方式。

### 3.2 登录与接口修复

1. 新增 `SecurityConfig`，关闭默认表单登录 / HTTP Basic，放行演示接口，避免前后端联调被 Spring Security 默认安全页拦截。
2. 新增 `PasswordEncoder`，注册与登录时使用 bcrypt 加密/校验密码。
3. 登录成功后返回可用于前端存储的演示 token，替代原来的固定 `token_placeholder`。
4. 新增 `GlobalExceptionHandler`，将业务异常统一返回 JSON。
5. 修复用户详情查询空指针风险，避免查询不存在用户时直接 NPE。
6. 修复食物分页筛选空字符串条件和食物详情不存在时响应不明确的问题。

### 3.3 业务演示接口补齐

1. 新增饮食记录接口：分页查询、添加记录、删除记录。
2. 新增健康数据接口：列表、最近一条、保存健康数据，并在可计算时自动生成 BMI。
3. 新增营养目标接口：目标列表、当前生效目标、保存目标。
4. 新增食谱接口：分页查询、详情查询与浏览数递增。
5. 新增营养分析接口：按用户和日期汇总热量、蛋白质、脂肪、碳水，并计算目标达成率。
6. 新增后端冒烟测试，覆盖默认 H2 数据下的登录、食物分页与营养汇总接口。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 测试账号 `admin / 123456`、`user1 / 123456`、`user2 / 123456` 可用于登录演示。
3. 食物库、饮食记录、营养目标、健康数据、营养分析、食谱推荐均已有可抽测后端接口。
4. 前端可通过 `npm run build` 构建，并可在 `3038` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖核心页面所需的基础业务场景。

### 4.2 仍有差距

1. 前端多个页面仍是占位或静态数据，例如营养分析、健康数据、营养目标、食谱推荐页面尚未完整接入本轮补齐的后端接口。
2. 当前 token 为本地演示 token，尚不是完整 JWT 鉴权体系；接口也未做细粒度权限隔离。
3. README / 启动说明仍以 MySQL + Redis 为主要环境描述，已保留 MySQL profile，但默认演示链路未覆盖真实 Redis 缓存能力。
4. 前端构建存在 Vite CJS API 废弃提示和大包体积告警，主要来自 Element Plus / ECharts 依赖体积。
5. 控制台日志中中文在部分 Windows/Maven 输出场景仍可能显示乱码，接口响应和源码文件按 UTF-8 处理。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`038-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/user/login?username=admin&password=123456` 返回 `code=200`
  3. `GET /api/food/page?current=1&size=5` 返回食物总数 `10`
  4. `GET /api/nutrition/daily?userId=2` 返回每日营养汇总与当前目标

### 5.2 前端构建

- 执行命令：`038-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vite build` 成功生成 `dist`
- 构建告警：
  1. `The CJS build of Vite's Node API is deprecated`
  2. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`038-backend/mvn spring-boot:run`
- 前端启动命令：`038-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端：`http://localhost:8038/api/food/page?current=1&size=1`
  2. 前端：`http://localhost:3038/login`
  3. 前端代理：`http://localhost:3038/api/user/login?username=admin&password=123456`
- 关键业务结果：
  1. 后端食物分页接口返回 `HTTP 200`
  2. 前端登录页返回 `HTTP 200`
  3. 经 Vite 代理调用登录接口返回 `HTTP 200`，响应包含演示 token

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`038-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - 管理员：`admin / 123456`
  - 普通用户：`user1 / 123456`
  - 普通用户：`user2 / 123456`
- 说明：
  1. 直接执行 `038-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认带 `/api` 上下文路径，例如 `POST http://localhost:8038/api/user/login`。
  3. 如需切换 MySQL，可先创建 `diet_management` 数据库，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3038`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8038`，前端真实启动使用端口 `3038`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、日志目录均已被仓库忽略，不纳入提交。
