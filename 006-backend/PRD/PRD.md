# 校园失物招领系统 PRD

## 一、项目概述

校园失物招领系统是一个帮助师生快速发布和查找丢失物品的平台。通过分类管理、图片展示、认领验证等功能，提高失物找回效率，促进校园诚信互助文化。

## 二、核心功能模块

### 1. 失物登记
- 发布丢失物品信息（名称、分类、丢失时间、地点、描述）
- 上传物品图片（支持多张）
- 设置联系方式
- 查看自己发布的失物信息
- 编辑、删除失物信息
- 标记已找到

### 2. 招领发布
- 发布拾到物品信息（名称、分类、拾取时间、地点、描述）
- 上传物品图片（支持多张）
- 设置联系方式
- 查看自己发布的招领信息
- 编辑、删除招领信息
- 标记已归还

### 3. 物品搜索与浏览
- 按分类浏览（证件类、电子产品、书籍、钥匙、卡类、其他）
- 关键词搜索
- 筛选条件（时间范围、地点、状态）
- 失物/招领分开展示
- 图片轮播查看
- 收藏功能

### 4. 认领申请
- 提交认领申请
- 填写物品特征验证信息
- 查看认领申请状态（待审核/通过/拒绝）
- 失主/拾物者审核认领申请
- 对比验证信息
- 确认归还

### 5. 消息通知
- 认领申请通知
- 审核结果通知
- 系统消息
- 未读消息提醒
- 消息已读标记

## 三、用户角色

### 1. 普通用户
- 发布失物信息
- 发布招领信息
- 搜索浏览物品
- 提交认领申请
- 处理认领申请
- 接收消息通知

### 2. 管理员
- 所有普通用户权限
- 物品分类管理
- 不当信息审核删除
- 用户管理
- 数据统计分析
- 系统配置

## 四、数据库设计（8张表）

### 1. user（用户表）
- id（主键）
- username（用户名，唯一）
- password（密码）
- real_name（真实姓名）
- student_id（学号/工号）
- phone（手机号）
- email（邮箱）
- role（角色：user/admin）
- status（状态：正常/禁用）
- created_at（创建时间）
- updated_at（更新时间）

### 2. category（分类表）
- id（主键）
- name（分类名称）
- icon（图标）
- sort（排序）
- status（状态：启用/禁用）
- created_at（创建时间）

### 3. lost_item（失物表）
- id（主键）
- user_id（发布用户ID）
- category_id（分类ID）
- title（标题）
- description（详细描述）
- lost_time（丢失时间）
- lost_location（丢失地点）
- contact_name（联系人）
- contact_phone（联系电话）
- status（状态：寻找中/已找到）
- views（浏览次数）
- created_at（发布时间）
- updated_at（更新时间）

### 4. found_item（招领表）
- id（主键）
- user_id（发布用户ID）
- category_id（分类ID）
- title（标题）
- description（详细描述）
- found_time（拾到时间）
- found_location（拾到地点）
- contact_name（联系人）
- contact_phone（联系电话）
- status（状态：待认领/已归还）
- views（浏览次数）
- created_at（发布时间）
- updated_at（更新时间）

### 5. item_image（物品图片表）
- id（主键）
- item_type（物品类型：lost/found）
- item_id（物品ID）
- image_url（图片路径）
- sort（排序）
- created_at（上传时间）

### 6. claim（认领申请表）
- id（主键）
- item_type（物品类型：lost/found）
- item_id（物品ID）
- claimer_id（认领人ID）
- owner_id（失主/拾物者ID）
- verify_info（验证信息）
- reason（认领说明）
- status（状态：待审核/通过/拒绝）
- reply（审核回复）
- created_at（申请时间）
- updated_at（更新时间）

### 7. favorite（收藏表）
- id（主键）
- user_id（用户ID）
- item_type（物品类型：lost/found）
- item_id（物品ID）
- created_at（收藏时间）

### 8. notification（通知表）
- id（主键）
- user_id（接收用户ID）
- type（类型：claim_apply/claim_result/system）
- title（标题）
- content（内容）
- link_type（关联类型：lost/found/claim）
- link_id（关联ID）
- is_read（是否已读）
- created_at（创建时间）

## 五、接口设计

