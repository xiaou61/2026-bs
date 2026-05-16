# 养老机构床位分配与护理记录管理系统 开发计划

## 后端
- 完成 `com.eldercare` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、护理区域、房间档案、床位档案、老人档案、家属档案、入住评估、床位分配、护理计划、护理记录、家属查询、异常上报、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / ADMISSION / NURSING / CAREGIVER / FAMILY / DIRECTOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
