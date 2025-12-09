# 线上理发预约系统 PRD

## 一、项目概述

### 1.1 项目背景
传统理发店普遍存在排队等待时间长、理发师资源分配不均、会员管理混乱等问题。本系统旨在通过线上预约、智能排班、会员管理等功能，提升理发店运营效率和用户体验。

### 1.2 项目目标
- 实现线上预约功能，减少客户到店等待时间
- 提供理发师展示和选择功能，提升服务质量
- 建立会员积分体系，增强用户粘性
- 实现门店管理和数据统计，辅助经营决策

### 1.3 目标用户
- **顾客**：需要理发服务的普通用户
- **理发师**：提供理发服务的专业人员
- **门店管理员**：负责门店运营和数据管理
- **系统管理员**：负责平台整体管理

---

## 二、技术架构

### 2.1 技术栈

**后端**
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT 0.12.3
- Hutool工具库

**前端（一体化集成在resources）**
- Vue 3.4.0
- Element Plus 2.5.1
- Axios 1.6.2
- Vue Router 4.2.5
- Vite 5.0

### 2.2 架构模式
- 前后端一体化部署（Vue打包后放入resources/static）
- RESTful API设计
- JWT无状态认证
- MVC三层架构

---

## 三、功能需求

### 3.1 用户系统

#### 3.1.1 用户注册登录
- 手机号注册/登录
- 密码加密存储（BCrypt）
- JWT Token认证
- 个人信息管理（头像、昵称、性别）

#### 3.1.2 会员体系
- 会员等级：普通会员、银卡会员、金卡会员、钻石会员
- 积分规则：
  - 消费1元获得1积分
  - 签到+5积分/天
  - 评价+10积分/次
- 会员权益：
  - 普通会员：无折扣（0-99积分）
  - 银卡会员：9.5折（100-499积分）
  - 金卡会员：9折（500-1999积分）
  - 钻石会员：8.5折（2000+积分）

### 3.2 门店管理

#### 3.2.1 门店信息
- 门店名称、地址、联系方式
- 营业时间设置（开始时间、结束时间）
- 门店照片（环境展示）
- 门店介绍
- 地图定位

#### 3.2.2 门店列表
- 按距离排序
- 按评分排序
- 关键词搜索
- 区域筛选

### 3.3 理发师管理

#### 3.3.1 理发师信息
- 基本信息：姓名、性别、工号、照片
- 专业信息：技能标签、从业年限、擅长风格
- 服务评价：评分、评价数量
- 工作状态：在岗/休息/请假

#### 3.3.2 理发师排班
- 每日排班设置
- 工作时间段设置（如9:00-12:00, 14:00-18:00）
- 休息日设置
- 预约时段管理（每30分钟一个时段）

#### 3.3.3 理发师展示
- 理发师列表（按评分/人气排序）
- 理发师详情页
- 服务案例展示（作品照片）
- 用户评价列表

### 3.4 服务项目管理

#### 3.4.1 项目分类
- 男士理发
- 女士理发
- 烫染护理
- 儿童理发
- 造型设计

#### 3.4.2 项目信息
- 项目名称、价格、时长
- 项目描述、效果图
- 推荐理发师
- 所属门店

#### 3.4.3 套餐管理
- 组合套餐（如洗剪吹套餐）
- 套餐优惠价
- 套餐有效期

### 3.5 预约系统

#### 3.5.1 预约流程
1. 选择门店
2. 选择服务项目
3. 选择理发师（可选：随机分配）
4. 选择预约日期
5. 选择预约时段（显示已预约/可预约）
6. 确认预约信息
7. 提交预约

#### 3.5.2 预约规则
- 最多提前7天预约
- 每个用户同时最多3个未完成预约
- 预约开始前2小时可取消
- 超过预约时间15分钟未到店自动取消

#### 3.5.3 预约状态管理
- **待确认**：用户提交预约，等待门店确认
- **已确认**：门店确认预约，等待服务
- **服务中**：顾客到店，开始服务
- **已完成**：服务完成，等待评价
- **已取消**：用户/门店取消预约
- **超时取消**：超时未到店自动取消

#### 3.5.4 预约管理
- 我的预约列表（全部/待确认/已确认/已完成）
- 预约详情查看
- 预约取消功能
- 预约提醒（开始前1小时提醒）

