<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">券模板<strong>{{ data.templateCount || 0 }}</strong></div>
      <div class="metric">营销活动<strong>{{ data.activityCount || 0 }}</strong></div>
      <div class="metric">核销记录<strong>{{ data.verificationCount || 0 }}</strong></div>
      <div class="metric">商户结算<strong>{{ data.settlementCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日核销趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.verifyTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '行业核销分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.industryPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>
