<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col v-for="item in cards" :key="item.label" :span="6">
        <el-card>
          <div class="card-title">{{ item.label }}</div>
          <div class="card-value">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="16" class="chart-row">
      <el-col :span="12">
        <el-card>
          <template #header>月度体检预约趋势</template>
          <div ref="monthChartRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>异常预警分布</template>
          <div ref="warningChartRef" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboard } from '../api'

const dashboard = ref({})
const monthChartRef = ref()
const warningChartRef = ref()

const cards = computed(() => [
  { label: '老人总数', value: dashboard.value.elderCount || 0 },
  { label: '今日预约', value: dashboard.value.todayAppointmentCount || 0 },
  { label: '异常人数', value: dashboard.value.abnormalCount || 0 },
  { label: '完成率(%)', value: dashboard.value.finishRate || 0 }
])

const getValue = (obj, keys, defaultValue = 0) => {
  for (const key of keys) {
    if (obj[key] !== undefined && obj[key] !== null) {
      return obj[key]
    }
  }
  return defaultValue
}

const renderCharts = () => {
  const monthChart = echarts.getInstanceByDom(monthChartRef.value) || echarts.init(monthChartRef.value)
  const trend = dashboard.value.monthTrend || []
  monthChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: trend.map(item => getValue(item, ['month', 'MONTH'], ''))
    },
    yAxis: { type: 'value' },
    series: [
      {
        type: 'line',
        smooth: true,
        data: trend.map(item => Number(getValue(item, ['value', 'VALUE'], 0)))
      }
    ]
  })

  const warningChart = echarts.getInstanceByDom(warningChartRef.value) || echarts.init(warningChartRef.value)
  const warningData = (dashboard.value.warningDistribution || []).map(item => ({
    name: String(getValue(item, ['name', 'NAME'], 'unknown')),
    value: Number(getValue(item, ['value', 'VALUE'], 0))
  }))
  warningChart.setOption({
    tooltip: { trigger: 'item' },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        data: warningData
      }
    ]
  })
}

const loadData = async () => {
  const res = await getDashboard()
  dashboard.value = res.data || {}
  await nextTick()
  renderCharts()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.card-title {
  color: #606266;
  font-size: 14px;
}

.card-value {
  font-size: 28px;
  margin-top: 12px;
  color: #1f2d3d;
  font-weight: bold;
}

.chart-box {
  height: 320px;
}
</style>
