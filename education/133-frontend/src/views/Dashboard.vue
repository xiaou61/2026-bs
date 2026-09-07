<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">耗材目录<strong>{{ data.catalogCount || 0 }}</strong></div>
      <div class="metric">库存台账<strong>{{ data.stockCount || 0 }}</strong></div>
      <div class="metric">采购申请<strong>{{ data.requestCount || 0 }}</strong></div>
      <div class="metric">库存预警<strong>{{ data.warningCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日采购申请趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.purchaseTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '库存风险分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.stockPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
