<template>
  <div class="venue-list">
    <el-card>
      <template #header>
        <div class="header">
          <span>场馆列表</span>
          <el-select v-model="venueType" placeholder="场馆类型" clearable @change="loadVenues" style="width: 150px;">
            <el-option label="篮球场" value="basketball" />
            <el-option label="羽毛球馆" value="badminton" />
            <el-option label="网球场" value="tennis" />
            <el-option label="健身房" value="gym" />
          </el-select>
        </div>
      </template>
      
      <el-row :gutter="20">
        <el-col :span="8" v-for="venue in venues" :key="venue.id">
          <el-card class="venue-card">
            <div class="venue-image">
              <img :src="venue.imageUrl || '/placeholder.png'" alt="">
            </div>
            
            <div class="venue-info">
              <h3>{{ venue.venueName }}</h3>
              <div class="info-item">
                <el-icon><MapLocation /></el-icon>
                <span>{{ venue.location }}</span>
              </div>
              <div class="info-item">
                <el-icon><User /></el-icon>
                <span>容纳人数: {{ venue.capacity }}人</span>
              </div>
              <div class="info-item">
                <el-icon><Clock /></el-icon>
                <span>{{ venue.openingTime }} - {{ venue.closingTime }}</span>
              </div>
              <div class="info-item">
                <el-icon><Money /></el-icon>
                <span class="price">¥{{ venue.pricePerHour }}/小时</span>
              </div>
              <div class="facilities">
                {{ venue.facilities }}
              </div>
            </div>
            
            <div class="venue-actions">
              <el-button type="primary" @click="handleBooking(venue)">
                立即预约
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <el-empty v-if="!venues.length" description="暂无场馆" />
    </el-card>
    
    <el-dialog v-model="dialogVisible" title="预约场馆" width="500px">
      <el-form :model="bookingForm" label-width="100px">
        <el-form-item label="场馆名称">
          <el-input v-model="currentVenue.venueName" disabled />
        </el-form-item>
        
        <el-form-item label="预约日期">
          <el-date-picker
            v-model="bookingForm.bookingDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
            :disabled-date="disabledDate"
          />
        </el-form-item>
        
        <el-form-item label="开始时间">
          <el-time-picker
            v-model="bookingForm.startTime"
            format="HH:mm:ss"
            value-format="HH:mm:ss"
            placeholder="选择时间"
          />
        </el-form-item>
        
        <el-form-item label="结束时间">
          <el-time-picker
            v-model="bookingForm.endTime"
            format="HH:mm:ss"
            value-format="HH:mm:ss"
            placeholder="选择时间"
          />
        </el-form-item>
        
        <el-form-item label="同伴人数">
          <el-input-number v-model="bookingForm.companionCount" :min="0" :max="20" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleConfirmBooking" :loading="loading">
          确认预约
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getVenueList } from '@/api/venue'
import { createBooking } from '@/api/venue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const venues = ref([])
const venueType = ref('')
const dialogVisible = ref(false)

const currentVenue = ref({})
const bookingForm = reactive({
  venueId: null,
  bookingDate: '',
  startTime: '',
  endTime: '',
  companionCount: 0
})

const disabledDate = (time) => {
  return time.getTime() < Date.now() - 8.64e7
}

const loadVenues = async () => {
  try {
    const params = { page: 1, size: 100 }
    if (venueType.value) {
      params.venueType = venueType.value
    }
    const res = await getVenueList(params)
    venues.value = res.data.records || []
  } catch (error) {
    console.error(error)
  }
}

const handleBooking = (venue) => {
  currentVenue.value = venue
  bookingForm.venueId = venue.id
  bookingForm.bookingDate = new Date().toISOString().split('T')[0]
  bookingForm.startTime = ''
  bookingForm.endTime = ''
  bookingForm.companionCount = 0
  dialogVisible.value = true
}

const handleConfirmBooking = async () => {
  if (!bookingForm.bookingDate || !bookingForm.startTime || !bookingForm.endTime) {
    ElMessage.warning('请填写完整的预约信息')
    return
  }
  
  loading.value = true
  try {
    await createBooking(bookingForm)
    ElMessage.success('预约成功')
    dialogVisible.value = false
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadVenues()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.venue-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.venue-card:hover {
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.venue-image {
  width: 100%;
  height: 180px;
  overflow: hidden;
  border-radius: 8px;
  margin-bottom: 15px;
  background-color: #f5f5f5;
}

.venue-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.venue-info h3 {
  margin: 0 0 10px;
  font-size: 18px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  font-size: 14px;
  color: #666;
}

.price {
  color: #f5222d;
  font-weight: bold;
  font-size: 16px;
}

.facilities {
  margin-top: 10px;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
  font-size: 13px;
  color: #999;
}

.venue-actions {
  margin-top: 15px;
}

.venue-actions el-button {
  width: 100%;
}
</style>

