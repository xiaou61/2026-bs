# 基于微信小程序的课堂考勤签到APP

## 项目简介

这是一个基于微信小程序的课堂考勤签到系统，支持多种签到方式（普通签到、定位签到、二维码签到、数字签到），包含学生端和教师端功能，实现了完整的课堂考勤管理流程。

## 技术栈

### 后端
- Spring Boot 3.x
- MyBatis-Plus
- MySQL 8.0
- JWT 认证
- Maven

### 前端（微信小程序）
- 原生微信小程序
- WXML + WXSS + JavaScript

## 功能模块

### 学生端
- 微信登录/账号绑定
- 查看我的课程
- 查看今日课表
- 进行签到（支持四种方式）
- 查看考勤记录
- 考勤统计
- 请假申请
- 补签申请
- 消息通知

### 教师端
- 登录认证
- 课程管理
- 发起签到任务
- 实时查看签到情况
- 结束签到
- 刷新二维码
- 审批请假申请
- 审批补签申请
- 考勤统计分析
- 发布公告

### 管理员端
- 用户管理
- 学期管理
- 课程管理
- 数据统计

## 签到方式说明

1. **普通签到**：学生点击签到按钮即可完成签到
2. **定位签到**：需要学生在指定范围内才能签到，使用GPS定位验证
3. **二维码签到**：教师展示二维码，学生扫码签到，支持动态刷新
4. **数字签到**：教师展示6位数字码，学生输入数字完成签到

## 数据库设计

主要表结构：
- `sys_user` - 用户表（学生、教师、管理员）
- `semester` - 学期表
- `course` - 课程表
- `course_schedule` - 课程安排表
- `course_student` - 选课表
- `attendance_task` - 签到任务表
- `attendance_record` - 签到记录表
- `leave_request` - 请假申请表
- `makeup_request` - 补签申请表
- `attendance_stat` - 考勤统计表
- `notice` - 公告表
- `message` - 消息表

## 快速开始

### 1. 数据库配置

```sql
CREATE DATABASE classroom_attendance DEFAULT CHARACTER SET utf8mb4;
source sql/init.sql;
```

### 2. 后端配置

修改 `application.yml` 中的数据库连接信息。

### 3. 启动后端

```bash
mvn spring-boot:run
```

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 教师 | teacher1 | 123456 |
| 学生 | student1 | 123456 |

## 项目结构

```
050-backend/
├── src/main/java/com/xiaou/
│   ├── common/          # 公共类
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── mapper/          # 数据访问层
│   ├── service/         # 服务层
│   └── utils/           # 工具类
├── src/main/resources/
│   └── application.yml  # 配置文件
├── sql/
│   └── init.sql         # 数据库初始化脚本
└── pom.xml
```

## 作者

xiaou
