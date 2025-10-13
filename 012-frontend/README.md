# 在线协作白板与笔记系统 - 前端

## 技术栈

- **Vue 3** - 渐进式JavaScript框架（CDN引入）
- **Element Plus** - Vue 3 UI组件库
- **Axios** - HTTP客户端
- **Marked.js** - Markdown解析器
- **Font Awesome** - 图标库

## 快速开始

### 1. 启动后端
确保后端服务已启动在 `http://localhost:8012`

### 2. 打开前端
直接用浏览器打开 `index.html` 文件即可

或使用简单的HTTP服务器：
```bash
# 使用 Python
python -m http.server 8080

# 使用 Node.js http-server
npx http-server -p 8080
```

然后访问: `http://localhost:8080`

## 功能特性

### ✅ 已实现功能

#### 用户功能
- [x] 用户注册/登录
- [x] 个人信息管理
- [x] 统计数据展示

#### 文档管理
- [x] 创建文档（白板/思维导图/笔记）
- [x] 文档列表展示
- [x] Markdown笔记编辑
- [x] 文档保存

#### 团队功能
- [x] 创建团队
- [x] 团队列表
- [x] 团队信息展示

#### 模板市场
- [x] 模板列表
- [x] 使用模板创建文档

### 🔧 待完善功能

以下功能需要集成第三方库：

#### 白板编辑器
建议集成：
- **Fabric.js** - Canvas图形库
- **Konva.js** - 2D Canvas框架
- **tldraw** - 白板组件

#### 思维导图
建议集成：
- **jsmind** - 思维导图库
- **MindElixir** - 思维导图框架
- **vue-mindmap** - Vue思维导图组件

#### 实时协作
建议集成：
- **Socket.io** - WebSocket实时通信
- **Y.js** - CRDT协同编辑

## 项目结构

```
012-frontend/
├── index.html          # 单文件应用入口
└── README.md          # 项目说明
```

## 主要组件

### LoginView
- 登录/注册表单
- JWT Token管理
- 自动跳转

### MainView
- 侧边栏导航
- 顶部工具栏
- 内容区域
- 多页面切换

### 文档编辑器
- 白板编辑器（待集成）
- 思维导图编辑器（待集成）
- Markdown编辑器（已实现）

## API对接

所有API请求默认指向: `http://localhost:8012/api`

### 请求拦截器
- 自动添加 Authorization Header
- Token过期自动跳转登录

### 响应拦截器
- 统一错误处理
- 401自动登出

## 测试账号

- 管理员: `admin` / `123456`
- 测试用户: `test1` / `123456`
- 测试用户: `test2` / `123456`

## 开发说明

### 修改API地址
在 `index.html` 中修改:
```javascript
const API_BASE_URL = 'http://localhost:8012/api';
```

### 添加新页面
在 `MainView` 组件的 `template` 中添加:
```html
<div v-else-if="currentPage === 'newpage'">
    <!-- 新页面内容 -->
</div>
```

在 `menuItems` 中添加菜单项:
```javascript
{ name: 'newpage', label: '新页面', icon: 'fas fa-icon' }
```

### 集成白板库示例

使用 Fabric.js:
```html
<!-- 引入Fabric.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/fabric.js/5.3.0/fabric.min.js"></script>

<script>
// 初始化画布
const canvas = new fabric.Canvas('boardCanvas', {
    width: 800,
    height: 600
});

// 添加矩形
const rect = new fabric.Rect({
    left: 100,
    top: 100,
    fill: 'red',
    width: 100,
    height: 100
});
canvas.add(rect);
</script>
```

## 浏览器兼容性

- Chrome (推荐)
- Firefox
- Safari
- Edge

## 注意事项

1. **跨域问题**: 如果遇到跨域，需要后端配置CORS
2. **文件协议**: 如果直接打开HTML文件，某些功能可能受限，建议使用HTTP服务器
3. **生产环境**: 实际部署时建议使用Vue CLI构建，性能更好

## 后续优化建议

1. **代码分离**: 将JS代码分离到独立文件
2. **组件化**: 进一步拆分组件
3. **状态管理**: 引入Pinia进行状态管理
4. **构建工具**: 使用Vite进行构建优化
5. **TypeScript**: 添加类型支持
6. **单元测试**: 添加测试覆盖

## License

MIT

