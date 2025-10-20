# 测试账号信息

## 管理员账户
- 用户名：`admin`
- 密码：`123456`
- 加密后：`$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O`

## 测试用户账户

### 用户1（发单用户）
- 学号：`20210001`
- 用户名：`user001`
- 手机号：`13800138001`
- 密码：`123456`
- 真实姓名：`张三`
- 宿舍：东区1号楼 101
- 加密后：`$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O`

### 用户2（代领员）
- 学号：`20210002`
- 用户名：`user002`
- 手机号：`13800138002`
- 密码：`123456`
- 真实姓名：`李四`
- 宿舍：东区2号楼 201
- 加密后：`$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O`

## 密码加密测试

运行测试类验证密码加密：
```bash
mvn test -Dtest=PasswordTest
```

测试类位置：`src/test/java/com/xiaou/express/PasswordTest.java`

## 数据库初始化

1. 创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS campus_express DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行初始化SQL：
```bash
mysql -u root -p campus_express < src/main/resources/init.sql
```

或直接在数据库管理工具中运行 `src/main/resources/init.sql` 文件。

## 配置说明

修改 `application.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_express
    username: root
    password: root
```

## 启动项目

```bash
mvn spring-boot:run
```

访问地址：`http://localhost:8080`

## API 测试

### 用户登录
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "20210001",
  "password": "123456"
}
```

### 管理员登录
```bash
POST http://localhost:8080/api/admin/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

