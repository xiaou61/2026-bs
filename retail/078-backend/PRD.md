# 网上团购系统

## 项目概述

### 项目简介
一个完整的网上团购平台，支持商家发起团购活动，用户参与拼团购买商品，实现多人拼团享受优惠价格的功能。

### 核心功能
- 商家入驻与管理
- 团购活动发起与参与
- 多级分类商品管理
- 订单全流程管理
- 用户评价系统
- 数据统计分析

### 技术栈
- 后端：Spring Boot 2.7.0 + MyBatis-Plus 3.5.2 + MySQL 8.0 + Redis + JWT
- 前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts

## 功能需求

### 1. 用户模块
- 用户注册、登录
- 个人信息管理
- 收货地址管理
- 密码修改

### 2. 商家模块
- 商家入驻申请
- 店铺信息管理
- 商家审核（管理员）

### 3. 分类模块
- 商品分类管理（二级分类）
- 分类的增删改查

### 4. 商品模块
- 商品发布与管理
- 商品上下架
- 库存管理

### 5. 团购模块
- 发起团购活动
- 设置成团人数、团购价格、时限
- 用户开团/参团
- 成团/失败自动处理

### 6. 订单模块
- 购物车管理
- 下单支付
- 订单状态流转
- 发货/收货/退款

### 7. 评价模块
- 订单评价
- 评分与评论
- 评价审核

### 8. 统计模块
- 销售额统计
- 订单量统计
- 用户增长趋势
- 热销商品排行

### 9. 系统管理
- 用户管理
- 公告管理
- 系统配置

## 技术设计

### 数据库设计

表1: user（用户表）
- id: bigint 主键
- username: varchar(50) 用户名
- password: varchar(100) 密码
- nickname: varchar(50) 昵称
- phone: varchar(20) 手机号
- email: varchar(100) 邮箱
- avatar: varchar(255) 头像
- role: int 角色(0管理员 1商家 2用户)
- status: int 状态(0禁用 1正常)
- create_time: datetime 创建时间
- update_time: datetime 更新时间

表2: merchant（商家表）
- id: bigint 主键
- user_id: bigint 用户ID
- name: varchar(100) 店铺名称
- logo: varchar(255) 店铺logo
- description: text 店铺描述
- contact: varchar(50) 联系人
- phone: varchar(20) 联系电话
- address: varchar(255) 店铺地址
- status: int 状态(0待审核 1通过 2拒绝)
- audit_remark: varchar(255) 审核备注
- create_time: datetime 创建时间
- update_time: datetime 更新时间

表3: category（分类表）
- id: bigint 主键
- parent_id: bigint 父级ID
- name: varchar(50) 分类名称
- icon: varchar(255) 图标
- sort: int 排序
- create_time: datetime 创建时间

表4: product（商品表）
- id: bigint 主键
- merchant_id: bigint 商家ID
- category_id: bigint 分类ID
- name: varchar(200) 商品名称
- cover: varchar(255) 封面图
- images: text 商品图片(JSON数组)
- description: text 商品描述
- original_price: decimal(10,2) 原价
- stock: int 库存
- sales: int 销量
- status: int 状态(0下架 1上架)
- create_time: datetime 创建时间
- update_time: datetime 更新时间

表5: group_activity（团购活动表）
- id: bigint 主键
- product_id: bigint 商品ID
- merchant_id: bigint 商家ID
- group_price: decimal(10,2) 团购价
- min_count: int 最小成团人数
- max_count: int 最大成团人数
- limit_hours: int 成团时限(小时)
- start_time: datetime 活动开始时间
- end_time: datetime 活动结束时间
- status: int 状态(0未开始 1进行中 2已结束)
- create_time: datetime 创建时间

表6: group_order（拼团表）
- id: bigint 主键
- activity_id: bigint 团购活动ID
- leader_id: bigint 团长用户ID
- current_count: int 当前人数
- target_count: int 目标人数
- status: int 状态(0拼团中 1已成团 2拼团失败)
- expire_time: datetime 过期时间
- create_time: datetime 创建时间
- update_time: datetime 更新时间

