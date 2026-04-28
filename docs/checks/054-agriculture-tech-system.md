# 054 基于 SpringBoot 的农业生产技术管理系统检查报告

## 1. 检查结论

- 项目编号：`054`
- 项目名称：`基于 SpringBoot 的农业生产技术管理系统`
- 检查日期：`2026-04-28`
- 当前状态：`已完成`
- 综合结论：`本轮已修复后端 Maven 编译失败、默认环境强依赖 MySQL/8080、H2 自举缺失、分页方言固定 MySQL、后端缺少自动化测试、用户密码响应泄露风险、修改密码接口假成功、Bearer token 兼容缺失、普通用户可访问统计/用户管理等管理员接口、前端代理仍指向 8080、前端角色枚举大小写与后端不一致、登录页默认密码提示错误等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。`

## 2. 项目形态

- 后端目录：`054-backend`
- 前端目录：`054-frontend`
- 后端技术栈：`Spring Boot 3.2.0 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8054`
- 前端开发端口：`3054`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 修复 `ConsultationService` 中 `LambdaQueryWrapper` 误用 `set` 导致的编译失败。
2. 将后端默认端口从 `8080` 调整为 `8054`，避免合集内项目端口冲突。
3. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. 增加 H2 和 Spring Boot Test 依赖。
6. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。

### 3.2 登录、鉴权与接口修复

1. `User.password` 改为 `WRITE_ONLY`，支持新增用户和修改密码写入，但响应不再回传密码字段。
2. 修改密码接口改为读取 `oldPassword/newPassword`，补充原密码校验和用户不存在校验，修复前端提示成功但实际未改密的问题。
3. JWT 拦截器兼容 `Bearer` token。
4. JWT 拦截器兼容 `OPTIONS` 预检请求。
5. JWT 拦截器补充管理员接口权限校验，普通农户访问 `/api/stats/**` 和用户管理接口返回 HTTP `403`。
6. 登录、用户信息、用户分页等接口在默认 H2 数据下可真实访问。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3054`。
2. 前端代理目标改为 `http://localhost:8054`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 登录页默认账号提示改为真实密码 `123456`。
5. 用户管理和菜单权限使用后端真实角色枚举：`ADMIN`、`EXPERT`、`TECHNICIAN`、`FARMER`。
6. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `AgricultureApplicationSmokeTest`。
2. 新增 `054-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `054-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员、专家、技术员和农户账号可登录。
3. 管理员可查看统计概览、用户、作物、农业知识、生产计划、生产任务、病虫害、预警、农资、技术咨询、预约和公告。
4. 农户可登录、查看个人信息、发起技术咨询和查看预约。
5. 可新增作物、知识、生产计划、生产任务、病虫害、预警、农资、咨询和公告。
6. 可执行生产计划/任务状态流转。
7. 可执行农资入库、出库和库存预警查询。
8. 可新增专家回答并采纳答案。
9. 可完成专家预约状态流转和评价。
10. 前端登录、代理、角色展示和用户管理表单已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 图片上传、AI 病虫害识别、消息通知和找回密码等 PRD 扩展能力仍未完整落地。
4. 作物分类树当前只返回顶层节点，前端基础展示可用，但严格树形嵌套仍可增强。
5. 前端构建存在 Vite chunk 体积提示和 Sass legacy JS API 提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`054-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 管理员和农户登录成功
  3. 未携带 token 访问用户信息返回 HTTP `401`
  4. 管理员用户信息响应不包含密码字段
  5. 作物分类列表返回演示数据
  6. 管理员访问统计接口成功
  7. 农户访问统计接口返回 HTTP `403`

### 5.2 前端构建

- 执行命令：`054-frontend/npm install`
- 结果：`通过`
- 执行命令：`054-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示和 Sass legacy JS API 提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`054-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 管理员登录：`POST http://localhost:8054/api/user/login`
  2. 农户登录：`POST http://localhost:8054/api/user/login`
  3. 用户信息：`GET http://localhost:8054/api/user/info`
  4. 统计概览：`GET http://localhost:8054/api/stats/overview`
  5. 用户分页：`GET http://localhost:8054/api/user/page`
  6. 作物分类：`GET http://localhost:8054/api/crop/category/list`
  7. 作物分页：`GET http://localhost:8054/api/crop/page`
  8. 知识分页/详情/点赞/收藏：`/api/knowledge/**`
  9. 生产计划与任务：`/api/plan/**`、`/api/task/**`
  10. 病虫害与预警：`/api/pest/**`
  11. 农资入库/出库/记录：`/api/material/**`
  12. 技术咨询与答案采纳：`/api/consultation/**`
  13. 专家预约与评价：`/api/appointment/**`
  14. 公告发布/更新/删除/已发布列表：`/api/notice/**`
- 关键业务结果：
  1. 管理员和农户登录均返回业务码 `200`
  2. 用户信息响应未回传密码字段
  3. 未登录访问用户信息返回 HTTP `401`
  4. 农户访问统计接口返回 HTTP `403`
  5. 用户新增、登录、修改密码、重新登录和删除均返回业务码 `200`
  6. 作物、知识、生产计划、生产任务、病虫害、预警、农资、咨询、预约和公告接口均返回业务码 `200`
  7. 联调结束后 `8054` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`054-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`054-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认专家：`expert / 123456`
- 默认技术员：`tech / 123456`
- 默认农户：`farmer / 123456`
- 说明：
  1. 直接执行 `054-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8054/h2-console`，JDBC URL 为 `jdbc:h2:mem:agriculture_tech`。
  3. 如需切换 MySQL，可先导入 `054-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3054`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8054`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
