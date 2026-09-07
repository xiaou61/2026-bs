<template>
  <div class="dashboard">
    <div class="hero-card">
      <div>
        <div class="hero-eyebrow">{{ hero.eyebrow }}</div>
        <h2>{{ hero.title }}</h2>
        <p>{{ hero.desc }}</p>
      </div>
      <div class="hero-side">
        <div class="hero-side-label">当前角色</div>
        <div class="hero-side-role">{{ roleText }}</div>
      </div>
    </div>

    <div class="stats-grid">
      <div v-for="item in cards" :key="item.label" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
      </div>
    </div>

    <div class="panel-grid">
      <el-card class="panel-card" shadow="never">
        <template #header>
          <div class="panel-title">近期剧目安排</div>
        </template>
        <el-table :data="performanceList" stripe>
          <el-table-column prop="repertoireName" label="剧目" min-width="150" />
          <el-table-column prop="artistName" label="艺术家" min-width="110" />
          <el-table-column prop="className" label="场馆" min-width="120" />
          <el-table-column label="时间" min-width="140">
            <template #default="{ row }">
              {{ row.weekDay }} {{ row.startSection }}-{{ row.endSection }}节
            </template>
          </el-table-column>
          <el-table-column prop="classroom" label="场地" min-width="100" />
        </el-table>
      </el-card>

      <el-card class="panel-card" shadow="never">
        <template #header>
          <div class="panel-title">公告提醒</div>
        </template>
        <div class="notice-list">
          <div v-for="item in notices" :key="item.id" class="notice-item">
            <div class="notice-type">{{ item.type || 'system' }}</div>
            <div class="notice-main">
              <div class="notice-title">{{ item.title }}</div>
              <div class="notice-time">{{ formatDateTime(item.publishTime || item.createTime) }}</div>
              <div class="notice-content">{{ item.content }}</div>
            </div>
          </div>
          <el-empty v-if="!notices.length" description="暂无公告" />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { getDashboard, getPublicNotices, getResourceList, getScheduleList, getMemberSchedule, getArtistSchedule } from '../api'
import { useUserStore } from '../store/user'
import { formatDateTime, roleLabelMap } from '../utils'

const userStore = useUserStore()
const stats = ref({})
const notices = ref([])
const performanceList = ref([])
const resourceStats = ref(0)

const roleText = computed(() => roleLabelMap[userStore.role] || '访客')
const hero = computed(() => {
  if (userStore.role === 'member') {
    return {
      eyebrow: 'Member Learning Hub',
      title: '今天先看行程，再拿资源，最后把赏析互动补完。',
      desc: '会员端聚合预约、资源、研学评分与赏析互动入口，保持学习任务一屏完成。'
    }
  }
  if (userStore.role === 'artist') {
    return {
      eyebrow: 'Artist Studio',
      title: '从主讲排期到签到研学评分，传承过程可以顺滑闭环。',
      desc: '艺术家端重点保留剧目安排、资源发放、签到与研学评分录入的高频操作。'
    }
  }
  return {
    eyebrow: 'Curation Console',
    title: '剧种分类、剧目、排期与公告在同一套节奏里协同运转。',
    desc: '管理员看板关注文化苑总览与内容结构维护，让剧目流程从配置开始就保持清晰。'
  }
})

const cards = computed(() => {
  if (userStore.role === 'member') {
    return [
      { label: '已选剧目', value: stats.value.performanceCount || 0 },
      { label: '可用资源', value: resourceStats.value || 0 },
      { label: '最新公告', value: notices.value.length || 0 }
    ]
  }
  return [
    { label: '用户总数', value: stats.value.userCount || 0 },
    { label: '艺术家人数', value: stats.value.artistCount || 0 },
    { label: '会员人数', value: stats.value.memberCount || 0 },
    { label: '剧目总数', value: stats.value.repertoireCount || 0 },
    { label: '排期总数', value: stats.value.performanceCount || 0 },
    { label: '预约总数', value: stats.value.bookingCount || 0 }
  ]
})

