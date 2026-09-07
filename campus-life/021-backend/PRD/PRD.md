# 校园二手交易平台 PRD

## 项目概述

校园二手交易平台（跳蚤市场），为大学生提供一个便捷的闲置物品交易平台。学生可以发布闲置物品、浏览购买、在线沟通、交易评价，实现校园内物品的循环利用。

## 核心功能

### 1. 用户管理
- 学生注册登录（学号、姓名、院系、宿舍）
- 个人信息管理
- 我的发布、我的购买
- 信用积分（100分初始，交易评价影响）

### 2. 商品管理
- 发布商品（标题、描述、价格、分类、成色、图片）
- 编辑商品
- 上下架管理
- 商品浏览（分类筛选、搜索）
- 商品详情

### 3. 交易管理
- 立即购买
- 议价功能（买家发起议价，卖家同意/拒绝）
- 订单状态流转
- 确认收货
- 交易评价

### 4. 聊天功能
- 买卖双方在线沟通
- 消息列表
- 未读消息提醒
- 简单文本聊天（不需要WebSocket，定时刷新即可）

### 5. 收藏功能
- 收藏商品
- 收藏列表
- 取消收藏

## 数据库设计

### 1. 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(50) | 用户名 |
| password | VARCHAR(200) | 密码(MD5加密) |
| student_id | VARCHAR(20) | 学号 |
| real_name | VARCHAR(50) | 真实姓名 |
| college | VARCHAR(100) | 学院 |
| dorm | VARCHAR(50) | 宿舍号 |
| phone | VARCHAR(20) | 手机号 |
| avatar | VARCHAR(200) | 头像URL |
| credit_score | INT | 信用分(默认100) |
| status | INT | 状态(0正常1禁用) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 2. 商品分类表 (category)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| category_name | VARCHAR(50) | 分类名称 |
| icon | VARCHAR(100) | 图标 |
| sort_order | INT | 排序 |
| create_time | DATETIME | 创建时间 |

**预置分类**：
- 教材书籍
- 数码电子
- 生活用品
- 服装鞋包
- 运动器材
- 其他闲置

### 3. 商品表 (product)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| seller_id | BIGINT | 卖家ID |
| category_id | BIGINT | 分类ID |
| title | VARCHAR(100) | 商品标题 |
| description | TEXT | 商品描述 |
| price | DECIMAL(10,2) | 价格 |
| original_price | DECIMAL(10,2) | 原价 |
| condition | VARCHAR(20) | 成色(全新/九成新/八成新/七成新) |
| images | TEXT | 图片URL列表(JSON数组) |
| status | VARCHAR(20) | 状态(on_sale/sold/off_shelf) |
| view_count | INT | 浏览次数 |
| favorite_count | INT | 收藏次数 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 4. 订单表 (order_info)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| order_no | VARCHAR(32) | 订单号 |
| product_id | BIGINT | 商品ID |
| seller_id | BIGINT | 卖家ID |
| buyer_id | BIGINT | 买家ID |
| price | DECIMAL(10,2) | 成交价格 |
| original_price | DECIMAL(10,2) | 原价 |
| is_bargain | INT | 是否议价(0否1是) |
| status | VARCHAR(20) | 状态(pending/completed/cancelled) |
| buyer_rating | INT | 买家评分(1-5) |
| buyer_comment | VARCHAR(500) | 买家评价 |
| seller_rating | INT | 卖家评分(1-5) |
| seller_comment | VARCHAR(500) | 卖家评价 |
| create_time | DATETIME | 创建时间 |
| complete_time | DATETIME | 完成时间 |
| cancel_time | DATETIME | 取消时间 |
| update_time | DATETIME | 更新时间 |

### 5. 聊天消息表 (chat_message)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| product_id | BIGINT | 商品ID |
| sender_id | BIGINT | 发送者ID |
| receiver_id | BIGINT | 接收者ID |
| content | TEXT | 消息内容 |
| is_read | INT | 是否已读(0未读1已读) |
| message_type | VARCHAR(20) | 消息类型(text/bargain) |
| bargain_price | DECIMAL(10,2) | 议价金额(议价消息专用) |
| bargain_status | VARCHAR(20) | 议价状态(pending/accepted/rejected) |
| create_time | DATETIME | 创建时间 |

### 6. 收藏表 (favorite)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| product_id | BIGINT | 商品ID |
| create_time | DATETIME | 创建时间 |

### 7. 系统配置表 (system_config)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| config_key | VARCHAR(50) | 配置键 |
| config_value | VARCHAR(500) | 配置值 |
| description | VARCHAR(200) | 描述 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

