# 在线选课与成绩管理系统前端

## 技术栈
- Vue 3 + Composition API
- Vite
- Element Plus
- Vue Router
- Pinia
- Axios

## 快速启动

### 1. 安装依赖
```bash
npm install
```

### 2. 启动开发服务器
```bash
npm run dev
```

访问 http://localhost:3001

### 3. 构建生产版本
```bash
npm run build
```

## 项目结构
```
src/
├── api/              # API接口
├── layout/           # 布局组件
├── router/           # 路由配置
├── stores/           # Pinia状态管理
├── utils/            # 工具类
├── views/            # 页面组件
│   ├── Login.vue
│   ├── Register.vue
│   ├── student/      # 学生页面
│   ├── teacher/      # 教师页面
│   └── admin/        # 管理员页面
├── App.vue
└── main.js
```

## 功能模块

### 学生端
- 浏览课程：查看所有可选课程，支持筛选和搜索
- 我的课表：查看已选课程，支持退课
- 我的成绩：查看成绩单，显示平均分和绩点

### 教师端
- 我的课程：查看授课课程列表
- 查看学生：查看课程选课学生名单
- 成绩管理：录入和提交学生成绩

### 管理员端
- 学生管理：增删改查学生信息
- 教师管理：增删改查教师信息
- 课程管理：增删改查课程信息
- 公告管理：发布和管理系统公告
- 系统配置：配置选课时间等系统参数

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 学生 | student1 | 123456 |
| 学生 | student2 | 123456 |
| 教师 | teacher1 | 123456 |
| 教师 | teacher2 | 123456 |

