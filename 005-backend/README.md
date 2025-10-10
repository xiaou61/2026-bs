# 在线问卷调查系统

基于 Spring Boot + Vue 3 的前后端一体化问卷调查系统

## 技术栈

### 后端
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT 认证
- Lombok

### 前端
- Vue 3 (CDN)
- Axios
- ECharts 5
- Thymeleaf

## 功能特性

### 用户功能
- 用户注册/登录
- 个人信息管理
- 创建/编辑/删除问卷
- 发布问卷
- 查看问卷统计

### 问卷功能
- 支持多种题型：单选、多选、填空、评分、下拉选择
- 问卷设计与编辑
- 问卷发布与回收
- 答卷收集（无需登录）

### 统计分析
- 自动统计分析
- ECharts 可视化图表
- 饼图展示选择题结果
- 文本答案列表展示

## 快速开始

### 1. 数据库初始化
```sql
-- 执行 src/main/resources/sql/init.sql
```

### 2. 配置数据库
修改 `src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/survey_db
    username: root
    password: root
```

### 3. 启动项目
```bash
mvn spring-boot:run
```

### 4. 访问系统
- 首页：http://localhost:8085/
- 登录：http://localhost:8085/login
- 注册：http://localhost:8085/register

## 测试账号
详见 ACCOUNTS.md

## 项目结构
```
005-backend/
├── src/main/
│   ├── java/com/xiaou/
│   │   ├── common/          # 通用类（Result）
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器
│   │   ├── entity/          # 实体类
│   │   ├── exception/       # 异常处理
│   │   ├── interceptor/     # 拦截器
│   │   ├── mapper/          # Mapper接口
│   │   ├── service/         # 服务层
│   │   └── utils/           # 工具类
│   └── resources/
│       ├── templates/       # 前端页面
│       ├── sql/            # SQL脚本
│       └── application.yml # 配置文件
└── ACCOUNTS.md             # 测试账号
```

## 核心接口

### 用户模块
- POST /api/user/register - 用户注册
- POST /api/user/login - 用户登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/update - 更新用户信息

### 问卷模块
- POST /api/survey/create - 创建问卷
- GET /api/survey/list - 获取我的问卷
- PUT /api/survey/update - 更新问卷
- DELETE /api/survey/delete/{id} - 删除问卷
- PUT /api/survey/publish/{id} - 发布问卷
- GET /api/survey/detail/{id} - 获取问卷详情

### 问题模块
- POST /api/question/add - 添加问题
- GET /api/question/list/{surveyId} - 获取问卷问题
- DELETE /api/question/delete/{id} - 删除问题

### 答卷模块
- POST /api/answer/submit - 提交答卷
- GET /api/answer/stat/{surveyId} - 获取统计数据

## 使用流程

1. 注册/登录账号
2. 创建问卷（设置标题和描述）
3. 编辑问卷（添加各类题目）
4. 发布问卷
5. 分享问卷链接给填答者
6. 查看统计分析

## 注意事项
- 填写问卷无需登录
- 只有创建者可以编辑/删除自己的问卷
- 问卷发布后仍可继续编辑
- 统计数据实时更新

