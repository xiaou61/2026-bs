# 校园快递代收管理系统 - 前端

## 项目简介
基于 Vue 3 + Element Plus 开发的校园快递代收管理系统前端项目。

## 技术栈
- Vue 3
- Vite 5
- Vue Router 4
- Pinia 2
- Element Plus 2
- Axios

## 项目结构
```
009-frontend/
├── src/
│   ├── api/                    # API接口
│   │   ├── auth.js            # 认证相关
│   │   ├── user.js            # 用户管理
│   │   ├── express.js         # 快递管理
│   │   ├── station.js         # 代收点管理
│   │   ├── company.js         # 快递公司管理
│   │   ├── notification.js    # 通知管理
│   │   └── stats.js           # 统计分析
│   ├── layout/                # 布局组件
│   │   ├── StudentLayout.vue # 学生端布局
│   │   ├── CourierLayout.vue # 快递员布局
│   │   └── AdminLayout.vue   # 管理员布局
│   ├── router/                # 路由配置
│   │   └── index.js
│   ├── stores/                # 状态管理
│   │   └── user.js           # 用户状态
│   ├── utils/                 # 工具类
│   │   └── request.js        # axios封装
│   ├── views/                 # 页面组件
│   │   ├── Login.vue         # 登录页
│   │   ├── Register.vue      # 注册页
│   │   ├── student/          # 学生端页面
│   │   │   ├── MyPackages.vue      # 我的快递
│   │   │   ├── PickupHistory.vue   # 取件历史
│   │   │   ├── Notifications.vue   # 消息通知
│   │   │   └── Profile.vue         # 个人中心
│   │   ├── courier/          # 快递员页面
│   │   │   ├── ExpressIn.vue       # 快递入库
│   │   │   ├── Pickup.vue          # 取件核销
│   │   │   └── ExpressList.vue     # 快递管理
│   │   └── admin/            # 管理员页面
│   │       ├── Dashboard.vue       # 数据统计
│   │       ├── ExpressManage.vue   # 快递管理
│   │       ├── StationManage.vue   # 代收点管理
│   │       ├── CompanyManage.vue   # 快递公司管理
│   │       ├── UserManage.vue      # 用户管理
│   │       └── Pickup.vue          # 取件核销
│   ├── App.vue               # 根组件
│   └── main.js               # 入口文件
├── index.html                # HTML模板
├── vite.config.js            # Vite配置
├── package.json              # 依赖配置
└── README.md                 # 项目说明
```

## 快速开始

### 1. 安装依赖
```bash
npm install
```

### 2. 启动开发服务器
```bash
npm run dev
```

### 3. 访问系统
浏览器访问: http://localhost:5009

### 4. 构建生产版本
```bash
npm run build
```

## 功能模块

### 学生端功能
- 我的快递：查看待取快递列表，显示取件码和超期信息
- 取件历史：查看已取件记录，包含超期费用
- 消息通知：接收快递到达、超期提醒等通知
- 个人中心：查看个人信息、修改密码

### 快递员功能
- 快递入库：录入快递信息，自动生成取件码
- 取件核销：验证取件码，确认取件
- 快递管理：查询快递记录，支持筛选

### 管理员功能
- 数据统计：查看今日入库、取件、库存等统计数据
- 快递管理：管理所有快递记录，支持多条件查询
- 代收点管理：管理代收点信息、库存容量
- 快递公司管理：管理快递公司基本信息
- 用户管理：管理用户信息、角色、状态
- 取件核销：核销快递取件

## 测试账号

### 学生账号
- 用户名: student1
- 密码: 123456

### 快递员账号
- 用户名: courier1
- 密码: 123456

### 管理员账号
- 用户名: admin
- 密码: 123456

## 技术特点

### 1. 组件化开发
- 使用Vue 3 Composition API
- 布局组件复用（三种不同角色布局）
- 路由守卫控制权限

### 2. 状态管理
- 使用Pinia管理全局状态
- 用户信息和Token持久化存储

### 3. 网络请求
- Axios封装统一请求
- 请求拦截器自动添加Token
- 响应拦截器统一错误处理

### 4. 路由配置
- 根据用户角色自动跳转
- 路由守卫验证登录状态
- 权限控制访问页面

### 5. UI设计
- Element Plus组件库
- 响应式布局
- 图标和颜色区分不同角色

## API接口

### 基础URL
开发环境: `/api` (代理到 http://localhost:8009)

### 主要接口

#### 认证接口
- POST /api/auth/register - 用户注册
- POST /api/auth/login - 用户登录
- GET /api/auth/info - 获取用户信息

#### 快递接口
- POST /api/express/in - 快递入库
- GET /api/express/list - 快递列表
- POST /api/express/verify-pickup - 验证取件码
- POST /api/express/pickup - 取件核销
- GET /api/express/my-packages - 我的待取快递
- GET /api/express/my-history - 我的取件历史

#### 代收点接口
- GET /api/station/list - 代收点列表
- GET /api/station/all - 所有代收点
- POST /api/station - 添加代收点
- PUT /api/station/{id} - 更新代收点
- DELETE /api/station/{id} - 删除代收点

#### 统计接口
- GET /api/stats/overview - 数据概览
- GET /api/stats/company-rank - 快递公司排行
- GET /api/stats/station-rank - 代收点排行

## 开发规范

### 1. 命名规范
- 组件名: PascalCase (如: MyPackages.vue)
- 文件名: PascalCase
- 变量名: camelCase
- 常量名: UPPER_SNAKE_CASE

### 2. 代码风格
- 使用2空格缩进
- 使用单引号
- 组件使用 `<script setup>`
- 响应式数据使用 ref/reactive

### 3. 提交规范
- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 重构

## 常见问题

### 1. 端口冲突
修改 `vite.config.js` 中的端口号：
```js
server: {
  port: 5009 // 修改为其他端口
}
```

### 2. 跨域问题
已在 `vite.config.js` 中配置代理，无需额外处理

### 3. Token过期
系统会自动跳转到登录页面，重新登录即可

## 部署说明

### 1. 构建
```bash
npm run build
```

### 2. 部署
将 `dist` 目录部署到Web服务器（Nginx、Apache等）

### 3. Nginx配置示例
```nginx
server {
    listen 80;
    server_name example.com;
    
    location / {
        root /path/to/dist;
        try_files $uri $uri/ /index.html;
    }
    
    location /api {
        proxy_pass http://localhost:8009;
    }
}
```

## 浏览器支持
- Chrome (推荐)
- Firefox
- Safari
- Edge

## 联系方式
如有问题，请查看后端项目的 PRD 文档或 README 文档。

