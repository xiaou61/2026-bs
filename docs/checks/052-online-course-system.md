# 052 网课在线学习观看系统检查报告

## 1. 检查结论

- 项目编号：`052`
- 项目名称：`网课在线学习观看系统`
- 检查日期：`2026-04-27`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/8080、H2 自举缺失、MySQL 驱动坐标旧写法、分页方言固定 MySQL、普通用户 token 可访问管理端接口、注册/登录响应存在密码泄露风险、学习进度保存依赖已存在学习记录、收藏和评论缺少课程存在性校验、前端用户端/管理端代理仍指向 8080、MySQL 演示数据返回不存在本地图片路径以及后端缺少可执行冒烟测试等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，用户端和管理端均可完成生产构建，真实 HTTP 核心链路抽测通过。`

## 2. 项目形态

- 后端目录：`052-backend`
- 用户端目录：`052-frontend/user`
- 管理端目录：`052-frontend/admin`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.4 + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus`
- 默认后端端口：`8052`
- 用户端开发端口：`3052`
- 管理端开发端口：`3152`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8052`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 将 MySQL 驱动坐标更新为 `com.mysql:mysql-connector-j`，消除旧坐标 relocation 风险。
5. 增加 `jaxb-runtime`，降低 JDK 11+ 环境下旧 JWT 依赖运行时缺类风险。
6. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
7. MySQL 初始化脚本中的课程封面和 banner 本地图片路径同步改为空，避免切换 MySQL 后返回不存在资源。

### 3.2 登录、鉴权与接口修复

1. JWT 拦截器补充管理端权限校验，普通用户 token 访问 `/api/admin/**` 返回 HTTP `403` 和业务码 `403`。
2. `User` 实体对 `password` 字段增加 JSON 脱敏，注册、登录和用户信息响应不再包含密码。
3. 注册返回前清理密码字段，避免实体复用时出现敏感字段外泄。
4. 修改密码补充用户不存在校验，避免空指针异常。
5. 学习进度保存补充视频和课程存在性校验。
6. 保存学习进度时，如果当前用户还没有学习记录，会自动创建学习记录。
7. 添加收藏和评论前补充课程存在性校验，避免写入孤儿数据。

### 3.3 前端修复

1. 用户端 Vite 开发端口改为 `3052`。
2. 管理端 Vite 开发端口改为 `3152`。
3. 用户端和管理端代理目标统一改为 `http://localhost:8052`。
4. 新增用户端和管理端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `OnlineCourseApplicationSmokeTest`。
2. 新增 `052-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `052-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员账号 `admin / 123456` 可登录管理端接口。
3. 默认学生账号 `student / 123456` 可登录用户端接口。
4. 演示数据覆盖课程分类、课程、章节、视频、轮播图、评论、学习记录和收藏。
5. 游客可浏览轮播图、课程分类、课程列表、课程详情章节和评论列表。
6. 普通用户可注册登录、查看个人信息、开始学习、查看视频、保存进度、查看我的课程、收藏课程和发表评论。
7. 管理员可登录并查看统计接口。
8. 普通用户访问管理端接口会被拒绝。

### 4.2 仍有差距

1. 视频 URL 仍为演示路径，真实播放文件和转码/防盗链能力未落地。
2. 密码仍使用 MD5 存储，生产环境建议改为 BCrypt。
3. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
4. 支付课程、订单购买、教师后台和更细粒度运营能力仍可继续扩展。
5. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`052-backend/mvn clean test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 轮播图、课程分类、课程列表返回演示数据
  3. 课程章节和视频返回演示数据
  4. 管理员登录成功且响应不包含密码字段
  5. 普通用户注册成功且响应不包含密码字段
  6. 普通用户 token 访问管理端统计返回 `403`
  7. 管理员 token 可访问统计接口
  8. 普通用户可查询个人信息
  9. 普通用户可开始学习、查看视频、保存进度和查看我的课程
  10. 普通用户可收藏课程并查询收藏状态
  11. 普通用户可发表评论并在评论列表中查到记录

### 5.2 用户端构建

- 执行命令：`052-frontend/user/npm install --no-audit --no-fund`
- 结果：`通过`
- 执行命令：`052-frontend/user/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.3 管理端构建

- 执行命令：`052-frontend/admin/npm install --no-audit --no-fund`
- 结果：`通过`
- 执行命令：`052-frontend/admin/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.4 启动与接口抽测

- 后端启动命令：`052-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 轮播图列表：`GET http://localhost:8052/api/banner/list`
  2. 分类列表：`GET http://localhost:8052/api/category/list`
  3. 课程列表：`GET http://localhost:8052/api/course/list`
  4. 课程章节：`GET http://localhost:8052/api/course/1/chapters`
  5. 管理员登录：`POST http://localhost:8052/api/admin/login`
  6. 用户注册：`POST http://localhost:8052/api/user/register`
  7. 管理端统计：`GET http://localhost:8052/api/admin/stats`
  8. 用户信息：`GET http://localhost:8052/api/user/info`
  9. 开始学习：`POST http://localhost:8052/api/learn/start`
  10. 视频信息：`GET http://localhost:8052/api/learn/video/1`
  11. 保存进度：`POST http://localhost:8052/api/learn/progress`
  12. 我的课程：`GET http://localhost:8052/api/learn/my-courses`
  13. 添加收藏：`POST http://localhost:8052/api/favorite/add`
  14. 收藏状态：`GET http://localhost:8052/api/favorite/check/2`
  15. 添加评论：`POST http://localhost:8052/api/comment/add`
  16. 评论列表：`GET http://localhost:8052/api/comment/list?courseId=3`
- 关键业务结果：
  1. 轮播图、分类、课程和章节接口均返回业务码 `200`
  2. 管理员登录返回 `role=2`，且未返回密码字段
  3. 用户注册返回 `role=0`，且未返回密码字段
  4. 普通用户访问管理端统计返回 HTTP `403`
  5. 管理员统计、用户信息、开始学习、视频信息、保存进度和我的课程均返回业务码 `200`
  6. 收藏课程后查询 `/api/favorite/check/2` 返回 `true`
  7. 添加评论和评论列表均返回业务码 `200`
  8. 联调结束后 `8052` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`052-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`052-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认教师：`teacher / 123456`
- 默认学生：`student / 123456`
- 说明：
  1. 直接执行 `052-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8052/h2-console`，JDBC URL 为 `jdbc:h2:mem:online_course`。
  3. 如需切换 MySQL，可先导入 `052-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 用户端执行 `npm run dev` 后默认访问 `http://localhost:3052`。
  5. 管理端执行 `npm run dev` 后默认访问 `http://localhost:3152`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8052`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
