# 001-frontend UI 美化展示

## 项目信息
- **项目名称**: 校园事务管理系统
- **技术栈**: Vue 3 + Element Plus + Vite
- **设计主题**: 清新校园风
- **美化完成时间**: 2026-05-29

## 美化页面清单

| 页面 | 文件路径 | 设计主题 | 状态 |
|------|----------|----------|------|
| 登录页面 | `src/views/Login.vue` | 渐变背景+毛玻璃效果 | ✅ 已完成 |
| 注册页面 | `src/views/Register.vue` | 与登录页统一风格 | ✅ 已完成 |
| 主布局 | `src/layout/MainLayout.vue` | 深绿色渐变侧边栏 | ✅ 已完成 |
| 仪表盘 | `src/views/Dashboard.vue` | 欢迎区域+统计卡片 | ✅ 已完成 |
| 请假管理 | `src/views/Leave/Index.vue` | 绿色主题 | ✅ 已完成 |
| 报修管理 | `src/views/Repair/Index.vue` | 蓝色主题 | ✅ 已完成 |
| 公告管理 | `src/views/Notice/Index.vue` | 紫色主题 | ✅ 已完成 |
| 公告详情 | `src/views/Notice/Detail.vue` | 紫色主题 | ✅ 已完成 |
| 活动管理 | `src/views/Activity/Index.vue` | 橙色主题 | ✅ 已完成 |
| 活动详情 | `src/views/Activity/Detail.vue` | 橙色主题 | ✅ 已完成 |
| 用户管理 | `src/views/User/Index.vue` | 蓝色主题 | ✅ 已完成 |
| 个人中心 | `src/views/Profile.vue` | 绿蓝渐变主题 | ✅ 已完成 |

## 设计规范

### 颜色方案
- **主色调**: #2E7D32 (校园绿)
- **辅助色**: #1565C0 (天空蓝)
- **强调色**: #FF6F00 (活力橙)
- **背景色**: #F5F7FA (浅灰)
- **文字色**: #2C3E50 (深灰)

### 各页面配色
| 页面 | 主色 | 用途 |
|------|------|------|
| 登录/注册 | #2E7D32 → #1565C0 | 渐变背景 |
| 侧边栏 | #1B5E20 → #2E7D32 → #1565C0 | 三色渐变 |
| 请假管理 | #2E7D32 | 按钮、标签 |
| 报修管理 | #1565C0 | 按钮、标签 |
| 公告管理 | #7B1FA2 | 按钮、标签 |
| 活动管理 | #FF6F00 | 按钮、标签 |
| 用户管理 | #409EFF | 按钮、标签 |

### 组件样式
- **卡片**: 圆角16px，阴影效果
- **按钮**: 渐变背景，悬停上移动画
- **表格**: 斑马纹，圆角12px边框
- **标签**: 圆角20px
- **分页**: 自定义激活渐变样式
- **对话框**: 圆角20px，渐变头部

### 动画效果
- **页面切换**: 淡入淡出+Y轴位移
- **卡片悬停**: 上升4px+阴影增强
- **按钮悬停**: 上升2px+阴影增强
- **登录框**: 从下方滑入动画
- **背景装饰**: 浮动动画

## 技术实现

### 全局样式变量 (src/styles/global.css)
```css
:root {
  --primary-color: #2E7D32;
  --primary-light: #4CAF50;
  --primary-dark: #1B5E20;
  --secondary-color: #1565C0;
  --secondary-light: #42A5F5;
  --accent-color: #FF6F00;
  --accent-light: #FFA726;
  --bg-color: #F5F7FA;
  --text-primary: #2C3E50;
  --font-heading: 'Noto Sans SC', 'PingFang SC', sans-serif;
  --shadow-sm: 0 2px 4px rgba(0, 0, 0, 0.1);
  --shadow-md: 0 4px 8px rgba(0, 0, 0, 0.12);
  --radius-md: 8px;
  --radius-lg: 12px;
  --transition-normal: 0.3s ease;
}
```

### 响应式设计
- 移动端优先设计
- 断点：480px, 768px, 1200px
- 弹性布局和网格系统
- 自适应组件大小

## 文件清单

```
001-frontend/
├── src/
│   ├── styles/
│   │   └── global.css          # 全局样式变量
│   ├── layout/
│   │   └── MainLayout.vue      # 主布局
│   ├── views/
│   │   ├── Login.vue           # 登录页面
│   │   ├── Register.vue        # 注册页面
│   │   ├── Dashboard.vue       # 仪表盘
│   │   ├── Leave/
│   │   │   └── Index.vue       # 请假管理
│   │   ├── Repair/
│   │   │   └── Index.vue       # 报修管理
│   │   ├── Notice/
│   │   │   ├── Index.vue       # 公告管理
│   │   │   └── Detail.vue      # 公告详情
│   │   ├── Activity/
│   │   │   ├── Index.vue       # 活动管理
│   │   │   └── Detail.vue      # 活动详情
│   │   ├── User/
│   │   │   └── Index.vue       # 用户管理
│   │   └── Profile.vue         # 个人中心
│   └── main.js                 # 入口文件(引入全局样式)
├── UI_REDESIGN_PLAN.md         # 设计方案
└── UI_REDESIGN_SHOWCASE.md     # 本文档
```

## 总结

001-frontend的UI美化采用了"清新校园"设计理念：
- 每个功能模块使用不同的主题色，便于区分
- 统一的圆角、阴影、动画效果
- 渐变按钮和卡片设计
- 响应式布局支持多端访问
- 全局CSS变量便于主题切换
