<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">社区区域<strong>{{ data['areaCount'] || 0 }}</strong></div>
      <div class="metric">楼栋档案<strong>{{ data['buildingCount'] || 0 }}</strong></div>
      <div class="metric">居民档案<strong>{{ data['residentCount'] || 0 }}</strong></div>
      <div class="metric">分类规则<strong>{{ data['categoryCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日分类打卡与积分趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '垃圾分类督导状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
