<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>首页看板</h2>
        <p>汇总 Prompt 资产、评测任务、待处理反馈和整体效果指标。</p>
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
        <h3>评测效果概览</h3>
        <div ref="chartRef" class="chart"></div>
      </div>
      <div class="table-panel">
        <h3>业务闭环状态</h3>
        <el-descriptions border :column="1">
          <el-descriptions-item label="已发布资产">{{ dashboard.publishedAssets || 0 }}</el-descriptions-item>
          <el-descriptions-item label="已发布版本">{{ dashboard.publishedVersions || 0 }}</el-descriptions-item>
          <el-descriptions-item label="已完成任务">{{ dashboard.finishedTasks || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待处理反馈">{{ dashboard.feedbackPending || 0 }}</el-descriptions-item>
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
  { label: 'Prompt 资产', value: dashboard.value.assetCount || 0 },
  { label: '版本数量', value: dashboard.value.versionCount || 0 },
  { label: '评测任务', value: dashboard.value.taskCount || 0 },
  { label: '平均得分', value: dashboard.value.averageScore || 0 }
])

const drawChart = () => {
  if (!chartRef.value) return
  chart = chart || echarts.init(chartRef.value)
  chart.setOption({
    color: ['#0891b2', '#16a34a', '#f59e0b'],
    tooltip: {},
    radar: {
      indicator: [
        { name: '平均分', max: 100 },
        { name: '通过率', max: 100 },
        { name: '发布资产', max: Math.max(10, Number(dashboard.value.assetCount || 0)) },
        { name: '发布版本', max: Math.max(10, Number(dashboard.value.versionCount || 0)) },
        { name: '任务完成', max: Math.max(10, Number(dashboard.value.taskCount || 0)) }
      ]
    },
    series: [
      {
        type: 'radar',
        data: [
          {
            value: [
              Number(dashboard.value.averageScore || 0),
              Number(dashboard.value.passRate || 0),
              Number(dashboard.value.publishedAssets || 0),
              Number(dashboard.value.publishedVersions || 0),
              Number(dashboard.value.finishedTasks || 0)
            ],
            name: 'Prompt 效果'
          }
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
