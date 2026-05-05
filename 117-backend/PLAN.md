# 本地生活服务券核销与商户结算系统开发计划

## 技术路线
后端采用 Spring Boot 2.7.18、MyBatis-Plus 3.5.5、MySQL、Redis 和 JWT。前端采用 Vue3、Element Plus、Pinia、Axios、Vue Router 和 ECharts。

## 实施步骤
1. 初始化后端工程、配置 8117 端口、MySQL 数据库 local_voucher_117 和 Redis 20 号库。
2. 建立 JWT 登录、Redis Token 校验、全局异常处理和跨域拦截。
1. 完成 账号权限 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
2. 完成 商户档案 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 完成 门店网点 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
4. 完成 用户档案 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
5. 完成 券模板 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
6. 完成 营销活动 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
7. 完成 用户领券 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
8. 完成 核销记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
9. 完成 商户结算 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
10. 完成 结算明细 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
11. 完成 打款记录 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
12. 完成 申诉工单 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
13. 完成 活动统计 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
14. 完成 操作日志 的实体、Mapper、Service、Controller、前端列表表单和状态动作。
3. 编写数据库初始化脚本，包含 14 张业务表和演示数据。
4. 构建 Vue3 管理端，配置 3117 端口代理、登录页、布局页、数据看板和通用数据维护页。
5. 执行静态扫描，确认主题关键词、端口、数据库、账号密码、表数量、接口数量和页面数量完整。
