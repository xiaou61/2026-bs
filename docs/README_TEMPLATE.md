# NNN - 项目名称

> 一句话说明项目解决的问题和主要使用场景。

## 项目概览

| 属性 | 内容 |
|------|------|
| 项目编号 | `NNN` |
| 后端目录 | `NNN-backend` |
| 前端目录 | `NNN-frontend` / 无 |
| 小程序目录 | `NNN-miniprogram` / `NNN-miniapp` / 无 |
| 后端端口 | 以 `application*.yml` 为准 |
| 前端端口 | 以 `vite.config.*` 为准 |

## 技术栈

- 后端：Spring Boot、数据访问框架、认证方式
- 前端：Vue / React / uni-app / 原生小程序
- 数据库：MySQL / PostgreSQL / H2 等
- 其他：Redis、文件存储、消息或第三方服务

## 功能模块

- 模块一：说明核心流程
- 模块二：说明核心流程
- 模块三：说明核心流程

## 环境要求

- JDK：填写 `pom.xml` 中的实际版本
- Maven：填写最低版本
- Node.js：填写 `package.json` 对应版本
- 数据库和 Redis：写明是否必需，以及默认启动模式

## 快速启动

### 后端

```bash
cd NNN-backend
# 如果使用外部数据库，先执行下方数据库配置和初始化步骤
mvn spring-boot:run
```

### 数据库和配置

1. 配置文件：`src/main/resources/application*.yml`
2. 初始化脚本：填写真实相对路径，例如 `sql/init.sql`
3. 启动 profile：填写默认 profile 和可选 profile
4. 明确脚本是否包含 `DROP DATABASE` 或其他破坏性操作

### 前端或小程序

```bash
cd ../NNN-frontend
npm install
npm run dev
```

填写前端代理目标、开发端口，以及小程序 AppID 或开发者工具要求。

## 演示账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | `admin` | `请填写` | 仅本地演示 |

## 验证状态

- 后端测试：`mvn test` / 未提供
- 前端构建：`npm run build` / 未验证
- 最近验证日期：`YYYY-MM-DD`
- 已知问题：列出不能运行、需要外部服务或尚未覆盖的部分

## 相关链接

- 文档站页面：`docs-site/projects/NNN.md`
- 检查报告：`docs/checks/NNN-*.md`
- 截图目录：`docs/previews/assets/NNN/`
