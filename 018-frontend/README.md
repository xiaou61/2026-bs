# 校园实习招聘与求职平台 - 前端

基于 Vue 3 + Element Plus 的校园实习招聘平台前端项目。

## 技术栈

- Vue 3
- Element Plus
- Vue Router
- Pinia
- Axios
- Vite

## 安装依赖

```bash
npm install
```

## 运行开发服务器

```bash
npm run dev
```

## 构建生产版本

```bash
npm run build
```

## 功能模块

### 学生端
- 岗位浏览与搜索
- 在线简历管理
- 投递记录查看
- 面试日程管理
- 经验分享广场
- 内推信息查看

### 企业端
- 岗位发布与管理
- 简历筛选与管理
- 面试安排与反馈
- 企业信息维护

## 项目结构

```
src/
├── api/            # API 接口
├── layout/         # 布局组件
├── router/         # 路由配置
├── stores/         # 状态管理
├── utils/          # 工具函数
├── views/          # 页面组件
│   ├── student/    # 学生端页面
│   └── company/    # 企业端页面
├── App.vue         # 根组件
└── main.js         # 入口文件
```

## 默认端口

前端服务运行在 http://localhost:5173

后端代理: http://localhost:8080

