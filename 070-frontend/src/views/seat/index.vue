<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-select v-model="showtimeId" placeholder="选择场次" style="width: 360px" @change="loadSeats">
          <el-option v-for="item in showtimes" :key="item.id" :label="`场次#${item.id} 影片${item.movieId} ${item.startTime}`" :value="item.id" />
        </el-select>
        <el-button type="primary" @click="loadShowtimes">刷新场次</el-button>
        <el-button type="success" @click="handleLock">锁定座位</el-button>
        <el-button @click="handleUnlock">释放座位</el-button>
      </div>

      <div class="seat-wrap">
        <div v-for="seat in seats" :key="seat.id" class="seat-item" :class="seatClass(seat)" @click="toggleSeat(seat)">
          {{ seat.rowNum }}-{{ seat.colNum }}
        </div>
      </div>

      <div class="order-bar">
        <div>已选座位：{{ selectedSeatText }}</div>
        <div>合计：¥{{ totalAmount.toFixed(2) }}</div>
        <el-select v-model="couponId" placeholder="选择优惠券" clearable style="width: 260px" @visible-change="loadCoupons">
          <el-option v-for="item in couponList" :key="item.userCouponId" :label="`${item.name} - ${item.discountType === 'RATE' ? item.discountValue + '折' : '减' + item.discountValue}`" :value="item.userCouponId" />
        </el-select>
        <el-button type="danger" @click="handleCreateOrder">提交订单</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createOrder, getAvailableCouponList, getPublicShowtimeList, getSeatList, lockSeat, unlockSeat } from '../../api'

const showtimes = ref([])
const showtimeId = ref(null)
const seats = ref([])
const selectedSeatIds = ref([])
const couponList = ref([])
const couponId = ref(null)

const loadShowtimes = async () => {
  const res = await getPublicShowtimeList({ pageNum: 1, pageSize: 100 })
  showtimes.value = res.data.records || []
  if (!showtimeId.value && showtimes.value.length) {
    showtimeId.value = showtimes.value[0].id
    await loadSeats()
  }
}

const loadSeats = async () => {
  if (!showtimeId.value) return
  const res = await getSeatList(showtimeId.value)
  seats.value = res.data || []
  selectedSeatIds.value = []
  couponId.value = null
}

const toggleSeat = seat => {
  if (seat.status !== 'AVAILABLE') return
  const idx = selectedSeatIds.value.indexOf(seat.id)
  if (idx >= 0) {
    selectedSeatIds.value.splice(idx, 1)
  } else {
    selectedSeatIds.value.push(seat.id)
  }
}

const seatClass = seat => {
  if (seat.status === 'SOLD') return 'sold'
  if (seat.status === 'LOCKED') return 'locked'
  if (selectedSeatIds.value.includes(seat.id)) return 'selected'
  return 'available'
}

const selectedSeats = computed(() => seats.value.filter(item => selectedSeatIds.value.includes(item.id)))
const totalAmount = computed(() => selectedSeats.value.reduce((sum, item) => sum + Number(item.price || 0), 0))
const selectedSeatText = computed(() => selectedSeats.value.map(i => `${i.rowNum}排${i.colNum}座`).join('，') || '-')

const handleLock = async () => {
  if (!showtimeId.value || !selectedSeatIds.value.length) {
    ElMessage.warning('请先选择座位')
    return
  }
  await lockSeat({ showtimeId: showtimeId.value, seatIds: selectedSeatIds.value })
  ElMessage.success('座位已锁定15分钟')
}

const handleUnlock = async () => {
  if (!showtimeId.value || !selectedSeatIds.value.length) {
    ElMessage.warning('请先选择座位')
    return
  }
  await unlockSeat({ showtimeId: showtimeId.value, seatIds: selectedSeatIds.value })
  ElMessage.success('已释放座位')
  await loadSeats()
}

const loadCoupons = async visible => {
  if (!visible || totalAmount.value <= 0) return
  const res = await getAvailableCouponList({ amount: totalAmount.value })
  couponList.value = res.data || []
}

const handleCreateOrder = async () => {
  if (!showtimeId.value || !selectedSeatIds.value.length) {
    ElMessage.warning('请先选择座位')
    return
  }
  await lockSeat({ showtimeId: showtimeId.value, seatIds: selectedSeatIds.value })
  const res = await createOrder({ showtimeId: showtimeId.value, seatIds: selectedSeatIds.value, couponId: couponId.value })
  ElMessage.success(`订单创建成功：${res.data.orderNo}`)
  selectedSeatIds.value = []
  couponId.value = null
  await loadSeats()
}

onMounted(loadShowtimes)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.seat-wrap {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: 8px;
  margin-bottom: 20px;
}

.seat-item {
  height: 34px;
  line-height: 34px;
  text-align: center;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
}

.available {
  background: #e9f6ec;
  color: #2f8f55;
}

.selected {
  background: #ffe7ba;
  color: #b26a00;
}

.locked {
  background: #f3f3f3;
  color: #999;
  cursor: not-allowed;
}

.sold {
  background: #fde2e2;
  color: #c0392b;
  cursor: not-allowed;
}

.order-bar {
  display: flex;
  align-items: center;
  gap: 14px;
  flex-wrap: wrap;
}
</style>
