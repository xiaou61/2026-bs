# 049 基于微信小程序的考研学习系统检查报告

## 1. 检查结论

- 项目编号：`049`
- 项目名称：`基于微信小程序的考研学习系统`
- 检查日期：`2026-04-27`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis、后端端口未项目化、JWT 登录 token 与过滤器不一致、请求上下文缺少 userId、登录响应泄露密码字段、MyBatis-Plus 分页方言固定 MySQL、后端缺少可执行冒烟测试、小程序默认接口仍指向 8080、小程序 tabBar/占位图资源断链，以及答题/学习计划/打卡字段前后端不一致等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，小程序页面声明和资源引用静态核查通过。`

## 2. 项目形态

- 后端目录：`049-backend`
- 小程序目录：`049-miniprogram`
- 后端技术栈：`Spring Boot 3.2.1 + MyBatis-Plus 3.5.5 + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`原生微信小程序`
- 默认后端端口：`8049`
- 小程序默认后端地址：`http://localhost:8049/api`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口调整为 `8049`，避免继续占用通用 `8080`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`，无需本机预装 MySQL/Redis 即可演示。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 将 MyBatis-Plus 分页方言改为配置项 `app.mybatis.db-type` 控制，默认 H2，MySQL profile 切换为 MySQL。
5. 放行 H2 控制台并启用 same-origin frame 配置，便于默认演示环境查看数据。
6. 删除根目录中误遗留的剧本杀 `init.sql`，避免与当前考研系统 SQL 口径冲突。

### 3.2 认证与接口修复

1. 统一 JWT 工具类，登录生成 token 时写入当前项目角色字段，过滤器按 `0-学生 / 1-教师 / 2-管理员` 映射角色。
2. JWT 过滤器补充 `request` attribute：`userId`、`username`、`role`，修复个人中心、刷题、学习计划、笔记、收藏、社区等接口拿不到登录用户的问题。
3. 登录响应通过 `@JsonIgnore` 隐藏密码字段，避免将明文密码返回给小程序。
4. 用户资料更新改为只允许更新昵称、头像、手机号、邮箱、目标院校、目标专业、考试年份等资料字段，避免覆盖角色、密码、状态等敏感字段。
5. 答题 DTO 兼容小程序旧字段，并为缺省用时提供默认值。
6. 题目接口将 JSON 选项拆分为 `optionA`、`optionB`、`optionC`、`optionD`，与小程序刷题页字段对齐。
7. 错题首次入库时补充 `lastWrongTime`，使错题本倒序查询稳定。
8. 学习计划进度接口兼容 JSON body 和 query 参数两种调用方式。

### 3.3 小程序联调修复

1. 将小程序默认 `baseUrl` 改为 `http://localhost:8049/api`。
2. 移除 tabBar 中不存在的 `images/tab-*.png` 图标引用，避免微信开发者工具编译时报资源缺失。
3. 将课程封面、头像、社区头像等缺失图片改为 WXSS 渐变占位块，避免 `/images/default-avatar.png`、`/images/course-default.png` 断链。
4. 修复刷题提交字段，从旧的 `answer` 调整为后端兼容的 `userAnswer`。
5. 修复学习计划创建字段，补齐 `title`、`targetContent`、`startDate`、`endDate`、`dailyHours`、`progress`。
6. 修复每日打卡 `mood` 字段，从中文字符串改为数值。
7. 修复错题本展示字段，改为读取 `item.question.content` 和 `item.question.answer`。
8. 请求工具兼容 HTTP `401/403`，未授权时自动清理登录态并跳转登录页。

### 3.4 文档修复

