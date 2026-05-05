# 数字孪生园区设备巡检管理系统开发计划

## 技术路线
后端采用 Spring Boot 2.7.18、MyBatis 2.3.1 注解 SQL、PageHelper 1.4.7、MySQL、Redis 和 JWT。前端采用 Vue3、Element Plus、Pinia、Axios、Vue Router 和 ECharts。

## 实施步骤
1. 初始化后端工程、配置 8120 端口、MySQL 数据库 digital_twin_park_120 和 Redis 23 号库。
2. 建立 JWT 登录、Redis Token 校验、全局异常处理和跨域拦截。
1. 完成 账号权限 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
2. 完成 园区楼宇 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 完成 孪生设备 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
4. 完成 巡检路线 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
5. 完成 巡检点位 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
6. 完成 巡检任务 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
7. 完成 巡检记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
8. 完成 缺陷报告 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
9. 完成 维修工单 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
10. 完成 传感数据 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
11. 完成 孪生模型 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
12. 完成 能耗监测 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
13. 完成 保养计划 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
14. 完成 操作日志 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 编写数据库初始化脚本，包含 14 张业务表和演示数据。
4. 构建 Vue3 管理端，配置 3120 端口代理、登录页、布局页、数据看板和通用数据维护页。
5. 执行静态扫描，确认主题关键词、端口、数据库、账号密码、表数量、接口数量和页面数量完整。
