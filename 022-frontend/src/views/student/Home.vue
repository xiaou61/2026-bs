<template>
  <div class="student-home">
    <div class="welcome-card">
      <h2>欢迎回来，{{ userStore.userInfo?.realName || '同学' }}</h2>
      <p>{{ currentTime }} {{ timeGreeting }}</p>
    </div>
    
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #409eff;">
            <Reading style="width: 24px; height: 24px; color: white;" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.totalRooms }}</div>
            <div class="stat-label">自习室总数</div>
          </div>
        </div>
      </el-col>
      
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #67c23a;">
            <Position style="width: 24px; height: 24px; color: white;" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.availableSeats }}</div>
            <div class="stat-label">空闲座位</div>
          </div>
        </div>
      </el-col>
      
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #e6a23c;">
            <Calendar style="width: 24px; height: 24px; color: white;" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.myReservations }}</div>
            <div class="stat-label">我的预约</div>
          </div>
        </div>
      </el-col>
      
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #f56c6c;">
            <Trophy style="width: 24px; height: 24px; color: white;" />
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ userStore.creditScore }}</div>
            <div class="stat-label">我的信用分</div>
          </div>
        </div>
      </el-col>
    </el-row>
    
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="card">
          <div class="card-header">
            <h3>当前预约</h3>
            <el-button type="primary" size="small" @click="router.push('/student/rooms')">
              新建预约
            </el-button>
          </div>
          
          <div v-if="currentReservation" class="current-reservation">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="自习室">
                {{ currentReservation.roomName }}
              </el-descriptions-item>
              <el-descriptions-item label="座位编号">
                {{ currentReservation.seatNumber }}
              </el-descriptions-item>
              <el-descriptions-item label="预约时间">
                {{ formatTime(currentReservation.startTime) }} - {{ formatTime(currentReservation.endTime) }}
              </el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag :type="getStatusType(currentReservation.status)">
                  {{ getStatusText(currentReservation.status) }}
                </el-tag>
              </el-descriptions-item>
            </el-descriptions>
            
            <div class="reservation-actions">
              <el-button 
                v-if="currentReservation.status === 1" 
                type="primary"
                @click="handleCheckIn"
              >
                签到
              </el-button>
              <el-button 
                v-if="currentReservation.status === 2" 
                type="success"
                @click="handleEnd"
              >
                结束使用
              </el-button>
              <el-button 
                v-if="currentReservation.status === 1" 
                type="danger"
                @click="handleCancel"
              >
                取消预约
              </el-button>
              <el-button @click="showQRCode">查看二维码</el-button>
            </div>
          </div>
          
          <el-empty v-else description="暂无进行中的预约" />
        </div>
      </el-col>
      
      <el-col :span="8">
        <div class="card">
          <div class="card-header">
            <h3>快捷入口</h3>
          </div>
          
          <div class="quick-links">
            <div class="quick-link" @click="router.push('/student/rooms')">
              <Reading style="width: 32px; height: 32px;" />
              <span>预约座位</span>
            </div>
            <div class="quick-link" @click="router.push('/student/reservations')">
              <Calendar style="width: 32px; height: 32px;" />
              <span>预约记录</span>
            </div>
            <div class="quick-link" @click="router.push('/student/credit')">
              <Trophy style="width: 32px; height: 32px;" />
              <span>信用记录</span>
            </div>
            <div class="quick-link" @click="router.push('/student/profile')">
              <UserFilled style="width: 32px; height: 32px;" />
              <span>个人信息</span>
            </div>
          </div>
        </div>
        
        <div class="card" style="margin-top: 20px;">
          <div class="card-header">
            <h3>预约规则</h3>
          </div>
          
          <div class="rules">
            <ul>
              <li>可提前2天预约，最少提前1小时</li>
              <li>单次预约最长4小时</li>
              <li>预约后15分钟内须签到</li>
              <li>违约将扣除信用分</li>
              <li>信用分低于60分将限制预约</li>
            </ul>
          </div>
        </div>
      </el-col>
    </el-row>
    
    <el-dialog v-model="qrCodeVisible" title="预约二维码" width="400px">
      <div class="qrcode-container">
        <canvas ref="qrCanvas"></canvas>
        <p>请在签到时出示此二维码</p>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getMyReservations, checkIn, endReservation, cancelReservation } from '@/api/reservation'
import { getStudyRooms } from '@/api/studyRoom'
import { ElMessage, ElMessageBox } from 'element-plus'
import QRCode from 'qrcode'
import dayjs from 'dayjs'

const router = useRouter()
const userStore = useUserStore()

