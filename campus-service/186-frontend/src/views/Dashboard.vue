<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">餐厅档案<strong>{{ data['canteenCount'] || 0 }}</strong></div>
      <div class="metric">后厨区域<strong>{{ data['areaCount'] || 0 }}</strong></div>
      <div class="metric">菜品档案<strong>{{ data['dishCount'] || 0 }}</strong></div>
      <div class="metric">留样登记<strong>{{ data['sampleCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日留样登记与卫生巡检趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '后厨卫生台账状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
