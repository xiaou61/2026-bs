# 052 网课在线学习观看系统后端

## 项目说明

本项目是 `052 网课在线学习观看系统` 的 Spring Boot 后端，提供用户注册登录、课程分类、课程浏览、章节视频、学习进度、收藏评论和管理端统计等接口。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.4
- JWT
- H2 默认演示数据库
- MySQL 生产兼容配置

## 默认启动

默认配置使用 H2 内存数据库，可直接启动，不需要提前安装 MySQL 或 Redis。

```bash
mvn spring-boot:run
```

默认访问地址：

- 后端接口：`http://localhost:8052`
- H2 控制台：`http://localhost:8052/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:online_course`
- H2 用户名：`sa`
- H2 密码：空

## 默认账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `123456` | 可访问 `/api/admin/**` |
| 教师 | `teacher` | `123456` | 演示课程教师 |
| 学生 | `student` | `123456` | 演示学习用户 |

## 前端端口

- 用户端目录：`../052-frontend/user`
- 用户端开发端口：`3052`
- 管理端目录：`../052-frontend/admin`
- 管理端开发端口：`3152`
- 两个前端的 Vite 代理目标均为：`http://localhost:8052`

## MySQL 模式

默认演示推荐使用 H2。如需切换到 MySQL：

1. 创建数据库 `online_course`。
2. 导入 `sql/init.sql`。
3. 按需修改 `src/main/resources/application-mysql.yml` 中的数据库账号密码。
4. 使用 MySQL profile 启动。

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 验证命令

```bash
mvn clean test
```

用户端：

```bash
cd ../052-frontend/user
npm install --no-audit --no-fund
npm run build
```

管理端：

```bash
cd ../052-frontend/admin
npm install --no-audit --no-fund
npm run build
```

## 本轮巡检修复摘要

- 默认端口调整为 `8052`，避免毕业设计合集内多个项目抢占 `8080`。
- 默认数据库改为 H2 自举，新增 `schema-h2.sql` 和 `data-h2.sql`。
- 新增 `application-mysql.yml`，保留 MySQL 部署方式。
- MySQL 驱动坐标更新为 `com.mysql:mysql-connector-j`。
- MyBatis-Plus 分页插件改为自动识别数据库方言。
- 管理端接口补充角色校验，普通用户访问 `/api/admin/**` 返回 `403`。
- 用户实体响应脱敏，注册、登录和个人信息接口不再返回密码字段。
- 学习进度保存、收藏、评论补充课程或视频存在性校验。
- 保存学习进度时如果没有学习记录，会自动创建学习记录。
- 前端用户端和管理端端口、代理地址已按项目编号隔离。
- MySQL 和 H2 演示数据中的不存在本地图片路径已清空，避免运行时资源断链。
- 新增后端冒烟测试覆盖游客、用户、学习、互动和管理端核心链路。
