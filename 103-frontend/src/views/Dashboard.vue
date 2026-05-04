<template>
  <div class="page-shell">
    <div class="page-title"><div><h2>数据看板</h2><p>展示客户、知识、工单、质检、推荐和绩效指标。</p></div><el-button type="primary" @click="loadData">刷新</el-button></div>
    <div class="metric-grid"><div v-for="item in metrics" :key="item.key" class="metric-card"><span>{{ item.label }}</span><strong>{{ dashboard[item.key] || 0 }}</strong></div></div>
    <div class="chart-panel" ref="chartRef"></div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboard } from '../api'
const chartRef = ref()
const dashboard = reactive({})
const metrics = [{ key: 'customerCount', label: '客户' }, { key: 'articleCount', label: '知识' }, { key: 'orderCount', label: '工单' }, { key: 'taskCount', label: '质检任务' }, { key: 'resultCount', label: '质检结果' }, { key: 'recommendCount', label: '推荐' }, { key: 'performanceCount', label: '绩效' }, { key: 'todoCount', label: '待办' }]
const draw = () => {
  const chart = echarts.init(chartRef.value)
  chart.setOption({ tooltip: {}, grid: { left: 40, right: 20, bottom: 30, top: 30 }, xAxis: { type: 'category', data: metrics.slice(0, 7).map((i) => i.label) }, yAxis: { type: 'value' }, series: [{ type: 'bar', data: metrics.slice(0, 7).map((i) => dashboard[i.key] || 0), itemStyle: { color: '#25636f' } }] })
}
const loadData = async () => {
  const res = await getDashboard()
  Object.assign(dashboard, res.data)
  await nextTick()
  draw()
}
onMounted(loadData)
</script>
