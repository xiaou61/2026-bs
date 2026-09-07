# 校园失物招领系统 - 测试账号

## 管理员账号
- 用户名：admin
- 密码：admin
- 角色：管理员
- 权限：所有功能 + 用户管理 + 分类管理

## 普通用户账号

### 用户1
- 用户名：user1
- 密码：123456
- 真实姓名：张三
- 学号：2024001
- 手机：13800000001
- 邮箱：user1@school.com
- 角色：普通用户

### 用户2
- 用户名：user2
- 密码：123456
- 真实姓名：李四
- 学号：2024002
- 手机：13800000002
- 邮箱：user2@school.com
- 角色：普通用户

## 系统访问地址
- 系统地址：http://localhost:8086
- 登录页面：http://localhost:8086/login
- 注册页面：http://localhost:8086/register

## 功能说明

### 普通用户功能
1. 浏览失物/招领信息
2. 搜索和筛选物品
3. 发布失物信息
4. 发布招领信息
5. 管理自己发布的信息
6. 提交认领申请
7. 审核认领申请
8. 收藏物品
9. 接收通知
10. 修改个人信息

### 管理员功能
1. 所有普通用户功能
2. 用户管理（查看、禁用、删除）
3. 分类管理（添加、编辑、删除）

## 数据库信息
- 数据库名：lost_found_db
- 用户名：root
- 密码：1234
- 端口：3306

## 初始化步骤

1. 创建数据库并执行初始化脚本
```bash
mysql -u root -p < src/main/resources/sql/init.sql
```

2. 修改数据库配置
编辑 `src/main/resources/application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lost_found_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 您的数据库密码
```

3. 启动项目
```bash
mvn spring-boot:run
```

4. 访问系统
打开浏览器访问：http://localhost:8086

## 测试数据

系统已预置以下测试数据：

### 分类（7个）
1. 证件类
2. 电子产品
3. 书籍资料
4. 钥匙类
5. 卡类
6. 生活用品
7. 其他

### 失物信息（2条）
1. 丢失校园卡 - 由user1发布
2. 遗失黑色耳机 - 由user1发布

### 招领信息（2条）
1. 捡到高数教材 - 由user2发布
2. 拾到钥匙一串 - 由user2发布

## 注意事项

1. 密码使用MD5加密存储
2. admin密码MD5值：21232f297a57a5a743894a0e4a801fc3
3. 123456密码MD5值：e10adc3949ba59abbe56e057f20f883e
4. 系统使用Session进行身份认证
5. 上传文件保存在项目根目录的uploads文件夹
6. 建议使用Chrome或Edge浏览器访问

