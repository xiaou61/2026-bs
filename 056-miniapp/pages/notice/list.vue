<template>
  <view class="container">
    <view class="card" v-for="item in notices" :key="item.id" @click="goDetail(item.id)">
      <view class="header">
        <text class="title">{{ item.title }}</text>
        <text class="top" v-if="item.top">置顶</text>
      </view>
      <text class="time">{{ item.createTime }}</text>
    </view>
    <view class="empty" v-if="!notices.length">暂无公告</view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNoticeList } from '../../api'

const notices = ref([])
const goDetail = (id) => uni.navigateTo({ url: `/pages/notice/detail?id=${id}` })

onMounted(async () => {
  const res = await getNoticeList({})
  notices.value = res.data
})
</script>

<style scoped>
.card { background: #fff; border-radius: 16rpx; padding: 24rpx; margin-bottom: 20rpx; }
.card .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12rpx; }
.card .title { font-size: 30rpx; font-weight: bold; flex: 1; }
.card .top { font-size: 22rpx; color: #E6A23C; background: rgba(230,162,60,0.1); padding: 4rpx 12rpx; border-radius: 4rpx; }
.card .time { font-size: 24rpx; color: #999; }
.empty { text-align: center; color: #999; padding: 80rpx; }
</style>
