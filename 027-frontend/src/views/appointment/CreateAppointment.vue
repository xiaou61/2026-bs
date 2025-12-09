<template>
  <div class="create-appointment-page">
    <el-card>
      <h2>创建预约</h2>

      <el-steps :active="currentStep" finish-status="success" style="margin: 30px 0">
        <el-step title="选择服务" />
        <el-step title="选择理发师" />
        <el-step title="选择时间" />
        <el-step title="确认预约" />
      </el-steps>

      <!-- 步骤1：选择服务 -->
      <div v-show="currentStep === 0">
        <h3>选择服务项目</h3>
        <el-radio-group v-model="selectedService">
          <el-row :gutter="20">
            <el-col :span="8" v-for="service in services" :key="service.id">
              <el-card shadow="hover" class="service-card">
                <el-radio :label="service.id">
                  <h4>{{ service.name }}</h4>
                  <p>{{ service.category }} - ¥{{ service.price }}</p>
                  <p>{{ service.duration }}分钟</p>
                  <p class="desc">{{ service.description }}</p>
                </el-radio>
              </el-card>
            </el-col>
          </el-row>
        </el-radio-group>
      </div>

      <!-- 步骤2：选择理发师 -->
      <div v-show="currentStep === 1">
        <h3>选择理发师</h3>
        <el-radio-group v-model="selectedHairdresser">
          <el-row :gutter="20">
            <el-col :span="6" v-for="hairdresser in hairdressers" :key="hairdresser.id">
              <el-card shadow="hover" class="hairdresser-card">
                <el-radio :label="hairdresser.id">
                  <div class="hairdresser-info">
                    <el-avatar :size="60" :src="hairdresser.avatar" />
                    <h4>{{ hairdresser.name }}</h4>
                    <el-rate v-model="hairdresser.rating" disabled show-score />
                    <p>{{ hairdresser.workYears }}年经验</p>
                  </div>
                </el-radio>
              </el-card>
            </el-col>
          </el-row>
        </el-radio-group>
      </div>

      <!-- 步骤3：选择日期时间 -->
      <div v-show="currentStep === 2">
        <h3>选择预约时间</h3>
        <el-date-picker
          v-model="selectedDate"
          type="date"
          placeholder="选择日期"
          :disabled-date="disabledDate"
          @change="loadAvailableTimeSlots"
          style="margin-bottom: 20px"
        />

        <div v-if="selectedDate">
          <h4>可预约时段</h4>
          <el-radio-group v-model="selectedTime">
            <el-radio-button
              v-for="time in availableTimeSlots"
              :key="time"
              :label="time"
            >
              {{ time }}
            </el-radio-button>
          </el-radio-group>
        </div>
      </div>

      <!-- 步骤4：确认预约 -->
      <div v-show="currentStep === 3">
        <h3>确认预约信息</h3>
        <el-descriptions :column="1" border>
          <el-descriptions-item label="服务项目">
            {{ getServiceName(selectedService) }}
          </el-descriptions-item>
          <el-descriptions-item label="理发师">
            {{ getHairdresserName(selectedHairdresser) }}
          </el-descriptions-item>
          <el-descriptions-item label="预约日期">
            {{ selectedDate }}
          </el-descriptions-item>
          <el-descriptions-item label="预约时间">
            {{ selectedTime }}
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <!-- 操作按钮 -->
      <div style="margin-top: 30px; text-align: center">
        <el-button v-if="currentStep > 0" @click="currentStep--">上一步</el-button>
        <el-button
          v-if="currentStep < 3"
          type="primary"
          @click="nextStep"
          :disabled="!canGoNext"
        >
          下一步
        </el-button>
        <el-button
          v-if="currentStep === 3"
          type="primary"
          @click="submitAppointment"
          :loading="submitting"
        >
          确认预约
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getStoreServices, getStoreHairdressers } from '@/api/store'
import { getAvailableTimeSlots, createAppointment } from '@/api/appointment'

const route = useRoute()
const router = useRouter()

const currentStep = ref(0)
const services = ref([])
const hairdressers = ref([])
const availableTimeSlots = ref([])

const selectedService = ref(null)
const selectedHairdresser = ref(null)
const selectedDate = ref(null)
const selectedTime = ref(null)
const submitting = ref(false)

const storeId = route.query.storeId
const defaultHairdresserId = route.query.hairdresserId

// 是否可以进入下一步
const canGoNext = computed(() => {
  switch (currentStep.value) {
    case 0: return selectedService.value != null
    case 1: return selectedHairdresser.value != null
    case 2: return selectedDate.value != null && selectedTime.value != null
    default: return false
  }
})

// 禁用过去的日期和超过7天的日期
const disabledDate = (time) => {
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const maxDate = new Date()
  maxDate.setDate(maxDate.getDate() + 7)
  return time < today || time > maxDate
}

// 加载服务项目
const loadServices = async () => {
  const res = await getStoreServices(storeId)
  services.value = res.data
}

// 加载理发师
const loadHairdressers = async () => {
  const res = await getStoreHairdressers(storeId)
  hairdressers.value = res.data
  
  // 如果有默认理发师，自动选中
  if (defaultHairdresserId) {
    selectedHairdresser.value = parseInt(defaultHairdresserId)
  }
}

// 加载可预约时段
const loadAvailableTimeSlots = async () => {
  if (!selectedDate.value || !selectedHairdresser.value) return

  const dateStr = selectedDate.value.toISOString().split('T')[0]
  const res = await getAvailableTimeSlots(selectedHairdresser.value, dateStr)
  availableTimeSlots.value = res.data
}

// 下一步
const nextStep = () => {
  if (canGoNext.value) {
    currentStep.value++
  }
}

// 获取服务名称
const getServiceName = (id) => {
  const service = services.value.find(s => s.id === id)
  return service ? `${service.name} - ¥${service.price}` : ''
}

// 获取理发师名称
const getHairdresserName = (id) => {
  const hairdresser = hairdressers.value.find(h => h.id === id)
  return hairdresser?.name || ''
}

// 提交预约
const submitAppointment = async () => {
  submitting.value = true
  try {
    await createAppointment({
      storeId: parseInt(storeId),
      hairdresserId: selectedHairdresser.value,
      serviceId: selectedService.value,
      appointmentDate: selectedDate.value.toISOString().split('T')[0],
      appointmentTime: selectedTime.value
    })
    
    ElMessage.success('预约成功，请等待门店确认')
    router.push('/appointment/my')
  } catch (error) {
    console.error('预约失败：', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadServices()
  loadHairdressers()
})
</script>

<style scoped>
.create-appointment-page {
  max-width: 1000px;
  margin: 30px auto;
  padding: 0 20px;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
}

h3 {
  margin: 20px 0;
}

.service-card,
.hairdresser-card {
  margin-bottom: 20px;
  cursor: pointer;
}

.service-card h4,
.hairdresser-card h4 {
  margin: 10px 0;
}

.service-card .desc {
  color: #666;
  font-size: 14px;
  margin-top: 5px;
}

.hairdresser-info {
  text-align: center;
}

.hairdresser-info h4 {
  margin: 10px 0;
}
</style>
