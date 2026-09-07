# AIGC 图片内容审核与版权存证平台实现计划

## 问题陈述

AIGC 图片平台在作品发布、素材流转和商业授权过程中，需要识别违规内容、记录审核结论、登记版权归属并保留可信存证。传统图片管理系统通常只保存作品信息，缺少审核、确权、授权和申诉处理闭环。本项目建设一个面向 AIGC 图片合规和版权保护的后台系统。

## 当前状态

- 当前仓库已完成 `001`-`098` 项目。
- 新项目编号为 `099`。
- 后端目录：`099-backend`
- 前端目录：`099-frontend`
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
   - 插入管理员、审核员、创作者和演示数据

3. 实体类
   - `SysUser`
   - `ImageAsset`
   - `AuditRule`
   - `AuditTask`
   - `AuditResult`
   - `RiskTag`
   - `CopyrightRegister`
   - `EvidenceRecord`
   - `LicenseRecord`
   - `InfringementClue`
   - `AppealRecord`
   - `OperationLog`

4. Mapper 接口
   - 所有 Mapper 继承 `BaseMapper`

5. Service 层
   - 登录鉴权
   - 用户管理
   - 图片作品、审核规则、风险标签通用 CRUD
   - 审核任务启动和审核结果生成
   - 版权登记审批和存证编号生成
   - 授权、侵权线索和申诉处理
   - 统计看板和操作日志

6. Controller 层
   - 认证接口
   - 用户接口
   - 图片作品接口
   - 审核规则接口
   - 审核任务接口
   - 审核结果接口
   - 风险标签接口
   - 版权登记接口
   - 存证记录接口
   - 授权记录接口
   - 侵权线索接口
   - 申诉接口
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
   - 图片作品
   - 审核规则
   - 审核任务
   - 审核结果
   - 风险标签
   - 版权登记
   - 版权存证
   - 授权使用
   - 侵权线索
   - 申诉处理
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
- README 合集新增 `099` 项目说明
