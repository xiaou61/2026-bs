<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.orderNo" placeholder="订单编号" style="width: 200px" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="待支付" :value="0" /><el-option label="待发货" :value="1" /><el-option label="待收货" :value="2" />
          <el-option label="已完成" :value="3" /><el-option label="已取消" :value="4" /><el-option label="退款中" :value="5" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单编号" width="180" />
        <el-table-column label="商品" min-width="200">
          <template #default="{ row }">
            <div v-for="item in row.items" :key="item.id" class="order-item">
              <el-image :src="item.productCover" style="width: 40px; height: 40px" fit="cover" />
              <span>{{ item.productName }} x{{ item.quantity }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="payAmount" label="金额" width="100">
          <template #default="{ row }">¥{{ row.payAmount }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusMap[row.status]?.type">{{ statusMap[row.status]?.text }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="下单时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="row.status === 0 && user?.role === 2" link type="primary" @click="handlePay(row.id)">支付</el-button>
            <el-button v-if="row.status === 0 && user?.role === 2" link type="info" @click="handleCancel(row.id)">取消</el-button>
            <el-button v-if="row.status === 1 && user?.role === 1" link type="success" @click="handleShip(row.id)">发货</el-button>
            <el-button v-if="row.status === 2 && user?.role === 2" link type="success" @click="handleReceive(row.id)">收货</el-button>
            <el-button v-if="row.status === 3 && user?.role === 2" link type="primary" @click="handleReview(row)">评价</el-button>
            <el-button v-if="row.status === 5 && user?.role === 1" link type="success" @click="handleRefund(row.id, true)">同意退款</el-button>
            <el-button v-if="row.status === 5 && user?.role === 1" link type="danger" @click="handleRefund(row.id, false)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="showReview" title="评价订单" width="500px">
      <el-form label-width="80px">
        <el-form-item label="评分"><el-rate v-model="reviewForm.rating" /></el-form-item>
        <el-form-item label="评价内容"><el-input v-model="reviewForm.content" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showReview = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getOrderPage, payOrder, cancelOrder, shipOrder, receiveOrder, handleRefund as handleRefundApi, addReview } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const user = computed(() => userStore.user)
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const showReview = ref(false)
const reviewOrderId = ref(null)
const reviewForm = reactive({ rating: 5, content: '' })
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', status: null })
const statusMap = {
  0: { text: '待支付', type: 'warning' },
  1: { text: '待发货', type: 'primary' },
  2: { text: '待收货', type: '' },
  3: { text: '已完成', type: 'success' },
  4: { text: '已取消', type: 'info' },
  5: { text: '退款中', type: 'danger' },
  6: { text: '已退款', type: 'info' }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getOrderPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handlePay = async (id) => {
  await payOrder(id)
  ElMessage.success('支付成功')
  loadData()
}

const handleCancel = async (id) => {
  await cancelOrder(id)
  ElMessage.success('已取消')
  loadData()
}

const handleShip = async (id) => {
  await shipOrder(id)
  ElMessage.success('已发货')
  loadData()
}

const handleReceive = async (id) => {
  await receiveOrder(id)
  ElMessage.success('已收货')
  loadData()
}

const handleRefund = async (id, agree) => {
  await handleRefundApi(id, { agree })
  ElMessage.success('操作成功')
  loadData()
}

const handleReview = (row) => {
  reviewOrderId.value = row.id
  reviewForm.rating = 5
  reviewForm.content = ''
  showReview.value = true
}

const submitReview = async () => {
  await addReview({ orderId: reviewOrderId.value, rating: reviewForm.rating, content: reviewForm.content })
  ElMessage.success('评价成功')
  showReview.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
.order-item { display: flex; align-items: center; gap: 10px; margin: 5px 0; }
</style>
