<template>
  <div class="pickup-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">取件核销</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-input
        v-model="pickupCode"
        placeholder="请输入取件码"
        size="large"
        @keyup.enter="handleVerify"
        style="margin-bottom: 20px"
      >
        <template #append>
          <el-button @click="handleVerify" :loading="loading">验证</el-button>
        </template>
      </el-input>

      <el-empty v-if="!expressInfo" description="请输入取件码查询快递信息" />

      <div v-else class="express-detail">
        <el-descriptions title="快递信息" :column="2" border>
          <el-descriptions-item label="快递单号">{{ expressInfo.trackingNumber }}</el-descriptions-item>
          <el-descriptions-item label="快递公司">{{ expressInfo.expressCompany }}</el-descriptions-item>
          <el-descriptions-item label="取件码">
            <el-tag type="success" size="large">{{ expressInfo.pickupCode }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="收件人">{{ expressInfo.recipientName }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ expressInfo.recipientPhone }}</el-descriptions-item>
          <el-descriptions-item label="代收点">{{ getStationName(expressInfo.stationId) }}</el-descriptions-item>
          <el-descriptions-item label="货架位置">{{ expressInfo.shelfLocation }}</el-descriptions-item>
          <el-descriptions-item label="入库时间">{{ expressInfo.inTime }}</el-descriptions-item>
          <el-descriptions-item label="超期天数">
            <el-tag :type="expressInfo.overdueDays === 0 ? 'success' : 'warning'">
              {{ expressInfo.overdueDays === 0 ? '未超期' : `${expressInfo.overdueDays}天` }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="超期费用">
            <span :style="{ color: expressInfo.overdueFee > 0 ? 'red' : 'green', fontWeight: 'bold', fontSize: '18px' }">
              ¥{{ expressInfo.overdueFee }}
            </span>
          </el-descriptions-item>
        </el-descriptions>

        <el-alert
          v-if="expressInfo.overdueFee > 0"
          title="注意：该快递已超期，请先收取超期费用后再核销"
          type="warning"
          :closable="false"
          style="margin-top: 20px"
        />

        <div style="margin-top: 20px; text-align: center">
          <el-button type="primary" size="large" @click="handlePickup" :loading="pickupLoading">
            确认取件
          </el-button>
          <el-button size="large" @click="handleReset">
            重新查询
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { verifyPickup, pickup } from '@/api/express'
import { getAllStations } from '@/api/station'

const loading = ref(false)
const pickupLoading = ref(false)
const pickupCode = ref('')
const expressInfo = ref(null)
const stationList = ref([])

const getStationName = (stationId) => {
  const station = stationList.value.find(s => s.id === stationId)
  return station ? station.name : '-'
}

const handleVerify = async () => {
  if (!pickupCode.value) {
    ElMessage.warning('请输入取件码')
    return
  }
  
  loading.value = true
  try {
    const res = await verifyPickup(pickupCode.value)
    expressInfo.value = res.data
  } catch (error) {
    console.error(error)
    expressInfo.value = null
  } finally {
    loading.value = false
  }
}

const handlePickup = async () => {
  try {
    await ElMessageBox.confirm('确认核销该快递吗？', '提示', {
      type: 'warning'
    })
    
    pickupLoading.value = true
    await pickup(pickupCode.value)
    ElMessage.success('取件成功')
    handleReset()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  } finally {
    pickupLoading.value = false
  }
}

const handleReset = () => {
  pickupCode.value = ''
  expressInfo.value = null
}

const loadData = async () => {
  try {
    const res = await getAllStations()
    stationList.value = res.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.pickup-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
  max-width: 800px;
}

.express-detail {
  margin-top: 20px;
}
</style>

