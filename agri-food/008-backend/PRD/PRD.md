# 智能菜谱推荐系统 PRD

## 一、项目概述

### 1.1 项目背景
现代人生活节奏快，做饭成为难题。本系统通过智能推荐算法，根据用户现有食材、口味偏好、营养需求等因素，推荐合适的菜谱，并提供营养分析、购物清单、做菜打卡等功能，帮助用户更好地安排日常饮食。

### 1.2 技术栈
- 后端：SpringBoot 3.x + MyBatis-Plus + MySQL 8.0
- 前端：jQuery + Bootstrap + ECharts + Layer

### 1.3 核心功能
- 食材管理（用户管理自己的食材库存）
- 菜谱推荐（智能推荐算法）
- 营养分析（菜谱营养成分展示）
- 购物清单（自动生成缺少的食材）
- 做菜打卡（记录做菜历史和评价）

## 二、用户角色

### 2.1 普通用户
- 管理个人食材库存
- 浏览和搜索菜谱
- 获取菜谱推荐
- 查看营养分析
- 生成购物清单
- 做菜打卡和评价
- 收藏菜谱

### 2.2 管理员
- 菜谱管理（增删改查）
- 食材库管理（全局食材数据）
- 用户管理
- 数据统计分析

## 三、功能模块详细设计

### 3.1 用户模块

#### 3.1.1 用户注册
- 用户名（唯一）
- 密码
- 昵称
- 性别
- 年龄
- 身高/体重（用于营养计算）
- 口味偏好（辣、甜、咸等）
- 饮食禁忌（过敏、不吃什么）

#### 3.1.2 用户登录
- 用户名密码登录
- Token鉴权

#### 3.1.3 个人信息管理
- 修改个人信息
- 修改口味偏好
- 修改饮食禁忌

### 3.2 食材管理模块

#### 3.2.1 全局食材库（管理员维护）
- 食材名称
- 食材分类（蔬菜、肉类、水产、调料等）
- 营养成分（每100g：热量、蛋白质、脂肪、碳水化合物、纤维素等）
- 常用单位（克、个、根、片等）
- 保质期参考

#### 3.2.2 用户食材库存
- 添加食材到库存
- 记录数量和单位
- 记录购买日期/过期日期
- 过期提醒
- 食材消耗记录

### 3.3 菜谱模块

#### 3.3.1 菜谱基本信息
- 菜谱名称
- 菜谱图片
- 菜系分类（川菜、粤菜、家常菜等）
- 难度等级（简单、中等、困难）
- 烹饪时间
- 适合人数
- 热量总计
- 菜谱描述

#### 3.3.2 菜谱食材清单
- 所需食材列表
- 每种食材的用量
- 必需/可选标识

#### 3.3.3 菜谱步骤
- 步骤序号
- 步骤描述
- 步骤图片（可选）
- 步骤时长

#### 3.3.4 菜谱操作
- 浏览菜谱列表（分页、筛选）
- 搜索菜谱（按名称、食材、菜系）
- 查看菜谱详情
- 收藏菜谱
- 评分评价

### 3.4 智能推荐模块

#### 3.4.1 推荐算法逻辑
- 基于用户库存食材匹配度（食材覆盖率高优先）
- 基于用户口味偏好
- 基于用户历史做菜记录
- 基于时间（早餐、午餐、晚餐推荐不同）
- 基于营养均衡（推荐营养互补的菜谱）
- 排除用户饮食禁忌

#### 3.4.2 推荐展示
- 今日推荐（每日推荐3-5道菜）
- 可用食材推荐（根据库存能做的菜）
- 热门菜谱
- 新手推荐（难度简单）

### 3.5 营养分析模块

#### 3.5.1 菜谱营养成分
- 根据食材用量自动计算
- 总热量
- 蛋白质、脂肪、碳水化合物
- 膳食纤维、维生素等
- 营养成分占比饼图

#### 3.5.2 每日营养统计
- 用户今日已做菜品营养汇总
- 与推荐摄入量对比
- 营养建议

### 3.6 购物清单模块

#### 3.6.1 生成购物清单
- 选择想做的菜谱
- 自动对比用户库存
- 生成缺少的食材清单
- 标注所需数量

#### 3.6.2 购物清单管理
- 查看购物清单
- 勾选已购买
- 一键加入库存
- 清空购物清单

### 3.7 做菜打卡模块

#### 3.7.1 打卡记录
- 选择菜谱打卡
- 记录制作时间
- 上传成品图片
- 记录心得体会
- 评分（味道、难度、时间）

#### 3.7.2 打卡历史
- 查看个人打卡历史
- 按时间筛选
- 统计做菜次数
- 成就系统（连续打卡、制作菜品数等）

#### 3.7.3 自动扣减库存
- 打卡后自动扣减使用的食材库存

### 3.8 管理员模块

#### 3.8.1 菜谱管理
- 添加菜谱
- 编辑菜谱
- 删除菜谱
- 菜谱审核（如果开放用户提交）

#### 3.8.2 食材库管理
- 添加全局食材
- 编辑食材信息
- 设置营养成分

