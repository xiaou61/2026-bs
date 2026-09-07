# 测试账号

## 账号密码

### 管理员账号
- 用户名: admin
- 密码: 123456
- 加密后密码: e10adc3949ba59abbe56e057f20f883e
- 角色: admin

### 学生账号
- 用户名: student1
- 密码: 123456
- 加密后密码: e10adc3949ba59abbe56e057f20f883e
- 角色: student

### 企业账号
- 用户名: company1
- 密码: 123456
- 加密后密码: e10adc3949ba59abbe56e057f20f883e
- 角色: company
- 关联企业: 字节跳动 (company_id = 1)

## 密码加密说明

密码使用 Hutool 的 MD5 加密工具进行加密。

加密方法：
```java
import com.xiaou.recruitment.utils.PasswordUtil;

String password = "123456";
String encrypted = PasswordUtil.encrypt(password);
```

验证密码：
```java
boolean isValid = PasswordUtil.verify("123456", encrypted);
```

## 测试密码加密

运行测试类验证密码加密是否正确：
```bash
mvn test -Dtest=PasswordTest
```

或在 IDE 中直接运行：
`src/test/java/com/xiaou/recruitment/PasswordTest.java`

