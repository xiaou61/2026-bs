<template>
  <view class="container">
    <view class="card">
      <view class="form-item">
        <text class="label">工单ID</text>
        <input :value="orderId" disabled />
      </view>
      <view class="form-item">
        <text class="label">进度内容</text>
        <textarea v-model="form.content" placeholder="请输入处理进度" />
      </view>
      <view class="form-item">
        <text class="label">进度图片</text>
        <view class="img-grid">
          <image v-for="img in imageList" :key="img" :src="fullImage(img)" mode="aspectFill" class="img-item" />
          <view class="img-upload" @click="chooseImages">+</view>
        </view>
      </view>
      <button class="submit-btn" @click="submitProcess">提交进度</button>
    </view>

    <view class="card">
      <view class="section-title">历史进度</view>
      <view v-if="!processList.length" class="empty">暂无进度</view>
      <view v-for="item in processList" :key="item.id" class="process-item">
        <view class="process-top">{{ item.nodeType }} · {{ item.createTime }}</view>
        <view class="process-content">{{ item.content }}</view>
        <view class="img-grid" v-if="splitImages(item.images).length">
          <image v-for="img in splitImages(item.images)" :key="img" :src="fullImage(img)" mode="aspectFill" class="img-item" />
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getOrderProcess, technicianAddProcess, uploadImage } from '../../api'

const orderId = ref('')
const processList = ref([])
const imageList = ref([])
const form = reactive({
  content: '',
  images: ''
})

const splitImages = (images) => {
  if (!images) return []
  return images.split(',').filter(Boolean)
}

const fullImage = (url) => {
  if (!url) return ''
  if (url.startsWith('http://') || url.startsWith('https://')) return url
  return `http://localhost:8080${url}`
}

const loadProcess = async () => {
  if (!orderId.value) return
  try {
    const res = await getOrderProcess(orderId.value)
    processList.value = res.data || []
  } catch (e) {}
}

const chooseImages = () => {
  uni.chooseImage({
    count: 3,
    sizeType: ['compressed'],
    success: async (res) => {
      try {
        uni.showLoading({ title: '上传中' })
        const uploadTasks = res.tempFilePaths.map(path => uploadImage(path))
        const urls = await Promise.all(uploadTasks)
        imageList.value = [...imageList.value, ...urls]
        form.images = imageList.value.join(',')
      } catch (e) {
      } finally {
        uni.hideLoading()
      }
    }
  })
}

const submitProcess = async () => {
  if (!form.content) {
    return uni.showToast({ title: '请输入进度内容', icon: 'none' })
  }
  try {
    await technicianAddProcess({
      orderId: Number(orderId.value),
      content: form.content,
      images: form.images
    })
    uni.showToast({ title: '提交成功', icon: 'success' })
    form.content = ''
    form.images = ''
    imageList.value = []
    loadProcess()
  } catch (e) {}
}

onLoad((options) => {
  if (!options.id) return
  orderId.value = options.id
  loadProcess()
})
</script>

<style scoped>
.form-item { margin-bottom: 24rpx; }
.label { display: block; font-size: 26rpx; color: #666; margin-bottom: 10rpx; }
input, textarea { background: #f5f6fa; border-radius: 10rpx; padding: 18rpx 20rpx; font-size: 28rpx; }
textarea { height: 160rpx; width: 100%; }
.submit-btn { margin-top: 20rpx; background: #2d5baf; color: #fff; border-radius: 12rpx; }
.section-title { font-size: 30rpx; font-weight: 600; margin-bottom: 14rpx; }
.process-item { border-left: 4rpx solid #2d5baf; padding-left: 16rpx; margin-bottom: 16rpx; }
.process-top { font-size: 24rpx; color: #909399; }
.process-content { font-size: 26rpx; color: #333; margin-top: 6rpx; }
.img-grid { display: flex; gap: 12rpx; flex-wrap: wrap; margin-top: 12rpx; }
.img-item { width: 140rpx; height: 140rpx; border-radius: 10rpx; }
.img-upload { width: 140rpx; height: 140rpx; border-radius: 10rpx; background: #eef2ff; color: #2d5baf; display: flex; justify-content: center; align-items: center; font-size: 56rpx; }
.empty { color: #999; font-size: 26rpx; }
</style>
