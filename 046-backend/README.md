# 基于SpringBoot的垃圾回收服务系统

## 项目简介

本系统是一个基于SpringBoot + Vue3的垃圾回收服务平台，实现了居民在线预约垃圾回收、回收员上门回收、积分奖励兑换等功能，旨在推动垃圾分类和环保理念的普及。

## 技术栈

### 后端
- Spring Boot 3.x
- MyBatis Plus
- MySQL 8.0
- JWT 认证

### 前端
- Vue 3
- TypeScript
- Element Plus
- Vue Router
- Pinia
- Axios

## 功能模块

### 居民端
- 用户注册/登录
- 预约垃圾回收
- 查看订单状态
- 积分管理
- 积分商城兑换
- 环保知识学习
- 系统公告查看

### 回收员端
- 查看待处理订单
- 接单/完成回收
- 录入回收明细（重量、金额、积分）

### 管理员端
- 数据统计仪表盘
- 订单管理
- 用户管理
- 小区管理
- 垃圾分类管理（设置价格、积分比例）
- 积分商品管理
- 兑换记录管理
- 环保知识发布
- 系统公告发布

## 数据库设计

系统包含10张核心表：
- `sys_user` - 用户表（居民、回收员、管理员）
- `community` - 小区表
- `garbage_category` - 垃圾分类表
- `recycle_order` - 回收订单表
- `order_detail` - 订单明细表
- `points_record` - 积分记录表
- `points_product` - 积分商品表
- `exchange_record` - 兑换记录表
- `knowledge` - 环保知识表
- `notice` - 系统公告表

## 快速开始

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+

### 后端启动
1. 创建数据库 `garbage_recycle`
2. 执行 `sql/init.sql` 初始化数据
3. 修改 `application.yml` 中的数据库配置
4. 运行 `GarbageRecycleApplication.java`

### 前端启动
```bash
cd 046-frontend
npm install
npm run dev
```

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 回收员 | collector1 | admin123 |
| 居民 | user1 | admin123 |

## 项目结构

```
046-backend/
├── src/main/java/com/example/
│   ├── common/          # 通用类（Result、JWT工具等）
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── mapper/          # MyBatis Mapper
│   ├── service/         # 服务层
│   └── vo/              # 视图对象
├── sql/
│   └── init.sql         # 数据库初始化脚本
└── README.md

046-frontend/
├── src/
│   ├── api/             # API 接口
│   ├── layouts/         # 布局组件
│   ├── router/          # 路由配置
│   ├── stores/          # Pinia 状态管理
│   └── views/           # 页面组件
│       ├── admin/       # 管理员页面
│       ├── collector/   # 回收员页面
│       └── user/        # 居民页面
└── README.md
```

## 业务流程

1. **居民预约回收**：选择回收时间、地址，提交预约订单
2. **回收员接单**：查看待处理订单，选择接单
3. **上门回收**：回收员上门回收垃圾
4. **完成订单**：录入各类垃圾重量，系统自动计算金额和积分
5. **积分奖励**：居民获得积分，可用于兑换商品
