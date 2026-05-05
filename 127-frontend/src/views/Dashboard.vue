<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">企业档案<strong>{{ data.companyCount || 0 }}</strong></div>
      <div class="metric">排放记录<strong>{{ data.emissionCount || 0 }}</strong></div>
      <div class="metric">减排任务<strong>{{ data.taskCount || 0 }}</strong></div>
      <div class="metric">碳排预警<strong>{{ data.warningCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日碳排核算趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.carbonTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '排放范围分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.scopePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
