<template>
  <div class="notice-detail-container">
    <el-card v-loading="loading">
      <template #header>
        <div class="card-header">
          <el-button @click="goBack">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
        </div>
      </template>

      <div v-if="notice" class="notice-content">
        <h1 class="notice-title">{{ notice.title }}</h1>
        
        <div class="notice-meta">
          <el-tag :type="getCategoryType(notice.category)">{{ notice.category }}</el-tag>
          <span class="meta-item">
            <el-icon><User /></el-icon>
            {{ notice.authorName }}
          </span>
          <span class="meta-item">
            <el-icon><Clock /></el-icon>
            {{ notice.createTime }}
          </span>
          <span class="meta-item">
            <el-icon><View /></el-icon>
            {{ notice.viewCount }} 次浏览
          </span>
        </div>

        <el-divider />

        <div class="notice-body" v-html="formatContent(notice.content)"></div>
      </div>

      <el-empty v-else description="公告不存在" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getNoticeDetail } from '@/api/notice'

const router = useRouter()
const route = useRoute()

const loading = ref(false)
const notice = ref(null)

// 获取公告详情
const fetchNoticeDetail = async () => {
  const id = route.params.id
  if (!id) return
  
  loading.value = true
  try {
    const res = await getNoticeDetail(id)
    if (res.code === 200) {
      notice.value = res.data
    }
  } catch (error) {
    console.error('获取公告详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取分类类型
const getCategoryType = (category) => {
  if (category === '通知') return 'primary'
  if (category === '活动') return 'success'
  return 'info'
}

// 格式化内容（将换行符转为<br>）
const formatContent = (content) => {
  return content?.replace(/\n/g, '<br>') || ''
}

// 返回
const goBack = () => {
  router.back()
}

onMounted(() => {
  fetchNoticeDetail()
})
</script>

<style scoped>
.notice-detail-container {
  padding: 0;
}

.card-header {
  display: flex;
  align-items: center;
}

.notice-content {
  padding: 20px;
}

.notice-title {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 20px;
  text-align: center;
}

.notice-meta {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  color: #909399;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
}

.notice-body {
  font-size: 16px;
  line-height: 1.8;
  color: #606266;
  text-align: justify;
  padding: 20px 0;
}

.notice-body :deep(br) {
  margin: 8px 0;
}
</style>

