# 021 校园二手交易平台检查报告

## 1. 基本信息

- 项目编号：`021`
- 项目名称：校园二手交易平台
- 检查日期：`2026-04-08`
- 项目目录：
  - 后端：`021-backend`
  - 前端：`021-frontend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`021-backend/PRD/PRD.md`
  - 后端说明：`021-backend/README.md`
  - 后端配置：`021-backend/pom.xml`
  - 后端应用配置：`021-backend/src/main/resources/application.yml`
  - 初始化脚本：`021-backend/src/main/resources/sql/init.sql`
  - 前端依赖：`021-frontend/package.json`

## 2. 项目形态结论

- 当前仓库已确认存在 `021-backend` 与 `021-frontend`
- 项目形态为 Spring Boot 3.2 + Vue 3 的前后端分离项目
- 巡检完成后，用户侧核心链路已形成“注册/登录 -> 商品发布 -> 收藏/聊天/议价 -> 订单 -> 评价 -> 信用分”的闭环

## 3. PRD 对照与修复结论

### 3.1 巡检前真实状态

1. 后端只完整落地了用户、分类、商品，订单/聊天/收藏虽然有实体和表结构，但没有完整控制器与服务闭环
2. 前端路由引用了大量根本不存在的页面文件，`npm run build` 初始必然失败
3. 登录态存储结构与后端返回 `UserLoginVO` 不匹配，登录后前端无法正确写入用户信息
4. README 中明确把订单、聊天、收藏、图片上传、信用评价、管理后台列为待完成功能，和 PRD 完整度不一致

### 3.2 本轮已完成修复

1. 补齐后端订单、聊天、收藏模块：
   - 新增 `OrderController` / `FavoriteController` / `ChatController`
   - 新增对应 DTO、VO、Mapper、Service、ServiceImpl
2. 修复 JDK 17 / Spring Boot 3.2 启动兼容性：
   - 将 `mybatis-plus-boot-starter` 切换为 `mybatis-plus-spring-boot3-starter`
3. 修复订单分页编译错误：
   - `OrderServiceImpl` 的 `LambdaQueryWrapper.eq(...)` 由错误的 `Function` 改为 `SFunction`
4. 修复商品接口鉴权断链：
   - 去掉 `WebConfig` 中过宽的 `/product/*` 放行
   - 仅在 `JwtInterceptor` 中放行公开商品详情 `GET /product/{id}`
5. 修复议价核心业务缺口：
   - 卖家接受议价后，系统现在会自动生成订单，并把商品状态更新为 `sold`
6. 修复个人资料更新逻辑：
   - 新增 `UserUpdateDTO`
   - 避免空密码覆盖数据库中的原密码
7. 修复商品 SQL 语法错误：
   - `product.condition` 在 MySQL 中需要转义，已统一在实体和 Mapper XML 中处理
8. 补齐图片上传能力：
   - 新增 `UploadController`
   - 新增本地上传目录静态资源映射
   - 前端发布页接入本地图片上传
9. 补齐前端用户侧页面与接线：
   - `layout/index.vue`
   - 登录 / 注册
   - 首页商品列表
   - 商品详情
   - 发布商品 / 我的发布
   - 收藏列表
   - 聊天列表 / 聊天详情
   - 个人中心 / 公开资料页
10. 补齐前端 API 模块：
   - `order.js`
   - `favorite.js`
   - `chat.js`
   - `upload.js`

## 4. 构建、启动与抽测结果

### 4.1 构建结果

- 后端：`021-backend`
  - 执行：`mvn test -DskipTests=false`
  - 结果：通过
  - 备注：当前项目没有测试源码，`Surefire` 通过但无自动化测试用例
- 前端：`021-frontend`
  - 执行：`npm run build`
  - 结果：通过
  - 备注：存在 Vite 大包告警，但不影响构建产物生成

### 4.2 启动结果

- 后端启动：
  - 命令：`mvn spring-boot:run`
  - 端口：`8080`
  - 结果：通过
- 前端启动：
  - 命令：`vite.cmd --host 127.0.0.1 --port 3021`
  - 端口：`3021`
  - 结果：通过
- 页面访问：
  - `http://127.0.0.1:3021` 返回 `200`
  - 页面标题为“校园二手交易平台”

### 4.3 核心链路抽测

本轮已手工复测并通过以下链路：

1. 获取分类列表
2. 注册卖家账号、注册买家账号
3. 卖家登录、买家登录
4. 卖家发布商品
5. 买家收藏商品
6. 买家发起议价
7. 卖家接受议价，系统自动生成订单
8. 买家查看订单、确认收货
9. 买卖双方互评，买家信用分更新到 `102`
10. 买家更新个人资料后重新登录，原密码仍可正常登录
11. 图片上传接口返回图片 URL
12. 上传后的图片 URL 可直接访问，返回 `200`

## 5. 当前阶段结论

- `021` 已完成本轮巡检、修复、构建、启动与关键链路复测
- 按用户侧核心功能衡量，PRD 的主要交易闭环已经补齐并可运行
- 当前项目可在 JDK 17、MySQL、本机 Node 环境下完成后端启动、前端启动与关键业务操作

## 6. 剩余风险

1. PRD 中提到的“简化管理后台”仍未落地，当前数据库设计也未提供独立管理员角色字段
2. 后端 `mvn test` 虽然通过，但项目仍缺少真正的自动化测试用例
3. 前端仍存在 Vite 主包体积较大的告警，后续可继续拆包优化
