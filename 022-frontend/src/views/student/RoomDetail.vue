<template>
  <div class="room-detail">
    <div class="page-header">
      <el-button icon="ArrowLeft" @click="router.back()">返回</el-button>
      <h2>{{ room?.roomName || '自习室' }}</h2>
      <div class="room-status">
        <span>空闲: {{ availableCount }}</span>
        <span>占用: {{ occupiedCount }}</span>
        <span>总计: {{ seats.length }}</span>
      </div>
    </div>
    
    <div class="seat-selection">
      <div class="seat-filters">
        <el-radio-group v-model="filterType" @change="handleFilterChange">
          <el-radio-button label="">全部</el-radio-button>
          <el-radio-button label="1">普通座位</el-radio-button>
          <el-radio-button label="2">电源座位</el-radio-button>
          <el-radio-button label="3">静音区</el-radio-button>
        </el-radio-group>
        
        <el-button type="primary" :disabled="!selectedSeat" @click="handleReserve">
          预约座位
        </el-button>
      </div>
      
      <div class="seat-map-container">
        <div class="seat-legend">
          <div class="legend-item">
            <div class="seat-icon available"></div>
            <span>空闲</span>
          </div>
          <div class="legend-item">
            <div class="seat-icon occupied"></div>
            <span>占用</span>
          </div>
          <div class="legend-item">
            <div class="seat-icon selected"></div>
            <span>已选</span>
          </div>
          <div class="legend-item">
            <div class="seat-icon maintenance"></div>
            <span>维修</span>
          </div>
        </div>
        
        <div v-loading="loading" class="seat-map">
          <div class="seats-grid">
            <div
              v-for="seat in filteredSeats"
              :key="seat.id"
              :class="[
                'seat',
                getSeatClass(seat),
                { selected: selectedSeat?.id === seat.id }
              ]"
              @click="selectSeat(seat)"
            >
              <div class="seat-number">{{ seat.seatNumber }}</div>
              <el-icon v-if="seat.seatType === 2"><Connection /></el-icon>
              <el-icon v-if="seat.seatType === 3"><Mute /></el-icon>
            </div>
          </div>
        </div>
      </div>
      
      <div v-if="selectedSeat" class="selected-info">
        <h3>已选座位信息</h3>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="座位编号">
            {{ selectedSeat.seatNumber }}
          </el-descriptions-item>
          <el-descriptions-item label="座位类型">
            {{ getSeatTypeName(selectedSeat.seatType) }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    
    <el-dialog v-model="reserveDialogVisible" title="预约座位" width="500px">
      <el-form ref="reserveForm" :model="reserveFormData" :rules="reserveRules" label-width="100px">
        <el-form-item label="座位编号">
          <el-input :value="selectedSeat?.seatNumber" disabled />
        </el-form-item>
        
        <el-form-item label="预约日期" prop="date">
          <el-date-picker
            v-model="reserveFormData.date"
            type="date"
            placeholder="选择日期"
            :disabled-date="disabledDate"
            style="width: 100%"
          />
        </el-form-item>
        
        <el-form-item label="开始时间" prop="startTime">
          <el-time-select
            v-model="reserveFormData.startTime"
            start="06:00"
            step="00:30"
            end="22:00"
            placeholder="选择开始时间"
            style="width: 100%"
          />
        </el-form-item>
        
        <el-form-item label="结束时间" prop="endTime">
          <el-time-select
            v-model="reserveFormData.endTime"
            start="06:00"
            step="00:30"
            end="22:00"
            placeholder="选择结束时间"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="reserveDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="reserving" @click="confirmReserve">
          确定预约
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getRoomDetail } from '@/api/studyRoom'
import { getSeats } from '@/api/seat'
import { createReservation } from '@/api/reservation'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'

const router = useRouter()
const route = useRoute()

const roomId = route.params.id
const room = ref(null)
const seats = ref([])
const selectedSeat = ref(null)
const filterType = ref('')
const loading = ref(false)
const reserving = ref(false)
const reserveDialogVisible = ref(false)

const reserveFormData = ref({
  date: '',
  startTime: '',
  endTime: ''
})

const reserveRules = {
  date: [{ required: true, message: '请选择日期', trigger: 'change' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }]
}

const filteredSeats = computed(() => {
  if (!filterType.value) return seats.value
  return seats.value.filter(seat => seat.seatType == filterType.value)
})

const availableCount = computed(() => seats.value.filter(s => s.seatStatus === 1).length)
const occupiedCount = computed(() => seats.value.filter(s => s.seatStatus === 2).length)

const getSeatClass = (seat) => {
  switch (seat.seatStatus) {
    case 1: return 'available'
    case 2: return 'occupied'
    case 3: return 'maintenance'
    default: return ''
  }
}

