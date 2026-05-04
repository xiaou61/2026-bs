<template>
  <div class="page">
    <div class="stat-grid">
      <div class="stat-card"><span>应用服务</span><strong>{{ stats.serviceCount || 0 }}</strong></div>
      <div class="stat-card"><span>发布单</span><strong>{{ stats.orderCount || 0 }}</strong></div>
      <div class="stat-card"><span>部署任务</span><strong>{{ stats.taskCount || 0 }}</strong></div>
      <div class="stat-card"><span>回滚记录</span><strong>{{ stats.rollbackCount || 0 }}</strong></div>
      <div class="stat-card"><span>版本制品</span><strong>{{ stats.artifactCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近七次发布趋势', left: 10, top: 8 }, tooltip: {}, xAxis: { type: 'category', data: ['一', '二', '三', '四', '五', '六', '七'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: stats.releaseTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '发布状态分布', left: 10, top: 8 }, tooltip: { trigger: 'item' }, series: [{ type: 'pie', radius: ['42%', '70%'], data: stats.statusPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(stats, res.data)
  await nextTick()
  render()
})
</script>
