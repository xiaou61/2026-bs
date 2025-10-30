<template>
  <div class="my-booking">
    <el-card>
      <template #header>
        <span>我的预约</span>
      </template>
      
      <el-table :data="bookings" style="width: 100%" v-loading="loading">
        <el-table-column prop="venueId" label="场馆" width="150">
          <template #default="{ row }">
            场馆#{{ row.venueId }}
          </template>
        </el-table-column>
        
        <el-table-column prop="bookingDate" label="预约日期" width="120" />
        
        <el-table-column label="时间段" width="180">
          <template #default="{ row }">
            {{ row.startTime }} - {{ row.endTime }}
          </template>
        </el-table-column>
        
        <el-table-column prop="durationHours" label="时长(小时)" width="120" />
        
        <el-table-column prop="totalPrice" label="金额" width="100">
          <template #default="{ row }">
            ¥{{ row.totalPrice }}
          </template>
        </el-table-column>
        
        <el-table-column prop="companionCount" label="同伴人数" width="100" />
        
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusName(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="cancelReason" label="取消原因" min-width="150" show-overflow-tooltip />
        
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              @click="handleCheckin(row.id)"
              :disabled="row.status !== 'confirmed'"
            >
              签到
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleCancel(row)"
              :disabled="row.status === 'cancelled' || row.status === 'completed'"
            >
              取消
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadBookings"
        />
      </div>
    </el-card>
    
    <el-dialog v-model="cancelDialogVisible" title="取消预约" width="400px">
      <el-form>
        <el-form-item label="取消原因">
          <el-input
            v-model="cancelReason"
            type="textarea"
            :rows="3"
            placeholder="请说明取消原因..."
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancelDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="handleConfirmCancel">确认取消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyBookings, checkinBooking, cancelBooking } from '@/api/venue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const bookings = ref([])
const page = ref(1)
const size = ref(10)
const total = ref(0)

const cancelDialogVisible = ref(false)
const currentBooking = ref(null)
const cancelReason = ref('')

const getStatusName = (status) => {
  const map = {
    pending: '待确认',
    confirmed: '已确认',
    checked_in: '已签到',
    completed: '已完成',
    cancelled: '已取消'
  }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = {
    pending: 'warning',
    confirmed: 'success',
    checked_in: '',
    completed: 'info',
    cancelled: 'danger'
  }
  return map[status] || ''
}

const loadBookings = async () => {
  loading.value = true
  try {
    const res = await getMyBookings({ page: page.value, size: size.value })
    bookings.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleCheckin = async (id) => {
  try {
    await checkinBooking(id)
    ElMessage.success('签到成功')
    loadBookings()
  } catch (error) {
    console.error(error)
  }
}

const handleCancel = (booking) => {
  currentBooking.value = booking
  cancelReason.value = ''
  cancelDialogVisible.value = true
}

const handleConfirmCancel = async () => {
  try {
    await cancelBooking(currentBooking.value.id, { cancelReason: cancelReason.value })
    ElMessage.success('取消成功')
    cancelDialogVisible.value = false
    loadBookings()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadBookings()
})
</script>

<style scoped>
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>

