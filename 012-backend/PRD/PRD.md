# 在线协作白板与笔记系统 PRD

## 一、项目概述

### 1.1 项目背景
随着远程办公和在线教育的普及，团队协作工具成为刚需。学生和职场人士需要一个集白板绘图、思维导图、笔记管理于一体的协作平台，支持实时多人编辑、知识沉淀和团队协作。本系统旨在打造一个轻量级的在线协作平台，提供白板绘图、Markdown笔记、知识库管理、团队协作等功能。

### 1.2 项目名称
在线协作白板与笔记系统（Collab Board & Notes）

### 1.3 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL 8.0 + JWT + Redis + WebSocket
- 前端：Vue 3 + Element Plus + Fabric.js / Konva.js + Markdown编辑器 + Socket.io + Axios + Pinia
- 实时通信：WebSocket
- 文件存储：本地存储 / 阿里云OSS（可选）

### 1.4 核心功能
- 在线白板（自由绘图、形状工具、文本标注、图片插入）
- 思维导图（节点创建、层级关系、样式定制）
- Markdown笔记（富文本编辑、代码高亮、图片上传）
- 实时协作（多人同时编辑、光标显示、操作同步）
- 知识库管理（文件夹分类、文档树结构、版本历史）
- 团队空间（团队创建、成员管理、权限控制）
- 模板市场（白板模板、笔记模板、分享导出）

## 二、用户角色

### 2.1 普通用户
- 注册登录、个人空间
- 创建白板、思维导图、笔记
- 实时协作编辑
- 创建团队、加入团队
- 分享文档、导出文档
- 使用模板创建
- 版本历史查看

### 2.2 团队管理员
- 团队设置管理
- 成员邀请与移除
- 权限分配
- 团队文档管理
- 团队空间统计
- 团队模板管理

### 2.3 系统管理员
- 用户管理（查看、禁用）
- 文档管理（查看、删除）
- 团队管理（查看、解散）
- 模板审核
- 数据统计（用户数、文档数、活跃度）
- 系统配置

## 三、核心功能模块

### 3.1 用户模块

#### 3.1.1 用户注册
- 邮箱/手机号
- 密码
- 昵称
- 验证码

#### 3.1.2 用户登录
- 邮箱/手机号登录
- JWT Token鉴权
- 记住登录状态
- 第三方登录（可选）

#### 3.1.3 个人信息
- 基本信息（头像、昵称、签名）
- 联系方式（邮箱、手机）
- 统计数据（文档数、协作次数、团队数）
- 修改密码

#### 3.1.4 个人空间
- 我的文档（白板、思维导图、笔记）
- 最近访问
- 收藏文档
- 星标文档
- 回收站

### 3.2 白板模块

#### 3.2.1 白板画布
- 无限画布（支持缩放、平移）
- 网格背景（可选）
- 画布尺寸设置
- 背景颜色/图片

#### 3.2.2 绘图工具
- 选择工具（移动、缩放、旋转）
- 画笔工具（自由绘制、粗细、颜色）
- 橡皮擦工具
- 形状工具（矩形、圆形、三角形、直线、箭头）
- 文本工具（文字标注、字体、大小、颜色）
- 图片工具（上传图片、拖拽调整）
- 便签工具（彩色便签）

#### 3.2.3 编辑操作
- 撤销/重做
- 复制/粘贴
- 删除
- 图层管理（上移、下移、置顶、置底）
- 对齐工具（左对齐、右对齐、居中、分布）
- 组合/取消组合
- 锁定/解锁元素

#### 3.2.4 样式设置
- 填充颜色
- 边框颜色
- 边框粗细
- 透明度
- 阴影效果
- 字体样式

#### 3.2.5 白板功能
- 导出图片（PNG、JPG、SVG）
- 导出PDF
- 分享链接（公开/加密）
- 演示模式
- 全屏模式
- 缩略图导航

### 3.3 思维导图模块

#### 3.3.1 节点操作
- 创建根节点
- 添加子节点
- 添加兄弟节点
- 删除节点
- 编辑节点文本
- 拖拽移动节点

#### 3.3.2 节点样式
- 节点形状（矩形、圆角矩形、圆形、菱形）
- 节点颜色
- 文字样式
- 连线样式（直线、曲线、折线）
- 图标添加

