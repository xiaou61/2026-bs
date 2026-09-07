<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">考试计划<strong>{{ data.planCount || 0 }}</strong></div>
      <div class="metric">异常行为<strong>{{ data.behaviorCount || 0 }}</strong></div>
      <div class="metric">证据记录<strong>{{ data.evidenceCount || 0 }}</strong></div>
      <div class="metric">告警通知<strong>{{ data.alertCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日异常告警趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.alertTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '异常类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.behaviorPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>




