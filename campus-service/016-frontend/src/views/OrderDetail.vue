<template>
  <div class="order-detail-container">
    <el-card v-if="order">
      <template #header>
        <div class="order-header">
          <span>订单详情</span>
          <el-tag :type="getStatusType(order.status)" size="large">
            {{ getStatusText(order.status) }}
          </el-tag>
        </div>
      </template>

      <el-steps :active="getStepActive(order.status)" finish-status="success" class="order-steps">
        <el-step title="待接单" />
        <el-step title="待取件" />
        <el-step title="配送中" />
        <el-step title="待确认" />
        <el-step title="已完成" />
      </el-steps>

      <el-descriptions :column="2" border class="order-info">
        <el-descriptions-item label="订单编号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="跑腿费">
          <span class="fee">¥{{ order.fee }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="快递公司">{{ order.expressCompany }}</el-descriptions-item>
        <el-descriptions-item label="取件码">{{ order.pickupCode }}</el-descriptions-item>
        <el-descriptions-item label="物品类型">{{ order.itemType }}</el-descriptions-item>
        <el-descriptions-item label="物品重量">{{ getWeightText(order.itemWeight) }}</el-descriptions-item>
        <el-descriptions-item label="取件地址" :span="2">{{ order.pickupAddress }}</el-descriptions-item>
        <el-descriptions-item label="送达地址" :span="2">{{ order.deliveryAddress }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ order.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="期望送达">{{ formatTime(order.expectTime) }}</el-descriptions-item>
        <el-descriptions-item label="备注说明" :span="2">{{ order.remark || '无' }}</el-descriptions-item>
        <el-descriptions-item label="发单人">{{ order.publisherName }}</el-descriptions-item>
        <el-descriptions-item label="接单人">{{ order.takerName || '待接单' }}</el-descriptions-item>
      </el-descriptions>

      <div v-if="order.pickupImage || order.deliveryImage" class="image-section">
        <el-divider>凭证照片</el-divider>
        <div class="images">
          <div v-if="order.pickupImage" class="image-item">
            <div class="image-label">取件凭证</div>
            <el-image :src="order.pickupImage" fit="cover" />
          </div>
          <div v-if="order.deliveryImage" class="image-item">
            <div class="image-label">送达凭证</div>
            <el-image :src="order.deliveryImage" fit="cover" />
          </div>
        </div>
      </div>

      <div class="action-buttons">
        <el-button
          v-if="isPublisher && order.status === 0"
          type="danger"
          @click="handleCancel"
        >
          取消订单
        </el-button>
        <el-button
          v-if="!isPublisher && order.status === 0"
          type="primary"
          @click="handleAccept"
        >
          接单
        </el-button>
        <el-button
          v-if="isTaker && order.status === 1"
          type="primary"
          @click="showUploadPickup = true"
        >
          上传取件凭证
        </el-button>
        <el-button
          v-if="isTaker && order.status === 2"
          type="primary"
          @click="showUploadDelivery = true"
        >
          上传送达凭证
        </el-button>
        <el-button
          v-if="isPublisher && order.status === 3"
          type="success"
          @click="handleConfirm"
        >
          确认收货
        </el-button>
        <el-button
          v-if="isPublisher && order.status === 4"
          type="primary"
          @click="showReview = true"
        >
          评价
        </el-button>
        <el-button @click="$router.back()">返回</el-button>
      </div>
    </el-card>

    <el-dialog v-model="showUploadPickup" title="上传取件凭证" width="400px">
      <el-input v-model="pickupImage" type="textarea" :rows="3" placeholder="请输入图片URL" />
      <template #footer>
        <el-button @click="showUploadPickup = false">取消</el-button>
        <el-button type="primary" @click="uploadPickup">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showUploadDelivery" title="上传送达凭证" width="400px">
      <el-input v-model="deliveryImage" type="textarea" :rows="3" placeholder="请输入图片URL" />
      <template #footer>
        <el-button @click="showUploadDelivery = false">取消</el-button>
        <el-button type="primary" @click="uploadDelivery">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showReview" title="评价订单" width="500px">
      <el-form :model="reviewForm" label-width="80px">
        <el-form-item label="评分">
          <el-rate v-model="reviewForm.rating" />
        </el-form-item>
        <el-form-item label="评价标签">
          <el-checkbox-group v-model="reviewForm.tags">
            <el-checkbox label="速度快" />
            <el-checkbox label="态度好" />
            <el-checkbox label="包装好" />
            <el-checkbox label="准时送达" />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input v-model="reviewForm.content" type="textarea" :rows="4" placeholder="请输入评价内容" />
        </el-form-item>
        <el-form-item label="匿名评价">
          <el-switch v-model="reviewForm.isAnonymous" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showReview = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { getOrderDetail, acceptOrder, uploadPickupImage, uploadDeliveryImage, confirmOrder, cancelOrder } from '../api/order'
import { createReview } from '../api/review'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const order = ref(null)
const showUploadPickup = ref(false)
const showUploadDelivery = ref(false)
const showReview = ref(false)
const pickupImage = ref('')
const deliveryImage = ref('')

const reviewForm = reactive({
  rating: 5,
  tags: [],
  content: '',
  isAnonymous: false
})

const isPublisher = computed(() => order.value && order.value.publisherId === userStore.userInfo?.id)
const isTaker = computed(() => order.value && order.value.takerId === userStore.userInfo?.id)

const loadOrder = async () => {
  try {
    order.value = await getOrderDetail(route.params.id)
  } catch (error) {
    console.error('加载订单失败', error)
  }
}

const handleAccept = async () => {
  try {
    await ElMessageBox.confirm('确认接单吗？', '提示')
    await acceptOrder(order.value.id)
    ElMessage.success('接单成功')
    loadOrder()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('接单失败', error)
    }
  }
}

const uploadPickup = async () => {
  try {
    await uploadPickupImage(order.value.id, pickupImage.value)
    ElMessage.success('上传成功')
    showUploadPickup.value = false
    loadOrder()
  } catch (error) {
    console.error('上传失败', error)
  }
}

const uploadDelivery = async () => {
  try {
    await uploadDeliveryImage(order.value.id, deliveryImage.value)
    ElMessage.success('上传成功')
    showUploadDelivery.value = false
    loadOrder()
  } catch (error) {
    console.error('上传失败', error)
  }
}

const handleConfirm = async () => {
  try {
    await ElMessageBox.confirm('确认收到快递了吗？', '确认收货')
    await confirmOrder(order.value.id)
    ElMessage.success('确认收货成功')
    loadOrder()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('确认收货失败', error)
    }
  }
}

