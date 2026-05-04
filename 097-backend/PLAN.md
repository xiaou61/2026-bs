# 大模型提示词资产管理与效果评测平台实现计划

## 问题陈述

企业 AI 应用团队在日常研发中会沉淀大量 Prompt，但常见管理方式依赖文档、聊天记录或个人笔记，缺少版本追踪、测试集评估和效果对比。本项目建设一个面向 Prompt 工程的资产管理与评测平台，支持 Prompt 从创建、版本迭代、测试评测到反馈复核的完整闭环。

## 当前状态

- 当前仓库已完成 `001`-`096` 项目。
- 新项目编号为 `097`。
- 后端目录：`097-backend`
- 前端目录：`097-frontend`
- 技术栈遵循仓库规则：Spring Boot、Vue、MyBatis-Plus、MySQL、Redis、JWT。

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
   - `MybatisPlusConfig`
   - `RedisConfig`
   - `JwtUtils`

2. 数据库
   - 创建 `sql/init.sql`
   - 建立 12 张业务表
   - 插入管理员、工程师、评审员和业务演示数据

3. 实体类
   - `SysUser`
   - `TeamSpace`
   - `PromptCategory`
   - `PromptAsset`
   - `PromptVersion`
   - `PromptTestCase`
   - `ModelConfig`
   - `ScoreRule`
   - `EvaluationTask`
   - `EvaluationResult`
   - `PromptFeedback`
   - `OperationLog`

4. Mapper 接口
   - 所有 Mapper 继承 `BaseMapper`

5. Service 层
   - 登录鉴权
   - 用户管理
   - 通用 CRUD
   - Prompt 资产版本管理
   - 评测任务启动和结果生成
   - 统计看板
   - 操作日志

6. Controller 层
   - 认证接口
   - 用户接口
   - 团队接口
   - 分类接口
   - 资产接口
   - 版本接口
   - 测试用例接口
   - 模型配置接口
   - 评分规则接口
   - 评测任务接口
   - 评测结果接口
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
   - 团队空间
   - Prompt 分类
   - Prompt 资产
   - Prompt 版本
   - 测试用例
   - 模型配置
   - 评分规则
   - 评测任务
   - 评测结果
   - 反馈管理
   - 操作日志

### 第三阶段：合集文档更新

1. 更新 `readme.md`
2. 更新 `readme_simple.md`
3. 更新候选题状态记录

## 验收标准

- 后端目录和前端目录完整
- 后端包含 12 张表、实体、Mapper、Service、Controller
- 前端包含登录、布局、仪表盘和全部业务页面
- 默认账号可用于演示系统功能
- README 合集新增 `097` 项目说明
