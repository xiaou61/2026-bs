<template>
  <div class="host-booking">
    <h2>订单管理</h2>
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="全部" name="all" />
      <el-tab-pane label="待确认" name="1" />
      <el-tab-pane label="已确认" name="2" />
      <el-tab-pane label="已完成" name="4" />
    </el-tabs>
    <el-table :data="bookingList" v-loading="loading" stripe>
      <el-table-column prop="orderNo" label="订单号" min-width="180" />
      <el-table-column prop="checkInDate" label="入住日期" width="110" />
      <el-table-column prop="checkOutDate" label="离店日期" width="110" />
      <el-table-column prop="nights" label="晚数" width="80" />
      <el-table-column prop="guests" label="人数" width="80" />
      <el-table-column prop="totalPrice" label="总价" width="100">
        <template #default="{ row }">¥{{ row.totalPrice }}</template>
      </el-table-column>
      <el-table-column prop="contactName" label="联系人" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <el-button v-if="row.status === 1" type="primary" text @click="handleConfirm(row)">确认</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination v-model:current-page="current" :total="total" :page-size="10" layout="total, prev, pager, next" @change="loadData" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getHostBookings, confirmBooking } from '@/api/host'

const loading = ref(false)
const bookingList = ref<any[]>([])
const current = ref(1)
const total = ref(0)
const activeTab = ref('all')

const statusMap: Record<number, string> = {
  0: '待支付', 1: '待确认', 2: '已确认', 3: '进行中', 4: '已完成', 5: '已取消'
}
const statusTypeMap: Record<number, string> = {
  0: 'info', 1: 'warning', 2: 'primary', 3: 'primary', 4: 'success', 5: 'info'
}
const getStatusText = (status: number) => statusMap[status] || '未知'
const getStatusType = (status: number) => statusTypeMap[status] || 'info'

const loadData = async () => {
  loading.value = true
  try {
    const params: any = { current: current.value, size: 10 }
    if (activeTab.value !== 'all') params.status = Number(activeTab.value)
    const res: any = await getHostBookings(params)
    bookingList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  current.value = 1
  loadData()
}

const handleConfirm = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要确认此订单吗？', '提示')
    await confirmBooking(row.id)
    ElMessage.success('订单已确认')
    loadData()
  } catch (e) {}
}

onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.host-booking {
  h2 { margin-bottom: 20px; }
  .pagination { margin-top: 20px; text-align: right; }
}
</style>
