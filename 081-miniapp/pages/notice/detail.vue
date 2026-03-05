<template>
  <view class="container">
    <view class="card" v-if="detail.id">
      <view class="title">{{ detail.title }}</view>
      <view class="meta">类型：{{ detail.type || 'system' }}</view>
      <view class="meta">发布时间：{{ detail.publishTime || detail.createTime }}</view>
      <view class="meta">浏览量：{{ detail.viewCount || 0 }}</view>
      <view class="content">{{ detail.content }}</view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getNoticeDetail } from '../../api'

const detail = ref({})

const loadDetail = async (id) => {
  try {
    const res = await getNoticeDetail(id)
    detail.value = res.data || {}
  } catch (e) {}
}

onLoad((options) => {
  if (!options.id) return
  loadDetail(options.id)
})
</script>

<style scoped>
.title { font-size: 34rpx; font-weight: 700; color: #2d5baf; margin-bottom: 14rpx; }
.meta { font-size: 24rpx; color: #909399; margin-bottom: 6rpx; }
.content { margin-top: 24rpx; font-size: 28rpx; color: #333; line-height: 1.7; white-space: pre-wrap; }
</style>
