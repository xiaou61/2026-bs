<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">心理文章</span>
      </template>
    </el-page-header>

    <el-row :gutter="20" style="margin-top: 20px" v-loading="loading">
      <el-col :span="8" v-for="article in articles" :key="article.id">
        <el-card shadow="hover" class="article-card" @click="viewDetail(article.id)">
          <h3>{{ article.title }}</h3>
          <p class="category">{{ article.category }}</p>
          <p class="content">{{ article.content }}</p>
          <div class="stats">
            <span>👁 {{ article.viewCount }}</span>
            <span>👍 {{ article.likeCount }}</span>
            <span>⭐ {{ article.collectCount }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getArticles } from '@/api/article'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const articles = ref([])

const loadArticles = async () => {
  loading.value = true
  try {
    const res = await getArticles()
    articles.value = res.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const viewDetail = (id) => {
  router.push(`/article/${id}`)
}

onMounted(() => {
  loadArticles()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.page-title {
  font-size: 20px;
  font-weight: bold;
}

.article-card {
  margin-bottom: 20px;
  cursor: pointer;
}

.article-card h3 {
  margin-bottom: 10px;
}

.category {
  color: #409eff;
  font-size: 14px;
  margin-bottom: 10px;
}

.content {
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.stats {
  display: flex;
  gap: 15px;
  font-size: 14px;
  color: #999;
}
</style>
