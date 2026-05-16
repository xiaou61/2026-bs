# 医院手术室器械包追踪与灭菌放行系统 开发计划

## 后端
- 完成 `com.orsterile` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、手术室档案、器械包档案、器械明细、器械包追踪、灭菌批次、灭菌记录、放行审核、手术使用、回收清点、缺损上报、异常召回、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / ORNURSE / CSSD / STERILE / QA / SURGEON 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
