<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">选品池<strong>{{ data.selectionCount || 0 }}</strong></div>
      <div class="metric">直播场次<strong>{{ data.sessionCount || 0 }}</strong></div>
      <div class="metric">直播订单<strong>{{ data.orderCount || 0 }}</strong></div>
      <div class="metric">售后工单<strong>{{ data.ticketCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日GMV趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.gmvTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '售后类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.ticketPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
