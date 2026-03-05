<template>
  <view class="container">
    <view class="card">
      <view class="form-item">
        <text class="label">联系人</text>
        <input v-model="form.contactName" placeholder="请输入联系人" />
      </view>
      <view class="form-item">
        <text class="label">联系电话</text>
        <input v-model="form.contactPhone" placeholder="请输入联系电话" />
      </view>
      <view class="form-item">
        <text class="label">电器分类</text>
        <picker mode="selector" :range="categoryNames" @change="onCategoryChange">
          <view class="picker">{{ selectedCategoryName || '请选择分类' }}</view>
        </picker>
      </view>
      <view class="form-item">
        <text class="label">品牌</text>
        <input v-model="form.brand" placeholder="如 格力/海尔" />
      </view>
      <view class="form-item">
        <text class="label">型号</text>
        <input v-model="form.model" placeholder="如 KFR-35GW" />
      </view>
      <view class="form-item">
        <text class="label">地址</text>
        <input v-model="form.address" placeholder="请输入详细地址" />
      </view>
      <view class="form-item">
        <text class="label">故障描述</text>
        <textarea v-model="form.faultDesc" placeholder="请描述故障现象" />
      </view>
      <view class="form-item">
        <text class="label">故障图片</text>
        <view class="img-grid">
          <image v-for="img in imageList" :key="img" :src="fullImage(img)" mode="aspectFill" class="img-item" />
          <view class="img-upload" @click="chooseImages">+</view>
        </view>
      </view>
      <view class="form-item">
        <text class="label">紧急程度</text>
        <radio-group @change="onUrgencyChange">
          <label class="radio"><radio value="0" :checked="form.urgency === 0" />普通</label>
          <label class="radio"><radio value="1" :checked="form.urgency === 1" />紧急</label>
        </radio-group>
      </view>
      <button class="submit-btn" @click="handleSubmit">提交报修</button>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { getCategoryList, createOrder, uploadImage } from '../../api'

const categories = ref([])
const selectedCategoryIndex = ref(-1)
const imageList = ref([])

const form = reactive({
  contactName: '',
  contactPhone: '',
  categoryId: null,
  brand: '',
  model: '',
  address: '',
  province: '四川省',
  city: '成都市',
  district: '高新区',
  faultDesc: '',
  images: '',
  urgency: 0
})

const categoryNames = computed(() => categories.value.map(item => item.name))
const selectedCategoryName = computed(() => {
  if (selectedCategoryIndex.value < 0) return ''
  return categoryNames.value[selectedCategoryIndex.value]
})

const loadCategories = async () => {
  try {
    const res = await getCategoryList()
    categories.value = res.data || []
  } catch (e) {}
}

const onCategoryChange = (e) => {
  selectedCategoryIndex.value = Number(e.detail.value)
  form.categoryId = categories.value[selectedCategoryIndex.value]?.id || null
}

const onUrgencyChange = (e) => {
  form.urgency = Number(e.detail.value)
}

const fullImage = (url) => {
  if (!url) return ''
  if (url.startsWith('http://') || url.startsWith('https://')) return url
  return `http://localhost:8080${url}`
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

const handleSubmit = async () => {
  if (!form.contactName || !form.contactPhone || !form.address || !form.faultDesc) {
    return uni.showToast({ title: '请填写完整信息', icon: 'none' })
  }
  try {
    await createOrder(form)
    uni.showToast({ title: '提交成功', icon: 'success' })
    setTimeout(() => {
      uni.redirectTo({ url: '/pages/order/list' })
    }, 800)
  } catch (e) {}
}

onMounted(loadCategories)
</script>

<style scoped>
.form-item { margin-bottom: 24rpx; }
.label { display: block; font-size: 26rpx; color: #666; margin-bottom: 10rpx; }
input, textarea, .picker { background: #f5f6fa; border-radius: 10rpx; padding: 18rpx 20rpx; font-size: 28rpx; }
textarea { height: 160rpx; width: 100%; }
.radio { margin-right: 24rpx; font-size: 28rpx; }
.submit-btn { margin-top: 20rpx; background: #2d5baf; color: #fff; border-radius: 12rpx; }
.img-grid { display: flex; gap: 12rpx; flex-wrap: wrap; }
.img-item { width: 140rpx; height: 140rpx; border-radius: 10rpx; }
.img-upload { width: 140rpx; height: 140rpx; border-radius: 10rpx; background: #eef2ff; color: #2d5baf; display: flex; justify-content: center; align-items: center; font-size: 56rpx; }
</style>
