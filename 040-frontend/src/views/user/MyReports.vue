<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">我的举报</span>
      </template>
    </el-page-header>

    <el-card style="margin-top: 20px">
      <el-table :data="reports" v-loading="loading">
        <el-table-column prop="reportNo" label="举报编号" width="150" />
        <el-table-column prop="plateNumber" label="车牌号" width="120" />
        <el-table-column prop="location" label="违停位置" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 'PENDING'" type="warning">待审核</el-tag>
            <el-tag v-else-if="row.status === 'APPROVED'" type="success">已通过</el-tag>
            <el-tag v-else-if="row.status === 'REJECTED'" type="danger">已拒绝</el-tag>
            <el-tag v-else type="info">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="举报时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="viewDetail(row.id)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div v-if="reports.length === 0 && !loading" style="text-align: center; padding: 40px; color: #909399">
        <el-empty description="暂无举报记录" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getMyReports } from '@/api/report'

const router = useRouter()
const loading = ref(false)
const reports = ref([])

const loadReports = async () => {
  loading.value = true
  try {
    const res = await getMyReports()
    reports.value = res.data || []
  } catch (error) {
    ElMessage.error('加载举报列表失败')
  } finally {
    loading.value = false
  }
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

const viewDetail = (id) => {
  router.push(`/report/${id}`)
}

onMounted(() => {
  loadReports()
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
</style>