**配置项**：
- good_review_score: 好评加分(默认+2)
- bad_review_score: 差评扣分(默认-5)
- max_images: 最大图片数(默认6张)

## 接口设计

### 用户模块 `/api/user`
- POST `/register` - 用户注册
- POST `/login` - 用户登录
- GET `/info` - 获取用户信息
- PUT `/update` - 更新用户信息
- GET `/profile/{id}` - 查看其他用户资料

### 分类模块 `/api/category`
- GET `/list` - 获取分类列表

### 商品模块 `/api/product`
- POST `/publish` - 发布商品
- PUT `/update` - 更新商品
- DELETE `/{id}` - 删除商品
- PUT `/{id}/shelf` - 上下架
- GET `/list` - 商品列表（分页、分类筛选、搜索）
- GET `/{id}` - 商品详情
- GET `/my` - 我的发布

### 订单模块 `/api/order`
- POST `/create` - 创建订单（立即购买）
- GET `/my/sell` - 我的卖出
- GET `/my/buy` - 我的买入
- GET `/{id}` - 订单详情
- PUT `/{id}/complete` - 确认收货
- PUT `/{id}/cancel` - 取消订单
- POST `/{id}/rate` - 评价订单

### 聊天模块 `/api/chat`
- POST `/send` - 发送消息
- GET `/list` - 消息列表（会话列表）
- GET `/messages` - 获取聊天记录（与某人关于某商品）
- PUT `/read` - 标记已读
- POST `/bargain` - 发起议价
- PUT `/bargain/{id}/accept` - 接受议价
- PUT `/bargain/{id}/reject` - 拒绝议价

### 收藏模块 `/api/favorite`
- POST `/add` - 添加收藏
- DELETE `/{productId}` - 取消收藏
- GET `/my` - 我的收藏
- GET `/check/{productId}` - 检查是否已收藏

## 业务流程

### 交易流程
1. **直接购买**
   - 买家点击"立即购买" → 创建订单(pending状态)
   - 卖家和买家线下交易
   - 买家确认收货 → 订单完成(completed状态)
   - 双方互评 → 信用分变化

2. **议价购买**
   - 买家发送议价消息 → 提出期望价格
   - 卖家同意 → 系统自动创建订单(议价价格)
   - 卖家拒绝 → 买家可继续议价或直接购买

3. **取消订单**
   - pending状态可取消
   - 取消后商品恢复在售状态

### 信用分规则
- 初始分数：100分
- 好评：+2分
- 差评：-5分
- 信用分影响：
  - 90分以上：信用优秀
  - 70-89分：信用良好
  - 60-69分：信用一般
  - 60分以下：信用较差（建议谨慎交易）

### 商品状态
- `on_sale` - 在售中
- `sold` - 已售出
- `off_shelf` - 已下架

### 订单状态
- `pending` - 待完成（已下单，线下交易中）
- `completed` - 已完成
- `cancelled` - 已取消

## 技术实现

### 后端技术栈
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT认证
- Hutool工具库
- MD5密码加密

### 前端技术栈（前后端分离）
- Vue 3.4.0
- Element Plus 2.5.1
- Vite 5.0
- Pinia 2.1.7
- Axios 1.6.2

### 核心功能实现
1. **图片上传**：Base64编码或文件上传到本地目录
2. **搜索功能**：标题模糊查询、分类筛选、成色筛选、价格排序
3. **聊天功能**：定时轮询（5秒刷新），无需WebSocket
4. **议价流程**：特殊消息类型，包含价格和状态
5. **信用分计算**：评价后自动计算更新
6. **订单号生成**：时间戳 + 随机数

### 页面设计
#### 用户端
1. 首页 - 商品列表（分类、搜索）
2. 商品详情页
3. 发布商品页
4. 我的发布页
5. 我的购买页
6. 我的卖出页
7. 我的收藏页
8. 聊天页面
9. 个人中心
10. 用户资料页

#### 管理后台（简化）
1. 用户管理
2. 商品管理
3. 订单管理
4. 数据统计

## 项目特色

1. **简单实用**：功能精简但完整，适合校园场景
2. **议价功能**：支持买卖双方议价，更灵活
3. **信用体系**：简单的信用分机制，促进诚信交易
4. **在线沟通**：买卖双方可直接沟通，提高成交率
5. **轻量设计**：不涉及复杂的支付、钱包系统，适合毕设
6. **数据库简洁**：仅7张核心表，易于理解和实现

## 数据统计

- 商品总数
- 成交订单数
- 用户总数
- 今日发布数
- 分类分布（饼图）
- 成交趋势（折线图）
