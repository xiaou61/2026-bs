<template>
  <view class="container">
    <view class="card" v-for="item in works" :key="item.id" @click="goDetail(item.id)">
      <view class="header">
        <text class="title">{{ item.title }}</text>
        <text class="status" :class="statusClass[item.status]">{{ statusText[item.status] }}</text>
      </view>
      <text class="competition">{{ item.competitionTitle }}</text>
      <view class="footer">
        <text class="info">{{ item.wordCount }}字 | {{ item.submitTime }}</text>
        <text class="score" v-if="item.finalScore">得分: {{ item.finalScore }}</text>
      </view>
    </view>
    <view class="empty" v-if="!works.length">
      <text>暂无作品</text>
      <button class="btn-go" @click="goCompetition">去参赛</button>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyWorks } from '../../api'

const works = ref([])
const statusText = { 0: '待审核', 1: '已通过', 2: '已退回', 3: '已撤回' }
const statusClass = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }

const goDetail = (id) => uni.navigateTo({ url: `/pages/work/detail?id=${id}` })
const goCompetition = () => uni.switchTab({ url: '/pages/competition/list' })

onMounted(async () => {
  const token = uni.getStorageSync('token')
  if (!token) {
    uni.showToast({ title: '请先登录', icon: 'none' })
    return uni.navigateTo({ url: '/pages/login/login' })
  }
  try {
    const res = await getMyWorks({ pageNum: 1, pageSize: 50 })
    works.value = res.data.records
  } catch (e) {}
})
</script>

<style scoped>
.card { background: #fff; border-radius: 16rpx; padding: 24rpx; margin-bottom: 20rpx; }
.card .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12rpx; }
.card .title { font-size: 30rpx; font-weight: bold; flex: 1; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.card .status { font-size: 24rpx; padding: 6rpx 16rpx; border-radius: 6rpx; }
.status.warning { color: #E6A23C; background: rgba(230,162,60,0.1); }
.status.success { color: #67C23A; background: rgba(103,194,58,0.1); }
.status.danger { color: #F56C6C; background: rgba(245,108,108,0.1); }
.status.info { color: #909399; background: rgba(144,147,153,0.1); }
.card .competition { font-size: 26rpx; color: #666; display: block; margin-bottom: 12rpx; }
.card .footer { display: flex; justify-content: space-between; align-items: center; }
.card .info { font-size: 24rpx; color: #999; }
.card .score { font-size: 26rpx; color: #409EFF; font-weight: bold; }
.empty { text-align: center; padding: 100rpx 0; color: #999; }
.btn-go { margin-top: 30rpx; background: #409EFF; color: #fff; border: none; width: 200rpx; height: 70rpx; line-height: 70rpx; border-radius: 35rpx; font-size: 28rpx; }
</style>
