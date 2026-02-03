<template>
  <view class="container" v-if="notice">
    <view class="card">
      <text class="title">{{ notice.title }}</text>
      <text class="time">{{ notice.createTime }}</text>
      <view class="divider"></view>
      <text class="content">{{ notice.content }}</text>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNotice } from '../../api'

const notice = ref(null)

onMounted(async () => {
  const pages = getCurrentPages()
  const page = pages[pages.length - 1]
  const res = await getNotice(page.options.id)
  notice.value = res.data
})
</script>

<style scoped>
.card { background: #fff; border-radius: 16rpx; padding: 30rpx; }
.title { font-size: 36rpx; font-weight: bold; display: block; margin-bottom: 16rpx; }
.time { font-size: 26rpx; color: #999; display: block; }
.divider { height: 1rpx; background: #eee; margin: 30rpx 0; }
.content { font-size: 28rpx; color: #333; line-height: 1.8; white-space: pre-wrap; }
</style>
