# 基于SpringBoot的剧本杀创作与预约平台

## 项目简介

本系统是一个剧本杀创作与预约平台,支持剧本作者创作发布剧本、店家管理店铺和接受预约、玩家浏览剧本和预约体验。系统采用前后端分离架构,后端使用SpringBoot 3,前端使用Vue 3 + Element Plus。

## 技术栈

### 后端
- Spring Boot 3.2.0
- MyBatis Plus 3.5.5
- MySQL 8.0
- JWT 认证
- Lombok

### 前端
- Vue 3
- TypeScript
- Element Plus
- Vue Router
- Pinia
- Axios

## 功能模块

### 管理员端
- 系统数据统计
- 用户管理(启用/禁用)
- 店铺管理(审核)
- 剧本管理(审核上架/下架)
- 剧本分类管理
- 公告管理

### 店家端
- 店铺信息管理
- 房间管理
- 上架剧本管理
- 预约订单管理(确认/完成)

### 作者端
- 剧本创作与发布
- 剧本内容管理(章节/角色剧本)
- 作品数据统计

### 玩家端
- 剧本浏览与搜索
- 店铺浏览与查看
- 在线预约
- 我的预约管理
- 收藏管理
- 评价功能
- 系统公告

## 数据库设计

系统包含11张核心表：
- `sys_user` - 用户表(玩家/店家/作者/管理员)
- `shop` - 店铺表
- `room` - 房间表
- `script_category` - 剧本分类表
- `script` - 剧本表
- `script_content` - 剧本内容表
- `shop_script` - 店铺剧本关联表
- `reservation` - 预约表
- `review` - 评价表
- `favorite` - 收藏表
- `notice` - 公告表

## 快速开始

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+

### 后端启动
1. 创建数据库 `script_kill`
2. 执行 `sql/init.sql` 初始化数据
3. 修改 `application.yml` 中的数据库配置
4. 运行 `ScriptKillApplication.java`

### 前端启动
```bash
cd 047-frontend
npm install
npm run dev
```

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 店家 | shop1 | 123456 |
| 作者 | author1 | 123456 |
| 玩家 | user1 | 123456 |

## 项目结构

```
047-backend/
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

047-frontend/
├── src/
│   ├── api/             # API 接口
│   ├── layouts/         # 布局组件
│   ├── router/          # 路由配置
│   ├── stores/          # Pinia 状态管理
│   └── views/           # 页面组件
│       ├── admin/       # 管理员页面
│       ├── owner/       # 店家页面
│       ├── author/      # 作者页面
│       └── user/        # 玩家页面
└── README.md
```

## 业务流程

1. **作者创作剧本**：创建剧本信息，编写章节和角色剧本，提交审核
2. **管理员审核**：审核剧本内容，通过后上架
3. **店家上架剧本**：从已上架剧本中选择，设置价格上架到店铺
4. **玩家浏览预约**：浏览剧本和店铺，选择时间和房间进行预约
5. **店家处理预约**：确认预约，完成后标记订单完成
6. **玩家评价**：完成体验后可对店铺和剧本进行评价
