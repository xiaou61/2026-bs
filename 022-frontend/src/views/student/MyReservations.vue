<template>
  <div class="my-reservations">
    <div class="page-header">
      <h2>我的预约记录</h2>
      <el-button type="primary" @click="router.push('/student/rooms')">
        新建预约
      </el-button>
    </div>
    
    <div class="filter-bar">
      <el-radio-group v-model="statusFilter" @change="loadReservations">
        <el-radio-button label="">全部</el-radio-button>
        <el-radio-button label="1">已预约</el-radio-button>
        <el-radio-button label="2">已签到</el-radio-button>
        <el-radio-button label="3">已完成</el-radio-button>
        <el-radio-button label="4">已取消</el-radio-button>
        <el-radio-button label="5">违约</el-radio-button>
      </el-radio-group>
      
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        @change="loadReservations"
        style="width: 250px"
      />
    </div>
    
    <div v-loading="loading" class="reservations-list">
      <div v-for="reservation in reservations" :key="reservation.id" class="reservation-card">
        <div class="card-header">
          <div class="room-info">
            <h3>{{ reservation.roomName }}</h3>
            <span class="seat-number">座位: {{ reservation.seatNumber }}</span>
          </div>
          <el-tag :type="getStatusType(reservation.status)">
            {{ getStatusText(reservation.status) }}
          </el-tag>
        </div>
        
        <div class="card-body">
          <div class="time-info">
            <div class="time-item">
              <Clock style="width: 16px; height: 16px;" />
              <span>预约时间: {{ formatTime(reservation.startTime) }} - {{ formatTime(reservation.endTime, 'HH:mm') }}</span>
            </div>
            <div v-if="reservation.checkInTime" class="time-item">
              <Check style="width: 16px; height: 16px;" />
              <span>签到时间: {{ formatTime(reservation.checkInTime) }}</span>
            </div>
          </div>
        </div>
        
        <div class="card-footer">
          <el-button
            v-if="reservation.status === 1"
            type="primary"
            size="small"
            @click="handleCheckIn(reservation)"
          >
            签到
          </el-button>
          <el-button
            v-if="reservation.status === 2"
            type="success"
            size="small"
            @click="handleEnd(reservation)"
          >
            结束使用
          </el-button>
          <el-button
            v-if="reservation.status === 1"
            type="danger"
            size="small"
            @click="handleCancel(reservation)"
          >
            取消预约
          </el-button>
          <el-button size="small" @click="showDetail(reservation)">
            查看详情
          </el-button>
        </div>
      </div>
      
      <el-empty v-if="!loading && reservations.length === 0" description="暂无预约记录" />
    </div>
    
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyReservations, checkIn, endReservation, cancelReservation } from '@/api/reservation'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'

const router = useRouter()

const loading = ref(false)
const reservations = ref([])
const statusFilter = ref('')
const dateRange = ref(null)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const formatTime = (time, format = 'YYYY-MM-DD HH:mm') => {
  return dayjs(time).format(format)
}

const getStatusType = (status) => {
  const types = {
    1: 'warning',
    2: 'success',
    3: 'info',
    4: 'info',
    5: 'danger'
  }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = {
    1: '已预约',
    2: '已签到',
    3: '已完成',
    4: '已取消',
    5: '违约'
  }
  return texts[status] || '未知'
}

const loadReservations = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      size: pageSize.value
    }
    
    if (statusFilter.value) {
      params.status = statusFilter.value
    }
    
    if (dateRange.value && dateRange.value.length === 2) {
      params.startDate = dayjs(dateRange.value[0]).format('YYYY-MM-DD')
      params.endDate = dayjs(dateRange.value[1]).format('YYYY-MM-DD')
    }
    
    const res = await getMyReservations(params)
    reservations.value = res.data.records || res.data
    total.value = res.data.total || res.data.length
  } catch (error) {
    ElMessage.error('加载预约记录失败')
  } finally {
    loading.value = false
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadReservations()
}

const handleCheckIn = async (reservation) => {
  try {
    await checkIn(reservation.id)
    ElMessage.success('签到成功')
    loadReservations()
  } catch (error) {
    ElMessage.error(error.message || '签到失败')
  }
}

const handleEnd = async (reservation) => {
  await ElMessageBox.confirm('确定要结束使用吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  
  try {
    await endReservation(reservation.id)
    ElMessage.success('已结束使用')
    loadReservations()
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

const handleCancel = async (reservation) => {
  await ElMessageBox.confirm('确定要取消预约吗？取消可能会扣除信用分', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  
  try {
    await cancelReservation(reservation.id)
    ElMessage.success('已取消预约')
    loadReservations()
  } catch (error) {
    ElMessage.error(error.message || '取消失败')
  }
}

const showDetail = (reservation) => {
  router.push(`/student/reservation/${reservation.id}`)
}

onMounted(() => {
  loadReservations()
})
</script>

<style lang="scss" scoped>
.my-reservations {
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
  }
  
  .filter-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding: 16px;
    background: white;
    border-radius: 8px;
  }
  
  .reservations-list {
    min-height: 400px;
  }
  
  .reservation-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 16px;
    border: 1px solid #e6e6e6;
    transition: all 0.3s;
    
    &:hover {
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    }
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
      
      .room-info {
        h3 {
          margin: 0 0 4px 0;
          font-size: 18px;
          color: #333;
        }
        
        .seat-number {
          color: #666;
          font-size: 14px;
        }
      }
    }
    
    .card-body {
      .time-info {
        display: flex;
        flex-direction: column;
        gap: 8px;
        margin-bottom: 16px;
        
        .time-item {
          display: flex;
          align-items: center;
          gap: 8px;
          color: #666;
          font-size: 14px;
        }
      }
    }
    
    .card-footer {
      display: flex;
      gap: 10px;
      padding-top: 16px;
      border-top: 1px solid #f0f0f0;
    }
  }
}
</style>