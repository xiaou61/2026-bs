<template>
  <div class="my-orders">
    <el-card>
      <template #header>
        <div class="header">
          <span>我的订单</span>
          <el-radio-group v-model="query.status" @change="loadData">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button label="pending">待支付</el-radio-button>
            <el-radio-button label="paid">已支付</el-radio-button>
            <el-radio-button label="used">已使用</el-radio-button>
            <el-radio-button label="cancelled">已取消</el-radio-button>
            <el-radio-button label="refunded">已退款</el-radio-button>
          </el-radio-group>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="200" />
        <el-table-column prop="spotName" label="景点" />
        <el-table-column prop="ticketName" label="票种" />
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="totalAmount" label="总价" width="100">
          <template #default="{ row }"><span style="color:#f56c6c">¥{{ row.totalAmount }}</span></template>
        </el-table-column>
        <el-table-column prop="visitDate" label="游玩日期" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="下单时间" width="170" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 'pending'" type="primary" link @click="payOrder(row)">支付</el-button>
            <el-button v-if="row.status === 'pending'" type="danger" link @click="cancelOrder(row)">取消</el-button>
            <el-button v-if="row.status === 'paid'" type="warning" link @click="refundOrder(row)">退款</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyOrders, payTicketOrder, cancelTicketOrder, refundTicketOrder } from '../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const query = ref({ status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const loading = ref(false)

const statusText = { pending: '待支付', paid: '已支付', used: '已使用', cancelled: '已取消', refunded: '已退款' }
const statusType = { pending: 'warning', paid: 'success', used: 'info', cancelled: 'info', refunded: 'danger' }

const loadData = async () => {
  loading.value = true
  const res = await getMyOrders(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
  loading.value = false
}

const payOrder = async (row) => {
  await ElMessageBox.confirm(`确认支付订单 ${row.orderNo}？将从钱包扣除 ¥${row.totalAmount}`, '支付确认')
  await payTicketOrder(row.id)
  ElMessage.success('支付成功')
  loadData()
}

const cancelOrder = async (row) => {
  await ElMessageBox.confirm('确认取消该订单？', '提示')
  await cancelTicketOrder(row.id)
  ElMessage.success('已取消')
  loadData()
}

const refundOrder = async (row) => {
  await ElMessageBox.confirm('确认申请退款？', '提示')
  await refundTicketOrder(row.id)
  ElMessage.success('退款成功')
  loadData()
}

onMounted(() => loadData())
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
