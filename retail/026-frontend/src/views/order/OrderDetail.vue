<template>
  <el-card v-if="order">
    <h3>订单详情</h3>
    <el-descriptions :column="2">
      <el-descriptions-item label="订单号">{{ order.orderNo }}</el-descriptions-item>
      <el-descriptions-item label="标题">{{ order.title }}</el-descriptions-item>
      <el-descriptions-item label="总价">¥{{ order.totalPrice }}</el-descriptions-item>
      <el-descriptions-item label="定金">¥{{ order.deposit }}</el-descriptions-item>
      <el-descriptions-item label="尾款">¥{{ order.finalPayment }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ order.status }}</el-descriptions-item>
    </el-descriptions>
    
    <div style="margin-top: 20px">
      <el-button v-if="order.status === 'DRAFT_SUBMITTED'" type="primary" @click="confirmDraft">确认草图</el-button>
      <el-button v-if="order.status === 'DRAFT_SUBMITTED'" @click="requestRevise">要求修改</el-button>
      <el-button v-if="order.status === 'PENDING_FINAL_PAYMENT'" type="primary" @click="payFinal">支付尾款</el-button>
    </div>
    
    <div v-if="order.draftUrl" style="margin-top: 20px">
      <h4>草图</h4>
      <img :src="order.draftUrl" style="max-width: 400px">
    </div>
    
    <div v-if="order.finalUrl" style="margin-top: 20px">
      <h4>成品</h4>
      <img :src="order.finalUrl" style="max-width: 400px">
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import request from '../../utils/request'
import { ElMessage } from 'element-plus'

const route = useRoute()
const order = ref(null)

const loadOrder = async () => {
  try {
    order.value = await request.get(`/order/${route.params.id}`)
  } catch (error) {
    console.error(error)
  }
}

const confirmDraft = async () => {
  try {
    await request.post(`/order/confirm-draft/${order.value.id}`)
    ElMessage.success('草图已确认')
    loadOrder()
  } catch (error) {
    console.error(error)
  }
}

const requestRevise = async () => {
  try {
    await request.post(`/order/request-revise/${order.value.id}`)
    ElMessage.success('已提交修改请求')
    loadOrder()
  } catch (error) {
    console.error(error)
  }
}

const payFinal = async () => {
  try {
    await request.post(`/order/pay-final/${order.value.id}`)
    ElMessage.success('尾款支付成功')
    loadOrder()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadOrder()
})
</script>
