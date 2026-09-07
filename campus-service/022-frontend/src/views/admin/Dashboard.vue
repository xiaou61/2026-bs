<template>
  <div class="dashboard">
    <el-row :gutter="20" class="summary-row">
      <el-col :span="6">
        <div class="stat-card">
          <div class="label">自习室总数</div>
          <div class="value">{{ summary.totalRooms }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="label">座位总数</div>
          <div class="value">{{ summary.totalSeats }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="label">注册用户</div>
          <div class="value">{{ summary.totalUsers }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="label">预约总数</div>
          <div class="value">{{ summary.totalReservations }}</div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header>今日数据</template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="总预约数">{{ dashboard.today?.totalReservations || 0 }}</el-descriptions-item>
            <el-descriptions-item label="实际签到数">{{ dashboard.today?.totalCheckins || 0 }}</el-descriptions-item>
            <el-descriptions-item label="平均使用率">{{ formatPercent(dashboard.today?.avgUsageRate) }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>近 7 天</template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="总预约数">{{ dashboard.week?.totalReservations || 0 }}</el-descriptions-item>
            <el-descriptions-item label="实际签到数">{{ dashboard.week?.totalCheckins || 0 }}</el-descriptions-item>
            <el-descriptions-item label="平均使用率">{{ formatPercent(dashboard.week?.avgUsageRate) }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>近 30 天</template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="总预约数">{{ dashboard.month?.totalReservations || 0 }}</el-descriptions-item>
            <el-descriptions-item label="实际签到数">{{ dashboard.month?.totalCheckins || 0 }}</el-descriptions-item>
            <el-descriptions-item label="平均使用率">{{ formatPercent(dashboard.month?.avgUsageRate) }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>房间使用概览</template>
          <el-table :data="roomStats" stripe>
            <el-table-column prop="roomName" label="自习室" />
            <el-table-column prop="totalReservations" label="预约数" width="100" />
            <el-table-column label="平均使用率" width="180">
              <template #default="{ row }">
                <el-progress :percentage="Number((row.avgUsageRate || 0).toFixed(2))" :stroke-width="10" />
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>最近预约</template>
          <el-table :data="recentReservations" stripe>
            <el-table-column prop="realName" label="用户" width="100" />
            <el-table-column prop="roomName" label="自习室" />
            <el-table-column prop="seatNumber" label="座位" width="80" />
            <el-table-column label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getDashboardData } from '@/api/statistics'
import { getAllReservations } from '@/api/reservation'
import { getUserList } from '@/api/user'
import { getStudyRoomPage } from '@/api/studyRoom'
import { getSeatPage } from '@/api/seat'

const summary = reactive({
  totalRooms: 0,
  totalSeats: 0,
  totalUsers: 0,
  totalReservations: 0
})

const dashboard = reactive({
  today: null,
  week: null,
  month: null
})

const roomStats = ref([])
const recentReservations = ref([])

const formatPercent = (value) => `${Number(value || 0).toFixed(2)}%`
const getStatusType = (status) => ({
  1: 'warning',
  2: 'success',
  3: 'info',
  4: 'info',
  5: 'danger'
}[status] || 'info')
const getStatusText = (status) => ({
  1: '已预约',
  2: '已签到',
  3: '已完成',
  4: '已取消',
  5: '违约'
}[status] || '未知')

const loadDashboard = async () => {
  const [dashboardRes, roomRes, seatRes, userRes, reservationRes] = await Promise.all([
    getDashboardData(),
    getStudyRoomPage({ current: 1, size: 100 }),
    getSeatPage({ current: 1, size: 1 }),
    getUserList({ current: 1, size: 1 }),
    getAllReservations({ current: 1, size: 5 })
  ])

  dashboard.today = dashboardRes.data.today
  dashboard.week = dashboardRes.data.week
  dashboard.month = dashboardRes.data.month
  roomStats.value = dashboard.month?.roomStatistics || []
  recentReservations.value = reservationRes.data.records || []

  summary.totalRooms = roomRes.data.total || roomRes.data.records?.length || 0
  summary.totalSeats = seatRes.data.total || 0
  summary.totalUsers = userRes.data.total || 0
  summary.totalReservations = dashboard.month?.totalReservations || 0
}

onMounted(() => {
  loadDashboard()
})
</script>

<style lang="scss" scoped>
.dashboard {
  .summary-row {
    margin-bottom: 20px;
  }

  .stat-card {
    padding: 20px;
    background: white;
    border-radius: 8px;

    .label {
      color: #909399;
      margin-bottom: 8px;
    }

    .value {
      font-size: 30px;
      font-weight: 700;
      color: #303133;
    }
  }
}
</style>
