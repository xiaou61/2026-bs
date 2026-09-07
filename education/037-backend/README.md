# 基于SpringBoot的编程学习交流平台

## 📖 项目简介

本项目是一个基于Spring Boot 3.2.0 + MyBatis 3.0.3 + 微信小程序的编程学习交流平台，提供课程学习、问答社区、技术文章、代码分享等功能，帮助编程学习者更好地学习和交流。

## 🚀 技术栈

### 后端技术
- **核心框架**：Spring Boot 3.2.0
- **持久层**：MyBatis 3.0.3（注意：使用MyBatis而不是MyBatis-Plus）
- **数据库**：MySQL 8.0
- **缓存**：Redis 6.0+
- **安全框架**：Spring Security + JWT 0.11.5
- **API文档**：Knife4j 4.3.0
- **连接池**：Druid 1.2.20
- **工具类**：Hutool、Lombok、MapStruct
- **微信SDK**：weixin-java-miniapp 4.5.0

### 前端技术
- **框架**：微信小程序原生开发
- **UI组件**：WeUI + Vant Weapp（可选）

## 💡 核心功能

### 1. 用户系统
- 微信授权登录
- 用户信息管理
- 积分等级体系（Lv1-Lv5）
- 角色权限管理

### 2. 课程学习
- 课程浏览与分类
- 章节学习（Markdown）
- 学习记录与进度跟踪

### 3. 问答社区
- 提问与悬赏
- 回答与采纳
- 问题搜索

### 4. 技术文章
- 文章发布（Markdown）
- 文章浏览与搜索
- 专栏管理

### 5. 代码分享
- 代码片段展示
- Fork功能
- 语法高亮

### 6. 学习打卡
- 每日打卡
- 连续打卡统计
- 排行榜

### 7. 互动功能
- 点赞/收藏
- 评论/回复
- 消息通知

### 8. 管理后台
- 用户管理
- 内容审核
- 数据统计

## 📊 项目规模

- **数据库表**：20+张
- **后端接口**：80+个API
- **小程序页面**：16+个页面
- **代码量**：约8000+行

## 🔧 快速开始

### 环境要求
- JDK 17
- Maven 3.6+
- MySQL 8.0
- Redis 6.0+
- 微信开发者工具

### 后端启动

1. 创建数据库并执行SQL脚本
```sql
-- 执行 src/main/resources/sql/schema.sql
-- 执行 src/main/resources/sql/data.sql
```

2. 修改配置文件
```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/programming_learning
    username: root
    password: your_password
```

3. 启动项目
```bash
mvn clean install
mvn spring-boot:run
```

4. 访问接口文档
- API文档：http://localhost:8037/api/doc.html
- Druid监控：http://localhost:8037/api/druid

### 前端启动

1. 使用微信开发者工具打开 `037-frontend` 目录

2. 修改API地址
```javascript
// app.js
globalData: {
  baseUrl: 'http://localhost:8037/api'
}
```

3. 编译运行即可

## 📁 项目结构

```
037-backend/
├── src/main/java/com/programming/learning/
│   ├── ProgrammingLearningApplication.java  # 启动类
│   ├── common/                              # 通用类
│   ├── config/                              # 配置类
│   ├── controller/                          # 控制器
│   ├── entity/                              # 实体类
│   ├── mapper/                              # Mapper接口
│   ├── service/                             # 服务层
│   └── util/                               # 工具类
├── src/main/resources/
│   ├── mapper/                             # MyBatis XML
│   ├── sql/                                # SQL脚本
│   └── application.yml                     # 配置文件
└── pom.xml

037-frontend/
├── pages/                                  # 页面
├── utils/                                  # 工具类
├── app.js                                  # 入口文件
├── app.json                                # 配置文件
└── app.wxss                                # 全局样式
```

## 🎯 项目特色

1. **技术差异化**：使用MyBatis原生XML配置，区别于MyBatis-Plus
2. **完整的积分体系**：五级等级系统，多种积分获取方式
3. **微信小程序集成**：原生小程序开发，用户体验好
4. **全文搜索**：基于MySQL全文索引实现高效搜索
5. **Redis缓存优化**：热门内容缓存、排行榜实现

## 📝 开发文档

- [启动说明](./启动说明.txt)
- [项目总结](../037-项目总结.txt)
- [API文档](http://localhost:8037/api/doc.html)

## 🤝 贡献

欢迎提交Issue和Pull Request！

## 📄 许可证

MIT License

## 👨‍💻 作者

2026毕业设计项目