#### 3.3.3 布局模式
- 思维导图布局（中心放射）
- 组织架构布局（树形）
- 鱼骨图布局
- 逻辑图布局
- 自由布局

#### 3.3.4 思维导图功能
- 展开/折叠
- 自动布局
- 导出图片
- 导出Markdown大纲
- 分享链接
- 演示模式

### 3.4 笔记模块

#### 3.4.1 Markdown编辑器
- 实时预览（分屏/全屏）
- 语法高亮
- 代码块（多语言）
- 表格支持
- 任务列表
- 数学公式（LaTeX）
- 图片上传
- 文件附件

#### 3.4.2 编辑功能
- 工具栏快捷操作
- 快捷键支持
- 自动保存
- 版本历史
- 字数统计
- 大纲目录

#### 3.4.3 笔记模板
- 会议纪要模板
- 日报周报模板
- 项目方案模板
- 学习笔记模板
- 读书笔记模板
- 自定义模板

#### 3.4.4 笔记功能
- 导出Markdown
- 导出HTML
- 导出PDF
- 导出Word
- 分享链接
- 打印

### 3.5 实时协作模块

#### 3.5.1 多人编辑
- 实时同步操作
- 光标位置显示（多人）
- 用户在线状态
- 用户头像显示
- 操作冲突解决
- 协作成员列表

#### 3.5.2 WebSocket通信
- 建立WebSocket连接
- 操作广播
- 增量更新
- 断线重连
- 心跳检测

#### 3.5.3 协作权限
- 所有者（全部权限）
- 编辑者（可编辑）
- 查看者（只读）

#### 3.5.4 协作功能
- 邀请协作（链接/邮箱）
- 移除协作者
- 权限变更
- 协作历史记录
- 操作日志

### 3.6 知识库管理模块

#### 3.6.1 文件夹结构
- 创建文件夹
- 文件夹嵌套（树形结构）
- 文件夹重命名
- 文件夹移动
- 文件夹删除

#### 3.6.2 文档管理
- 创建文档（白板/思维导图/笔记）
- 文档重命名
- 文档移动
- 文档复制
- 文档删除（回收站）
- 文档恢复
- 文档永久删除

#### 3.6.3 文档属性
- 文档标题
- 文档类型
- 创建人
- 创建时间
- 修改时间
- 文档标签
- 文档描述

#### 3.6.4 搜索功能
- 全文搜索
- 标签筛选
- 类型筛选
- 时间筛选
- 创建人筛选
- 搜索历史

#### 3.6.5 版本历史
- 自动保存版本
- 版本列表
- 版本对比
- 版本恢复
- 版本命名

### 3.7 团队协作模块

#### 3.7.1 团队管理
- 创建团队
- 团队信息（名称、描述、头像）
- 解散团队
- 退出团队

#### 3.7.2 成员管理
- 邀请成员（邮箱/链接）
- 成员列表
- 成员角色（管理员/成员）
- 移除成员
- 成员权限设置

#### 3.7.3 团队空间
- 团队文档库
- 团队白板
- 团队模板
- 共享文档
- 团队活动动态

#### 3.7.4 权限控制
- 团队级权限（创建文档、邀请成员）
- 文档级权限（查看、编辑、管理）
- 文件夹级权限（继承/自定义）

### 3.8 模板市场模块

#### 3.8.1 模板分类
- 白板模板（流程图、UML图、架构图）
- 思维导图模板（项目规划、学习计划）
- 笔记模板（会议、日报、方案）
- 自定义模板

#### 3.8.2 模板功能
- 浏览模板
- 使用模板创建
- 保存为模板
- 分享模板
- 模板收藏
- 模板评分

#### 3.8.3 模板管理
- 我的模板
- 团队模板
- 公开模板
- 模板编辑
- 模板删除

### 3.9 分享与导出模块

#### 3.9.1 分享方式
- 生成分享链接
- 设置访问权限（公开/密码/成员）
- 设置有效期
- 复制链接
- 二维码分享

#### 3.9.2 导出格式
- 白板导出：PNG、JPG、SVG、PDF
- 思维导图导出：PNG、PDF、Markdown
- 笔记导出：Markdown、HTML、PDF、Word

#### 3.9.3 导入功能
- 导入Markdown文件
- 导入图片（白板）
- 导入思维导图（XMind、MindNode）

### 3.10 消息通知模块

