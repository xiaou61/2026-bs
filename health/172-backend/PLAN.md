# 口腔门诊治疗预约与耗材计费管理系统 开发计划

## 后端
- 完成 `com.dentalclinic` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、诊室档案、医生档案、患者档案、治疗项目、预约诊疗、签到分诊、治疗记录、耗材目录、耗材库存、耗材使用、费用结算、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / RECEPTION / DENTIST / NURSE / CASHIER / PATIENT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
