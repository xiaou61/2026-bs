<template>
  <div class="dashboard-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">数据统计</span>
      </template>
    </el-page-header>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="6">
        <el-card>
          <el-statistic title="今日入库" :value="overview.todayIn">
            <template #prefix>
              <el-icon color="#409EFF"><Upload /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-statistic title="今日取件" :value="overview.todayOut">
            <template #prefix>
              <el-icon color="#67C23A"><Download /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-statistic title="当前库存" :value="overview.currentStock">
            <template #prefix>
              <el-icon color="#E6A23C"><Box /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-statistic title="超期快递" :value="overview.overdueCount">
            <template #prefix>
              <el-icon color="#F56C6C"><Warning /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card>
          <template #header>
            <span>7日入库/取件趋势</span>
          </template>
          <div ref="trendChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>快递公司占比</span>
          </template>
          <div ref="companyChartRef" style="height: 350px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>代收点业务量对比</span>
          </template>
          <div ref="stationChartRef" style="height: 350px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { getOverview, getCompanyRank, getStationRank, getTrend } from '@/api/stats'
import { getAllStations } from '@/api/station'
import { Upload, Download, Box, Warning } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'

const overview = reactive({
  todayIn: 0,
  todayOut: 0,
  currentStock: 0,
  overdueCount: 0
})

const companyRank = ref([])
const stationRank = ref([])
const stationList = ref([])
const trendChartRef = ref()
const companyChartRef = ref()
const stationChartRef = ref()

let trendChart = null
let companyChart = null
let stationChart = null

const getStationName = (stationId) => {
  const station = stationList.value.find(s => s.id === stationId)
  return station ? station.name : '-'
}

const initTrendChart = (trendData) => {
  if (trendChart) {
    trendChart.dispose()
  }
  
  trendChart = echarts.init(trendChartRef.value)
  
  const dates = trendData.map(item => item.date)
  const inCounts = trendData.map(item => item.inCount)
  const outCounts = trendData.map(item => item.outCount)
  
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['入库', '取件']
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: dates
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '入库',
        type: 'line',
        data: inCounts,
        smooth: true,
        itemStyle: { color: '#409EFF' }
      },
      {
        name: '取件',
        type: 'line',
        data: outCounts,
        smooth: true,
        itemStyle: { color: '#67C23A' }
      }
    ]
  }
  
  trendChart.setOption(option)
}

const initCompanyChart = (data) => {
  if (companyChart) {
    companyChart.dispose()
  }
  
  companyChart = echarts.init(companyChartRef.value)
  
  const chartData = data.map(item => ({
    name: item.company,
    value: item.count
  }))
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '快递数量',
        type: 'pie',
        radius: '60%',
        data: chartData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  
  companyChart.setOption(option)
}

const initStationChart = (data) => {
  if (stationChart) {
    stationChart.dispose()
  }
  
  stationChart = echarts.init(stationChartRef.value)
  
  const stationNames = data.map(item => getStationName(item.stationId))
  const counts = data.map(item => item.count)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: stationNames
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '快递数量',
        type: 'bar',
        data: counts,
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#83bff6' },
            { offset: 0.5, color: '#188df0' },
            { offset: 1, color: '#188df0' }
          ])
        },
        emphasis: {
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#2378f7' },
              { offset: 0.7, color: '#2378f7' },
              { offset: 1, color: '#83bff6' }
            ])
          }
        }
      }
    ]
  }
  
  stationChart.setOption(option)
}

const loadData = async () => {
  try {
    const [overviewRes, companyRes, stationRes, stationsRes, trendRes] = await Promise.all([
      getOverview(),
      getCompanyRank(),
      getStationRank(),
      getAllStations(),
      getTrend(7)
    ])
    
    Object.assign(overview, overviewRes.data)
    companyRank.value = companyRes.data
    stationRank.value = stationRes.data
    stationList.value = stationsRes.data
    
    await nextTick()
    
    initTrendChart(trendRes.data)
    initCompanyChart(companyRes.data)
    initStationChart(stationRes.data)
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  }
}

onMounted(() => {
  loadData()
  
  window.addEventListener('resize', () => {
    trendChart?.resize()
    companyChart?.resize()
    stationChart?.resize()
  })
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}
</style>

