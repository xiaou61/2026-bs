<template>
  <div>
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="用户总数" :value="stats.userCount || 0" /></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="电影总数" :value="stats.movieCount || 0" /></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="订单总数" :value="stats.orderCount || 0" /></el-card></el-col>
      <el-col :span="6"><el-card shadow="hover"><el-statistic title="总收入(元)" :value="stats.totalIncome || 0" :precision="2" /></el-card></el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8"><el-card><div ref="pieChart" style="height:320px"></div></el-card></el-col>
      <el-col :span="8"><el-card><div ref="lineChart" style="height:320px"></div></el-card></el-col>
      <el-col :span="8"><el-card><div ref="barChart" style="height:320px"></div></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getCategoryStats, getOrderTrend, getBoxOfficeRank } from '../api'

const stats = ref({})
const pieChart = ref()
const lineChart = ref()
const barChart = ref()

onMounted(async () => {
  const s = await getDashboardStats()
  stats.value = s.data

  const catRes = await getCategoryStats()
  const pie = echarts.init(pieChart.value)
  pie.setOption({
    title: { text: '电影分类统计', left: 'center' },
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: '60%', data: catRes.data }]
  })

  const trendRes = await getOrderTrend()
  const line = echarts.init(lineChart.value)
  line.setOption({
    title: { text: '近7日订单趋势', left: 'center' },
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: trendRes.data.map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', data: trendRes.data.map(i => i.count), smooth: true, areaStyle: {} }]
  })

  const boxRes = await getBoxOfficeRank()
  const bar = echarts.init(barChart.value)
  bar.setOption({
    title: { text: '票房排行', left: 'center' },
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: boxRes.data.map(i => i.name), axisLabel: { rotate: 30 } },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: boxRes.data.map(i => i.value), itemStyle: { color: '#409eff' } }]
  })
})
</script>