const getSeatTypeName = (type) => {
  const types = {
    1: '普通座位',
    2: '电源座位',
    3: '静音区'
  }
  return types[type] || '未知'
}

const disabledDate = (date) => {
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const maxDate = new Date()
  maxDate.setDate(maxDate.getDate() + 2)
  return date < today || date > maxDate
}

const loadRoom = async () => {
  try {
    const res = await getRoomDetail(roomId)
    room.value = res.data
  } catch (error) {
    ElMessage.error('加载自习室信息失败')
  }
}

const loadSeats = async () => {
  loading.value = true
  try {
    const res = await getSeats({ roomId })
    seats.value = res.data
  } catch (error) {
    ElMessage.error('加载座位信息失败')
  } finally {
    loading.value = false
  }
}

const selectSeat = (seat) => {
  if (seat.seatStatus !== 1) {
    ElMessage.warning('该座位不可预约')
    return
  }
  selectedSeat.value = selectedSeat.value?.id === seat.id ? null : seat
}

const handleFilterChange = () => {
  selectedSeat.value = null
}

const handleReserve = () => {
  if (!selectedSeat.value) {
    ElMessage.warning('请先选择座位')
    return
  }
  
  reserveFormData.value = {
    date: dayjs().add(1, 'day').toDate(),
    startTime: '08:00',
    endTime: '12:00'
  }
  reserveDialogVisible.value = true
}

const confirmReserve = async () => {
  const valid = await reserveForm.value?.validate()
  if (!valid) return
  
  const { date, startTime, endTime } = reserveFormData.value
  
  // 验证时间逻辑
  if (startTime >= endTime) {
    ElMessage.error('结束时间必须晚于开始时间')
    return
  }
  
  const startHour = parseInt(startTime.split(':')[0])
  const endHour = parseInt(endTime.split(':')[0])
  const duration = endHour - startHour
  
  if (duration > 4) {
    ElMessage.error('单次预约最长4小时')
    return
  }
  
  reserving.value = true
  try {
    const dateStr = dayjs(date).format('YYYY-MM-DD')
    await createReservation({
      seatId: selectedSeat.value.id,
      startTime: `${dateStr} ${startTime}:00`,
      endTime: `${dateStr} ${endTime}:00`
    })
    
    ElMessage.success('预约成功')
    reserveDialogVisible.value = false
    router.push('/student/reservations')
  } catch (error) {
    ElMessage.error(error.message || '预约失败')
  } finally {
    reserving.value = false
  }
}

onMounted(() => {
  loadRoom()
  loadSeats()
})
</script>

<style lang="scss" scoped>
.room-detail {
  .page-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
    background: white;
    padding: 16px 20px;
    border-radius: 8px;
    
    h2 {
      margin: 0;
      font-size: 20px;
      color: #333;
    }
    
    .room-status {
      display: flex;
      gap: 20px;
      color: #666;
      font-size: 14px;
      
      span {
        &:first-child { color: #67c23a; }
        &:nth-child(2) { color: #e6a23c; }
      }
    }
  }
  
  .seat-selection {
    background: white;
    padding: 20px;
    border-radius: 8px;
    
    .seat-filters {
      display: flex;
      justify-content: space-between;
      margin-bottom: 20px;
    }
    
    .seat-map-container {
      .seat-legend {
        display: flex;
        gap: 20px;
        justify-content: center;
        margin-bottom: 20px;
        
        .legend-item {
          display: flex;
          align-items: center;
          gap: 8px;
          
          .seat-icon {
            width: 24px;
            height: 24px;
            border-radius: 4px;
            
            &.available { background: #67c23a; }
            &.occupied { background: #e6a23c; }
            &.selected { background: #409eff; }
            &.maintenance { background: #909399; }
          }
        }
      }
      
      .seat-map {
        min-height: 400px;
        border: 1px solid #e6e6e6;
        border-radius: 8px;
        padding: 20px;
        background: #f9f9f9;
        
        .seats-grid {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(60px, 1fr));
          gap: 10px;
          
          .seat {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.3s;
            border: 2px solid transparent;
            
            &.available {
              background: #67c23a;
              color: white;
              
              &:hover {
                transform: scale(1.1);
              }
            }
            
            &.occupied {
              background: #e6a23c;
              color: white;
              cursor: not-allowed;
            }
            
            &.maintenance {
              background: #909399;
              color: white;
              cursor: not-allowed;
            }
            
            &.selected {
              background: #409eff;
              border-color: #1976d2;
              transform: scale(1.1);
            }
            
            .seat-number {
              font-size: 12px;
              font-weight: bold;
            }
          }
        }
      }
    }
    
    .selected-info {
      margin-top: 20px;
      padding: 16px;
      background: #f5f7fa;
      border-radius: 8px;
      
      h3 {
        margin: 0 0 16px 0;
        font-size: 16px;
        color: #333;
      }
    }
  }
}
</style>