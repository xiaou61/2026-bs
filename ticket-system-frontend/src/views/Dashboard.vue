<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #409eff">
              <el-icon><Money /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">¥{{ stats.todaySales || 0 }}</div>
              <div class="stat-label">今日销售额</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #67c23a">
              <el-icon><ShoppingCart /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.todayOrders || 0 }}</div>
              <div class="stat-label">今日订单</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #e6a23c">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalUsers || 0 }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #f56c6c">
              <el-icon><Film /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalMovies || 0 }}</div>
              <div class="stat-label">热映电影</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>销售趋势</span>
          </template>
          <div ref="trendChart" style="width: 100%; height: 400px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <span>票房排行</span>
          </template>
          <div class="rank-list">
            <div v-for="(item, index) in boxOfficeRank" :key="index" class="rank-item">
              <div class="rank-number">{{ index + 1 }}</div>
              <div class="rank-info">
                <div class="rank-title">{{ item.movieTitle }}</div>
                <div class="rank-value">¥{{ item.boxOffice }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { statisticsApi } from '../api'

const stats = ref({})
const trendChart = ref()
const boxOfficeRank = ref([])

const loadStats = async () => {
  const res = await statisticsApi.getDashboardStats()
  stats.value = res.data
}

const loadTrend = async () => {
  const res = await statisticsApi.getSalesTrend(7)
  const dates = res.data.map(item => item.date)
  const amounts = res.data.map(item => item.amount)
  
  const chart = echarts.init(trendChart.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: dates },
    yAxis: { type: 'value' },
    series: [{
      data: amounts,
      type: 'line',
      smooth: true,
      areaStyle: {}
    }]
  })
}

const loadBoxOffice = async () => {
  const res = await statisticsApi.getBoxOfficeRank()
  boxOfficeRank.value = res.data.slice(0, 5)
}

onMounted(() => {
  loadStats()
  loadTrend()
  loadBoxOffice()
})
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
  color: #fff;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.rank-list {
  max-height: 400px;
  overflow-y: auto;
}

.rank-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.rank-number {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-right: 15px;
}

.rank-info {
  flex: 1;
}

.rank-title {
  font-size: 14px;
  color: #333;
}

.rank-value {
  font-size: 16px;
  color: #f56c6c;
  font-weight: bold;
  margin-top: 5px;
}
</style>
