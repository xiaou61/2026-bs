<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="4"><el-card><div class="card"><span>用户数</span><b>{{ stats.userCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>文章总数</span><b>{{ stats.postCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>已发布</span><b>{{ stats.publishedPostCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>评论数</span><b>{{ stats.commentCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>待审核评论</span><b>{{ stats.pendingCommentCount || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-card style="margin-top: 12px">
      <template #header>近7日发文趋势</template>
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
    xAxis: { type: 'category', data: daily.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [{ name: '发文量', type: 'line', smooth: true, data: daily.map(i => i.postCount) }]
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
