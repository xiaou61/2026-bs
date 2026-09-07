<template>
  <view class="container">
    <view v-for="item in list" :key="item.id" class="card notice-card" @click="goDetail(item.id)">
      <view class="title">{{ item.title }}</view>
      <view class="meta">类型：{{ item.type || 'system' }}</view>
      <view class="meta">发布时间：{{ item.publishTime || item.createTime }}</view>
      <view class="meta">浏览量：{{ item.viewCount || 0 }}</view>
    </view>
    <view v-if="!list.length" class="empty">暂无公告</view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNoticeList } from '../../api'

const list = ref([])

const loadData = async () => {
  try {
    const res = await getNoticeList({ pageNum: 1, pageSize: 50 })
    list.value = res.data.records || []
  } catch (e) {}
}

const goDetail = (id) => uni.navigateTo({ url: `/pages/notice/detail?id=${id}` })

onMounted(loadData)
</script>

<style scoped>
.notice-card { margin-bottom: 16rpx; }
.title { font-size: 30rpx; font-weight: 600; margin-bottom: 10rpx; color: #2d5baf; }
.meta { font-size: 24rpx; color: #909399; margin-top: 6rpx; }
.empty { text-align: center; color: #999; margin-top: 120rpx; }
</style>
