<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">产品批次<strong>{{ data.batchCount || 0 }}</strong></div>
      <div class="metric">质检报告<strong>{{ data.inspectionCount || 0 }}</strong></div>
      <div class="metric">区块存证<strong>{{ data.blockCount || 0 }}</strong></div>
      <div class="metric">流通节点<strong>{{ data.nodeCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日上链趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.chainTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '产品风险分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.riskPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
