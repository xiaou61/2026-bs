<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">设备档案<strong>{{ data.assetCount || 0 }}</strong></div>
      <div class="metric">实验室档案<strong>{{ data.laboratoryCount || 0 }}</strong></div>
      <div class="metric">预约申请<strong>{{ data.reservationCount || 0 }}</strong></div>
      <div class="metric">违规记录<strong>{{ data.violationCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近 7 日预约申请趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.reservationTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '设备当前状态分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.equipmentStatusPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>











