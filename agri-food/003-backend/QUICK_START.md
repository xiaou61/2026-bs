# 快速启动指南

## 一键启动步骤

### 1️⃣ 准备工作（5分钟）

确保已安装：
- ✅ JDK 17+
- ✅ MySQL 8.0+
- ✅ Maven 3.6+

### 2️⃣ 数据库初始化（2分钟）

```sql
-- 打开 MySQL 命令行
mysql -u root -p

-- 执行初始化脚本
source D:/毕业设计/2026-biyesheji/003-backend/src/main/resources/sql/init.sql
```

或者：
1. 打开 Navicat
2. 新建查询
3. 复制 `src/main/resources/sql/init.sql` 内容
4. 执行

### 3️⃣ 修改配置（1分钟）

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    password: 你的MySQL密码  # 改这里！
```

### 4️⃣ 启动项目（1分钟）

**方式一：命令行**
```bash
cd 003-backend
mvn spring-boot:run
```

**方式二：IDEA**
1. 打开 IDEA
2. 打开 `003-backend` 目录
3. 右键 `FarmPlatformApplication.java`
4. 点击 Run

### 5️⃣ 访问系统（马上体验！）

🌐 **首页**：http://localhost:8080

🔐 **测试账号**：

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | admin | admin123 | 完整管理权限 |
| 农户 | farmer1 | admin123 | 发布商品、管理订单 |
| 消费者 | buyer1 | admin123 | 购买商品、评价 |

---

## 🎯 推荐体验路径

### 路径一：管理员视角（5分钟）
1. 使用 `admin / admin123` 登录
2. 查看数据统计
3. 审核待审核商品
4. 管理用户账号
5. 发布平台公告

### 路径二：农户视角（5分钟）
1. 使用 `farmer1 / admin123` 登录
2. 发布一个新商品
3. 查看我的商品列表
4. 查看收到的订单
5. 发货操作

### 路径三：消费者视角（5分钟）
1. 使用 `buyer1 / admin123` 登录
2. 浏览商品列表
3. 搜索"西红柿"
4. 加入购物车
5. 提交订单
6. 支付订单
7. 评价商品

---

## ❓ 遇到问题？

### 问题：端口被占用
```
修改 application.yml 中的 server.port
```

### 问题：数据库连接失败
```
1. 检查 MySQL 是否启动
2. 检查用户名密码是否正确
3. 检查数据库是否创建成功
```

### 问题：依赖下载慢
```
配置阿里云 Maven 镜像
```

---

## 📖 更多文档

- 📘 [README.md](README.md) - 完整项目说明
- 🔑 [TEST_ACCOUNTS.md](TEST_ACCOUNTS.md) - 详细测试账号
- 📦 [INSTALL.md](INSTALL.md) - 详细安装指南
- ✅ [FEATURES.md](FEATURES.md) - 功能清单

---

**开始你的助农扶贫之旅吧！** 🌾🚀

