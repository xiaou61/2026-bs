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
          <template #header>月度评价趋势</template>
          <div ref="trendRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>课程均分TOP5</template>
          <div ref="topRef" class="chart-box"></div>
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
const trendRef = ref()
const topRef = ref()

const cards = computed(() => [
  { label: '用户总数', value: dashboard.value.userCount || 0 },
  { label: '课程总数', value: dashboard.value.courseCount || 0 },
  { label: '任务总数', value: dashboard.value.taskCount || 0 },
  { label: '评价总数', value: dashboard.value.recordCount || 0 }
])

const getVal = (obj, keys, d = 0) => {
  for (const k of keys) {
    if (obj[k] !== undefined && obj[k] !== null) return obj[k]
  }
  return d
}

const renderCharts = () => {
  const trendData = dashboard.value.monthTrend || []
  const trendChart = echarts.getInstanceByDom(trendRef.value) || echarts.init(trendRef.value)
  trendChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: trendData.map(i => getVal(i, ['month', 'MONTH'], '')) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: trendData.map(i => Number(getVal(i, ['value', 'VALUE'], 0))) }]
  })

  const topData = (dashboard.value.courseTop || []).map(i => ({
    name: String(getVal(i, ['name', 'NAME'], '未知课程')),
    value: Number(getVal(i, ['value', 'VALUE'], 0))
  }))
  const topChart = echarts.getInstanceByDom(topRef.value) || echarts.init(topRef.value)
  topChart.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['40%', '70%'], data: topData }]
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
