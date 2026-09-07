<template>
  <div class="dashboard">
    <el-row :gutter="16" class="mb-16">
      <el-col :span="8"><el-card><div class="stat"><span>工单总数</span><b>{{ stats.totalOrders || 0 }}</b></div></el-card></el-col>
      <el-col :span="8"><el-card><div class="stat"><span>待受理</span><b>{{ stats.pendingOrders || 0 }}</b></div></el-card></el-col>
      <el-col :span="8"><el-card><div class="stat"><span>处理中</span><b>{{ stats.processingOrders || 0 }}</b></div></el-card></el-col>
    </el-row>
    <el-row :gutter="16" class="mb-16">
      <el-col :span="8"><el-card><div class="stat"><span>已完成</span><b>{{ stats.completedOrders || 0 }}</b></div></el-card></el-col>
      <el-col :span="8"><el-card><div class="stat"><span>技师总数</span><b>{{ stats.totalTechnicians || 0 }}</b></div></el-card></el-col>
      <el-col :span="8"><el-card><div class="stat"><span>平均评分</span><b>{{ stats.avgScore || 0 }}</b></div></el-card></el-col>
    </el-row>
    <el-card>
      <template #header>
        <div class="card-header">
          <span>近7天工单趋势</span>
          <el-button type="primary" link @click="loadData">刷新</el-button>
        </div>
      </template>
      <el-table :data="trendRows" stripe>
        <el-table-column prop="date" label="日期" />
        <el-table-column prop="count" label="工单数" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDashboard, getOrderTrend } from '../api'

const stats = ref({})
const trendRows = ref([])

const loadData = async () => {
  const [dashboardRes, trendRes] = await Promise.all([getDashboard(), getOrderTrend()])
  stats.value = dashboardRes.data || {}
  const dateList = trendRes.data?.dateList || []
  const countList = trendRes.data?.countList || []
  trendRows.value = dateList.map((date, index) => ({
    date,
    count: countList[index] || 0
  }))
}

onMounted(loadData)
</script>

<style scoped>
.mb-16 {
  margin-bottom: 16px;
}

.stat {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat b {
  font-size: 26px;
  color: #1f2d3d;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