### 3.6 订单支付

#### 3.6.1 订单创建
- 服务完成后自动生成订单
- 订单信息：门店、理发师、服务项目、原价、折扣、实付金额
- 自动计算会员折扣

#### 3.6.2 支付方式
- 余额支付（模拟）
- 到店支付（线下支付标记）

#### 3.6.3 余额管理
- 余额充值（模拟充值）
- 充值记录查询
- 消费记录查询
- 余额变动明细

### 3.7 评价系统

#### 3.7.1 评价对象
- 理发师评价（服务态度、技术水平、环境卫生）
- 门店评价（整体满意度）

#### 3.7.2 评价内容
- 5星评分（1-5星）
- 文字评价
- 图片上传（服务效果照片）
- 评价标签（手艺好、态度好、环境佳等）

#### 3.7.3 评价展示
- 理发师详情页展示评价
- 门店详情页展示评价
- 评价统计（好评率、平均分）

### 3.8 消息通知

#### 3.8.1 通知类型
- 预约确认通知
- 预约提醒（开始前1小时）
- 预约取消通知
- 积分变动通知
- 系统公告

#### 3.8.2 通知方式
- 站内消息（消息列表）
- 未读消息提醒（红点）

### 3.9 数据统计（管理端）

#### 3.9.1 营业概览
- 今日预约数
- 今日完成数
- 今日营业额
- 本月累计营业额

#### 3.9.2 数据分析
- 预约趋势图（近7天/近30天）
- 服务项目占比（饼图）
- 理发师业绩排行
- 会员增长趋势
- 热门时段分析

#### 3.9.3 报表导出
- 预约记录导出（Excel）
- 订单记录导出
- 理发师业绩报表

---

## 四、数据库设计

### 4.1 核心数据表

#### 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| phone | varchar(11) | 手机号（登录账号） |
| password | varchar(255) | 密码（BCrypt加密） |
| nickname | varchar(50) | 昵称 |
| avatar | varchar(255) | 头像URL |
| gender | tinyint | 性别（0女1男） |
| points | int | 积分 |
| level | varchar(20) | 会员等级 |
| balance | decimal(10,2) | 余额 |
| created_at | datetime | 创建时间 |

#### 门店表 (store)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(100) | 门店名称 |
| address | varchar(255) | 详细地址 |
| phone | varchar(20) | 联系电话 |
| open_time | time | 营业开始时间 |
| close_time | time | 营业结束时间 |
| intro | text | 门店介绍 |
| images | text | 门店图片（JSON数组） |
| rating | decimal(3,2) | 评分 |
| longitude | decimal(10,7) | 经度 |
| latitude | decimal(10,7) | 纬度 |
| status | tinyint | 状态（0停业1营业） |
| created_at | datetime | 创建时间 |

#### 理发师表 (hairdresser)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| store_id | bigint | 所属门店ID |
| name | varchar(50) | 姓名 |
| gender | tinyint | 性别 |
| work_no | varchar(20) | 工号 |
| avatar | varchar(255) | 照片 |
| skills | varchar(255) | 技能标签（JSON数组） |
| work_years | int | 从业年限 |
| intro | text | 个人简介 |
| rating | decimal(3,2) | 评分 |
| status | tinyint | 状态（0休息1在岗2请假） |
| created_at | datetime | 创建时间 |

#### 服务项目表 (service)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| store_id | bigint | 所属门店ID |
| category | varchar(20) | 分类 |
| name | varchar(100) | 项目名称 |
| price | decimal(10,2) | 价格 |
| duration | int | 时长（分钟） |
| description | text | 描述 |
| image | varchar(255) | 效果图 |
| status | tinyint | 状态（0下架1上架） |
| created_at | datetime | 创建时间 |

#### 预约表 (appointment)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| store_id | bigint | 门店ID |
| hairdresser_id | bigint | 理发师ID |
| service_id | bigint | 服务项目ID |
| appointment_date | date | 预约日期 |
| appointment_time | time | 预约时间 |
| status | varchar(20) | 状态 |
| cancel_reason | varchar(255) | 取消原因 |
| created_at | datetime | 创建时间 |

