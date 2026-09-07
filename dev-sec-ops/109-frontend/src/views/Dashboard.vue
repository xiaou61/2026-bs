<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">数据源<strong>{{ data.sourceCount || 0 }}</strong></div>
      <div class="metric">数据集<strong>{{ data.datasetCount || 0 }}</strong></div>
      <div class="metric">识别结果<strong>{{ data.resultCount || 0 }}</strong></div>
      <div class="metric">风险告警<strong>{{ data.alertCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日识别任务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.scanTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '敏感类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.riskPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
