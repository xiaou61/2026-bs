# 校园餐厅后厨留样与卫生巡检台账系统 开发计划

## 后端
- 完成 `com.canteenhygiene` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、餐厅档案、后厨区域、菜品档案、留样登记、留样存储、食材验收、消毒记录、卫生巡检、问题整改、风险提醒、厨余处置、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / CANTEEN / CHEF / INSPECTOR / WAREHOUSE / SCHOOL 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
