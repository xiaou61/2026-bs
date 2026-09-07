<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>首页看板</h2>
        <p>汇总候选人、简历、岗位、解析、匹配和面试排期数据。</p>
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
        <h3>招聘流程数据</h3>
        <div ref="chartRef" class="chart"></div>
      </div>
      <div class="table-panel">
        <h3>关键指标</h3>
        <el-descriptions border :column="1">
          <el-descriptions-item label="开放岗位">{{ dashboard.openJobs || 0 }}</el-descriptions-item>
          <el-descriptions-item label="强推荐">{{ dashboard.strongRecommend || 0 }}</el-descriptions-item>
          <el-descriptions-item label="待面试">{{ dashboard.pendingInterview || 0 }}</el-descriptions-item>
          <el-descriptions-item label="平均解析分">{{ dashboard.averageParseScore || 0 }}</el-descriptions-item>
          <el-descriptions-item label="平均匹配分">{{ dashboard.averageMatchScore || 0 }}</el-descriptions-item>
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
  { label: '候选人', value: dashboard.value.candidateCount || 0 },
  { label: '简历材料', value: dashboard.value.resumeCount || 0 },
  { label: '岗位数', value: dashboard.value.jobCount || 0 },
  { label: '匹配结果', value: dashboard.value.matchResultCount || 0 }
])

const drawChart = () => {
  if (!chartRef.value) return
  chart = chart || echarts.init(chartRef.value)
  chart.setOption({
    color: ['#0891b2', '#2563eb', '#16a34a', '#f97316'],
    tooltip: {},
    xAxis: { type: 'category', data: ['候选人', '简历', '岗位', '解析', '匹配', '强推荐', '待面试'] },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: [dashboard.value.candidateCount || 0, dashboard.value.resumeCount || 0, dashboard.value.jobCount || 0, dashboard.value.parseResultCount || 0, dashboard.value.matchResultCount || 0, dashboard.value.strongRecommend || 0, dashboard.value.pendingInterview || 0] }]
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
