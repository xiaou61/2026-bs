<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">家庭档案<strong>{{ data.homeCount || 0 }}</strong></div>
      <div class="metric">用电设备<strong>{{ data.deviceCount || 0 }}</strong></div>
      <div class="metric">电费账单<strong>{{ data.billCount || 0 }}</strong></div>
      <div class="metric">节能建议<strong>{{ data.suggestionCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日家庭用电趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.energyTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '设备能耗分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.devicePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
