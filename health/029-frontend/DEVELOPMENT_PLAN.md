# 029 前端开发计划

## 项目状态：✅ 基础框架完成

### 已完成
- ✅ 项目初始化和 Vite 配置
- ✅ Vue 3 + Element Plus 环境搭建
- ✅ 路由系统完整配置
- ✅ Pinia 状态管理集成
- ✅ HTTP 请求库和拦截器配置
- ✅ 用户认证流程 (登录、注册、守卫)
- ✅ 导航栏和页脚组件
- ✅ 首页基础框架
- ✅ 404 页面
- ✅ API 接口层完整定义

## 后续开发阶段

### 第一阶段：食谱模块（优先级：最高）
**目标**: 实现完整的食谱浏览、搜索、收藏功能

#### 需要创建的文件：
1. `src/views/recipes/RecipeList.vue` - 食谱列表页
   - 功能：
     - 分页显示食谱
     - 搜索和过滤功能
     - 食谱卡片展示（图片、名称、简介、浏览数）
     - 收藏按钮
   - API 调用：`getRecipeList()`, `searchRecipes()`, `collectRecipe()`

2. `src/views/recipes/RecipeDetail.vue` - 食谱详情页
   - 功能：
     - 完整食谱信息（图片、材料、步骤、功效）
     - 浏览量自动增加
     - 收藏/取消收藏
     - 营养信息展示
     - 用户评分
   - API 调用：`getRecipeDetail()`, `collectRecipe()`, `uncollectRecipe()`

3. `src/views/recipes/RecipeForm.vue` - 发布/编辑食谱页
   - 功能：
     - 表单验证
     - 食材选择（下拉或搜索）
     - 步骤动态添加
     - 图片上传预览
     - 功效标签选择
   - API 调用：`createRecipe()`, `updateRecipe()`

**预计工时**: 3-4 天

---

### 第二阶段：食材模块（优先级：高）
**目标**: 实现食材库和食材禁忌查询

#### 需要创建的文件：
1. `src/views/ingredients/IngredientList.vue` - 食材列表页
   - 功能：
     - 分类浏览食材（温、凉、平、热等）
     - 搜索功能
     - 食材卡片（图片、名称、功效简述）
   - API 调用：`getIngredientsByCategory()`, `searchIngredients()`

2. `src/views/ingredients/IngredientDetail.vue` - 食材详情页
   - 功能：
     - 食材详细信息（功效、用量、禁忌人群）
     - 食材禁忌表显示
     - 相关食谱推荐
   - API 调用：`getIngredientDetail()`, `getIngredientTaboos()`

**预计工时**: 2-3 天

---

### 第三阶段：社区话题模块（优先级：高）
**目标**: 实现话题讨论和评论功能

#### 需要创建的文件：
1. `src/views/topics/TopicList.vue` - 话题列表页
   - 功能：
     - 分页显示话题
     - 搜索功能
     - 话题卡片（标题、简介、发布者、评论数、时间）
     - 排序（最新、最热）
   - API 调用：`getTopicList()`, `searchTopics()`

2. `src/views/topics/TopicDetail.vue` - 话题详情和评论页
   - 功能：
     - 话题完整内容
     - 评论列表（分页）
     - 评论表单（需要认证）
     - 删除自己的评论
   - API 调用：`getTopicDetail()`, `getTopicComments()`, `addComment()`, `deleteComment()`

3. `src/views/topics/TopicForm.vue` - 发起话题页
   - 功能：
     - 话题表单（标题、内容）
     - 分类选择
     - 内容编辑器
   - API 调用：`createTopic()`, `updateTopic()`

**预计工时**: 3-4 天

---

### 第四阶段：用户中心模块（优先级：中）
**目标**: 完成用户信息管理和个人内容管理

#### 需要创建的文件：
1. `src/views/user/Profile.vue` - 个人资料页
   - 功能：
     - 显示和编辑用户信息（用户名、昵称、邮箱、头像）
     - 健康档案管理
     - 个人统计信息
   - API 调用：`getUserInfo()`, `updateUserInfo()`

