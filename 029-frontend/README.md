# 中药食疗推荐交流平台 - 前端项目

## 项目概述
中药食疗推荐交流平台是一个专注于中药食疗的社区应用，用户可以浏览、发布食谱，学习食材知识，参与健康讨论。

## 技术栈
- **框架**: Vue 3 + Vite
- **路由**: Vue Router 4
- **状态管理**: Pinia
- **UI 库**: Element Plus
- **HTTP 请求**: Axios
- **样式**: CSS 3 + Sass
- **图表**: ECharts
- **时间处理**: dayjs

## 项目结构

```
029-frontend/
├── src/
│   ├── api/              # API 接口层
│   │   ├── request.js    # HTTP 请求配置和拦截器
│   │   ├── user.js       # 用户相关接口
│   │   ├── recipe.js     # 食谱相关接口
│   │   ├── topic.js      # 话题相关接口
│   │   └── ingredient.js # 食材相关接口
│   ├── components/       # 通用组件
│   │   ├── NavBar.vue    # 导航栏
│   │   └── AppFooter.vue # 页脚
│   ├── router/           # 路由配置
│   │   └── index.js
│   ├── stores/           # Pinia 状态管理
│   │   └── user.js       # 用户状态
│   ├── views/            # 页面组件
│   │   ├── auth/         # 认证页面（登录、注册）
│   │   ├── recipes/      # 食谱相关页面
│   │   ├── ingredients/  # 食材相关页面
│   │   ├── topics/       # 话题社区页面
│   │   ├── user/         # 用户中心页面
│   │   ├── Home.vue      # 首页
│   │   └── NotFound.vue  # 404 页面
│   ├── App.vue           # 主应用组件
│   ├── main.js           # 应用入口
│   └── style.css         # 全局样式
├── index.html            # HTML 入口
├── vite.config.js        # Vite 配置
├── package.json          # 依赖配置
└── README.md             # 项目说明
```

## 快速开始

### 安装依赖
```bash
npm install
```

### 开发环境运行
```bash
npm run dev
```

应用将在 `http://localhost:5173` 启动

### 生产环境构建
```bash
npm run build
```

### 预览构建结果
```bash
npm run preview
```

## 核心功能页面

### 已实现页面
- ✅ 首页 (`/`)
- ✅ 登录页 (`/login`)
- ✅ 注册页 (`/register`)

### 待实现页面（需要创建对应的 Vue 组件）
- [ ] 食谱列表 (`/recipes`) - 需要 `src/views/recipes/RecipeList.vue`
- [ ] 食谱详情 (`/recipes/:id`) - 需要 `src/views/recipes/RecipeDetail.vue`
- [ ] 发布食谱 (`/create-recipe`) - 需要 `src/views/recipes/RecipeForm.vue`
- [ ] 食材列表 (`/ingredients`) - 需要 `src/views/ingredients/IngredientList.vue`
- [ ] 食材详情 (`/ingredients/:id`) - 需要 `src/views/ingredients/IngredientDetail.vue`
- [ ] 话题列表 (`/topics`) - 需要 `src/views/topics/TopicList.vue`
- [ ] 话题详情 (`/topics/:id`) - 需要 `src/views/topics/TopicDetail.vue`
- [ ] 发起话题 (`/create-topic`) - 需要 `src/views/topics/TopicForm.vue`
- [ ] 我的食谱 (`/my-recipes`) - 需要 `src/views/user/MyRecipes.vue`
- [ ] 我的话题 (`/my-topics`) - 需要 `src/views/user/MyTopics.vue`
- [ ] 我的收藏 (`/collections`) - 需要 `src/views/user/Collections.vue`
- [ ] 个人资料 (`/profile`) - 需要 `src/views/user/Profile.vue`

## API 接口说明

### 基础信息
- **基础 URL**: `/api/v1`
- **认证方式**: Bearer Token (JWT)
- **请求头**: `Authorization: Bearer <token>`

### 用户相关接口
```javascript
// 注册
POST /user/register
{ username, email, password, nickname }

// 登录
POST /user/login
{ username, password }
// 返回: { token, user }

// 获取用户信息
GET /user/info

// 更新用户信息
PUT /user/info
{ ...userData }
```

