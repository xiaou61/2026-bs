# 中药餐饮推荐交流平台 - 后端项目

## 项目介绍

基于SpringBoot 3.2的中药餐饮推荐交流平台后端系统，提供完整的用户管理、食谱管理、社区交流等核心功能。

## 技术栈

- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis 6.x
- JWT 认证
- Java 17

## 项目结构

```
src/main/java/com/xiaou/herbal/
├── controller/          # API控制层
├── service/            # 业务逻辑层
│   └── impl/          # 服务实现
├── entity/            # 数据库实体
├── dto/               # 数据传输对象
├── mapper/            # 数据库访问层
├── config/            # 配置类
├── interceptor/       # 拦截器
├── util/              # 工具类
└── common/            # 通用类
```

## 核心功能模块

### 1. 用户管理模块
- 用户注册、登录、注销
- 个人信息管理
- 健康档案管理
- 创作者认证申请

### 2. 食谱管理模块
- 食谱创建、编辑、删除
- 食谱搜索、列表展示
- 食谱评论、收藏
- 食材关联管理

### 3. 食材库模块
- 食材查询、分类浏览
- 食材禁忌管理
- 食材搭配指导

### 4. 社区交流模块
- 话题创建、编辑
- 评论互动
- 话题搜索

### 5. 管理后台
- 用户管理
- 内容审核
- 创作者认证审核
- 话题评论管理

## API接口规范

### 请求/响应格式

#### 基础URL
```
http://localhost:8080/api/v1
```

#### 响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 认证方式

使用JWT Token认证，请求头格式：
```
Authorization: Bearer <token>
```

## 主要API端点

### 用户相关
- `POST /user/register` - 用户注册
- `POST /user/login` - 用户登录
- `GET /user/info` - 获取用户信息
- `PUT /user/info` - 更新用户信息

### 食谱相关
- `POST /recipe/create` - 创建食谱
- `GET /recipe/list` - 获取食谱列表
- `GET /recipe/{id}/detail` - 获取食谱详情
- `GET /recipe/search` - 搜索食谱
- `PUT /recipe/{id}` - 更新食谱
- `DELETE /recipe/{id}` - 删除食谱
- `POST /recipe/{id}/collect` - 收藏食谱
- `GET /recipe/my-recipes` - 获取我的食谱

### 食材相关
- `GET /ingredient/{id}/detail` - 获取食材详情
- `GET /ingredient/category/{category}` - 按分类获取食材
- `GET /ingredient/search` - 搜索食材
- `GET /ingredient/{id}/tabooed-ingredients` - 获取食材禁忌

### 话题相关
- `POST /topic/create` - 创建话题
- `GET /topic/list` - 获取话题列表
- `GET /topic/{id}/detail` - 获取话题详情
- `GET /topic/search` - 搜索话题
- `PUT /topic/{id}` - 更新话题
- `DELETE /topic/{id}` - 删除话题
- `GET /topic/my-topics` - 获取我的话题

### 评论相关
- `POST /comment/create` - 发表评论
- `GET /comment/list/{targetType}/{targetId}` - 获取评论列表
- `DELETE /comment/{id}` - 删除评论
- `POST /comment/{id}/like` - 点赞评论
- `GET /comment/my-comments` - 获取我的评论

### 健康档案
- `GET /health-profile/info` - 获取健康档案
- `POST /health-profile/save` - 保存/更新健康档案

### 创作者认证
- `POST /creator/auth/apply` - 申请创作者认证
- `GET /creator/auth/info` - 获取认证信息

### 管理员
- `GET /admin/users` - 获取用户列表
- `PUT /admin/user/{id}/status` - 更新用户状态
- `GET /admin/recipes/pending` - 获取待审核食谱
- `PUT /admin/recipe/{id}/audit` - 审核食谱
- `GET /admin/auth/pending` - 获取待审核认证
- `PUT /admin/auth/{id}/approve` - 批准认证
- `PUT /admin/auth/{id}/reject` - 拒绝认证

## 数据库配置

### MySQL配置
编辑 `application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/herbal_food
    username: root
    password: root
```

### 初始化数据库
项目启动时会自动执行 `schema.sql` 建表脚本。

## 启动应用

```bash
# 使用Maven启动
mvn spring-boot:run

# 或使用IDE直接运行HerbalFoodApplication类
```

默认应用启动在 `http://localhost:8080/api/v1`

## 常量定义

见 `com.xiaou.herbal.common.Constants` 类

主要常量：
- `UserType` - 用户类型(普通、创作者、管理员)
- `RecipeStatus` - 食谱状态(草稿、审核、已发布、已下架)
- `CommentStatus` - 评论状态
- `CreatorAuthStatus` - 创作者认证状态

## 工具类

### JwtUtil
JWT Token生成和验证工具

### UserContext
用户上下文，存储当前请求的用户ID

## 注意事项

1. 所有涉及用户的操作都需要JWT认证
2. 某些查看类接口不需要认证（食谱列表、话题列表等）
3. 管理员操作需要验证用户类型为ADMIN
4. 数据库表名和字段名使用下划线命名规范
5. 实体类与数据库字段名通过MyBatis-Plus驼峰转下划线自动映射

## 开发规范

- 无注释代码
- 使用Lombok简化实体类
- 使用Lambda表达式查询
- 事务控制使用@Transactional注解
- 统一错误处理和响应格式
