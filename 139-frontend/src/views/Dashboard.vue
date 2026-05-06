<template>
  <div class="page">
    <div class="dashboard-grid">
      <div class="metric">培训项目<strong>{{ data.programCount || 0 }}</strong></div>
      <div class="metric">学习路径<strong>{{ data.pathCount || 0 }}</strong></div>
      <div class="metric">考核考试<strong>{{ data.examCount || 0 }}</strong></div>
      <div class="metric">认证记录<strong>{{ data.certCount || 0 }}</strong></div>
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
  echarts.init(trendRef.value).setOption({ title: { text: '近7日学习任务趋势' }, tooltip: {}, xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: data.taskTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '能力等级分布' }, tooltip: {}, series: [{ type: 'pie', radius: '62%', data: data.abilityPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(data, res.data)
  nextTick(draw)
})
</script>






