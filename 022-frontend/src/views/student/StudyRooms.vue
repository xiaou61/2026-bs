<template>
  <div class="study-rooms">
    <div class="page-header">
      <h2>自习室列表</h2>
      <div class="header-actions">
        <el-input 
          v-model="searchText" 
          placeholder="搜索自习室"
          prefix-icon="Search"
          style="width: 200px; margin-right: 10px;"
          @input="handleSearch"
        />
        <el-select 
          v-model="filterFloor" 
          placeholder="选择楼层"
          clearable
          style="width: 120px;"
          @change="handleFilter"
        >
          <el-option label="1楼" :value="1" />
          <el-option label="2楼" :value="2" />
          <el-option label="3楼" :value="3" />
          <el-option label="4楼" :value="4" />
          <el-option label="5楼" :value="5" />
        </el-select>
      </div>
    </div>
    
    <div v-loading="loading" class="rooms-grid">
      <div 
        v-for="room in filteredRooms" 
        :key="room.id"
        class="room-card"
        @click="goToRoomDetail(room.id)"
      >
        <div class="room-header">
          <h3>{{ room.roomName }}</h3>
          <el-tag :type="room.status === 1 ? 'success' : 'info'">
            {{ room.status === 1 ? '开放' : '关闭' }}
          </el-tag>
        </div>
        
        <div class="room-info">
          <div class="info-item">
            <Location style="width: 16px; height: 16px;" />
            <span>{{ room.floorNumber }}楼</span>
          </div>
          <div class="info-item">
            <Clock style="width: 16px; height: 16px;" />
            <span>{{ room.openTime }} - {{ room.closeTime }}</span>
          </div>
        </div>
        
        <div class="room-stats">
          <div class="stat">
            <div class="stat-value">{{ room.capacity }}</div>
            <div class="stat-label">总座位</div>
          </div>
          <div class="stat">
            <div class="stat-value" style="color: #67c23a;">{{ room.availableSeats || 0 }}</div>
            <div class="stat-label">空闲</div>
          </div>
          <div class="stat">
            <div class="stat-value" style="color: #e6a23c;">{{ room.occupiedSeats || 0 }}</div>
            <div class="stat-label">占用</div>
          </div>
        </div>
        
        <div class="room-description">
          {{ room.description || '舒适的学习环境，欢迎预约' }}
        </div>
        
        <el-button 
          type="primary" 
          :disabled="room.status !== 1 || !room.availableSeats"
          @click.stop="goToRoomDetail(room.id)"
        >
          {{ room.availableSeats ? '选择座位' : '暂无空位' }}
        </el-button>
      </div>
    </div>
    
    <el-empty v-if="!loading && filteredRooms.length === 0" description="暂无自习室" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getStudyRooms } from '@/api/studyRoom'
import { getSeats } from '@/api/seat'
import { ElMessage } from 'element-plus'

const router = useRouter()

const loading = ref(false)
const rooms = ref([])
const searchText = ref('')
const filterFloor = ref('')

const filteredRooms = computed(() => {
  let result = rooms.value
  
  if (searchText.value) {
    result = result.filter(room => 
      room.roomName.toLowerCase().includes(searchText.value.toLowerCase())
    )
  }
  
  if (filterFloor.value) {
    result = result.filter(room => room.floorNumber === filterFloor.value)
  }
  
  return result
})

const loadRooms = async () => {
  loading.value = true
  try {
    const res = await getStudyRooms()
    rooms.value = res.data
    
    // 加载每个房间的座位统计
    for (const room of rooms.value) {
      try {
        const seatsRes = await getSeats({ roomId: room.id })
        const seats = seatsRes.data
        room.availableSeats = seats.filter(s => s.seatStatus === 1).length
        room.occupiedSeats = seats.filter(s => s.seatStatus === 2).length
      } catch (error) {
        console.error(`Failed to load seats for room ${room.id}:`, error)
      }
    }
  } catch (error) {
    ElMessage.error('加载自习室列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  // 搜索逻辑已通过 computed 实现
}

const handleFilter = () => {
  // 筛选逻辑已通过 computed 实现
}

const goToRoomDetail = (roomId) => {
  router.push(`/student/room/${roomId}`)
}

onMounted(() => {
  loadRooms()
})
</script>

<style lang="scss" scoped>
.study-rooms {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    
    h2 {
      margin: 0;
      font-size: 20px;
      color: #333;
    }
    
    .header-actions {
      display: flex;
      align-items: center;
    }
  }
  
  .rooms-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }
  
  .room-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    cursor: pointer;
    transition: all 0.3s;
    border: 1px solid #e6e6e6;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    
    .room-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
      
      h3 {
        margin: 0;
        font-size: 18px;
        color: #333;
      }
    }
    
    .room-info {
      display: flex;
      gap: 20px;
      margin-bottom: 16px;
      
      .info-item {
        display: flex;
        align-items: center;
        gap: 4px;
        color: #666;
        font-size: 14px;
      }
    }
    
    .room-stats {
      display: flex;
      justify-content: space-around;
      padding: 16px 0;
      border-top: 1px solid #f0f0f0;
      border-bottom: 1px solid #f0f0f0;
      margin-bottom: 16px;
      
      .stat {
        text-align: center;
        
        .stat-value {
          font-size: 24px;
          font-weight: bold;
          margin-bottom: 4px;
        }
        
        .stat-label {
          font-size: 12px;
          color: #999;
        }
      }
    }
    
    .room-description {
      color: #666;
      font-size: 14px;
      margin-bottom: 16px;
      line-height: 1.5;
    }
  }
}
</style>