#### 3.10.1 通知类型
- 协作邀请通知
- @提及通知
- 文档分享通知
- 团队邀请通知
- 系统通知

#### 3.10.2 通知方式
- 站内通知
- 邮件通知（可选）
- 浏览器推送（可选）

#### 3.10.3 通知管理
- 通知列表
- 未读数量
- 标记已读
- 通知设置

### 3.11 数据统计模块

#### 3.11.1 个人统计
- 文档总数
- 协作次数
- 最活跃文档
- 使用时长统计
- 创建趋势图

#### 3.11.2 团队统计
- 团队成员数
- 团队文档数
- 协作活跃度
- 成员贡献排行
- 团队活动日志

#### 3.11.3 系统统计（管理员）
- 用户总数
- 文档总数
- 团队总数
- 日活跃用户（DAU）
- 月活跃用户（MAU）
- 存储使用量
- 热门模板TOP10

## 四、数据库设计

### 4.1 用户表 (user)
```sql
id                BIGINT          主键
username          VARCHAR(50)     用户名（唯一）
password          VARCHAR(100)    密码（加密）
nickname          VARCHAR(50)     昵称
avatar            VARCHAR(200)    头像URL
email             VARCHAR(100)    邮箱
phone             VARCHAR(20)     手机号
signature         VARCHAR(200)    个性签名
doc_count         INT             文档数
collab_count      INT             协作次数
team_count        INT             团队数
role              VARCHAR(20)     角色（USER/ADMIN）
status            TINYINT         状态（0禁用/1正常）
last_login_time   DATETIME        最后登录时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.2 文档表 (document)
```sql
id                BIGINT          主键
user_id           BIGINT          创建者ID
team_id           BIGINT          团队ID（0表示个人）
folder_id         BIGINT          文件夹ID（0表示根目录）
title             VARCHAR(200)    文档标题
doc_type          VARCHAR(20)     文档类型（BOARD/MINDMAP/NOTE）
content           LONGTEXT        文档内容（JSON）
cover_url         VARCHAR(200)    封面URL
description       TEXT            文档描述
tags              VARCHAR(200)    标签（逗号分隔）
view_count        INT             查看次数
edit_count        INT             编辑次数
collab_count      INT             协作人数
is_public         TINYINT         是否公开（0私密/1公开）
is_template       TINYINT         是否模板（0否/1是）
is_starred        TINYINT         是否星标（0否/1是）
share_link        VARCHAR(100)    分享链接
share_password    VARCHAR(20)     分享密码
share_expire_time DATETIME        分享过期时间
status            TINYINT         状态（0草稿/1正常/2已删除）
delete_time       DATETIME        删除时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.3 文件夹表 (folder)
```sql
id                BIGINT          主键
user_id           BIGINT          创建者ID
team_id           BIGINT          团队ID（0表示个人）
parent_id         BIGINT          父文件夹ID（0表示根目录）
folder_name       VARCHAR(100)    文件夹名称
description       VARCHAR(200)    描述
doc_count         INT             文档数量
sort_order        INT             排序
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.4 文档版本表 (document_version)
```sql
id                BIGINT          主键
document_id       BIGINT          文档ID
version_number    INT             版本号
content           LONGTEXT        版本内容（JSON）
version_name      VARCHAR(100)    版本名称
change_log        TEXT            变更日志
file_size         BIGINT          文件大小（字节）
create_user_id    BIGINT          创建者ID
create_time       DATETIME        创建时间
```

### 4.5 协作表 (collaboration)
```sql
id                BIGINT          主键
document_id       BIGINT          文档ID
user_id           BIGINT          用户ID
permission        VARCHAR(20)     权限（OWNER/EDIT/VIEW）
invite_user_id    BIGINT          邀请人ID
is_active         TINYINT         是否活跃（0否/1是）
last_edit_time    DATETIME        最后编辑时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.6 团队表 (team)
```sql
id                BIGINT          主键
team_name         VARCHAR(100)    团队名称
description       TEXT            团队描述
avatar            VARCHAR(200)    团队头像
creator_id        BIGINT          创建者ID
member_count      INT             成员数
doc_count         INT             文档数
status            TINYINT         状态（0禁用/1正常）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.7 团队成员表 (team_member)
```sql
id                BIGINT          主键
team_id           BIGINT          团队ID
user_id           BIGINT          用户ID
role              VARCHAR(20)     角色（ADMIN/MEMBER）
join_time         DATETIME        加入时间
create_time       DATETIME        创建时间
```

### 4.8 模板表 (template)
```sql
id                BIGINT          主键
user_id           BIGINT          创建者ID
team_id           BIGINT          团队ID（0表示个人）
template_name     VARCHAR(100)    模板名称
template_type     VARCHAR(20)     模板类型（BOARD/MINDMAP/NOTE）
category          VARCHAR(50)     分类
cover_url         VARCHAR(200)    封面URL
description       TEXT            模板描述
content           LONGTEXT        模板内容（JSON）
use_count         INT             使用次数
collect_count     INT             收藏次数
rating            DECIMAL(3,2)    评分（0-5）
is_public         TINYINT         是否公开（0否/1是）
is_official       TINYINT         是否官方（0否/1是）
status            TINYINT         状态（0待审核/1已发布/2已拒绝）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.9 模板收藏表 (template_collect)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
template_id       BIGINT          模板ID
create_time       DATETIME        创建时间
```

### 4.10 文档收藏表 (document_collect)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
document_id       BIGINT          文档ID
create_time       DATETIME        创建时间
```

