# Thymeleaf 前端一体化说明

## 技术栈

- **Thymeleaf** - Spring Boot 官方模板引擎（服务端渲染）
- **Bootstrap 5** - 响应式UI框架
- **jQuery 3.7** - JavaScript库
- **Axios** - HTTP客户端（API调用）
- **Font Awesome 6** - 图标库
- **Marked.js** - Markdown解析器

## 特色功能

### ✨ 美观的UI设计

#### 1. 渐变色主题
- 登录页：紫色渐变背景 (#667eea → #764ba2)
- 侧边栏：深色渐变 (#2c3e50 → #34495e)
- 按钮：紫色渐变效果
- 统计卡片：多彩渐变图标

#### 2. 现代化样式
- 圆角卡片 (border-radius: 12px)
- 柔和阴影 (box-shadow)
- 平滑过渡动画 (transition)
- Hover悬浮效果
- 响应式布局

#### 3. 交互体验
- 自动保存提示
- 加载动画
- 表单验证
- 确认对话框

## 项目结构

```
012-backend/
├── src/main/
│   ├── java/com/xiaou/collabboard/
│   │   └── controller/
│   │       └── ViewController.java          # 页面路由控制器（返回Thymeleaf模板）
│   └── resources/
│       ├── application.yml                   # 已配置Thymeleaf
│       └── templates/                        # Thymeleaf模板目录
│           ├── login.html                    # 登录注册页（独立页面）
│           ├── dashboard.html                # 工作台
│           ├── documents.html                # 文档列表
│           ├── document-edit.html            # 文档编辑器（全屏）
│           ├── teams.html                    # 团队空间
│           ├── templates.html                # 模板市场
│           └── profile.html                  # 个人中心
└── pom.xml                                   # 已添加Thymeleaf依赖
```

**注意**: 每个页面都是独立完整的HTML，不使用布局继承，确保最大兼容性

## 已实现页面

### 1. 登录注册页 (`/login`) 
- ✅ 登录/注册Tab切换
- ✅ JWT Token存储（localStorage）
- ✅ 紫色渐变背景 + 白色卡片
- ✅ Font Awesome图标
- ✅ 表单验证
- ✅ 回车登录
- ✅ 已登录自动跳转

### 2. 工作台 (`/dashboard`)
- ✅ 4个渐变色统计卡片（紫、粉、蓝、橙）
- ✅ 悬浮动画效果
- ✅ 最近文档列表
- ✅ 快速创建入口
- ✅ 实时数据加载

### 3. 文档列表 (`/documents`)
- ✅ 网格布局文档卡片
- ✅ 悬浮上浮效果
- ✅ 彩色类型图标（蓝/绿/黄）
- ✅ 新建文档模态框
- ✅ 文档类型单选（白板/思维导图/笔记）
- ✅ 编辑和删除操作

### 4. 文档编辑器 (`/document/{id}`)
- ✅ 全屏沉浸式编辑
- ✅ 渐变色顶部工具栏
- ✅ **Markdown编辑器**:
  - 深色代码编辑区（VS Code风格）
  - 白色预览区
  - 实时预览
  - 语法高亮
  - 自动保存（3秒延迟 + 30秒定时）
  - 保存提示动画
- ✅ **白板编辑器**（演示界面）
- ✅ **思维导图编辑器**（演示界面）

### 5. 团队空间 (`/teams`)
- ✅ 团队卡片展示
- ✅ 成员和文档统计
- ✅ 创建团队模态框
- ✅ 创建时间显示

### 6. 模板市场 (`/templates`)
- ✅ 模板网格布局
- ✅ 官方/用户徽章
- ✅ 使用模板创建文档
- ✅ 使用次数统计
- ✅ 一键使用并跳转

### 7. 个人中心 (`/profile`)
- ✅ 左侧个人信息表单
- ✅ 修改密码表单
- ✅ 右侧统计卡片（渐变背景）
- ✅ 表单自动填充
- ✅ 修改后自动刷新

## 快速启动

### 1. 确保依赖已添加

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 2. 启动后端

```bash
# 运行主类
CollabBoardApplication
```

### 3. 访问应用

```
http://localhost:8012/
```

自动跳转到登录页或工作台（如果已登录）

## 页面路由

| 路由 | 页面 | 说明 |
|------|------|------|
| `/` | 首页 | 自动跳转到工作台或登录页 |
| `/login` | 登录注册 | 登录/注册表单 |
| `/dashboard` | 工作台 | 数据概览和最近文档 |
| `/documents` | 文档列表 | 我的文档管理 |
| `/document/{id}` | 文档编辑 | 编辑器页面 |
| `/teams` | 团队空间 | 团队管理 |
| `/templates` | 模板市场 | 模板浏览和使用 |
| `/profile` | 个人中心 | 个人信息和统计 |

## 测试账号

- 管理员: `admin` / `123456`
- 测试用户: `test1` / `123456`
- 测试用户: `test2` / `123456`

## 特色功能

### 1. Thymeleaf布局复用
使用 `layout.html` 作为主模板，其他页面继承该布局：

```html
<html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout}">
```

### 2. 侧边栏导航
- 固定侧边栏
- 当前页面高亮
- 底部用户信息

### 3. JWT Token管理
- localStorage存储
- Axios自动添加Authorization Header
- 401自动跳转登录

### 4. Markdown编辑器
- 左右分屏
- 实时预览
- 自动保存

### 5. 响应式设计
- Bootstrap Grid系统
- 移动端自适应

## API交互

所有页面通过 Axios 调用后端 REST API：

```javascript
// 配置
axios.defaults.baseURL = '/api';

// 请求拦截器
axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// 响应拦截器
axios.interceptors.response.use(
    response => response.data,
    error => {
        if (error.response?.status === 401) {
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);
```

## 后续扩展

### 1. 白板编辑器
集成 Fabric.js 或 Konva.js：

```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/fabric.js/5.3.0/fabric.min.js"></script>
<script>
const canvas = new fabric.Canvas('boardCanvas');
</script>
```

### 2. 思维导图
集成 jsMind 或 MindElixir：

```html
<script src="https://cdn.jsdelivr.net/npm/jsmind@0.5.1/js/jsmind.min.js"></script>
```

### 3. 实时协作
集成 Socket.io 或使用原生WebSocket：

```javascript
const socket = new WebSocket('ws://localhost:8012/ws/collab/123');
```

## 优势对比

### Thymeleaf 一体化方案
✅ 无需构建工具
✅ SEO友好（服务端渲染）
✅ 简单部署（单个jar包）
✅ Spring Boot原生支持
✅ 模板复用和继承
✅ 安全性好（无CORS问题）

### 前后端分离方案
❌ 需要构建工具
❌ SEO不友好
❌ 需要部署两个应用
❌ 需要配置CORS
✅ 开发体验好
✅ 前后端独立迭代

## 常见问题

### 1. 页面404
确保 `ViewController` 有对应的路由映射

### 2. 静态资源加载失败
检查CDN链接是否可访问，可以下载到本地使用

### 3. API调用失败
- 检查后端是否启动
- 检查Token是否过期
- 查看浏览器控制台错误

### 4. Thymeleaf缓存
开发环境建议关闭缓存：

```yaml
spring:
  thymeleaf:
    cache: false
```

## 性能优化

1. **CDN资源**: 使用CDN加速静态资源加载
2. **懒加载**: 大文档列表使用分页
3. **缓存**: 合理使用浏览器缓存
4. **压缩**: 生产环境启用Gzip压缩

## 总结

本项目采用 **Thymeleaf + Bootstrap** 一体化方案：
- ✅ 前后端完全集成
- ✅ 开箱即用
- ✅ 单jar包部署
- ✅ 核心功能完整
- ✅ 界面美观现代

非常适合毕业设计和快速原型开发！🎉

