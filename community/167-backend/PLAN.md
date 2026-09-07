# 社区垃圾分类督导与积分兑换平台 开发计划

## 后端
- 完成 `com.wastesorting` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、社区区域、楼栋档案、居民档案、分类规则、分类打卡、督导记录、整改任务、积分记录、兑换商品、积分兑换、楼栋排名、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / COMMUNITY / SUPERVISOR / RESIDENT / VOLUNTEER / EXCHANGE 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
