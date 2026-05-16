# 校园宿舍能耗监测与节能排名系统 开发计划

## 后端
- 完成 `com.dormenergy` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、宿舍楼栋、宿舍房间、能耗表计、用电读数、能耗账单、预警策略、异常预警、节能任务、节能排行、巡查记录、通知公告、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / LOGISTICS / COUNSELOR / ENERGY / STUDENT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
