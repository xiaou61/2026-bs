<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon class="icon" style="color: #409EFF"><User /></el-icon>
            <div class="info"><div class="num">{{ stats.studentCount }}</div><div class="label">考生总数</div></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon class="icon" style="color: #67C23A"><Document /></el-icon>
            <div class="info"><div class="num">{{ stats.applicationCount }}</div><div class="label">报名数量</div></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon class="icon" style="color: #E6A23C"><Tickets /></el-icon>
            <div class="info"><div class="num">{{ stats.admissionCount }}</div><div class="label">录取人数</div></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon class="icon" style="color: #F56C6C"><School /></el-icon>
            <div class="info"><div class="num">{{ stats.majorCount }}</div><div class="label">专业数量</div></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>专业招生情况</template>
          <div ref="majorChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>生源地区分布</template>
          <div ref="provinceChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboard, getMajorStats, getProvinceStats } from '../api'

const stats = reactive({ studentCount: 0, applicationCount: 0, admissionCount: 0, majorCount: 0 })
const majorChartRef = ref()
const provinceChartRef = ref()

onMounted(async () => {
  const res = await getDashboard()
  Object.assign(stats, res.data)
  const majorRes = await getMajorStats()
  const provinceRes = await getProvinceStats()
  initMajorChart(majorRes.data)
  initProvinceChart(provinceRes.data)
})

const initMajorChart = (data) => {
  const chart = echarts.init(majorChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: data.map(item => item.majorName), axisLabel: { rotate: 30 } },
    yAxis: { type: 'value' },
    series: [
      { name: '计划招生', type: 'bar', data: data.map(item => item.planCount) },
      { name: '实际录取', type: 'bar', data: data.map(item => item.actualCount) }
    ]
  })
}

const initProvinceChart = (data) => {
  const chart = echarts.init(provinceChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      data: data.slice(0, 8).map(item => ({ name: item.province, value: item.count }))
    }]
  })
}
</script>

<style scoped>
.dashboard { padding: 10px; }
.stat-card { display: flex; align-items: center; }
.stat-card .icon { font-size: 50px; margin-right: 20px; }
.stat-card .num { font-size: 28px; font-weight: bold; }
.stat-card .label { color: #999; }
</style>
