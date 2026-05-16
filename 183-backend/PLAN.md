# 实验动物饲养排班与伦理审批管理系统 开发计划

## 后端
- 完成 `com.labanimal` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、动物房间、实验动物档案、饲养排班、饲养记录、伦理申请、伦理审批、实验登记、健康巡检、异常告警、兽医处置、耗材使用、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / LAB / BREEDER / RESEARCHER / ETHICS / VET 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
