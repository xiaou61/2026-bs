<template>
  <div class="page-container">
    <el-row :gutter="14" class="card-row">
      <el-col v-for="item in cards" :key="item.label" :xs="12" :sm="8" :md="6">
        <el-card class="stat-card">
          <div class="label">{{ item.label }}</div>
          <div class="value">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="14" class="chart-row">
      <el-col :xs="24" :md="16">
        <el-card>
          <template #header>近7日评教趋势</template>
          <div ref="trendRef" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="8">
        <el-card>
          <template #header>任务状态分布</template>
          <div ref="statusRef" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getDashboardTrend } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const stats = ref({})
const trend = ref({ daily: [], status: [] })
const trendRef = ref()
const statusRef = ref()
let trendChart
let statusChart

const role = computed(() => userStore.user?.role)

const cards = computed(() => {
  if (role.value === 'ADMIN') {
    return [
      { label: '用户总数', value: stats.value.userCount || 0 },
      { label: '教师账号', value: stats.value.teacherUserCount || 0 },
      { label: '学生账号', value: stats.value.studentUserCount || 0 },
      { label: '考评任务', value: stats.value.taskCount || 0 },
      { label: '评教记录', value: stats.value.recordCount || 0 },
      { label: '待处理申诉', value: stats.value.pendingAppealCount || 0 }
    ]
  }
  if (role.value === 'TEACHER') {
    return [
      { label: '我的任务', value: stats.value.myTaskCount || 0 },
      { label: '被评记录', value: stats.value.myRecordCount || 0 },
      { label: '平均分', value: stats.value.myAverageScore || 0 },
      { label: '我的申诉', value: stats.value.myAppealCount || 0 },
      { label: '待处理申诉', value: stats.value.myPendingAppealCount || 0 },
      { label: '有效公告', value: stats.value.noticeCount || 0 }
    ]
  }
  return [
    { label: '我的任务', value: stats.value.myTaskCount || 0 },
    { label: '我的评教', value: stats.value.myRecordCount || 0 },
    { label: '未完成任务', value: stats.value.unfinishedTaskCount || 0 },
    { label: '进行中任务', value: stats.value.openTaskCount || 0 },
    { label: '有效公告', value: stats.value.noticeCount || 0 }
  ]
})

const loadData = async () => {
  const [statsRes, trendRes] = await Promise.all([getDashboardStats(), getDashboardTrend()])
  stats.value = statsRes.data || {}
  trend.value = trendRes.data || { daily: [], status: [] }
  await nextTick()
  renderCharts()
}

const renderCharts = () => {
  if (!trendRef.value || !statusRef.value) {
    return
  }
  if (!trendChart) {
    trendChart = echarts.init(trendRef.value)
  }
  if (!statusChart) {
    statusChart = echarts.init(statusRef.value)
  }
  const daily = trend.value.daily || []
  trendChart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['评教次数', '平均分'] },
    xAxis: { type: 'category', data: daily.map(item => item.day) },
    yAxis: [
      { type: 'value', name: '次数' },
      { type: 'value', name: '平均分' }
    ],
    series: [
      { name: '评教次数', type: 'line', smooth: true, data: daily.map(item => Number(item.recordCount || 0)) },
      { name: '平均分', type: 'line', smooth: true, yAxisIndex: 1, data: daily.map(item => Number(item.avgScore || 0)) }
    ]
  })

  const status = trend.value.status || []
  statusChart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [
      {
        type: 'pie',
        radius: ['35%', '65%'],
        data: status.map(item => ({ name: statusText(item.status), value: Number(item.count || 0) }))
      }
    ]
  })
}

const statusText = (status) => {
  if (status === 'DRAFT') return '草稿'
  if (status === 'OPEN') return '进行中'
  if (status === 'CLOSED') return '已结束'
  return status
}

const handleResize = () => {
  trendChart && trendChart.resize()
  statusChart && statusChart.resize()
}

onMounted(() => {
  loadData()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  trendChart && trendChart.dispose()
  statusChart && statusChart.dispose()
})
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.stat-card {
  border-radius: 12px;
  background: linear-gradient(145deg, #ecfeff, #f0fdfa);
}

.label {
  font-size: 13px;
  color: #64748b;
}

.value {
  margin-top: 10px;
  font-size: 28px;
  color: #0f172a;
  font-weight: 700;
}

.chart {
  height: 320px;
}
</style>
