<template>
  <view class="container">
    <scroll-view scroll-x class="category-scroll">
      <view class="category-item" :class="{ active: !currentCategory }" @click="currentCategory = null">全部</view>
      <view class="category-item" :class="{ active: currentCategory === item.id }" v-for="item in categories" :key="item.id" @click="currentCategory = item.id">{{ item.name }}</view>
    </scroll-view>
    <view class="list">
      <view class="card" v-for="item in competitions" :key="item.id" @click="goDetail(item.id)">
        <image class="cover" :src="item.cover || '/static/default-cover.png'" mode="aspectFill" />
        <view class="info">
          <text class="name">{{ item.title }}</text>
          <text class="desc">{{ item.description }}</text>
          <view class="meta">
            <text class="tag">{{ item.categoryName }}</text>
            <text class="time">截止: {{ item.submitDeadline || '未设置' }}</text>
          </view>
        </view>
      </view>
      <view class="empty" v-if="!competitions.length">暂无竞赛</view>
    </view>
  </view>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { getCategories, getCompetitionList } from '../../api'

const categories = ref([])
const competitions = ref([])
const currentCategory = ref(null)

const loadData = async () => {
  try {
    const res = await getCompetitionList(currentCategory.value)
    competitions.value = res.data
  } catch (e) {}
}

const goDetail = (id) => uni.navigateTo({ url: `/pages/competition/detail?id=${id}` })

watch(currentCategory, loadData)

onMounted(async () => {
  const res = await getCategories()
  categories.value = res.data
  loadData()
})
</script>

<style scoped>
.category-scroll { white-space: nowrap; padding: 20rpx 0; background: #fff; margin-bottom: 20rpx; }
.category-item { display: inline-block; padding: 16rpx 32rpx; margin: 0 10rpx; border-radius: 30rpx; background: #f5f5f5; font-size: 26rpx; }
.category-item.active { background: #409EFF; color: #fff; }
.card { background: #fff; border-radius: 16rpx; overflow: hidden; margin-bottom: 20rpx; }
.card .cover { width: 100%; height: 300rpx; }
.card .info { padding: 24rpx; }
.card .name { font-size: 32rpx; font-weight: bold; display: block; margin-bottom: 10rpx; }
.card .desc { font-size: 26rpx; color: #666; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.card .meta { display: flex; justify-content: space-between; align-items: center; margin-top: 16rpx; }
.card .tag { font-size: 24rpx; color: #409EFF; background: rgba(64,158,255,0.1); padding: 6rpx 16rpx; border-radius: 6rpx; }
.card .time { font-size: 24rpx; color: #999; }
.empty { text-align: center; color: #999; padding: 80rpx; }
</style>
