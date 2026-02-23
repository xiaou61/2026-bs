<template>
  <div class="order-detail-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>订单详情</span>
          <el-button @click="goBack">返回</el-button>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag :type="getStatusType(order.status)">
            {{ getStatusText(order.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="用户名">{{ order.username }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ order.phone }}</el-descriptions-item>
        <el-descriptions-item label="电影名称">{{ order.movieTitle }}</el-descriptions-item>
        <el-descriptions-item label="影院名称">{{ order.cinemaName }}</el-descriptions-item>
        <el-descriptions-item label="影厅">{{ order.hallName }}</el-descriptions-item>
        <el-descriptions-item label="放映日期">{{ order.showDate }}</el-descriptions-item>
        <el-descriptions-item label="放映时间">{{ order.showTime }}</el-descriptions-item>
        <el-descriptions-item label="座位信息">{{ order.seatInfo }}</el-descriptions-item>
        <el-descriptions-item label="单价">¥{{ order.price }}</el-descriptions-item>
        <el-descriptions-item label="数量">{{ order.quantity }}张</el-descriptions-item>
        <el-descriptions-item label="优惠券">
          {{ order.couponId ? `已使用优惠券 (ID: ${order.couponId})` : '未使用' }}
        </el-descriptions-item>
        <el-descriptions-item label="优惠金额">¥{{ order.discountAmount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="订单总额">
          <span style="color: #f56c6c; font-size: 18px; font-weight: bold;">¥{{ order.totalAmount }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ order.createTime }}</el-descriptions-item>
        <el-descriptions-item label="支付时间">{{ order.payTime || '未支付' }}</el-descriptions-item>
      </el-descriptions>

      <div v-if="order.status === 'unpaid'" class="action-buttons">
        <el-button type="success" size="large" @click="handlePay">立即支付</el-button>
        <el-button type="danger" size="large" @click="handleCancel">取消订单</el-button>
      </div>

      <div v-if="order.status === 'paid'" class="action-buttons">
        <el-button type="primary" size="large" @click="handleViewTickets">查看票券</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { orderApi } from '@/api'

const route = useRoute()
const router = useRouter()

const order = ref({
  orderNo: '',
  status: '',
  username: '',
  phone: '',
  movieTitle: '',
  cinemaName: '',
  hallName: '',
  showDate: '',
  showTime: '',
  seatInfo: '',
  price: 0,
  quantity: 0,
  couponId: null,
  discountAmount: 0,
  totalAmount: 0,
  createTime: '',
  payTime: ''
})

const loadOrderDetail = async () => {
  try {
    const res = await orderApi.getOrderDetail(route.params.id)
    order.value = res.data
  } catch (error) {
    ElMessage.error('获取订单详情失败')
  }
}

const goBack = () => {
  router.back()
}

const handlePay = () => {
  ElMessageBox.confirm('确定要支付该订单吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await orderApi.payOrder(route.params.id)
      ElMessage.success('支付成功')
      loadOrderDetail()
    } catch (error) {
      ElMessage.error('支付失败')
    }
  })
}

const handleCancel = () => {
  ElMessageBox.confirm('确定要取消该订单吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await orderApi.cancelOrder(route.params.id)
      ElMessage.success('取消成功')
      router.back()
    } catch (error) {
      ElMessage.error('取消失败')
    }
  })
}

const handleViewTickets = () => {
  router.push('/ticket/my-tickets')
}

const getStatusType = (status) => {
  const types = {
    unpaid: 'warning',
    paid: 'success',
    completed: 'info',
    canceled: 'danger'
  }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = {
    unpaid: '未支付',
    paid: '已支付',
    completed: '已完成',
    canceled: '已取消'
  }
  return texts[status] || status
}

onMounted(() => {
  loadOrderDetail()
})
</script>

<style scoped>
.order-detail-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.action-buttons {
  margin-top: 30px;
  text-align: center;
}

.action-buttons .el-button {
  margin: 0 10px;
}
</style>