表7: orders（订单表）
- id: bigint 主键
- order_no: varchar(50) 订单编号
- user_id: bigint 用户ID
- merchant_id: bigint 商家ID
- group_order_id: bigint 拼团ID
- total_amount: decimal(10,2) 订单总额
- pay_amount: decimal(10,2) 实付金额
- address_info: varchar(500) 收货地址信息(JSON)
- status: int 状态(0待支付 1待发货 2待收货 3已完成 4已取消 5退款中 6已退款)
- pay_time: datetime 支付时间
- ship_time: datetime 发货时间
- receive_time: datetime 收货时间
- remark: varchar(255) 备注
- create_time: datetime 创建时间
- update_time: datetime 更新时间

表8: order_item（订单明细表）
- id: bigint 主键
- order_id: bigint 订单ID
- product_id: bigint 商品ID
- product_name: varchar(200) 商品名称
- product_cover: varchar(255) 商品封面
- price: decimal(10,2) 单价
- quantity: int 数量
- subtotal: decimal(10,2) 小计

表9: address（收货地址表）
- id: bigint 主键
- user_id: bigint 用户ID
- name: varchar(50) 收货人
- phone: varchar(20) 联系电话
- province: varchar(50) 省
- city: varchar(50) 市
- district: varchar(50) 区
- detail: varchar(255) 详细地址
- is_default: int 是否默认(0否 1是)
- create_time: datetime 创建时间

表10: cart（购物车表）
- id: bigint 主键
- user_id: bigint 用户ID
- product_id: bigint 商品ID
- activity_id: bigint 团购活动ID
- quantity: int 数量
- selected: int 是否选中(0否 1是)
- create_time: datetime 创建时间

表11: review（评价表）
- id: bigint 主键
- order_id: bigint 订单ID
- user_id: bigint 用户ID
- product_id: bigint 商品ID
- merchant_id: bigint 商家ID
- rating: int 评分(1-5)
- content: text 评价内容
- images: text 评价图片(JSON数组)
- status: int 状态(0待审核 1已通过 2已拒绝)
- create_time: datetime 创建时间

表12: notice（公告表）
- id: bigint 主键
- title: varchar(200) 标题
- content: text 内容
- type: int 类型(0系统公告 1活动公告)
- status: int 状态(0草稿 1发布)
- create_time: datetime 创建时间
- update_time: datetime 更新时间

### API接口设计

#### 1. 认证模块
1.1 用户注册
- POST /api/auth/register
- 参数: {username, password, nickname, phone}
- 返回: {code, message, data}

1.2 用户登录
- POST /api/auth/login
- 参数: {username, password}
- 返回: {code, message, data: {token, user}}

1.3 获取当前用户
- GET /api/auth/info
- 返回: {code, message, data: user}

1.4 修改密码
- PUT /api/auth/password
- 参数: {oldPassword, newPassword}
- 返回: {code, message}

#### 2. 用户模块
2.1 用户列表（管理员）
- GET /api/user/page
- 参数: {pageNum, pageSize, username, role, status}
- 返回: {code, message, data: {records, total}}

2.2 更新用户状态
- PUT /api/user/status/{id}
- 参数: {status}
- 返回: {code, message}

2.3 更新个人信息
- PUT /api/user/profile
- 参数: {nickname, phone, email, avatar}
- 返回: {code, message}

#### 3. 商家模块
3.1 申请入驻
- POST /api/merchant/apply
- 参数: {name, logo, description, contact, phone, address}
- 返回: {code, message}

3.2 商家列表
- GET /api/merchant/page
- 参数: {pageNum, pageSize, name, status}
- 返回: {code, message, data: {records, total}}

3.3 审核商家
- PUT /api/merchant/audit/{id}
- 参数: {status, auditRemark}
- 返回: {code, message}

3.4 获取商家信息
- GET /api/merchant/info
- 返回: {code, message, data: merchant}

3.5 更新商家信息
- PUT /api/merchant/update
- 参数: {name, logo, description, contact, phone, address}
- 返回: {code, message}

#### 4. 分类模块
4.1 分类列表
- GET /api/category/list
- 返回: {code, message, data: []}

4.2 分类树
- GET /api/category/tree
- 返回: {code, message, data: []}

4.3 添加分类
- POST /api/category/add
- 参数: {parentId, name, icon, sort}
- 返回: {code, message}

4.4 更新分类
- PUT /api/category/update
- 参数: {id, name, icon, sort}
- 返回: {code, message}

4.5 删除分类
- DELETE /api/category/delete/{id}
- 返回: {code, message}

#### 5. 商品模块
5.1 商品列表
- GET /api/product/page
- 参数: {pageNum, pageSize, categoryId, name, status, merchantId}
- 返回: {code, message, data: {records, total}}

