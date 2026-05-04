<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">运输订单<strong>{{ data.orderCount || 0 }}</strong></div>
      <div class="metric">温控记录<strong>{{ data.temperatureCount || 0 }}</strong></div>
      <div class="metric">异常告警<strong>{{ data.alertCount || 0 }}</strong></div>
      <div class="metric">处置任务<strong>{{ data.taskCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日温控采集趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.temperatureTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '异常类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.alertPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
