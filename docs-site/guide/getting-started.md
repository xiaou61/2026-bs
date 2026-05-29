# 快速上手

本指南帮助你快速了解和使用 2026 毕设项目合集。

## 项目结构

每个毕设项目由以下部分组成：

```
项目根目录/
├── NNN-backend/          # 后端源码 (Spring Boot)
├── NNN-frontend/         # 前端源码 (Vue 3 / 小程序)
└── docs-site/projects/NNN.md  # 项目文档
```

其中 `NNN` 为项目编号（如 001、050、100 等）。

## 技术栈概览

### 后端

所有项目统一使用以下后端技术栈：

- **Spring Boot 3.x** - Java Web 框架
- **MyBatis-Plus** - ORM 框架
- **MySQL 8.0** - 关系型数据库
- **Redis** - 缓存中间件
- **Maven** - 项目构建工具

### 前端

前端分为两种类型：

#### Web 应用
- **Vue 3** - 前端框架
- **Element Plus** - UI 组件库
- **Vite** - 构建工具
- **Axios** - HTTP 请求

#### 微信小程序
- **微信小程序原生开发** / **uni-app**
- **WeUI** - UI 组件库

## 环境准备

在运行任何项目之前，请确保你的开发环境已安装：

| 工具 | 版本要求 | 说明 |
|------|----------|------|
| JDK | 17+ | Java 开发环境 |
| Maven | 3.8+ | Java 构建工具 |
| Node.js | 18+ | 前端运行环境 |
| MySQL | 8.0+ | 数据库 |
| Redis | 6.0+ | 缓存服务 |
| 微信开发者工具 | 最新版 | 仅小程序项目需要 |

## 启动项目

### 1. 后端启动

```bash
cd NNN-backend

# 1. 修改数据库配置
# 编辑 src/main/resources/application.yml
# 修改 spring.datasource.url、username、password

# 2. 创建数据库
mysql -u root -p -e "CREATE DATABASE db_xxxx DEFAULT CHARACTER SET utf8mb4;"

# 3. 导入初始数据（如有）
mysql -u root -p db_xxxx < sql/init.sql

# 4. 启动后端
mvn spring-boot:run
```

### 2. 前端启动

#### Web 应用

```bash
cd NNN-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

#### 微信小程序

使用微信开发者工具打开小程序目录即可。

## 查找项目

你可以通过以下方式找到你需要的项目：

1. **按分类浏览** - 在导航栏选择项目分类
2. **按编号查找** - 如果你知道项目编号，直接访问 `/projects/NNN`
3. **搜索** - 使用站点搜索功能，输入关键词查找

## 常见问题

### Q: 数据库连接失败？

检查 `application.yml` 中的数据库配置，确保 MySQL 服务已启动，数据库已创建。

### Q: 前端页面空白？

确保后端服务已启动，前端代理配置正确指向后端地址。

### Q: 小程序真机调试失败？

确保小程序 AppID 配置正确，已在微信开发者工具中配置合法域名。

### Q: Maven 依赖下载失败？

配置国内 Maven 镜像源（如阿里云镜像），在 `settings.xml` 中添加镜像配置。
