<template>
  <div class="page">
    <div class="stat-grid">
      <div class="stat-card"><span>项目仓库</span><strong>{{ stats.repositoryCount || 0 }}</strong></div>
      <div class="stat-card"><span>依赖包</span><strong>{{ stats.dependencyCount || 0 }}</strong></div>
      <div class="stat-card"><span>扫描任务</span><strong>{{ stats.taskCount || 0 }}</strong></div>
      <div class="stat-card"><span>风险问题</span><strong>{{ stats.riskCount || 0 }}</strong></div>
      <div class="stat-card"><span>审计报告</span><strong>{{ stats.reportCount || 0 }}</strong></div>
    </div>
    <div class="chart-grid">
      <div ref="trendRef" class="chart"></div>
      <div ref="pieRef" class="chart"></div>
    </div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboard } from '../api'
const stats = reactive({})
const trendRef = ref()
const pieRef = ref()
const render = () => {
  echarts.init(trendRef.value).setOption({
    title: { text: '近七次风险发现趋势', left: 10, top: 8 },
    tooltip: {},
    xAxis: { type: 'category', data: ['一', '二', '三', '四', '五', '六', '七'] },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, areaStyle: {}, data: stats.riskTrend || [] }]
  })
  echarts.init(pieRef.value).setOption({
    title: { text: '许可证分布', left: 10, top: 8 },
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['42%', '70%'], data: stats.licensePie || [] }]
  })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(stats, res.data)
  await nextTick()
  render()
})
</script>
