<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="4"><el-card><div class="card"><span>供应商数</span><b>{{ stats.supplierCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>客户数</span><b>{{ stats.customerCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>商品数</span><b>{{ stats.productCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>库存预警</span><b>{{ stats.warnCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>今日采购额</span><b>{{ stats.todayPurchaseAmount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>今日销售额</span><b>{{ stats.todaySaleAmount || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-row :gutter="12" style="margin-top: 12px">
      <el-col :span="16"><el-card><div ref="trendRef" class="chart"></div></el-card></el-col>
      <el-col :span="8">
        <el-card>
          <template #header>最新公告</template>
          <el-empty v-if="!announcements.length" description="暂无公告" />
          <el-timeline v-else>
            <el-timeline-item v-for="item in announcements" :key="item.id" :timestamp="item.createTime">
              <b>{{ item.title }}</b>
              <div style="margin-top: 4px">{{ item.content }}</div>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getAnnouncementList, getDashboardStats, getDashboardTrend } from '../api'

const stats = ref({})
const announcements = ref([])
const trendRef = ref()

const loadData = async () => {
  const [statsRes, trendRes, noticeRes] = await Promise.all([
    getDashboardStats(),
    getDashboardTrend(),
    getAnnouncementList()
  ])
  stats.value = statsRes.data || {}
  announcements.value = noticeRes.data || []
  await nextTick()
  const trendData = trendRes.data || []
  const chart = echarts.init(trendRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['采购额', '销售额'] },
    xAxis: { type: 'category', data: trendData.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [
      { name: '采购额', type: 'line', smooth: true, data: trendData.map(i => i.purchaseAmount) },
      { name: '销售额', type: 'line', smooth: true, data: trendData.map(i => i.saleAmount) }
    ]
  })
}

onMounted(loadData)
</script>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.card b {
  font-size: 22px;
  color: #0f172a;
}

.chart {
  height: 360px;
}
</style>
