<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">服务项目<strong>{{ data.projectCount || 0 }}</strong></div>
      <div class="metric">服务预约<strong>{{ data.bookingCount || 0 }}</strong></div>
      <div class="metric">时长账户<strong>{{ data.accountCount || 0 }}</strong></div>
      <div class="metric">站内通知<strong>{{ data.noticeCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日志愿服务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.serviceTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '服务状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.servicePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>








