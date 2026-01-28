<template>
  <div class="page-container">
    <el-card v-loading="loading">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <h2>{{ detail.title }}</h2>
          <el-button @click="$router.back()">返回</el-button>
        </div>
      </template>
      <div class="detail-meta">
        <span>分类: {{ detail.category }}</span>
        <span style="margin-left: 20px">浏览: {{ detail.viewCount }}</span>
        <span style="margin-left: 20px">点赞: {{ detail.likeCount }}</span>
        <span style="margin-left: 20px">发布时间: {{ detail.createTime }}</span>
      </div>
      <el-divider />
      <div class="detail-content" v-html="detail.content || '暂无内容'" style="white-space: pre-wrap" />
      <el-divider />
      <div style="text-align: center">
        <el-button type="primary" @click="handleLike">点赞</el-button>
        <el-button @click="handleCollect">收藏</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getKnowledgeById, likeKnowledge, collectKnowledge } from '@/api/knowledge'
import { ElMessage } from 'element-plus'

const route = useRoute()
const loading = ref(false)
const detail = ref({})

const loadDetail = async () => {
  loading.value = true
  try {
    const res = await getKnowledgeById(route.params.id)
    detail.value = res.data
  } finally {
    loading.value = false
  }
}

const handleLike = async () => {
  await likeKnowledge(route.params.id)
  ElMessage.success('点赞成功')
  loadDetail()
}

const handleCollect = async () => {
  await collectKnowledge(route.params.id)
  ElMessage.success('收藏成功')
}

onMounted(() => {
  loadDetail()
})
</script>

<style scoped>
.detail-meta {
  color: #999;
  font-size: 14px;
}

.detail-content {
  font-size: 16px;
  line-height: 1.8;
  min-height: 300px;
}
</style>
