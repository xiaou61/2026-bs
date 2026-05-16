<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">动物房间<strong>{{ data['roomCount'] || 0 }}</strong></div>
      <div class="metric">实验动物档案<strong>{{ data['animalCount'] || 0 }}</strong></div>
      <div class="metric">饲养排班<strong>{{ data['scheduleCount'] || 0 }}</strong></div>
      <div class="metric">饲养记录<strong>{{ data['feedingCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日饲养排班与伦理审批趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '实验动物业务状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
