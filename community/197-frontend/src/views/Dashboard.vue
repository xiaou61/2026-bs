<template>
  <div class="dashboard">
    <div class="welcome-section">
      <h2>🧹 家政服务数据看板</h2>
      <p>面向社区服务站的服务预约、人员排班、上门记录与信用评价一体化管理平台</p>
    </div>

    <div class="metric-grid">
      <div class="metric-card" v-for="item in metricCards" :key="item.key">
        <div class="metric-icon">{{ item.icon }}</div>
        <div class="metric-info">
          <span class="metric-label">{{ item.label }}</span>
          <span class="metric-value">{{ data[item.key] || 0 }}</span>
        </div>
      </div>
    </div>

    <div class="chart-grid">
      <el-card class="chart-card">
        <template #header><span>📊 近7日服务预约与信用评价趋势</span></template>
        <div ref="trendRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <template #header><span>🧹 家政服务状态分布</span></template>
        <div ref="pieRef" class="chart"></div>
      </el-card>
    </div>

    <div class="chart-grid">
      <el-card class="chart-card">
        <template #header><span>📈 业务分类统计</span></template>
        <div ref="categoryRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card summary-card">
        <template #header><span>🏆 平台概览</span></template>
        <div class="summary-content">
          <div class="summary-item"><span>总记录数</span><strong>{{ data.totalCount || 0 }}</strong></div>
          <div class="summary-item"><span>服务预约</span><strong>{{ data.bookingCount || 0 }}</strong></div>
          <div class="summary-item"><span>人员排班</span><strong>{{ data.dispatchCount || 0 }}</strong></div>
          <div class="summary-item"><span>上门记录</span><strong>{{ data.recordCount || 0 }}</strong></div>
          <div class="summary-item"><span>信用评价</span><strong>{{ data.evaluationCount || 0 }}</strong></div>
        </div>
      </el-card>
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
const categoryRef = ref()
const metricCards = [
  { key: 'stationCount', label: '服务站点', icon: '🏠' },
  { key: 'residentCount', label: '居民档案', icon: '👨‍👩‍👧' },
  { key: 'workerCount', label: '人员档案', icon: '👷' },
  { key: 'serviceCount', label: '服务项目', icon: '🧼' },
  { key: 'bookingCount', label: '服务预约', icon: '📅' },
  { key: 'dispatchCount', label: '人员排班', icon: '📋' },
  { key: 'recordCount', label: '上门记录', icon: '🚪' },
  { key: 'evaluationCount', label: '信用评价', icon: '⭐' }
]
const homeColors = ['#E65100', '#F57C00', '#FF9800', '#FFB74D', '#FFCC80', '#FFE0B2', '#BF360C', '#EF6C00']
const draw = () => {
  const trendData = data.trend || []
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['服务预约', '信用评价'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: trendData.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '服务预约', type: 'line', smooth: true, areaStyle: { color: 'rgba(230,81,0,0.2)' }, lineStyle: { color: '#E65100' }, itemStyle: { color: '#E65100' }, data: trendData.map(i => i.bookings) },
      { name: '信用评价', type: 'line', smooth: true, areaStyle: { color: 'rgba(255,152,0,0.2)' }, lineStyle: { color: '#FF9800' }, itemStyle: { color: '#FF9800' }, data: trendData.map(i => i.evaluations) }
    ]
  })
  echarts.init(pieRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '服务状态', type: 'pie', radius: ['40%', '65%'], avoidLabelOverlap: false, itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 }, label: { show: false, position: 'center' }, emphasis: { label: { show: true, fontSize: 18, fontWeight: 'bold' } }, labelLine: { show: false }, data: data.pie || [], color: homeColors }]
  })
  const categoryData = data.categoryPie || []
  echarts.init(categoryRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务分类', type: 'pie', radius: '62%', data: categoryData, color: homeColors, emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } } }]
  })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
<style scoped>
.dashboard { padding: 0; }
.welcome-section {
  background: linear-gradient(135deg, #E65100 0%, #F57C00 50%, #FF9800 100%);
  border-radius: 16px;
  padding: 28px 32px;
  margin-bottom: 24px;
  color: #fff;
}
.welcome-section h2 { margin: 0 0 8px 0; font-size: 24px; letter-spacing: 2px; }
.welcome-section p { margin: 0; opacity: 0.9; font-size: 14px; }
.metric-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  margin-bottom: 24px;
}
.metric-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 12px rgba(230, 81, 0, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #FFF3E0;
}
.metric-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(230, 81, 0, 0.15);
}
.metric-icon { font-size: 36px; }
.metric-info { display: flex; flex-direction: column; }
.metric-label { color: '#F57C00'; font-size: 13px; margin-bottom: 4px; }
.metric-value { color: #E65100; font-size: 28px; font-weight: 800; }
.chart-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}
.chart-card { border-radius: 12px; }
.chart-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #FFF3E0 0%, #FFFDE7 100%);
  border-bottom: 2px solid #FFE0B2;
  font-weight: 600;
  color: #E65100;
}
.chart { height: 340px; }
.summary-card :deep(.el-card__body) { padding: 0; }
.summary-content { padding: 20px; }
.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #FFF3E0;
  transition: background 0.2s;
}
.summary-item:last-child { border-bottom: none; }
.summary-item:hover { background: #FFFDE7; }
.summary-item span { color: '#F57C00'; font-size: 14px; }
.summary-item strong { color: #E65100; font-size: 20px; }
@media (max-width: 1200px) {
  .metric-grid { grid-template-columns: repeat(2, 1fr); }
  .chart-grid { grid-template-columns: 1fr; }
}
</style>
