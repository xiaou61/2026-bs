<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">充电站点<strong>{{ data.stationCount || 0 }}</strong></div>
      <div class="metric">充电桩位<strong>{{ data.pileCount || 0 }}</strong></div>
      <div class="metric">预约订单<strong>{{ data.appointmentCount || 0 }}</strong></div>
      <div class="metric">故障报修<strong>{{ data.faultCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日预约充电趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.chargeTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '桩位状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pilePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