1. 更新 `049-backend/README.md`，补齐默认 H2 启动、MySQL profile、接口文档、H2 控制台、验证命令和默认账号说明。
2. 新增 `049-backend/启动说明.txt`，便于答辩和本地演示快速启动。
3. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认账号覆盖管理员 `admin / 123456`、教师 `teacher1 / 123456`、学生 `student1 / 123456`。
3. 演示数据覆盖 8 个科目、4 门课程、9 个章节、13 个题库分类、5 道题、学习计划、打卡、笔记、帖子、评论、收藏和公告。
4. 游客可浏览科目、课程、课程详情、章节、题库、帖子和公告等公开数据。
5. 学生可登录、查看个人信息与学习统计、提交答题、生成错题、创建学习计划、更新计划进度、创建笔记、收藏课程、发帖评论和每日打卡。
6. 管理员可登录并访问仪表盘、用户、科目、课程、题库和公告管理接口。
7. 小程序页面文件完整，`app.json` 声明的 16 个页面均具备 `.js/.json/.wxml/.wxss`。

### 4.2 仍有差距

1. 当前密码仍为明文演示数据，尚未接入 BCrypt 存储与历史密码迁移。
2. 课程购买、视频播放、支付、订单和学习进度百分比计算仍偏简化。
3. 社区点赞、评论回复、消息通知和内容审核仍缺少更完整闭环。
4. 管理端仅提供后端接口，小程序侧没有独立后台管理 UI。
5. 真实微信登录、openid 绑定和线上合法域名配置需在微信开发者平台补充。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`049-backend/mvn clean test`、`049-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 公开科目列表返回 8 条演示数据
  3. 公开课程列表返回课程与讲师信息
  4. 公开题库列表返回题目并拆分选项字段
  5. 管理员登录返回角色 `2`，且不返回密码字段
  6. 管理员仪表盘返回用户、课程、题目统计
  7. 学生登录返回角色 `0`，且不返回密码字段
  8. 学生个人信息接口通过 JWT 识别当前用户
  9. 学生提交错误答案后生成错题记录
  10. 学生创建学习计划并更新进度成功
  11. 学生创建笔记成功
  12. 学生收藏课程并可查询收藏状态
  13. 学生发帖和评论成功
  14. 新注册学生可完成每日打卡并查询统计

### 5.2 小程序静态核查

- 执行内容：检查 `app.json` 声明页面与 `.js/.json/.wxml/.wxss` 文件匹配情况，并搜索陈旧资源引用。
- 结果：`通过`
- 关键结果：`PageCount=16, MissingFiles=0, StaleRefs=0`

### 5.3 启动与接口抽测

- 后端启动命令：`049-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 学生登录：`POST http://localhost:8049/api/auth/login`
  2. 科目列表：`GET http://localhost:8049/api/subject/list`
  3. 课程列表：`GET http://localhost:8049/api/course/list`
  4. 打卡统计：`GET http://localhost:8049/api/study/checkin/stats`
  5. 答题提交：`POST http://localhost:8049/api/question/submit`
  6. 管理员登录：`POST http://localhost:8049/api/auth/login`
  7. 管理员仪表盘：`GET http://localhost:8049/api/admin/dashboard`
- 关键业务结果：
  1. 学生登录返回业务码 `200`，角色为 `0`，登录响应不返回密码字段
  2. 科目列表返回 `8` 条演示数据
  3. 课程列表返回 `4` 条演示数据
  4. 打卡统计返回 `totalDays=2`
  5. 答题提交返回 `isCorrect=false`，可用于验证错题链路
  6. 管理员登录返回业务码 `200`，角色为 `2`
  7. 管理员仪表盘返回用户数 `5`、课程数 `4`、题目数 `5`
  8. 联调结束后 `8049` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`049-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`049-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / 123456`
  - 教师：`teacher1 / 123456`
  - 学生：`student1 / 123456`
- 说明：
  1. 直接执行 `049-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8049/api/auth/login`。
  3. H2 控制台地址为 `http://localhost:8049/h2-console`，JDBC URL 为 `jdbc:h2:mem:kaoyan_study`。
  4. 如需切换 MySQL，可先导入 `049-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  5. 使用微信开发者工具打开 `049-miniprogram`，后端默认地址已配置为 `http://localhost:8049/api`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8049`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. `target/`、临时日志目录等生成物不纳入提交。
