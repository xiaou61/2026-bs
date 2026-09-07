<template>
  <div class="order-detail">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>订单详情</span>
          <el-tag :type="getStatusType(order?.orderStatus)">{{ getStatusText(order?.orderStatus) }}</el-tag>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单编号" :span="2">{{ order?.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="订单类型">{{ getOrderTypeText(order?.orderType) }}</el-descriptions-item>
        <el-descriptions-item label="物品/服务">{{ order?.itemTitle }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ order?.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ order?.endTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="计划时长">{{ order?.plannedDuration }}分钟</el-descriptions-item>
        <el-descriptions-item label="实际时长">{{ order?.actualDuration || '-' }}分钟</el-descriptions-item>
      </el-descriptions>

      <el-divider />

      <h3>费用明细</h3>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="租金/服务费">¥{{ order?.rentalFee }}</el-descriptions-item>
        <el-descriptions-item label="押金">¥{{ order?.depositAmount }}</el-descriptions-item>
        <el-descriptions-item label="平台服务费">¥{{ order?.platformFee }}</el-descriptions-item>
        <el-descriptions-item label="超时费用">¥{{ order?.overtimeFee || 0 }}</el-descriptions-item>
        <el-descriptions-item label="总金额" :span="2">
          <span style="color: #f56c6c; font-size: 20px; font-weight: bold;">¥{{ order?.totalAmount }}</span>
        </el-descriptions-item>
      </el-descriptions>

      <el-divider />

      <div class="order-actions">
        <el-button v-if="order?.orderStatus === 0" type="danger" @click="handleCancel">取消订单</el-button>
        <el-button v-if="order?.orderStatus === 1 && order?.orderType === 'SHARED'" type="primary" @click="handleReturn">归还物品</el-button>
        <el-button v-if="order?.orderStatus === 3" type="primary" @click="reviewDialogVisible = true">评价</el-button>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </el-card>

    <el-dialog v-model="reviewDialogVisible" title="评价订单" width="500px">
      <el-form :model="reviewForm" label-width="80px">
        <el-form-item label="评分">
          <el-rate v-model="reviewForm.rating" show-text />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input type="textarea" v-model="reviewForm.content" :rows="4" placeholder="请输入评价内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitReview">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getOrderDetail, cancelOrder } from '@/api/order'
import { returnItem } from '@/api/shared'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()

const order = ref(null)
const reviewDialogVisible = ref(false)

const reviewForm = reactive({
  rating: 5,
  content: ''
})

const getOrderTypeText = (type) => {
  const map = {
    'SHARED': '共享物品',
    'IDLE': '闲置租赁',
    'SKILL': '技能服务'
  }
  return map[type] || type
}

const getStatusText = (status) => {
  const map = {
    0: '待支付',
    1: '进行中',
    2: '待归还',
    3: '待评价',
    4: '已完成',
    5: '已取消',
    6: '异常'
  }
  return map[status] || '未知'
}

const getStatusType = (status) => {
  const map = {
    0: 'warning',
    1: 'primary',
    2: 'warning',
    3: 'success',
    4: 'info',
    5: 'info',
    6: 'danger'
  }
  return map[status] || ''
}

const loadDetail = async () => {
  try {
    const res = await getOrderDetail(route.params.id)
    order.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleCancel = async () => {
  try {
    await ElMessageBox.confirm('确定要取消该订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await cancelOrder(order.value.id)
    ElMessage.success('取消成功')
    loadDetail()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleReturn = async () => {
  try {
    await ElMessageBox.confirm('确认已归还物品？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await returnItem({ orderId: order.value.id })
    ElMessage.success('归还成功')
    loadDetail()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleSubmitReview = () => {
  ElMessage.success('评价成功')
  reviewDialogVisible.value = false
  loadDetail()
}

onMounted(() => {
  loadDetail()
})
</script>

<style scoped>
.order-item {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: all 0.3s;
}

.order-item:hover {
  border-color: #409eff;
  box-shadow: 0 2px 12px rgba(64, 158, 255, 0.2);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background: #f5f7fa;
  border-bottom: 1px solid #ebeef5;
}

.order-body {
  display: flex;
  align-items: center;
  padding: 20px;
  gap: 20px;
}

.order-item-info {
  flex: 1;
  display: flex;
  gap: 15px;
}

.item-icon {
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  color: #909399;
}

.item-detail h4 {
  margin-bottom: 5px;
  color: #303133;
}

.item-detail p {
  margin: 3px 0;
  color: #909399;
  font-size: 14px;
}

.order-price {
  width: 120px;
  text-align: center;
}

.price {
  color: #f56c6c;
  font-size: 20px;
  font-weight: bold;
}

.order-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>

