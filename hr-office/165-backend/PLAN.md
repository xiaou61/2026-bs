# 企业访客预约登记与门禁联动管理系统 开发计划

## 后端
- 完成 `com.visitoraccess` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、楼宇区域、被访员工、访客档案、访客预约、访问审批、二维码通行、门禁设备、门禁联动、入园登记、轨迹留痕、异常告警、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / RECEPTION / EMPLOYEE / SECURITY / VISITOR / MANAGER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
