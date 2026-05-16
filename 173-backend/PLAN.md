# 高校毕业去向填报与就业帮扶跟踪系统 开发计划

## 后端
- 完成 `com.grademployment` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、学院专业、毕业生档案、用人单位、招聘岗位、去向填报、协议归档、材料审核、帮扶记录、岗位推荐、跟踪回访、就业统计、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / CAREER / COLLEGE / COUNSELOR / STUDENT / EMPLOYER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
