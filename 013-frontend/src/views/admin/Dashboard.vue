<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <el-icon :size="40" color="#409eff"><User /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount || 0 }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <el-icon :size="40" color="#67c23a"><Box /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.idleItemCount || 0 }}</div>
              <div class="stat-label">闲置物品数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <el-icon :size="40" color="#e6a23c"><Document /></el-icon>
            <div class="stat-info">
              <div class="stat-value">0</div>
              <div class="stat-label">订单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <el-icon :size="40" color="#f56c6c"><Money /></el-icon>
            <div class="stat-info">
              <div class="stat-value">¥0</div>
              <div class="stat-label">平台收益</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>订单趋势</span>
          </template>
          <div class="chart-placeholder">
            <el-icon :size="60"><TrendCharts /></el-icon>
            <p>图表区域（可集成 ECharts）</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>收益趋势</span>
          </template>
          <div class="chart-placeholder">
            <el-icon :size="60"><DataAnalysis /></el-icon>
            <p>图表区域（可集成 ECharts）</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStatsOverview } from '@/api/admin'

const stats = ref({})

const loadStats = async () => {
  try {
    const res = await getStatsOverview()
    stats.value = res.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadStats()
})
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 0;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.chart-placeholder {
  height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #909399;
}

.chart-placeholder p {
  margin-top: 15px;
}
</style>

