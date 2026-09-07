# 035 项目快速上手（精简版）

1. 默认环境：JDK17、Maven3.8+，后端开箱即用使用 H2 内存库。
2. 启动后端：`mvn spring-boot:run`
3. 前端（可选）：`cd ../035-frontend && npm install && npm run dev`，浏览器打开 `/login`。
4. 默认账号：
   - 管理员：`admin / 123456`
   - 农户：`farmer_demo / 123456`
   - 机手：`driver_demo / 123456`
5. 如需切回 MySQL：先执行 `mysql -u root -p < sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
6. 获取 token 后请求需带 `Authorization: Bearer <token>`。
7. 常用接口：
   - 认证：`/api/auth/login`，`/api/auth/register`
   - 地块：`/api/plots`
   - 设备：`/api/machines`
   - 预约：`/api/bookings` + `/{id}/assign|start|finish|cancel`
   - 健康：`/api/health`
8. 文档：运行后访问 `http://localhost:8080/swagger-ui/index.html`。