### 用户模块
- POST /api/auth/register - 注册
- POST /api/auth/login - 登录
- GET /api/user/profile - 获取个人信息
- PUT /api/user/profile - 更新个人信息
- GET /api/user/list - 用户列表（管理员）

### 分类模块
- GET /api/category/list - 分类列表
- POST /api/category - 创建分类（管理员）
- PUT /api/category/{id} - 更新分类（管理员）
- DELETE /api/category/{id} - 删除分类（管理员）

### 失物模块
- POST /api/lost - 发布失物
- GET /api/lost/list - 失物列表
- GET /api/lost/{id} - 失物详情
- PUT /api/lost/{id} - 更新失物
- DELETE /api/lost/{id} - 删除失物
- PUT /api/lost/{id}/status - 更新状态
- GET /api/lost/my - 我的失物

### 招领模块
- POST /api/found - 发布招领
- GET /api/found/list - 招领列表
- GET /api/found/{id} - 招领详情
- PUT /api/found/{id} - 更新招领
- DELETE /api/found/{id} - 删除招领
- PUT /api/found/{id}/status - 更新状态
- GET /api/found/my - 我的招领

### 图片模块
- POST /api/image/upload - 上传图片
- DELETE /api/image/{id} - 删除图片

### 认领模块
- POST /api/claim - 提交认领申请
- GET /api/claim/list - 认领列表
- GET /api/claim/{id} - 认领详情
- PUT /api/claim/{id}/status - 审核认领
- GET /api/claim/received - 收到的认领（失主/拾物者）
- GET /api/claim/sent - 发出的认领（认领人）

### 收藏模块
- POST /api/favorite - 添加收藏
- DELETE /api/favorite/{id} - 取消收藏
- GET /api/favorite/list - 我的收藏

### 通知模块
- GET /api/notification/list - 通知列表
- GET /api/notification/unread-count - 未读数量
- PUT /api/notification/{id}/read - 标记已读
- PUT /api/notification/read-all - 全部标记已读

### 统计模块（管理员）
- GET /api/stats/overview - 数据概览
- GET /api/stats/category - 分类统计
- GET /api/stats/trend - 趋势统计

## 六、技术栈

### 后端
- Spring Boot 3.x
- MySQL 8.0
- MyBatis-Plus
- JWT鉴权
- Lombok

### 前端
- Vue 3
- Element Plus
- Vue Router
- Pinia
- Axios

## 七、关键功能说明

### 1. 认领流程
1. 用户浏览失物/招领信息
2. 点击"我要认领"填写验证信息
3. 失主/拾物者收到通知
4. 审核验证信息
5. 通过后双方线下交接
6. 确认归还，更新物品状态

### 2. 图片管理
- 每个物品最多上传5张图片
- 支持jpg/png格式
- 单张图片不超过2MB
- 前端压缩后上传
- 物品删除时级联删除图片

### 3. 搜索功能
- 标题、描述关键词搜索
- 分类筛选
- 时间范围筛选
- 地点筛选
- 状态筛选
- 按时间倒序排列

### 4. 权限控制
- 只能编辑/删除自己发布的信息
- 只能审核自己物品的认领申请
- 管理员可以管理所有内容
- 禁用用户无法发布信息

## 八、非功能需求

### 1. 性能要求
- 列表页响应时间 < 500ms
- 图片加载使用懒加载
- 分页查询，每页20条

### 2. 安全要求
- 密码MD5加密
- JWT token鉴权
- 敏感信息脱敏显示
- 防止SQL注入
- 图片上传类型校验

### 3. 用户体验
- 响应式布局
- 操作成功/失败提示
- 加载状态显示
- 表单验证
- 图片预览功能

## 九、初始数据

### 分类数据
1. 证件类（身份证、学生证、校园卡等）
2. 电子产品（手机、耳机、充电宝等）
3. 书籍资料（教材、笔记本、文件等）
4. 钥匙类（宿舍钥匙、车钥匙等）
5. 卡类（银行卡、公交卡、会员卡等）
6. 生活用品（水杯、雨伞、衣物等）
7. 其他

### 测试账号
- 管理员：admin / admin123
- 普通用户1：user1 / 123456
- 普通用户2：user2 / 123456

