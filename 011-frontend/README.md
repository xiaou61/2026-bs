# 校园短视频平台 - 前端

基于 Vue 3 + Element Plus 的短视频创作与分享平台前端

## 技术栈

- Vue 3
- Vite 5
- Element Plus 2
- Vue Router 4
- Pinia 2
- Axios
- Video.js

## 项目结构

```
src/
├── api/              # API接口封装
│   ├── auth.js       # 认证接口
│   ├── video.js      # 视频接口
│   ├── user.js       # 用户接口
│   ├── topic.js      # 话题接口
│   ├── comment.js    # 评论接口
│   └── notification.js # 通知接口
├── layout/           # 布局组件
│   └── MainLayout.vue
├── router/           # 路由配置
│   └── index.js
├── stores/           # 状态管理
│   └── user.js
├── utils/            # 工具函数
│   └── request.js    # Axios封装
├── views/            # 页面组件
│   ├── Login.vue     # 登录页
│   ├── Register.vue  # 注册页
│   ├── Home.vue      # 推荐页（首页）
│   ├── Following.vue # 关注页
│   ├── Publish.vue   # 发布页
│   ├── Profile.vue   # 我的页面
│   ├── User.vue      # 用户主页
│   ├── VideoDetail.vue # 视频详情页
│   ├── Topic.vue     # 话题页
│   └── Notification.vue # 通知页
├── App.vue
└── main.js

## 快速开始

### 安装依赖

```bash
cd 011-frontend
npm install
```

### 启动开发服务器

```bash
npm run dev
```

访问: http://localhost:5173

### 构建生产版本

```bash
npm run build
```

## 主要功能

### 用户功能
- 用户注册登录
- 个人信息修改
- 头像上传
- 关注/取消关注
- 积分查看

### 视频功能
- 视频上传发布
- 沉浸式视频播放
- 上下滑动切换
- 点赞/收藏/转发
- 视频详情查看

### 社交功能
- 发布评论
- 点赞评论
- 回复评论
- 消息通知
- 关注用户

### 话题功能
- 话题浏览
- 话题视频列表
- 话题搜索

## API配置

开发环境下，Vite会自动代理API请求到后端服务器：

- 后端地址: http://localhost:8011
- 代理配置: vite.config.js

## 测试账号

- 用户名: test1
- 密码: 123456

## 注意事项

1. 确保后端服务已启动（端口8011）
2. 视频上传限制100MB
3. 封面图片限制2MB
4. 需要先登录才能访问主要功能

