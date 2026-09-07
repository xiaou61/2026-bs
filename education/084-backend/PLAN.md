# 084项目实施计划

## 问题陈述
需要构建教学资料管理系统，实现教学资料从上传、审核、发布到检索下载与收藏统计的全流程管理。

## 当前状态
- 根目录已具备001-083项目模板可复用
- 084项目目录为空
- 已完成PRD初版定义

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建pom.xml（MyBatis + PageHelper + Redis + JWT）
- 创建application.yml
- 创建启动类
- 创建Result、异常处理、JWT鉴权
- 创建MyBatis分页与Redis配置

2. 数据库脚本
- 创建sql/init.sql
- 完成12张表与测试数据

3. 实体与Mapper
- 创建12个Entity
- 创建Mapper接口与XML映射

4. Service层
- 鉴权与用户信息
- 资料分类管理
- 资料管理与审核流转
- 下载/收藏/学习清单业务
- 公告管理与统计看板

5. Controller层
- AuthController
- CategoryController
- MaterialController
- AuditController
- FavoriteController
- NoticeController
- StatisticsController

### 第二阶段: 前端开发
1. 基础工程
- package.json、vite.config.js、main.js、App.vue
- 路由与权限守卫
- Axios请求封装
- Pinia用户状态

2. 页面开发
- 登录页
- 布局页
- 仪表盘页
- 分类管理页
- 资料管理页
- 审核管理页
- 收藏清单页
- 公告管理页

3. 联调优化
- 字段映射与状态字典统一
- 表单校验与错误提示统一
- 列表分页与弹窗交互统一

## 交付结果
- 084-backend后端完整代码
- 084-frontend前端完整代码
- 根目录readme.md与readme_simple.md新增084项目介绍