#### 订单表 (orders)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| order_no | varchar(32) | 订单号 |
| appointment_id | bigint | 预约ID |
| user_id | bigint | 用户ID |
| store_id | bigint | 门店ID |
| hairdresser_id | bigint | 理发师ID |
| service_name | varchar(100) | 服务名称 |
| original_price | decimal(10,2) | 原价 |
| discount | decimal(3,2) | 折扣 |
| actual_price | decimal(10,2) | 实付金额 |
| pay_type | varchar(20) | 支付方式 |
| pay_status | tinyint | 支付状态（0未支付1已支付） |
| pay_time | datetime | 支付时间 |
| created_at | datetime | 创建时间 |

#### 评价表 (review)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| order_id | bigint | 订单ID |
| user_id | bigint | 用户ID |
| hairdresser_id | bigint | 理发师ID |
| store_id | bigint | 门店ID |
| rating | tinyint | 评分（1-5） |
| content | text | 评价内容 |
| images | text | 评价图片（JSON数组） |
| tags | varchar(255) | 评价标签（JSON数组） |
| created_at | datetime | 创建时间 |

#### 排班表 (schedule)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| hairdresser_id | bigint | 理发师ID |
| work_date | date | 工作日期 |
| start_time | time | 开始时间 |
| end_time | time | 结束时间 |
| status | tinyint | 状态（0休息1上班） |
| created_at | datetime | 创建时间 |

#### 积分记录表 (points_log)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| points | int | 积分变动（正数增加，负数减少） |
| reason | varchar(100) | 变动原因 |
| created_at | datetime | 创建时间 |

#### 余额记录表 (balance_log)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| amount | decimal(10,2) | 金额变动 |
| type | varchar(20) | 类型（充值/消费） |
| reason | varchar(100) | 变动原因 |
| created_at | datetime | 创建时间 |

#### 消息通知表 (notification)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| type | varchar(20) | 类型 |
| title | varchar(100) | 标题 |
| content | text | 内容 |
| is_read | tinyint | 是否已读（0未读1已读） |
| created_at | datetime | 创建时间 |

---

## 五、接口设计

### 5.1 用户接口

