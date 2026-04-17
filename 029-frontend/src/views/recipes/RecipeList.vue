<template>
  <section class="page-shell">
    <div class="page-hero">
      <div>
        <p class="eyebrow">Herbal Kitchen</p>
        <h1>节气食养食谱库</h1>
        <p class="hero-copy">
          聚合药食同源灵感、家常调理方案和创作者实战经验，快速找到适合当前体感的食疗搭配。
        </p>
      </div>
      <el-button type="primary" size="large" @click="router.push({ name: 'create-recipe' })">
        发布食谱
      </el-button>
    </div>

    <el-card class="toolbar-card" shadow="never">
      <div class="toolbar">
        <el-input
          v-model="keyword"
          placeholder="搜索食谱名称或描述"
          clearable
          @keyup.enter="handleSearch"
          @clear="loadRecipes"
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>
    </el-card>

    <el-row v-loading="loading" :gutter="20" class="grid">
      <el-col v-for="recipe in recipes" :key="recipe.id" :xs="24" :sm="12" :lg="8">
        <el-card class="recipe-card" shadow="hover" @click="openDetail(recipe.id)">
          <div class="cover">
            <img :src="recipe.coverImage || fallbackImage" :alt="recipe.title" />
          </div>
          <div class="card-body">
            <div class="card-header">
              <h3>{{ recipe.title }}</h3>
              <el-tag size="small" effect="plain">{{ recipe.seasons || '四季可用' }}</el-tag>
            </div>
            <p class="description">{{ recipe.description }}</p>
            <div class="meta-grid">
              <span>难度 {{ recipe.difficulty ?? '-' }}</span>
              <span>{{ recipe.cookTime || 0 }} 分钟</span>
              <span>{{ recipe.views || 0 }} 次浏览</span>
              <span>{{ recipe.collects || 0 }} 次收藏</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="!loading && !recipes.length" description="暂时没有找到匹配的食谱" />
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getRecipeList, searchRecipes } from '../../api/recipe'

const router = useRouter()
const loading = ref(false)
const keyword = ref('')
const recipes = ref([])
const fallbackImage = 'https://via.placeholder.com/800x480?text=Herbal+Recipe'

const loadRecipes = async () => {
  loading.value = true
  try {
    const response = keyword.value.trim()
      ? await searchRecipes(keyword.value.trim())
      : await getRecipeList({ page: 1, pageSize: 12 })
    recipes.value = response.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载食谱失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  loadRecipes()
}

const resetSearch = () => {
  keyword.value = ''
  loadRecipes()
}

const openDetail = (id) => {
  router.push({ name: 'recipe-detail', params: { id } })
}

onMounted(loadRecipes)
</script>

<style scoped>
.page-shell {
  max-width: 1200px;
  margin: 0 auto;
  padding-bottom: 40px;
}

.page-hero {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  align-items: flex-end;
  padding: 32px;
  border-radius: 28px;
  background:
    radial-gradient(circle at top left, rgba(208, 237, 214, 0.92), transparent 38%),
    linear-gradient(135deg, #183c2f 0%, #285844 54%, #c7813e 100%);
  color: #f7f4ed;
  margin-bottom: 24px;
}

.eyebrow {
  letter-spacing: 0.24em;
  text-transform: uppercase;
  font-size: 12px;
  opacity: 0.75;
  margin-bottom: 10px;
}

.page-hero h1 {
  font-size: 36px;
  margin-bottom: 10px;
}

.hero-copy {
  max-width: 620px;
  line-height: 1.7;
  opacity: 0.88;
}

.toolbar-card {
  margin-bottom: 24px;
  border-radius: 20px;
}

.toolbar {
  display: flex;
  gap: 12px;
}

.grid {
  row-gap: 20px;
}

.recipe-card {
  height: 100%;
  border-radius: 22px;
  overflow: hidden;
  cursor: pointer;
}

.cover {
  height: 220px;
  overflow: hidden;
}

.cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-body {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.card-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
}

.card-header h3 {
  font-size: 20px;
  color: #163729;
}

.description {
  color: #50645a;
  line-height: 1.7;
  min-height: 48px;
}

.meta-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 8px;
  color: #7b806f;
  font-size: 13px;
}

@media (max-width: 768px) {
  .page-hero {
    flex-direction: column;
    align-items: stretch;
  }

  .toolbar {
    flex-direction: column;
  }
}
</style>
