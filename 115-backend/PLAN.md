# 跨境电商清关订单与汇率结算平台开发计划

## 技术路线
后端采用 Spring Boot 2.7.18、MyBatis-Plus 3.5.5、MySQL、Redis 和 JWT。前端采用 Vue3、Element Plus、Pinia、Axios、Vue Router 和 ECharts。

## 实施步骤
1. 初始化后端工程、配置 8115 端口、MySQL 数据库 cross_border_115 和 Redis 18 号库。
2. 建立 JWT 登录、Redis Token 校验、全局异常处理和跨域拦截。
1. 完成 账号权限 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
2. 完成 商家店铺 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 完成 客户档案 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
4. 完成 商品SKU 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
5. 完成 跨境订单 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
6. 完成 清关申报 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
7. 完成 清关单证 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
8. 完成 税费记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
9. 完成 汇率牌价 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
10. 完成 结算账单 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
11. 完成 支付记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
12. 完成 物流跟踪 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
13. 完成 订单对账 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
14. 完成 操作日志 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 编写数据库初始化脚本，包含 14 张业务表和演示数据。
4. 构建 Vue3 管理端，配置 3115 端口代理、登录页、布局页、数据看板和通用数据维护页。
5. 执行静态扫描，确认主题关键词、端口、数据库、账号密码、表数量、接口数量和页面数量完整。

## 验收标准
1. 后端具备 14 个核心业务模块、Auth 接口和 Statistics 接口。
2. 前端具备登录、布局、看板和 14 个业务页面。
3. SQL 脚本可创建 cross_border_115 数据库并写入演示数据。
4. 配置使用 MySQL root/1234、Redis、MyBatis-Plus 和跨境电商主题。
