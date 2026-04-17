<template>
  <div class="reservation-detail">
    <h2>预约详情</h2>
    <el-card v-if="reservation">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="自习室">
          {{ reservation.roomName }}
        </el-descriptions-item>
        <el-descriptions-item label="座位编号">
          {{ reservation.seatNumber }}
        </el-descriptions-item>
        <el-descriptions-item label="预约时间">
          {{ formatTime(reservation.startTime) }} - {{ formatTime(reservation.endTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(reservation.status)">
            {{ getStatusText(reservation.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="签到时间">
          {{ reservation.checkInTime ? formatTime(reservation.checkInTime) : '未签到' }}
        </el-descriptions-item>
        <el-descriptions-item label="结束时间">
          {{ reservation.actualEndTime ? formatTime(reservation.actualEndTime) : '未结束' }}
        </el-descriptions-item>
        <el-descriptions-item label="二维码内容" :span="2">
          {{ reservation.qrcodeContent || '暂无二维码' }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-empty v-else description="预约记录不存在" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getReservationDetail } from '@/api/reservation'
import dayjs from 'dayjs'

const route = useRoute()
const reservation = ref(null)

const formatTime = (value) => dayjs(value).format('YYYY-MM-DD HH:mm')
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

const loadReservation = async () => {
  try {
    const res = await getReservationDetail(route.params.id)
    reservation.value = res.data
  } catch (error) {
    console.error('Failed to load reservation:', error)
  }
}

onMounted(() => {
  loadReservation()
})
</script>
