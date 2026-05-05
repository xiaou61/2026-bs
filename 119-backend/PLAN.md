# 设备备件寿命预测与维保决策系统开发计划

## 技术路线
后端采用 Spring Boot 2.7.18、MyBatis-Plus 3.5.5、MySQL、Redis 和 JWT。前端采用 Vue3、Element Plus、Pinia、Axios、Vue Router 和 ECharts。

## 实施步骤
1. 初始化后端工程、配置 8119 端口、MySQL 数据库 spare_life_119 和 Redis 22 号库。
2. 建立 JWT 登录、Redis Token 校验、全局异常处理和跨域拦截。
1. 完成 账号权限 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
2. 完成 设备资产 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 完成 备件目录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
4. 完成 备件库存 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
5. 完成 入库记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
6. 完成 出库领用 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
7. 完成 使用记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
8. 完成 运行指标 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
9. 完成 故障记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
10. 完成 寿命预测 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
11. 完成 维保计划 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
12. 完成 采购申请 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
13. 完成 风险预警 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
14. 完成 操作日志 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 编写数据库初始化脚本，包含 14 张业务表和演示数据。
4. 构建 Vue3 管理端，配置 3119 端口代理、登录页、布局页、数据看板和通用数据维护页。
5. 执行静态扫描，确认主题关键词、端口、数据库、账号密码、表数量、接口数量和页面数量完整。
