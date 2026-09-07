<template>
  <div>
    <el-row :gutter="15" style="margin-bottom:15px">
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="用户总数" :value="overview.userCount || 0"><template #prefix><el-icon style="color:#409EFF"><User /></el-icon></template></el-statistic></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="单车总数" :value="overview.bikeCount || 0"><template #prefix><el-icon style="color:#67C23A"><Van /></el-icon></template></el-statistic></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="今日订单" :value="overview.todayOrders || 0"><template #prefix><el-icon style="color:#E6A23C"><Tickets /></el-icon></template></el-statistic></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="今日收入(元)" :value="overview.todayIncome || 0" :precision="2"><template #prefix><el-icon style="color:#F56C6C"><Money /></el-icon></template></el-statistic></el-card></el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="12"><el-card><template #header>骑行趋势</template><div ref="rideTrendRef" style="height:320px"></div></el-card></el-col>
      <el-col :span="12"><el-card><template #header>收入统计</template><div ref="incomeTrendRef" style="height:320px"></div></el-card></el-col>
    </el-row>
    <el-row :gutter="15" style="margin-top:15px">
      <el-col :span="12"><el-card><template #header>站点排行</template><div ref="stationRankRef" style="height:320px"></div></el-card></el-col>
      <el-col :span="12"><el-card><template #header>车型分布</template><div ref="bikeTypeRef" style="height:320px"></div></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getOverview, getRideTrend, getIncomeTrend, getStationRank, getBikeTypeRatio } from '../api'

const overview = ref({})
const rideTrendRef = ref()
const incomeTrendRef = ref()
const stationRankRef = ref()
const bikeTypeRef = ref()

onMounted(async () => {
  const res = await getOverview()
  overview.value = res.data
  await nextTick()
  loadRideTrend()
  loadIncomeTrend()
  loadStationRank()
  loadBikeType()
})

const loadRideTrend = async () => {
  const res = await getRideTrend({ days: 7 })
  const chart = echarts.init(rideTrendRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: res.data.map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ name: '骑行次数', type: 'line', data: res.data.map(i => i.count), smooth: true, areaStyle: { opacity: 0.3 }, itemStyle: { color: '#409EFF' } }]
  })
}

const loadIncomeTrend = async () => {
  const res = await getIncomeTrend({ days: 7 })
  const chart = echarts.init(incomeTrendRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: res.data.map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ name: '收入(元)', type: 'bar', data: res.data.map(i => i.income), itemStyle: { color: '#67C23A' } }]
  })
}

const loadStationRank = async () => {
  const res = await getStationRank()
  const chart = echarts.init(stationRankRef.value)
  const data = res.data.reverse()
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'value' },
    yAxis: { type: 'category', data: data.map(i => i.name) },
    series: [{ type: 'bar', data: data.map(i => i.value), itemStyle: { color: '#E6A23C' } }]
  })
}

const loadBikeType = async () => {
  const res = await getBikeTypeRatio()
  const chart = echarts.init(bikeTypeRef.value)
  const typeMap = { '1': '普通单车', '2': '电动单车' }
  chart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [{ type: 'pie', radius: ['40%', '65%'], data: res.data.map(i => ({ name: typeMap[i.name] || i.name, value: i.value })), emphasis: { itemStyle: { shadowBlur: 10 } } }]
  })
}
</script>
