<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">无人机设备<strong>{{ data.droneCount || 0 }}</strong></div>
      <div class="metric">巡检任务<strong>{{ data.taskCount || 0 }}</strong></div>
      <div class="metric">缺陷报告<strong>{{ data.defectCount || 0 }}</strong></div>
      <div class="metric">整改工单<strong>{{ data.rectifyCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日巡检任务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.flightTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '缺陷等级分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.defectPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
