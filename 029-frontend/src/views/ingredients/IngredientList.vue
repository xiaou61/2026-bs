<template>
  <section class="page-shell">
    <div class="page-head">
      <div>
        <p class="eyebrow">Ingredient Atlas</p>
        <h1>药食同源食材图谱</h1>
        <p>查看食材性味、适配方向和常见搭配禁忌，减少盲目进补。</p>
      </div>
    </div>

    <el-card class="toolbar-card" shadow="never">
      <div class="toolbar">
        <el-input
          v-model="keyword"
          placeholder="搜索食材名称"
          clearable
          @keyup.enter="loadIngredients"
          @clear="loadIngredients"
        />
        <el-select v-model="activeCategory" placeholder="分类" @change="loadIngredients">
          <el-option label="全部" value="全部" />
          <el-option v-for="category in categories" :key="category" :label="category" :value="category" />
        </el-select>
        <el-button type="primary" @click="loadIngredients">查询</el-button>
      </div>
    </el-card>

    <el-row v-loading="loading" :gutter="20">
      <el-col v-for="ingredient in ingredients" :key="ingredient.id" :xs="24" :sm="12" :lg="8">
        <el-card class="ingredient-card" shadow="hover" @click="openDetail(ingredient.id)">
          <div class="ingredient-image">
            <img :src="ingredient.image || fallbackImage" :alt="ingredient.name" />
          </div>
          <div class="ingredient-body">
            <div class="top-line">
              <h3>{{ ingredient.name }}</h3>
              <el-tag size="small" effect="plain">{{ ingredient.category || '未分类' }}</el-tag>
            </div>
            <p>{{ ingredient.efficacy || '暂无功效说明' }}</p>
            <div class="badge-line">
              <span>性味：{{ ingredient.nature || '平' }} / {{ ingredient.taste || '甘' }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="!loading && !ingredients.length" description="没有找到相关食材" />
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getIngredientsByCategory, searchIngredients } from '../../api/ingredient'

const router = useRouter()
const loading = ref(false)
const keyword = ref('')
const activeCategory = ref('全部')
const ingredients = ref([])
const categories = ['根茎类', '果实类', '豆类', '花叶类']
const fallbackImage = 'https://via.placeholder.com/640x400?text=Ingredient'

const loadIngredients = async () => {
  loading.value = true
  try {
    let response
    if (keyword.value.trim()) {
      response = await searchIngredients(keyword.value.trim())
    } else if (activeCategory.value !== '全部') {
      response = await getIngredientsByCategory(activeCategory.value)
    } else {
      response = await searchIngredients('')
    }
    ingredients.value = response.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载食材失败')
  } finally {
    loading.value = false
  }
}

const openDetail = (id) => {
  router.push({ name: 'ingredient-detail', params: { id } })
}

onMounted(loadIngredients)
</script>

<style scoped>
.page-shell {
  max-width: 1180px;
  margin: 0 auto;
}

.page-head {
  padding: 28px 32px;
  border-radius: 28px;
  background: linear-gradient(135deg, #f3eedf 0%, #d5e3c8 45%, #c4dcb7 100%);
  color: #183c2f;
  margin-bottom: 20px;
}

.eyebrow {
  font-size: 12px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  color: #7b806f;
  margin-bottom: 8px;
}

.toolbar-card {
  margin-bottom: 20px;
  border-radius: 20px;
}

.toolbar {
  display: grid;
  grid-template-columns: 1.4fr 0.7fr auto;
  gap: 12px;
}

.ingredient-card {
  margin-bottom: 20px;
  border-radius: 22px;
  overflow: hidden;
  cursor: pointer;
}

.ingredient-image {
  height: 200px;
}

.ingredient-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.ingredient-body {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.ingredient-body p {
  color: #55655c;
  line-height: 1.7;
}

.top-line {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.top-line h3 {
  color: #163729;
}

.badge-line {
  color: #7b806f;
  font-size: 13px;
}

@media (max-width: 768px) {
  .toolbar {
    grid-template-columns: 1fr;
  }
}
</style>
