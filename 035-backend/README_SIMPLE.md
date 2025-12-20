# 035 项目快速上手（精简版）

1. 环境：JDK17、MySQL8、Redis6、Maven3.8+
2. 初始化库：`mysql -u root -p < sql/init.sql`
3. 配置：编辑 `src/main/resources/application.yml`（DB/Redis/JWT secret）。
4. 启动后端：`mvn spring-boot:run`
5. 前端（可选）：`cd ../035-frontend && npm install && npm run dev`，浏览器打开 `/login`。
6. 登录：默认管理员 `admin / 123456`，获取 token 后请求需带 `Authorization: Bearer <token>`。
7. 常用接口：
   - 认证：`/api/auth/login`，`/api/auth/register`
   - 地块：`/api/plots`
   - 设备：`/api/machines`
   - 预约：`/api/bookings` + `/{id}/assign|start|finish|cancel`
   - 健康：`/api/health`
8. 文档：运行后访问 `http://localhost:8080/swagger-ui/index.html`。
