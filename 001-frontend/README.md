# 校园事务管理系统 - 前端

基于 Vue 3 + Element Plus + Vite 的校园事务管理系统前端项目

## 项目简介

本项目是校园事务管理系统的前端部分，采用 Vue 3 Composition API 开发，使用 Element Plus 作为 UI 组件库，实现了学生请假、宿舍报修、公告管理、活动报名等功能的用户界面。

## 技术栈

- **框架**: Vue 3.4
- **构建工具**: Vite 5.0
- **UI组件库**: Element Plus 2.5
- **状态管理**: Pinia 2.1
- **路由**: Vue Router 4.2
- **HTTP请求**: Axios 1.6
- **图表**: ECharts 5.4
- **日期处理**: Day.js 1.11

## 功能模块

### 1. 用户认证
- 用户登录/注册
- JWT Token 认证
- 路由权限控制
- 个人信息管理

### 2. 首页仪表盘
- 数据统计展示
- 最新公告列表
- 最新活动列表
- 个人事务总览

### 3. 请假管理
- 学生提交请假申请
- 教师/管理员审批请假
- 请假记录查询
- 状态筛选和分页

### 4. 报修管理
- 学生提交报修申请
- 管理员处理报修
- 报修状态跟踪
- 类型和状态筛选

### 5. 公告中心
- 公告列表展示
- 公告详情查看
- 教师/管理员发布公告
- 公告分类和搜索
- 管理员置顶功能

### 6. 活动中心
- 活动列表展示
- 活动详情查看
- 学生报名/取消报名
- 教师发布活动
- 报名人数限制

### 7. 用户管理（管理员）
- 用户列表查询
- 添加/编辑用户
- 删除用户
- 角色分配

### 8. 个人中心
- 修改个人信息
- 修改密码
- 查看个人资料

## 项目结构

```
src/
├── api/                    # API接口
│   ├── auth.js            # 认证接口
│   ├── user.js            # 用户管理接口
│   ├── leave.js           # 请假接口
│   ├── repair.js          # 报修接口
│   ├── notice.js          # 公告接口
│   └── activity.js        # 活动接口
├── assets/                # 静态资源
├── components/            # 公共组件
├── layout/                # 布局组件
│   └── MainLayout.vue     # 主布局
├── router/                # 路由配置
│   └── index.js          # 路由定义
├── stores/                # 状态管理
│   └── user.js           # 用户状态
├── utils/                 # 工具函数
│   └── request.js        # Axios封装
├── views/                 # 页面组件
│   ├── Login.vue         # 登录页
│   ├── Register.vue      # 注册页
│   ├── Dashboard.vue     # 首页
│   ├── Profile.vue       # 个人中心
│   ├── Leave/            # 请假管理
│   ├── Repair/           # 报修管理
│   ├── Notice/           # 公告中心
│   ├── Activity/         # 活动中心
│   └── User/             # 用户管理
├── App.vue               # 根组件
└── main.js               # 入口文件
```

## 快速开始

### 1. 环境要求
- Node.js 16+
- npm 或 pnpm

### 2. 安装依赖
```bash
npm install
# 或
pnpm install
```

### 3. 启动开发服务器
```bash
npm run dev
# 或
pnpm dev
```

访问 http://localhost:3000

### 4. 构建生产版本
```bash
npm run build
# 或
pnpm build
```

### 5. 预览生产版本
```bash
npm run preview
# 或
pnpm preview
```

## 默认账号

- **管理员**: admin / admin123
- **教师**: teacher001 / teacher123
- **学生**: student001 / student123

## 开发说明

### API 配置

后端 API 地址在 `vite.config.js` 中配置：

```javascript
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',  // 后端地址
      changeOrigin: true
    }
  }
}
```

### 路由守卫

路由守卫配置在 `router/index.js` 中：
- 未登录用户自动跳转到登录页
- 根据角色控制页面访问权限
- 自动从 localStorage 恢复登录状态

### 权限控制

```javascript
// 路由元信息中配置角色权限
{
  path: 'user',
  meta: { roles: ['admin'] }  // 仅管理员可访问
}
```

### HTTP 请求

所有 HTTP 请求通过 Axios 统一处理：
- 自动添加 Token 到请求头
- 统一错误处理
- 自动处理 401 跳转登录

### 状态管理

使用 Pinia 管理全局状态：
- 用户信息
- Token 管理
- 登录/登出逻辑

## UI 规范

- 使用 Element Plus 组件库
- 响应式布局
- 统一的颜色主题
- 友好的交互体验

## 浏览器支持

- Chrome (推荐)
- Firefox
- Safari
- Edge

## 注意事项

1. 确保后端服务已启动
2. Token 存储在 localStorage
3. 生产环境需要配置正确的后端 API 地址
4. 建议使用 HTTPS 保护数据传输

## 待优化功能

- [ ] 图片上传功能
- [ ] 文件下载功能
- [ ] 消息推送
- [ ] 移动端适配
- [ ] 暗色主题
- [ ] 国际化

## 许可证

MIT License

## 联系方式

如有问题，请联系项目负责人。

