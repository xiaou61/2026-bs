# 智能菜谱推荐系统（一体化版本）

## 项目简介
智能菜谱推荐系统是一个前后端一体化的Spring Boot项目，提供食材管理、菜谱推荐、营养分析、购物清单、做菜打卡等功能。

## 技术栈
### 后端
- SpringBoot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT 4.4.0

### 前端
- jQuery 3.7.1
- Bootstrap 5.3.0
- ECharts 5.4.3
- Layer 3.5.1

## 项目结构
```
008-backend/
├── src/main/
│   ├── java/com/xiaou/          # 后端Java代码
│   │   ├── controller/          # 控制器层
│   │   ├── service/             # 业务逻辑层
│   │   ├── mapper/              # 数据访问层
│   │   ├── entity/              # 实体类
│   │   ├── dto/                 # 数据传输对象
│   │   ├── vo/                  # 视图对象
│   │   ├── common/              # 通用类
│   │   ├── config/              # 配置类
│   │   ├── interceptor/         # 拦截器
│   │   ├── exception/           # 异常处理
│   │   └── utils/               # 工具类
│   └── resources/
│       ├── application.yml      # 应用配置
│       ├── sql/schema.sql       # 数据库脚本
│       └── static/              # 前端静态资源（一体化）
│           ├── index.html       # 登录页
│           ├── css/             # 样式文件
│           ├── js/              # JavaScript文件
│           └── pages/           # 前端页面
│               ├── user/        # 用户端页面
│               └── admin/       # 管理端页面
├── pom.xml                      # Maven配置
├── ACCOUNTS.md                  # 测试账户
└── README.md                    # 本文档
```

## 核心功能

### 1. 用户端功能
- **用户认证**：注册/登录（JWT）
- **首页**：今日推荐、热门菜谱、统计数据、营养分析图表
- **菜谱模块**：列表、详情、搜索、收藏
- **智能推荐**：今日推荐、基于食材推荐、新手推荐
- **食材管理**：库存管理、过期提醒
- **营养分析**：自动计算、可视化展示
- **购物清单**：自动生成、一键加入库存
- **打卡记录**：记录、统计、成就展示
- **个人中心**：信息编辑、我的收藏

### 2. 管理员功能
- **数据统计**：用户、菜谱、食材、打卡统计
- **菜谱管理**：查看、删除
- **食材管理**：查看、删除
- **用户管理**：查看、启用/禁用

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置
执行SQL脚本创建数据库：
```bash
mysql -u root -p < src/main/resources/sql/schema.sql
```

修改 `application.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smart_recipe
    username: root
    password: your_password
```

### 3. 启动项目
```bash
# 使用Maven启动
mvn spring-boot:run

# 或者使用IDE直接运行 SmartRecipeApplication.java
```

### 4. 访问系统
项目启动后访问：
- 系统入口：http://localhost:8008
- 用户端：http://localhost:8008/pages/user/index.html
- 管理端：http://localhost:8008/pages/admin/index.html

## 测试账户

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | admin | 123456 | 可访问管理后台 |
| 用户1 | user1 | 123456 | 美食爱好者 |
| 用户2 | user2 | 123456 | 厨房新手 |

## 数据库设计

系统共9张表：
- **user** - 用户表
- **ingredient** - 全局食材表
- **user_ingredient** - 用户食材库存表
- **recipe** - 菜谱表
- **recipe_ingredient** - 菜谱食材关联表
- **recipe_step** - 菜谱步骤表
- **user_collect** - 用户收藏表
- **cooking_record** - 做菜打卡表
- **shopping_list** - 购物清单表

## API接口

### 用户相关
- POST /api/user/register - 注册
- POST /api/user/login - 登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/info - 更新用户信息

### 菜谱相关
- GET /api/recipe/list - 菜谱列表
- GET /api/recipe/{id} - 菜谱详情
- POST /api/recipe/collect/{id} - 收藏菜谱

### 推荐相关
- GET /api/recommend/today - 今日推荐
- GET /api/recommend/by-ingredient - 基于食材推荐
- GET /api/recommend/hot - 热门推荐
- GET /api/recommend/easy - 新手推荐

### 更多接口
详见代码中的Controller层

## 智能推荐算法

系统采用多维度推荐算法：
```
推荐分数 = 
  食材匹配度 * 0.4 +
  口味偏好匹配度 * 0.2 +
  历史做菜偏好 * 0.2 +
  菜谱评分 * 0.1 +
  时间适配度 * 0.1
```

排除规则：
- 包含用户饮食禁忌的菜谱
- 近3天内已做过的菜谱

## 项目特点

### 一体化部署
- 前后端集成在同一个Spring Boot项目中
- 只需启动一次即可访问完整系统
- 简化部署流程，适合毕业设计

### 核心功能
1. **智能推荐算法** - 基于多维度的菜谱推荐
2. **营养分析** - 自动计算营养成分
3. **可视化展示** - ECharts图表展示
4. **用户体验** - 响应式设计，友好交互
5. **权限管理** - JWT认证，角色区分

### 技术亮点
- Spring Boot 3.x 最新版本
- MyBatis-Plus 简化数据访问
- JWT无状态认证
- 前后端分离架构（一体化部署）
- RESTful API设计

## 开发说明

### 添加新功能
1. 在 `controller` 包创建控制器
2. 在 `service` 包实现业务逻辑
3. 在 `mapper` 包定义数据访问
4. 在 `static/pages` 添加前端页面

### 前端开发
- 所有前端文件位于 `src/main/resources/static/`
- 修改后刷新浏览器即可看到效果
- API地址配置在 `static/js/config.js`

### 后端开发
- 修改Java代码后需要重启项目
- 数据库变更需要更新 `schema.sql`
- 新增API需要在 `WebConfig` 中配置拦截器排除规则

## 注意事项
1. 首次运行前必须执行数据库脚本
2. 确保MySQL服务已启动
3. 端口8008未被占用
4. 所有密码使用MD5加密存储
5. Token有效期为24小时

## 预置数据
系统已预置以下测试数据：
- 3个测试用户
- 18种常见食材
- 5道示例菜谱（含食材和步骤）

## 故障排除

### 启动失败
- 检查MySQL是否运行
- 检查数据库连接配置
- 检查端口8008是否被占用

### 登录失败
- 确认数据库中有测试用户
- 检查密码是否为123456的MD5值

### 页面无法访问
- 确认项目已成功启动
- 检查浏览器控制台错误信息
- 确认静态资源路径正确

## 扩展建议
1. 增加图片上传功能
2. 实现实时通知推送
3. 添加社交分享功能
4. 开发移动端应用
5. 集成第三方支付

## License
MIT

## 联系方式
如有问题，请查看代码或联系开发者。
