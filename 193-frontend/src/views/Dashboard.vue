<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">实验班项目<strong>{{ data['programCount'] || 0 }}</strong></div>
      <div class="metric">学生档案<strong>{{ data['studentCount'] || 0 }}</strong></div>
      <div class="metric">导师档案<strong>{{ data['mentorCount'] || 0 }}</strong></div>
      <div class="metric">选拔公告<strong>{{ data['noticeCount'] || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日报名选拔与过程跟踪趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一','周二','周三','周四','周五','周六','周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: data.trend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '实验班培养状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.pie || [] }] })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
