<template>
  <div class="dashboard">
    <el-row :gutter="20" class="stat-row" v-if="userStore.isAdmin()">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #409eff"><el-icon><User /></el-icon></div>
          <div class="stat-info"><div class="stat-num">{{ stats.userCount || 0 }}</div><div class="stat-title">注册用户</div></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #67c23a"><el-icon><Location /></el-icon></div>
          <div class="stat-info"><div class="stat-num">{{ stats.spotCount || 0 }}</div><div class="stat-title">景点数量</div></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #e6a23c"><el-icon><Ticket /></el-icon></div>
          <div class="stat-info"><div class="stat-num">{{ stats.orderCount || 0 }}</div><div class="stat-title">订单数量</div></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #f56c6c"><el-icon><Money /></el-icon></div>
          <div class="stat-info"><div class="stat-num">¥{{ stats.totalIncome || 0 }}</div><div class="stat-title">总收入</div></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="welcome-card">
          <h2>❄️ 欢迎来到冰雪之城哈尔滨</h2>
          <p class="desc">哈尔滨，中国黑龙江省省会，享有"东方小巴黎""冰城"之称。这里有世界最大的冰雪主题乐园、充满异域风情的欧式建筑、地道的东北美食...</p>
          <div class="features">
            <div class="feature-item">
              <span class="icon">🏰</span>
              <span>冰雪大世界</span>
            </div>
            <div class="feature-item">
              <span class="icon">⛪</span>
              <span>圣索菲亚教堂</span>
            </div>
            <div class="feature-item">
              <span class="icon">🛣️</span>
              <span>中央大街</span>
            </div>
            <div class="feature-item">
              <span class="icon">🍦</span>
              <span>马迭尔冰棍</span>
            </div>
          </div>
          <el-button type="primary" @click="$router.push('/spots')">探索景点</el-button>
          <el-button @click="$router.push('/routes')">查看路线</el-button>
        </el-card>
        <el-card class="ranking-card" v-if="spotRanking.length">
          <template #header><span>🔥 热门景点 TOP10</span></template>
          <div class="ranking-list">
            <div v-for="(spot, index) in spotRanking" :key="spot.id" class="ranking-item" @click="$router.push(`/spots/${spot.id}`)">
              <span class="rank" :class="{ top3: index < 3 }">{{ index + 1 }}</span>
              <span class="name">{{ spot.name }}</span>
              <span class="views">{{ spot.viewCount }}次浏览</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="announcement-card">
          <template #header><span>📢 最新公告</span></template>
          <div v-if="announcements.length">
            <div v-for="item in announcements" :key="item.id" class="announcement-item" @click="$router.push('/announcements')">
              <el-tag size="small" :type="item.isTop ? 'danger' : ''">{{ item.isTop ? '置顶' : '公告' }}</el-tag>
              <span class="title">{{ item.title }}</span>
            </div>
          </div>
          <el-empty v-else description="暂无公告" :image-size="60" />
        </el-card>
        <el-card class="quick-card">
          <template #header><span>⚡ 快捷入口</span></template>
          <div class="quick-list">
            <div class="quick-item" @click="$router.push('/ticket/orders')"><el-icon><Tickets /></el-icon><span>我的订单</span></div>
            <div class="quick-item" @click="$router.push('/favorites')"><el-icon><Star /></el-icon><span>我的收藏</span></div>
            <div class="quick-item" @click="$router.push('/notes/edit')"><el-icon><Edit /></el-icon><span>写游记</span></div>
            <div class="quick-item" @click="$router.push('/activities')"><el-icon><Calendar /></el-icon><span>活动报名</span></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStatistics, getSpotRanking, getTopAnnouncements } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const stats = ref({})
const spotRanking = ref([])
const announcements = ref([])

onMounted(async () => {
  try {
    if (userStore.isAdmin()) {
      const res = await getStatistics()
      stats.value = res.data
    }
    const rankRes = await getSpotRanking()
    spotRanking.value = rankRes.data || []
    const annRes = await getTopAnnouncements()
    announcements.value = annRes.data || []
  } catch (e) {}
})
</script>

<style scoped>
.stat-row { margin-bottom: 20px; }
.stat-card { display: flex; align-items: center; padding: 20px; }
.stat-icon { width: 60px; height: 60px; border-radius: 10px; display: flex; align-items: center; justify-content: center; color: white; font-size: 28px; margin-right: 15px; }
.stat-info { flex: 1; }
.stat-num { font-size: 28px; font-weight: bold; color: #333; }
.stat-title { color: #999; font-size: 14px; }
.welcome-card { margin-bottom: 20px; }
.welcome-card h2 { margin-bottom: 15px; color: #333; }
.welcome-card .desc { color: #666; line-height: 1.8; margin-bottom: 20px; }
.features { display: flex; gap: 20px; margin-bottom: 20px; }
.feature-item { display: flex; align-items: center; gap: 8px; padding: 10px 15px; background: #f5f7fa; border-radius: 8px; }
.feature-item .icon { font-size: 20px; }
.ranking-card { margin-bottom: 20px; }
.ranking-list { }
.ranking-item { display: flex; align-items: center; padding: 10px 0; border-bottom: 1px solid #eee; cursor: pointer; }
.ranking-item:hover { background: #f5f7fa; }
.ranking-item:last-child { border-bottom: none; }
.rank { width: 24px; height: 24px; border-radius: 50%; background: #ddd; color: #666; display: flex; align-items: center; justify-content: center; font-size: 12px; margin-right: 10px; }
.rank.top3 { background: #f56c6c; color: white; }
.name { flex: 1; }
.views { color: #999; font-size: 12px; }
.announcement-card { margin-bottom: 20px; }
.announcement-item { display: flex; align-items: center; gap: 10px; padding: 10px 0; border-bottom: 1px solid #eee; cursor: pointer; }
.announcement-item:last-child { border-bottom: none; }
.announcement-item .title { flex: 1; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.quick-card { }
.quick-list { display: grid; grid-template-columns: repeat(2, 1fr); gap: 15px; }
.quick-item { display: flex; flex-direction: column; align-items: center; gap: 8px; padding: 20px; background: #f5f7fa; border-radius: 8px; cursor: pointer; transition: all 0.3s; }
.quick-item:hover { background: #e6f7ff; }
.quick-item .el-icon { font-size: 24px; color: #409eff; }
</style>
