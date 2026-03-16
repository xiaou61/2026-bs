# 085项目实施计划

## 问题陈述
需要构建数学课程评价系统，实现从课程与评价指标配置、评价任务发布、学生评分到统计看板分析的完整闭环。

## 当前状态
- 根目录已具备001-084项目模板可复用
- 已创建085-backend与085-frontend工程目录
- 已完成085项目PRD与技术方案设计

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建/调整pom.xml（MyBatis + PageHelper + Redis + JWT）
- 创建application.yml
- 保留统一Result、异常处理、JWT鉴权拦截器
- 保持MyBatis分页与Redis配置

2. 数据库脚本
- 创建sql/init.sql
- 完成9张核心表与测试数据

3. 实体与Mapper
- 创建课程分类、课程、指标、任务、评价主表、评价明细、公告、用户、日志实体
- 创建Mapper接口与XML映射

4. Service层
- 鉴权与用户信息服务
- 课程分类/课程/指标/任务服务
- 学生评价提交与防重复校验
- 公告管理与统计看板服务

5. Controller层
- AuthController
- CategoryController
- CourseController
- IndicatorController
- TaskController
- EvaluationController
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
- 课程分类页
- 课程管理页
- 指标管理页
- 任务管理页
- 课程评价页
- 公告管理页

3. 联调优化
- 接口字段与状态字典统一
- 评分提交流程与提示信息优化
- 列表分页、弹窗交互、图表展示统一

### 第三阶段: 验证与文档
1. 编译构建
- 后端执行mvn -DskipTests package
- 前端执行npm run build

2. 文档更新
- 更新085-backend/PRD.md
- 更新根目录readme.md与readme_simple.md中的085条目

## 交付结果
- 085-backend后端完整代码
- 085-frontend前端完整代码
- 数学课程评价系统数据库脚本与测试数据
- 根目录readme.md与readme_simple.md新增/更新085项目介绍
