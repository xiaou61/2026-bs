# 博物馆文物数字化管理平台

## 项目简介

本系统是一个基于 Spring Boot + Vue 3 的博物馆文物数字化管理平台，面向管理员、讲解员、研究员、游客四类角色，支持文物数字档案、展览活动、参观预约、讲解预约、研究成果审核、收藏和公告等功能。

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
- Pinia
- Vue Router
- Axios

## 功能模块

### 管理员端

- 用户管理：管理游客、讲解员、研究员和管理员
- 文物管理：文物信息录入、编辑、上下架
- 分类管理：文物分类维护
- 朝代管理：朝代信息维护
- 展厅管理：展厅信息管理
- 展览管理：展览活动发布与管理
- 研究管理：研究成果审核
- 预约管理：参观预约审核确认
- 公告管理：系统公告和展览通知发布

### 讲解员端

- 查看自己的讲解预约订单
- 确认讲解预约
- 完成讲解服务

### 研究员端

- 提交研究成果
- 查看个人研究成果
- 编辑研究成果

### 游客端

- 文物浏览、详情查看、图片查看、点赞
- 展览浏览与参观预约
- 讲解服务预约
- 已发布研究成果浏览
- 我的参观预约和讲解预约
- 文物收藏管理
- 公告中心

## 数据库设计

系统包含 13 张核心表：

- `sys_user`：用户表
- `relic_category`：文物分类表
- `dynasty`：朝代表
- `exhibition_hall`：展厅表
- `relic`：文物表
- `relic_image`：文物图片表
- `research`：研究成果表
- `exhibition`：展览活动表
- `reservation`：参观预约表
- `guide_booking`：讲解预约表
- `favorite`：收藏表
- `comment`：评论表
- `notice`：系统公告表

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Node.js 18+
- npm 9+

### 后端默认 H2 启动

```bash
cd 048-backend
mvn spring-boot:run
```

默认后端地址：

- 接口地址：`http://localhost:8048`
- 接口文档：`http://localhost:8048/doc.html`
- H2 控制台：`http://localhost:8048/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:museum_relic`
- H2 用户名：`sa`
- H2 密码：留空

默认 H2 模式会自动执行：

- `src/main/resources/schema-h2.sql`
- `src/main/resources/data-h2.sql`

### 后端 MySQL 启动

如需使用 MySQL：

1. 创建数据库 `museum_relic`
2. 导入 `048-backend/sql/init.sql`
3. 根据本机环境修改 `src/main/resources/application-mysql.yml`
4. 启动：

```bash
cd 048-backend
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

### 前端启动

```bash
cd 048-frontend
npm install
npm run dev
```

默认前端地址：

- 前端页面：`http://localhost:3048`
- Vite 代理：`/api -> http://localhost:8048`

### 前端构建

```bash
cd 048-frontend
npm run build
```

## 测试账号

| 角色 | 用户名 | 密码 | 角色值 |
| --- | --- | --- | ---: |
| 管理员 | `admin` | `123456` | 3 |
| 讲解员 | `guide1` | `123456` | 1 |
| 研究员 | `researcher1` | `123456` | 2 |
| 游客 | `user1` | `123456` | 0 |

## 验证命令

```bash
cd 048-backend
mvn test
```

当前后端冒烟测试覆盖：

- 管理员、游客、讲解员、研究员四角色登录
- 公告列表和管理员统计
- 分类、朝代、展厅、讲解员基础数据
- 文物列表、详情、图片、点赞
- 展览列表和研究成果列表
- 游客创建参观预约
- 管理员确认和完成参观预约
- 游客收藏和取消收藏
- 游客创建讲解预约
- 讲解员确认和完成讲解预约
- 研究员提交研究成果
- 管理员审核研究成果
- 审核后游客侧可检索到研究成果

```bash
cd 048-frontend
npm run build
```

当前前端构建可通过，仍存在 Vite 主包体积超过 500KB 的提示，不影响默认启动与演示。

## 业务流程

1. 管理员维护文物分类、朝代、展厅、文物和展览活动。
2. 游客浏览文物和展览，创建参观预约。
3. 管理员确认参观预约，并在参观后标记完成。
4. 游客预约讲解服务，讲解员确认并完成讲解预约。
5. 研究员提交文物研究成果，管理员审核发布。
6. 游客浏览已发布研究成果，并可收藏感兴趣的文物。

## 项目结构

```text
048-backend/
├── src/main/java/com/xiaou/
│   ├── common/          # 通用响应和异常处理
│   ├── config/          # Spring Security、MyBatis-Plus 等配置
│   ├── controller/      # 控制器
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── mapper/          # MyBatis Mapper
│   ├── service/         # 服务层
│   ├── utils/           # JWT 工具
│   └── vo/              # 视图对象
├── src/main/resources/
│   ├── application.yml          # 默认 H2 配置
│   ├── application-mysql.yml    # MySQL profile 配置
│   ├── schema-h2.sql            # H2 表结构
│   └── data-h2.sql              # H2 演示数据
├── src/test/java/com/xiaou/
│   └── MuseumRelicApplicationSmokeTest.java
└── sql/
    └── init.sql

048-frontend/
├── src/
│   ├── api/             # API 封装
│   ├── layouts/         # 四端布局
│   ├── router/          # 路由配置
│   ├── stores/          # Pinia 状态管理
│   └── views/           # 页面组件
└── vite.config.ts       # 3048 端口与 /api 代理
```

## 注意事项

1. 默认环境不需要本机安装 MySQL 或 Redis，直接使用 H2 内存库。
2. `mvn` 输出中的 `rdc` profile warning 来自外部 Maven 环境，不影响项目测试和启动。
3. 如需保留数据，请使用 MySQL profile；默认 H2 为内存库，服务停止后数据会重置。
4. `target/`、`dist/`、`node_modules/` 和 `.tmp/` 均为生成物或临时文件，不应提交。
