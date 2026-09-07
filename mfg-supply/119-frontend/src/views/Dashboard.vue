<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">设备资产<strong>{{ data.assetCount || 0 }}</strong></div>
      <div class="metric">备件库存<strong>{{ data.stockCount || 0 }}</strong></div>
      <div class="metric">寿命预测<strong>{{ data.predictionCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日备件风险趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.riskTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '备件类型分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.partPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