### 4.11 最近访问表 (recent_visit)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
document_id       BIGINT          文档ID
visit_time        DATETIME        访问时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.12 消息通知表 (notification)
```sql
id                BIGINT          主键
user_id           BIGINT          接收用户ID
from_user_id      BIGINT          发送用户ID
type              VARCHAR(20)     类型（INVITE/MENTION/SHARE/TEAM/SYSTEM）
title             VARCHAR(100)    标题
content           VARCHAR(500)    通知内容
related_id        BIGINT          关联ID（文档ID/团队ID）
related_type      VARCHAR(20)     关联类型
is_read           TINYINT         是否已读（0未读/1已读）
create_time       DATETIME        创建时间
```

### 4.13 操作日志表 (operation_log)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
document_id       BIGINT          文档ID
operation_type    VARCHAR(50)     操作类型（CREATE/EDIT/DELETE/SHARE/EXPORT）
operation_detail  TEXT            操作详情
ip_address        VARCHAR(50)     IP地址
user_agent        VARCHAR(200)    用户代理
create_time       DATETIME        创建时间
```

### 4.14 附件表 (attachment)
```sql
id                BIGINT          主键
document_id       BIGINT          文档ID
user_id           BIGINT          上传者ID
file_name         VARCHAR(200)    文件名
file_size         BIGINT          文件大小（字节）
file_type         VARCHAR(50)     文件类型
file_url          VARCHAR(200)    文件URL
create_time       DATETIME        创建时间
```

## 五、接口设计

### 5.1 认证接口

#### POST /api/auth/register
用户注册

#### POST /api/auth/login
用户登录

#### POST /api/auth/logout
用户登出

#### GET /api/auth/info
获取当前用户信息

### 5.2 用户接口

#### GET /api/user/profile
获取个人信息

#### PUT /api/user/profile
更新个人信息

#### POST /api/user/avatar
上传头像

#### PUT /api/user/password
修改密码

#### GET /api/user/stats
获取个人统计数据

### 5.3 文档接口

#### POST /api/document/create
创建文档

#### GET /api/document/{id}
获取文档详情

#### PUT /api/document/{id}
更新文档

#### DELETE /api/document/{id}
删除文档（移到回收站）

#### POST /api/document/{id}/restore
恢复文档

#### DELETE /api/document/{id}/permanent
永久删除文档

#### GET /api/document/list
获取文档列表

#### GET /api/document/recent
获取最近访问

#### GET /api/document/starred
获取星标文档

#### GET /api/document/trash
获取回收站

#### POST /api/document/{id}/star
星标文档

#### DELETE /api/document/{id}/star
取消星标

#### POST /api/document/{id}/copy
复制文档

#### POST /api/document/{id}/move
移动文档

#### GET /api/document/search
搜索文档

### 5.4 文件夹接口

#### POST /api/folder/create
创建文件夹

#### GET /api/folder/{id}
获取文件夹详情

#### PUT /api/folder/{id}
更新文件夹

#### DELETE /api/folder/{id}
删除文件夹

#### GET /api/folder/tree
获取文件夹树

#### POST /api/folder/{id}/move
移动文件夹

### 5.5 版本接口

#### GET /api/version/list
获取版本列表

#### GET /api/version/{id}
获取版本详情

#### POST /api/version/save
保存版本

#### POST /api/version/{id}/restore
恢复到某版本

#### GET /api/version/compare
版本对比

### 5.6 协作接口

#### POST /api/collab/invite
邀请协作

#### GET /api/collab/list
获取协作者列表

#### PUT /api/collab/{id}/permission
修改协作权限

#### DELETE /api/collab/{id}
移除协作者

#### GET /api/collab/my-documents
获取协作的文档

### 5.7 团队接口

#### POST /api/team/create
创建团队

#### GET /api/team/{id}
获取团队详情

#### PUT /api/team/{id}
更新团队信息

#### DELETE /api/team/{id}
解散团队

#### POST /api/team/{id}/leave
退出团队

#### GET /api/team/list
获取我的团队列表

#### POST /api/team/{id}/invite
邀请成员

#### GET /api/team/{id}/members
获取成员列表

#### DELETE /api/team/{id}/member/{userId}
移除成员

#### PUT /api/team/{id}/member/{userId}/role
修改成员角色

#### GET /api/team/{id}/documents
获取团队文档

#### GET /api/team/{id}/stats
获取团队统计

### 5.8 模板接口

#### POST /api/template/create
创建模板

#### GET /api/template/{id}
获取模板详情

#### PUT /api/template/{id}
更新模板

#### DELETE /api/template/{id}
删除模板

#### GET /api/template/list
获取模板列表

#### GET /api/template/my
获取我的模板

#### GET /api/template/team/{teamId}
获取团队模板

#### POST /api/template/{id}/use
使用模板创建文档

#### POST /api/template/{id}/collect
收藏模板

#### DELETE /api/template/{id}/collect
取消收藏

#### POST /api/template/{id}/rate
评分模板

### 5.9 分享接口

#### POST /api/share/create
创建分享链接

#### GET /api/share/{shareLink}
通过分享链接访问

#### POST /api/share/{shareLink}/verify
验证分享密码

#### DELETE /api/share/{id}
取消分享

#### PUT /api/share/{id}
更新分享设置

### 5.10 导出接口

#### POST /api/export/image
导出为图片

#### POST /api/export/pdf
导出为PDF

#### POST /api/export/markdown
导出为Markdown

#### POST /api/export/html
导出为HTML

#### POST /api/export/word
导出为Word

### 5.11 通知接口

#### GET /api/notification/list
获取通知列表

#### GET /api/notification/unread-count
获取未读数量

#### PUT /api/notification/{id}/read
标记已读

#### PUT /api/notification/read-all
全部标记已读

#### DELETE /api/notification/{id}
删除通知

#### GET /api/notification/settings
获取通知设置

#### PUT /api/notification/settings
更新通知设置

### 5.12 附件接口

#### POST /api/attachment/upload
上传附件

#### GET /api/attachment/list
获取附件列表

#### DELETE /api/attachment/{id}
删除附件

#### GET /api/attachment/{id}/download
下载附件

### 5.13 WebSocket接口

#### WS /ws/collab/{documentId}
协作WebSocket连接

#### 消息类型
- CURSOR_MOVE：光标移动
- CONTENT_CHANGE：内容变更
- USER_JOIN：用户加入
- USER_LEAVE：用户离开
- CHAT_MESSAGE：聊天消息

### 5.14 管理员接口

#### GET /api/admin/user/list
用户列表

#### PUT /api/admin/user/{id}/status
修改用户状态

#### GET /api/admin/document/list
文档列表

#### DELETE /api/admin/document/{id}
删除文档

#### GET /api/admin/team/list
团队列表

#### DELETE /api/admin/team/{id}
解散团队

#### GET /api/admin/template/list
模板列表

#### PUT /api/admin/template/{id}/audit
审核模板

#### GET /api/admin/stats/overview
数据概览

#### GET /api/admin/stats/trend
数据趋势

#### GET /api/admin/operation-log/list
操作日志列表

## 六、前端页面设计

### 6.1 用户端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单
- 忘记密码
- 第三方登录入口

#### 6.1.2 工作台首页
- 顶部导航（工作台、团队、模板、通知）
- 快速创建（白板、思维导图、笔记）
- 最近访问列表
- 我的文档（文件夹树+文档列表）
- 星标文档
- 共享给我的
- 回收站入口

#### 6.1.3 白板编辑页
- 顶部工具栏（文件、编辑、视图、分享）
- 左侧工具面板（选择、画笔、形状、文本、图片）
- 中间画布区域
- 右侧属性面板（样式、图层）
- 底部状态栏（缩放比例、协作者）
- 协作者光标显示

#### 6.1.4 思维导图编辑页
- 顶部工具栏（文件、编辑、布局、分享）
- 左侧节点工具
- 中间画布区域
- 右侧属性面板（节点样式、连线样式）
- 底部状态栏
- 协作者在线状态

#### 6.1.5 笔记编辑页
- 顶部工具栏（文件、编辑、插入、分享）
- 左侧大纲目录
- 中间编辑器（Markdown）
- 右侧预览区域
- 底部状态栏（字数统计、自动保存）

#### 6.1.6 文档详情页
- 文档信息（标题、类型、创建时间）
- 协作者列表
- 版本历史
- 操作按钮（编辑、分享、导出、删除）

#### 6.1.7 团队空间页
- 团队信息卡片
- 成员列表
- 团队文档列表
- 团队模板
- 团队活动动态
- 团队设置入口

#### 6.1.8 模板市场页
- 模板分类导航
- 模板卡片列表（封面、名称、使用次数）
- 模板详情弹窗
- 使用模板按钮
- 收藏按钮
- 我的模板入口

#### 6.1.9 分享预览页
- 文档预览（只读模式）
- 作者信息
- 分享设置（密码输入）
- 操作按钮（复制、导出、收藏）

#### 6.1.10 个人中心页
- 个人信息编辑
- 头像上传
- 修改密码
- 通知设置
- 个人统计数据
- 我的收藏
- 我的模板

#### 6.1.11 消息通知页
- Tab切换（全部、@我的、协作、团队）
- 通知列表
- 未读标识
- 通知操作（已读、删除）
- 通知跳转

#### 6.1.12 搜索页
- 搜索框
- 搜索历史
- 筛选条件（类型、时间、创建人）
- 搜索结果列表
- 高亮关键词

### 6.2 管理员端页面

#### 6.2.1 数据统计页
- 数据概览卡片
- 用户增长趋势图
- 文档创建趋势图
- 存储使用情况
- 活跃度统计
- 热门模板TOP10

#### 6.2.2 用户管理页
- 用户列表
- 搜索筛选
- 状态管理
- 用户详情
- 操作日志

#### 6.2.3 文档管理页
- 文档列表
- 搜索筛选
- 文档预览
- 文档删除
- 批量操作

#### 6.2.4 团队管理页
- 团队列表
- 团队详情
- 成员管理
- 解散团队

#### 6.2.5 模板管理页
- 模板列表
- 模板审核
- 模板推荐设置
- 官方模板管理

#### 6.2.6 系统配置页
- 存储配置
- 上传限制配置
- 协作配置
- 通知配置

## 七、业务流程

### 7.1 文档创建流程
```
用户登录
→ 进入工作台
→ 点击创建按钮（白板/思维导图/笔记）
→ 选择从空白创建/模板创建
→ 输入文档标题
→ 进入编辑器
→ 开始创作
→ 自动保存
```

### 7.2 实时协作流程
```
文档所有者
→ 点击分享按钮
→ 生成协作链接/邀请指定用户
→ 设置协作权限（编辑/查看）
→ 发送邀请

