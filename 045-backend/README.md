# 养老院管理系统

基于 Spring Boot 3.2、MyBatis-Plus 3.5.5、Vue 3、TypeScript 和 Element Plus 的养老院管理系统，覆盖管理员、护工、家属三类角色。

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

### 管理员

- 数据统计仪表盘
- 老人入住/退住管理
- 楼栋、房间、床位管理
- 员工管理
- 探访审批
- 费用管理
- 公告发布

### 护工

- 老人列表查看
- 健康记录录入
- 护理计划管理
- 护理记录管理

### 家属

- 老人信息查看
- 健康记录查看
- 费用账单查询与缴费
- 探访预约申请
- 通知公告查看

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8.0（仅 MySQL profile 需要）

### 默认 H2 模式启动后端

默认配置已经内置 H2 内存库、建表脚本和演示数据，不需要提前安装 MySQL 或 Redis。

```bash
cd 045-backend
mvn spring-boot:run
```

后端默认地址：

- API 地址：`http://localhost:8045/api`
- Knife4j 文档：`http://localhost:8045/doc.html`
- Swagger UI：`http://localhost:8045/swagger-ui.html`
- H2 控制台：`http://localhost:8045/h2-console`

H2 控制台连接信息：

- JDBC URL：`jdbc:h2:mem:nursing_home`
- 用户名：`sa`
- 密码：空

### MySQL 模式启动后端

如果需要连接 MySQL，请先导入初始化脚本，再使用 `mysql` profile 启动。

```bash
cd 045-backend
mysql -u root -p < sql/init.sql
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 配置位于：

- `src/main/resources/application-mysql.yml`

默认连接信息：

- 数据库：`nursing_home`
- 用户名：`root`
- 密码：`123456`

### 启动前端

```bash
cd 045-frontend
npm install
npm run dev
```

前端默认地址：

- 页面地址：`http://localhost:3045`
- Vite 代理：`/api -> http://localhost:8045`

生产构建：

```bash
npm run build
```

## 默认测试账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `admin123` | 系统管理员 |
| 护工 | `nurse1` | `admin123` | 可录入健康记录、护理记录 |
| 家属 | `family1` | `admin123` | 绑定演示老人 `张大爷` |

## 默认演示数据

H2 默认数据包含：

- 3 个系统用户
- 2 栋楼
- 3 个房间
- 4 个床位
- 2 位老人
- 1 条健康记录
- 1 条护理计划
- 3 个费用项目
- 1 条账单
- 2 条公告

## 验证命令

后端测试：

```bash
cd 045-backend
mvn test
```

前端构建：

```bash
cd 045-frontend
npm run build
```

## 项目结构

```text
045-backend/
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
    └── NursingHomeApplicationSmokeTest.java

045-frontend/
├── src/
│   ├── api/
│   ├── layouts/
│   ├── router/
│   ├── stores/
│   ├── utils/
│   └── views/
└── vite.config.ts
```

## 备注

1. 默认 H2 模式用于毕业设计巡检、演示和快速答辩，不会持久化数据。
2. MySQL 模式保留给需要真实数据库部署的场景。
3. 前端开发端口为 `3045`，后端开发端口为 `8045`，避免与其他项目默认端口冲突。
4. 当前前端构建可能出现 Vite 主包体积超过 500KB 的告警，不影响构建产物生成。
