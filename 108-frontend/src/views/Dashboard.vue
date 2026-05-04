<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">云账号<strong>{{ data.accountCount || 0 }}</strong></div>
      <div class="metric">成本账单<strong>{{ data.billCount || 0 }}</strong></div>
      <div class="metric">优化建议<strong>{{ data.adviceCount || 0 }}</strong></div>
      <div class="metric">异常事件<strong>{{ data.anomalyCount || 0 }}</strong></div>
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
  const trend = echarts.init(trendRef.value)
  trend.setOption({ title: { text: '近7月成本趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['10月', '11月', '12月', '1月', '2月', '3月', '4月'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.costTrend || [] }] })
  const pie = echarts.init(pieRef.value)
  pie.setOption({ title: { text: '节省来源分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.savingPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
