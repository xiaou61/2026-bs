<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">停车场<strong>{{ data.lotCount || 0 }}</strong></div>
      <div class="metric">车位档案<strong>{{ data.spaceCount || 0 }}</strong></div>
      <div class="metric">预约订单<strong>{{ data.reservationCount || 0 }}</strong></div>
      <div class="metric">空位预测<strong>{{ data.predictionCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日停车预约趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.parkingTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '车位状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.spacePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
