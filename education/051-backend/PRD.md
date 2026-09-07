# 基于微信小程序的网络安全知识科普APP - 产品需求文档

## 1. 项目概述

### 1.1 项目背景
随着互联网的普及，网络安全问题日益严峻。普通用户缺乏基本的网络安全知识，容易遭受网络诈骗、隐私泄露等威胁。本项目旨在通过微信小程序平台，以轻松易懂的方式向用户普及网络安全知识。

### 1.2 项目目标
- 提供系统化的网络安全知识学习内容
- 通过趣味答题方式增强学习效果
- 提供实时安全资讯和预警信息
- 建立用户学习激励机制

### 1.3 目标用户
- 普通互联网用户
- 中小学生及家长
- 老年人群体
- 对网络安全感兴趣的人群

## 2. 功能需求

### 2.1 用户模块
- 微信授权登录
- 用户信息管理（昵称、头像）
- 学习记录查看
- 积分与等级系统

### 2.2 知识学习模块
- 知识分类浏览（密码安全、防诈骗、隐私保护、病毒防护等）
- 知识文章详情（图文并茂）
- 学习进度记录
- 收藏功能

### 2.3 答题测验模块
- 每日答题挑战
- 分类专项测验
- 错题本功能
- 答题排行榜

### 2.4 安全资讯模块
- 最新安全新闻
- 诈骗案例警示
- 安全预警推送

### 2.5 互动社区模块
- 安全问答（用户提问/回答）
- 经验分享
- 评论点赞

### 2.6 后台管理模块
- 用户管理
- 内容管理（知识文章、题库）
- 资讯管理
- 数据统计

## 3. 非功能需求

### 3.1 性能需求
- 页面加载时间 < 2秒
- 支持1000+并发用户

### 3.2 安全需求
- 用户数据加密存储
- 接口鉴权机制
- 防SQL注入、XSS攻击

### 3.3 兼容性
- 支持微信版本7.0+
- 适配主流手机屏幕

## 4. 数据库设计

### 4.1 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| openid | varchar(64) | 微信openid |
| nickname | varchar(50) | 昵称 |
| avatar | varchar(255) | 头像URL |
| points | int | 积分 |
| level | int | 等级 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.2 知识分类表 (knowledge_category)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(50) | 分类名称 |
| icon | varchar(255) | 图标URL |
| sort | int | 排序 |
| status | tinyint | 状态 |

### 4.3 知识文章表 (knowledge_article)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| category_id | bigint | 分类ID |
| title | varchar(100) | 标题 |
| cover | varchar(255) | 封面图 |
| content | text | 内容 |
| view_count | int | 浏览次数 |
| like_count | int | 点赞数 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

### 4.4 题目表 (question)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| category_id | bigint | 分类ID |
| content | varchar(500) | 题目内容 |
| options | json | 选项(JSON格式) |
| answer | varchar(10) | 正确答案 |
| explanation | varchar(500) | 解析 |
| difficulty | tinyint | 难度(1-3) |

### 4.5 答题记录表 (answer_record)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| question_id | bigint | 题目ID |
| user_answer | varchar(10) | 用户答案 |
| is_correct | tinyint | 是否正确 |
| create_time | datetime | 答题时间 |

### 4.6 学习记录表 (learn_record)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| article_id | bigint | 文章ID |
| progress | int | 进度(百分比) |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.7 收藏表 (favorite)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| article_id | bigint | 文章ID |
| create_time | datetime | 收藏时间 |

### 4.8 资讯表 (news)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 标题 |
| cover | varchar(255) | 封面图 |
| content | text | 内容 |
| source | varchar(50) | 来源 |
| view_count | int | 浏览次数 |
| status | tinyint | 状态 |
| publish_time | datetime | 发布时间 |

### 4.9 问答表 (qa_post)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| title | varchar(100) | 标题 |
| content | text | 内容 |
| view_count | int | 浏览次数 |
| reply_count | int | 回复数 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

### 4.10 问答回复表 (qa_reply)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| post_id | bigint | 问答ID |
| user_id | bigint | 用户ID |
| content | text | 内容 |
| like_count | int | 点赞数 |
| create_time | datetime | 创建时间 |

### 4.11 管理员表 (admin)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| username | varchar(50) | 用户名 |
| password | varchar(100) | 密码 |
| nickname | varchar(50) | 昵称 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

## 5. 接口设计

### 5.1 用户接口
- POST /api/user/login - 微信登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/info - 更新用户信息
- GET /api/user/stats - 获取学习统计

### 5.2 知识接口
- GET /api/category/list - 获取分类列表
- GET /api/article/list - 获取文章列表
- GET /api/article/{id} - 获取文章详情
- POST /api/article/like/{id} - 点赞文章
- POST /api/favorite/add - 添加收藏
- DELETE /api/favorite/{id} - 取消收藏
- GET /api/favorite/list - 收藏列表

### 5.3 答题接口
- GET /api/question/daily - 每日答题
- GET /api/question/category/{categoryId} - 分类题目
- POST /api/answer/submit - 提交答案
- GET /api/answer/wrong-list - 错题列表
- GET /api/rank/list - 排行榜

### 5.4 资讯接口
- GET /api/news/list - 资讯列表
- GET /api/news/{id} - 资讯详情

### 5.5 问答接口
- GET /api/qa/list - 问答列表
- GET /api/qa/{id} - 问答详情
- POST /api/qa/post - 发布问题
- POST /api/qa/reply - 回复问题
- POST /api/qa/like/{id} - 点赞回复

### 5.6 管理后台接口
- POST /api/admin/login - 管理员登录
- CRUD /api/admin/user/* - 用户管理
- CRUD /api/admin/category/* - 分类管理
- CRUD /api/admin/article/* - 文章管理
- CRUD /api/admin/question/* - 题目管理
- CRUD /api/admin/news/* - 资讯管理
- GET /api/admin/stats/* - 数据统计

## 6. 技术架构

### 6.1 后端技术栈
- Spring Boot 2.7.x
- MyBatis-Plus
- MySQL 8.0
- Redis
- JWT认证

### 6.2 前端技术栈
- 微信小程序原生开发
- Vue 3 (管理后台)
- Element Plus (管理后台UI)

## 7. 项目结构

### 7.1 后端结构
```
051-backend/
├── src/main/java/com/security/
│   ├── controller/      # 控制器
│   ├── service/         # 服务层
│   ├── mapper/          # 数据访问层
│   ├── entity/          # 实体类
│   ├── dto/             # 数据传输对象
│   ├── vo/              # 视图对象
│   ├── config/          # 配置类
│   ├── utils/           # 工具类
│   └── common/          # 公共类
├── src/main/resources/
│   ├── mapper/          # MyBatis XML
│   └── application.yml  # 配置文件
└── pom.xml
```

### 7.2 前端结构(小程序)
```
051-frontend/miniprogram/
├── pages/               # 页面
├── components/          # 组件
├── utils/               # 工具
├── api/                 # 接口
├── static/              # 静态资源
└── app.js/json/wxss    # 入口文件
```

### 7.3 管理后台结构
```
051-frontend/admin/
├── src/
│   ├── views/           # 页面
│   ├── components/      # 组件
│   ├── api/             # 接口
│   ├── router/          # 路由
│   ├── store/           # 状态管理
│   └── utils/           # 工具
└── package.json
```
