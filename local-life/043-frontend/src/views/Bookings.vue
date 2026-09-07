<template>
  <div class="bookings">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的预约</span>
          <el-radio-group v-model="statusFilter" @change="loadBookings">
            <el-radio-button :value="undefined">全部</el-radio-button>
            <el-radio-button value="PENDING">待确认</el-radio-button>
            <el-radio-button value="CONFIRMED">已确认</el-radio-button>
            <el-radio-button value="IN_PROGRESS">进行中</el-radio-button>
            <el-radio-button value="COMPLETED">已完成</el-radio-button>
            <el-radio-button value="CANCELLED">已取消</el-radio-button>
          </el-radio-group>
        </div>
      </template>
      <el-table :data="bookingList" v-loading="loading">
        <el-table-column prop="id" label="预约编号" width="100" />
        <el-table-column prop="petId" label="宠物ID" width="100" />
        <el-table-column prop="providerId" label="服务商ID" width="100" />
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="totalAmount" label="总价" width="100">
          <template #default="{ row }">¥{{ row.totalAmount }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusTagType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.status === 'PENDING'" type="danger" link @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadBookings"
        style="margin-top: 20px"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getBookingPage, cancelBooking } from '../api'

const bookingList = ref<any[]>([])
const loading = ref(false)
const statusFilter = ref<string | undefined>(undefined)
const pagination = reactive({ current: 1, size: 10, total: 0 })

const statusText = (status: string) => {
  const map: Record<string, string> = {
    PENDING: '待确认',
    CONFIRMED: '已确认',
    IN_PROGRESS: '进行中',
    COMPLETED: '已完成',
    CANCELLED: '已取消'
  }
  return map[status] || '未知'
}

const statusTagType = (status: string) => {
  const map: Record<string, string> = {
    PENDING: 'warning',
    CONFIRMED: 'primary',
    IN_PROGRESS: 'success',
    COMPLETED: 'info',
    CANCELLED: 'danger'
  }
  return map[status] || 'info'
}

const loadBookings = async () => {
  loading.value = true
  try {
    const res: any = await getBookingPage({
      current: pagination.current,
      size: pagination.size,
      status: statusFilter.value
    })
    bookingList.value = res.data?.records || []
    pagination.total = res.data?.total || 0
  } finally {
    loading.value = false
  }
}

const handleCancel = async (id: number) => {
  await ElMessageBox.confirm('确定取消该预约吗？', '提示', { type: 'warning' })
  await cancelBooking(id)
  ElMessage.success('取消成功')
  loadBookings()
}

onMounted(() => loadBookings())
</script>

<style scoped>
.bookings {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
