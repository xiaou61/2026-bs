<template>
  <view class="container">
    <view class="card" v-if="order.id">
      <view class="title">{{ order.orderNo }}</view>
      <view class="line">状态：{{ statusText(order.status) }}</view>
      <view class="line">联系人：{{ order.contactName }} {{ order.contactPhone }}</view>
      <view class="line">地址：{{ order.address }}</view>
      <view class="line">故障：{{ order.faultDesc }}</view>
      <view class="line">技师ID：{{ order.technicianId || '-' }}</view>
      <view class="line">支付状态：{{ order.payStatus === 1 ? '已支付' : '未支付' }}</view>
      <view class="line">费用：{{ order.totalFee || 0 }}</view>
      <view class="img-grid" v-if="splitImages(order.images).length">
        <image v-for="img in splitImages(order.images)" :key="img" :src="fullImage(img)" mode="aspectFill" class="img-item" />
      </view>
      <button v-if="order.status === 3 && order.payStatus !== 1" class="submit-btn" @click="handlePay">立即支付</button>
    </view>

    <view class="card">
      <view class="section-title">处理进度</view>
      <view v-if="!processList.length" class="empty">暂无进度</view>
      <view v-for="item in processList" :key="item.id" class="process-item">
        <view class="process-top">{{ item.nodeType }} · {{ item.createTime }}</view>
        <view class="process-content">{{ item.content }}</view>
        <view class="img-grid" v-if="splitImages(item.images).length">
          <image v-for="img in splitImages(item.images)" :key="img" :src="fullImage(img)" mode="aspectFill" class="img-item" />
        </view>
      </view>
    </view>

    <view class="card" v-if="order.status === 4 && !evaluated">
      <view class="section-title">服务评价</view>
      <view class="form-item">
        <text class="label">综合评分</text>
        <slider :value="evalForm.score" min="1" max="5" step="1" show-value @change="onScoreChange" />
      </view>
      <view class="form-item">
        <text class="label">服务态度</text>
        <slider :value="evalForm.attitudeScore" min="1" max="5" step="1" show-value @change="onAttitudeChange" />
      </view>
      <view class="form-item">
        <text class="label">维修质量</text>
        <slider :value="evalForm.qualityScore" min="1" max="5" step="1" show-value @change="onQualityChange" />
      </view>
      <view class="form-item">
        <text class="label">时效满意度</text>
        <slider :value="evalForm.speedScore" min="1" max="5" step="1" show-value @change="onSpeedChange" />
      </view>
      <view class="form-item">
        <text class="label">评价内容</text>
        <textarea v-model="evalForm.content" placeholder="请输入评价内容" />
      </view>
      <view class="form-item">
        <text class="label">评价图片</text>
        <view class="img-grid">
          <image v-for="img in evaluateImages" :key="img" :src="fullImage(img)" mode="aspectFill" class="img-item" />
          <view class="img-upload" @click="chooseEvaluateImages">+</view>
        </view>
      </view>
      <button class="submit-btn" @click="submitEval">提交评价</button>
    </view>

    <view class="card" v-if="evaluated">
      <view class="section-title">已评价</view>
      <view class="line">该工单已提交评价，感谢反馈。</view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getOrderDetail, getOrderProcess, submitEvaluate, getMyEvaluate, payOrder, uploadImage } from '../../api'

const order = ref({})
const processList = ref([])
const evaluated = ref(false)
const evaluateImages = ref([])
const evalForm = reactive({
  orderId: null,
  technicianId: null,
  score: 5,
  attitudeScore: 5,
  qualityScore: 5,
  speedScore: 5,
  content: '',
  images: ''
})

const statusText = (status) => ({ 0: '待受理', 1: '已派单', 2: '上门中', 3: '待支付', 4: '已完成', 5: '已取消' }[status] || '未知')

const onScoreChange = (e) => {
  evalForm.score = Number(e.detail.value)
}

const onAttitudeChange = (e) => {
  evalForm.attitudeScore = Number(e.detail.value)
}

const onQualityChange = (e) => {
  evalForm.qualityScore = Number(e.detail.value)
}

const onSpeedChange = (e) => {
  evalForm.speedScore = Number(e.detail.value)
}

const splitImages = (images) => {
  if (!images) return []
  return images.split(',').filter(Boolean)
}

const fullImage = (url) => {
  if (!url) return ''
  if (url.startsWith('http://') || url.startsWith('https://')) return url
  return `http://localhost:8080${url}`
}

const chooseEvaluateImages = () => {
  uni.chooseImage({
    count: 3,
    sizeType: ['compressed'],
    success: async (res) => {
      try {
        uni.showLoading({ title: '上传中' })
        const uploadTasks = res.tempFilePaths.map(path => uploadImage(path))
        const urls = await Promise.all(uploadTasks)
        evaluateImages.value = [...evaluateImages.value, ...urls]
        evalForm.images = evaluateImages.value.join(',')
      } catch (e) {
      } finally {
        uni.hideLoading()
      }
    }
  })
}

const loadDetail = async (id) => {
  try {
    const [detailRes, processRes, evalRes] = await Promise.all([
      getOrderDetail(id),
      getOrderProcess(id),
      getMyEvaluate({ pageNum: 1, pageSize: 100 })
    ])
    order.value = detailRes.data || {}
    processList.value = processRes.data || []
    const myEvalList = evalRes.data.records || []
    evaluated.value = myEvalList.some(item => item.orderId === Number(id))
    evalForm.orderId = Number(id)
    evalForm.technicianId = order.value.technicianId
  } catch (e) {}
}

const submitEval = async () => {
  if (!evalForm.content) {
    return uni.showToast({ title: '请输入评价内容', icon: 'none' })
  }
  try {
    await submitEvaluate(evalForm)
    uni.showToast({ title: '评价成功', icon: 'success' })
    evaluated.value = true
  } catch (e) {}
}

const handlePay = async () => {
  try {
    await payOrder(order.value.id, { content: '小程序用户支付完成' })
    uni.showToast({ title: '支付成功', icon: 'success' })
    loadDetail(order.value.id)
  } catch (e) {}
}

onLoad((options) => {
  const id = options.id
  if (!id) return
  loadDetail(id)
})
</script>

<style scoped>
.title { font-size: 30rpx; font-weight: bold; color: #2d5baf; margin-bottom: 12rpx; }
.line { font-size: 26rpx; color: #666; margin-top: 8rpx; }
.section-title { font-size: 30rpx; font-weight: 600; margin-bottom: 14rpx; }
.process-item { border-left: 4rpx solid #2d5baf; padding-left: 16rpx; margin-bottom: 16rpx; }
.process-top { font-size: 24rpx; color: #909399; }
.process-content { font-size: 26rpx; color: #333; margin-top: 6rpx; }
.form-item { margin-bottom: 18rpx; }
.label { display: block; font-size: 26rpx; color: #666; margin-bottom: 6rpx; }
textarea { background: #f5f6fa; border-radius: 10rpx; padding: 18rpx 20rpx; height: 140rpx; width: 100%; }
.submit-btn { margin-top: 12rpx; background: #2d5baf; color: #fff; border-radius: 12rpx; }
.empty { color: #999; font-size: 26rpx; }
.img-grid { display: flex; gap: 12rpx; flex-wrap: wrap; margin-top: 12rpx; }
.img-item { width: 140rpx; height: 140rpx; border-radius: 10rpx; }
.img-upload { width: 140rpx; height: 140rpx; border-radius: 10rpx; background: #eef2ff; color: #2d5baf; display: flex; justify-content: center; align-items: center; font-size: 56rpx; }
</style>
