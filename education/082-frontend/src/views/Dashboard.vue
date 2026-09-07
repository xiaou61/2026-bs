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
          <template #header>学科课程分布</template>
          <div ref="subjectChartRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>考试通过率</template>
          <div class="rate-wrap">
            <el-progress type="dashboard" :percentage="Number(dashboard.passRate || 0)" />
          </div>
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
const subjectChartRef = ref()

const cards = computed(() => [
  { label: '用户总数', value: dashboard.value.userCount || 0 },
  { label: '课程总数', value: dashboard.value.courseCount || 0 },
  { label: '试题总数', value: dashboard.value.questionCount || 0 },
  { label: '试卷总数', value: dashboard.value.paperCount || 0 }
])

const renderSubjectChart = () => {
  const chart = echarts.init(subjectChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        data: dashboard.value.subjectDistribution || []
      }
    ]
  })
}

const loadData = async () => {
  const res = await getDashboard()
  dashboard.value = res.data || {}
  await nextTick()
  renderSubjectChart()
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

.chart-row {
  margin-top: 0;
}

.chart-box {
  height: 320px;
}

.rate-wrap {
  height: 320px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
