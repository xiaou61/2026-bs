# 农贸市场摊位巡检与食品追溯台账系统 开发计划

## 后端
- 完成 `com.markettrace` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、市场区域、摊位档案、商户档案、商品溯源、摊位巡检、问题整改、抽检记录、检测结果、进货台账、处置记录、风险预警、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / MARKET / INSPECTOR / VENDOR / SAMPLER / REGULATOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
