# 校园快递代收管理系统 PRD

## 一、项目概述

### 1.1 项目背景
随着电商的快速发展，校园快递数量激增，传统的快递管理方式效率低下。学生取件排队时间长，快递员派送压力大，快递丢失、错领时有发生。本系统通过信息化手段，实现快递的高效入库、存储、取件管理，提升校园快递代收服务质量。

### 1.2 项目名称
校园快递代收管理系统

### 1.3 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL 8.0 + JWT
- 前端：Vue 3 + Element Plus + Axios + Pinia

### 1.4 核心功能
- 快递入库管理（扫码录入、批量导入）
- 取件码生成（短信/微信通知）
- 取件核销（扫码取件、验证码取件）
- 超期管理（超期提醒、超期收费）
- 代收点管理（多网点支持）
- 数据统计分析

## 二、用户角色

### 2.1 学生用户
- 查看待取快递列表
- 扫码取件/输入取件码取件
- 查看取件历史
- 超期费用查询
- 个人信息管理

### 2.2 快递员
- 快递入库录入
- 批量导入快递单号
- 查看库存快递
- 快递签收确认
- 退件处理

### 2.3 代收点管理员
- 快递入库管理
- 取件核销
- 超期快递处理
- 快递查询
- 代收点库存管理
- 数据统计

### 2.4 系统管理员
- 用户管理
- 代收点管理
- 快递公司管理
- 系统配置（超期规则、收费标准）
- 数据统计分析
- 操作日志查看

## 三、核心功能模块

### 3.1 用户模块

#### 3.1.1 用户注册
- 学号/工号（唯一）
- 姓名
- 手机号（用于取件通知）
- 密码
- 用户类型（学生/快递员/管理员）

#### 3.1.2 用户登录
- 学号/手机号登录
- JWT Token鉴权
- 记住登录状态

#### 3.1.3 个人信息管理
- 查看个人信息
- 修改手机号
- 修改密码
- 绑定微信（可选）

### 3.2 快递入库模块

#### 3.2.1 单个入库
- 扫描快递单号（支持主流快递公司）
- 选择快递公司
- 输入收件人手机号后4位
- 选择代收点
- 选择货架位置
- 系统自动生成取件码（6位数字）
- 发送短信通知收件人

#### 3.2.2 批量导入
- Excel模板导入
- 字段：快递单号、快递公司、收件人手机号、代收点
- 批量生成取件码
- 批量发送取件通知

#### 3.2.3 入库信息
- 快递单号
- 快递公司
- 收件人姓名（根据手机号匹配）
- 收件人手机号
- 取件码
- 代收点
- 货架位置
- 入库时间
- 入库操作员

### 3.3 取件管理模块

#### 3.3.1 取件方式
- **方式一**：扫描快递上的取件码
- **方式二**：手动输入取件码
- **方式三**：手机号+取件码验证
- 验证成功后核销

#### 3.3.2 取件流程
1. 用户到代收点
2. 出示取件码（短信/系统内查看）
3. 工作人员扫码或输入取件码
4. 系统验证并显示快递信息
5. 确认取件人身份
6. 核销快递
7. 更新快递状态为已取件

#### 3.3.3 取件核销
- 验证取件码
- 显示快递详细信息
- 确认取件操作
- 记录取件时间
- 记录取件操作员
- 如有超期费用，先收费再核销

### 3.4 快递查询模块

#### 3.4.1 用户查询
- 查看我的待取快递（状态：待取件）
- 查看取件历史
- 按时间筛选
- 快递单号搜索
- 显示快递详情（快递公司、入库时间、代收点、取件码）

#### 3.4.2 管理员查询
- 全部快递列表
- 多条件筛选：
  - 快递状态（待取件/已取件/超期/退件）
  - 快递公司
  - 代收点
  - 时间范围
  - 收件人
  - 快递单号
- 导出Excel

### 3.5 超期管理模块

#### 3.5.1 超期规则
- 免费保管期限：3天
- 超期第4-7天：1元/天
- 超期第8天及以后：2元/天
- 超期计算从入库第二天0点开始

#### 3.5.2 超期提醒
- 入库第3天发送提醒短信
- 入库第5天再次提醒
- 超期7天未取，标记为长期滞留

#### 3.5.3 超期处理
- 自动计算超期天数和费用
- 取件时显示超期费用
- 收费后才能核销
- 超期费用记录

