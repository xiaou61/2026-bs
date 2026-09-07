<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">投诉工单<strong>{{ data.complaintCount || 0 }}</strong></div>
      <div class="metric">处置任务<strong>{{ data.taskCount || 0 }}</strong></div>
      <div class="metric">整改通知<strong>{{ data.rectifyCount || 0 }}</strong></div>
      <div class="metric">公告公示<strong>{{ data.publicCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日噪声处置趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.noiseTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '工单状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.noisePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>









