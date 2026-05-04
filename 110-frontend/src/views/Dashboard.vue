<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">数据主体<strong>{{ data.subjectCount || 0 }}</strong></div>
      <div class="metric">授权记录<strong>{{ data.authorizationCount || 0 }}</strong></div>
      <div class="metric">访问日志<strong>{{ data.logCount || 0 }}</strong></div>
      <div class="metric">风险预警<strong>{{ data.warningCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日授权趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.authTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '风险类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.riskPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