协作者
→ 收到邀请通知/打开链接
→ 进入文档编辑页
→ 建立WebSocket连接
→ 显示在线协作者
→ 实时同步操作
→ 光标位置实时显示
→ 操作冲突自动解决
```

### 7.3 版本管理流程
```
用户编辑文档
→ 自动保存（每隔30秒）
→ 生成版本记录
→ 用户查看版本历史
→ 选择某个版本
→ 预览版本内容
→ 恢复到该版本
→ 生成新版本
```

### 7.4 团队协作流程
```
创建团队
→ 输入团队信息
→ 邀请成员（邮箱/链接）
→ 成员接受邀请
→ 加入团队

团队协作
→ 在团队空间创建文档
→ 团队成员可见
→ 设置文档权限
→ 团队成员协作编辑
→ 查看团队活动动态
```

### 7.5 模板使用流程
```
用户进入模板市场
→ 浏览/搜索模板
→ 预览模板
→ 点击使用模板
→ 选择创建位置（个人/团队）
→ 基于模板创建新文档
→ 开始编辑
```

## 八、实时协作技术方案

### 8.1 WebSocket连接
```java
用户打开文档
→ 建立WebSocket连接（/ws/collab/{documentId}）
→ 服务端记录在线用户
→ 广播用户加入消息
→ 返回当前在线用户列表
```

### 8.2 操作同步
```java
用户A执行操作（绘图/输入文字/移动元素）
→ 捕获操作事件
→ 封装操作消息（operationType、data、timestamp）
→ 通过WebSocket发送到服务端
→ 服务端广播给其他在线用户
→ 用户B/C收到消息
→ 解析操作消息
→ 在本地画布应用操作
→ 更新显示
```

### 8.3 冲突解决
```java
操作冲突检测
→ 基于时间戳排序
→ 操作转换算法（OT）
→ 确保最终一致性
```

### 8.4 光标同步
```java
用户移动光标/选中元素
→ 发送光标位置消息
→ 服务端广播
→ 其他用户显示彩色光标（带用户头像）
```

### 8.5 断线重连
```java
检测连接断开
→ 自动重连机制（exponential backoff）
→ 重连成功后
→ 获取最新文档版本
→ 合并本地未同步操作
→ 继续协作
```

## 九、特色功能

### 9.1 无限画布
- 支持无限缩放和平移
- 流畅的拖拽体验
- 小地图导航

### 9.2 实时协作
- 多人同时编辑
- 彩色光标显示
- 操作实时同步
- 协作聊天

### 9.3 智能版本控制
- 自动保存版本
- 版本对比
- 一键恢复
- 版本命名

### 9.4 丰富的模板库
- 多种场景模板
- 官方精选模板
- 用户自定义模板
- 一键使用模板

### 9.5 强大的导出能力
- 多格式导出
- 高清图片导出
- PDF导出
- 保留样式

### 9.6 灵活的权限控制
- 文档级权限
- 团队级权限
- 细粒度权限设置

### 9.7 知识库管理
- 文件夹树形结构
- 全文搜索
- 标签分类
- 收藏和星标

### 9.8 团队协作
- 团队空间
- 成员管理
- 团队文档
- 活动动态

## 十、非功能需求

### 10.1 性能要求
- 画布渲染：支持1000+元素流畅操作
- WebSocket延迟：< 100ms
- 自动保存间隔：30秒
- 文档加载时间：< 2秒
- 支持并发协作：> 10人

### 10.2 安全要求
- 密码加密（BCrypt）
- JWT Token鉴权
- 文件类型校验
- 文件大小限制
- XSS防护
- CSRF防护
- 分享链接加密

### 10.3 存储要求
- 文件存储：本地存储 / OSS对象存储
- 单文档大小限制：< 50MB
- 单用户空间限制：1GB（可扩展）
- 数据库备份：每日自动备份

### 10.4 兼容性要求
- 浏览器：Chrome、Firefox、Safari、Edge（最新版）
- 分辨率：1920x1080及以上
- 支持触控屏操作

## 十一、开发优先级

### P0（核心功能 - 第1-2周）
- 用户注册登录
- 白板基础绘图
- 笔记Markdown编辑
- 文档创建保存
- 文件夹管理

### P1（重要功能 - 第3周）
- 实时协作（WebSocket）
- 思维导图
- 版本历史
- 分享功能
- 导出功能

### P2（增强功能 - 第4周）
- 团队功能
- 模板市场
- 搜索功能
- 通知系统

### P3（优化功能 - 第5周）
- 管理员后台
- 数据统计
- 操作日志
- 附件管理
- 性能优化

## 十二、部署说明

### 12.1 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+
- Nginx（可选）

### 12.2 存储规划
- 文档附件：/data/attachments/
- 用户头像：/data/avatars/
- 模板封面：/data/covers/
- 导出文件：/data/exports/
- 数据库备份：/data/backup/

### 12.3 配置说明
- WebSocket端口：8012
- HTTP端口：8012
- Redis配置：localhost:6379
- MySQL配置：localhost:3306/collab_board

### 12.4 初始账户
- 管理员：admin / admin123
- 测试用户1：test1 / 123456
- 测试用户2：test2 / 123456

### 12.5 测试数据
- 预置5个用户
- 预置10个文档（白板、思维导图、笔记各3个）
- 预置3个团队
- 预置20个模板

