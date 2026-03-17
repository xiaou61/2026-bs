<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="6"><el-card><el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="今日订单数" :value="Number(stats.todayOrders || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="设备总数" :value="Number(stats.totalMachines || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="今日补货单" :value="Number(stats.todayReplenish || 0)" /></el-card></el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>销售趋势</template>
          <div ref="trendRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>热销商品排行</template>
          <div ref="productRef" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mt16">
      <template #header>设备销售排行</template>
      <div ref="machineRef" style="height: 360px"></div>
    </el-card>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getHotProducts, getMachineRank, getSalesTrend } from '../../api'

const stats = ref({})
const trendRef = ref()
const productRef = ref()
const machineRef = ref()

const loadData = async () => {
  const [a, b, c, d] = await Promise.all([
    getDashboardStats(),
    getSalesTrend({ days: 7 }),
    getHotProducts(),
    getMachineRank()
  ])
  stats.value = a.data || {}
  await nextTick()

  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (b.data || []).map(i => Number(i.sales || 0)) }]
  })

  echarts.init(productRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.productName) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.salesAmount || 0)) }]
  })

  echarts.init(machineRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (d.data || []).map(i => i.machineName) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (d.data || []).map(i => Number(i.salesAmount || 0)) }]
  })
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.mt16 {
  margin-top: 16px;
}
</style>
