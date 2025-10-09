# 安装部署指南

## 环境准备

### 必需环境
- JDK 17 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6 或更高版本

### 开发工具（可选）
- IntelliJ IDEA 2023+
- Navicat / MySQL Workbench（数据库管理工具）
- Postman（API 测试工具）

---

## 数据库安装

### 1. 安装 MySQL

Windows 用户：
```bash
# 下载 MySQL 8.0 安装包
# https://dev.mysql.com/downloads/mysql/

# 安装后设置 root 密码
```

Linux 用户：
```bash
sudo apt-get update
sudo apt-get install mysql-server
sudo mysql_secure_installation
```

### 2. 创建数据库

打开 MySQL 命令行或可视化工具，执行以下命令：

```sql
-- 方式一：直接执行 SQL 文件
source /path/to/003-backend/src/main/resources/sql/init.sql

-- 方式二：手动执行
CREATE DATABASE IF NOT EXISTS farm_platform 
  DEFAULT CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

USE farm_platform;

-- 然后复制 init.sql 中的内容执行
```

### 3. 验证数据库

```sql
USE farm_platform;

-- 查看表
SHOW TABLES;

-- 应该看到 6 张表：
-- user, category, product, order, order_item, comment

-- 查看测试数据
SELECT * FROM user;
-- 应该有 5 条测试账号数据
```

---

## 项目配置

### 1. 克隆或下载项目

```bash
cd D:\毕业设计\2026-biyesheji
```

### 2. 修改数据库配置

编辑 `003-backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/farm_platform?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root        # 修改为你的 MySQL 用户名
    password: your_password  # 修改为你的 MySQL 密码
```

### 3. 修改服务器端口（可选）

如果 8080 端口被占用，可以修改端口号：

```yaml
server:
  port: 8888  # 修改为其他端口
```

---

## 编译与运行

### 方式一：使用 Maven 命令行

```bash
cd 003-backend

# 清理并编译
mvn clean install

# 运行项目
mvn spring-boot:run
```

### 方式二：使用 IntelliJ IDEA

1. 打开 IntelliJ IDEA
2. File -> Open -> 选择 `003-backend` 目录
3. 等待 Maven 依赖下载完成
4. 找到 `FarmPlatformApplication.java`
5. 右键 -> Run 'FarmPlatformApplication'

### 方式三：打包成 JAR 运行

```bash
cd 003-backend

# 打包
mvn clean package -DskipTests

# 运行
java -jar target/003-backend-1.0-SNAPSHOT.jar
```

---

## 验证安装

### 1. 检查启动日志

看到以下日志说明启动成功：

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
Started FarmPlatformApplication in 5.234 seconds
```

### 2. 访问首页

浏览器访问：http://localhost:8080

应该能看到助农精准扶贫平台首页。

### 3. 测试登录

1. 点击"登录"按钮
2. 输入用户名：`admin`，密码：`admin123`
3. 点击"登录"
4. 成功跳转到管理员后台

### 4. 测试 API

使用 Postman 或 curl 测试：

```bash
# 登录接口
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# 应该返回 token
```

---

## 常见问题

### 问题 1：端口被占用

**错误信息**：
```
Web server failed to start. Port 8080 was already in use.
```

**解决方案**：
- 方式一：关闭占用 8080 端口的程序
- 方式二：修改 `application.yml` 中的端口号

### 问题 2：数据库连接失败

**错误信息**：
```
Cannot create PoolableConnectionFactory
Access denied for user 'root'@'localhost'
```

**解决方案**：
1. 检查 MySQL 是否启动
2. 检查用户名密码是否正确
3. 检查数据库名是否正确
4. 检查 MySQL 用户权限

```sql
-- 给 root 用户授权
GRANT ALL PRIVILEGES ON farm_platform.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### 问题 3：Maven 依赖下载失败

**解决方案**：
1. 配置国内 Maven 镜像源（阿里云）

编辑 `~/.m2/settings.xml`：

```xml
<mirrors>
  <mirror>
    <id>aliyun</id>
    <mirrorOf>central</mirrorOf>
    <name>Aliyun Maven</name>
    <url>https://maven.aliyun.com/repository/public</url>
  </mirror>
</mirrors>
```

2. 重新执行 `mvn clean install`

### 问题 4：前端页面无法访问

**错误信息**：404 Not Found

**解决方案**：
1. 确认项目已启动
2. 检查路径是否正确：http://localhost:8080/
3. 检查 `templates` 目录下是否有 HTML 文件
4. 清除浏览器缓存重试

### 问题 5：JWT Token 过期

**错误信息**：token 无效或已过期

**解决方案**：
1. 重新登录获取新 token
2. Token 默认有效期 24 小时，可在 `application.yml` 中修改：

```yaml
jwt:
  expiration: 86400000  # 单位：毫秒，24小时
```

---

## 目录结构说明

```
003-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/xiaou/
│   │   │       ├── common/           # 公共类
│   │   │       ├── config/           # 配置类
│   │   │       ├── controller/       # 控制器
│   │   │       ├── entity/           # 实体类
│   │   │       ├── mapper/           # Mapper 接口
│   │   │       ├── service/          # 服务层
│   │   │       ├── utils/            # 工具类
│   │   │       └── FarmPlatformApplication.java  # 启动类
│   │   └── resources/
│   │       ├── application.yml       # 配置文件 ⚠️ 需要修改
│   │       ├── sql/
│   │       │   └── init.sql          # 数据库初始化脚本
│   │       ├── static/               # 静态资源
│   │       └── templates/            # 前端页面
│   └── test/                         # 测试代码
├── target/                           # 编译输出目录
├── pom.xml                           # Maven 配置
├── README.md                         # 项目说明
├── TEST_ACCOUNTS.md                  # 测试账号
└── INSTALL.md                        # 本文件
```

---

## 访问地址

### 前端页面
- 首页：http://localhost:8080/
- 登录：http://localhost:8080/login.html
- 注册：http://localhost:8080/register.html
- 商品列表：http://localhost:8080/products.html
- 农户后台：http://localhost:8080/farmer_dashboard.html
- 管理员后台：http://localhost:8080/admin_dashboard.html

### API 接口
- 基础路径：http://localhost:8080/api/
- 接口文档：参考 README.md 中的 API 接口部分

---

## 下一步

1. 查看 [README.md](README.md) 了解项目功能
2. 查看 [TEST_ACCOUNTS.md](TEST_ACCOUNTS.md) 了解测试账号
3. 开始测试各项功能
4. 根据需要进行二次开发

---

## 技术支持

如遇到其他问题，请检查：
1. JDK 版本是否正确（17+）
2. MySQL 版本是否正确（8.0+）
3. 数据库配置是否正确
4. Maven 依赖是否下载完成
5. 端口是否被占用

---

祝使用愉快！ 🎉

