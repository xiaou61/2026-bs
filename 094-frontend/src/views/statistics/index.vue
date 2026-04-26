<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="6"><el-card><el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="今日订单数" :value="Number(stats.todayOrders || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="门店总数" :value="Number(stats.totalShops || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="今日预约数" :value="Number(stats.todayReservations || 0)" /></el-card></el-col>
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
          <template #header>热门菜单排行</template>
          <div ref="menuRef" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mt16">
      <template #header>门店销售排行</template>
      <div ref="shopRef" style="height: 360px"></div>
    </el-card>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getHotMenus, getSalesTrend, getShopRank } from '../../api'

const stats = ref({})
const trendRef = ref()
const menuRef = ref()
const shopRef = ref()

const loadData = async () => {
  const [a, b, c, d] = await Promise.all([
    getDashboardStats(),
    getSalesTrend({ days: 7 }),
    getHotMenus(),
    getShopRank()
  ])
  stats.value = a.data || {}
  await nextTick()

  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (b.data || []).map(i => Number(i.sales || 0)) }]
  })

  echarts.init(menuRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.menuName) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.salesAmount || 0)) }]
  })

  echarts.init(shopRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (d.data || []).map(i => i.shopName) },
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
