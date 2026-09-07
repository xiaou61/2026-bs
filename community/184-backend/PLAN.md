# 停车场月租合同续费与异常占位管理系统 开发计划

## 后端
- 完成 `com.parkinglease` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、停车场档案、车位档案、月租车主、月租合同、续费提醒、续费缴费、车辆绑定、占位巡检、异常占位、违规处理、客服工单、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / PARKING / FINANCE / PATROL / TENANT / SERVICE 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
