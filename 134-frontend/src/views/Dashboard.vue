<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">科研项目<strong>{{ data.projectCount || 0 }}</strong></div>
      <div class="metric">报销申请<strong>{{ data.claimCount || 0 }}</strong></div>
      <div class="metric">科研成果<strong>{{ data.achievementCount || 0 }}</strong></div>
      <div class="metric">风险预警<strong>{{ data.riskCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日报销申请趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.claimTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '成果类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.achievementPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
