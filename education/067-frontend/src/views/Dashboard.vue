<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="4"><el-card><div class="card"><span>用户数</span><b>{{ stats.userCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>课程数</span><b>{{ stats.courseCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>选课记录</span><b>{{ stats.enrollCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>活动报名</span><b>{{ stats.signupCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>岗位投递</span><b>{{ stats.applyCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>失物信息</span><b>{{ stats.lostCount || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-card style="margin-top: 12px">
      <template #header>近7日业务趋势</template>
      <div ref="chartRef" class="chart"></div>
    </el-card>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getDashboardTrend } from '../api'

const stats = ref({})
const chartRef = ref()

const loadData = async () => {
  const [statsRes, trendRes] = await Promise.all([getDashboardStats(), getDashboardTrend()])
  stats.value = statsRes.data || {}
  const daily = trendRes.data?.daily || []
  await nextTick()
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['选课', '报名', '投递'] },
    xAxis: { type: 'category', data: daily.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '选课', type: 'line', smooth: true, data: daily.map(i => i.enrollCount) },
      { name: '报名', type: 'line', smooth: true, data: daily.map(i => i.signupCount) },
      { name: '投递', type: 'line', smooth: true, data: daily.map(i => i.applyCount) }
    ]
  })
}

onMounted(loadData)
</script>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.card b {
  font-size: 22px;
  color: #0f172a;
}

.chart {
  height: 360px;
}
</style>
