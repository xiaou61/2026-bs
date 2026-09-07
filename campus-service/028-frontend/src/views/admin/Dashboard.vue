<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #409eff"><el-icon><User /></el-icon></div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.userCount }}</div>
            <div class="stat-label">用户总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #67c23a"><el-icon><Bicycle /></el-icon></div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.bikeCount }}</div>
            <div class="stat-label">车辆总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #e6a23c"><el-icon><DocumentChecked /></el-icon></div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.orderCount }}</div>
            <div class="stat-label">订单总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #f56c6c"><el-icon><Money /></el-icon></div>
          <div class="stat-content">
            <div class="stat-value">¥{{ stats.totalIncome }}</div>
            <div class="stat-label">总收入</div>
          </div>
        </div>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="mt-20">
      <el-col :span="12">
        <el-card>
          <template #header>近7天订单趋势</template>
          <div ref="orderChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>车辆状态分布</template>
          <div ref="bikeChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="mt-20">
      <el-col :span="12">
        <el-card>
          <template #header>最近订单</template>
          <el-table :data="recentOrders" size="small">
            <el-table-column prop="orderNo" label="订单号" width="180" />
            <el-table-column prop="username" label="用户" />
            <el-table-column prop="amount" label="金额" />
            <el-table-column prop="createTime" label="时间" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>待处理故障</template>
          <el-table :data="pendingFaults" size="small">
            <el-table-column prop="bikeNo" label="车辆编号" />
            <el-table-column prop="description" label="故障描述" />
            <el-table-column prop="createTime" label="上报时间" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { User, Bicycle, DocumentChecked, Money } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { adminApi } from '@/api'

const stats = reactive({ userCount: 0, bikeCount: 0, orderCount: 0, totalIncome: 0 })
const recentOrders = ref([])
const pendingFaults = ref([])
const orderChartRef = ref()
const bikeChartRef = ref()

const loadStats = async () => {
  try {
    const res = await adminApi.getOverview()
    Object.assign(stats, res.data)
    recentOrders.value = res.data.recentOrders || []
    pendingFaults.value = res.data.pendingFaults || []
  } catch (e) {
    console.error(e)
  }
}

const initCharts = () => {
  // 订单趋势图
  const orderChart = echarts.init(orderChartRef.value)
  orderChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] },
    yAxis: { type: 'value' },
    series: [{ data: [120, 132, 101, 134, 90, 230, 210], type: 'line', smooth: true, areaStyle: {} }]
  })
  
  // 车辆状态饼图
  const bikeChart = echarts.init(bikeChartRef.value)
  bikeChart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      data: [
        { value: 15, name: '可用' },
        { value: 3, name: '使用中' },
        { value: 2, name: '维修中' }
      ]
    }]
  })
}

onMounted(() => {
  loadStats()
  initCharts()
})
</script>

<style scoped lang="scss">
.stat-card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  
  .stat-icon {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    font-size: 28px;
    margin-right: 15px;
  }
  
  .stat-content {
    .stat-value {
      font-size: 28px;
      font-weight: bold;
      color: #333;
    }
    .stat-label {
      color: #999;
      font-size: 14px;
    }
  }
}
</style>
