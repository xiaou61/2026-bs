# 景区失物招领与游客寻回协同平台 开发计划

## 后端
- 完成 `com.lostfound` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、景区区域、失物登记、拾物上报、游客认领、身份核验、位置追踪、暂存保管、通知协同、归还交接、寻回任务、回访评价、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / SERVICE / SECURITY / SCENIC / BROADCAST / VISITOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