2. `src/views/user/MyRecipes.vue` - 我的食谱页
   - 功能：
     - 列表显示用户发布的食谱
     - 编辑、删除操作
     - 发布状态显示
   - API 调用：`getMyRecipes()`, `deleteRecipe()`, `updateRecipe()`

3. `src/views/user/MyTopics.vue` - 我的话题页
   - 功能：
     - 列表显示用户发起的话题
     - 编辑、删除操作
   - API 调用：`getMyTopics()`, `deleteTopic()`, `updateTopic()`

4. `src/views/user/Collections.vue` - 我的收藏页
   - 功能：
     - 显示收藏的食谱
     - 取消收藏
     - 分类显示
   - API 调用：`getCollections()`, `removeCollection()`

**预计工时**: 2-3 天

---

### 第五阶段：优化和完善（优先级：中）
**目标**: 性能优化、响应式设计、体验优化

#### 待做项目：
- [ ] 响应式设计完善（移动设备适配）
- [ ] 图片懒加载
- [ ] 列表虚拟滚动优化（大数据量）
- [ ] 缓存策略优化
- [ ] 错误边界处理
- [ ] 加载动画优化
- [ ] SEO 优化
- [ ] 访问权限细化
- [ ] 数据统计和埋点

**预计工时**: 2-3 天

---

## 开发建议

### 通用模板
参考 `src/views/auth/Login.vue` 的结构创建新页面：
```vue
<template>
  <!-- 页面内容 -->
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import * as api from '../../api/...'

// 数据和逻辑
</script>

<style scoped>
/* 样式 */
</style>
```

### 常用模式

#### 获取列表数据
```javascript
const items = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)

const loadItems = async () => {
  try {
    loading.value = true
    const response = await api.getItemList({
      page: page.value,
      pageSize: pageSize.value
    })
    items.value = response.data || []
  } catch (error) {
    ElMessage.error(error?.msg || '加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadItems()
})
```

#### 搜索功能
```javascript
const searchKeyword = ref('')

const handleSearch = async () => {
  if (!searchKeyword.value.trim()) {
    loadItems() // 重置列表
    return
  }
  
  try {
    loading.value = true
    const response = await api.searchItems(searchKeyword.value)
    items.value = response.data || []
  } catch (error) {
    ElMessage.error('搜索失败')
  } finally {
    loading.value = false
  }
}
```

### 代码风格检查清单
- [ ] 组件名称采用 PascalCase
- [ ] 使用 `<script setup>` 语法
- [ ] 合理使用 `computed` 和 `ref`
- [ ] 正确处理异步操作和错误
- [ ] 提供用户反馈（加载、成功、错误提示）
- [ ] 样式使用 `scoped` 避免污染
- [ ] 图片和资源路径正确

## 测试建议

### 前端测试清单
- [ ] 登录/注册流程
- [ ] 路由导航和守卫
- [ ] 列表分页和搜索
- [ ] 收藏/取消收藏
- [ ] 表单验证
- [ ] 错误提示显示
- [ ] 响应式设计在不同屏幕尺寸
- [ ] API 超时处理
- [ ] Token 过期重登

## 性能指标目标
- 首屏加载时间: < 3s
- 列表切换: < 500ms
- 搜索响应: < 1s
- 图片加载: 带进度提示

## 部署检查清单
- [ ] 环境变量配置正确
- [ ] API 基础 URL 指向正确的后端服务
- [ ] 所有页面都能正确路由
- [ ] 错误提示能正确显示
- [ ] 登出后能正确清除认证信息
- [ ] 生产构建无错误警告

---

## 联系和支持
- 后端接口文档: `029-backend/PRD/029-中药餐饮推荐交流平台-PRD.md`
- 后端 API 地址: `http://localhost:8080/api/v1`
- 前端运行: `npm run dev` (localhost:5173)