#### 3.5.4 长期滞留处理
- 超期15天的快递标记为长期滞留
- 联系收件人
- 可选择退回寄件地址
- 或选择代为处理

### 3.6 代收点管理模块

#### 3.6.1 代收点信息
- 代收点名称（如：东区菜鸟驿站、西区快递中心）
- 位置地址
- 联系电话
- 营业时间
- 货架容量
- 当前库存量
- 负责人
- 状态（营业中/暂停服务）

#### 3.6.2 货架管理
- 货架编号
- 货架容量
- 当前使用情况
- 快递分区（按快递公司或大小）

#### 3.6.3 库存统计
- 当前待取件数量
- 今日入库数量
- 今日取件数量
- 超期快递数量
- 货架使用率

### 3.7 通知模块

#### 3.7.1 短信通知
- 快递到达通知（取件码）
- 超期提醒通知
- 长期滞留通知
- 退件通知

#### 3.7.2 站内消息
- 系统公告
- 取件提醒
- 超期提醒
- 消息已读标记

### 3.8 统计分析模块

#### 3.8.1 数据概览
- 今日入库数量
- 今日取件数量
- 当前库存数量
- 超期快递数量
- 超期费用总计

#### 3.8.2 趋势分析
- 每日入库/取件趋势图（折线图）
- 快递公司占比（饼图）
- 代收点业务量对比（柱状图）
- 平均取件时长统计

#### 3.8.3 排行榜
- 快递公司业务量排行
- 取件最快用户排行
- 超期最多用户排行
- 代收点业务量排行

### 3.9 系统配置模块

#### 3.9.1 快递公司管理
- 快递公司列表
- 添加快递公司
- 编辑快递公司信息
- 启用/禁用快递公司
- 单号识别规则配置

#### 3.9.2 收费标准配置
- 免费保管天数
- 超期收费标准（分阶梯）
- 特殊时期免费政策（如假期）

#### 3.9.3 通知模板配置
- 短信模板管理
- 通知时机配置
- 短信签名配置

## 四、数据库设计

### 4.1 用户表 (user)
```sql
id                BIGINT          主键
student_id        VARCHAR(20)     学号/工号（唯一）
username          VARCHAR(50)     用户名
password          VARCHAR(100)    密码（加密）
real_name         VARCHAR(50)     真实姓名
phone             VARCHAR(11)     手机号
role              VARCHAR(20)     角色（STUDENT/COURIER/STATION_ADMIN/ADMIN）
status            TINYINT         状态（0禁用/1正常）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.2 快递表 (express)
```sql
id                BIGINT          主键
tracking_number   VARCHAR(50)     快递单号（唯一）
express_company   VARCHAR(50)     快递公司
pickup_code       VARCHAR(10)     取件码
recipient_name    VARCHAR(50)     收件人姓名
recipient_phone   VARCHAR(11)     收件人手机号
recipient_id      BIGINT          收件人用户ID（可为空）
station_id        BIGINT          代收点ID
shelf_location    VARCHAR(20)     货架位置
status            TINYINT         状态（0待取件/1已取件/2超期/3退件/4取消）
in_time           DATETIME        入库时间
out_time          DATETIME        取件时间
in_operator_id    BIGINT          入库操作员ID
in_operator_name  VARCHAR(50)     入库操作员姓名
out_operator_id   BIGINT          取件操作员ID
out_operator_name VARCHAR(50)     取件操作员姓名
overdue_days      INT             超期天数
overdue_fee       DECIMAL(10,2)   超期费用
is_notified       TINYINT         是否已通知（0否/1是）
remark            VARCHAR(200)    备注
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.3 代收点表 (station)
```sql
id                BIGINT          主键
name              VARCHAR(100)    代收点名称
address           VARCHAR(200)    地址
contact_phone     VARCHAR(20)     联系电话
business_hours    VARCHAR(50)     营业时间
manager_id        BIGINT          负责人ID
manager_name      VARCHAR(50)     负责人姓名
shelf_capacity    INT             货架容量
current_stock     INT             当前库存
status            TINYINT         状态（0暂停/1营业中）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.4 快递公司表 (express_company)
```sql
id                BIGINT          主键
name              VARCHAR(50)     公司名称
code              VARCHAR(20)     公司代码
phone             VARCHAR(20)     客服电话
logo              VARCHAR(200)    公司Logo
tracking_url      VARCHAR(200)    物流查询网址
sort_order        INT             排序
status            TINYINT         状态（0禁用/1启用）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.5 超期记录表 (overdue_record)
```sql
id                BIGINT          主键
express_id        BIGINT          快递ID
tracking_number   VARCHAR(50)     快递单号
recipient_id      BIGINT          收件人ID
recipient_name    VARCHAR(50)     收件人姓名
overdue_days      INT             超期天数
overdue_fee       DECIMAL(10,2)   超期费用
payment_status    TINYINT         支付状态（0未支付/1已支付）
payment_time      DATETIME        支付时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.6 通知记录表 (notification)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
type              VARCHAR(20)     类型（ARRIVAL/OVERDUE/RETURN）
title             VARCHAR(100)    标题
content           TEXT            内容
express_id        BIGINT          关联快递ID
is_read           TINYINT         是否已读（0否/1是）
send_method       VARCHAR(20)     发送方式（SMS/SYSTEM）
send_status       TINYINT         发送状态（0失败/1成功）
create_time       DATETIME        创建时间
```