const handleCancel = async () => {
  try {
    const { value } = await ElMessageBox.prompt('请输入取消原因', '取消订单', {
      confirmButtonText: '确认',
      cancelButtonText: '取消'
    })
    await cancelOrder(order.value.id, value)
    ElMessage.success('取消成功')
    loadOrder()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消失败', error)
    }
  }
}

const submitReview = async () => {
  try {
    await createReview({
      orderId: order.value.id,
      orderNo: order.value.orderNo,
      revieweeId: order.value.takerId,
      rating: reviewForm.rating,
      tags: reviewForm.tags.join(','),
      content: reviewForm.content,
      isAnonymous: reviewForm.isAnonymous ? 1 : 0
    })
    ElMessage.success('评价成功')
    showReview.value = false
    loadOrder()
  } catch (error) {
    console.error('评价失败', error)
  }
}

const getStepActive = (status) => {
  const steps = { 0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 0, 6: 0 }
  return steps[status] || 0
}

const getStatusType = (status) => {
  const types = { 0: '', 1: 'warning', 2: 'primary', 3: 'success', 4: 'success', 5: 'info', 6: 'info' }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = { 0: '待接单', 1: '待取件', 2: '配送中', 3: '待确认', 4: '已完成', 5: '已取消', 6: '已退款' }
  return texts[status] || '未知'
}

const getWeightText = (weight) => {
  const texts = { SMALL: '小件', MEDIUM: '中件', LARGE: '大件' }
  return texts[weight] || weight
}

const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

onMounted(() => {
  loadOrder()
})
</script>

<style scoped>
.order-detail-container {
  max-width: 1000px;
  margin: 0 auto;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: 500;
}

.order-steps {
  margin: 30px 0;
}

.order-info {
  margin: 20px 0;
}

.fee {
  font-size: 20px;
  font-weight: bold;
  color: #f56c6c;
}

.image-section {
  margin: 20px 0;
}

.images {
  display: flex;
  gap: 20px;
}

.image-item {
  flex: 1;
}

.image-label {
  margin-bottom: 10px;
  font-size: 14px;
  color: #606266;
}

.el-image {
  width: 100%;
  height: 300px;
  border-radius: 4px;
}

.action-buttons {
  margin-top: 30px;
  display: flex;
  gap: 10px;
  justify-content: center;
}
</style>

