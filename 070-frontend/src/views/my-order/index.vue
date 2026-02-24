<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.orderNo" placeholder="订单号" clearable style="width: 240px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 160px">
          <el-option label="待支付" value="WAIT_PAY" />
          <el-option label="已支付" value="PAID" />
          <el-option label="已取消" value="CANCELED" />
          <el-option label="已完成" value="FINISHED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="210" />
        <el-table-column prop="movieTitle" label="影片" min-width="140" />
        <el-table-column prop="showTime" label="场次时间" width="180" />
        <el-table-column prop="seatInfo" label="座位" min-width="180" />
        <el-table-column prop="payAmount" label="实付" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handlePay(row)" v-if="row.status === 'WAIT_PAY'">余额支付</el-button>
            <el-button link type="warning" @click="handleCancel(row.id)" v-if="row.status === 'WAIT_PAY'">取消</el-button>
            <el-button link type="success" @click="handleFinish(row.id)" v-if="row.status === 'PAID'">完成</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { balancePay, cancelOrder, createPayment, finishOrder, getMyOrderList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', status: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyOrderList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handlePay = async row => {
  await createPayment({ orderId: row.id, payType: 'BALANCE' })
  await balancePay({ orderId: row.id })
  ElMessage.success('支付成功')
  loadData()
}

const handleCancel = async id => {
  await cancelOrder(id)
  ElMessage.success('订单已取消')
  loadData()
}

const handleFinish = async id => {
  await finishOrder(id)
  ElMessage.success('订单已完成')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
