<template>
  <div class="order-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>订单管理</span>
          <div>
            <el-select v-model="filterStatus" placeholder="订单状态" clearable style="width: 120px; margin-right: 10px" @change="loadOrders">
              <el-option label="进行中" :value="0" />
              <el-option label="已完成" :value="1" />
              <el-option label="已取消" :value="2" />
              <el-option label="异常" :value="3" />
            </el-select>
            <el-date-picker v-model="dateRange" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" style="width: 240px" @change="loadOrders" />
          </div>
        </div>
      </template>
      <el-table :data="orderList" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="username" label="用户" width="120" />
        <el-table-column prop="bikeNo" label="车辆编号" width="120" />
        <el-table-column prop="startStationName" label="起始站点" />
        <el-table-column prop="endStationName" label="结束站点" />
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column prop="duration" label="时长(分钟)" width="100" />
        <el-table-column prop="amount" label="金额" width="80">
          <template #default="{ row }">¥{{ row.amount }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button size="small" @click="handleDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next, total" :total="total" v-model:current-page="currentPage" @current-change="loadOrders" class="mt-20" />
    </el-card>
    
    <el-dialog v-model="detailVisible" title="订单详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ orderDetail.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="用户">{{ orderDetail.username }}</el-descriptions-item>
        <el-descriptions-item label="车辆编号">{{ orderDetail.bikeNo }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="statusType[orderDetail.status]">{{ statusText[orderDetail.status] }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="起始站点">{{ orderDetail.startStationName }}</el-descriptions-item>
        <el-descriptions-item label="结束站点">{{ orderDetail.endStationName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ orderDetail.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ orderDetail.endTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="骑行时长">{{ orderDetail.duration || 0 }}分钟</el-descriptions-item>
        <el-descriptions-item label="骑行距离">{{ orderDetail.distance || 0 }}km</el-descriptions-item>
        <el-descriptions-item label="订单金额">¥{{ orderDetail.amount }}</el-descriptions-item>
        <el-descriptions-item label="支付状态">{{ orderDetail.payStatus === 1 ? '已支付' : '未支付' }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { adminApi } from '@/api'

const orderList = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const filterStatus = ref('')
const dateRange = ref([])
const detailVisible = ref(false)
const orderDetail = reactive({})

const statusText = { 0: '进行中', 1: '已完成', 2: '已取消', 3: '异常' }
const statusType = { 0: 'primary', 1: 'success', 2: 'info', 3: 'danger' }

const loadOrders = async () => {
  loading.value = true
  try {
    const params = { page: currentPage.value, status: filterStatus.value }
    if (dateRange.value && dateRange.value.length === 2) {
      params.startDate = dateRange.value[0]
      params.endDate = dateRange.value[1]
    }
    const res = await adminApi.getOrderList(params)
    orderList.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleDetail = (row) => {
  Object.assign(orderDetail, row)
  detailVisible.value = true
}

onMounted(() => loadOrders())
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
