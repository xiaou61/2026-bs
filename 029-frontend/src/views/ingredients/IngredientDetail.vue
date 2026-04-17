<template>
  <section class="page-shell" v-loading="loading">
    <el-card v-if="ingredient" class="detail-card" shadow="never">
      <div class="hero-grid">
        <div class="detail-cover">
          <img :src="ingredient.image || fallbackImage" :alt="ingredient.name" />
        </div>
        <div class="detail-content">
          <p class="eyebrow">Ingredient Detail</p>
          <h1>{{ ingredient.name }}</h1>
          <div class="tag-row">
            <el-tag>{{ ingredient.category || '未分类' }}</el-tag>
            <el-tag type="success" effect="plain">性 {{ ingredient.nature || '平' }}</el-tag>
            <el-tag type="warning" effect="plain">味 {{ ingredient.taste || '甘' }}</el-tag>
          </div>
          <div class="info-box">
            <h3>功效说明</h3>
            <p>{{ ingredient.efficacy || '暂无说明' }}</p>
          </div>
          <div class="info-box">
            <h3>营养特点</h3>
            <p>{{ ingredient.nutrition || '暂无说明' }}</p>
          </div>
        </div>
      </div>
    </el-card>

    <el-card class="detail-card" shadow="never">
      <h2>禁忌搭配</h2>
      <el-empty v-if="!tabooedIngredients.length" description="当前没有录入禁忌食材" />
      <div v-else class="taboo-grid">
        <div
          v-for="item in tabooedIngredients"
          :key="item.id"
          class="taboo-item"
          @click="router.push({ name: 'ingredient-detail', params: { id: item.id } })"
        >
          <strong>{{ item.name }}</strong>
          <span>{{ item.category || '未分类' }}</span>
        </div>
      </div>
    </el-card>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getIngredientDetail, getTabooedIngredients } from '../../api/ingredient'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const ingredient = ref(null)
const tabooedIngredients = ref([])
const fallbackImage = 'https://via.placeholder.com/720x480?text=Ingredient'

const loadDetail = async () => {
  loading.value = true
  try {
    const ingredientId = Number(route.params.id)
    const [detail, tabooed] = await Promise.all([
      getIngredientDetail(ingredientId),
      getTabooedIngredients(ingredientId)
    ])
    ingredient.value = detail.data
    tabooedIngredients.value = tabooed.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载食材详情失败')
  } finally {
    loading.value = false
  }
}

onMounted(loadDetail)
</script>

<style scoped>
.page-shell {
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.detail-card {
  border-radius: 24px;
}

.hero-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

.detail-cover {
  min-height: 300px;
  border-radius: 22px;
  overflow: hidden;
}

.detail-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-content {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.eyebrow {
  color: #7b806f;
  text-transform: uppercase;
  letter-spacing: 0.16em;
  font-size: 12px;
}

.detail-content h1,
.detail-card h2 {
  color: #173d2e;
}

.tag-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.info-box {
  padding: 18px;
  border-radius: 18px;
  background: #f7f3e8;
}

.info-box p {
  color: #55655c;
  line-height: 1.8;
}

.taboo-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 12px;
  margin-top: 14px;
}

.taboo-item {
  padding: 16px;
  border-radius: 18px;
  background: #fff7ef;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.taboo-item span {
  color: #7b806f;
  font-size: 13px;
}

@media (max-width: 768px) {
  .hero-grid {
    grid-template-columns: 1fr;
  }
}
</style>
