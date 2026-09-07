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
          <template #header>近7日订单趋势</template>
          <div ref="trendRef" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="8">
        <el-card>
          <template #header>订单状态分布</template>
          <div ref="statusRef" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-card>
      <template #header>模块说明</template>
      <div class="tips">
        <p>1. 用户端可管理个人资料、出行人、收藏、订单、评价和投诉。</p>
        <p>2. 管理员端可进行用户维护、订单监管、评价审核回复和投诉处理。</p>
      </div>
    </el-card>
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
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const trendRef = ref()
const statusRef = ref()
let trendChart
let statusChart

const cards = computed(() => {
  if (isAdmin.value) {
    return [
      { label: '用户总数', value: stats.value.userCount || 0 },
      { label: '订单总数', value: stats.value.orderCount || 0 },
      { label: '评价总数', value: stats.value.reviewCount || 0 },
      { label: '待处理投诉', value: stats.value.pendingComplaintCount || 0 },
      { label: '景点总数', value: stats.value.spotCount || 0 }
    ]
  }
  return [
    { label: '我的订单', value: stats.value.myOrderCount || 0 },
    { label: '待支付订单', value: stats.value.myWaitPayCount || 0 },
    { label: '已完成订单', value: stats.value.myFinishedCount || 0 },
    { label: '我的收藏', value: stats.value.myFavoriteCount || 0 },
    { label: '我的投诉', value: stats.value.myComplaintCount || 0 },
    { label: '常用出行人', value: stats.value.myTravelerCount || 0 }
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
    legend: { data: ['订单数', '金额'] },
    xAxis: { type: 'category', data: daily.map(item => item.day) },
    yAxis: [
      { type: 'value', name: '订单数' },
      { type: 'value', name: '金额' }
    ],
    series: [
      { name: '订单数', type: 'line', smooth: true, data: daily.map(item => item.orderCount || 0) },
      { name: '金额', type: 'line', smooth: true, yAxisIndex: 1, data: daily.map(item => Number(item.totalAmount || 0)) }
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
        data: status.map(item => ({ name: statusText(item.status), value: item.count || 0 }))
      }
    ]
  })
}

const statusText = (status) => {
  if (status === 'WAIT_PAY') return '待支付'
  if (status === 'PAID') return '已支付'
  if (status === 'FINISHED') return '已完成'
  if (status === 'CANCELED') return '已取消'
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

.card-row {
  margin-bottom: 0;
}

.chart-row {
  margin-top: 0;
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

.tips {
  color: #334155;
  line-height: 1.9;
}

.chart {
  height: 320px;
}
</style>
