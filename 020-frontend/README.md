# 校园学习资源共享平台 - 前端

基于 Vue3 + Element Plus 开发的校园学习资源共享平台前端项目

## 技术栈

- Vue 3
- Element Plus
- Vue Router
- Pinia
- Axios
- Vite

## 项目启动

### 安装依赖

```bash
npm install
```

### 开发运行

```bash
npm run dev
```

访问地址：http://localhost:5020

### 生产构建

```bash
npm run build
```

## 主要功能

### 1. 用户系统
- 用户注册/登录
- 个人信息管理
- 积分系统

### 2. 资源管理
- 资源上传
- 资源浏览和搜索
- 资源下载（积分兑换）
- 资源评价

### 3. 学习小组
- 创建/加入小组
- 小组成员管理
- 协作学习

### 4. 题库系统
- 题目浏览
- 随机刷题
- 错题本管理

### 5. 答疑系统
- 提问功能
- 悬赏提问
- 回答问题
- 采纳答案

### 6. 笔记系统
- Markdown笔记编辑
- 笔记分享
- 笔记点赞

## 测试账户

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| student1 | 123456 | 学生 |
| student2 | 123456 | 学生 |
| teacher1 | 123456 | 老师 |

## 项目结构

```
020-frontend/
├── src/
│   ├── api/              # API接口
│   ├── assets/           # 静态资源
│   ├── layout/           # 布局组件
│   ├── router/           # 路由配置
│   ├── stores/           # 状态管理
│   ├── utils/            # 工具函数
│   ├── views/            # 页面组件
│   │   ├── resource/     # 资源模块
│   │   ├── group/        # 小组模块
│   │   ├── question/     # 题库模块
│   │   ├── qa/           # 答疑模块
│   │   ├── note/         # 笔记模块
│   │   ├── Login.vue
│   │   ├── Register.vue
│   │   ├── Home.vue
│   │   └── Profile.vue
│   ├── App.vue
│   └── main.js
├── index.html
├── vite.config.js
└── package.json
```

