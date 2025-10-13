# 校园短视频平台 - 测试账号

## 数据库配置

### 数据库名
```
campus_video
```

### 导入SQL文件
```
011-backend/src/main/resources/sql/campus_video.sql
```

## 测试账号

### 管理员账号
- **用户名**: admin
- **密码**: 123456
- **角色**: ADMIN
- **说明**: 拥有所有权限

### 普通用户账号

#### 测试用户1
- **用户名**: test1
- **密码**: 123456
- **昵称**: 小明
- **学号**: 2024001
- **角色**: USER

#### 测试用户2
- **用户名**: test2
- **密码**: 123456
- **昵称**: 小红
- **学号**: 2024002
- **角色**: USER

#### 测试用户3
- **用户名**: test3
- **密码**: 123456
- **昵称**: 小李
- **学号**: 2024003
- **角色**: USER

#### 测试用户4
- **用户名**: test4
- **密码**: 123456
- **昵称**: 小张
- **学号**: 2024004
- **角色**: USER

## 热门话题

系统预置了10个热门话题：
1. #校园生活#
2. #学习打卡#
3. #才艺展示#
4. #美食探店#
5. #运动健身#
6. #搞笑段子#
7. #情感故事#
8. #技能教学#
9. #毕业季#
10. #考研加油#

## 服务端口

- 后端服务: http://localhost:8011

## 文件存储路径

视频、封面、头像文件默认存储在：
```
D:/campus-video-files/
├── videos/     # 视频文件
├── covers/     # 封面图片
└── avatars/    # 用户头像
```

## API文档

### 认证接口
- POST `/api/auth/register` - 注册
- POST `/api/auth/login` - 登录
- POST `/api/auth/logout` - 登出
- GET `/api/auth/info` - 获取当前用户信息

### 用户接口
- GET `/api/user/{id}` - 获取用户信息
- PUT `/api/user/profile` - 更新个人信息
- POST `/api/user/avatar` - 上传头像
- POST `/api/user/follow/{userId}` - 关注用户
- DELETE `/api/user/follow/{userId}` - 取消关注

### 视频接口
- POST `/api/video/upload` - 上传视频
- POST `/api/video/publish` - 发布视频
- GET `/api/video/recommend` - 获取推荐视频
- GET `/api/video/following` - 获取关注视频
- GET `/api/video/{id}` - 获取视频详情
- POST `/api/video/{id}/like` - 点赞视频
- POST `/api/video/{id}/collect` - 收藏视频

### 话题接口
- GET `/api/topic/hot` - 获取热门话题
- GET `/api/topic/{id}` - 获取话题详情
- GET `/api/topic/{id}/videos` - 获取话题视频列表

### 评论接口
- POST `/api/comment/publish` - 发布评论
- GET `/api/comment/list` - 获取评论列表
- POST `/api/comment/{id}/like` - 点赞评论

### 通知接口
- GET `/api/notification/list` - 获取通知列表
- GET `/api/notification/unread-count` - 获取未读数量
- PUT `/api/notification/{id}/read` - 标记已读

## 快速启动

1. 导入SQL文件创建数据库和表
2. 修改 `application.yml` 中的数据库配置
3. 创建文件存储目录 `D:/campus-video-files/`
4. 启动 `CampusVideoApplication`
5. 访问 http://localhost:8011

## 注意事项

1. 所有密码已使用MD5加密，原始密码为 `123456`
2. 需要先创建文件存储目录，否则文件上传会失败
3. 视频文件较大，建议使用分片上传（前端实现）
4. Token有效期为24小时
5. 所有请求需要在Header中携带Token（除了登录注册）
   ```
   Authorization: Bearer {token}
   ```

