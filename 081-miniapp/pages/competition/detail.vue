<template>
  <view class="container" v-if="competition">
    <image class="cover" :src="competition.cover || '/static/default-cover.png'" mode="aspectFill" />
    <view class="content">
      <text class="title">{{ competition.title }}</text>
      <view class="tags">
        <text class="tag">{{ competition.categoryName }}</text>
        <text class="tag">{{ statusText[competition.status] }}</text>
      </view>
      <view class="info-section">
        <view class="info-item"><text class="label">开始时间</text><text class="value">{{ competition.startTime || '未设置' }}</text></view>
        <view class="info-item"><text class="label">结束时间</text><text class="value">{{ competition.endTime || '未设置' }}</text></view>
        <view class="info-item"><text class="label">提交截止</text><text class="value">{{ competition.submitDeadline || '未设置' }}</text></view>
        <view class="info-item"><text class="label">字数要求</text><text class="value">{{ competition.minWords }}-{{ competition.maxWords }}字</text></view>
      </view>
      <view class="section">
        <text class="section-title">竞赛描述</text>
        <text class="section-content">{{ competition.description }}</text>
      </view>
      <view class="section">
        <text class="section-title">参赛要求</text>
        <text class="section-content">{{ competition.requirement }}</text>
      </view>
    </view>
    <view class="bottom-bar" v-if="competition.status === 1">
      <button class="btn-submit" @click="goSubmit">提交作品</button>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCompetition } from '../../api'

const competition = ref(null)
const statusText = { 0: '草稿', 1: '进行中', 2: '已结束', 3: '已下架' }

const goSubmit = () => {
  const token = uni.getStorageSync('token')
  if (!token) {
    uni.showToast({ title: '请先登录', icon: 'none' })
    return uni.navigateTo({ url: '/pages/login/login' })
  }
  uni.navigateTo({ url: `/pages/work/submit?competitionId=${competition.value.id}` })
}

onMounted(async () => {
  const pages = getCurrentPages()
  const page = pages[pages.length - 1]
  const id = page.options.id
  const res = await getCompetition(id)
  competition.value = res.data
})
</script>

<style scoped>
.cover { width: 100%; height: 400rpx; }
.content { padding: 30rpx; padding-bottom: 150rpx; }
.title { font-size: 36rpx; font-weight: bold; display: block; margin-bottom: 20rpx; }
.tags { display: flex; gap: 16rpx; margin-bottom: 30rpx; }
.tag { font-size: 24rpx; color: #409EFF; background: rgba(64,158,255,0.1); padding: 8rpx 20rpx; border-radius: 6rpx; }
.info-section { background: #f5f7fa; border-radius: 12rpx; padding: 24rpx; margin-bottom: 30rpx; }
.info-item { display: flex; justify-content: space-between; padding: 12rpx 0; border-bottom: 1rpx solid #eee; }
.info-item:last-child { border-bottom: none; }
.info-item .label { color: #666; font-size: 26rpx; }
.info-item .value { color: #333; font-size: 26rpx; }
.section { margin-bottom: 30rpx; }
.section-title { font-size: 30rpx; font-weight: bold; margin-bottom: 16rpx; display: block; }
.section-content { font-size: 28rpx; color: #666; line-height: 1.8; white-space: pre-wrap; }
.bottom-bar { position: fixed; bottom: 0; left: 0; right: 0; padding: 20rpx 30rpx; background: #fff; box-shadow: 0 -4rpx 12rpx rgba(0,0,0,0.05); }
.btn-submit { background: #409EFF; color: #fff; border: none; height: 90rpx; line-height: 90rpx; border-radius: 12rpx; font-size: 32rpx; }
</style>
