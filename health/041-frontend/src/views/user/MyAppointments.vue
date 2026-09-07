<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">我的预约</span>
      </template>
    </el-page-header>

    <el-table :data="appointments" v-loading="loading" style="margin-top: 20px">
      <el-table-column prop="id" label="预约ID" width="80" />
      <el-table-column prop="counselorId" label="咨询师ID" width="100" />
      <el-table-column prop="price" label="价格" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="paymentStatus" label="支付状态" width="100" />
      <el-table-column prop="createdAt" label="预约时间" width="180" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyAppointments } from '@/api/appointment'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const appointments = ref([])

const loadAppointments = async () => {
  loading.value = true
  try {
    const res = await getMyAppointments()
    appointments.value = res.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const getStatusType = (status) => {
  const types = { 'PENDING': 'warning', 'CONFIRMED': 'success', 'COMPLETED': 'info', 'CANCELLED': 'danger' }
  return types[status] || 'info'
}

onMounted(() => {
  loadAppointments()
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
