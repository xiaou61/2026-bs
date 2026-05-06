<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">课题发布<strong>{{ data.topicCount || 0 }}</strong></div>
      <div class="metric">课题申请<strong>{{ data.applicationCount || 0 }}</strong></div>
      <div class="metric">开题材料<strong>{{ data.proposalCount || 0 }}</strong></div>
      <div class="metric">节点通知<strong>{{ data.noticeCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日双选申请趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.selectionTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '过程节点分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.stagePie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>


