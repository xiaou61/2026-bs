<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>首页看板</h2>
        <p>汇总图片作品、审核任务、版权登记、电子存证、待处理申诉和侵权线索。</p>
      </div>
      <el-button type="primary" :icon="Refresh" @click="loadData">刷新</el-button>
    </div>
    <div class="stat-grid">
      <div v-for="item in stats" :key="item.label" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
      </div>
    </div>
    <div class="dashboard-grid">
      <div class="table-panel">
        <h3>审核与版权业务分布</h3>
        <div ref="chartRef" class="chart"></div>
      </div>
      <div class="table-panel">
        <h3>风险与待办</h3>
        <el-descriptions border :column="1">
          <el-descriptions-item label="已通过图片">{{ dashboard.publishedAssets || 0 }}</el-descriptions-item>
          <el-descriptions-item label="已通过登记">{{ dashboard.approvedRegisters || 0 }}</el-descriptions-item>
          <el-descriptions-item label="有效存证">{{ dashboard.validEvidence || 0 }}</el-descriptions-item>
          <el-descriptions-item label="高风险结果">{{ dashboard.highRisk || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待处理线索">{{ dashboard.pendingClue || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待处理申诉">{{ dashboard.pendingAppeal || 0 }}</el-descriptions-item>
          <el-descriptions-item label="平均审核分">{{ dashboard.averageScore || 0 }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { Refresh } from '@element-plus/icons-vue'
import { getDashboard } from '../api'

const chartRef = ref()
const dashboard = ref({})
let chart

const stats = computed(() => [
  { label: '图片作品', value: dashboard.value.assetCount || 0 },
  { label: '审核任务', value: dashboard.value.taskCount || 0 },
  { label: '版权登记', value: dashboard.value.registerCount || 0 },
  { label: '电子存证', value: dashboard.value.evidenceCount || 0 }
])

const drawChart = () => {
  if (!chartRef.value) return
  chart = chart || echarts.init(chartRef.value)
  chart.setOption({
    color: ['#2563eb', '#16a34a', '#f97316', '#dc2626'],
    tooltip: {},
    xAxis: { type: 'category', data: ['作品', '任务', '登记', '存证', '线索', '申诉', '高风险'] },
    yAxis: { type: 'value' },
    series: [
      {
        type: 'bar',
        data: [
          dashboard.value.assetCount || 0,
          dashboard.value.taskCount || 0,
          dashboard.value.registerCount || 0,
          dashboard.value.evidenceCount || 0,
          dashboard.value.pendingClue || 0,
          dashboard.value.pendingAppeal || 0,
          dashboard.value.highRisk || 0
        ]
      }
    ]
  })
}

const loadData = async () => {
  const res = await getDashboard()
  dashboard.value = res.data || {}
  await nextTick()
  drawChart()
}

onMounted(loadData)
</script>

<style scoped>
.dashboard-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(320px, 0.6fr);
  gap: 14px;
}

.table-panel h3 {
  margin: 0 0 14px;
  font-size: 18px;
}

.chart {
  height: 360px;
}

@media (max-width: 980px) {
  .dashboard-grid {
    grid-template-columns: 1fr;
  }
}
</style>
