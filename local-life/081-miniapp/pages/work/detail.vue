<template>
  <view class="container" v-if="work">
    <view class="card">
      <text class="title">{{ work.title }}</text>
      <view class="meta">
        <text class="competition">{{ work.competitionTitle }}</text>
        <text class="status" :class="statusClass[work.status]">{{ statusText[work.status] }}</text>
      </view>
      <view class="info-row">
        <text>字数: {{ work.wordCount }}</text>
        <text>提交时间: {{ work.submitTime }}</text>
      </view>
      <view class="info-row" v-if="work.finalScore">
        <text class="score">最终得分: {{ work.finalScore }}</text>
      </view>
      <view class="info-row" v-if="work.rejectReason">
        <text class="reject">退回原因: {{ work.rejectReason }}</text>
      </view>
    </view>
    <view class="card">
      <text class="section-title">作品内容</text>
      <text class="content">{{ work.content }}</text>
    </view>
    <view class="bottom-bar" v-if="work.status === 0 || work.status === 2">
      <button class="btn-edit" @click="goEdit">修改作品</button>
      <button class="btn-withdraw" @click="handleWithdraw" v-if="work.status === 0">撤回作品</button>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getWork, withdrawWork } from '../../api'

const work = ref(null)
const statusText = { 0: '待审核', 1: '已通过', 2: '已退回', 3: '已撤回' }
const statusClass = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }
let workId = null

const goEdit = () => uni.navigateTo({ url: `/pages/work/submit?id=${workId}` })

const handleWithdraw = () => {
  uni.showModal({
    title: '提示',
    content: '确定要撤回作品吗？',
    success: async (res) => {
      if (res.confirm) {
        await withdrawWork(workId)
        uni.showToast({ title: '已撤回', icon: 'success' })
        setTimeout(() => uni.navigateBack(), 1000)
      }
    }
  })
}

onMounted(async () => {
  const pages = getCurrentPages()
  const page = pages[pages.length - 1]
  workId = page.options.id
  const res = await getWork(workId)
  work.value = res.data
})
</script>

<style scoped>
.card { background: #fff; border-radius: 16rpx; padding: 24rpx; margin-bottom: 20rpx; }
.title { font-size: 34rpx; font-weight: bold; display: block; margin-bottom: 16rpx; }
.meta { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16rpx; }
.competition { font-size: 26rpx; color: #666; }
.status { font-size: 24rpx; padding: 6rpx 16rpx; border-radius: 6rpx; }
.status.warning { color: #E6A23C; background: rgba(230,162,60,0.1); }
.status.success { color: #67C23A; background: rgba(103,194,58,0.1); }
.status.danger { color: #F56C6C; background: rgba(245,108,108,0.1); }
.status.info { color: #909399; background: rgba(144,147,153,0.1); }
.info-row { font-size: 26rpx; color: #999; margin-bottom: 10rpx; display: flex; gap: 30rpx; }
.score { color: #409EFF; font-weight: bold; }
.reject { color: #F56C6C; }
.section-title { font-size: 30rpx; font-weight: bold; margin-bottom: 16rpx; display: block; }
.content { font-size: 28rpx; color: #333; line-height: 1.8; white-space: pre-wrap; }
.bottom-bar { position: fixed; bottom: 0; left: 0; right: 0; padding: 20rpx 30rpx; background: #fff; display: flex; gap: 20rpx; box-shadow: 0 -4rpx 12rpx rgba(0,0,0,0.05); }
.bottom-bar button { flex: 1; height: 80rpx; line-height: 80rpx; border-radius: 12rpx; font-size: 28rpx; }
.btn-edit { background: #409EFF; color: #fff; border: none; }
.btn-withdraw { background: #f5f5f5; color: #666; border: none; }
</style>
