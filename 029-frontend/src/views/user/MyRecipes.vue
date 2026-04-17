<template>
  <section class="page-shell">
    <div class="page-head">
      <div>
        <p class="eyebrow">My Workspace</p>
        <h1>我发布的食谱</h1>
      </div>
      <el-button type="primary" @click="router.push({ name: 'create-recipe' })">继续发布</el-button>
    </div>

    <div v-loading="loading" class="card-list">
      <el-card v-for="recipe in recipes" :key="recipe.id" class="item-card" shadow="never">
        <div class="item-head">
          <div>
            <h3>{{ recipe.title }}</h3>
            <p>{{ recipe.description }}</p>
          </div>
          <el-tag :type="statusType(recipe.status)">{{ statusText(recipe.status) }}</el-tag>
        </div>
        <div class="item-meta">
          <span>更新时间 {{ recipe.updateTime?.replace('T', ' ') }}</span>
          <span>{{ recipe.views || 0 }} 浏览</span>
        </div>
        <div class="actions">
          <el-button @click="router.push({ name: 'recipe-detail', params: { id: recipe.id } })">查看详情</el-button>
          <el-button text type="danger" @click="removeRecipe(recipe.id)">删除</el-button>
        </div>
      </el-card>
      <el-empty v-if="!loading && !recipes.length" description="你还没有发布食谱" />
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyRecipes } from '../../api/user'
import { deleteRecipe } from '../../api/recipe'

const router = useRouter()
const loading = ref(false)
const recipes = ref([])

const statusText = (status) => ({
  0: '草稿',
  1: '审核中',
  2: '已发布',
  3: '已下架'
}[status] || '未知状态')

const statusType = (status) => ({
  0: 'info',
  1: 'warning',
  2: 'success',
  3: 'danger'
}[status] || 'info')

const loadRecipes = async () => {
  loading.value = true
  try {
    const response = await getMyRecipes()
    recipes.value = response.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载我的食谱失败')
  } finally {
    loading.value = false
  }
}

const removeRecipe = async (id) => {
  try {
    await ElMessageBox.confirm('删除后无法恢复，确认继续吗？', '删除食谱', { type: 'warning' })
    await deleteRecipe(id)
    ElMessage.success('已删除')
    await loadRecipes()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error?.message || '删除失败')
    }
  }
}

onMounted(loadRecipes)
</script>

<style scoped>
.page-shell {
  max-width: 980px;
  margin: 0 auto;
}

.page-head,
.item-card {
  border-radius: 24px;
}

.page-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-end;
  padding: 28px 30px;
  margin-bottom: 20px;
  background: linear-gradient(135deg, #edf1e2 0%, #d5e3c8 100%);
}

.eyebrow {
  color: #7b806f;
  font-size: 12px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  margin-bottom: 8px;
}

.card-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.item-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.item-head h3 {
  color: #183c2f;
  margin-bottom: 8px;
}

.item-head p {
  color: #55655c;
  line-height: 1.7;
}

.item-meta {
  display: flex;
  gap: 16px;
  color: #7b806f;
  margin-top: 12px;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 16px;
}

@media (max-width: 768px) {
  .page-head,
  .item-head,
  .item-meta,
  .actions {
    flex-direction: column;
  }
}
</style>