#### 3.8.3 用户管理
- 用户列表
- 用户状态管理（启用/禁用）
- 用户数据统计

#### 3.8.4 数据统计
- 用户总数
- 菜谱总数
- 打卡总数
- 热门菜谱TOP10
- 用户活跃度统计

## 四、数据库设计

### 4.1 用户表 (user)
```sql
id              BIGINT          主键
username        VARCHAR(50)     用户名（唯一）
password        VARCHAR(100)    密码（加密）
nickname        VARCHAR(50)     昵称
gender          TINYINT         性别 0女 1男
age             INT             年龄
height          DECIMAL(5,2)    身高(cm)
weight          DECIMAL(5,2)    体重(kg)
taste_prefer    VARCHAR(200)    口味偏好（JSON数组）
diet_taboo      VARCHAR(200)    饮食禁忌（JSON数组）
role            VARCHAR(20)     角色 USER/ADMIN
status          TINYINT         状态 0禁用 1正常
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.2 全局食材表 (ingredient)
```sql
id              BIGINT          主键
name            VARCHAR(50)     食材名称
category        VARCHAR(20)     分类
calories        DECIMAL(8,2)    热量(kcal/100g)
protein         DECIMAL(8,2)    蛋白质(g/100g)
fat             DECIMAL(8,2)    脂肪(g/100g)
carbohydrate    DECIMAL(8,2)    碳水化合物(g/100g)
fiber           DECIMAL(8,2)    膳食纤维(g/100g)
unit            VARCHAR(20)     常用单位
shelf_life      INT             保质期(天)
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.3 用户食材库存表 (user_ingredient)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
ingredient_id   BIGINT          食材ID
quantity        DECIMAL(10,2)   数量
unit            VARCHAR(20)     单位
purchase_date   DATE            购买日期
expire_date     DATE            过期日期
status          TINYINT         状态 0已用完 1正常
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.4 菜谱表 (recipe)
```sql
id              BIGINT          主键
name            VARCHAR(100)    菜谱名称
image           VARCHAR(200)    菜谱图片
cuisine_type    VARCHAR(20)     菜系分类
difficulty      TINYINT         难度 1简单 2中等 3困难
cooking_time    INT             烹饪时间(分钟)
serving_size    INT             适合人数
total_calories  DECIMAL(10,2)   总热量
description     TEXT            描述
view_count      INT             浏览次数
collect_count   INT             收藏次数
rating_score    DECIMAL(3,2)    平均评分
rating_count    INT             评分人数
status          TINYINT         状态 0下架 1上架
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.5 菜谱食材关联表 (recipe_ingredient)
```sql
id              BIGINT          主键
recipe_id       BIGINT          菜谱ID
ingredient_id   BIGINT          食材ID
quantity        DECIMAL(10,2)   用量
unit            VARCHAR(20)     单位
is_required     TINYINT         是否必需 0可选 1必需
```

### 4.6 菜谱步骤表 (recipe_step)
```sql
id              BIGINT          主键
recipe_id       BIGINT          菜谱ID
step_number     INT             步骤序号
description     TEXT            步骤描述
image           VARCHAR(200)    步骤图片
duration        INT             步骤时长(分钟)
```

### 4.7 用户收藏表 (user_collect)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
recipe_id       BIGINT          菜谱ID
create_time     DATETIME        创建时间
```

