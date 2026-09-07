# 社区党建活动报名与积分激励平台 开发计划

## 后端
- 完成 `com.communityparty` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、党组织维护、党员档案、党建活动、活动报名、签到记录、志愿任务、积分记录、积分兑换、组织关系、榜单统计、通知公告、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / SECRETARY / ORGANIZER / VOLUNTEER / RESIDENT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
