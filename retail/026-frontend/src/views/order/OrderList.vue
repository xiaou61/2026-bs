<template>
  <div>
    <el-table :data="orders" style="width: 100%">
      <el-table-column prop="orderNo" label="订单号" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="totalPrice" label="总价" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag>{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="viewDetail(row.id)">查看</el-button>
          <el-button size="small" type="primary" v-if="row.status === 'PENDING_DEPOSIT'" @click="payDeposit(row.id)">支付定金</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../utils/request'
import { ElMessage } from 'element-plus'

const router = useRouter()
const user = JSON.parse(localStorage.getItem('user') || '{}')
const orders = ref([])

const getStatusText = (status) => {
  const map = {
    'PENDING_DEPOSIT': '待支付定金',
    'IN_PRODUCTION': '制作中',
    'DRAFT_SUBMITTED': '草图已提交',
    'REVISING': '修改中',
    'PENDING_FINAL_PAYMENT': '待支付尾款',
    'COMPLETED': '已完成'
  }
  return map[status] || status
}

const loadOrders = async () => {
  try {
    if (user.role === 'ARTIST') {
      const artist = await request.get(`/artist/user/${user.id}`)
      orders.value = await request.get(`/order/artist/${artist.id}`)
    } else {
      orders.value = await request.get(`/order/user/${user.id}`)
    }
  } catch (error) {
    console.error(error)
  }
}

const viewDetail = (id) => {
  router.push(`/order/${id}`)
}

const payDeposit = async (id) => {
  try {
    await request.post(`/order/pay-deposit/${id}`)
    ElMessage.success('定金支付成功')
    loadOrders()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadOrders()
})
</script>
