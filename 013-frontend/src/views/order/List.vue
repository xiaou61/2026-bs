<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>我的订单</span>
          <el-radio-group v-model="activeTab" @change="handleTabChange">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button label="1">进行中</el-radio-button>
            <el-radio-button label="3">待评价</el-radio-button>
            <el-radio-button label="4">已完成</el-radio-button>
          </el-radio-group>
        </div>
      </template>

      <div class="order-item" v-for="order in orders" :key="order.id" @click="$router.push(`/order/${order.id}`)">
        <div class="order-header">
          <span class="order-no">订单号：{{ order.orderNo }}</span>
          <el-tag :type="getStatusType(order.orderStatus)">{{ getStatusText(order.orderStatus) }}</el-tag>
        </div>

        <div class="order-body">
          <div class="order-item-info">
            <div class="item-icon">
              <el-icon :size="40">
                <Camera v-if="order.orderType === 'IDLE'" />
                <User v-else-if="order.orderType === 'SKILL'" />
                <Bicycle v-else />
              </el-icon>
            </div>
            <div class="item-detail">
              <h4>{{ order.itemTitle }}</h4>
              <p>{{ getOrderTypeText(order.orderType) }}</p>
              <p class="time">{{ order.createTime }}</p>
            </div>
          </div>

          <div class="order-price">
            <div class="price">¥{{ order.totalAmount }}</div>
          </div>

          <div class="order-actions">
            <el-button v-if="order.orderStatus === 0" size="small" @click.stop="handleCancel(order.id)">取消订单</el-button>
            <el-button v-if="order.orderStatus === 3" type="primary" size="small" @click.stop="handleReview(order)">评价</el-button>
            <el-button size="small" @click.stop="$router.push(`/order/${order.id}`)">查看详情</el-button>
          </div>
        </div>
      </div>

      <el-empty v-if="orders.length === 0" description="暂无订单" />

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
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getOrderList, cancelOrder } from '@/api/order'
import { ElMessage, ElMessageBox } from 'element-plus'

const activeTab = ref('')
const orders = ref([])
const reviewDialogVisible = ref(false)
const currentOrder = ref(null)

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

const loadOrders = async () => {
  try {
    const params = {}
    if (activeTab.value) {
      params.status = parseInt(activeTab.value)
    }
    const res = await getOrderList(params)
    orders.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleTabChange = () => {
  loadOrders()
}

const handleCancel = async (id) => {
  try {
    await ElMessageBox.confirm('确定要取消该订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await cancelOrder(id)
    ElMessage.success('取消成功')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleReview = (order) => {
  currentOrder.value = order
  reviewDialogVisible.value = true
}

const handleSubmitReview = () => {
  ElMessage.success('评价成功')
  reviewDialogVisible.value = false
  loadOrders()
}

onMounted(() => {
  loadOrders()
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

.order-no {
  font-size: 14px;
  color: #606266;
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

.time {
  font-size: 12px;
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

