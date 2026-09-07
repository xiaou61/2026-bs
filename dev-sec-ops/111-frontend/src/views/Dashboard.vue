<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">员工档案<strong>{{ data.employeeCount || 0 }}</strong></div>
      <div class="metric">演练活动<strong>{{ data.campaignCount || 0 }}</strong></div>
      <div class="metric">点击追踪<strong>{{ data.clickCount || 0 }}</strong></div>
      <div class="metric">风险评分<strong>{{ data.riskCount || 0 }}</strong></div>
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
const data = reactive({})
const trendRef = ref()
const pieRef = ref()
const draw = () => {
  echarts.init(trendRef.value).setOption({ title: { text: '近7次演练趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.campaignTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '员工风险分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.riskPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
