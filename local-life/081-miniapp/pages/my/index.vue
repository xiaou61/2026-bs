<template>
  <view class="container">
    <view class="card user-card">
      <view class="name">{{ userStore.userInfo?.realName || userStore.userInfo?.username || '未登录' }}</view>
      <view class="meta">账号：{{ userStore.userInfo?.username || '-' }}</view>
      <view class="meta">手机号：{{ userStore.userInfo?.phone || '-' }}</view>
      <view class="meta">角色：{{ userStore.userInfo?.role || '-' }}</view>
    </view>

    <view class="card menu-card">
      <view class="menu-item" @click="go('/pages/order/list')">我的工单</view>
      <view class="menu-item" @click="go('/pages/order/create')">提交报修</view>
      <view v-if="userStore.userInfo?.role === 'technician'" class="menu-item" @click="go('/pages/technician/index')">技师工作台</view>
      <view class="menu-item" @click="go('/pages/notice/list')">公告通知</view>
      <view class="menu-item danger" @click="handleLogout">退出登录</view>
    </view>
  </view>
</template>

<script setup>
import { useUserStore } from '../../store/user'

const userStore = useUserStore()

const go = (url) => uni.navigateTo({ url })

const handleLogout = () => {
  userStore.logout()
  uni.redirectTo({ url: '/pages/login/login' })
}
</script>

<style scoped>
.user-card { background: linear-gradient(135deg, #2d5baf 0%, #1d2a3f 100%); color: #fff; }
.name { font-size: 36rpx; font-weight: bold; margin-bottom: 20rpx; }
.meta { font-size: 26rpx; margin-top: 6rpx; }
.menu-card { margin-top: 20rpx; padding: 0; overflow: hidden; }
.menu-item { padding: 28rpx 24rpx; border-bottom: 1rpx solid #eee; font-size: 28rpx; }
.menu-item:last-child { border-bottom: none; }
.danger { color: #f56c6c; }
</style>
