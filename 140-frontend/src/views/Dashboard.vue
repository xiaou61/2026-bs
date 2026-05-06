<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">合同模板<strong>{{ data.templateCount || 0 }}</strong></div>
      <div class="metric">用印申请<strong>{{ data.sealCount || 0 }}</strong></div>
      <div class="metric">合同签署<strong>{{ data.signCount || 0 }}</strong></div>
      <div class="metric">到期提醒<strong>{{ data.reminderCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日合同流转趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.contractTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '合同状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.contractPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>






