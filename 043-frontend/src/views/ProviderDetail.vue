<template>
  <div class="provider-detail" v-loading="loading">
    <el-card v-if="provider">
      <template #header>
        <div class="header">
          <el-button @click="router.back()"><el-icon><Back /></el-icon>返回</el-button>
          <h2>{{ provider.name }}</h2>
        </div>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="评分">
          <el-rate v-model="provider.rating" disabled show-score />
        </el-descriptions-item>
        <el-descriptions-item label="地址">{{ provider.address }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ provider.phone }}</el-descriptions-item>
        <el-descriptions-item label="营业时间">{{ provider.businessHours }}</el-descriptions-item>
        <el-descriptions-item label="简介" :span="2">{{ provider.description }}</el-descriptions-item>
      </el-descriptions>

      <h3 style="margin: 20px 0 10px">服务项目</h3>
      <el-table :data="services">
        <el-table-column prop="name" label="服务名称" />
        <el-table-column prop="description" label="服务描述" />
        <el-table-column prop="price" label="价格" width="120">
          <template #default="{ row }">¥{{ row.price }}/天</template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button type="primary" @click="showBooking(row)">预约</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="bookingVisible" title="预约寄养" width="500">
      <el-form :model="bookingForm" :rules="bookingRules" ref="bookingFormRef" label-width="80px">
        <el-form-item label="选择宠物" prop="petId">
          <el-select v-model="bookingForm.petId" placeholder="请选择宠物" style="width: 100%">
            <el-option v-for="pet in petList" :key="pet.id" :label="pet.name" :value="pet.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="寄养时间" prop="dateRange">
          <el-date-picker
            v-model="bookingForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :disabled-date="(date: Date) => date < new Date()"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="服务项目">
          {{ selectedService?.name }} - ¥{{ selectedService?.price }}/天
        </el-form-item>
        <el-form-item label="预估费用">
          <span style="color: #f56c6c; font-size: 20px">¥{{ estimatedPrice }}</span>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="bookingForm.remark" type="textarea" placeholder="特殊需求说明" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="bookingVisible = false">取消</el-button>
        <el-button type="primary" @click="handleBooking" :loading="submitLoading">确认预约</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getProviderDetail, getProviderServices, getPetList, createBooking } from '../api'
import { Back } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const provider = ref<any>(null)
const services = ref<any[]>([])
const petList = ref<any[]>([])
const bookingVisible = ref(false)
const submitLoading = ref(false)
const bookingFormRef = ref()
const selectedService = ref<any>(null)

const bookingForm = reactive({
  petId: null as number | null,
  dateRange: [] as Date[],
  remark: ''
})

const bookingRules = {
  petId: [{ required: true, message: '请选择宠物', trigger: 'change' }],
  dateRange: [{ required: true, message: '请选择寄养时间', trigger: 'change' }]
}

const estimatedPrice = computed(() => {
  if (!selectedService.value || !bookingForm.dateRange?.length) return 0
  const days = Math.ceil((bookingForm.dateRange[1].getTime() - bookingForm.dateRange[0].getTime()) / (1000 * 60 * 60 * 24)) + 1
  return (days * selectedService.value.price).toFixed(2)
})

const loadData = async () => {
  loading.value = true
  try {
    const id = Number(route.params.id)
    const [providerRes, servicesRes, petRes]: any[] = await Promise.all([
      getProviderDetail(id),
      getProviderServices(id),
      getPetList()
    ])
    provider.value = providerRes.data
    services.value = servicesRes.data || []
    petList.value = petRes.data || []
  } finally {
    loading.value = false
  }
}

const showBooking = (service: any) => {
  if (!petList.value.length) {
    ElMessage.warning('请先添加宠物')
    router.push('/pets')
    return
  }
  selectedService.value = service
  bookingForm.petId = null
  bookingForm.dateRange = []
  bookingForm.remark = ''
  bookingVisible.value = true
}

const handleBooking = async () => {
  await bookingFormRef.value.validate()
  submitLoading.value = true
  try {
    await createBooking({
      petId: bookingForm.petId,
      providerId: provider.value.id,
      serviceId: selectedService.value.id,
      startDate: bookingForm.dateRange[0].toISOString().split('T')[0],
      endDate: bookingForm.dateRange[1].toISOString().split('T')[0],
      remark: bookingForm.remark
    })
    ElMessage.success('预约成功')
    bookingVisible.value = false
    router.push('/bookings')
  } finally {
    submitLoading.value = false
  }
}

onMounted(() => loadData())
</script>

<style scoped>
.provider-detail {
  padding: 20px;
}
.header {
  display: flex;
  align-items: center;
  gap: 20px;
}
.header h2 {
  margin: 0;
}
</style>
