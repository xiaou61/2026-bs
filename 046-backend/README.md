# 垃圾回收服务系统

基于 Spring Boot 3.2、MyBatis-Plus 3.5.5、Vue 3、TypeScript 和 Element Plus 的垃圾回收服务平台，覆盖居民、回收员、管理员三类角色，并提供积分商城兑换能力。

## 技术栈

### 后端

- Spring Boot 3.2.1
- MyBatis-Plus 3.5.5
- Spring Security + JWT
- H2 默认演示环境
- MySQL 8.0 生产/课程设计数据库环境
- Knife4j / Swagger 接口文档

### 前端

- Vue 3 + TypeScript
- Vite
- Element Plus
- Pinia
- Vue Router
- Axios

## 功能模块

### 居民端

- 用户注册/登录
- 预约垃圾回收
- 查看订单状态
- 积分管理
- 积分商城兑换
- 环保知识学习
- 系统公告查看

### 回收员端

- 查看待处理订单
- 接单
- 完成回收
- 录入回收明细、重量、金额和积分

### 管理员端

- 数据统计仪表盘
- 订单管理
- 用户管理
- 小区管理
- 垃圾分类管理
- 积分商品管理
- 兑换记录管理
- 环保知识发布
- 系统公告发布

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8.0（仅 MySQL profile 需要）

### 默认 H2 模式启动后端

默认配置已经内置 H2 内存库、建表脚本和演示数据，不需要提前安装 MySQL 或 Redis。

```bash
cd 046-backend
mvn spring-boot:run
```

后端默认地址：

- API 地址：`http://localhost:8046/api`
- Knife4j 文档：`http://localhost:8046/doc.html`
- Swagger UI：`http://localhost:8046/swagger-ui.html`
- H2 控制台：`http://localhost:8046/h2-console`

H2 控制台连接信息：

- JDBC URL：`jdbc:h2:mem:garbage_recycle`
- 用户名：`sa`
- 密码：空

### MySQL 模式启动后端

如果需要连接 MySQL，请先导入初始化脚本，再使用 `mysql` profile 启动。

```bash
cd 046-backend
mysql -u root -p < sql/init.sql
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 配置位于：

- `src/main/resources/application-mysql.yml`

默认连接信息：

- 数据库：`garbage_recycle`
- 用户名：`root`
- 密码：`123456`

### 启动前端

```bash
cd 046-frontend
npm install
npm run dev
```

前端默认地址：

- 页面地址：`http://localhost:3046`
- Vite 代理：`/api -> http://localhost:8046`

生产构建：

```bash
npm run build
```

## 默认测试账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `admin123` | 系统管理员 |
| 回收员 | `collector1` | `admin123` | 负责阳光花园小区订单 |
| 居民 | `user1` | `admin123` | 阳光花园小区居民，初始积分 500 |

## 默认演示数据

H2 默认数据包含：

- 5 个系统用户
- 3 个小区
- 10 个垃圾分类
- 5 个积分商品
- 3 条环保知识
- 2 条系统公告

## 验证命令

后端测试：

```bash
cd 046-backend
mvn test
```

前端构建：

```bash
cd 046-frontend
npm run build
```

## 项目结构

```text
046-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/xiaou/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── utils/
│   └── vo/
├── src/main/resources/
│   ├── application.yml
│   ├── application-mysql.yml
│   ├── schema-h2.sql
│   └── data-h2.sql
└── src/test/java/com/xiaou/
    └── GarbageRecycleApplicationSmokeTest.java

046-frontend/
├── src/
│   ├── api/
│   ├── layouts/
│   ├── router/
│   ├── stores/
│   ├── utils/
│   └── views/
└── vite.config.ts
```

## 业务流程

1. 居民登录后创建垃圾回收预约订单。
2. 回收员查看同小区待接单订单并接单。
3. 回收员上门回收后录入各分类重量。
4. 系统根据垃圾分类单价和积分比例计算金额与积分。
5. 居民获得环保积分，可在积分商城兑换商品。
6. 管理员查看兑换记录并完成发放。

## 备注

1. 默认 H2 模式用于毕业设计巡检、演示和快速答辩，不会持久化数据。
2. MySQL 模式保留给需要真实数据库部署的场景。
3. 前端开发端口为 `3046`，后端开发端口为 `8046`，避免与其他项目默认端口冲突。
4. 当前前端构建可能出现 Vite 主包体积超过 500KB 的告警，不影响构建产物生成。
