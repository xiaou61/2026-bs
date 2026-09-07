<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">公厕点位<strong>{{ data['siteCount'] || 0 }}</strong></div>
      <div class="metric">保洁路线<strong>{{ data['routeCount'] || 0 }}</strong></div>
      <div class="metric">保洁任务<strong>{{ data['taskCount'] || 0 }}</strong></div>
      <div class="metric">保洁记录<strong>{{ data['recordCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日保洁巡检与耗材补给趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '公厕运维业务状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
