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
          <template #header>下载趋势</template>
          <div ref="trendRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>热门资料TOP5</template>
          <div ref="hotRef" class="chart-box"></div>
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
const hotRef = ref()

const cards = computed(() => [
  { label: '用户总数', value: dashboard.value.userCount || 0 },
  { label: '资料总数', value: dashboard.value.materialCount || 0 },
  { label: '下载总量', value: dashboard.value.downloadCount || 0 },
  { label: '通过率(%)', value: dashboard.value.passRate || 0 }
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

  const hotData = (dashboard.value.hotTop || []).map(i => ({
    name: String(getVal(i, ['name', 'NAME'], '未知资料')),
    value: Number(getVal(i, ['value', 'VALUE'], 0))
  }))
  const hotChart = echarts.getInstanceByDom(hotRef.value) || echarts.init(hotRef.value)
  hotChart.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['40%', '70%'], data: hotData }]
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