const currentTime = ref('')
const timeGreeting = ref('')
const stats = reactive({
  totalRooms: 0,
  availableSeats: 0,
  myReservations: 0
})
const currentReservation = ref(null)
const qrCodeVisible = ref(false)
const qrCanvas = ref()

let timer = null

const updateTime = () => {
  const now = dayjs()
  currentTime.value = now.format('YYYY-MM-DD HH:mm:ss')
  
  const hour = now.hour()
  if (hour < 6) timeGreeting.value = '深夜了，注意休息'
  else if (hour < 9) timeGreeting.value = '早上好'
  else if (hour < 12) timeGreeting.value = '上午好'
  else if (hour < 14) timeGreeting.value = '中午好'
  else if (hour < 18) timeGreeting.value = '下午好'
  else if (hour < 22) timeGreeting.value = '晚上好'
  else timeGreeting.value = '夜深了，注意休息'
}

const formatTime = (time) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
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

const loadData = async () => {
  try {
    // 获取自习室统计
    const roomsRes = await getStudyRooms()
    stats.totalRooms = roomsRes.data.length
    stats.availableSeats = roomsRes.data.reduce((sum, room) => sum + (room.availableSeats || 0), 0)
    
    // 获取我的预约
    const reservationsRes = await getMyReservations({ status: '1,2' })
    stats.myReservations = reservationsRes.data.length
    currentReservation.value = reservationsRes.data[0] || null
  } catch (error) {
    console.error('Failed to load data:', error)
  }
}

const handleCheckIn = async () => {
  try {
    await checkIn(currentReservation.value.id)
    ElMessage.success('签到成功')
    loadData()
  } catch (error) {
    ElMessage.error(error.message || '签到失败')
  }
}

const handleEnd = async () => {
  await ElMessageBox.confirm('确定要结束使用吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  
  try {
    await endReservation(currentReservation.value.id)
    ElMessage.success('已结束使用')
    loadData()
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

const handleCancel = async () => {
  await ElMessageBox.confirm('确定要取消预约吗？取消可能会扣除信用分', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  
  try {
    await cancelReservation(currentReservation.value.id)
    ElMessage.success('已取消预约')
    loadData()
  } catch (error) {
    ElMessage.error(error.message || '取消失败')
  }
}

const showQRCode = async () => {
  qrCodeVisible.value = true
  await new Promise(resolve => setTimeout(resolve, 100))
  
  if (qrCanvas.value) {
    QRCode.toCanvas(qrCanvas.value, currentReservation.value.qrcodeContent || 'reservation-' + currentReservation.value.id, {
      width: 200,
      margin: 2
    })
  }
}

onMounted(() => {
  updateTime()
  loadData()
  timer = setInterval(updateTime, 1000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})
</script>

<style lang="scss" scoped>
.student-home {
  .welcome-card {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 30px;
    border-radius: 8px;
    margin-bottom: 20px;
    
    h2 {
      margin: 0 0 10px 0;
      font-size: 24px;
    }
    
    p {
      margin: 0;
      font-size: 14px;
      opacity: 0.9;
    }
  }
  
  .stats-row {
    margin-bottom: 20px;
  }
  
  .stat-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    display: flex;
    align-items: center;
    
    .stat-icon {
      width: 48px;
      height: 48px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 16px;
    }
    
    .stat-content {
      flex: 1;
      
      .stat-value {
        font-size: 24px;
        font-weight: bold;
        color: #333;
      }
      
      .stat-label {
        font-size: 14px;
        color: #999;
        margin-top: 4px;
      }
    }
  }
  
  .card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      
      h3 {
        margin: 0;
        font-size: 18px;
        color: #333;
      }
    }
    
    .current-reservation {
      .reservation-actions {
        margin-top: 20px;
        text-align: center;
      }
    }
    
    .quick-links {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 20px;
      
      .quick-link {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 20px;
        border: 1px solid #e6e6e6;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.3s;
        
        &:hover {
          background: #f5f7fa;
          border-color: #409eff;
          color: #409eff;
        }
        
        span {
          margin-top: 8px;
          font-size: 14px;
        }
      }
    }
    
    .rules {
      ul {
        margin: 0;
        padding-left: 20px;
        
        li {
          margin-bottom: 8px;
          color: #666;
          font-size: 14px;
        }
      }
    }
  }
}

.qrcode-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  
  canvas {
    border: 1px solid #e6e6e6;
    border-radius: 4px;
  }
  
  p {
    margin-top: 16px;
    color: #666;
    font-size: 14px;
  }
}
</style>