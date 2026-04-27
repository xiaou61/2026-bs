# 050 基于微信小程序的课堂考勤签到APP检查报告

## 1. 检查结论

- 项目编号：`050`
- 项目名称：`基于微信小程序的课堂考勤签到APP`
- 检查日期：`2026-04-27`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis、后端端口未项目化、artifact 与启动类命名串项、根目录 init.sql 错误串到剧本杀项目、JWT token 与过滤器不一致、角色语义沿用其他项目、登录响应泄露密码字段、MyBatis-Plus 分页方言固定 MySQL、教师创建签到任务时签到记录缺少 courseId、小程序默认接口仍指向 8080、tabBar 图标资源断链，以及后端缺少可执行冒烟测试等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，小程序页面声明和资源引用静态核查通过。`

## 2. 项目形态

- 后端目录：`050-backend`
- 小程序目录：`050-miniprogram`
- 后端技术栈：`Spring Boot 3.2.1 + MyBatis-Plus 3.5.5 + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`原生微信小程序`
- 默认后端端口：`8050`
- 小程序默认后端地址：`http://localhost:8050/api`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口调整为 `8050`，避免继续占用通用 `8080`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 将 Maven artifact 从错误的 `nursing-home` 改为 `classroom-attendance`。
5. 将启动类从错误的 `KaoyanStudyApplication` 更名为 `ClassroomAttendanceApplication`。
6. 将 MyBatis-Plus 分页方言改为配置项 `app.mybatis.db-type` 控制，默认 H2，MySQL profile 切换为 MySQL。
7. 放行 H2 控制台并启用 same-origin frame 配置。
8. 删除根目录中误遗留的剧本杀 `init.sql`，避免 SQL 口径冲突。

### 3.2 认证与接口修复

1. 登录生成 token 时写入当前课堂考勤角色字段。
2. JWT 过滤器按 `0-学生 / 1-教师 / 2-管理员` 映射角色，修复上一项目遗留的 `ROLE_NURSE`、`ROLE_FAMILY`。
3. JWT 过滤器补充 `request` attribute：`userId`、`username`、`role`。
4. 登录响应通过 `@JsonIgnore` 隐藏密码字段，避免将密码 hash 返回给小程序。
5. 登录 DTO 的 `password` 字段排除 `toString` 输出，减少日志泄露风险。
6. 用户资料更新改为只允许更新资料字段，避免普通用户覆盖角色、密码、状态等敏感字段。
7. 教师创建签到任务时，为每条待签到记录写入 `courseId`，修复数据库非空约束失败。
8. 学生签到成功后写入实际签到方式，并同步递增签到任务已签人数。
9. MySQL 初始化脚本中的默认账号密码改为 BCrypt hash，和现有登录校验逻辑保持一致。

### 3.3 小程序联调修复

1. 将小程序默认 `baseUrl` 改为 `http://localhost:8050/api`。
2. 移除 tabBar 中不存在的 `images/*.png` 图标引用，避免微信开发者工具编译时报资源缺失。
3. 请求工具兼容 HTTP `401/403`，未授权时自动清理登录态并跳转登录页。

### 3.4 文档与测试修复

1. 更新 `050-backend/README.md`，补齐默认 H2 启动、MySQL profile、H2 控制台、验证命令和默认账号说明。
2. 新增 `050-backend/启动说明.txt`，便于答辩和本地演示快速启动。
3. 新增后端冒烟测试 `ClassroomAttendanceApplicationSmokeTest`。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认账号覆盖管理员 `admin / 123456`、教师 `teacher1 / 123456`、学生 `student1 / 123456`。
3. 演示数据覆盖 2 个学期、9 个用户、4 门课程、6 条课程安排、选课关系、签到任务、签到记录、考勤统计、请假申请、补签申请、公告和消息。
4. 游客可浏览公告、当前学期和课程分页等公开数据。
5. 学生可登录、查看个人信息、查看我的课程、查看进行中签到任务、完成签到、查看统计、提交请假并查看消息。
6. 教师可登录、查看授课课程、创建签到任务、查看签到统计和审批列表。
7. 管理员可登录并访问用户分页等管理接口。
8. 小程序页面文件完整，`app.json` 声明的 9 个页面均具备 `.js/.json/.wxml/.wxss`。

### 4.2 仍有差距

1. 真实微信登录、openid 绑定和线上合法域名配置需在微信开发者平台补充。
2. 二维码展示、动态刷新和扫码链路仍需接入真实小程序扫码场景做端侧验证。
3. 定位签到当前使用简化距离计算，生产环境建议接入更严格的定位与防作弊策略。
4. 管理员端 UI 仍较简化，主要能力通过后端接口提供。
5. Redis 依赖仍保留用于后续扩展，但默认 H2 演示链路不依赖 Redis。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`050-backend/mvn clean test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 公告列表返回演示数据
  3. 当前学期返回第二学期
  4. 公开课程分页返回 4 门课程
  5. 管理员登录返回角色 `2`，且不返回密码字段
  6. 管理员用户分页返回 9 个用户
  7. 教师登录返回角色 `1`，并能查询授课课程
  8. 学生登录返回角色 `0`，并能查询个人信息和选课列表
  9. 学生可查询进行中签到任务
  10. 教师可创建签到任务并生成 3 条待签到记录
  11. 学生可完成普通签到
  12. 签到统计返回正常签到数量
  13. 学生可提交请假，教师可查看审批列表
  14. 学生可查询未读消息数

### 5.2 小程序静态核查

- 执行内容：检查 `app.json` 声明页面与 `.js/.json/.wxml/.wxss` 文件匹配情况，并搜索陈旧资源引用。
- 结果：`通过`
- 关键结果：`PageCount=9, MissingFiles=0, StaleRefs=0`

### 5.3 启动与接口抽测

- 后端启动命令：`050-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 公告列表：`GET http://localhost:8050/api/notice/latest`
  2. 课程分页：`GET http://localhost:8050/api/course/page`
  3. 三角色登录：`POST http://localhost:8050/api/auth/login`
  4. 教师课程：`GET http://localhost:8050/api/course/teacher`
  5. 学生课程：`GET http://localhost:8050/api/course/student`
  6. 创建签到：`POST http://localhost:8050/api/attendance/task/create`
  7. 学生签到：`POST http://localhost:8050/api/attendance/sign`
  8. 签到统计：`GET http://localhost:8050/api/attendance/record/stats/{taskId}`
  9. 未读消息：`GET http://localhost:8050/api/message/unreadCount`
- 关键业务结果：
  1. 公告返回 `2` 条演示数据
  2. 课程返回 `4` 条演示数据
  3. 管理员/教师/学生角色分别为 `2/1/0`
  4. 登录响应密码字段已隐藏
  5. 教师课程返回 `2` 条，学生课程返回 `3` 条
  6. 创建签到任务生成 `3` 条待签到记录
  7. 学生签到状态为 `1`，签到方式为 `1`
  8. 签到统计正常签到数为 `1`
  9. 学生未读消息数为 `2`
  10. 联调结束后 `8050` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`050-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`050-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / 123456`
  - 教师：`teacher1 / 123456`
  - 学生：`student1 / 123456`
- 说明：
  1. 直接执行 `050-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8050/api/auth/login`。
  3. H2 控制台地址为 `http://localhost:8050/h2-console`，JDBC URL 为 `jdbc:h2:mem:classroom_attendance`。
  4. 如需切换 MySQL，可先导入 `050-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  5. 使用微信开发者工具打开 `050-miniprogram`，后端默认地址已配置为 `http://localhost:8050/api`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8050`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. `target/`、临时日志目录等生成物不纳入提交。
