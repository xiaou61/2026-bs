<template>
  <div class="page-container">
    <el-card v-loading="loading">
      <el-page-header @back="$router.back()" />
      <h2>{{ article.title }}</h2>
      <div class="meta">
        <span>分类：{{ article.category }}</span>
        <span>浏览：{{ article.viewCount }}</span>
        <span>点赞：{{ article.likeCount }}</span>
      </div>
      <el-divider />
      <div class="content">{{ article.content }}</div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleById } from '@/api/article'
import { ElMessage } from 'element-plus'

const route = useRoute()
const loading = ref(false)
const article = ref({})

const loadArticle = async () => {
  loading.value = true
  try {
    const res = await getArticleById(route.params.id)
    article.value = res.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadArticle()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.el-card {
  max-width: 800px;
  margin: 0 auto;
}

h2 {
  margin: 20px 0;
}

.meta {
  display: flex;
  gap: 20px;
  color: #999;
  font-size: 14px;
}

.content {
  line-height: 1.8;
  color: #333;
  white-space: pre-wrap;
}
</style>
