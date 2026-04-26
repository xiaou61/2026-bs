<template>
  <div class="page-container">
    <el-row :gutter="12">
      <el-col :span="8"><el-card><div ref="departmentRef" class="chart" /></el-card></el-col>
      <el-col :span="8"><el-card><div ref="trendRef" class="chart" /></el-card></el-col>
      <el-col :span="8"><el-card><div ref="doctorRef" class="chart" /></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getAppointmentTrend, getDepartmentRank, getHotDoctors } from '../../api'

const departmentRef = ref()
const trendRef = ref()
const doctorRef = ref()

onMounted(async () => {
  const [depRes, trendRes, doctorRes] = await Promise.all([getDepartmentRank(), getAppointmentTrend(), getHotDoctors()])
  await nextTick()
  echarts.init(departmentRef.value).setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['45%', '70%'], data: depRes.data || [] }]
  })
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (trendRes.data || []).map(item => item.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (trendRes.data || []).map(item => item.count) }]
  })
  echarts.init(doctorRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'value' },
    yAxis: { type: 'category', data: (doctorRes.data || []).map(item => item.name) },
    series: [{ type: 'bar', data: (doctorRes.data || []).map(item => item.appointmentCount) }]
  })
})
</script>

<style scoped>
.page-container { padding: 4px; }
.chart { height: 360px; }
</style>
