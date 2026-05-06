<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">孵化项目<strong>{{ data.projectCount || 0 }}</strong></div>
      <div class="metric">项目申报<strong>{{ data.applicationCount || 0 }}</strong></div>
      <div class="metric">路演活动<strong>{{ data.roadshowCount || 0 }}</strong></div>
      <div class="metric">经费记录<strong>{{ data.fundingCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日项目申报趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.applicationTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '项目阶段分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.stagePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>




