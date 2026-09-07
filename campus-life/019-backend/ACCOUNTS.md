# 测试账号

## 默认账号信息

### 管理员账号
- 用户名: `admin`
- 密码: `admin123`
- 角色: admin

### 教练账号
- 用户名: `coach1`
- 密码: `coach123`
- 角色: coach

### 学生账号
- 用户名: `student1`
- 密码: `student123`
- 角色: student

## 密码加密说明

本系统使用 BCrypt 算法对密码进行加密，加密后的密码无法解密，只能通过验证原密码是否匹配来进行登录验证。

### 测试密码加密

运行测试类 `PasswordTest` 可以生成新的加密密码：

```bash
mvn test -Dtest=PasswordTest
```

测试会输出：
1. 三个账号的密码加密后的哈希值
2. 验证密码是否正确的结果

### 使用新密码

如需修改初始密码：
1. 运行 `PasswordTest` 生成新的密码哈希值
2. 将哈希值更新到 `schema.sql` 文件的 INSERT 语句中
3. 重新执行数据库初始化脚本

## 数据库初始化

执行 `src/main/resources/schema.sql` 文件创建数据库和初始数据：

```bash
mysql -u root -p < src/main/resources/schema.sql
```

或在MySQL客户端中执行该文件内容。

## 注意事项

- 密码在数据库中以加密形式存储
- 登录时系统会自动验证密码
- 不要在生产环境使用默认密码
- 建议首次登录后立即修改密码

