<template>
  <div>
    <el-row :gutter="12">
      <el-col :span="4"><el-card><div class="card"><span>用户数</span><b>{{ stats.userCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>房屋数</span><b>{{ stats.houseCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>待缴费</span><b>{{ stats.unpaidFeeCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>待处理报修</span><b>{{ stats.pendingRepairCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="4"><el-card><div class="card"><span>今日收费</span><b>{{ stats.todayPaidAmount || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-row :gutter="12" style="margin-top: 12px">
      <el-col :span="16"><el-card><div ref="trendRef" class="chart"></div></el-card></el-col>
      <el-col :span="8">
        <el-card>
          <template #header>报修状态分布</template>
          <div class="status-box">
            <el-tag v-for="item in repairStatus" :key="item.status" :type="tagType(item.status)">{{ statusText(item.status) }}：{{ item.total }}</el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getDashboardTrend } from '../api'

const stats = ref({})
const repairStatus = ref([])
const trendRef = ref()

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '待受理'
  if (v === 1) return '处理中'
  if (v === 2) return '已完成'
  if (v === 3) return '已取消'
  return '未知'
}

const tagType = (status) => {
  const v = Number(status)
  if (v === 0) return 'warning'
  if (v === 1) return 'primary'
  if (v === 2) return 'success'
  if (v === 3) return 'info'
  return ''
}

const loadData = async () => {
  const [statsRes, trendRes] = await Promise.all([getDashboardStats(), getDashboardTrend()])
  stats.value = statsRes.data || {}
  const trend = trendRes.data || {}
  const daily = trend.daily || []
  repairStatus.value = trend.repairStatus || []
  await nextTick()
  const chart = echarts.init(trendRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: daily.map(i => i.day) },
    yAxis: { type: 'value' },
    series: [{ name: '收费金额', type: 'line', smooth: true, data: daily.map(i => i.paidAmount) }]
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
  flex-wrap: wrap;
  gap: 10px;
}
</style>
