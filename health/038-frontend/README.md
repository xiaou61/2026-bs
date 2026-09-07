# 个人饮食管理系统 - 前端

## 技术栈
- Vue 3
- Element Plus
- Vue Router 4
- Pinia
- Axios
- ECharts
- Vite

## 安装依赖
```bash
npm install
```

## 启动开发服务器
```bash
npm run dev
```

访问地址: http://localhost:3038

## 构建生产版本
```bash
npm run build
```

## 项目结构
```
src/
├── views/          # 页面组件
├── router/         # 路由配置
├── utils/          # 工具函数
├── App.vue         # 根组件
└── main.js         # 入口文件
```

## 功能页面
- 登录页面 (/login)
- 数据概览 (/dashboard)
- 饮食记录 (/diet-record)
- 食物库 (/food-library)
- 营养分析 (/nutrition)
- 健康数据 (/health)
- 营养目标 (/goal)
- 食谱推荐 (/recipe)

## 注意事项
- 确保后端服务已启动 (端口8038)
- 默认代理配置已在vite.config.js中设置
