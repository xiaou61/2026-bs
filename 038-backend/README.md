# 个人饮食管理系统 - 后端

## 项目简介
基于SpringBoot的个人饮食管理系统后端服务

## 技术栈
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis 6.0+
- Spring Security + JWT 0.11.5
- Knife4j 4.3.0

## 快速开始

### 1. 环境要求
- JDK 17
- Maven 3.6+
- MySQL 8.0
- Redis 6.0+

### 2. 数据库初始化
```sql
-- 执行以下SQL文件
src/main/resources/sql/schema.sql
src/main/resources/sql/data.sql
```

### 3. 配置修改
修改 `application.yml` 中的数据库和Redis连接信息

### 4. 启动项目
```bash
mvn clean install
mvn spring-boot:run
```

### 5. 访问地址
- 后端API: http://localhost:8038/api
- 接口文档: http://localhost:8038/api/doc.html
- Druid监控: http://localhost:8038/api/druid

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
