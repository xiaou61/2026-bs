<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">学院专业<strong>{{ data['majorCount'] || 0 }}</strong></div>
      <div class="metric">毕业生档案<strong>{{ data['graduateCount'] || 0 }}</strong></div>
      <div class="metric">用人单位<strong>{{ data['employerCount'] || 0 }}</strong></div>
      <div class="metric">招聘岗位<strong>{{ data['jobCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日去向填报与就业帮扶趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '毕业去向状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
