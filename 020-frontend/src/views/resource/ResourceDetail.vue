<template>
  <div class="resource-detail-container">
    <el-card v-if="resource">
      <template #header>
        <div class="header">
          <h2>{{ resource.title }}</h2>
          <el-button type="primary" @click="handleDownload" :loading="downloading">
            <el-icon><Download /></el-icon>
            下载资源 ({{ resource.points }} 积分)
          </el-button>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="分类">
          <el-tag>{{ resource.category }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="文件名">{{ resource.fileName }}</el-descriptions-item>
        <el-descriptions-item label="文件大小">{{ formatFileSize(resource.fileSize) }}</el-descriptions-item>
        <el-descriptions-item label="文件类型">{{ resource.fileType }}</el-descriptions-item>
        <el-descriptions-item label="所需积分">
          <el-tag type="warning">{{ resource.points }} 积分</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="评分">
          <el-rate v-model="resource.rating" disabled show-score />
        </el-descriptions-item>
        <el-descriptions-item label="下载量">{{ resource.downloadCount }}</el-descriptions-item>
        <el-descriptions-item label="浏览量">{{ resource.viewCount }}</el-descriptions-item>
        <el-descriptions-item label="上传时间" :span="2">{{ resource.createTime }}</el-descriptions-item>
        <el-descriptions-item label="资源描述" :span="2">
          {{ resource.description }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <h3>资源评价</h3>
      </template>

      <div class="rating-form">
        <el-form :inline="true" :model="ratingForm">
          <el-form-item label="评分">
            <el-rate v-model="ratingForm.rating" />
          </el-form-item>
          <el-form-item label="评价">
            <el-input v-model="ratingForm.comment" placeholder="请输入评价" style="width: 400px" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleRate">提交评价</el-button>
          </el-form-item>
        </el-form>
      </div>

      <el-divider />

      <div v-if="ratings.length">
        <div v-for="item in ratings" :key="item.id" class="rating-item">
          <div class="rating-header">
            <el-rate v-model="item.rating" disabled />
            <span class="rating-time">{{ item.createTime }}</span>
          </div>
          <div class="rating-comment">{{ item.comment }}</div>
        </div>
      </div>
      <el-empty v-else description="暂无评价" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getResourceDetail, downloadResource, rateResource, getResourceRatings } from '@/api/resource'
import { ElMessage } from 'element-plus'

const route = useRoute()
const resource = ref(null)
const ratings = ref([])
const downloading = ref(false)

const ratingForm = ref({
  rating: 5,
  comment: ''
})

const formatFileSize = (size) => {
  if (size < 1024) return size + ' B'
  if (size < 1024 * 1024) return (size / 1024).toFixed(2) + ' KB'
  return (size / (1024 * 1024)).toFixed(2) + ' MB'
}

const loadData = async () => {
  try {
    const res = await getResourceDetail(route.params.id)
    resource.value = res.data

    const ratingRes = await getResourceRatings(route.params.id)
    ratings.value = ratingRes.data
  } catch (error) {
    console.error(error)
  }
}

const handleDownload = async () => {
  downloading.value = true
  try {
    await downloadResource(route.params.id)
    ElMessage.success('下载成功')
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    downloading.value = false
  }
}

const handleRate = async () => {
  try {
    await rateResource({
      resourceId: route.params.id,
      rating: ratingForm.value.rating,
      comment: ratingForm.value.comment
    })
    ElMessage.success('评价成功')
    ratingForm.value.comment = ''
    loadData()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.resource-detail-container {
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h2 {
  margin: 0;
}

.rating-form {
  margin-bottom: 20px;
}

.rating-item {
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.rating-item:last-child {
  border-bottom: none;
}

.rating-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.rating-time {
  color: #909399;
  font-size: 12px;
}

.rating-comment {
  color: #606266;
}
</style>

