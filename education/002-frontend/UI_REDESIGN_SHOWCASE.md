# 002-frontend UI 美化展示

## 项目信息
- **项目名称**: 在线选课与成绩管理系统
- **技术栈**: Vue 3 + Element Plus + Vite
- **设计主题**: 学术蓝
- **美化完成时间**: 2026-05-29

## 设计理念

采用"学术蓝"设计理念，以深蓝色和金色为主色调，体现学术氛围和专业感。

## 颜色方案

| 颜色 | 色值 | 用途 |
|------|------|------|
| 学术蓝 | #1A237E | 主色调，管理员头部 |
| 翡翠绿 | #00897B | 学生端头部 |
| 琥珀金 | #F9A825 | 教师端头部，强调色 |
| 背景灰 | #F5F7FA | 页面背景 |

## 各角色配色

| 角色 | 头部颜色 | 侧边栏激活色 |
|------|----------|--------------|
| 管理员 | 学术蓝渐变 | 学术蓝渐变 |
| 学生 | 翡翠绿渐变 | 翡翠绿渐变 |
| 教师 | 琥珀金渐变 | 琥珀金渐变 |

## 美化页面清单

| 页面 | 文件路径 | 状态 |
|------|----------|------|
| 登录页面 | `src/views/Login.vue` | ✅ 已完成 |
| 注册页面 | `src/views/Register.vue` | 待美化 |
| 管理员布局 | `src/layout/AdminLayout.vue` | ✅ 已完成 |
| 学生布局 | `src/layout/StudentLayout.vue` | ✅ 已完成 |
| 教师布局 | `src/layout/TeacherLayout.vue` | ✅ 已完成 |
| 学生管理 | `src/views/admin/StudentManage.vue` | 待美化 |
| 教师管理 | `src/views/admin/TeacherManage.vue` | 待美化 |
| 课程管理 | `src/views/admin/CourseManage.vue` | 待美化 |
| 公告管理 | `src/views/admin/NoticeManage.vue` | 待美化 |
| 系统配置 | `src/views/admin/SystemConfig.vue` | 待美化 |
| 浏览课程 | `src/views/student/CourseList.vue` | 待美化 |
| 我的课表 | `src/views/student/MyCourses.vue` | 待美化 |
| 我的成绩 | `src/views/student/Grades.vue` | 待美化 |
| 教师课程 | `src/views/teacher/MyCourses.vue` | 待美化 |
| 成绩管理 | `src/views/teacher/GradeManage.vue` | 待美化 |
| 课程学生 | `src/views/teacher/CourseStudents.vue` | 待美化 |

## 设计特点

### 登录页面
- 学术蓝渐变背景
- 毛玻璃效果登录框
- 浮动圆形装饰
- 测试账号快速填充
- 滑入动画效果

### 管理员布局
- 学术蓝渐变头部
- 白色侧边栏
- 圆角菜单项
- 渐变激活状态

### 学生布局
- 翡翠绿渐变头部
- 与管理员统一的侧边栏风格
- 翡翠绿激活状态

### 教师布局
- 琥珀金渐变头部
- 与管理员统一的侧边栏风格
- 琥珀金激活状态

## 文件清单

```
002-frontend/
├── src/
│   ├── styles/
│   │   └── global.css          # 全局样式变量
│   ├── layout/
│   │   ├── AdminLayout.vue     # 管理员布局
│   │   ├── StudentLayout.vue   # 学生布局
│   │   └── TeacherLayout.vue   # 教师布局
│   ├── views/
│   │   ├── Login.vue           # 登录页面
│   │   ├── Register.vue        # 注册页面
│   │   ├── admin/              # 管理员页面
│   │   ├── student/            # 学生页面
│   │   └── teacher/            # 教师页面
│   └── main.js                 # 入口文件
└── UI_REDESIGN_SHOWCASE.md     # 本文档
```

## 与001-frontend的区别

| 特性 | 001-frontend | 002-frontend |
|------|--------------|--------------|
| 设计主题 | 清新校园风 | 学术蓝 |
| 主色调 | 绿色+蓝色 | 深蓝色+金色 |
| 侧边栏 | 深色渐变 | 白色背景 |
| 角色区分 | 统一绿色 | 不同角色不同颜色 |
| 登录背景 | 绿蓝渐变 | 深蓝渐变 |

## 总结

002-frontend采用"学术蓝"设计理念，通过不同角色使用不同的主题色，便于区分用户身份。白色侧边栏搭配渐变激活状态，整体风格简洁专业。
