<template>
  <div class="notice-detail-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><Document /></el-icon>
        <span>公告详情</span>
      </div>
      <el-button @click="goBack" class="back-btn">
        <el-icon><ArrowLeft /></el-icon>
        返回列表
      </el-button>
    </div>

    <el-card class="detail-card" v-loading="loading">
      <div v-if="notice" class="notice-content">
        <!-- 标题区域 -->
        <div class="notice-header">
          <h1 class="notice-title">{{ notice.title }}</h1>
          <div class="notice-meta">
            <el-tag :type="getCategoryType(notice.category)" class="category-tag">
              {{ notice.category }}
            </el-tag>
            <div class="meta-list">
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
          </div>
        </div>

        <el-divider class="custom-divider" />

        <!-- 内容区域 -->
        <div class="notice-body" v-html="formatContent(notice.content)"></div>
      </div>

      <el-empty v-else description="公告不存在" :image-size="200" />
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

/* 页面标题 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding: 0 4px;
}

.page-title {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 24px;
  font-weight: 700;
  color: #2C3E50;
  font-family: var(--font-heading);
}

.page-title .el-icon {
  color: #7B1FA2;
  font-size: 28px;
}

.back-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(123, 31, 162, 0.3);
  transition: all 0.3s ease;
  color: white !important;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(123, 31, 162, 0.4);
}

/* 详情卡片 */
.detail-card {
  border-radius: 16px !important;
  border: none !important;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08) !important;
}

.detail-card :deep(.el-card__body) {
  padding: 32px;
}

/* 公告内容 */
.notice-content {
  max-width: 800px;
  margin: 0 auto;
}

/* 标题区域 */
.notice-header {
  text-align: center;
  margin-bottom: 32px;
}

.notice-title {
  font-size: 32px;
  font-weight: 700;
  color: #2C3E50;
  margin-bottom: 20px;
  line-height: 1.4;
  font-family: var(--font-heading);
}

.notice-meta {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.category-tag {
  border-radius: 20px !important;
  padding: 0 20px !important;
  font-weight: 600;
  font-size: 14px;
}

.meta-list {
  display: flex;
  align-items: center;
  gap: 24px;
  color: #909399;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
}

.meta-item .el-icon {
  color: #7B1FA2;
  font-size: 16px;
}

/* 分割线 */
.custom-divider {
  margin: 32px 0;
  border-color: #f0f0f0;
}

/* 内容区域 */
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

.notice-body :deep(p) {
  margin-bottom: 16px;
}

.notice-body :deep(h1),
.notice-body :deep(h2),
.notice-body :deep(h3) {
  color: #2C3E50;
  margin-top: 24px;
  margin-bottom: 12px;
}

.notice-body :deep(ul),
.notice-body :deep(ol) {
  padding-left: 24px;
  margin-bottom: 16px;
}

.notice-body :deep(li) {
  margin-bottom: 8px;
}

.notice-body :deep(blockquote) {
  border-left: 4px solid #7B1FA2;
  padding: 12px 20px;
  margin: 16px 0;
  background: #f8f9fa;
  border-radius: 0 8px 8px 0;
  color: #606266;
}

.notice-body :deep(code) {
  background: #f0f0f0;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  font-size: 14px;
}

.notice-body :deep(pre) {
  background: #2C3E50;
  color: #ecf0f1;
  padding: 16px;
  border-radius: 8px;
  overflow-x: auto;
  margin: 16px 0;
}

.notice-body :deep(pre code) {
  background: transparent;
  color: inherit;
  padding: 0;
}

/* 响应式 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
  
  .back-btn {
    width: 100%;
  }
  
  .detail-card :deep(.el-card__body) {
    padding: 20px;
  }
  
  .notice-title {
    font-size: 24px;
  }
  
  .meta-list {
    flex-direction: column;
    gap: 8px;
  }
}
</style>

