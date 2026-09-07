<template>
  <div class="page">
    <div class="stat-grid">
      <div class="stat-card"><span>主机资产</span><strong>{{ stats.assetCount || 0 }}</strong></div>
      <div class="stat-card"><span>指标采样</span><strong>{{ stats.sampleCount || 0 }}</strong></div>
      <div class="stat-card"><span>告警规则</span><strong>{{ stats.ruleCount || 0 }}</strong></div>
      <div class="stat-card"><span>告警事件</span><strong>{{ stats.eventCount || 0 }}</strong></div>
      <div class="stat-card"><span>处置工单</span><strong>{{ stats.ticketCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近七次指标趋势', left: 10, top: 8 }, tooltip: {}, xAxis: { type: 'category', data: ['一', '二', '三', '四', '五', '六', '七'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: stats.metricTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '告警级别分布', left: 10, top: 8 }, tooltip: { trigger: 'item' }, series: [{ type: 'pie', radius: ['42%', '70%'], data: stats.severityPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(stats, res.data)
  await nextTick()
  render()
})
</script>