5.2 商品详情
- GET /api/product/detail/{id}
- 返回: {code, message, data: product}

5.3 添加商品
- POST /api/product/add
- 参数: {categoryId, name, cover, images, description, originalPrice, stock}
- 返回: {code, message}

5.4 更新商品
- PUT /api/product/update
- 参数: {id, categoryId, name, cover, images, description, originalPrice, stock}
- 返回: {code, message}

5.5 商品上下架
- PUT /api/product/status/{id}
- 参数: {status}
- 返回: {code, message}

5.6 删除商品
- DELETE /api/product/delete/{id}
- 返回: {code, message}

#### 6. 团购活动模块
6.1 活动列表
- GET /api/activity/page
- 参数: {pageNum, pageSize, productId, status}
- 返回: {code, message, data: {records, total}}

6.2 活动详情
- GET /api/activity/detail/{id}
- 返回: {code, message, data: activity}

6.3 创建活动
- POST /api/activity/add
- 参数: {productId, groupPrice, minCount, maxCount, limitHours, startTime, endTime}
- 返回: {code, message}

6.4 更新活动
- PUT /api/activity/update
- 参数: {id, groupPrice, minCount, maxCount, limitHours, startTime, endTime}
- 返回: {code, message}

6.5 结束活动
- PUT /api/activity/end/{id}
- 返回: {code, message}

6.6 首页活动列表
- GET /api/activity/home
- 参数: {pageNum, pageSize, categoryId}
- 返回: {code, message, data: {records, total}}

#### 7. 拼团模块
7.1 开团
- POST /api/group/create
- 参数: {activityId, addressId}
- 返回: {code, message, data: {groupOrderId, orderId}}

7.2 参团
- POST /api/group/join
- 参数: {groupOrderId, addressId}
- 返回: {code, message, data: {orderId}}

7.3 拼团详情
- GET /api/group/detail/{id}
- 返回: {code, message, data: groupOrder}

7.4 拼团列表（用户）
- GET /api/group/my
- 参数: {pageNum, pageSize, status}
- 返回: {code, message, data: {records, total}}

7.5 进行中的拼团
- GET /api/group/ongoing/{activityId}
- 返回: {code, message, data: []}

#### 8. 购物车模块
8.1 购物车列表
- GET /api/cart/list
- 返回: {code, message, data: []}

8.2 添加购物车
- POST /api/cart/add
- 参数: {productId, activityId, quantity}
- 返回: {code, message}

8.3 更新数量
- PUT /api/cart/quantity
- 参数: {id, quantity}
- 返回: {code, message}

8.4 删除购物车
- DELETE /api/cart/delete/{id}
- 返回: {code, message}

8.5 清空购物车
- DELETE /api/cart/clear
- 返回: {code, message}

#### 9. 订单模块
9.1 创建订单
- POST /api/order/create
- 参数: {cartIds, addressId, remark}
- 返回: {code, message, data: orderNo}

9.2 订单列表
- GET /api/order/page
- 参数: {pageNum, pageSize, status, orderNo}
- 返回: {code, message, data: {records, total}}

9.3 订单详情
- GET /api/order/detail/{id}
- 返回: {code, message, data: order}

9.4 支付订单
- PUT /api/order/pay/{id}
- 返回: {code, message}

9.5 发货
- PUT /api/order/ship/{id}
- 返回: {code, message}

9.6 收货
- PUT /api/order/receive/{id}
- 返回: {code, message}

9.7 取消订单
- PUT /api/order/cancel/{id}
- 返回: {code, message}

9.8 申请退款
- PUT /api/order/refund/{id}
- 参数: {reason}
- 返回: {code, message}

9.9 处理退款
- PUT /api/order/refund/handle/{id}
- 参数: {agree, remark}
- 返回: {code, message}

#### 10. 地址模块
10.1 地址列表
- GET /api/address/list
- 返回: {code, message, data: []}

10.2 添加地址
- POST /api/address/add
- 参数: {name, phone, province, city, district, detail, isDefault}
- 返回: {code, message}

10.3 更新地址
- PUT /api/address/update
- 参数: {id, name, phone, province, city, district, detail, isDefault}
- 返回: {code, message}

10.4 删除地址
- DELETE /api/address/delete/{id}
- 返回: {code, message}

10.5 设为默认
- PUT /api/address/default/{id}
- 返回: {code, message}

