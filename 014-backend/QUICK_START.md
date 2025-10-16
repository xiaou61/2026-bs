# 快速启动指南

## 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

## 启动步骤

### 1. 创建数据库
```sql
CREATE DATABASE campus_club DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 执行SQL脚本
在MySQL中依次执行：
```bash
# 建表
source src/main/resources/sql/schema.sql

# 导入数据
source src/main/resources/sql/init_data.sql
```

或使用命令行：
```bash
mysql -u root -p campus_club < src/main/resources/sql/schema.sql
mysql -u root -p campus_club < src/main/resources/sql/init_data.sql
```

### 3. 修改配置
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    username: root
    password: 你的密码
```

### 4. 创建上传目录
```bash
mkdir -p D:/uploads/campus-club/
```

### 5. 启动应用
#### 使用IDE
直接运行 `CampusClubApplication.java`

#### 使用Maven
```bash
mvn clean spring-boot:run
```

#### 打包运行
```bash
mvn clean package
java -jar target/014-backend-1.0-SNAPSHOT.jar
```

### 6. 访问应用
浏览器打开：http://localhost:8080/index.html

## 测试账户

### 学生账户
| 账号 | 密码 | 说明 |
|------|------|------|
| student1 | 123456 | 张三，计算机协会社长 |
| student2 | 123456 | 李四，书画社社长 |
| student3 | 123456 | 王五，篮球俱乐部社长 |

### 管理员账户
| 账号 | 密码 | 说明 |
|------|------|------|
| admin | 123456 | 超级管理员 |
| manager | 123456 | 普通管理员 |

## 功能测试

### 1. 用户功能
1. 注册新用户
2. 登录系统
3. 完善个人资料
4. 添加兴趣标签
5. 查看积分和勋章

### 2. 社团功能
1. 浏览社团列表
2. 查看社团详情
3. 申请加入社团
4. 创建新社团（需要等待审核）

### 3. 活动功能
1. 查看活动列表
2. 报名参加活动
3. 使用签到码签到
4. 评价活动

### 4. 话题功能
1. 浏览话题广场
2. 发布新话题
3. 评论和点赞
4. 回复其他用户

### 5. 管理功能
1. 使用管理员账号登录
2. 审核待审核的社团
3. 管理用户和话题
4. 查看数据统计

## 常见问题

### Q: 启动报错找不到主类？
A: 确保使用JDK 17+，检查IDEA的Project SDK设置

### Q: 数据库连接失败？
A: 检查MySQL是否启动，用户名密码是否正确

### Q: 页面打不开？
A: 确保Spring Boot已成功启动，端口8080未被占用

### Q: 登录后看不到用户名？
A: 检查浏览器控制台是否有JS错误，清除浏览器缓存

### Q: 文件上传失败？
A: 确保上传目录存在且有写入权限

## API测试

可以使用Postman或curl测试API：

### 注册
```bash
curl -X POST http://localhost:8080/api/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test",
    "password": "123456",
    "realName": "测试用户",
    "studentId": "2024001"
  }'
```

### 登录
```bash
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "password": "123456"
  }'
```

### 获取社团列表
```bash
curl http://localhost:8080/api/clubs?page=1&size=10
```

## 技术支持

- 查看 ACCOUNTS.md 了解测试账户详情
- 查看 PROJECT_SUMMARY.md 了解项目整体架构
- 查看 src/main/resources/static/README.md 了解前端说明

---

**祝你使用愉快！** 🎉

