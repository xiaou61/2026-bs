<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>数据看板</h2>
        <p>展示案件、进度、咨询、文书、预约、证据和费用的关键指标。</p>
      </div>
      <el-button type="primary" @click="loadData">刷新</el-button>
    </div>
    <div class="metric-grid">
      <div v-for="item in metrics" :key="item.key" class="metric-card">
        <span>{{ item.label }}</span>
        <strong>{{ dashboard[item.key] || 0 }}</strong>
      </div>
    </div>
    <div class="chart-panel" ref="chartRef"></div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboard } from '../api'

const chartRef = ref()
const dashboard = reactive({})
const metrics = [
  { key: 'caseCount', label: '案件总数' },
  { key: 'stageCount', label: '进度节点' },
  { key: 'consultationCount', label: '咨询记录' },
  { key: 'documentCount', label: '法律文书' },
  { key: 'appointmentCount', label: '咨询预约' },
  { key: 'evidenceCount', label: '证据材料' },
  { key: 'feeCount', label: '费用记录' },
  { key: 'todoCount', label: '待办总量' }
]

const draw = () => {
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    tooltip: {},
    grid: { left: 40, right: 20, bottom: 30, top: 30 },
    xAxis: { type: 'category', data: metrics.slice(0, 7).map((item) => item.label) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: metrics.slice(0, 7).map((item) => dashboard[item.key] || 0), itemStyle: { color: '#2f6f73' } }]
  })
}

const loadData = async () => {
  const res = await getDashboard()
  Object.assign(dashboard, res.data)
  await nextTick()
  draw()
}

onMounted(loadData)
</script>
