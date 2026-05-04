<template>
  <div class="page">
    <div class="stat-grid">
      <div class="stat-card"><span>接口项目</span><strong>{{ stats.projectCount || 0 }}</strong></div>
      <div class="stat-card"><span>接口定义</span><strong>{{ stats.endpointCount || 0 }}</strong></div>
      <div class="stat-card"><span>测试用例</span><strong>{{ stats.caseCount || 0 }}</strong></div>
      <div class="stat-card"><span>执行记录</span><strong>{{ stats.executionCount || 0 }}</strong></div>
      <div class="stat-card"><span>文档快照</span><strong>{{ stats.documentCount || 0 }}</strong></div>
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
const stats = reactive({})
const trendRef = ref()
const pieRef = ref()
const render = () => {
  echarts.init(trendRef.value).setOption({ title: { text: '近七次通过率', left: 10, top: 8 }, tooltip: {}, xAxis: { type: 'category', data: ['一', '二', '三', '四', '五', '六', '七'] }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, areaStyle: {}, data: stats.passTrend || [] }] })
  echarts.init(pieRef.value).setOption({ title: { text: '请求方法分布', left: 10, top: 8 }, tooltip: { trigger: 'item' }, series: [{ type: 'pie', radius: ['42%', '70%'], data: stats.methodPie || [] }] })
}
onMounted(async () => {
  const res = await getDashboard()
  Object.assign(stats, res.data)
  await nextTick()
  render()
})
</script>
