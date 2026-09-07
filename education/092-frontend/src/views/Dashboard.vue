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
          <div class="panel-title">近期活动安排</div>
        </template>
        <el-table :data="scheduleList" stripe>
          <el-table-column prop="courseName" label="活动名称" min-width="150" />
          <el-table-column prop="teacherName" label="负责教师" min-width="110" />
          <el-table-column prop="className" label="班级" min-width="120" />
          <el-table-column label="时间" min-width="140">
            <template #default="{ row }">
              {{ row.weekDay }} {{ row.startSection }}-{{ row.endSection }}节
            </template>
          </el-table-column>
          <el-table-column prop="classroom" label="活动室" min-width="100" />
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
import { getDashboard, getPublicNotices, getRecipeList, getScheduleList, getTeacherSchedule } from '../api'
import { useUserStore } from '../store/user'
import { formatDateTime, roleLabelMap } from '../utils'

const userStore = useUserStore()
const stats = ref({})
const notices = ref([])
const scheduleList = ref([])
const recipeStats = ref(0)

const roleText = computed(() => roleLabelMap[userStore.role] || '访客')
const hero = computed(() => {
  if (userStore.role === 'parent') {
    return {
      eyebrow: 'Parent Companion',
      title: '先看孩子安排，再看晨检与食谱，最后把家园反馈说清楚。',
      desc: '家长端把孩子档案、活动安排、食谱、考勤和反馈入口聚合在同一屏。'
    }
  }
  if (userStore.role === 'teacher') {
    return {
      eyebrow: 'Teacher Workflow',
      title: '从活动安排到考勤晨检，教师日常工作可以顺滑闭环。',
      desc: '教师端重点保留幼儿档案、活动安排、食谱发布、入园考勤和健康晨检等高频操作。'
    }
  }
  return {
    eyebrow: 'Garden Operations',
    title: '园区资料、班级活动与家园协同在同一套节奏里稳定运转。',
    desc: '管理员看板聚焦园区总览、班级规模、活动安排、公告发布和运营数据。'
  }
})

const cards = computed(() => {
  if (userStore.role === 'parent') {
    return [
      { label: '孩子档案', value: stats.value.childCount || 0 },
      { label: '活动安排', value: stats.value.scheduleCount || 0 },
      { label: '每周食谱', value: recipeStats.value || 0 },
      { label: '最新公告', value: notices.value.length || 0 }
    ]
  }
  return [
    { label: '账号总数', value: stats.value.userCount || 0 },
    { label: '教师人数', value: stats.value.teacherCount || 0 },
    { label: '家长人数', value: stats.value.parentCount || 0 },
    { label: '幼儿档案', value: stats.value.childCount || 0 },
    { label: '活动课程', value: stats.value.activityCount || 0 },
    { label: '活动安排', value: stats.value.scheduleCount || 0 }
  ]
})

const loadParentData = async () => {
  const [scheduleRes, recipeRes, noticeRes] = await Promise.all([
    getScheduleList({ pageNum: 1, pageSize: 8, status: 1 }),
    getRecipeList({ pageNum: 1, pageSize: 100 }),
    getPublicNotices()
  ])
  scheduleList.value = scheduleRes.data?.list || []
  recipeStats.value = recipeRes.data?.total || 0
  notices.value = noticeRes.data || []
  stats.value = {
    childCount: 1,
    scheduleCount: scheduleList.value.length
  }
}

const loadTeacherAdminData = async () => {
  const tasks = [getDashboard(), getPublicNotices()]
  if (userStore.role === 'teacher') {
    tasks.push(getTeacherSchedule())
  } else {
    tasks.push(getScheduleList({ pageNum: 1, pageSize: 8 }))
  }
  const [dashboardRes, noticeRes, scheduleRes] = await Promise.all(tasks)
  stats.value = dashboardRes.data || {}
  notices.value = noticeRes.data || []
  scheduleList.value = Array.isArray(scheduleRes.data) ? scheduleRes.data : (scheduleRes.data?.list || [])
}

onMounted(async () => {
  if (userStore.role === 'parent') {
    await loadParentData()
    return
  }
  await loadTeacherAdminData()
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
