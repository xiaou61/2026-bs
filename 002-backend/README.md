# 在线选课与成绩管理系统后端

## 技术栈
- Spring Boot 3.2.0
- MyBatis 3.0.3
- MySQL 8
- JWT (jjwt 0.12.3)
- Spring Validation
- Lombok

## 快速启动

### 1. 数据库配置
修改 `application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/course_selection_system
    username: root
    password: root
```

### 2. 初始化数据库
执行 `src/main/resources/sql/init.sql` 文件

### 3. 启动项目
```bash
mvn spring-boot:run
```

服务将在 `http://localhost:8080` 启动

## 测试账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | admin | 123456 | 系统管理员 |
| 学生 | student1 | 123456 | 张三 |
| 学生 | student2 | 123456 | 李四 |
| 教师 | teacher1 | 123456 | 王教授 |
| 教师 | teacher2 | 123456 | 赵老师 |

## API接口

### 认证模块
- POST `/auth/login` - 登录
- POST `/auth/register` - 注册
- GET `/auth/info` - 获取当前用户信息
- POST `/auth/change-password` - 修改密码

### 用户管理
- GET `/users` - 用户列表
- GET `/users/{id}` - 用户详情
- POST `/users` - 创建用户
- PUT `/users/{id}` - 更新用户
- DELETE `/users/{id}` - 删除用户

### 课程管理
- GET `/courses` - 课程列表
- GET `/courses/{id}` - 课程详情
- GET `/courses/my` - 我的课程（教师）
- POST `/courses` - 创建课程
- PUT `/courses/{id}` - 更新课程
- DELETE `/courses/{id}` - 删除课程

### 选课管理
- GET `/selections/my` - 我的选课
- GET `/selections/course/{courseId}` - 课程选课学生列表
- POST `/selections/select/{courseId}` - 选课
- DELETE `/selections/drop/{courseId}` - 退课

### 成绩管理
- GET `/grades/my` - 我的成绩
- GET `/grades/student/{studentId}` - 学生成绩列表
- GET `/grades/course/{courseId}` - 课程成绩列表
- POST `/grades/submit` - 提交成绩

### 公告管理
- GET `/notices` - 公告列表
- GET `/notices/{id}` - 公告详情
- POST `/notices` - 发布公告
- PUT `/notices/{id}` - 更新公告
- DELETE `/notices/{id}` - 删除公告

### 系统配置
- GET `/system-config` - 配置列表
- GET `/system-config/{id}` - 配置详情
- GET `/system-config/key/{key}` - 根据key获取配置
- PUT `/system-config/{id}` - 更新配置

## 系统功能

### 学生功能
1. 在线选课、退课
2. 查看课表
3. 查看成绩

### 教师功能
1. 查看授课课程
2. 查看选课学生
3. 录入成绩

### 管理员功能
1. 管理学生、教师信息
2. 管理课程信息
3. 发布系统公告
4. 配置选课时间

