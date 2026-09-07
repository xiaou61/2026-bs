# 校园自习室座位预约系统 - 前端

## 技术栈
- Vue 3.4.0
- Element Plus 2.5.1  
- Vite 5.0.8
- Pinia 2.1.7
- Vue Router 4.2.5
- Axios 1.6.2

## 功能模块

### 学生端
- 用户登录/注册
- 首页仪表盘
- 自习室列表查看
- 座位选择和预约
- 预约记录管理
- 个人中心
- 信用分查看

### 管理端
- 管理员登录
- 数据统计仪表盘
- 自习室管理
- 座位管理
- 预约管理
- 用户管理
- 系统配置

## 安装运行

### 1. 安装依赖
```bash
npm install
```

### 2. 启动开发服务器
```bash
npm run dev
```
前端将运行在 http://localhost:5173

### 3. 构建生产版本
```bash
npm run build
```

## 项目结构
```
022-frontend/
├── src/
│   ├── api/              # API接口封装
│   ├── layouts/          # 布局组件
│   ├── router/           # 路由配置
│   ├── stores/           # Pinia状态管理
│   ├── styles/           # 全局样式
│   ├── utils/            # 工具函数
│   ├── views/            # 页面组件
│   │   ├── admin/        # 管理端页面
│   │   └── student/      # 学生端页面
│   ├── App.vue           # 根组件
│   └── main.js           # 入口文件
├── index.html
├── package.json
└── vite.config.js
```

## 配置说明

### API代理配置
在 `vite.config.js` 中配置了API代理：
```js
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
    rewrite: (path) => path.replace(/^\/api/, '')
  }
}
```

### 默认账号
- 管理员：admin / 123456
- 学生：202001 / 123456

## 注意事项
1. 确保后端服务运行在 http://localhost:8080
2. 首次运行需要初始化数据库
3. 开发环境已配置跨域代理
4. 生产环境需要配置nginx反向代理