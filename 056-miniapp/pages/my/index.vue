<template>
  <view class="container">
    <view class="user-card" v-if="userStore.userInfo">
      <image class="avatar" :src="userStore.userInfo.avatar || '/static/default-avatar.png'" />
      <view class="info">
        <text class="nickname">{{ userStore.userInfo.nickname }}</text>
        <text class="username">@{{ userStore.userInfo.username }}</text>
      </view>
    </view>
    <view class="user-card login-card" v-else @click="goLogin">
      <text>点击登录</text>
    </view>
    <view class="menu-list">
      <view class="menu-item" @click="goPage('/pages/work/list')">
        <text class="icon">📝</text>
        <text class="text">我的作品</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goPage('/pages/award/list')">
        <text class="icon">🏆</text>
        <text class="text">我的获奖</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goPage('/pages/my/info')" v-if="userStore.userInfo">
        <text class="icon">👤</text>
        <text class="text">个人信息</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goPage('/pages/notice/list')">
        <text class="icon">📢</text>
        <text class="text">公告通知</text>
        <text class="arrow">›</text>
      </view>
    </view>
    <button class="btn-logout" v-if="userStore.userInfo" @click="handleLogout">退出登录</button>
  </view>
</template>

<script setup>
import { useUserStore } from '../../store/user'

const userStore = useUserStore()

const goLogin = () => uni.navigateTo({ url: '/pages/login/login' })
const goPage = (url) => {
  if (url.includes('/work/') || url.includes('/award/') || url.includes('/my/info')) {
    const token = uni.getStorageSync('token')
    if (!token) {
      uni.showToast({ title: '请先登录', icon: 'none' })
      return uni.navigateTo({ url: '/pages/login/login' })
    }
  }
  uni.navigateTo({ url })
}

const handleLogout = () => {
  uni.showModal({
    title: '提示',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        userStore.logout()
        uni.showToast({ title: '已退出', icon: 'success' })
      }
    }
  })
}
</script>

<style scoped>
.user-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 16rpx; padding: 40rpx; margin-bottom: 30rpx; display: flex; align-items: center; }
.user-card .avatar { width: 120rpx; height: 120rpx; border-radius: 60rpx; margin-right: 30rpx; background: #fff; }
.user-card .info { color: #fff; }
.user-card .nickname { font-size: 36rpx; font-weight: bold; display: block; }
.user-card .username { font-size: 26rpx; opacity: 0.8; margin-top: 8rpx; display: block; }
.login-card { justify-content: center; color: #fff; font-size: 32rpx; }
.menu-list { background: #fff; border-radius: 16rpx; overflow: hidden; }
.menu-item { display: flex; align-items: center; padding: 30rpx 24rpx; border-bottom: 1rpx solid #f5f5f5; }
.menu-item:last-child { border-bottom: none; }
.menu-item .icon { font-size: 40rpx; margin-right: 20rpx; }
.menu-item .text { flex: 1; font-size: 28rpx; }
.menu-item .arrow { color: #ccc; font-size: 32rpx; }
.btn-logout { margin-top: 50rpx; background: #fff; color: #F56C6C; border: none; height: 90rpx; line-height: 90rpx; border-radius: 16rpx; font-size: 30rpx; }
</style>
