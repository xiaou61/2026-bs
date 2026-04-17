# 029 中药食疗推荐交流平台检查报告

## 1. 检查结论

- 项目编号：`029`
- 台账名称：`中药食疗推荐交流平台`
- 工程文档名称：`中药餐饮推荐交流平台`
- 检查日期：`2026-04-15`
- 当前状态：`已完成`
- 综合结论：`已完成 JDK 17 兼容修复、前后端构建修复、默认环境自举、启动验证与核心链路抽测；当前项目可以在默认 H2 环境下通过测试并启动。`

## 2. 项目形态

- 后端目录：`029-backend`
- 前端目录：`029-frontend`
- 后端 README：`029-backend/README.md`
- 后端 PRD：`029-backend/PRD/029-中药餐饮推荐交流平台-PRD.md`
- 说明：
  - `readme_simple.md` 与前端 `package.json` 使用“中药食疗推荐交流平台”
  - 后端 README / PRD 使用“中药餐饮推荐交流平台”
  - 本轮台账继续沿用“中药食疗推荐交流平台”，但已在报告中标记名称口径冲突

## 3. 主要问题与修复

### 3.1 后端修复

1. 修复 `Result<T>` 静态工厂方法泛型不兼容，消除 JDK 17 编译失败。
2. 修复 `JwtUtil` 与 `jjwt 0.12.3` API 不匹配问题，改为可兼容的解析写法。
3. 将 `MyBatis-Plus` 依赖从 `mybatis-plus-boot-starter` 切换为 `mybatis-plus-spring-boot3-starter`，解决 Spring Boot 3 初始化失败。
4. 移除 `type-aliases-package` 导致的 `Collection` / `java.util.Collection` 别名冲突。
5. 将默认环境从强依赖本地 MySQL 改为 H2 文件库自举，并补充：
   - `application-mysql.yml`
   - `schema-h2.sql`
   - `data-h2.sql`
6. 新增默认种子数据，补齐普通用户、创作者、管理员、健康档案、食材、禁忌、食谱、话题、评论、收藏、认证申请。
7. 延长 JWT 密钥长度，修复 `HS512` 登录时报“密钥长度不足”的问题。
8. 给 `User.password` 增加 `@JsonIgnore`，修复 `/user/info`、`/admin/users` 返回密码哈希泄露。
9. 新增后端冒烟测试，确保 `mvn test` 真正覆盖启动、公开列表与登录链路。

### 3.2 前端修复

1. 安装缺失依赖，恢复 Vite 构建能力。
2. 补齐路由引用但实际缺失的页面：
   - `recipes/RecipeList.vue`
   - `recipes/RecipeDetail.vue`
   - `recipes/RecipeForm.vue`
   - `ingredients/IngredientList.vue`
   - `ingredients/IngredientDetail.vue`
   - `topics/TopicList.vue`
   - `topics/TopicDetail.vue`
   - `topics/TopicForm.vue`
   - `user/MyRecipes.vue`
   - `user/MyTopics.vue`
   - `user/Profile.vue`
   - `user/Collections.vue`
3. 修复请求拦截与错误提示字段，适配后端统一返回的 `message`。
4. 修复登录状态保存逻辑，适配后端 `LoginResponse` 结构。
5. 修复注册页缺少 `phone` 字段，适配后端 `RegisterRequest` 校验。
6. 修复首页食谱字段映射错误，适配后端 `title / coverImage`。
7. 修复前后端接口路径不一致：
   - 收藏列表改为 `/collection/my-collections`
   - 话题评论改为 `/comment/list/1/{topicId}`

## 4. 验证结果

### 4.1 后端验证

- 执行命令：`029-backend/mvn test -DskipTests=false`
- 结果：`通过`
- 新增测试：
  - `recipeListShouldReturnSeededData`
  - `loginShouldReturnTokenForSeededAdmin`

### 4.2 后端启动与接口抽测

- 启动命令：`029-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测通过接口：
  - `GET /api/v1/recipe/list`
  - `POST /api/v1/user/login`
  - `GET /api/v1/admin/users`（管理员 token）
  - `GET /api/v1/user/info`（普通用户 token）

### 4.3 前端验证

- 执行命令：`029-frontend/npm run build`
- 结果：`通过`

### 4.4 前端启动验证

- 启动命令：`029-frontend/npm run dev -- --host 127.0.0.1 --port 5173`
- 结果：`通过`
- 抽测结果：
  - `GET http://127.0.0.1:5173` 返回 `200`

### 4.5 默认账号

- 管理员：`admin029 / 123456`
- 创作者：`creator029 / 123456`
- 普通用户：`user029 / 123456`
- 待认证用户：`applicant029 / 123456`

## 5. 当前实现与 PRD 对照

### 5.1 已具备闭环

1. 用户注册、登录、个人资料查看与更新
2. 健康档案读取与保存
3. 食谱列表、详情、搜索、创建、我的食谱、收藏
4. 食材列表、详情、分类、搜索、禁忌查看
5. 话题列表、详情、搜索、创建、评论、我的话题
6. 创作者认证申请与认证信息查看
7. 管理端用户列表、待审食谱、评论与认证审核接口

### 5.2 仍存在缺口

1. 管理端目前只有后端接口，没有对应的独立前端后台页面。
2. 创作者统计、内容运营统计等 PRD 扩展能力未见完整前端落地。
3. README 与 PRD 的名称、接口命名仍有口径不一致。

## 6. 剩余风险

1. 项目名称“食疗 / 餐饮”仍未统一，后续如果要交付文档，建议统一为一套工程口径。
2. 前端生产构建已通过，但仍有 `chunk size` 过大的告警，后续可再做拆包优化。
3. 后端业务异常仍大量采用 `HTTP 200 + code/message` 约定，不是标准 REST 错误语义。
4. 默认环境已切到 H2；如果后续要接真实 MySQL，需要显式启用 `mysql` profile。

## 7. 结论口径

- 本项目已达到“可测试、可构建、可启动、核心链路可用”的巡检通过标准。
- 文档完整性评估：`明显缺口`
- JDK 17 兼容性评估：`通过`
- 后续可进入下一个项目巡检。
