<template>
  <div>
    <el-card class="mb16">
      <template #header>核心指标</template>
      <div class="card-grid">
        <el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" />
        <el-statistic title="今日订单" :value="Number(stats.todayOrders || 0)" />
        <el-statistic title="总订单" :value="Number(stats.totalOrders || 0)" />
        <el-statistic title="用户总数" :value="Number(stats.totalUsers || 0)" />
      </div>
    </el-card>

    <el-row :gutter="16">
      <el-col :span="12">
        <el-card>
          <template #header>销售趋势</template>
          <div ref="salesRef" style="height: 340px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>票房排行</template>
          <div ref="boxRef" style="height: 340px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getBoxOffice, getDashboardStats, getSalesTrend } from '../../api'

const stats = ref({})
const salesRef = ref()
const boxRef = ref()

const loadData = async () => {
  const [a, b, c] = await Promise.all([getDashboardStats(), getSalesTrend({ days: 15 }), getBoxOffice()])
  stats.value = a.data || {}
  await nextTick()
  const salesChart = echarts.init(salesRef.value)
  salesChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, areaStyle: {}, data: (b.data || []).map(i => Number(i.sales || 0)) }]
  })
  const boxChart = echarts.init(boxRef.value)
  boxChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.movieTitle) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.boxOffice || 0)) }]
  })
}

onMounted(loadData)
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}
</style>
