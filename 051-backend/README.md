# 051 基于微信小程序的网络安全知识科普 APP 后端

## 项目简介

本项目是一个面向普通用户的网络安全知识科普系统，包含知识分类、文章学习、每日答题、错题本、排行榜、安全资讯、互动问答和后台管理能力。后端采用 Spring Boot 2.7、MyBatis-Plus、JWT，前端包含 Vue 3 管理端和原生微信小程序。

本轮巡检后，项目默认使用 H2 内存数据库自举，启动后会自动建表并写入演示数据，无需提前安装 MySQL 或 Redis。需要连接 MySQL 时，可切换到 `mysql` profile 并导入 `sql/init.sql`。

## 运行环境

- JDK：`11+`，当前已在 JDK 17 下验证通过
- Maven：`3.8+`
- 默认端口：`8051`
- 默认数据库：`H2` 内存库
- H2 控制台：`http://localhost:8051/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:security_knowledge`

## 快速启动

```bash
cd 051-backend
mvn spring-boot:run
```

启动后可访问：

```text
GET  http://localhost:8051/api/category/list
GET  http://localhost:8051/api/article/list
POST http://localhost:8051/api/admin/login
POST http://localhost:8051/api/user/login
```

## 默认账号与登录

管理端默认账号：

```text
用户名：admin
密码：123456
```

小程序端默认启用模拟微信登录，不需要真实微信 `appid` / `secret`。调用示例：

```json
{
  "code": "demo-code",
  "nickname": "演示用户",
  "avatar": ""
}
```

后端会将 `code` 映射为本地 openid：`local-demo-code`，便于答辩和本地演示。

## MySQL 模式

如需使用 MySQL：

1. 创建并初始化数据库：

```bash
mysql -uroot -p123456 < sql/init.sql
```

2. 使用 `mysql` profile 启动：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

3. 如果需要真实微信登录，请在 `src/main/resources/application-mysql.yml` 中配置正式 `wechat.appid`、`wechat.secret`，并将 `wechat.mock-enabled` 改为 `false`。

## 验证命令

后端自动化测试：

```bash
cd 051-backend
mvn clean test
```

管理端构建：

```bash
cd 051-frontend/admin
npm install --no-audit --no-fund
npm run build
```

小程序静态核查：

```powershell
$app = Get-Content -Raw 051-frontend\miniprogram\app.json | ConvertFrom-Json
$missing = @()
foreach ($page in $app.pages) {
  foreach ($ext in @('.js','.json','.wxml','.wxss')) {
    $path = Join-Path '051-frontend\miniprogram' ($page + $ext)
    if (-not (Test-Path $path)) { $missing += $path }
  }
}
"PageCount=$($app.pages.Count)"
"MissingFiles=$($missing.Count)"
```

## 本轮巡检修复摘要

1. 默认端口从 `8080` 调整为 `8051`。
2. 默认环境改为 H2 自举，新增 `application-mysql.yml` 保留 MySQL 部署模式。
3. 新增 H2 建表与演示数据脚本，默认无需外部数据库。
4. 将 Redis 点赞和每日题目缓存改为本地缓存兜底，默认无需启动 Redis。
5. 微信登录支持本地模拟，避免缺少真实微信配置时无法演示。
6. JWT 拦截器改为内部判断公开接口，并限制普通用户 token 访问管理端接口。
7. 修复小程序 JSON 请求体与后端 `@RequestParam` 不匹配导致的学习记录、收藏失败。
8. 将 MyBatis-Plus 分页方言改为自动识别，兼容 H2 和 MySQL。
9. 管理端代理和小程序默认请求地址统一调整到 `8051`。
10. 补齐小程序 `pages/qa` 页面，移除缺失本地静态资源引用。
11. 新增后端冒烟测试，覆盖登录、权限、答题、学习记录、收藏和问答核心链路。

## 备注

`mvn` 输出中的 `rdc` profile warning 来自当前机器 Maven 环境，不影响本项目测试、启动和接口验证。
