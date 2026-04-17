# 011 校园短视频创作与分享平台检查报告

## 1. 项目基本信息

- 项目编号：`011`
- 项目名称：校园短视频创作与分享平台
- 检查日期：`2026-04-05`
- 项目结构：
  - 后端：`011-backend`
  - 前端：`011-frontend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`011-backend/PRD/PRD.md`
  - 账号文档：`011-backend/ACCOUNTS.md`
  - 前端说明：`011-frontend/README.md`
  - 历史资料：`011-backend/FEATURES_CHECK.md`、`011-backend/FINAL_CHECK_REPORT.md`、`011-backend/IMPLEMENTATION_SUMMARY.md`

## 2. 检查目标

- 核对 PRD、账号文档、前端说明与当前源码是否基本一致
- 验证项目在 JDK 17 下的构建、测试和启动情况
- 抽测用户端核心链路、权限链路和关键统计链路
- 对发现的明确问题直接修复，并完成复测闭环

## 3. 文档完整性与技术栈结论

### 3.1 文档完整性

- 后端资料较完整，包含 PRD、账号说明、初始化 SQL 和历史总结文档
- 前端包含独立 `README.md`、`package.json`、`vite.config.js`
- 文档存在过至少一处口径冲突：
  - PRD 管理员密码曾写为 `admin123`
  - 账号文档实际为 `admin / 123456`
  - 本轮已修正 PRD 口径

### 3.2 技术栈与环境

- 后端：
  - Spring Boot `3.2.0`
  - MyBatis-Plus `3.5.5`
  - MySQL Connector/J `8.1.0`
  - Redis
  - JWT `0.12.5`
  - JDK `17`
- 前端：
  - Vue `3`
  - Vite `5`
  - Element Plus `2`
  - Pinia `2`
  - Axios

### 3.3 运行前置

- 后端端口：`8011`
- 前端端口：`5173`
- 数据库：`campus_video`
- 数据库账号：`root / 1234`
- Redis：`localhost:6379`
- 文件目录：`D:/campus-video-files/`

## 4. 本轮检查与修复结果

### 4.1 已确认并修复的问题

1. 修复前端 `User.vue` 模板缺失闭合标签导致的构建失败
2. 修复后端 `/api/admin/**` 仅校验 token、不校验管理员角色的问题
3. 修复“我的喜欢 / 我的收藏”错误复用作品接口的问题
4. 修复 PRD 与账号文档中管理员默认密码不一致的问题
5. 补齐 PRD 缺失接口：
   - `GET /api/video/{id}/related`
   - `GET /api/stats/creator`
   - `GET /api/stats/video/{id}`
   - `GET /api/user/points/mall/items`
   - `POST /api/user/points/exchange`
6. 补齐用户端核心缺口：
   - 草稿箱页面
   - 创作中心页面
   - 积分商城页面
   - 视频详情相关推荐
   - 评论回复与评论点赞切换
   - 发布页草稿保存 / 回填 / 发布后清理草稿
7. 修复发布视频时 `topicIds` 强转导致的 `Integer -> Long` 类型问题
8. 修复新视频发布未写入 `publishTime` 的问题
9. 修复历史视频 `publishTime` 为空导致的兼容问题：
   - 创作中心统计低估
   - 视频详情时间为空
   - 热度更新存在潜在空指针风险
10. 修复历史媒体 URL 脏数据导致的页面媒体加载失败问题：
   - 旧数据仍指向 `http://localhost:8080/api/upload/...`
   - 旧封面包含无效 `blob:http://localhost:3000/...`
   - 已在前端请求层统一重写旧 URL，并清空无效 `blob:` 地址

### 4.2 本轮未展开的大项

- 管理员后台前端整套页面仍未实现
- 这一点与历史总结文档、自检报告和当前前端目录结构一致
- 本轮已补齐普通用户最关键的缺口，但未继续扩展管理员前端整块能力

## 5. 构建、测试与启动验证

### 5.1 后端

- 验证命令：`mvn test -DskipTests=false`
- 结果：通过
- 结论：JDK 17 下可正常编译并完成测试阶段

### 5.2 前端

- 验证命令：`npm run build`
- 结果：通过
- 结论：当前 Node 环境下可正常构建

### 5.3 启动验证

- 后端 `8011` 已成功启动并完成接口抽测
- 前端 `5173` 已成功启动并完成浏览器侧页面抽测

## 6. 关键实测链路

### 6.1 鉴权与权限

- 管理员 `admin / 123456`、普通用户 `codex011user / 123456` 登录链路通过
- 普通用户访问管理员接口已正确返回 `403`

### 6.2 创作与统计链路

- 使用测试用户 `codex011user / 123456` 完成多次演示发布
- 最新复测视频 ID：`9`
- `GET /api/stats/video/9` 返回非空 `publishTime`
- `GET /api/stats/creator` 返回：
  - `videoCount = 4`
  - `weeklyVideoCount = 4`
- 历史视频 `id = 6` 的 `publishTime` 也已通过兼容逻辑正常返回

### 6.3 草稿箱链路

- `POST /api/draft/save`：通过
- `GET /api/draft/list`：通过
- `GET /api/draft/{id}`：通过
- `DELETE /api/draft/{id}`：通过

### 6.4 积分商城链路

- `GET /api/user/points/mall/items`：通过
- `POST /api/user/points/exchange`：通过
- `GET /api/user/points/log`：通过
- 已实测生成新的积分日志：
  - `VIDEO +10`，原因：`发布视频`
  - `EXCHANGE -10`，原因：`积分兑换：创作新星勋章`

### 6.5 视频详情与相关推荐

- `GET /api/video/{id}/related?size=3`：通过
- 视频详情页的分享、评论、回复、相关推荐链路已完成接线

### 6.6 浏览器页面验证

使用 Playwright 对以下页面进行了带登录态验证：

- `/`
- `/drafts`
- `/creator-center`
- `/points-mall`
- `/video/1`

验证结果：

- 无白屏
- 无页面脚本异常
- 无控制台错误
- 无失败请求

## 7. 仍需记录的风险

### 7.1 管理员前端未实现

- 后端管理员接口存在
- 前端仍缺少完整管理员后台页面
- 这属于项目剩余范围风险，不影响本轮“测试通过并可启动”的结论

### 7.2 自动化测试覆盖仍偏弱

- 当前后端 `mvn test` 实际更多是编译验证
- 项目仍缺少成体系的后端单元测试 / 集成测试

### 7.3 前端主包偏大

- `vite build` 仍提示部分 chunk 超过 `500 kB`
- 当前不阻塞启动与使用，但后续可继续优化拆包

## 8. 最终结论

`011` 项目本轮检查结论为：`已完成`、`已通过测试并可启动`。

综合判断：

- 文档资料基本齐全，关键口径冲突已修正
- 后端在 JDK 17 下可通过测试并成功启动
- 前端可成功构建并启动
- 用户端关键链路已经可以闭环使用
- 本轮发现的明确问题均已修复并复测通过

当前可以把 `011` 计入“已完成检查、已通过测试并可启动”。
