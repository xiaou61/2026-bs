# 基于 Spring Boot 的剧本杀创作与预约平台

## 项目简介

本系统是一个剧本杀创作与预约平台，支持玩家、店家、作者、管理员四类角色协同完成剧本创作、内容审核、店铺房间管理、玩家预约、店家确认完成、评价收藏和公告管理等流程。项目采用前后端分离架构，后端使用 Spring Boot 3 + MyBatis-Plus，前端使用 Vue 3 + TypeScript + Element Plus。

本轮巡检后，项目默认使用 H2 内存数据库自举，直接启动即可演示；如需连接 MySQL，可切换到 `mysql` profile。

## 技术栈

### 后端

- JDK 17
- Spring Boot 3.2.1
- MyBatis-Plus 3.5.5
- Spring Security + JWT
- H2 默认演示数据库
- MySQL 8 可选部署数据库
- Knife4j / springdoc-openapi
- Lombok

### 前端

- Vue 3
- TypeScript
- Vite
- Element Plus
- Vue Router
- Pinia
- Axios

## 功能模块

### 管理员端

- 系统数据统计
- 用户管理
- 店铺管理
- 剧本审核与上下架
- 剧本分类管理
- 公告管理

### 店家端

- 店铺信息管理
- 房间管理
- 店铺上架剧本管理
- 预约订单确认与完成

### 作者端

- 剧本创作与发布
- 章节和角色剧本内容维护
- 作者作品列表
- 提交管理员审核

### 玩家端

- 剧本浏览与搜索
- 店铺浏览与房间查看
- 在线预约
- 我的预约
- 收藏管理
- 评价功能
- 系统公告

## 数据库设计

系统包含 11 张核心表：

- `sys_user`：用户表，覆盖玩家、店家、作者、管理员
- `shop`：店铺表
- `room`：房间表
- `script_category`：剧本分类表
- `script`：剧本表
- `script_content`：剧本内容表
- `shop_script`：店铺剧本关联表
- `reservation`：预约表
- `review`：评价表
- `favorite`：收藏表
- `notice`：公告表

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Node.js 18+
- npm 9+

### 后端默认 H2 启动

```bash
cd 047-backend
mvn spring-boot:run
```

默认后端地址：

- 接口地址：`http://localhost:8047`
- 接口文档：`http://localhost:8047/doc.html`
- H2 控制台：`http://localhost:8047/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:script_kill`
- H2 用户名：`sa`
- H2 密码：留空

默认 H2 模式会自动执行：

- `src/main/resources/schema-h2.sql`
- `src/main/resources/data-h2.sql`

### 后端 MySQL 启动

如需使用 MySQL：

1. 创建数据库 `script_kill`
2. 导入 `047-backend/sql/init.sql`
3. 根据本机环境修改 `src/main/resources/application-mysql.yml`
4. 启动：

```bash
cd 047-backend
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

### 前端启动

```bash
cd 047-frontend
npm install
npm run dev
```

默认前端地址：

- 前端页面：`http://localhost:3047`
- Vite 代理：`/api -> http://localhost:8047`

### 前端构建

```bash
cd 047-frontend
npm run build
```

## 测试账号

| 角色 | 用户名 | 密码 | 角色值 |
| --- | --- | --- | ---: |
| 管理员 | `admin` | `123456` | 3 |
| 店家 | `shop1` | `123456` | 1 |
| 作者 | `author1` | `123456` | 2 |
| 玩家 | `user1` | `123456` | 0 |

## 验证命令

```bash
cd 047-backend
mvn test
```

当前后端冒烟测试覆盖：

- 管理员、玩家、店家、作者四角色登录
- 公告列表
- 管理员统计
- 剧本分类、剧本列表、店铺列表、房间列表
- 玩家创建预约
- 店家确认预约和完成预约
- 玩家收藏与评价
- 作者创建剧本
- 管理员审核剧本
- 审核后玩家侧可检索到新剧本

```bash
cd 047-frontend
npm run build
```

当前前端构建可通过，仍存在 Vite 主包体积超过 500KB 的提示，不影响默认启动与演示。

## 业务流程

1. 作者创建剧本，编写章节和角色剧本内容，提交审核。
2. 管理员审核剧本，通过后上架。
3. 店家维护店铺房间，并管理店铺可预约剧本。
4. 玩家浏览剧本和店铺，选择房间与时间段创建预约。
5. 店家确认预约，体验完成后标记订单完成。
6. 玩家对剧本和店铺进行评价，也可以收藏感兴趣的剧本或店铺。

## 项目结构

```text
047-backend/
├── src/main/java/com/xiaou/
│   ├── common/          # 通用响应、JWT 工具等
│   ├── config/          # Spring Security、MyBatis-Plus 等配置
│   ├── controller/      # 控制器
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── mapper/          # MyBatis Mapper
│   ├── service/         # 服务层
│   └── vo/              # 视图对象
├── src/main/resources/
│   ├── application.yml          # 默认 H2 配置
│   ├── application-mysql.yml    # MySQL profile 配置
│   ├── schema-h2.sql            # H2 表结构
│   └── data-h2.sql              # H2 演示数据
├── src/test/java/com/xiaou/
│   └── ScriptKillApplicationSmokeTest.java
└── sql/
    └── init.sql

047-frontend/
├── src/
│   ├── api/             # API 封装
│   ├── layouts/         # 四端布局
│   ├── router/          # 路由配置
│   ├── stores/          # Pinia 状态管理
│   └── views/           # 页面组件
└── vite.config.ts       # 3047 端口与 /api 代理
```

## 注意事项

1. 默认环境不需要本机安装 MySQL 或 Redis，直接使用 H2 内存库。
2. `mvn` 输出中的 `rdc` profile warning 来自外部 Maven 环境，不影响项目测试和启动。
3. 如需保留数据，请使用 MySQL profile；默认 H2 为内存库，服务停止后数据会重置。
4. `target/`、`dist/`、`node_modules/` 和 `.tmp/` 均为生成物或临时文件，不应提交。
