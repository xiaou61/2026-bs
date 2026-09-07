<template>
  <div class="home-container">
    <!-- 热烈欢迎区 -->
    <section class="banner">
      <div class="banner-content">
        <h1>🌿 中药食疗推荐交流平台</h1>
        <p>探索中医食疗之道，用美食滋养生命</p>
        <div class="banner-actions">
          <el-button type="primary" size="large" @click="goToRecipes">
            浏览食谱
          </el-button>
          <el-button size="large" @click="goToTopics">
            加入社区
          </el-button>
        </div>
      </div>
    </section>

    <!-- 快速导航 -->
    <section class="quick-nav">
      <div class="nav-card" @click="goToRecipes">
        <div class="nav-icon">🥗</div>
        <h3>食谱库</h3>
        <p>千万食疗食谱</p>
      </div>
      <div class="nav-card" @click="goToIngredients">
        <div class="nav-icon">🌾</div>
        <h3>食材库</h3>
        <p>食材功效与禁忌</p>
      </div>
      <div class="nav-card" @click="goToTopics">
        <div class="nav-icon">💬</div>
        <h3>社区</h3>
        <p>健康食疗讨论</p>
      </div>
      <div class="nav-card" v-if="!userStore.isLoggedIn" @click="goToRegister">
        <div class="nav-icon">✨</div>
        <h3>加入我们</h3>
        <p>开启食疗之旅</p>
      </div>
      <div class="nav-card" v-else @click="goToProfile">
        <div class="nav-icon">👤</div>
        <h3>我的账户</h3>
        <p>管理个人信息</p>
      </div>
    </section>

    <!-- 功能介绍 -->
    <section class="features">
      <h2>平台特色</h2>
      <div class="features-grid">
        <div class="feature-item">
          <h4>🎯 个性化推荐</h4>
          <p>根据您的健康需求，智能推荐适合的食疗方案</p>
        </div>
        <div class="feature-item">
          <h4>📚 专业食材库</h4>
          <p>详细的食材功效、用量和食用禁忌信息</p>
        </div>
        <div class="feature-item">
          <h4>👥 活跃社区</h4>
          <p>与食疗爱好者交流经验，分享健康生活</p>
        </div>
        <div class="feature-item">
          <h4>💾 收藏管理</h4>
          <p>收藏喜欢的食谱，随时查看和分享</p>
        </div>
      </div>
    </section>

    <!-- 最新内容 -->
    <section class="latest-content" v-if="latestRecipes.length > 0">
      <h2>最新食谱</h2>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" v-for="recipe in latestRecipes" :key="recipe.id">
          <div class="recipe-card" @click="goToRecipeDetail(recipe.id)">
            <div class="recipe-image">
              <el-image
                :src="recipe.coverImage || 'https://via.placeholder.com/300x200?text=Recipe'"
                fit="cover"
              />
            </div>
            <div class="recipe-info">
              <h4>{{ recipe.title }}</h4>
              <p class="description">{{ recipe.description }}</p>
              <div class="recipe-meta">
                <span>👁️ {{ recipe.views || 0 }} 次浏览</span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </section>

    <!-- 为空状态 -->
    <section class="empty-state" v-else>
      <el-empty description="暂无食谱数据" />
      <el-button type="primary" @click="goToRecipes">
        浏览所有食谱
      </el-button>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import * as recipeApi from '../api/recipe'

const router = useRouter()
const userStore = useUserStore()
const latestRecipes = ref([])

const goToRecipes = () => {
  router.push({ name: 'recipes' })
}

const goToIngredients = () => {
  router.push({ name: 'ingredients' })
}

const goToTopics = () => {
  router.push({ name: 'topics' })
}

const goToRegister = () => {
  router.push({ name: 'register' })
}

const goToProfile = () => {
  router.push({ name: 'profile' })
}

const goToRecipeDetail = (id) => {
  router.push({ name: 'recipe-detail', params: { id } })
}

const loadLatestRecipes = async () => {
  try {
    const response = await recipeApi.getRecipeList({
      page: 1,
      pageSize: 6
    })
    latestRecipes.value = response.data || []
  } catch (error) {
    console.error('加载食谱失败:', error)
  }
}

onMounted(() => {
  loadLatestRecipes()
})
</script>

<style scoped>
.home-container {
  padding-bottom: 40px;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 80px 20px;
  text-align: center;
  margin: -20px -20px 40px -20px;
}

.banner-content h1 {
  font-size: 42px;
  margin-bottom: 15px;
  font-weight: bold;
}

.banner-content p {
  font-size: 18px;
  margin-bottom: 30px;
  opacity: 0.9;
}

.banner-actions {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.quick-nav {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
  margin-bottom: 60px;
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
}

.nav-card {
  background: white;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 30px 20px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.nav-card:hover {
  border-color: #27ae60;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transform: translateY(-5px);
}

.nav-icon {
  font-size: 32px;
  margin-bottom: 10px;
}

.nav-card h3 {
  margin: 10px 0 5px 0;
  font-size: 16px;
  color: #333;
}

.nav-card p {
  margin: 0;
  font-size: 12px;
  color: #999;
}

.features {
  background: #f9f9f9;
  padding: 60px 20px;
  margin: 0 -20px;
}

.features h2 {
  text-align: center;
  font-size: 28px;
  margin-bottom: 40px;
  color: #333;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
  max-width: 1200px;
  margin: 0 auto;
}

.feature-item {
  background: white;
  padding: 30px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.feature-item h4 {
  font-size: 18px;
  margin-bottom: 15px;
  color: #27ae60;
}

.feature-item p {
  color: #666;
  line-height: 1.6;
  margin: 0;
}

.latest-content {
  max-width: 1200px;
  margin: 60px auto 0;
}

.latest-content h2 {
  font-size: 24px;
  margin-bottom: 30px;
  color: #333;
}

.recipe-card {
  background: white;
  border: 1px solid #eee;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
}

.recipe-card:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transform: translateY(-3px);
}

.recipe-image {
  height: 180px;
  overflow: hidden;
}

:deep(.recipe-image .el-image) {
  width: 100%;
  height: 100%;
}

.recipe-info {
  padding: 15px;
}

.recipe-info h4 {
  margin: 0 0 8px 0;
  font-size: 16px;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.description {
  margin: 0 0 10px 0;
  color: #666;
  font-size: 12px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.recipe-meta {
  font-size: 12px;
  color: #999;
}

.empty-state {
  text-align: center;
  padding: 40px 20px;
}

@media (max-width: 768px) {
  .banner-content h1 {
    font-size: 28px;
  }

  .banner-content p {
    font-size: 14px;
  }

  .banner-actions {
    flex-direction: column;
  }

  .quick-nav {
    grid-template-columns: repeat(2, 1fr);
  }

  .features-grid {
    grid-template-columns: 1fr;
  }
}
</style>
