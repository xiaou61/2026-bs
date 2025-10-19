# 校园社团与兴趣圈层平台 - 前端说明

## 技术栈
- jQuery 3.7.1
- Bootstrap 5.3.2
- Bootstrap Icons 1.11.2

## 页面列表

### 已完成页面
1. **index.html** - 首页（热门社团、最新活动）
2. **login.html** - 登录页面
3. **register.html** - 注册页面
4. **clubs.html** - 社团广场（列表、搜索、筛选）

### 待创建页面
- club-detail.html - 社团详情
- create-club.html - 创建社团
- activities.html - 活动列表
- activity-detail.html - 活动详情
- topics.html - 话题广场
- topic-detail.html - 话题详情
- circles.html - 兴趣圈子
- profile.html - 个人中心
- my-clubs.html - 我的社团
- my-activities.html - 我的活动

## 目录结构
```
static/
├── index.html              # 首页
├── login.html             # 登录
├── register.html          # 注册
├── clubs.html             # 社团广场
├── css/
│   └── style.css          # 全局样式
├── js/
│   ├── common.js          # 通用工具函数
│   ├── index.js           # 首页逻辑
│   └── clubs.js           # 社团页面逻辑
└── README.md              # 说明文档
```

## API 调用说明

所有 API 调用都通过 `common.js` 中的 `request` 对象：
- `request.get(url, params)` - GET 请求
- `request.post(url, data)` - POST 请求
- `request.put(url, data)` - PUT 请求
- `request.delete(url)` - DELETE 请求

## 认证说明

使用 JWT Token 认证，Token 存储在 localStorage 中：
- 登录后自动保存 Token
- 所有 API 请求自动携带 Token
- Token 过期自动跳转登录页

## 启动方式

1. 启动后端服务（Spring Boot）
2. 访问 http://localhost:8080/index.html
3. 使用测试账户登录：
   - 用户名：student1
   - 密码：123456

## 注意事项

1. 所有页面使用 CDN 引入依赖，需要联网
2. 图片使用占位图或用户上传的图片路径
3. 响应式设计，支持移动端访问

