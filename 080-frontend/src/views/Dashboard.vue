<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #409eff">
              <el-icon size="30"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalChildren }}</div>
              <div class="stat-label">儿童总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #67c23a">
              <el-icon size="30"><Check /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.sponsoredChildren }}</div>
              <div class="stat-label">已资助儿童</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #e6a23c">
              <el-icon size="30"><UserFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalDonors }}</div>
              <div class="stat-label">捐赠人总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #f56c6c">
              <el-icon size="30"><Money /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalAmount }}</div>
              <div class="stat-label">总捐赠金额</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>捐赠趋势</span>
          </template>
          <div ref="trendChart" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>地区分布</span>
          </template>
          <div ref="regionChart" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboard, getDonationTrend, getRegionDistribution } from '../api'

const stats = ref({
  totalChildren: 0,
  sponsoredChildren: 0,
  totalDonors: 0,
  totalAmount: 0
})

const trendChart = ref()
const regionChart = ref()

const loadDashboard = async () => {
  const res = await getDashboard()
  stats.value = res.data
}

const loadTrendChart = async () => {
  const res = await getDonationTrend()
  const chart = echarts.init(trendChart.value)
  chart.setOption({
    xAxis: {
      type: 'category',
      data: res.data.dateList
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: res.data.amountList,
      type: 'line',
      smooth: true,
      itemStyle: { color: '#409eff' }
    }],
    tooltip: {
      trigger: 'axis'
    }
  })
}

const loadRegionChart = async () => {
  const res = await getRegionDistribution()
  const chart = echarts.init(regionChart.value)
  chart.setOption({
    xAxis: {
      type: 'category',
      data: res.data.provinceList
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: res.data.countList,
      type: 'bar',
      itemStyle: { color: '#67c23a' }
    }],
    tooltip: {
      trigger: 'axis'
    }
  })
}

onMounted(() => {
  loadDashboard()
  loadTrendChart()
  loadRegionChart()
})
</script>

<style scoped>
.dashboard {
  width: 100%;
}

.stat-card {
  cursor: pointer;
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}
</style>
