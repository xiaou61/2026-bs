<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>首页看板</h2>
        <p>汇总知识空间、文档、分段、问答记录、反馈和平均置信度。</p>
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
        <h3>知识问答质量</h3>
        <div ref="chartRef" class="chart"></div>
      </div>
      <div class="table-panel">
        <h3>知识库状态</h3>
        <el-descriptions border :column="1">
          <el-descriptions-item label="已发布文档">{{ dashboard.publishedDocuments || 0 }}</el-descriptions-item>
          <el-descriptions-item label="已索引分段">{{ dashboard.indexedChunks || 0 }}</el-descriptions-item>
          <el-descriptions-item label="问答会话">{{ dashboard.sessionCount || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待处理反馈">{{ dashboard.pendingFeedback || 0 }}</el-descriptions-item>
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
  { label: '知识空间', value: dashboard.value.spaceCount || 0 },
  { label: '知识文档', value: dashboard.value.documentCount || 0 },
  { label: '文档分段', value: dashboard.value.chunkCount || 0 },
  { label: '平均置信度', value: dashboard.value.averageConfidence || 0 }
])

const drawChart = () => {
  if (!chartRef.value) return
  chart = chart || echarts.init(chartRef.value)
  chart.setOption({
    color: ['#0d9488', '#2563eb'],
    tooltip: {},
    xAxis: { type: 'category', data: ['空间', '文档', '分段', '会话', '问答', '反馈'] },
    yAxis: { type: 'value' },
    series: [
      {
        type: 'bar',
        data: [
          dashboard.value.spaceCount || 0,
          dashboard.value.documentCount || 0,
          dashboard.value.chunkCount || 0,
          dashboard.value.sessionCount || 0,
          dashboard.value.recordCount || 0,
          dashboard.value.pendingFeedback || 0
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
