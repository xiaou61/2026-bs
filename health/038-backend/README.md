# 个人饮食管理系统 - 后端

## 项目简介
基于SpringBoot的个人饮食管理系统后端服务

## 技术栈
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- H2（默认演示环境）
- MySQL 8.0（可选，使用 `mysql` profile）
- Redis 6.0+（可选扩展）
- Spring Security + JWT 0.11.5
- Knife4j 4.3.0

## 快速开始

### 1. 环境要求
- JDK 17
- Maven 3.6+
- 默认启动无需提前安装 MySQL / Redis
- 如需生产式部署，可准备 MySQL 8.0 与 Redis 6.0+

### 2. 默认 H2 演示启动
```bash
mvn spring-boot:run
```

默认会自动加载：
- `src/main/resources/sql/schema-h2.sql`
- `src/main/resources/sql/data-h2.sql`

H2 控制台地址：`http://localhost:8038/api/h2-console`

### 3. MySQL 数据库初始化
```sql
-- 执行以下SQL文件
src/main/resources/sql/schema.sql
src/main/resources/sql/data.sql
```

### 4. MySQL 配置修改
修改 `application-mysql.yml` 中的数据库和 Redis 连接信息

### 5. MySQL 模式启动项目
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

### 6. 访问地址
- 后端API: http://localhost:8038/api
- 接口文档: http://localhost:8038/api/doc.html
- H2控制台: http://localhost:8038/api/h2-console
- Druid监控: MySQL profile 下访问 http://localhost:8038/api/druid

## 项目结构
```
src/main/java/com/diet/management/
├── common/           # 通用类
├── config/           # 配置类
├── controller/       # 控制器
├── entity/           # 实体类
├── enums/            # 枚举类
├── mapper/           # Mapper接口
├── service/          # 服务层
└── util/             # 工具类
```

## 核心功能
- 用户管理（注册/登录/信息维护）
- 饮食记录（记录每日饮食摄入）
- 食物库（丰富的食物营养数据）
- 营养分析（每日营养统计和分析）
- 健康数据（体重、BMI、体脂率追踪）
- 营养目标（设定和追踪个人目标）
- 食谱推荐（健康食谱浏览和收藏）

## 测试账号
- 管理员: admin / 123456
- 用户1: user1 / 123456
- 用户2: user2 / 123456

## 接口文档
启动项目后访问 http://localhost:8038/api/doc.html
