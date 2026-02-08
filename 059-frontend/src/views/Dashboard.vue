<template>
  <div class="dashboard">
    <el-row :gutter="15" style="margin-bottom:15px">
      <el-col :span="6" v-for="item in overviewCards" :key="item.label">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-icon" :style="{background:item.color}">
              <el-icon :size="28"><component :is="item.icon" /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-value">{{ item.value }}</div>
              <div class="card-label">{{ item.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="12">
        <el-card header="设备状态分布">
          <div ref="equipmentChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card header="工单状态统计">
          <div ref="orderChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15" style="margin-top:15px">
      <el-col :span="24">
        <el-card header="质量合格率趋势">
          <div ref="qualityChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, markRaw } from 'vue'
import * as echarts from 'echarts'
import { getDashboardEquipmentStatus, getDashboardOrderStats, getDashboardAlertStats, getDashboardQualityTrend, getDashboardOverview } from '../api'

const equipmentChartRef = ref()
const orderChartRef = ref()
const qualityChartRef = ref()
const overviewCards = ref([
  { label: '设备总数', value: 0, icon: 'SetUp', color: '#1a237e' },
  { label: '工单总数', value: 0, icon: 'Box', color: '#2e7d32' },
  { label: '库存预警', value: 0, icon: 'WarningFilled', color: '#f57c00' },
  { label: '未处理告警', value: 0, icon: 'Bell', color: '#c62828' }
])

const loadOverview = async () => {
  const res = await getDashboardOverview()
  overviewCards.value[0].value = res.data.equipmentTotal
  overviewCards.value[1].value = res.data.orderTotal
  overviewCards.value[2].value = res.data.lowStockCount
  overviewCards.value[3].value = res.data.alertUnhandled
}

const loadEquipmentChart = async () => {
  const res = await getDashboardEquipmentStatus()
  const chart = echarts.init(equipmentChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [{
      type: 'pie', radius: ['40%', '70%'],
      data: [
        { value: res.data.running, name: '运行中', itemStyle: { color: '#4caf50' } },
        { value: res.data.stopped, name: '停机', itemStyle: { color: '#9e9e9e' } },
        { value: res.data.repairing, name: '维修中', itemStyle: { color: '#ff9800' } },
        { value: res.data.scrapped, name: '已报废', itemStyle: { color: '#f44336' } }
      ]
    }]
  })
}

const loadOrderChart = async () => {
  const res = await getDashboardOrderStats()
  const chart = echarts.init(orderChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: ['待排产', '生产中', '已完工', '已取消'] },
    yAxis: { type: 'value' },
    series: [{
      type: 'bar', barWidth: '40%',
      data: [
        { value: res.data.pending, itemStyle: { color: '#42a5f5' } },
        { value: res.data.producing, itemStyle: { color: '#66bb6a' } },
        { value: res.data.completed, itemStyle: { color: '#26a69a' } },
        { value: res.data.cancelled, itemStyle: { color: '#ef5350' } }
      ]
    }]
  })
}

const loadQualityChart = async () => {
  const res = await getDashboardQualityTrend()
  const chart = echarts.init(qualityChartRef.value)
  const times = res.data.map(i => i.time?.substring(5, 16) || '')
  const rates = res.data.map(i => i.rate)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: times },
    yAxis: { type: 'value', min: 0, max: 100, axisLabel: { formatter: '{value}%' } },
    series: [{
      type: 'line', smooth: true, data: rates,
      areaStyle: { color: 'rgba(25,118,210,0.15)' },
      itemStyle: { color: '#1976d2' }
    }]
  })
}

onMounted(() => {
  loadOverview()
  loadEquipmentChart()
  loadOrderChart()
  loadQualityChart()
})
</script>

<style scoped>
.card-item { display: flex; align-items: center; gap: 15px; }
.card-icon { width: 56px; height: 56px; border-radius: 12px; display: flex; align-items: center; justify-content: center; color: #fff; }
.card-value { font-size: 28px; font-weight: bold; color: #333; }
.card-label { font-size: 13px; color: #999; margin-top: 4px; }
</style>
