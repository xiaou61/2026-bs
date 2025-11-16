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
          {{ reservation.startTime }} - {{ reservation.endTime }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag>{{ reservation.status }}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getReservationDetail } from '@/api/reservation'

const route = useRoute()
const reservation = ref(null)

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