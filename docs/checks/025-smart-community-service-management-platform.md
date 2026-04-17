# 025 智慧社区综合服务管理平台检查报告

## 1. 检查结论

- 项目编号：`025`
- 项目名称：`智慧社区综合服务管理平台`
- 检查日期：`2026-04-10`
- 当前状态：`已完成`
- 结论：`项目已可在 JDK 17 下通过后端测试、前端构建并完成前后端启动；登录、业主、缴费、报修、公告、访客、车位链路可用，但在安全性和角色隔离方面与 PRD 仍有差距`

## 2. 项目形态

- 后端：`Spring Boot 3 + MyBatis XML`
- 前端：`Vue 3 + Vite + Element Plus`
- 后端目录：`025-backend`
- 前端目录：`025-frontend`
- PRD：`025-backend/PRD/product_requirements_document.md`

## 3. PRD 目标范围

PRD 主要覆盖以下模块：

1. 业主信息管理
2. 物业缴费
3. 在线报修
4. 社区公告
5. 访客登记
6. 停车位管理

角色分为：

1. 管理员（物业人员）
2. 业主（普通用户）

## 4. 当前已完成核对

已完成以下内容核对与复测：

1. 后端 `pom.xml`、`application.yml`、`schema.sql`
2. 后端控制器、服务层、Mapper 与 XML
3. 前端 `package.json`、路由、请求层和主要页面
4. PRD 与当前实现模块的覆盖关系
5. 后端测试、前端构建、前后端启动、接口抽测与浏览器联调

## 5. 本轮修复摘要

### 5.1 后端修复

1. 将默认后端端口从冲突的 `8081` 调整为 `8025`
2. 修正 `application.yml` 中 `mybatis` 配置层级错误，恢复 XML Mapper 正常加载
3. 默认运行环境从不可用的本机 MySQL 改为 `H2` 文件库自举
4. 新增：
   - `src/main/resources/sql/schema-h2.sql`
   - `src/main/resources/sql/data-h2.sql`
5. 增加 `spring.sql.init.encoding=UTF-8`，修复中文初始化数据乱码
6. 优化全局异常处理，避免接口返回 `msg=null`
7. 登录与用户查询返回前对密码字段做脱敏
8. 修复访客登记时间格式与前端提交格式不兼容的问题
9. 修复业主删除缺少关联数据校验的问题，改为返回友好业务提示

### 5.2 前端修复

1. 前端默认请求地址改为 `http://127.0.0.1:8025`
2. `vite.config.js` 增加固定开发端口 `3025`
3. 修复车位新增 / 更新时后端强制覆盖状态为 `FREE` 的断链问题，前端现可正确联动 `FREE / SOLD / RENTED`

## 6. 测试与启动验证

### 6.1 构建验证

- 后端：`mvn test -DskipTests=false`
- 结果：`通过`
- 说明：`项目无测试用例，当前为编译与测试阶段通过`

- 前端：`npm run build`
- 结果：`通过`

### 6.2 启动验证

- 后端启动：
  - 命令：`mvn spring-boot:run`
  - 结果：`通过`
- 前端启动：
  - 命令：`npm run dev -- --host 0.0.0.0 --port 3025`
  - 结果：`通过`

### 6.3 接口抽测

已验证后端接口链路：

1. `POST /user/login`
2. `POST /user/register`
3. `GET /user/list`
4. `POST /owner/add`
5. `POST /owner/update`
6. `GET /owner/list`
7. `POST /fee/create`
8. `POST /fee/pay/{id}`
9. `GET /fee/owner/{ownerId}`
10. `POST /repair/submit`
11. `POST /repair/status/{id}`
12. `GET /repair/owner/{ownerId}`
13. `POST /notice/add`
14. `POST /notice/update`
15. `DELETE /notice/{id}`
16. `GET /notice/list`
17. `POST /visitor/register`
18. `POST /visitor/status/{id}`
19. `GET /visitor/owner/{ownerId}`
20. `POST /parking/add`
21. `POST /parking/update`
22. `DELETE /parking/{id}`
23. `GET /parking/list`

### 6.4 浏览器联调验证

已使用无头浏览器实际验证：

1. 管理员登录成功
2. 首页可显示当前用户与角色
3. 业主管理页可加载真实列表数据
4. 物业缴费页可加载真实账单数据
5. 在线报修页可加载真实报修数据
6. 社区公告页可加载真实公告数据
7. 访客登记页可加载真实访客数据并显示状态
8. 停车位管理页可加载真实车位数据

## 7. 当前可用范围

当前已实现并可运行的模块主要包括：

1. 管理员登录
2. 用户注册与用户列表查询
3. 业主新增、编辑、列表查询
4. 物业账单生成、缴费、列表查询
5. 报修提交、状态流转、列表查询
6. 公告发布、编辑、删除、列表查询
7. 访客登记、状态流转、列表查询
8. 车位新增、编辑、删除、列表查询
9. 前端首页与六个业务页面联调

## 8. 与 PRD 的主要差距

以下能力目前仍有明显缺口：

1. 密码仍为明文存储，与 PRD “加密存储”不符
2. 当前没有真正的接口鉴权，管理员与业主角色未做后端权限隔离
3. 前端主要是统一管理台，缺少业主专属的“个人信息、待缴账单、我的车位”等独立体验
4. 报修图片上传、评价、催缴提醒、访客码等扩展能力未落地
5. 异常返回仍采用 `HTTP 200 + code=500` 风格，不够规范
6. 后端没有自动化测试用例

## 9. 当前结论

- `025` 已达到“可测试、可构建、可启动”的基础目标
- 当前已实现范围内的核心业务链路已经补齐并复测通过
- 但从 PRD 完整度和安全性角度看，仍属于“主流程可用，但安全和角色能力偏弱”的项目
