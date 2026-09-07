<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">保险保单<strong>{{ data.policyCount || 0 }}</strong></div>
      <div class="metric">事故报案<strong>{{ data.reportCount || 0 }}</strong></div>
      <div class="metric">材料审核<strong>{{ data.reviewCount || 0 }}</strong></div>
      <div class="metric">进度跟踪<strong>{{ data.progressCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日理赔进度趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.claimTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '理赔状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.claimPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>







