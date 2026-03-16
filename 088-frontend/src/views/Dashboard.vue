<template>
  <div class="dashboard">
    <div class="hero-card">
      <div>
        <div class="hero-eyebrow">Child Adoption Workflow</div>
        <h2>{{ headline }}</h2>
        <p>{{ description }}</p>
      </div>
      <div class="hero-side">
        <div class="hero-side-label">当前角色</div>
        <div class="hero-side-role">{{ roleLabelMap[userStore.role] || '访客' }}</div>
      </div>
    </div>

    <div class="stats-grid">
      <div v-for="item in cards" :key="item.label" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
      </div>
    </div>

    <div class="panel-grid">
      <el-card shadow="never">
        <template #header>
          <div class="panel-title">最新公告</div>
        </template>
        <div class="notice-list">
          <div v-for="item in notices" :key="item.id" class="notice-item">
            <div class="notice-title">{{ item.title }}</div>
            <div class="notice-time">{{ formatDateTime(item.publishTime || item.createTime) }}</div>
            <div class="notice-content">{{ item.content }}</div>
          </div>
          <el-empty v-if="!notices.length" description="暂无公告" />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { getDashboard, getPublicNoticeList } from '../api'
import { useUserStore } from '../store/user'
import { formatDateTime, roleLabelMap } from '../utils'

const userStore = useUserStore()
const stats = ref({})
const notices = ref([])

const headline = computed(() => userStore.role === 'reviewer' ? '聚焦审核、家访、匹配和回访的关键节点。' : '从儿童档案到签约回访，系统把收养流程串成一条线。')
const description = computed(() => userStore.role === 'applicant' ? '申请人可查看自己的申请状态、已完成节点与公告资讯。' : '后台可以同时掌握开放儿童数量、申请总量、签约完成与回访执行情况。')

const cards = computed(() => {
  if (userStore.role === 'applicant') {
    return [
      { label: '我的申请', value: stats.value.myApplicationCount || 0 },
      { label: '已完成', value: stats.value.completedCount || 0 },
      { label: '开放儿童', value: stats.value.openChildCount || 0 },
      { label: '公告数', value: stats.value.noticeCount || 0 }
    ]
  }
  return [
    { label: '用户总数', value: stats.value.userCount || 0 },
    { label: '儿童档案', value: stats.value.childCount || 0 },
    { label: '可申请儿童', value: stats.value.openChildCount || 0 },
    { label: '申请总数', value: stats.value.applicationCount || 0 },
    { label: '已签约', value: stats.value.matchedCount || 0 },
    { label: '回访记录', value: stats.value.followCount || 0 }
  ]
})

onMounted(async () => {
  const [dashboardRes, noticeRes] = await Promise.all([getDashboard(), getPublicNoticeList()])
  stats.value = dashboardRes.data || {}
  notices.value = noticeRes.data || []
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
  gap: 22px;
  background: linear-gradient(135deg, rgba(234, 88, 12, 0.92), rgba(15, 118, 110, 0.88));
  color: #fff;
}

.hero-eyebrow {
  font-size: 12px;
  letter-spacing: 0.24em;
  text-transform: uppercase;
  opacity: 0.78;
}

.hero-card h2 {
  margin: 12px 0;
  font-size: clamp(28px, 3vw, 40px);
  line-height: 1.2;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.hero-card p {
  margin: 0;
  max-width: 720px;
  line-height: 1.8;
}

.hero-side {
  min-width: 180px;
  padding: 18px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.14);
}

.hero-side-role {
  margin-top: 10px;
  font-size: 24px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
}

.stat-card {
  padding: 18px 20px;
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.92);
}

.stat-label {
  color: var(--subtle);
}

.stat-value {
  margin-top: 12px;
  font-size: 34px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.panel-title,
.notice-title {
  font-weight: 700;
}

.notice-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.notice-item {
  padding-bottom: 14px;
  border-bottom: 1px solid rgba(146, 64, 14, 0.1);
}

.notice-time,
.notice-content {
  margin-top: 8px;
  color: var(--subtle);
}
</style>
