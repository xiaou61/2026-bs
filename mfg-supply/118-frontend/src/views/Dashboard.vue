<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">库位数量<strong>{{ data.locationCount || 0 }}</strong></div>
      <div class="metric">AGV车辆<strong>{{ data.vehicleCount || 0 }}</strong></div>
      <div class="metric">AGV任务<strong>{{ data.taskCount || 0 }}</strong></div>
      <div class="metric">异常告警<strong>{{ data.alertCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日AGV任务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.taskTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '库区任务分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.zonePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
