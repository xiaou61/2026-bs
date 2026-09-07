<template>
  <div class="page-container">
    <el-card v-loading="loading">
      <h2>{{ counselor.realName }}</h2>
      <p>擅长领域：{{ counselor.specialties }}</p>
      <p>咨询风格：{{ counselor.style }}</p>
      <p>个人简介：{{ counselor.introduction }}</p>
      <p>价格：￥{{ counselor.price }}/次</p>
      <p>评分：⭐{{ counselor.rating }} | 咨询次数：{{ counselor.consultCount }}</p>

      <el-divider />
      
      <h3>可预约时段</h3>
      <el-radio-group v-model="selectedSlot">
        <el-radio v-for="slot in timeSlots" :key="slot.id" :value="slot.id" style="display: block; margin: 10px 0">
          {{ slot.slotDate }} {{ slot.slotTime }} ({{ slot.duration }}分钟)
        </el-radio>
      </el-radio-group>

      <el-button type="primary" @click="handleBook" :disabled="!selectedSlot" style="margin-top: 20px">
        预约咨询
      </el-button>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getCounselorById, getTimeSlots } from '@/api/counselor'
import { createAppointment } from '@/api/appointment'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const counselor = ref({})
const timeSlots = ref([])
const selectedSlot = ref(null)

const loadData = async () => {
  loading.value = true
  try {
    const counselorRes = await getCounselorById(route.params.id)
    counselor.value = counselorRes.data
    
    const slotsRes = await getTimeSlots(route.params.id)
    timeSlots.value = slotsRes.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const handleBook = async () => {
  try {
    await createAppointment({
      counselorId: route.params.id,
      timeSlotId: selectedSlot.value,
      price: counselor.value.price
    })
    ElMessage.success('预约成功')
    router.push('/appointments/my')
  } catch (error) {
    ElMessage.error('预约失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.el-card {
  max-width: 800px;
  margin: 0 auto;
}

h2 {
  margin-bottom: 20px;
}

p {
  margin: 10px 0;
  line-height: 1.6;
}
</style>
