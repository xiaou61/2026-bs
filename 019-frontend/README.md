# 校园运动健康管理平台 - 前端

基于 Vue 3 + Element Plus 的校园运动健康管理系统前端。

## 技术栈

- Vue 3
- Vite
- Vue Router
- Pinia
- Element Plus
- ECharts
- Axios

## 功能模块

### 1. 用户认证
- 登录
- 注册
- 个人中心

### 2. 运动打卡
- 创建运动记录
- 查看运动记录列表
- 运动数据统计

### 3. 健身计划
- 创建健身计划
- 计划进度管理
- 计划列表查看

### 4. 约球活动
- 发起约球活动
- 浏览活动列表
- 参加/取消活动

### 5. 健康档案
- 记录健康数据
- 健康数据趋势图表
- BMI计算

### 6. 场馆预约
- 浏览场馆列表
- 预约场馆
- 我的预约管理

### 7. 排行榜
- 积分排行
- 运动数据排行

## 安装运行

```bash
# 安装依赖
npm install

# 运行开发服务器
npm run dev

# 构建生产版本
npm run build
```

## 目录结构

```
src/
├── api/              # API接口封装
├── assets/           # 静态资源
├── layout/           # 布局组件
├── router/           # 路由配置
├── stores/           # 状态管理
├── utils/            # 工具函数
├── views/            # 页面组件
│   ├── sport/        # 运动记录
│   ├── plan/         # 健身计划
│   ├── activity/     # 约球活动
│   ├── health/       # 健康档案
│   └── venue/        # 场馆预约
├── App.vue           # 根组件
└── main.js           # 入口文件
```

## 默认端口

- 开发服务器: http://localhost:5173
- 后端API代理: http://localhost:8080

## 注意事项

1. 启动前确保后端服务已运行
2. 默认代理配置在 `vite.config.js` 中
3. 请先注册账号或使用测试账号登录

