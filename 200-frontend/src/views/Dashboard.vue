<template>
  <div class="dashboard">
    <div class="welcome-section">
      <h2>🎭 非遗工坊数据看板</h2>
      <p>面向非遗传承场景的工坊课程、预约签到、作品展销、传承人管理和结算统计一体化平台</p>
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
        <template #header><span>📊 近7日课程预约与作品展销趋势</span></template>
        <div ref="trendRef" class="chart"></div>
      </el-card>
      <el-card class="chart-card">
        <template #header><span>🎨 非遗工坊业务状态分布</span></template>
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
          <div class="summary-item"><span>课程预约</span><strong>{{ data.bookingCount || 0 }}</strong></div>
          <div class="summary-item"><span>作品展销</span><strong>{{ data.showcaseCount || 0 }}</strong></div>
          <div class="summary-item"><span>展销订单</span><strong>{{ data.orderCount || 0 }}</strong></div>
          <div class="summary-item"><span>展销结算</span><strong>{{ data.settlementCount || 0 }}</strong></div>
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
  { key: 'workshopCount', label: '工坊档案', icon: '🏛️' },
  { key: 'inheritorCount', label: '传承人档案', icon: '👤' },
  { key: 'courseCount', label: '课程目录', icon: '📚' },
  { key: 'scheduleCount', label: '工坊排期', icon: '📅' },
  { key: 'bookingCount', label: '课程预约', icon: '🎫' },
  { key: 'artworkCount', label: '作品档案', icon: '🎨' },
  { key: 'showcaseCount', label: '作品展销', icon: '🖼️' },
  { key: 'orderCount', label: '展销订单', icon: '🛒' }
]
const heritageColors = ['#8B4513', '#A0522D', '#CD853F', '#DEB887', '#D2691E', '#F4A460', '#E8C39E', '#B8860B']
const draw = () => {
  const trendData = data.trend || []
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['课程预约', '作品展销'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: trendData.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '课程预约', type: 'line', smooth: true, areaStyle: { color: 'rgba(139,69,19,0.2)' }, lineStyle: { color: '#8B4513' }, itemStyle: { color: '#8B4513' }, data: trendData.map(i => i.bookings) },
      { name: '作品展销', type: 'line', smooth: true, areaStyle: { color: 'rgba(205,133,63,0.2)' }, lineStyle: { color: '#CD853F' }, itemStyle: { color: '#CD853F' }, data: trendData.map(i => i.sales) }
    ]
  })
  echarts.init(pieRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务状态', type: 'pie', radius: ['40%', '65%'], avoidLabelOverlap: false, itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 }, label: { show: false, position: 'center' }, emphasis: { label: { show: true, fontSize: 18, fontWeight: 'bold' } }, labelLine: { show: false }, data: data.pie || [], color: heritageColors }]
  })
  const categoryData = data.categoryPie || []
  echarts.init(categoryRef.value).setOption({
    tooltip: { trigger: 'item', formatter: '{a} <br/>{b}: {c} ({d}%)' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{ name: '业务分类', type: 'pie', radius: '62%', data: categoryData, color: heritageColors, emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } } }]
  })
}
onMounted(async () => { const res = await getDashboard(); Object.assign(data, res.data); nextTick(draw) })
</script>
<style scoped>
.dashboard { padding: 0; }
.welcome-section {
  background: linear-gradient(135deg, #8B4513 0%, #A0522D 50%, #CD853F 100%);
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
  box-shadow: 0 2px 12px rgba(139, 69, 19, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #EFEBE9;
}
.metric-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(139, 69, 19, 0.15);
}
.metric-icon { font-size: 36px; }
.metric-info { display: flex; flex-direction: column; }
.metric-label { color: #8D6E63; font-size: 13px; margin-bottom: 4px; }
.metric-value { color: #5D4037; font-size: 28px; font-weight: 800; }
.chart-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}
.chart-card { border-radius: 12px; }
.chart-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #EFEBE9 0%, #FFF8E1 100%);
  border-bottom: 2px solid #D7CCC8;
  font-weight: 600;
  color: #5D4037;
}
.chart { height: 340px; }
.summary-card :deep(.el-card__body) { padding: 0; }
.summary-content { padding: 20px; }
.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #EFEBE9;
  transition: background 0.2s;
}
.summary-item:last-child { border-bottom: none; }
.summary-item:hover { background: #FFF8E1; }
.summary-item span { color: #8D6E63; font-size: 14px; }
.summary-item strong { color: #5D4037; font-size: 20px; }
@media (max-width: 1200px) {
  .metric-grid { grid-template-columns: repeat(2, 1fr); }
  .chart-grid { grid-template-columns: 1fr; }
}
</style>