### 4.7 系统配置表 (system_config)
```sql
id                BIGINT          主键
config_key        VARCHAR(50)     配置键（唯一）
config_value      VARCHAR(500)    配置值
description       VARCHAR(200)    说明
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.8 操作日志表 (operation_log)
```sql
id                BIGINT          主键
operator_id       BIGINT          操作人ID
operator_name     VARCHAR(50)     操作人姓名
operation_type    VARCHAR(50)     操作类型（IN/OUT/UPDATE/DELETE）
operation_desc    VARCHAR(200)    操作描述
express_id        BIGINT          关联快递ID
ip_address        VARCHAR(50)     IP地址
create_time       DATETIME        操作时间
```

## 五、接口设计

### 5.1 认证接口

#### POST /api/auth/register
用户注册
```json
Request: {
  "studentId": "2021001",
  "username": "zhangsan",
  "password": "123456",
  "realName": "张三",
  "phone": "13800138000"
}
Response: {
  "code": 200,
  "message": "注册成功",
  "data": null
}
```

#### POST /api/auth/login
用户登录
```json
Request: {
  "username": "zhangsan",
  "password": "123456"
}
Response: {
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "id": 1,
      "username": "zhangsan",
      "realName": "张三",
      "role": "STUDENT"
    }
  }
}
```

#### GET /api/auth/info
获取当前用户信息

### 5.2 快递入库接口

#### POST /api/express/in
快递入库
```json
Request: {
  "trackingNumber": "SF1234567890",
  "expressCompany": "顺丰速运",
  "recipientPhone": "13800138000",
  "stationId": 1,
  "shelfLocation": "A-01-05"
}
Response: {
  "code": 200,
  "message": "入库成功",
  "data": {
    "pickupCode": "123456",
    "expressId": 100
  }
}
```

#### POST /api/express/batch-import
批量导入快递

#### GET /api/express/list
快递列表（分页、筛选）

#### GET /api/express/{id}
快递详情

### 5.3 取件接口

#### POST /api/express/pickup
取件核销
```json
Request: {
  "pickupCode": "123456",
  "operatorId": 2
}
Response: {
  "code": 200,
  "message": "取件成功",
  "data": {
    "trackingNumber": "SF1234567890",
    "overdueFee": 0
  }
}
```

#### POST /api/express/verify-pickup
验证取件码
```json
Request: {
  "pickupCode": "123456"
}
Response: {
  "code": 200,
  "message": "验证成功",
  "data": {
    "expressId": 100,
    "trackingNumber": "SF1234567890",
    "recipientName": "张三",
    "expressCompany": "顺丰速运",
    "overdueDays": 0,
    "overdueFee": 0
  }
}
```

#### GET /api/express/my-packages
我的快递（待取件）

#### GET /api/express/my-history
我的取件历史

### 5.4 代收点接口

#### GET /api/station/list
代收点列表

#### GET /api/station/{id}
代收点详情

#### POST /api/station
添加代收点（管理员）

#### PUT /api/station/{id}
更新代收点（管理员）

#### DELETE /api/station/{id}
删除代收点（管理员）

#### GET /api/station/{id}/stats
代收点统计

### 5.5 快递公司接口

#### GET /api/company/list
快递公司列表

#### POST /api/company
添加快递公司（管理员）

#### PUT /api/company/{id}
更新快递公司（管理员）

#### DELETE /api/company/{id}
删除快递公司（管理员）

### 5.6 超期管理接口

#### GET /api/overdue/list
超期快递列表

#### GET /api/overdue/my
我的超期快递

#### POST /api/overdue/pay
支付超期费用

#### GET /api/overdue/records
超期记录

### 5.7 通知接口

#### GET /api/notification/list
通知列表

#### GET /api/notification/unread-count
未读消息数量

#### PUT /api/notification/{id}/read
标记已读

#### PUT /api/notification/read-all
全部标记已读

### 5.8 统计接口

#### GET /api/stats/overview
数据概览
```json
Response: {
  "code": 200,
  "data": {
    "todayIn": 156,
    "todayOut": 142,
    "currentStock": 89,
    "overdueCount": 12,
    "overdueFeeTotal": 86.5
  }
}
```

#### GET /api/stats/trend
趋势分析

#### GET /api/stats/company-rank
快递公司排行

#### GET /api/stats/station-rank
代收点排行

### 5.9 用户管理接口（管理员）

#### GET /api/user/list
用户列表

#### PUT /api/user/{id}/status
修改用户状态

#### PUT /api/user/{id}/role
修改用户角色

### 5.10 系统配置接口（管理员）

#### GET /api/config/list
配置列表

#### PUT /api/config
更新配置

## 六、前端页面设计

### 6.1 学生端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单
- 忘记密码

#### 6.1.2 首页
- 待取快递数量展示
- 快速查询（输入取件码）
- 最近入库快递列表
- 代收点信息

#### 6.1.3 我的快递页
- 待取快递列表（显示取件码、入库时间、代收点）
- 取件历史
- 超期快递提醒
- 快递搜索

#### 6.1.4 快递详情页
- 快递单号
- 快递公司
- 取件码（大字号显示）
- 入库时间
- 代收点信息
- 货架位置
- 超期提醒

#### 6.1.5 通知中心页
- 消息列表
- 未读消息提醒
- 消息详情

#### 6.1.6 个人中心页
- 个人信息
- 修改手机号
- 修改密码
- 取件统计

### 6.2 快递员/代收点管理员端页面

#### 6.2.1 工作台
- 今日数据统计
- 快速入库入口
- 快速取件入口
- 待处理事项

#### 6.2.2 快递入库页
- 单个入库表单
- 批量导入入口
- 快递公司选择
- 代收点选择
- 货架位置选择
- 扫码枪支持

#### 6.2.3 取件核销页
- 取件码输入框（大字号）
- 扫码取件
- 快递信息展示
- 超期费用显示
- 核销确认

#### 6.2.4 快递管理页
- 快递列表（待取件/已取件/超期/退件）
- 多条件筛选
- 快递搜索
- 批量操作
- 导出Excel

#### 6.2.5 超期管理页
- 超期快递列表
- 超期天数排序
- 联系用户功能
- 退件处理

#### 6.2.6 库存管理页
- 货架使用情况
- 当前库存快递
- 货架分布图

### 6.3 系统管理员端页面

#### 6.3.1 数据统计页
- 数据概览卡片
- 入库/取件趋势图
- 快递公司占比饼图
- 代收点业务量柱状图
- 排行榜

#### 6.3.2 代收点管理页
- 代收点列表
- 添加/编辑代收点
- 代收点详情
- 代收点统计

#### 6.3.3 快递公司管理页
- 快递公司列表
- 添加/编辑快递公司
- 启用/禁用

#### 6.3.4 用户管理页
- 用户列表
- 用户搜索
- 角色分配
- 状态管理

#### 6.3.5 系统配置页
- 收费标准配置
- 通知模板配置
- 超期规则配置

#### 6.3.6 操作日志页
- 日志列表
- 日志搜索
- 日志导出

## 七、业务流程

### 7.1 快递入库流程
```
1. 快递员/管理员登录系统
2. 进入快递入库页面
3. 扫描或输入快递单号
4. 选择快递公司
5. 输入收件人手机号后4位
6. 选择代收点和货架位置
7. 系统验证信息
8. 生成取件码
9. 发送短信通知收件人
10. 入库成功
```

### 7.2 用户取件流程
```
1. 用户收到取件短信
2. 前往代收点
3. 出示取件码
4. 工作人员扫码或输入取件码
5. 系统验证取件码
6. 显示快递信息（单号、货架位置）
7. 工作人员取出快递
8. 核对手机号确认身份
9. 确认取件
10. 系统更新快递状态
11. 取件完成
```

### 7.3 超期处理流程
```
1. 系统每日凌晨自动计算超期天数
2. 第3天发送提醒短信
3. 第5天再次提醒
4. 超期7天标记为长期滞留
5. 用户取件时系统自动计算超期费用
6. 显示应付金额
7. 收费后核销
8. 记录超期费用
```

### 7.4 退件处理流程
```
1. 超期15天未取
2. 管理员联系收件人
3. 收件人拒收或无法联系
4. 标记为退件状态
5. 联系快递公司
6. 快递公司取回
7. 更新快递状态
```

## 八、特色功能

### 8.1 智能取件码生成
- 6位纯数字，易记易输入
- 当日不重复
- 自动避开已使用的取件码

### 8.2 快递单号智能识别
- 支持主流快递公司单号格式
- 自动识别快递公司
- 单号重复检测

### 8.3 超期自动计算
- 定时任务每日计算
- 自动发送提醒
- 分阶梯收费

### 8.4 货架智能推荐
- 根据货架使用率推荐
- 同快递公司聚集存放
- 大件小件分区

### 8.5 短信通知
- 到件通知（含取件码）
- 超期提醒
- 退件通知
- 短信模板可配置

### 8.6 数据可视化
- ECharts图表展示
- 实时数据更新
- 多维度统计分析

## 九、系统配置项

### 9.1 超期规则
```json
{
  "freeDays": 3,
  "feeRules": [
    {"startDay": 4, "endDay": 7, "feePerDay": 1.0},
    {"startDay": 8, "endDay": 999, "feePerDay": 2.0}
  ]
}
```

### 9.2 通知配置
```json
{
  "arrivalNotify": true,
  "overdueNotify": true,
  "overdueNotifyDays": [3, 5],
  "smsTemplate": "您的快递已到达{station}，取件码：{code}，请及时取件"
}
```

### 9.3 主流快递公司代码
- SF: 顺丰速运
- YTO: 圆通速递
- ZTO: 中通快递
- STO: 申通快递
- YD: 韵达快递
- JD: 京东快递
- EMS: 邮政EMS
- JT: 极兔速递
- YZPY: 邮政包裹
- CNSD: 菜鸟速递

## 十、非功能需求

### 10.1 性能要求
- 入库响应时间 < 1s
- 取件核销响应时间 < 500ms
- 支持并发入库 > 50次/秒
- 数据库查询优化（添加索引）

### 10.2 安全要求
- 密码MD5加密存储
- JWT Token有效期24小时
- 接口权限验证
- 防止SQL注入
- 敏感信息脱敏

### 10.3 可用性
- 7x24小时运行
- 数据每日备份
- 操作日志完整记录
- 异常情况报警

### 10.4 扩展性
- 支持多代收点
- 支持新增快递公司
- 配置项可灵活调整
- 预留第三方对接接口

## 十一、开发优先级

### P0（核心功能 - 第一期）
- 用户注册登录
- 快递入库（单个）
- 取件核销
- 我的快递查询
- 基础通知功能

### P1（重要功能 - 第二期）
- 批量导入快递
- 超期管理（计算、提醒、收费）
- 代收点管理
- 快递公司管理
- 数据统计

### P2（优化功能 - 第三期）
- 短信通知集成
- 高级搜索筛选
- 数据可视化图表
- 操作日志
- Excel导入导出

### P3（扩展功能 - 后续）
- 微信小程序
- 扫码枪对接
- 监控大屏
- 物流轨迹查询

## 十二、测试数据

### 12.1 初始用户
- 管理员：admin / admin123 （角色：ADMIN）
- 代收点管理员：station1 / 123456 （角色：STATION_ADMIN）
- 快递员：courier1 / 123456 （角色：COURIER）
- 学生1：student1 / 123456 （角色：STUDENT）
- 学生2：student2 / 123456 （角色：STUDENT）

### 12.2 初始代收点
- 东区菜鸟驿站（货架容量：500）
- 西区快递中心（货架容量：800）
- 北区驿站（货架容量：300）

### 12.3 快递公司
- 预置10个主流快递公司数据

### 12.4 测试快递
- 生成50条测试快递数据
- 包含各种状态（待取件、已取件、超期等）

