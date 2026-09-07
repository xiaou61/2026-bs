<template>
  <div class="portal-page">
    <section class="hero portal-container">
      <div class="hero-copy">
        <div class="hero-tag">完整业务闭环</div>
        <h1>让公开信息、在线申请与后续回访在同一平台顺畅流转。</h1>
        <p>面向申请人提供孩童信息浏览、资料维护、申请提交、进度跟踪和公告查看能力，方便了解每一个关键办理节点。</p>
        <div class="hero-actions">
          <router-link to="/children"><el-button type="primary">查看孩童信息</el-button></router-link>
          <router-link to="/application"><el-button plain>立即申请</el-button></router-link>
        </div>
      </div>
      <div class="hero-side">
        <div class="side-title">系统亮点</div>
        <div class="side-item">儿童档案公开展示</div>
        <div class="side-item">申请审核与匹配审批</div>
        <div class="side-item">签约后回访跟踪</div>
      </div>
    </section>

    <section class="portal-container stats-grid">
      <div v-for="item in cards" :key="item.label" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
      </div>
    </section>

    <section class="portal-container section-grid">
      <div>
        <div class="section-header">
          <div class="section-title">可申请孩童</div>
          <router-link to="/children">查看全部</router-link>
        </div>
        <div class="child-grid">
          <article v-for="item in children" :key="item.id" class="child-card" @click="goDetail(item.id)">
            <img :src="item.avatarUrl" :alt="item.name">
            <div class="child-info">
              <div class="child-name">{{ item.name }}</div>
              <div class="child-meta">{{ item.age }} 岁 · {{ item.gender === 1 ? '男' : '女' }}</div>
              <div class="child-summary">{{ item.summary }}</div>
            </div>
          </article>
        </div>
      </div>
      <div class="notice-panel">
        <div class="section-title">公告资讯</div>
        <div class="notice-list">
          <div v-for="item in notices" :key="item.id" class="notice-item">
            <div class="notice-title">{{ item.title }}</div>
            <div class="notice-time">{{ formatDateTime(item.publishTime || item.createTime) }}</div>
            <div class="notice-content">{{ item.content }}</div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getDashboard, getPublicChildList, getPublicNoticeList } from '../../api'
import { useUserStore } from '../../store/user'
import { formatDateTime } from '../../utils'

const router = useRouter()
const userStore = useUserStore()
const stats = ref({})
const children = ref([])
const notices = ref([])

const cards = computed(() => [
  { label: '开放儿童', value: stats.value.openChildCount || 0 },
  { label: '我的申请', value: stats.value.myApplicationCount || 0 },
  { label: '已完成', value: stats.value.completedCount || 0 },
  { label: '公告数量', value: stats.value.noticeCount || 0 }
])

const goDetail = (id) => {
  router.push(`/children/${id}`)
}

onMounted(async () => {
  const tasks = [
    getPublicChildList({ pageNum: 1, pageSize: 6 }),
    getPublicNoticeList()
  ]
  const dashboardTask = userStore.token ? getDashboard() : Promise.resolve({ data: {} })
  const [dashboardRes, childRes, noticeRes] = await Promise.all([dashboardTask, ...tasks])
  stats.value = dashboardRes.data || {}
  children.value = childRes.data?.list || []
  notices.value = noticeRes.data || []
})
</script>

<style scoped>
.portal-page {
  padding: 28px 0 0;
}

.hero,
.stats-grid,
.section-grid {
  margin-top: 24px;
}

.hero {
  display: grid;
  grid-template-columns: 1.15fr 0.85fr;
  gap: 20px;
}

.hero-copy,
.hero-side,
.stat-card,
.child-card,
.notice-panel {
  border-radius: 28px;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: var(--shadow);
}

.hero-copy,
.hero-side,
.notice-panel {
  padding: 28px;
}

.hero-tag {
  color: var(--accent);
  letter-spacing: 0.2em;
  text-transform: uppercase;
  font-size: 12px;
}

.hero-copy h1 {
  margin: 14px 0;
  font-size: clamp(34px, 4vw, 54px);
  line-height: 1.12;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.hero-copy p {
  line-height: 1.85;
  color: var(--subtle);
}

.hero-actions {
  display: flex;
  gap: 14px;
  margin-top: 26px;
}

.side-title,
.section-title {
  font-size: 24px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  color: var(--brand-strong);
}

.side-item {
  margin-top: 14px;
  padding: 14px 16px;
  border-radius: 16px;
  background: rgba(249, 115, 22, 0.08);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.stat-card {
  padding: 18px 20px;
}

.stat-value {
  margin-top: 10px;
  font-size: 32px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.section-grid {
  display: grid;
  grid-template-columns: 1.2fr 0.8fr;
  gap: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.child-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 18px;
}

.child-card {
  overflow: hidden;
  cursor: pointer;
}

.child-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.child-info {
  padding: 18px;
}

.child-name,
.notice-title {
  font-size: 18px;
  font-weight: 700;
}

.child-meta,
.child-summary,
.notice-time,
.notice-content {
  margin-top: 8px;
  color: var(--subtle);
  line-height: 1.7;
}

.notice-item {
  padding-bottom: 14px;
  margin-bottom: 14px;
  border-bottom: 1px solid rgba(146, 64, 14, 0.1);
}

@media (max-width: 900px) {
  .hero,
  .section-grid,
  .child-grid {
    grid-template-columns: 1fr;
  }
}
</style>
