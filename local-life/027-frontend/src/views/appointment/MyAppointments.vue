<template>
  <div class="my-appointments-page">
    <el-card>
      <h2>我的预约</h2>

      <!-- 状态筛选 -->
      <el-radio-group v-model="statusFilter" @change="fetchAppointments" style="margin-bottom: 20px">
        <el-radio-button label="">全部</el-radio-button>
        <el-radio-button label="待确认">待确认</el-radio-button>
        <el-radio-button label="已确认">已确认</el-radio-button>
        <el-radio-button label="服务中">服务中</el-radio-button>
        <el-radio-button label="已完成">已完成</el-radio-button>
        <el-radio-button label="已取消">已取消</el-radio-button>
      </el-radio-group>

      <!-- 预约列表 -->
      <el-table :data="appointments" v-loading="loading" stripe>
        <el-table-column prop="appointmentDate" label="预约日期" width="120" />
        <el-table-column prop="appointmentTime" label="预约时间" width="100" />
        <el-table-column label="门店" width="180">
          <template #default="{ row }">
            <!-- 这里需要关联查询门店名称，简化处理显示ID -->
            门店 #{{ row.storeId }}
          </template>
        </el-table-column>
        <el-table-column label="理发师" width="120">
          <template #default="{ row }">
            理发师 #{{ row.hairdresserId }}
          </template>
        </el-table-column>
        <el-table-column label="服务" width="150">
          <template #default="{ row }">
            服务 #{{ row.serviceId }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button
              v-if="row.status === '待确认' || row.status === '已确认'"
              type="danger"
              size="small"
              @click="handleCancel(row)"
            >
              取消预约
            </el-button>
            <el-button
              v-if="row.status === '已完成'"
              type="primary"
              size="small"
              @click="handleReview(row)"
            >
              去评价
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchAppointments"
        style="margin-top: 20px; text-align: center"
      />
    </el-card>

    <!-- 取消预约对话框 -->
    <el-dialog v-model="cancelDialogVisible" title="取消预约" width="400px">
      <el-form>
        <el-form-item label="取消原因">
          <el-input
            v-model="cancelReason"
            type="textarea"
            :rows="3"
            placeholder="请输入取消原因（可选）"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancelDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmCancel" :loading="cancelling">确认取消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyAppointments, cancelAppointment } from '@/api/appointment'

const router = useRouter()

const loading = ref(false)
const appointments = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const statusFilter = ref('')

const cancelDialogVisible = ref(false)
const cancelReason = ref('')
const cancelling = ref(false)
const currentAppointment = ref(null)

// 获取预约列表
const fetchAppointments = async () => {
  loading.value = true
  try {
    const res = await getMyAppointments({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      status: statusFilter.value || undefined
    })
    appointments.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取预约列表失败：', error)
  } finally {
    loading.value = false
  }
}

// 获取状态标签类型
const getStatusType = (status) => {
  const typeMap = {
    '待确认': 'warning',
    '已确认': 'success',
    '服务中': 'primary',
    '已完成': 'info',
    '已取消': 'danger',
    '超时取消': 'danger'
  }
  return typeMap[status] || 'info'
}

// 处理取消预约
const handleCancel = (appointment) => {
  currentAppointment.value = appointment
  cancelDialogVisible.value = true
  cancelReason.value = ''
}

// 确认取消
const confirmCancel = async () => {
  cancelling.value = true
  try {
    await cancelAppointment(currentAppointment.value.id, cancelReason.value)
    ElMessage.success('预约已取消')
    cancelDialogVisible.value = false
    fetchAppointments()
  } catch (error) {
    console.error('取消预约失败：', error)
  } finally {
    cancelling.value = false
  }
}

// 处理评价
const handleReview = (appointment) => {
  // 跳转到评价页面或打开评价对话框
  ElMessage.info('评价功能开发中')
}

onMounted(() => {
  fetchAppointments()
})
</script>

<style scoped>
.my-appointments-page {
  max-width: 1400px;
  margin: 30px auto;
  padding: 0 20px;
}

h2 {
  margin-bottom: 20px;
}
</style>
