<template>
  <div class="dashboard">
    <div class="welcome-section">
      <h2>💊 药店慢病管理数据看板</h2>
      <p>面向连锁药店的处方审核、购药记录、续方提醒与风险复核一体化管理平台</p>
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
        <template #header><span>📊 近7日处方审核与随访记录趋势</span></template>
        <div ref="trendRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <template #header><span>💊 处方慢病服务状态分布</span></template>
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
          <div class="summary-item"><span>处方登记</span><strong>{{ data.prescriptionCount || 0 }}</strong></div>
          <div class="summary-item"><span>购药记录</span><strong>{{ data.purchaseCount || 0 }}</strong></div>
          <div class="summary-item"><span>续方提醒</span><strong>{{ data.reminderCount || 0 }}</strong></div>
          <div class="summary-item"><span>随访记录</span><strong>{{ data.followupCount || 0 }}</strong></div>
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
  { key: 'storeCount', label: '药店门店', icon: '🏥' },
  { key: 'customerCount', label: '顾客档案', icon: '👤' },
  { key: 'medicineCount', label: '药品目录', icon: '💊' },
  { key: 'prescriptionCount', label: '处方登记', icon: '📋' },
  { key: 'purchaseCount', label: '购药记录', icon: '🛒' },
  { key: 'guideCount', label: '用药指导', icon: '📖' },
  { key: 'reminderCount', label: '续方提醒', icon: '⏰' },
  { key: 'followupCount', label: '随访记录', icon: '📞' }
]
const pharmacyColors = ['#0077B6', '#00B4D8', '#48CAE4', '#90E0EF', '#023E8A', '#0096C7', '#CAF0F8', '#03045E']
const draw = () => {
  const trendData = data.trend || []
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['处方审核', '随访记录'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: trendData.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '处方审核', type: 'line', smooth: true, areaStyle: { color: 'rgba(0,119,182,0.2)' }, lineStyle: { color: '#0077B6' }, itemStyle: { color: '#0077B6' }, data: trendData.map(i => i.prescriptions) },
      { name: '随访记录', type: 'line', smooth: true, areaStyle: { color: 'rgba(72,202,228,0.2)' }, lineStyle: { color: '#48CAE4' }, itemStyle: { color: '#48CAE4' }, data: trendData.map(i => i.followups) }
    ]
  })
  echarts.init(pieRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '服务状态', type: 'pie', radius: ['40%', '65%'], avoidLabelOverlap: false, itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 }, label: { show: false, position: 'center' }, emphasis: { label: { show: true, fontSize: 18, fontWeight: 'bold' } }, labelLine: { show: false }, data: data.pie || [], color: pharmacyColors }]
  })
  const categoryData = data.categoryPie || []
  echarts.init(categoryRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务分类', type: 'pie', radius: '62%', data: categoryData, color: pharmacyColors, emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } } }]
  })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
<style scoped>
.dashboard { padding: 0; }
.welcome-section {
  background: linear-gradient(135deg, #0077B6 0%, #00B4D8 50%, #48CAE4 100%);
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
  box-shadow: 0 2px 12px rgba(0, 119, 182, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #E0F7FA;
}
.metric-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 119, 182, 0.15);
}
.metric-icon { font-size: 36px; }
.metric-info { display: flex; flex-direction: column; }
.metric-label { color: #00B4D8; font-size: 13px; margin-bottom: 4px; }
.metric-value { color: #0077B6; font-size: 28px; font-weight: 800; }
.chart-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}
.chart-card { border-radius: 12px; }
.chart-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #E0F7FA 0%, #E1F5FE 100%);
  border-bottom: 2px solid #B3E5FC;
  font-weight: 600;
  color: #0077B6;
}
.chart { height: 340px; }
.summary-card :deep(.el-card__body) { padding: 0; }
.summary-content { padding: 20px; }
.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #E0F7FA;
  transition: background 0.2s;
}
.summary-item:last-child { border-bottom: none; }
.summary-item:hover { background: #E1F5FE; }
.summary-item span { color: #00B4D8; font-size: 14px; }
.summary-item strong { color: #0077B6; font-size: 20px; }
@media (max-width: 1200px) {
  .metric-grid { grid-template-columns: repeat(2, 1fr); }
  .chart-grid { grid-template-columns: 1fr; }
}
</style>
