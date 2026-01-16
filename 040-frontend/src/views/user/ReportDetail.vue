<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">举报详情</span>
      </template>
    </el-page-header>

    <el-card style="margin-top: 20px" v-loading="loading">
      <el-descriptions title="基本信息" :column="2" border>
        <el-descriptions-item label="举报编号">{{ report.reportNo }}</el-descriptions-item>
        <el-descriptions-item label="车牌号">{{ report.plateNumber }}</el-descriptions-item>
        <el-descriptions-item label="违停位置" :span="2">{{ report.location }}</el-descriptions-item>
        <el-descriptions-item label="违停描述" :span="2">{{ report.description }}</el-descriptions-item>
        <el-descriptions-item label="举报时间" :span="2">
          {{ formatTime(report.createdAt) }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag v-if="report.status === 'PENDING'" type="warning">待审核</el-tag>
          <el-tag v-else-if="report.status === 'APPROVED'" type="success">已通过</el-tag>
          <el-tag v-else-if="report.status === 'REJECTED'" type="danger">已拒绝</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="匿名举报">
          {{ report.isAnonymous ? '是' : '否' }}
        </el-descriptions-item>
      </el-descriptions>

      <div v-if="report.images" style="margin-top: 20px">
        <h4>违停照片</h4>
        <div class="images-container">
          <el-image
            v-for="(img, index) in images"
            :key="index"
            :src="img"
            fit="cover"
            style="width: 200px; height: 150px; margin-right: 10px"
            :preview-src-list="images"
          />
        </div>
      </div>

      <el-divider v-if="report.auditTime" />

      <el-descriptions v-if="report.auditTime" title="审核信息" :column="2" border>
        <el-descriptions-item label="审核时间" :span="2">
          {{ formatTime(report.auditTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="审核结果" :span="2" v-if="report.auditReason">
          {{ report.auditReason }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getReportById } from '@/api/report'

const route = useRoute()
const loading = ref(false)
const report = ref({})

const images = computed(() => {
  if (!report.value.images) return []
  return report.value.images.split(',').filter(img => img)
})

const loadReport = async () => {
  loading.value = true
  try {
    const res = await getReportById(route.params.id)
    report.value = res.data || {}
  } catch (error) {
    ElMessage.error('加载举报详情失败')
  } finally {
    loading.value = false
  }
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

onMounted(() => {
  loadReport()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.images-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
}
</style>