#### 5.1.1 用户认证
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/auth/info` - 获取当前用户信息
- `PUT /api/auth/profile` - 更新个人信息

#### 5.1.2 会员管理
- `GET /api/user/points` - 获取积分信息
- `GET /api/user/points/log` - 积分记录
- `GET /api/user/balance` - 获取余额信息
- `POST /api/user/balance/recharge` - 余额充值
- `GET /api/user/balance/log` - 余额记录

### 5.2 门店接口
- `GET /api/store/list` - 门店列表
- `GET /api/store/{id}` - 门店详情
- `GET /api/store/{id}/hairdressers` - 门店理发师列表
- `GET /api/store/{id}/services` - 门店服务项目列表

### 5.3 理发师接口
- `GET /api/hairdresser/list` - 理发师列表
- `GET /api/hairdresser/{id}` - 理发师详情
- `GET /api/hairdresser/{id}/reviews` - 理发师评价列表
- `GET /api/hairdresser/{id}/schedule` - 理发师排班
- `GET /api/hairdresser/{id}/available-times` - 可预约时段

### 5.4 服务项目接口
- `GET /api/service/list` - 服务项目列表
- `GET /api/service/{id}` - 服务项目详情
- `GET /api/service/categories` - 服务分类列表

### 5.5 预约接口
- `POST /api/appointment/create` - 创建预约
- `GET /api/appointment/my-list` - 我的预约列表
- `GET /api/appointment/{id}` - 预约详情
- `PUT /api/appointment/{id}/cancel` - 取消预约
- `GET /api/appointment/check-available` - 检查时段是否可预约

### 5.6 订单接口
- `GET /api/order/list` - 订单列表
- `GET /api/order/{id}` - 订单详情
- `POST /api/order/{id}/pay` - 订单支付

### 5.7 评价接口
- `POST /api/review/create` - 创建评价
- `GET /api/review/my-list` - 我的评价列表
- `GET /api/review/hairdresser/{id}` - 理发师评价
- `GET /api/review/store/{id}` - 门店评价

### 5.8 通知接口
- `GET /api/notification/list` - 消息列表
- `GET /api/notification/unread-count` - 未读消息数
- `PUT /api/notification/{id}/read` - 标记已读
- `PUT /api/notification/read-all` - 全部标记已读

### 5.9 管理端接口
- `GET /api/admin/stats/overview` - 营业概览
- `GET /api/admin/stats/trend` - 预约趋势
- `GET /api/admin/appointment/list` - 预约管理列表
- `PUT /api/admin/appointment/{id}/confirm` - 确认预约
- `GET /api/admin/hairdresser/list` - 理发师管理
- `PUT /api/admin/hairdresser/{id}/status` - 更新理发师状态
- `GET /api/admin/service/list` - 服务项目管理
- `POST /api/admin/service/create` - 添加服务项目

---

## 六、非功能性需求

### 6.1 性能需求
- 接口响应时间 < 500ms
- 支持并发用户数 > 1000
- 数据库查询优化（索引、分页）

### 6.2 安全需求
- 密码BCrypt加密存储
- JWT Token认证
- 接口权限控制
- SQL注入防护
- XSS攻击防护

### 6.3 可用性需求
- 系统可用性 > 99%
- 定时任务自动处理超时预约
- 异常日志记录

### 6.4 兼容性需求
- 浏览器兼容：Chrome、Firefox、Safari、Edge
- 移动端适配：响应式设计

---

## 七、项目亮点

### 7.1 业务亮点
- **智能预约系统**：可视化选择理发师和时段，避免排队等待
- **会员积分体系**：四级会员等级，8.5折-无折扣差异化权益
- **理发师展示**：作品案例、用户评价、技能标签，帮助用户选择
- **灵活排班管理**：理发师自主设置工作时间，提升资源利用率
- **双向评价机制**：顾客评价服务，理发师评价顾客（未实现）
- **数据可视化统计**：营业趋势、项目占比、业绩排行辅助决策

### 7.2 技术亮点
- **前后端一体化**：Vue打包后集成到Spring Boot，单jar包部署
- **RESTful API设计**：统一接口规范，清晰的响应格式
- **JWT无状态认证**：Token认证，支持分布式扩展
- **定时任务自动化**：自动取消超时预约，自动计算会员等级
- **预约冲突检测**：防止同一时段重复预约
- **订单状态机流转**：完整的预约→服务→支付→评价流程

---

## 八、开发计划

### 8.1 第一阶段：基础功能（Week 1-2）
- 项目初始化
- 数据库设计与创建
- 用户注册登录
- 门店、理发师、服务项目管理

### 8.2 第二阶段：核心功能（Week 3-4）
- 预约系统实现
- 订单支付流程
- 评价系统
- 会员积分体系

### 8.3 第三阶段：管理端与优化（Week 5-6）
- 管理端数据统计
- 消息通知系统
- 定时任务
- 前端优化与美化

### 8.4 第四阶段：测试与部署（Week 7）
- 功能测试
- 性能优化
- 部署文档编写
- 项目验收

---

## 九、验收标准

### 9.1 功能验收
- [ ] 用户可以注册登录并管理个人信息
- [ ] 用户可以浏览门店、理发师、服务项目
- [ ] 用户可以选择理发师和时段进行预约
- [ ] 用户可以查看和管理自己的预约
- [ ] 服务完成后可以进行评价
- [ ] 会员积分自动累积和等级升级
- [ ] 管理员可以查看数据统计
- [ ] 预约超时自动取消

### 9.2 性能验收
- [ ] 接口平均响应时间 < 500ms
- [ ] 支持100+并发用户正常使用
- [ ] 数据库查询优化完成

### 9.3 安全验收
- [ ] 密码加密存储
- [ ] Token认证正常
- [ ] 接口权限控制有效
- [ ] 无SQL注入漏洞

---

## 十、风险评估

### 10.1 技术风险
- **风险**：前后端一体化部署可能导致资源路径问题
- **应对**：使用相对路径，配置正确的静态资源映射

### 10.2 业务风险
- **风险**：预约冲突和超时处理逻辑复杂
- **应对**：设计完善的预约规则和状态机流转

### 10.3 时间风险
- **风险**：功能较多，开发时间可能不足
- **应对**：优先实现核心功能，次要功能后续迭代

---

**文档版本**：v1.0  
**编写日期**：2025-11-27  
**负责人**：开发团队
