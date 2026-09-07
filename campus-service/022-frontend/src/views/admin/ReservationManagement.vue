<template>
  <div class="reservation-management">
    <div class="page-header">
      <h2>预约监控</h2>
      <div class="filters">
        <el-select v-model="roomId" clearable placeholder="选择自习室" style="width: 220px" @change="loadReservations">
          <el-option v-for="room in rooms" :key="room.id" :label="room.roomName" :value="room.id" />
        </el-select>
        <el-select v-model="status" clearable placeholder="状态" style="width: 140px" @change="loadReservations">
          <el-option label="已预约" :value="1" />
          <el-option label="已签到" :value="2" />
          <el-option label="已完成" :value="3" />
          <el-option label="已取消" :value="4" />
          <el-option label="违约" :value="5" />
        </el-select>
      </div>
    </div>

    <el-table :data="reservations" stripe v-loading="loading">
      <el-table-column prop="username" label="账号" width="130" />
      <el-table-column prop="realName" label="姓名" width="100" />
      <el-table-column prop="roomName" label="自习室" />
      <el-table-column prop="seatNumber" label="座位" width="90" />
      <el-table-column prop="startTime" label="开始时间" width="170" />
      <el-table-column prop="endTime" label="结束时间" width="170" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="qrcodeContent" label="二维码内容" min-width="220" show-overflow-tooltip />
    </el-table>

    <el-pagination
      v-if="total > pageSize"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="handlePageChange"
      style="margin-top: 20px; text-align: center;"
    />
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getAllReservations } from '@/api/reservation'
import { getStudyRoomPage } from '@/api/studyRoom'

const loading = ref(false)
const reservations = ref([])
const rooms = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const roomId = ref(null)
const status = ref(null)

const getStatusType = (value) => ({
  1: 'warning',
  2: 'success',
  3: 'info',
  4: 'info',
  5: 'danger'
}[value] || 'info')

const getStatusText = (value) => ({
  1: '已预约',
  2: '已签到',
  3: '已完成',
  4: '已取消',
  5: '违约'
}[value] || '未知')

const loadRooms = async () => {
  const res = await getStudyRoomPage({ current: 1, size: 100 })
  rooms.value = res.data.records || []
}

const loadReservations = async () => {
  loading.value = true
  try {
    const res = await getAllReservations({
      current: currentPage.value,
      size: pageSize.value,
      roomId: roomId.value || undefined,
      status: status.value || undefined
    })
    reservations.value = res.data.records || []
    total.value = res.data.total || reservations.value.length
  } finally {
    loading.value = false
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadReservations()
}

onMounted(async () => {
  await loadRooms()
  await loadReservations()
})
</script>

<style lang="scss" scoped>
.reservation-management {
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
}
</style>