### 4.8 做菜打卡表 (cooking_record)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
recipe_id       BIGINT          菜谱ID
image           VARCHAR(200)    成品图片
note            TEXT            心得体会
taste_rating    TINYINT         味道评分 1-5
difficulty_rating TINYINT       难度评分 1-5
time_rating     TINYINT         时间评分 1-5
create_time     DATETIME        打卡时间
```

### 4.9 购物清单表 (shopping_list)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
ingredient_id   BIGINT          食材ID
quantity        DECIMAL(10,2)   数量
unit            VARCHAR(20)     单位
status          TINYINT         状态 0未购买 1已购买
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

## 五、接口设计

### 5.1 用户接口

#### POST /api/user/register
注册

#### POST /api/user/login
登录

#### GET /api/user/info
获取当前用户信息

#### PUT /api/user/info
更新用户信息

### 5.2 食材接口

#### GET /api/ingredient/list
获取全局食材列表（分页）

#### GET /api/ingredient/{id}
获取食材详情

#### POST /api/user-ingredient/add
添加食材到库存

#### GET /api/user-ingredient/list
获取用户食材库存

#### PUT /api/user-ingredient/{id}
更新库存食材

#### DELETE /api/user-ingredient/{id}
删除库存食材

### 5.3 菜谱接口

#### GET /api/recipe/list
获取菜谱列表（分页、筛选）

#### GET /api/recipe/{id}
获取菜谱详情（含食材、步骤）

#### POST /api/recipe/collect/{id}
收藏菜谱

#### DELETE /api/recipe/collect/{id}
取消收藏

#### GET /api/recipe/collect/list
获取我的收藏

#### GET /api/recipe/search
搜索菜谱

### 5.4 推荐接口

#### GET /api/recommend/today
今日推荐

#### GET /api/recommend/by-ingredient
基于库存推荐

#### GET /api/recommend/hot
热门推荐

#### GET /api/recommend/easy
新手推荐

### 5.5 营养接口

#### GET /api/nutrition/recipe/{id}
获取菜谱营养分析

#### GET /api/nutrition/daily
获取今日营养统计

### 5.6 购物清单接口

#### POST /api/shopping/generate
生成购物清单（传入菜谱ID数组）

#### GET /api/shopping/list
获取购物清单

#### PUT /api/shopping/{id}/check
标记已购买

#### POST /api/shopping/add-to-stock
一键加入库存

#### DELETE /api/shopping/clear
清空购物清单

### 5.7 打卡接口

#### POST /api/cooking/record
做菜打卡

#### GET /api/cooking/record/list
获取打卡历史

#### GET /api/cooking/stats
获取打卡统计

### 5.8 管理员接口

#### GET /api/admin/recipe/list
管理菜谱列表

#### POST /api/admin/recipe
添加菜谱

#### PUT /api/admin/recipe/{id}
编辑菜谱

#### DELETE /api/admin/recipe/{id}
删除菜谱

#### GET /api/admin/ingredient/list
管理食材列表

#### POST /api/admin/ingredient
添加食材

#### PUT /api/admin/ingredient/{id}
编辑食材

#### DELETE /api/admin/ingredient/{id}
删除食材

#### GET /api/admin/user/list
用户列表

#### PUT /api/admin/user/{id}/status
修改用户状态

#### GET /api/admin/stats
数据统计

## 六、前端页面设计

### 6.1 用户端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单（含口味偏好选择）

#### 6.1.2 首页
- 导航栏
- 今日推荐轮播
- 热门菜谱
- 快速搜索

#### 6.1.3 菜谱列表页
- 筛选条件（菜系、难度、时间）
- 菜谱卡片展示
- 分页

#### 6.1.4 菜谱详情页
- 菜谱基本信息
- 食材清单（标注库存是否足够）
- 制作步骤
- 营养分析图表
- 收藏按钮
- 加入购物清单按钮
- 立即打卡按钮
- 评价区域

#### 6.1.5 我的食材库存页
- 食材列表
- 添加食材
- 编辑数量
- 过期提醒

#### 6.1.6 智能推荐页
- 今日推荐
- 根据库存推荐
- 新手推荐

#### 6.1.7 购物清单页
- 购物清单列表
- 勾选已购买
- 一键加入库存

#### 6.1.8 我的打卡页
- 打卡历史列表
- 打卡统计（ECharts图表）
- 成就展示

#### 6.1.9 我的收藏页
- 收藏的菜谱列表

#### 6.1.10 个人中心页
- 个人信息编辑
- 口味偏好设置
- 饮食禁忌设置

### 6.2 管理员端页面

#### 6.2.1 管理后台首页
- 数据统计卡片
- 数据图表（ECharts）

#### 6.2.2 菜谱管理页
- 菜谱列表
- 添加/编辑菜谱表单
- 食材选择器
- 步骤编辑器

#### 6.2.3 食材管理页
- 食材列表
- 添加/编辑食材表单

#### 6.2.4 用户管理页
- 用户列表
- 状态管理

## 七、前端组件库选择

- **Bootstrap 5**：响应式布局、基础组件
- **jQuery 3.x**：DOM操作、AJAX请求
- **ECharts**：数据可视化图表
- **Layer**：弹层组件（提示、确认、加载）
- **jQuery Validation**：表单验证
- **Select2**：下拉选择增强
- **Dropzone.js**：图片上传
- **Swiper**：轮播图

## 八、推荐算法设计

### 8.1 食材匹配度计算
```
匹配度 = (用户拥有的必需食材数 / 菜谱必需食材总数) * 100%
```

### 8.2 推荐评分公式
```
推荐分数 = 
  食材匹配度 * 0.4 +
  口味偏好匹配度 * 0.2 +
  历史做菜偏好 * 0.2 +
  菜谱评分 * 0.1 +
  时间适配度 * 0.1
```

### 8.3 排除规则
- 食材包含用户饮食禁忌的菜谱
- 难度过高且用户是新手
- 近3天内已做过的菜谱

## 九、特色功能

### 9.1 过期提醒
每日检查用户食材库存，提前3天提醒即将过期的食材，并推荐使用该食材的菜谱。

### 9.2 营养均衡建议
根据用户今日已做菜品的营养成分，给出晚餐建议（如：今日蛋白质不足，推荐肉类菜品）。

### 9.3 成就系统
- 新手上路：第1次打卡
- 勤劳小蜜蜂：连续7天打卡
- 美食达人：累计打卡30次
- 菜系大师：制作5个不同菜系的菜品
- 零浪费：连续30天无食材过期

## 十、开发优先级

### P0（核心功能）
- 用户注册登录
- 菜谱CRUD
- 食材管理
- 基础推荐算法
- 打卡功能

### P1（重要功能）
- 购物清单
- 营养分析
- 收藏功能
- 搜索筛选

### P2（优化功能）
- 成就系统
- 过期提醒
- 数据统计图表
- 高级推荐算法

