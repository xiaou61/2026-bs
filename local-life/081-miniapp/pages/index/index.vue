<template>
  <view class="container">
    <view class="card header-card">
      <view class="title">电器维修服务平台</view>
      <view class="subtitle">快速报修，实时跟踪，专业上门</view>
    </view>

    <view class="stats-grid">
      <view class="card stat-item"><text class="num">{{ dashboard.totalOrders || 0 }}</text><text class="label">工单总数</text></view>
      <view class="card stat-item"><text class="num">{{ dashboard.pendingOrders || 0 }}</text><text class="label">待受理</text></view>
      <view class="card stat-item"><text class="num">{{ dashboard.processingOrders || 0 }}</text><text class="label">处理中</text></view>
      <view class="card stat-item"><text class="num">{{ dashboard.completedOrders || 0 }}</text><text class="label">已完成</text></view>
    </view>

    <view class="card action-card">
      <view class="action-title">快捷入口</view>
      <view class="action-grid">
        <button class="action-btn" @click="go('/pages/order/create')">提交报修</button>
        <button class="action-btn" @click="go('/pages/order/list')">我的工单</button>
        <button class="action-btn" @click="go('/pages/notice/list')">公告通知</button>
        <button class="action-btn" @click="go('/pages/my/index')">个人中心</button>
        <button v-if="userRole === 'technician'" class="action-btn" @click="go('/pages/technician/index')">技师工作台</button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getDashboard } from '../../api'
import { useUserStore } from '../../store/user'

const dashboard = ref({})
const userStore = useUserStore()
const userRole = computed(() => userStore.userInfo?.role || '')

const loadData = async () => {
  try {
    const res = await getDashboard()
    dashboard.value = res.data || {}
  } catch (e) {}
}

const go = (url) => {
  uni.navigateTo({ url })
}

onMounted(loadData)
</script>

<style scoped>
.container { padding: 20rpx; }
.header-card { background: linear-gradient(135deg, #2d5baf 0%, #1d2a3f 100%); color: #fff; }
.title { font-size: 38rpx; font-weight: bold; margin-bottom: 8rpx; }
.subtitle { font-size: 24rpx; opacity: .9; }
.stats-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 16rpx; margin-top: 20rpx; }
.stat-item { text-align: center; }
.num { display: block; font-size: 40rpx; font-weight: 700; color: #2d5baf; }
.label { display: block; color: #666; margin-top: 8rpx; }
.action-card { margin-top: 20rpx; }
.action-title { font-size: 30rpx; font-weight: 600; margin-bottom: 20rpx; }
.action-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 16rpx; }
.action-btn { background: #fff; border: 2rpx solid #2d5baf; color: #2d5baf; border-radius: 12rpx; font-size: 28rpx; }
</style>
