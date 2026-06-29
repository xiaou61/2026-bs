<template>
  <div class="dashboard">
    <div class="welcome-section">
      <h2>🔋 充电宝运营数据看板</h2>
      <p>面向城市共享充电宝运营企业的点位投放、设备巡检、异常回收与收益结算一体化管理平台</p>
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
        <template #header><span>📊 近7日租借订单与故障维修趋势</span></template>
        <div ref="trendRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <template #header><span>🔋 充电宝运营状态分布</span></template>
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
          <div class="summary-item"><span>设备巡检</span><strong>{{ data.inspectionCount || 0 }}</strong></div>
          <div class="summary-item"><span>故障维修</span><strong>{{ data.repairCount || 0 }}</strong></div>
          <div class="summary-item"><span>租借订单</span><strong>{{ data.orderCount || 0 }}</strong></div>
          <div class="summary-item"><span>收益结算</span><strong>{{ data.settlementCount || 0 }}</strong></div>
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
  { key: 'siteCount', label: '投放点位', icon: '📍' },
  { key: 'cabinetCount', label: '设备柜档案', icon: '🗄️' },
  { key: 'deviceCount', label: '充电宝档案', icon: '🔋' },
  { key: 'planCount', label: '点位投放', icon: '🚀' },
  { key: 'inspectionCount', label: '设备巡检', icon: '🔍' },
  { key: 'repairCount', label: '故障维修', icon: '🔧' },
  { key: 'orderCount', label: '租借订单', icon: '📱' },
  { key: 'settlementCount', label: '收益结算', icon: '💰' }
]
const powerColors = ['#0277BD', '#0288D1', '#29B6F6', '#4FC3F7', '#81D4FA', '#B3E5FC', '#01579B', '#00BCD4']
const draw = () => {
  const trendData = data.trend || []
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['租借订单', '故障维修'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: trendData.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '租借订单', type: 'line', smooth: true, areaStyle: { color: 'rgba(2,119,189,0.2)' }, lineStyle: { color: '#0277BD' }, itemStyle: { color: '#0277BD' }, data: trendData.map(i => i.orders) },
      { name: '故障维修', type: 'line', smooth: true, areaStyle: { color: 'rgba(41,182,246,0.2)' }, lineStyle: { color: '#29B6F6' }, itemStyle: { color: '#29B6F6' }, data: trendData.map(i => i.repairs) }
    ]
  })
  echarts.init(pieRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '运营状态', type: 'pie', radius: ['40%', '65%'], avoidLabelOverlap: false, itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 }, label: { show: false, position: 'center' }, emphasis: { label: { show: true, fontSize: 18, fontWeight: 'bold' } }, labelLine: { show: false }, data: data.pie || [], color: powerColors }]
  })
  const categoryData = data.categoryPie || []
  echarts.init(categoryRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务分类', type: 'pie', radius: '62%', data: categoryData, color: powerColors, emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } } }]
  })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
<style scoped>
.dashboard { padding: 0; }
.welcome-section {
  background: linear-gradient(135deg, #01579B 0%, #0277BD 50%, #0288D1 100%);
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
  box-shadow: 0 2px 12px rgba(2, 119, 189, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #E1F5FE;
}
.metric-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(2, 119, 189, 0.15);
}
.metric-icon { font-size: 36px; }
.metric-info { display: flex; flex-direction: column; }
.metric-label { color: #0288D1; font-size: 13px; margin-bottom: 4px; }
.metric-value { color: #01579B; font-size: 28px; font-weight: 800; }
.chart-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}
.chart-card { border-radius: 12px; }
.chart-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #E1F5FE 0%, #E0F7FA 100%);
  border-bottom: 2px solid #B3E5FC;
  font-weight: 600;
  color: #01579B;
}
.chart { height: 340px; }
.summary-card :deep(.el-card__body) { padding: 0; }
.summary-content { padding: 20px; }
.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #E1F5FE;
  transition: background 0.2s;
}
.summary-item:last-child { border-bottom: none; }
.summary-item:hover { background: #E0F7FA; }
.summary-item span { color: #0288D1; font-size: 14px; }
.summary-item strong { color: #01579B; font-size: 20px; }
@media (max-width: 1200px) {
  .metric-grid { grid-template-columns: repeat(2, 1fr); }
  .chart-grid { grid-template-columns: 1fr; }
}
</style>
