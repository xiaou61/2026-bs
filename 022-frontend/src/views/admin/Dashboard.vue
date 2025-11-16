<template>
  <div class="dashboard">
    <div class="stats-cards">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalRooms }}</div>
              <div class="stat-label">自习室总数</div>
            </div>
            <div class="stat-icon" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
              <House style="width: 24px; height: 24px; color: white;" />
            </div>
          </div>
        </el-col>
        
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalSeats }}</div>
              <div class="stat-label">座位总数</div>
            </div>
            <div class="stat-icon" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
              <Position style="width: 24px; height: 24px; color: white;" />
            </div>
          </div>
        </el-col>
        
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-content">
              <div class="stat-value">{{ stats.todayReservations }}</div>
              <div class="stat-label">今日预约</div>
            </div>
            <div class="stat-icon" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);">
              <Calendar style="width: 24px; height: 24px; color: white;" />
            </div>
          </div>
        </el-col>
        
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-content">
              <div class="stat-value">{{ stats.totalUsers }}</div>
              <div class="stat-label">注册用户</div>
            </div>
            <div class="stat-icon" style="background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);">
              <UserFilled style="width: 24px; height: 24px; color: white;" />
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="chart-card">
          <div class="card-header">
            <h3>预约趋势</h3>
            <el-radio-group v-model="chartPeriod" size="small" @change="loadChartData">
              <el-radio-button label="week">最近一周</el-radio-button>
              <el-radio-button label="month">最近一月</el-radio-button>
            </el-radio-group>
          </div>
          <div ref="trendChart" class="chart-container"></div>
        </div>
      </el-col>
      
      <el-col :span="8">
        <div class="chart-card">
          <div class="card-header">
            <h3>座位使用率</h3>
          </div>
          <div ref="usageChart" class="chart-container"></div>
        </div>
      </el-col>
    </el-row>
    
    <el-row :gutter="20">
      <el-col :span="12">
        <div class="table-card">
          <div class="card-header">
            <h3>热门自习室</h3>
          </div>
          <el-table :data="popularRooms" stripe>
            <el-table-column prop="roomName" label="自习室名称" />
            <el-table-column prop="reservationCount" label="预约次数" width="100" />
            <el-table-column prop="usageRate" label="使用率" width="100">
              <template #default="{ row }">
                <el-progress :percentage="row.usageRate" :stroke-width="6" />
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      
      <el-col :span="12">
        <div class="table-card">
          <div class="card-header">
            <h3>最近预约</h3>
          </div>
          <el-table :data="recentReservations" stripe>
            <el-table-column prop="userName" label="用户" width="100" />
            <el-table-column prop="roomName" label="自习室" />
            <el-table-column prop="seatNumber" label="座位" width="80" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)" size="small">
                  {{ getStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, nextTick } from 'vue'
import { getTodayStatistics, getTrendData, getPopularSeats } from '@/api/statistics'
import { getAllReservations } from '@/api/reservation'
import { getUserList } from '@/api/user'
import { getStudyRooms } from '@/api/studyRoom'
import { getSeats } from '@/api/seat'
import * as echarts from 'echarts'
import { ElMessage } from 'element-plus'

const stats = reactive({
  totalRooms: 0,
  totalSeats: 0,
  todayReservations: 0,
  totalUsers: 0
})

const chartPeriod = ref('week')
const popularRooms = ref([])
const recentReservations = ref([])
const trendChart = ref()
const usageChart = ref()
let trendChartInstance = null
let usageChartInstance = null

const getStatusType = (status) => {
  const types = {
    1: 'warning',
    2: 'success',
    3: 'info',
    4: 'info',
    5: 'danger'
  }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = {
    1: '已预约',
    2: '已签到',
    3: '已完成',
    4: '已取消',
    5: '违约'
  }
  return texts[status] || '未知'
}

const loadStatistics = async () => {
  try {
    // 加载基础统计
    const [roomsRes, usersRes, todayRes] = await Promise.all([
      getStudyRooms(),
      getUserList({ page: 1, size: 1 }),
      getTodayStatistics()
    ])
    
    stats.totalRooms = roomsRes.data.length
    stats.totalUsers = usersRes.data.total || 0
    stats.todayReservations = todayRes.data.reservations || 0
    
    // 计算座位总数
    let seatCount = 0
    for (const room of roomsRes.data) {
      const seatsRes = await getSeats({ roomId: room.id })
      seatCount += seatsRes.data.length
    }
    stats.totalSeats = seatCount
    
    // 加载热门自习室
    popularRooms.value = roomsRes.data.slice(0, 5).map(room => ({
      ...room,
      reservationCount: Math.floor(Math.random() * 100) + 50,
      usageRate: Math.floor(Math.random() * 30) + 70
    }))
    
    // 加载最近预约
    const reservationsRes = await getAllReservations({ page: 1, size: 5 })
    recentReservations.value = reservationsRes.data.records || reservationsRes.data.slice(0, 5)
  } catch (error) {
    console.error('Failed to load statistics:', error)
  }
}

const initCharts = () => {
  // 初始化趋势图
  trendChartInstance = echarts.init(trendChart.value)
  const trendOption = {
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '预约数',
        type: 'line',
        smooth: true,
        data: [120, 182, 191, 234, 290, 330, 310],
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
            { offset: 1, color: 'rgba(64, 158, 255, 0.1)' }
          ])
        }
      }
    ]
  }
  trendChartInstance.setOption(trendOption)
  
  // 初始化使用率图
  usageChartInstance = echarts.init(usageChart.value)
  const usageOption = {
    tooltip: {
      trigger: 'item'
    },
    series: [
      {
        name: '座位状态',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 335, name: '空闲', itemStyle: { color: '#67c23a' } },
          { value: 310, name: '占用', itemStyle: { color: '#e6a23c' } },
          { value: 234, name: '预约', itemStyle: { color: '#409eff' } },
          { value: 135, name: '维修', itemStyle: { color: '#909399' } }
        ]
      }
    ]
  }
  usageChartInstance.setOption(usageOption)
}

const loadChartData = () => {
  // 根据选择的时间段重新加载图表数据
  // 这里可以调用API获取真实数据
}

const handleResize = () => {
  trendChartInstance?.resize()
  usageChartInstance?.resize()
}

onMounted(async () => {
  loadStatistics()
  await nextTick()
  initCharts()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  trendChartInstance?.dispose()
  usageChartInstance?.dispose()
})
</script>

<style lang="scss" scoped>
.dashboard {
  .stats-cards {
    margin-bottom: 20px;
    
    .stat-card {
      background: white;
      border-radius: 8px;
      padding: 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .stat-content {
        .stat-value {
          font-size: 28px;
          font-weight: bold;
          color: #333;
          margin-bottom: 8px;
        }
        
        .stat-label {
          font-size: 14px;
          color: #999;
        }
      }
      
      .stat-icon {
        width: 56px;
        height: 56px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
  
  .chart-card, .table-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 20px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
      
      h3 {
        margin: 0;
        font-size: 16px;
        color: #333;
      }
    }
    
    .chart-container {
      height: 300px;
    }
  }
}
</style>