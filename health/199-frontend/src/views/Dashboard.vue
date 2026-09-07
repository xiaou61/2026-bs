<template>
  <div class="dashboard">
    <div class="welcome-section">
      <h2>🏃 运动康复数据看板</h2>
      <p>面向运动康复中心的体测评估、风险提醒、训练计划、训练打卡和康复反馈一体化管理平台</p>
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
        <template #header><span>📊 近7日体测评估与训练反馈趋势</span></template>
        <div ref="trendRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <template #header><span>🏋️ 运动康复状态分布</span></template>
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
          <div class="summary-item"><span>体测评估</span><strong>{{ data.assessmentCount || 0 }}</strong></div>
          <div class="summary-item"><span>训练计划</span><strong>{{ data.planCount || 0 }}</strong></div>
          <div class="summary-item"><span>训练打卡</span><strong>{{ data.checkinCount || 0 }}</strong></div>
          <div class="summary-item"><span>康复反馈</span><strong>{{ data.feedbackCount || 0 }}</strong></div>
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
  { key: 'centerCount', label: '康复中心', icon: '🏥' },
  { key: 'memberCount', label: '会员档案', icon: '👤' },
  { key: 'coachCount', label: '教练档案', icon: '🏋️' },
  { key: 'itemCount', label: '体测项目', icon: '📋' },
  { key: 'assessmentCount', label: '体测评估', icon: '📊' },
  { key: 'planCount', label: '训练计划', icon: '📝' },
  { key: 'checkinCount', label: '训练打卡', icon: '✅' },
  { key: 'feedbackCount', label: '康复反馈', icon: '💬' }
]
const healthColors = ['#2E7D32', '#43A047', '#66BB6A', '#81C784', '#A5D6A7', '#C8E6C9', '#388E3C', '#1B5E20']
const draw = () => {
  const trendData = data.trend || []
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['体测评估', '训练反馈'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: trendData.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '体测评估', type: 'line', smooth: true, areaStyle: { color: 'rgba(46,125,50,0.2)' }, lineStyle: { color: '#2E7D32' }, itemStyle: { color: '#2E7D32' }, data: trendData.map(i => i.assessments) },
      { name: '训练反馈', type: 'line', smooth: true, areaStyle: { color: 'rgba(102,187,106,0.2)' }, lineStyle: { color: '#66BB6A' }, itemStyle: { color: '#66BB6A' }, data: trendData.map(i => i.feedbacks) }
    ]
  })
  echarts.init(pieRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务状态', type: 'pie', radius: ['40%', '65%'], avoidLabelOverlap: false, itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 }, label: { show: false, position: 'center' }, emphasis: { label: { show: true, fontSize: 18, fontWeight: 'bold' } }, labelLine: { show: false }, data: data.pie || [], color: healthColors }]
  })
  const categoryData = data.categoryPie || []
  echarts.init(categoryRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务分类', type: 'pie', radius: '62%', data: categoryData, color: healthColors, emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } } }]
  })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
<style scoped>
.dashboard { padding: 0; }
.welcome-section {
  background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 50%, #43A047 100%);
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
  box-shadow: 0 2px 12px rgba(46, 125, 50, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #E8F5E9;
}
.metric-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(46, 125, 50, 0.15);
}
.metric-icon { font-size: 36px; }
.metric-info { display: flex; flex-direction: column; }
.metric-label { color: #4CAF50; font-size: 13px; margin-bottom: 4px; }
.metric-value { color: #1B5E20; font-size: 28px; font-weight: 800; }
.chart-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}
.chart-card { border-radius: 12px; }
.chart-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #E8F5E9 0%, #F1F8E9 100%);
  border-bottom: 2px solid #C8E6C9;
  font-weight: 600;
  color: #2E7D32;
}
.chart { height: 340px; }
.summary-card :deep(.el-card__body) { padding: 0; }
.summary-content { padding: 20px; }
.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #E8F5E9;
  transition: background 0.2s;
}
.summary-item:last-child { border-bottom: none; }
.summary-item:hover { background: #F1F8E9; }
.summary-item span { color: #4CAF50; font-size: 14px; }
.summary-item strong { color: #1B5E20; font-size: 20px; }
@media (max-width: 1200px) {
  .metric-grid { grid-template-columns: repeat(2, 1fr); }
  .chart-grid { grid-template-columns: 1fr; }
}
</style>
