<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-select v-model="query.status" placeholder="票券状态" clearable style="width: 160px">
          <el-option label="未使用" value="UNUSED" />
          <el-option label="已使用" value="USED" />
        </el-select>
        <el-input v-model="verifyForm.ticketNo" placeholder="输入票券号核销" style="width: 280px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleVerify">核销</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="ticketNo" label="票券号" width="260" />
        <el-table-column prop="orderId" label="订单ID" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column prop="useTime" label="使用时间" width="180" />
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getTicketList, verifyTicket } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, status: '' })
const verifyForm = reactive({ ticketNo: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTicketList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleVerify = async () => {
  if (!verifyForm.ticketNo) {
    ElMessage.warning('请输入票券号')
    return
  }
  await verifyTicket({ ticketNo: verifyForm.ticketNo })
  ElMessage.success('核销成功')
  verifyForm.ticketNo = ''
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
