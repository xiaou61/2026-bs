<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">养殖池塘<strong>{{ data.pondCount || 0 }}</strong></div>
      <div class="metric">水质读数<strong>{{ data.readingCount || 0 }}</strong></div>
      <div class="metric">投喂记录<strong>{{ data.feedingCount || 0 }}</strong></div>
      <div class="metric">病害预警<strong>{{ data.warningCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日水质监测趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.waterTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '养殖品类分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pondPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
