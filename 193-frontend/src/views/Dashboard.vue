<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">报名选拔<strong>{{ data.record01Count || 0 }}</strong></div>
      <div class="metric">导师匹配<strong>{{ data.record02Count || 0 }}</strong></div>
      <div class="metric">培养计划<strong>{{ data.record03Count || 0 }}</strong></div>
      <div class="metric">过程跟踪<strong>{{ data.record04Count || 0 }}</strong></div>
    </div>
    <div class="chart-grid"><div ref="trendRef" class="chart"></div><div ref="pieRef" class="chart"></div></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日业务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '业务状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
