<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-select v-model="scheduleId" placeholder="选择班次" style="width: 420px" @change="loadSeats">
          <el-option
            v-for="item in schedules"
            :key="item.id"
            :label="`${scheduleLabel(item)} ｜ 余票${item.availableSeats}`"
            :value="item.id"
          />
        </el-select>
        <el-button type="primary" @click="loadSchedules">刷新班次</el-button>
        <el-button type="success" @click="handleLock">锁定座位</el-button>
        <el-button @click="handleUnlock">释放座位</el-button>
      </div>

      <div class="toolbar">
        <el-select v-model="selectedPassengerIds" multiple collapse-tags placeholder="选择乘车人" style="width: 420px">
          <el-option v-for="item in passengers" :key="item.id" :label="`${item.passengerName}(${item.idCard})`" :value="item.id" />
        </el-select>
        <el-input v-model="contactPhone" placeholder="联系人手机号" style="width: 220px" />
        <el-button type="danger" @click="handleCreateOrder">提交订单</el-button>
      </div>

      <div class="seat-wrap">
        <div v-for="seat in seats" :key="seat.id" class="seat-item" :class="seatClass(seat)" @click="toggleSeat(seat)">
          {{ seat.coachNo }}车{{ seat.rowNum }}{{ seat.seatNo }}
        </div>
      </div>

      <div class="order-bar">
        <div>已选座位：{{ selectedSeatText }}</div>
        <div>合计：¥{{ totalAmount.toFixed(2) }}</div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createOrder, getPassengerAll, getScheduleEnabledList, getSeatList, lockSeat, unlockSeat } from '../../api'

const schedules = ref([])
const scheduleId = ref(null)
const seats = ref([])
const passengers = ref([])
const selectedSeatIds = ref([])
const selectedPassengerIds = ref([])
const contactPhone = ref('')

const scheduleLabel = item => `${item.travelDate} ${item.departureTime} #${item.id}`

const loadSchedules = async () => {
  const res = await getScheduleEnabledList()
  schedules.value = res.data || []
  if (!scheduleId.value && schedules.value.length) {
    scheduleId.value = schedules.value[0].id
    await loadSeats()
  }
}

const loadSeats = async () => {
  if (!scheduleId.value) return
  const res = await getSeatList(scheduleId.value)
  seats.value = res.data || []
  selectedSeatIds.value = []
}

const loadPassengers = async () => {
  const res = await getPassengerAll()
  passengers.value = res.data || []
  const defaultPassenger = passengers.value.find(item => item.isDefault === 1)
  if (defaultPassenger) {
    selectedPassengerIds.value = [defaultPassenger.id]
    contactPhone.value = defaultPassenger.phone || ''
  }
}

const toggleSeat = seat => {
  if (seat.status !== 'AVAILABLE') return
  const index = selectedSeatIds.value.indexOf(seat.id)
  if (index >= 0) {
    selectedSeatIds.value.splice(index, 1)
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
const selectedSeatText = computed(() => selectedSeats.value.map(item => `${item.coachNo}车${item.rowNum}排${item.seatNo}座`).join('，') || '-')

const handleLock = async () => {
  if (!scheduleId.value || !selectedSeatIds.value.length) {
    ElMessage.warning('请先选择座位')
    return
  }
  await lockSeat({ scheduleId: scheduleId.value, seatIds: selectedSeatIds.value })
  ElMessage.success('座位已锁定15分钟')
  await loadSeats()
}

const handleUnlock = async () => {
  if (!scheduleId.value || !selectedSeatIds.value.length) {
    ElMessage.warning('请先选择座位')
    return
  }
  await unlockSeat({ scheduleId: scheduleId.value, seatIds: selectedSeatIds.value })
  ElMessage.success('已释放座位')
  await loadSeats()
}

const handleCreateOrder = async () => {
  if (!scheduleId.value || !selectedSeatIds.value.length) {
    ElMessage.warning('请先选择座位')
    return
  }
  if (!selectedPassengerIds.value.length) {
    ElMessage.warning('请先选择乘车人')
    return
  }
  await lockSeat({ scheduleId: scheduleId.value, seatIds: selectedSeatIds.value })
  const res = await createOrder({
    scheduleId: scheduleId.value,
    seatIds: selectedSeatIds.value,
    passengerIds: selectedPassengerIds.value,
    contactPhone: contactPhone.value
  })
  ElMessage.success(`订单创建成功：${res.data.orderNo}`)
  selectedSeatIds.value = []
  await loadSeats()
}

onMounted(async () => {
  await Promise.all([loadSchedules(), loadPassengers()])
})
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.seat-wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 8px;
  margin-bottom: 20px;
}

.seat-item {
  height: 36px;
  line-height: 36px;
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
  gap: 18px;
  flex-wrap: wrap;
}
</style>