#### 11. 评价模块
11.1 提交评价
- POST /api/review/add
- 参数: {orderId, rating, content, images}
- 返回: {code, message}

11.2 评价列表
- GET /api/review/page
- 参数: {pageNum, pageSize, productId, merchantId, status}
- 返回: {code, message, data: {records, total}}

11.3 审核评价
- PUT /api/review/audit/{id}
- 参数: {status}
- 返回: {code, message}

11.4 商品评价
- GET /api/review/product/{productId}
- 参数: {pageNum, pageSize}
- 返回: {code, message, data: {records, total}}

#### 12. 公告模块
12.1 公告列表
- GET /api/notice/page
- 参数: {pageNum, pageSize, type, status}
- 返回: {code, message, data: {records, total}}

12.2 公告详情
- GET /api/notice/detail/{id}
- 返回: {code, message, data: notice}

12.3 添加公告
- POST /api/notice/add
- 参数: {title, content, type, status}
- 返回: {code, message}

12.4 更新公告
- PUT /api/notice/update
- 参数: {id, title, content, type, status}
- 返回: {code, message}

12.5 删除公告
- DELETE /api/notice/delete/{id}
- 返回: {code, message}

12.6 前台公告
- GET /api/notice/front
- 返回: {code, message, data: []}

#### 13. 统计模块
13.1 概览数据
- GET /api/stats/overview
- 返回: {code, message, data: {userCount, merchantCount, orderCount, totalAmount}}

13.2 销售趋势
- GET /api/stats/sales
- 参数: {days}
- 返回: {code, message, data: [{date, amount, count}]}

13.3 热销商品
- GET /api/stats/hot-products
- 参数: {limit}
- 返回: {code, message, data: []}

13.4 商家销售统计
- GET /api/stats/merchant
- 参数: {days}
- 返回: {code, message, data: {}}

### 项目结构

后端:
```
078-backend/
├── sql/
│   └── init.sql
├── pom.xml
└── src/main/
    ├── java/com/groupbuy/
    │   ├── GroupBuyApplication.java
    │   ├── common/
    │   │   ├── Result.java
    │   │   ├── BusinessException.java
    │   │   └── GlobalExceptionHandler.java
    │   ├── config/
    │   │   ├── JwtInterceptor.java
    │   │   ├── WebMvcConfig.java
    │   │   ├── MybatisPlusConfig.java
    │   │   └── RedisConfig.java
    │   ├── utils/
    │   │   └── JwtUtils.java
    │   ├── entity/
    │   │   ├── User.java
    │   │   ├── Merchant.java
    │   │   ├── Category.java
    │   │   ├── Product.java
    │   │   ├── GroupActivity.java
    │   │   ├── GroupOrder.java
    │   │   ├── Orders.java
    │   │   ├── OrderItem.java
    │   │   ├── Address.java
    │   │   ├── Cart.java
    │   │   ├── Review.java
    │   │   └── Notice.java
    │   ├── mapper/
    │   ├── service/
    │   └── controller/
    └── resources/
        └── application.yml
```

前端:
```
078-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Register.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── user/
        │   └── List.vue
        ├── merchant/
        │   ├── List.vue
        │   ├── Apply.vue
        │   └── Info.vue
        ├── category/
        │   └── List.vue
        ├── product/
        │   ├── List.vue
        │   └── Detail.vue
        ├── activity/
        │   ├── List.vue
        │   └── Detail.vue
        ├── group/
        │   └── List.vue
        ├── order/
        │   ├── List.vue
        │   └── Detail.vue
        ├── cart/
        │   └── Index.vue
        ├── address/
        │   └── List.vue
        ├── review/
        │   └── List.vue
        ├── notice/
        │   └── List.vue
        ├── profile/
        │   └── Index.vue
        └── home/
            └── Index.vue
```

## 用户角色

- 管理员(role=0): 
  - 用户管理、商家审核
  - 分类管理、公告管理
  - 评价审核、数据统计
  - 查看所有订单

- 商家(role=1): 
  - 店铺信息管理
  - 商品管理、团购活动管理
  - 订单管理（自己店铺）
  - 销售统计

- 用户(role=2): 
  - 浏览商品、参与团购
  - 购物车管理、下单支付
  - 地址管理、订单管理
  - 提交评价

## 默认账号
- admin/123456 (管理员)
- merchant/123456 (商家-已审核)
- user/123456 (普通用户)