const loadMemberData = async () => {
  const [performanceRes, resourceRes, noticeRes] = await Promise.all([
    getMemberSchedule(),
    getResourceList({ pageNum: 1, pageSize: 100 }),
    getPublicNotices()
  ])
  performanceList.value = performanceRes.data || []
  resourceStats.value = resourceRes.data?.total || 0
  notices.value = noticeRes.data || []
  stats.value = { performanceCount: performanceList.value.length }
}

const loadArtistAdminData = async () => {
  const tasks = [getDashboard(), getPublicNotices()]
  if (userStore.role === 'artist') {
    tasks.push(getArtistSchedule())
  } else {
    tasks.push(getScheduleList({ pageNum: 1, pageSize: 8 }))
  }
  const [dashboardRes, noticeRes, performanceRes] = await Promise.all(tasks)
  stats.value = dashboardRes.data || {}
  notices.value = noticeRes.data || []
  performanceList.value = Array.isArray(performanceRes.data) ? performanceRes.data : (performanceRes.data?.list || [])
}

onMounted(async () => {
  if (userStore.role === 'member') {
    await loadMemberData()
    return
  }
  await loadArtistAdminData()
})
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.hero-card {
  padding: 28px;
  border-radius: 28px;
  display: flex;
  justify-content: space-between;
  gap: 24px;
  background: linear-gradient(135deg, rgba(154, 52, 18, 0.92), rgba(15, 118, 110, 0.88));
  color: #fff;
  box-shadow: 0 24px 46px rgba(120, 53, 15, 0.2);
}

.hero-eyebrow {
  letter-spacing: 0.24em;
  text-transform: uppercase;
  font-size: 12px;
  opacity: 0.8;
}

.hero-card h2 {
  margin: 10px 0 12px;
  font-size: clamp(28px, 3vw, 40px);
  line-height: 1.2;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.hero-card p {
  margin: 0;
  max-width: 760px;
  line-height: 1.85;
  opacity: 0.88;
}

.hero-side {
  min-width: 180px;
  padding: 18px;
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.14);
  align-self: flex-start;
}

.hero-side-label {
  font-size: 12px;
  opacity: 0.7;
}

.hero-side-role {
  margin-top: 10px;
  font-size: 26px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
}

.stat-card {
  padding: 20px;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.88);
  box-shadow: var(--shadow);
}

.stat-label {
  color: var(--subtle);
  font-size: 13px;
}

.stat-value {
  margin-top: 12px;
  font-size: 34px;
  color: #3f2f1f;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.panel-grid {
  display: grid;
  grid-template-columns: 1.2fr 0.8fr;
  gap: 18px;
}

.panel-card {
  border: none;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.88);
}

.panel-title {
  font-size: 18px;
  color: #3f2f1f;
  font-weight: 700;
}

.notice-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.notice-item {
  display: grid;
  grid-template-columns: 82px 1fr;
  gap: 12px;
}

.notice-type {
  height: fit-content;
  padding: 8px 10px;
  border-radius: 999px;
  text-align: center;
  background: rgba(154, 52, 18, 0.1);
  color: var(--brand);
  font-size: 12px;
  text-transform: uppercase;
}

.notice-main {
  padding-bottom: 14px;
  border-bottom: 1px solid rgba(154, 52, 18, 0.1);
}

.notice-title {
  font-size: 16px;
  font-weight: 700;
  color: #374151;
}

.notice-time {
  margin-top: 6px;
  font-size: 12px;
  color: var(--subtle);
}

.notice-content {
  margin-top: 8px;
  line-height: 1.7;
  color: #4b5563;
}

@media (max-width: 960px) {
  .hero-card {
    flex-direction: column;
  }

  .panel-grid {
    grid-template-columns: 1fr;
  }
}
</style>


