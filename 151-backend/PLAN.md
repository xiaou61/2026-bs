# 文旅场馆讲解预约与票务核销管理平台 开发计划

## 后端
- 完成 `com.culturevenue` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、场馆档案、票种产品、票务预约、讲解员档案、讲解排期、讲解预约、扫码核销、客流统计、游客评价、文旅活动、场馆公告、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / MANAGER / GUIDE / CHECKER / VISITOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留旧泛化模板命名
