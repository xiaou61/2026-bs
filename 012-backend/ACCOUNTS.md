# 在线协作白板与笔记系统 - 测试账号

## 数据库配置

### 数据库名
```
collab_board
```

### 导入SQL文件
```
012-backend/src/main/resources/sql/collab_board.sql
```

## 测试账号

### 管理员账号
- **用户名**: admin
- **密码**: 123456
- **角色**: ADMIN
- **邮箱**: admin@collabboard.com
- **说明**: 拥有所有权限

### 普通用户账号

#### 测试用户1
- **用户名**: test1
- **密码**: 123456
- **昵称**: 测试用户1
- **邮箱**: test1@collabboard.com
- **角色**: USER

#### 测试用户2
- **用户名**: test2
- **密码**: 123456
- **昵称**: 测试用户2
- **邮箱**: test2@collabboard.com
- **角色**: USER

#### 测试用户3
- **用户名**: test3
- **密码**: 123456
- **昵称**: 测试用户3
- **邮箱**: test3@collabboard.com
- **角色**: USER

#### 测试用户4
- **用户名**: test4
- **密码**: 123456
- **昵称**: 测试用户4
- **邮箱**: test4@collabboard.com
- **角色**: USER

## 预置模板

系统预置了8个官方模板：

### 白板模板
1. **空白白板** - 空白画布，自由创作
2. **流程图模板** - 标准流程图模板
3. **UML类图** - UML类图模板

### 思维导图模板
4. **思维导图** - 经典思维导图模板
5. **组织架构图** - 企业组织架构模板

### 笔记模板
6. **会议纪要** - 会议纪要模板
7. **日报模板** - 每日工作日报
8. **项目方案** - 项目方案模板

## 服务端口

- 后端服务: http://localhost:8012
- WebSocket: ws://localhost:8012/ws/collab/{documentId}

## 文件存储路径

文档附件、头像、封面文件默认存储在：
```
D:/collab-board-files/
├── attachments/   # 文档附件
├── avatars/       # 用户头像
├── covers/        # 封面图片
└── exports/       # 导出文件
```

## API文档

### 认证接口
- POST `/api/auth/register` - 注册
- POST `/api/auth/login` - 登录
- POST `/api/auth/logout` - 登出
- GET `/api/auth/info` - 获取当前用户信息

### 用户接口
- GET `/api/user/profile` - 获取个人信息
- GET `/api/user/{id}` - 获取用户信息
- PUT `/api/user/profile` - 更新个人信息
- POST `/api/user/avatar` - 上传头像
- PUT `/api/user/password` - 修改密码
- GET `/api/user/stats` - 获取个人统计数据

### 文档接口
- POST `/api/document/create` - 创建文档
- GET `/api/document/{id}` - 获取文档详情
- PUT `/api/document/{id}` - 更新文档
- DELETE `/api/document/{id}` - 删除文档（移到回收站）
- POST `/api/document/{id}/restore` - 恢复文档
- DELETE `/api/document/{id}/permanent` - 永久删除文档
- GET `/api/document/list` - 获取文档列表
- GET `/api/document/recent` - 获取最近访问
- GET `/api/document/starred` - 获取星标文档
- GET `/api/document/trash` - 获取回收站
- POST `/api/document/{id}/star` - 星标文档
- POST `/api/document/{id}/copy` - 复制文档
- POST `/api/document/{id}/move` - 移动文档
- GET `/api/document/search` - 搜索文档

### 文件夹接口
- POST `/api/folder/create` - 创建文件夹
- GET `/api/folder/{id}` - 获取文件夹详情
- PUT `/api/folder/{id}` - 更新文件夹
- DELETE `/api/folder/{id}` - 删除文件夹
- GET `/api/folder/tree` - 获取文件夹树
- POST `/api/folder/{id}/move` - 移动文件夹

### 团队接口
- POST `/api/team/create` - 创建团队
- GET `/api/team/{id}` - 获取团队详情
- PUT `/api/team/{id}` - 更新团队信息
- DELETE `/api/team/{id}` - 解散团队
- POST `/api/team/{id}/leave` - 退出团队
- GET `/api/team/list` - 获取我的团队列表
- POST `/api/team/{id}/invite` - 邀请成员
- GET `/api/team/{id}/members` - 获取成员列表
- DELETE `/api/team/{id}/member/{userId}` - 移除成员
- PUT `/api/team/{id}/member/{userId}/role` - 修改成员角色

### 模板接口
- POST `/api/template/create` - 创建模板
- GET `/api/template/{id}` - 获取模板详情
- PUT `/api/template/{id}` - 更新模板
- DELETE `/api/template/{id}` - 删除模板
- GET `/api/template/list` - 获取模板列表
- POST `/api/template/{id}/use` - 使用模板

### 通知接口
- GET `/api/notification/list` - 获取通知列表
- GET `/api/notification/unread-count` - 获取未读数量
- PUT `/api/notification/{id}/read` - 标记已读
- PUT `/api/notification/read-all` - 全部标记已读
- DELETE `/api/notification/{id}` - 删除通知

### WebSocket接口
- WS `/ws/collab/{documentId}` - 协作WebSocket连接

## 快速启动

1. 导入SQL文件创建数据库和表
2. 修改 `application.yml` 中的数据库配置
3. 创建文件存储目录 `D:/collab-board-files/`
4. 启动 `CollabBoardApplication`
5. 访问 http://localhost:8012

## 注意事项

1. 所有密码已使用MD5加密，原始密码为 `123456`
2. 需要先创建文件存储目录，否则文件上传会失败
3. Token有效期为24小时
4. 所有请求需要在Header中携带Token（除了登录注册）
   ```
   Authorization: Bearer {token}
   ```
5. WebSocket连接需要在建立连接后通过文档ID进行房间管理

## 文档类型

- **BOARD**: 白板
- **MINDMAP**: 思维导图
- **NOTE**: Markdown笔记

## 权限说明

### 用户角色
- **ADMIN**: 管理员，拥有所有权限
- **USER**: 普通用户

### 协作权限
- **OWNER**: 所有者，拥有全部权限
- **EDIT**: 编辑者，可以编辑文档
- **VIEW**: 查看者，只能查看文档

### 团队角色
- **ADMIN**: 团队管理员，可管理团队和成员
- **MEMBER**: 普通成员

