# 医疗废弃物收运联单与闭环监管系统 开发计划

## 后端
- 完成 `com.medicalwaste` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、医废来源、废物类别、包装赋码、收集预约、称重记录、暂存交接、转运联单、运输轨迹、处置确认、异常追溯、监管抽查、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / HOSPITAL / COLLECTOR / TRANSPORTER / DISPOSAL / REGULATOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
