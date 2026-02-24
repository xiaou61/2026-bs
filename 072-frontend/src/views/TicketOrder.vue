<template>
  <div class="ticket-order">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="景点"><el-input v-model="query.spotName" placeholder="请输入景点名称" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="8" v-for="ticket in list" :key="ticket.id">
        <el-card class="ticket-card" shadow="hover">
          <div class="ticket-header">
            <h3>{{ ticket.spotName }}</h3>
            <el-tag>{{ ticket.name }}</el-tag>
          </div>
          <p class="desc">{{ ticket.description }}</p>
          <div class="footer">
            <span class="price">¥{{ ticket.price }}</span>
            <span class="stock">库存: {{ ticket.stock }}</span>
          </div>
          <el-button type="primary" @click="openOrder(ticket)" :disabled="ticket.stock <= 0">立即预订</el-button>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    <el-dialog v-model="orderDialog" title="门票预订" width="400px">
      <el-form :model="orderForm" label-width="80px">
        <el-form-item label="景点">{{ currentTicket?.spotName }}</el-form-item>
        <el-form-item label="票种">{{ currentTicket?.name }}</el-form-item>
        <el-form-item label="单价">¥{{ currentTicket?.price }}</el-form-item>
        <el-form-item label="数量"><el-input-number v-model="orderForm.quantity" :min="1" :max="Math.min(10, currentTicket?.stock || 10)" /></el-form-item>
        <el-form-item label="游玩日期"><el-date-picker v-model="orderForm.visitDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" :disabled-date="(date) => date < new Date()" /></el-form-item>
        <el-form-item label="总价"><span style="color:#f56c6c;font-size:20px;font-weight:bold">¥{{ (currentTicket?.price || 0) * orderForm.quantity }}</span></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="orderDialog = false">取消</el-button>
        <el-button type="primary" @click="submitOrder">确认预订</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getTicketList, createTicketOrder } from '../api'
import { ElMessage } from 'element-plus'

const query = ref({ spotName: '', pageNum: 1, pageSize: 9 })
const list = ref([])
const total = ref(0)
const orderDialog = ref(false)
const currentTicket = ref(null)
const orderForm = ref({ quantity: 1, visitDate: '' })

const loadData = async () => {
  const res = await getTicketList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

const openOrder = (ticket) => {
  currentTicket.value = ticket
  orderForm.value = { quantity: 1, visitDate: '' }
  orderDialog.value = true
}

const submitOrder = async () => {
  if (!orderForm.value.visitDate) {
    ElMessage.warning('请选择游玩日期')
    return
  }
  await createTicketOrder({ ticketTypeId: currentTicket.value.id, quantity: orderForm.value.quantity, visitDate: orderForm.value.visitDate })
  ElMessage.success('预订成功')
  orderDialog.value = false
  loadData()
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.ticket-card { margin-bottom: 20px; }
.ticket-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.ticket-header h3 { margin: 0; font-size: 16px; }
.desc { color: #666; font-size: 14px; margin-bottom: 15px; }
.footer { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
.price { color: #f56c6c; font-size: 22px; font-weight: bold; }
.stock { color: #999; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
