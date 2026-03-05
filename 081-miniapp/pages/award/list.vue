<template>
  <view class="container">
    <view class="section" v-if="myAwards.length">
      <text class="section-title">我的获奖</text>
      <view class="award-card" v-for="item in myAwards" :key="item.id">
        <view class="medal">🏆</view>
        <view class="info">
          <text class="work-title">{{ item.workTitle }}</text>
          <text class="level">{{ item.awardLevel }}</text>
          <text class="score">得分: {{ item.score }}</text>
        </view>
      </view>
    </view>
    <view class="section">
      <text class="section-title">获奖名单</text>
      <text class="tip">选择已结束的竞赛查看获奖名单</text>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyAwards } from '../../api'

const myAwards = ref([])

onMounted(async () => {
  const token = uni.getStorageSync('token')
  if (token) {
    try {
      const res = await getMyAwards()
      myAwards.value = res.data
    } catch (e) {}
  }
})
</script>

<style scoped>
.section { margin-bottom: 30rpx; }
.section-title { font-size: 32rpx; font-weight: bold; margin-bottom: 20rpx; display: block; }
.tip { font-size: 26rpx; color: #999; }
.award-card { background: linear-gradient(135deg, #ffd700 0%, #ffb800 100%); border-radius: 16rpx; padding: 24rpx; margin-bottom: 20rpx; display: flex; align-items: center; }
.medal { font-size: 60rpx; margin-right: 20rpx; }
.info { flex: 1; }
.work-title { font-size: 28rpx; color: #333; font-weight: bold; display: block; }
.level { font-size: 32rpx; color: #8B4513; font-weight: bold; display: block; margin: 8rpx 0; }
.score { font-size: 24rpx; color: #666; }
</style>
