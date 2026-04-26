# 032 社区老年人健康管理系统检查报告

## 1. 检查结论

- 项目编号：`032`
- 项目名称：`社区老年人健康管理系统`
- 检查日期：`2026-04-21`
- 当前状态：`已完成`
- 综合结论：`已修复默认 MySQL 依赖、JWT 密钥与解析错误、Spring Data JDBC 列映射问题和默认账号缺失问题；后端测试通过，后端服务可直接启动，前端依赖安装、构建和开发服务启动均已验证通过。`

## 2. 项目形态

- 后端目录：`032-backend`
- 前端目录：`032-frontend`
- 后端技术栈：`Spring Boot 3.2 + Spring Data JDBC + Spring Security + JWT`
- 前端技术栈：`Vue 3 + Element Plus + Vite`

## 3. 本轮修复

### 3.1 默认环境与启动问题

1. 默认数据源从本地 `MySQL` 改为 `H2` 内存库自举
2. 补充 `application-mysql.yml`，保留显式 MySQL 运行方式
3. 关闭默认 `Redis repositories` 依赖，避免无意义的外部阻塞
4. 引入 `H2` 运行时依赖
5. 修正默认建表 SQL 顺序和索引定义，保证 H2 可执行

### 3.2 JWT 与安全链路

1. 默认 JWT 密钥改为满足长度要求的安全字符串
2. 修复 `TokenService.parse()` 将 HMAC 密钥错误转成 `PublicKey` 的问题
3. 登录后的 `me` 鉴权链路已恢复可用
4. 注册时补充用户名重复校验

### 3.3 Spring Data JDBC 映射

1. 为领域对象补齐显式列映射，修复 H2 下字段大小写/下划线不匹配问题
2. 管理端用户列表返回已隐藏密码字段，避免敏感信息泄露

### 3.4 默认数据与自动化测试

1. 启动时自动初始化默认账号：
   - `admin / admin123`
   - `doctor1 / doctor123`
   - `elder1 / elder123`
2. 新增后端自动化测试，覆盖：
   - 管理员登录
   - JWT claims 解析
   - 医生注册
   - 老人建档
   - 健康测量
   - 健康评估
   - 预约创建与状态流转
   - 随访创建与状态流转
   - 统计总览

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 登录、注册与角色区分
2. 老人信息建档与列表查询
3. 血压、身高、体重等健康测量记录
4. BMI 和血压风险评估
5. 预约管理
6. 随访管理
7. 通知列表与已读处理
8. 管理端用户状态/角色调整
9. 统计总览

### 4.2 仍有差距

1. 当前通知能力仍为站内提醒，缺少短信/电话等真实触达能力
2. 角色和业务权限模型仍相对简化
3. 前端构建产物体积较大，仍有 `chunk size` 告警

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`032-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 2, Failures: 0, Errors: 0`

### 5.2 后端启动与接口抽测

- 执行命令：`032-backend/mvn spring-boot:run`
- 结果：`通过（端口 8032）`
- 抽测链路：
  1. `POST /api/auth/login`
  2. `GET /api/auth/me`
  3. `GET /api/admin/users`
  4. `POST /api/elders`
  5. `POST /api/measurements`
  6. `GET /api/assessment/elder/{id}`
  7. `POST /api/appointments`
  8. `POST /api/followups`
  9. `GET /api/stats/overview`
- 关键业务结果：
  - 管理员登录成功
  - `doctor1` 默认账号可用
  - 老人建档成功
  - BMI / 血压风险评估成功返回
  - 医生预约与随访列表成功返回

### 5.3 前端验证

- 执行命令：
  - `032-frontend/npm install`
  - `032-frontend/npm run build`
  - `032-frontend/npm run dev -- --host 127.0.0.1 --port 5132`
- 结果：`通过`
- 关键结果：
  1. 构建产物生成成功
  2. 前端开发服务成功启动在 `5132`
  3. `GET http://127.0.0.1:5132/login` 返回 `200`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`032-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - `admin / admin123`
  - `doctor1 / doctor123`
  - `elder1 / elder123`

## 7. 备注

1. `032-frontend/src/api.js` 当前默认指向 `http://localhost:8032`
2. `npm run build` 仍提示前端主包较大，但不影响构建和启动结论
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响项目测试和启动结论
