<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">咨询个案<strong>{{ data.caseCount || 0 }}</strong></div>
      <div class="metric">预约申请<strong>{{ data.appointmentCount || 0 }}</strong></div>
      <div class="metric">测评问卷<strong>{{ data.questionnaireCount || 0 }}</strong></div>
      <div class="metric">通知公告<strong>{{ data.noticeCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日咨询预约趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.counselTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '个案状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.counselPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>










