<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.orderNo" placeholder="订单号" clearable style="width: 220px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 160px">
          <el-option label="待支付" value="WAIT_PAY" />
          <el-option label="已支付" value="PAID" />
          <el-option label="已取消" value="CANCELED" />
          <el-option label="已退款" value="REFUNDED" />
          <el-option label="已改签" value="CHANGED" />
          <el-option label="已完成" value="FINISHED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="210" />
        <el-table-column prop="userId" label="用户ID" width="90" />
        <el-table-column prop="trainCode" label="车次" width="100" />
        <el-table-column label="区间" min-width="180">
          <template #default="{ row }">{{ row.departureStation }} → {{ row.arrivalStation }}</template>
        </el-table-column>
        <el-table-column prop="passengerNames" label="乘车人" min-width="120" />
        <el-table-column prop="seatInfo" label="座位信息" min-width="180" show-overflow-tooltip />
        <el-table-column prop="payAmount" label="实付金额" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="warning" @click="handleCancel(row.id)" v-if="row.status === 'WAIT_PAY'">取消</el-button>
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
import { cancelOrder, getOrderList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', status: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getOrderList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleCancel = async id => {
  await cancelOrder(id)
  ElMessage.success('订单已取消')
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
