<template>
  <view class="container">
    <view class="banner">
      <text class="title">短文写作竞赛</text>
      <text class="subtitle">用文字记录精彩，用创作赢得荣誉</text>
    </view>
    <view class="section">
      <view class="section-header">
        <text class="section-title">热门竞赛</text>
        <text class="more" @click="goCompetition">更多</text>
      </view>
      <view class="card" v-for="item in competitions" :key="item.id" @click="goDetail(item.id)">
        <image class="cover" :src="item.cover || '/static/default-cover.png'" mode="aspectFill" />
        <view class="info">
          <text class="name">{{ item.title }}</text>
          <text class="category">{{ item.categoryName }}</text>
          <view class="meta">
            <text class="text-sm text-gray">截止: {{ item.submitDeadline || '未设置' }}</text>
          </view>
        </view>
      </view>
      <view class="empty" v-if="!competitions.length">暂无进行中的竞赛</view>
    </view>
    <view class="section">
      <view class="section-header">
        <text class="section-title">最新公告</text>
        <text class="more" @click="goNotice">更多</text>
      </view>
      <view class="notice-item" v-for="item in notices" :key="item.id" @click="goNoticeDetail(item.id)">
        <text class="notice-title">{{ item.title }}</text>
        <text class="notice-time">{{ item.createTime }}</text>
      </view>
      <view class="empty" v-if="!notices.length">暂无公告</view>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCompetitionList, getNoticeList } from '../../api'

const competitions = ref([])
const notices = ref([])

const loadData = async () => {
  try {
    const [compRes, noticeRes] = await Promise.all([
      getCompetitionList(),
      getNoticeList({ type: 0 })
    ])
    competitions.value = compRes.data.slice(0, 3)
    notices.value = noticeRes.data.slice(0, 5)
  } catch (e) {}
}

const goCompetition = () => uni.switchTab({ url: '/pages/competition/list' })
const goDetail = (id) => uni.navigateTo({ url: `/pages/competition/detail?id=${id}` })
const goNotice = () => uni.navigateTo({ url: '/pages/notice/list' })
const goNoticeDetail = (id) => uni.navigateTo({ url: `/pages/notice/detail?id=${id}` })

onMounted(loadData)
</script>

<style scoped>
.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 60rpx 30rpx;
  border-radius: 16rpx;
  margin-bottom: 30rpx;
}
.banner .title { color: #fff; font-size: 40rpx; font-weight: bold; display: block; }
.banner .subtitle { color: rgba(255,255,255,0.8); font-size: 26rpx; margin-top: 10rpx; display: block; }
.section { margin-bottom: 30rpx; }
.section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20rpx; }
.section-title { font-size: 32rpx; font-weight: bold; }
.more { color: #409EFF; font-size: 26rpx; }
.card { background: #fff; border-radius: 16rpx; overflow: hidden; margin-bottom: 20rpx; display: flex; }
.card .cover { width: 200rpx; height: 150rpx; }
.card .info { flex: 1; padding: 20rpx; display: flex; flex-direction: column; justify-content: space-between; }
.card .name { font-size: 28rpx; font-weight: bold; }
.card .category { font-size: 24rpx; color: #409EFF; background: rgba(64,158,255,0.1); padding: 4rpx 12rpx; border-radius: 4rpx; width: fit-content; }
.notice-item { background: #fff; padding: 24rpx; border-radius: 12rpx; margin-bottom: 16rpx; display: flex; justify-content: space-between; align-items: center; }
.notice-title { font-size: 28rpx; flex: 1; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.notice-time { font-size: 24rpx; color: #999; margin-left: 20rpx; }
.empty { text-align: center; color: #999; padding: 40rpx; }
</style>
