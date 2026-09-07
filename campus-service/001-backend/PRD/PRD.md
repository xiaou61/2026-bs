一、项目概述
1.1 项目名称

基于 Spring Boot + Vue 的校园事务管理系统

1.2 项目背景

传统校园事务处理流程繁琐、效率低，学生请假、宿舍报修、活动报名、公告查看等事务大多依靠人工或纸质流程完成。
为了提升效率和管理透明度，本系统实现校园事务的在线化、流程化管理，为学生、教师、管理员提供统一的事务管理平台。

二、项目目标

提供一个简洁的校园事务在线管理系统。

支持学生提交事务、教师审核、管理员监管。

实现请假、报修、公告、活动四大核心功能。

使用 JWT 登录认证机制，去除复杂权限框架。

前后端分离，界面简洁，易部署易展示。

三、系统架构与技术栈
层级	技术栈
前端	Vue 3 + Element Plus + Axios + Vue Router
后端	Spring Boot 3 + MyBatis-Plus + JWT
数据库	MySQL 8.0
工具	Maven + Node.js + Nginx
部署	单机部署即可（本地或云服务器）
简化架构图：
[Vue 前端] → [Spring Boot 后端] → [MyBatis-Plus ORM] → [MySQL 数据库]

四、系统角色与权限
角色	权限说明
管理员	用户管理、公告管理、事务审批、统计查看
教师	审批学生请假、发布公告、发布活动
学生	提交请假、报修申请、报名活动、查看公告
登录认证方案

用户登录成功后由后端签发 JWT Token

前端保存 token（localStorage）

请求接口时携带 token（Header: Authorization）

拦截器验证 token 有效性与角色

五、系统主要功能模块
5.1 用户模块

功能描述：
实现用户注册、登录、信息修改与角色分配。

功能点：

用户注册（默认学生角色）

用户登录（JWT 签发）

修改个人信息（头像、手机号、邮箱）

管理员添加教师、学生账号

用户分页列表、搜索、删除

5.2 请假管理模块

功能描述：
学生可提交请假申请，教师或管理员进行审批。

主要功能点：

学生提交请假申请

教师审批：同意/驳回

历史记录查询

请假统计（按状态/时间）

数据字段：

字段	类型	说明
id	bigint	主键
student_id	bigint	学生ID
reason	varchar	请假理由
start_time	datetime	开始时间
end_time	datetime	结束时间
status	int	状态（0待审、1通过、2驳回）
approver_id	bigint	审批人ID
create_time	datetime	创建时间
5.3 报修管理模块

功能描述：
学生提交宿舍或教室设备报修申请，管理员安排维修。

主要功能点：

学生上报（附说明与图片）

管理员分配维修任务

报修状态跟踪：未处理 / 处理中 / 已完成

报修记录查询与统计

5.4 公告管理模块

功能描述：
教师或管理员发布校园公告，学生可查看与搜索。

主要功能点：

公告发布 / 编辑 / 删除

公告分类（通知 / 活动 / 系统）

公告列表分页显示

公告详情查看

5.5 校园活动模块

功能描述：
教师发布校园活动，学生可报名参加。

主要功能点：

活动发布 / 修改 / 删除

学生报名活动

报名人数限制与统计

活动列表、详情、报名状态查询

5.6 数据统计模块

功能描述：
管理员查看系统运行统计信息。

主要功能点：

学生请假数、报修数统计

活动报名人数统计

报表图表展示（ECharts）

六、前端页面设计
页面名称	功能描述
登录页	输入账号密码登录
首页（Dashboard）	系统总览
请假申请页	填写、查看请假记录
报修申请页	提交、查看报修
公告中心页	查看公告
活动中心页	查看、报名活动
后台管理页	用户、公告、事务管理
个人中心页	修改资料、查看历史操作
七、数据库设计（简化示例）
表结构简要：
user
字段	类型	说明
id	bigint	主键
username	varchar	用户名
password	varchar	密码（加密存储）
role	varchar	角色（admin/teacher/student）
phone	varchar	电话
email	varchar	邮箱
avatar	varchar	头像地址
create_time	datetime	注册时间
leave_request

（请假表，如上节所示）

repair_request
字段	类型	说明
id	bigint	主键
student_id	bigint	学生ID
type	varchar	报修类型
location	varchar	报修位置
description	varchar	报修说明
image_url	varchar	图片地址
status	int	状态（0未处理、1处理中、2已完成）
create_time	datetime	创建时间
notice
字段	类型	说明
id	bigint	主键
title	varchar	标题
content	text	内容
category	varchar	分类
author_id	bigint	发布者
create_time	datetime	发布时间
activity + activity_signup

用于活动与报名的对应关系。

八、系统实现要点

登录认证：
Spring Boot 使用 JWT + 拦截器实现
登录时生成 token，放入响应头
前端请求携带 Authorization Header 验证

文件上传：
Spring Boot + MultipartFile 实现图片/附件上传
存储路径配置在 application.yml

接口安全：
简单基于角色的判断（如 if (user.getRole().equals("admin"))）
无需复杂权限框架

接口风格：
RESTful API，例如：

POST /api/login
POST /api/leave/apply
GET  /api/leave/list
POST /api/notice/add
GET  /api/activity/list