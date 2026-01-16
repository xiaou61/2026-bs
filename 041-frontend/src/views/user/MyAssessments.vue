<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">我的测评记录</span>
      </template>
    </el-page-header>

    <el-table :data="assessments" v-loading="loading" style="margin-top: 20px">
      <el-table-column prop="id" label="记录ID" width="80" />
      <el-table-column prop="scaleId" label="量表ID" width="100" />
      <el-table-column prop="totalScore" label="总分" width="100" />
      <el-table-column prop="resultLevel" label="结果等级" width="120">
        <template #default="{ row }">
          <el-tag :type="getLevelType(row.resultLevel)">{{ row.resultLevel }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="report" label="报告" show-overflow-tooltip />
      <el-table-column prop="createdAt" label="测评时间" width="180" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyAssessments } from '@/api/assessment'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const assessments = ref([])

const loadAssessments = async () => {
  loading.value = true
  try {
    const res = await getMyAssessments()
    assessments.value = res.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const getLevelType = (level) => {
  const types = { '正常': 'success', '轻度': 'warning', '中度': 'danger', '重度': 'danger' }
  return types[level] || 'info'
}

onMounted(() => {
  loadAssessments()
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
</style>