### 食谱相关接口
```javascript
// 获取食谱列表
GET /recipe/list
参数: page, pageSize

// 获取食谱详情
GET /recipe/{id}/detail

// 创建食谱
POST /recipe/create
{ name, description, ingredients, steps, ... }

// 更新食谱
PUT /recipe/{id}

// 删除食谱
DELETE /recipe/{id}

// 搜索食谱
GET /recipe/search
参数: keyword

// 收藏食谱
POST /recipe/{id}/collect

// 获取我的食谱
GET /recipe/my-recipes
```

### 话题相关接口
```javascript
// 获取话题列表
GET /topic/list
参数: page, pageSize

// 获取话题详情
GET /topic/{id}/detail

// 创建话题
POST /topic/create
{ title, content, ... }

// 获取话题评论
GET /comment/topic/{topicId}

// 添加评论
POST /comment/create
{ topicId, content, ... }

// 删除评论
DELETE /comment/{id}
```

### 食材相关接口
```javascript
// 获取食材详情
GET /ingredient/{id}/detail

// 按分类获取食材
GET /ingredient/category/{category}

// 搜索食材
GET /ingredient/search
参数: keyword

// 获取食材禁忌
GET /ingredient/{id}/taboos
```

## 状态管理 (Pinia)

### useUserStore
```javascript
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

// 属性
userStore.token           // JWT 令牌
userStore.userInfo        // 用户信息对象
userStore.isLoggedIn      // 是否已登录 (computed)

// 方法
userStore.login(credentials)       // 登录
userStore.register(formData)       // 注册
userStore.fetchUserInfo()          // 获取用户信息
userStore.updateUserInfo(data)     // 更新用户信息
userStore.logout()                 // 登出
userStore.restoreState()           // 从 localStorage 恢复状态
```

## 开发规范

### 文件命名
- 组件文件: PascalCase (例: `NavBar.vue`)
- 路由页面: PascalCase (例: `RecipeList.vue`)
- 工具函数: camelCase (例: `request.js`)
- 样式类名: kebab-case (例: `recipe-card`)

### 组件规范
- 使用 Vue 3 Composition API (setup)
- 合理使用 scoped 样式
- 提取可复用逻辑到 composables
- 充分考虑响应式设计

### API 调用规范
- 所有 API 调用通过 `src/api` 下的模块
- 自动处理 token 添加和过期重登
- 统一的错误处理和消息提示

## 路由守卫

需要身份验证的路由（`meta.requiresAuth: true`）会自动重定向未登录用户到登录页面。

```javascript
// 受保护的路由示例
{
  path: '/my-recipes',
  name: 'my-recipes',
  component: () => import('../views/user/MyRecipes.vue'),
  meta: { requiresAuth: true }
}
```

## 样式指南

使用 Element Plus 的默认色彩方案，补充的品牌色：
- 主色: `#27ae60` (绿色 - 与中医健康理念呼应)
- 辅助色: `#667eea`、`#764ba2`

## 常见问题

### 如何添加新页面？
1. 在 `src/views` 中创建对应的 Vue 文件
2. 在 `src/router/index.js` 中添加路由
3. 根据需要在 `src/api` 中添加接口调用

### 如何处理 API 错误？
通过 `src/api/request.js` 中的响应拦截器统一处理，自动显示错误提示和处理认证失败。

### 如何修改 API 基础 URL？
修改 `src/api/request.js` 中的 `baseURL` 配置。

## 后续开发优先级

1. **第一阶段**: 完成食谱相关页面 (食谱列表、详情、发布)
2. **第二阶段**: 完成食材相关页面 (食材库、搜索、禁忌)
3. **第三阶段**: 完成社区话题功能 (话题列表、讨论、评论)
4. **第四阶段**: 完成用户中心 (个人资料、收藏、我的发布)
5. **第五阶段**: 优化和完善 (性能优化、响应式设计、SEO)

## 部署

### 环境变量配置
创建 `.env.production` 文件：
```
VITE_API_BASE_URL=https://api.example.com/api/v1
```

### 部署步骤
```bash
npm run build
# 将 dist 目录部署到服务器
```

## 许可证
MIT

## 联系方式
如有问题，请联系项目维护者。
