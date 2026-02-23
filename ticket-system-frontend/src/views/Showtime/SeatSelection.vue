<template>
  <div class="seat-selection">
    <el-card>
      <template #header>
        <h3>选座购票</h3>
      </template>
      
      <div class="movie-info">
        <h4>{{ movieInfo.title }}</h4>
        <p>{{ movieInfo.cinemaName }} - {{ movieInfo.hallName }}</p>
        <p>{{ movieInfo.showTime }}</p>
      </div>

      <div class="screen">银幕中央</div>

      <div class="seat-map">
        <div v-for="row in seatRows" :key="row" class="seat-row">
          <span class="row-label">{{ row }}排</span>
          <div
            v-for="seat in getSeatsByRow(row)"
            :key="seat.id"
            :class="['seat', getSeatClass(seat)]"
            @click="selectSeat(seat)"
          >
            {{ seat.colNum }}
          </div>
        </div>
      </div>

      <div class="legend">
        <div class="legend-item">
          <div class="seat available"></div>
          <span>可选</span>
        </div>
        <div class="legend-item">
          <div class="seat selected"></div>
          <span>已选</span>
        </div>
        <div class="legend-item">
          <div class="seat sold"></div>
          <span>已售</span>
        </div>
        <div class="legend-item">
          <div class="seat locked"></div>
          <span>锁定</span>
        </div>
      </div>

      <div class="footer">
        <div class="selected-info">
          <span>已选座位：{{ selectedSeats.map(s => `${s.rowNum}排${s.colNum}座`).join(', ') }}</span>
          <span>总价：¥{{ totalPrice }}</span>
        </div>
        <el-button type="primary" @click="confirmOrder" :disabled="selectedSeats.length === 0">确认下单</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { seatApi, showtimeApi, orderApi } from '../../api'

const route = useRoute()
const router = useRouter()

const seats = ref([])
const selectedSeats = ref([])
const movieInfo = ref({})

const seatRows = computed(() => {
  return [...new Set(seats.value.map(s => s.rowNum))].sort((a, b) => a - b)
})

const getSeatsByRow = (row) => {
  return seats.value.filter(s => s.rowNum === row).sort((a, b) => a.colNum - b.colNum)
}

const getSeatClass = (seat) => {
  if (selectedSeats.value.find(s => s.id === seat.id)) return 'selected'
  if (seat.status === 'sold') return 'sold'
  if (seat.status === 'locked') return 'locked'
  return 'available'
}

const totalPrice = computed(() => {
  return selectedSeats.value.reduce((sum, seat) => sum + parseFloat(seat.price), 0).toFixed(2)
})

const selectSeat = (seat) => {
  if (seat.status !== 'available') return
  
  const index = selectedSeats.value.findIndex(s => s.id === seat.id)
  if (index > -1) {
    selectedSeats.value.splice(index, 1)
  } else {
    selectedSeats.value.push(seat)
  }
}

const loadSeats = async () => {
  const res = await seatApi.getSeatsByShowtime(route.params.id)
  seats.value = res.data
}

const loadShowtime = async () => {
  const res = await showtimeApi.getShowtimeById(route.params.id)
  movieInfo.value = res.data
}

const confirmOrder = async () => {
  try {
    const seatIds = selectedSeats.value.map(s => s.id)
    const res = await orderApi.createOrder({
      showtimeId: route.params.id,
      seatIds
    })
    ElMessage.success('下单成功')
    router.push(`/order/${res.data.orderId}`)
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadSeats()
  loadShowtime()
})
</script>

<style scoped>
.seat-selection {
  max-width: 1200px;
  margin: 0 auto;
}

.movie-info {
  text-align: center;
  margin-bottom: 30px;
}

.screen {
  text-align: center;
  background: linear-gradient(to bottom, #666, #333);
  color: #fff;
  padding: 10px;
  margin: 20px auto 40px;
  width: 60%;
  border-radius: 5px;
}

.seat-map {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.seat-row {
  display: flex;
  align-items: center;
  gap: 10px;
}

.row-label {
  width: 50px;
  text-align: right;
  font-size: 14px;
  color: #666;
}

.seat {
  width: 35px;
  height: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
  font-size: 12px;
  transition: all 0.3s;
}

.seat.available {
  background-color: #67c23a;
  color: #fff;
}

.seat.available:hover {
  transform: scale(1.1);
}

.seat.selected {
  background-color: #409eff;
  color: #fff;
}

.seat.sold {
  background-color: #ddd;
  color: #999;
  cursor: not-allowed;
}

.seat.locked {
  background-color: #f56c6c;
  color: #fff;
  cursor: not-allowed;
}

.legend {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-top: 30px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.selected-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
