<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">投放点位<strong>{{ data['siteCount'] || 0 }}</strong></div>
      <div class="metric">设备柜档案<strong>{{ data['cabinetCount'] || 0 }}</strong></div>
      <div class="metric">充电宝档案<strong>{{ data['deviceCount'] || 0 }}</strong></div>
      <div class="metric">点位投放<strong>{{ data['planCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日投放巡检与收益结算趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '充电宝运营状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
