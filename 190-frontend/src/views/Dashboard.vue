<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">楼宇档案<strong>{{ data['buildingCount'] || 0 }}</strong></div>
      <div class="metric">设备档案<strong>{{ data['equipmentCount'] || 0 }}</strong></div>
      <div class="metric">入驻档案<strong>{{ data['tenantCount'] || 0 }}</strong></div>
      <div class="metric">访修工单<strong>{{ data['ticketCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日访修工单与保养任务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '楼宇访修保养状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
