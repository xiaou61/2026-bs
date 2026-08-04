# 2026 年计算机毕业设计项目合集

> 200 个基于 Spring Boot、Vue、微信小程序等技术的计算机毕业设计项目，覆盖校园、社区、电商、医疗、养老、农业和企业信息化等方向。

本仓库是一个项目集合仓库。每个项目按编号拆分为后端、前端或小程序模块；项目级说明和源码统计由文档站统一生成，方便按场景、技术栈和项目规模筛选。

<p align="center">
  <a href="#项目索引">项目索引</a> |
  <a href="#快速开始">快速开始</a> |
  <a href="#文档开发">文档开发</a> |
  <a href="#维护约定">维护约定</a>
</p>

---

<a name="项目索引"></a>
## 项目索引

| 入口 | 用途 |
|------|------|
| [在线文档站](http://36.140.150.167:8013/) | 在线搜索、分类浏览和技术栈筛选（服务可用时访问） |
| [文档站源码](docs-site/index.md) | 本地运行 VitePress 文档站 |
| [项目标题速览](readme_simple.md) | 快速浏览 001-200 的项目名称 |
| [项目详情目录](docs/projects/) | 按编号分组的 Markdown 项目目录 |
| [项目截图预览](docs/previews/groups/) | 各项目运行截图索引 |
| [项目 README 模板](docs/README_TEMPLATE.md) | 新增或补齐项目文档时使用 |

## 项目概览

- **项目编号**：001-200，共 200 个项目
- **后端模块**：200 个
- **前端模块**：188 个
- **小程序模块**：4 个（`miniprogram` 或 `miniapp`）
- **主要技术**：Spring Boot、MyBatis / MyBatis-Plus、MySQL / PostgreSQL / H2、Vue、Element Plus、Vite、Redis

项目目录命名约定如下：

```text
001-backend/       后端服务
001-frontend/      Vue 或其他 Web 前端（如果项目提供）
049-miniprogram/   小程序端（如果项目提供）
docs-site/         VitePress 文档站
scripts/           文档生成和校验脚本
```

每个项目的准确端口、Java / Node 版本、数据库模式、初始化脚本、测试账号和验证状态，以对应的 [文档站项目页](docs-site/projects/) 为准。

---

<a name="快速开始"></a>
## 快速开始

### 环境要求

- JDK：以项目的 `pom.xml` 为准，常见为 17
- Maven：3.8+
- Node.js：前端项目按 `package.json` 和项目 README 为准
- MySQL / PostgreSQL / Redis：仅在对应项目的配置或文档明确要求时安装

### 启动一个前后端分离项目

以下命令以 001 为例，具体项目请先打开对应的文档站页面：

```bash
# 后端
cd 001-backend
mvn spring-boot:run

# 另开终端启动前端
cd ../001-frontend
npm install
npm run dev
```

数据库启动方式存在差异：

1. H2 演示模式通常可以直接启动，不需要预先创建 MySQL 数据库。
2. MySQL / PostgreSQL 模式需要先确认 `application*.yml` 或 `application*.properties` 中的 profile 和数据库名。
3. 执行 SQL 前请先检查脚本是否包含 `DROP DATABASE`，避免误删本地数据。
4. 不要默认假定后端端口是 8080；每个项目页会显示源码中提取到的端口和前端代理目标。

---

<a name="文档开发"></a>
## 文档开发

根目录安装依赖后，可以重新生成项目页、校验文档数据并构建文档站：

```bash
npm install
npm run docs:generate
npm run docs:validate
npm run docs:build
```

本地预览：

```bash
npm run docs:dev
```

生成流程会从项目源码、项目 README / PRD、检查报告和截图目录提取信息，更新以下内容：

- `docs-site/projects/*.md`：200 个项目详情页
- `docs-site/categories/*.md`：按方向分类的项目页
- `docs-site/.vitepress/*.json`：侧边栏、搜索和对比筛选数据
- `docs-site/public/previews/assets/`：文档站截图资源

`npm run docs:validate` 会检查项目索引、项目页数量、生成页中的端口和数据库表数一致性，以及根 README 的本地链接。生成文件不要手工修改，应该修改源 README、PRD 或生成脚本后重新运行命令。

---

<a name="维护约定"></a>
## 维护约定

新增或更新项目时，至少维护以下信息：

- 一句话项目简介和主要功能模块
- 后端、前端或小程序的实际目录
- JDK、Node.js、数据库和 Redis 要求
- 默认启动模式、准确端口和代理地址
- SQL 初始化路径及是否会覆盖数据库
- 演示账号、测试命令和最后验证日期
- 截图、已知问题和对应的检查报告

项目目录下的 README 可以包含更详细的 API、目录结构和开发说明；统一结构请参考 [项目 README 模板](docs/README_TEMPLATE.md)。

默认账号只用于本地演示，发布或部署前必须修改密码。请不要把生产环境的密钥、数据库密码或真实个人数据写入 README、SQL 和示例配置。

## 反馈

发现项目说明、链接或启动步骤错误时，请提交 Issue，并附上项目编号、操作系统、JDK / Node.js 版本和完整错误信息。

## 许可证

仓库根目录目前没有统一的 `LICENSE` 文件。使用或二次分发前，请同时确认根仓库、项目目录和依赖项的实际许可证声明。
