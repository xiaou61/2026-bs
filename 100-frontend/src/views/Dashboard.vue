<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>首页看板</h2>
        <p>汇总课程作业、文本提交、检测任务、风险结果、待处理预警和整改申诉。</p>
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
        <h3>学术诚信检测分布</h3>
        <div ref="chartRef" class="chart"></div>
      </div>
      <div class="table-panel">
        <h3>风险与待办</h3>
        <el-descriptions border :column="1">
          <el-descriptions-item label="检测结果">{{ dashboard.resultCount || 0 }}</el-descriptions-item>
          <el-descriptions-item label="高风险结果">{{ dashboard.highRiskCount || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待处理预警">{{ dashboard.pendingWarning || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待审核整改">{{ dashboard.pendingRectification || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待处理申诉">{{ dashboard.pendingAppeal || 0 }}</el-descriptions-item>
          <el-descriptions-item label="平均诚信分">{{ dashboard.averageScore || 0 }}</el-descriptions-item>
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
  { label: '课程数', value: dashboard.value.courseCount || 0 },
  { label: '作业任务', value: dashboard.value.assignmentCount || 0 },
  { label: '文本提交', value: dashboard.value.submissionCount || 0 },
  { label: '检测任务', value: dashboard.value.taskCount || 0 }
])

const drawChart = () => {
  if (!chartRef.value) return
  chart = chart || echarts.init(chartRef.value)
  chart.setOption({
    color: ['#059669', '#2563eb', '#f97316', '#dc2626'],
    tooltip: {},
    xAxis: { type: 'category', data: ['课程', '作业', '提交', '检测', '结果', '高风险', '预警'] },
    yAxis: { type: 'value' },
    series: [
      {
        type: 'bar',
        data: [
          dashboard.value.courseCount || 0,
          dashboard.value.assignmentCount || 0,
          dashboard.value.submissionCount || 0,
          dashboard.value.taskCount || 0,
          dashboard.value.resultCount || 0,
          dashboard.value.highRiskCount || 0,
          dashboard.value.pendingWarning || 0
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
