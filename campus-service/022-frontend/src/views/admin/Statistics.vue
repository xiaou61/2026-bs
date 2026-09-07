<template>
  <div class="statistics">
    <div class="page-header">
      <h2>数据统计</h2>
      <div class="filters">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
        <el-select v-model="roomId" clearable placeholder="选择自习室" style="width: 220px">
          <el-option v-for="room in rooms" :key="room.id" :label="room.roomName" :value="room.id" />
        </el-select>
        <el-button type="primary" @click="loadStatistics">查询</el-button>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :span="8">
        <div class="stat-card">
          <div class="label">预约总数</div>
          <div class="value">{{ summary.totalReservations || 0 }}</div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="stat-card">
          <div class="label">签到总数</div>
          <div class="value">{{ summary.totalCheckins || 0 }}</div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="stat-card">
          <div class="label">平均使用率</div>
          <div class="value">{{ Number(summary.avgUsageRate || 0).toFixed(2) }}%</div>
        </div>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px;">
      <template #header>房间统计</template>
      <el-table :data="roomStatistics" stripe>
        <el-table-column prop="roomName" label="自习室" />
        <el-table-column prop="totalReservations" label="预约数" width="100" />
        <el-table-column prop="avgUsageRate" label="平均使用率" width="140">
          <template #default="{ row }">{{ Number(row.avgUsageRate || 0).toFixed(2) }}%</template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-if="peakHours.length" style="margin-top: 20px;">
      <template #header>高峰时段</template>
      <el-table :data="peakHours" stripe>
        <el-table-column prop="hour" label="小时" width="100">
          <template #default="{ row }">{{ row.hour }}:00</template>
        </el-table-column>
        <el-table-column prop="count" label="预约人数" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import dayjs from 'dayjs'
import { getOverallStatistics, getPeakHoursStatistics, getRoomStatistics } from '@/api/statistics'
import { getStudyRoomPage } from '@/api/studyRoom'

const rooms = ref([])
const roomId = ref(null)
const dateRange = ref([
  dayjs().subtract(6, 'day').format('YYYY-MM-DD'),
  dayjs().format('YYYY-MM-DD')
])
const summary = reactive({
  totalReservations: 0,
  totalCheckins: 0,
  avgUsageRate: 0
})
const roomStatistics = ref([])
const peakHours = ref([])

const loadRooms = async () => {
  const res = await getStudyRoomPage({ current: 1, size: 100 })
  rooms.value = res.data.records || []
  if (!roomId.value && rooms.value.length) {
    roomId.value = rooms.value[0].id
  }
}

const loadStatistics = async () => {
  const [startDate, endDate] = dateRange.value
  const overallRes = await getOverallStatistics({ startDate, endDate })

  summary.totalReservations = overallRes.data.totalReservations || 0
  summary.totalCheckins = overallRes.data.totalCheckins || 0
  summary.avgUsageRate = overallRes.data.avgUsageRate || 0
  roomStatistics.value = overallRes.data.roomStatistics || []

  if (roomId.value) {
    await getRoomStatistics(roomId.value, { startDate, endDate })
    const peakRes = await getPeakHoursStatistics(roomId.value, {
      date: endDate
    })
    peakHours.value = peakRes.data || []
  } else {
    peakHours.value = []
  }
}

onMounted(async () => {
  await loadRooms()
  await loadStatistics()
})
</script>

<style lang="scss" scoped>
.statistics {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }

  .filters {
    display: flex;
    gap: 12px;
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
      font-size: 28px;
      font-weight: 700;
      color: #303133;
    }
  }
}
</style>
