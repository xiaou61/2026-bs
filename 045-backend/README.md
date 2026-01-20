# 养老院管理系统

基于SpringBoot + Vue3的养老院管理系统

## 技术栈

### 后端
- Spring Boot 3.2.1
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis
- JWT认证
- Knife4j接口文档

### 前端
- Vue 3 + TypeScript
- Element Plus
- Pinia
- Vue Router
- Axios

## 功能模块

### 管理员
- 数据统计仪表盘
- 老人入住/退住管理
- 床位管理（楼栋、房间、床位）
- 员工管理
- 探访审批
- 费用管理
- 公告发布

### 护工
- 老人列表查看
- 健康记录录入
- 护理计划管理
- 护理记录管理

### 家属
- 老人信息查看
- 健康记录查看
- 费用账单查询与缴费
- 探访预约申请
- 通知公告查看

## 快速开始

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0
- Redis

### 后端启动
```bash
# 1. 创建数据库
mysql -u root -p < sql/init.sql

# 2. 修改配置
# 编辑 src/main/resources/application.yml 配置数据库连接

# 3. 启动后端
mvn spring-boot:run
```

### 前端启动
```bash
cd ../045-frontend

# 安装依赖
npm install

# 开发模式
npm run dev

# 生产构建
npm run build
```

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 护工 | nurse1 | admin123 |
| 家属 | family1 | admin123 |

## 接口文档

启动后端后访问：http://localhost:8080/doc.html

## 项目结构

```
045-backend/
├── src/main/java/com/xiaou/
│   ├── common/          # 通用类
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── mapper/          # MyBatis映射
│   ├── service/         # 服务层
│   ├── utils/           # 工具类
│   └── vo/              # 视图对象
├── src/main/resources/
│   └── application.yml  # 配置文件
└── sql/
    └── init.sql         # 数据库初始化脚本

045-frontend/
├── src/
│   ├── api/             # API接口
│   ├── layouts/         # 布局组件
│   ├── router/          # 路由配置
│   ├── stores/          # Pinia状态
│   ├── utils/           # 工具函数
│   └── views/           # 页面组件
│       ├── admin/       # 管理员页面
│       ├── nurse/       # 护工页面
│       └── family/      # 家属页面
└── vite.config.ts       # Vite配置
```
