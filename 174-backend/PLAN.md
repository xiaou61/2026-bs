# 社区慢病档案随访与服药依从性管理系统 开发计划

## 后端
- 完成 `com.chroniccare` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、社区站点、患者档案、医护团队、慢病档案、随访计划、随访记录、用药方案、服药打卡、健康指标、风险分层、提醒通知、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / CENTER / DOCTOR / NURSE / PHARMACIST / RESIDENT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
