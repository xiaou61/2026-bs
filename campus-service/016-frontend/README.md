# 校园快递代领服务平台 - 前端

基于 Vue 3 + Element Plus 开发的校园快递代领服务平台前端项目。

## 技术栈

- Vue 3 - 渐进式 JavaScript 框架
- Element Plus - Vue 3 组件库
- Vue Router - 路由管理
- Pinia - 状态管理
- Axios - HTTP 请求库
- Vite - 构建工具

## 功能模块

### 用户端
- 用户注册/登录
- 订单广场（浏览待接单订单）
- 发布代领订单
- 接单功能
- 我的订单（我发布的/我接的）
- 订单详情与操作（接单、上传凭证、确认收货、评价、取消）
- 钱包管理（充值、提现、交易记录）
- 个人中心（信息修改、密码修改）
- 系统通知

### 管理端
- 管理员登录
- 数据概览（统计图表）
- 用户管理（查询、禁用/启用）
- 订单管理（查询、详情、强制取消）
- 投诉管理（查询、处理）
- 交易流水（查询、统计）

## 项目结构

```
016-frontend/
├── index.html              # 入口HTML
├── package.json            # 依赖配置
├── vite.config.js          # Vite配置
├── src/
│   ├── main.js            # 应用入口
│   ├── App.vue            # 根组件
│   ├── api/               # API接口
│   │   ├── auth.js
│   │   ├── order.js
│   │   ├── wallet.js
│   │   ├── review.js
│   │   ├── notification.js
│   │   ├── complaint.js
│   │   └── admin.js
│   ├── stores/            # 状态管理
│   │   └── user.js
│   ├── router/            # 路由配置
│   │   └── index.js
│   ├── utils/             # 工具类
│   │   └── request.js
│   ├── layout/            # 布局组件
│   │   ├── UserLayout.vue
│   │   └── AdminLayout.vue
│   └── views/             # 页面组件
│       ├── Login.vue
│       ├── Register.vue
│       ├── Home.vue
│       ├── Publish.vue
│       ├── OrderDetail.vue
│       ├── MyOrders.vue
│       ├── Wallet.vue
│       ├── Profile.vue
│       ├── Notifications.vue
│       └── admin/
│           ├── Login.vue
│           ├── Dashboard.vue
│           ├── Users.vue
│           ├── Orders.vue
│           ├── Complaints.vue
│           └── Transactions.vue
```

## 安装依赖

```bash
npm install
```

## 开发运行

```bash
npm run dev
```

访问地址：http://localhost:5173

## 构建部署

```bash
npm run build
```

## 配置说明

### API代理配置

在 `vite.config.js` 中配置了开发环境的API代理：

```javascript
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

后端服务默认运行在 `http://localhost:8080`，如需修改请更新此配置。

## 默认账号

### 用户账号
参考后端 ACCOUNTS.md 文件

### 管理员账号
- 账号：admin
- 密码：admin123

## 主要功能说明

### 订单流程
1. 用户发布订单 → 订单进入广场
2. 代领员接单 → 上传取件凭证 → 上传送达凭证
3. 发单人确认收货 → 订单完成 → 评价

### 钱包流程
1. 用户充值（模拟支付）
2. 发单时冻结跑腿费
3. 确认收货后解冻并转给代领员
4. 代领员可提现

### 通知系统
- 订单状态变更通知
- 余额变动通知
- 实时消息提醒

## 注意事项

1. 首次运行前请确保后端服务已启动
2. 所有接口都需要登录后才能访问（除登录/注册接口）
3. Token存储在localStorage中，过期后需要重新登录
4. 图片上传功能目前使用URL输入方式（可根据需要对接OSS）

