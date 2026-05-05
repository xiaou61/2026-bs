<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">患者档案<strong>{{ data.patientCount || 0 }}</strong></div>
      <div class="metric">不良反应上报<strong>{{ data.reportCount || 0 }}</strong></div>
      <div class="metric">风险评估<strong>{{ data.riskCount || 0 }}</strong></div>
      <div class="metric">随访记录<strong>{{ data.followupCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日不良反应上报趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.reportTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '严重等级分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.severityPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
