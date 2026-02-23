<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="4"><el-card><div class="card"><span>用户数</span><b>{{ stats.userCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>食材数</span><b>{{ stats.ingredientCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>经方数</span><b>{{ stats.formulaCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>方案数</span><b>{{ stats.planCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>服务单</span><b>{{ stats.orderCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>待处理服务单</span><b>{{ stats.pendingOrderCount || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-row :gutter="12" style="margin-top: 12px">
      <el-col :span="16"><el-card><div ref="trendRef" class="chart"></div></el-card></el-col>
      <el-col :span="8">
        <el-card>
          <template #header>服务单状态分布</template>
          <div class="status-box">
            <el-tag v-for="item in statusData" :key="item.status" :type="statusTagType(item.status)">{{ statusText(item.status) }}：{{ item.total }}</el-tag>
          </div>
        </el-card>
        <el-card style="margin-top: 12px">
          <template #header>平台提示</template>
          <div>已回复体质记录：{{ stats.repliedConstitutionCount || 0 }}</div>
          <div style="margin-top: 8px">建议管理员优先处理待处理服务单并定期发布调理公告。</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardOrderTrend, getDashboardStats } from '../api'

const stats = ref({})
const statusData = ref([])
const trendRef = ref()

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '待处理'
  if (v === 1) return '已确认'
  if (v === 2) return '已完成'
  if (v === 3) return '已取消'
  return '未知状态'
}

const statusTagType = (status) => {
  const v = Number(status)
  if (v === 0) return 'warning'
  if (v === 1) return 'primary'
  if (v === 2) return 'success'
  if (v === 3) return 'info'
  return ''
}

const loadData = async () => {
  const [statsRes, trendRes] = await Promise.all([
    getDashboardStats(),
    getDashboardOrderTrend()
  ])
  stats.value = statsRes.data || {}
  const trend = trendRes.data || {}
  const daily = trend.daily || []
  statusData.value = trend.status || []
  await nextTick()
  const chart = echarts.init(trendRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: daily.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [{ name: '服务单数量', type: 'line', smooth: true, data: daily.map(i => i.total) }]
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

.status-box {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}
</style>
