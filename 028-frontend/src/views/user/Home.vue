<template>
  <div class="home-page">
    <!-- 用户信息卡片 -->
    <el-card class="user-card">
      <div class="user-info">
        <el-avatar :size="60" :src="userInfo.avatar">
          <el-icon><User /></el-icon>
        </el-avatar>
        <div class="info">
          <h3>{{ userInfo.realName || userInfo.username }}</h3>
          <p>信用分: {{ userInfo.creditScore }} | 余额: ¥{{ userInfo.balance || '0.00' }}</p>
        </div>
      </div>
    </el-card>

    <!-- 当前订单 -->
    <el-card v-if="currentOrder" class="current-order-card">
      <template #header>
        <div class="flex-between">
          <span>当前骑行中</span>
          <el-tag type="warning">进行中</el-tag>
        </div>
      </template>
      <div class="order-info">
        <p><strong>订单号:</strong> {{ currentOrder.orderNo }}</p>
        <p><strong>车辆编号:</strong> {{ currentOrder.bikeId }}</p>
        <p><strong>开始时间:</strong> {{ currentOrder.startTime }}</p>
        <p><strong>骑行时长:</strong> {{ ridingDuration }} 分钟</p>
        <el-button type="primary" @click="showReturnDialog">还车</el-button>
      </div>
    </el-card>

    <!-- 快捷操作 -->
    <el-card class="action-card">
      <template #header>快捷操作</template>
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="action-item" @click="showRentDialog">
            <el-icon class="icon"><Unlock /></el-icon>
            <span>扫码租车</span>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="action-item" @click="$router.push('/stations')">
            <el-icon class="icon"><Location /></el-icon>
            <span>查找停车点</span>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="action-item" @click="$router.push('/wallet')">
            <el-icon class="icon"><Wallet /></el-icon>
            <span>充值钱包</span>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 租车弹窗 -->
    <el-dialog v-model="rentDialogVisible" title="扫码租车" width="400px">
      <el-form :model="rentForm">
        <el-form-item label="车辆编号">
          <el-input v-model="rentForm.bikeNo" placeholder="请输入车辆编号或扫描二维码" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rentDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="rentLoading" @click="handleRent">确认租车</el-button>
      </template>
    </el-dialog>

    <!-- 还车弹窗 -->
    <el-dialog v-model="returnDialogVisible" title="确认还车" width="400px">
      <el-form :model="returnForm">
        <el-form-item label="停车点">
          <el-select v-model="returnForm.stationId" placeholder="请选择还车停车点" style="width: 100%">
            <el-option
              v-for="station in stations"
              :key="station.id"
              :label="station.stationName"
              :value="station.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="returnDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="returnLoading" @click="handleReturn">确认还车</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { userApi, rentalApi, stationApi } from '@/api'

const userInfo = ref({})
const currentOrder = ref(null)
const stations = ref([])

const rentDialogVisible = ref(false)
const returnDialogVisible = ref(false)
const rentLoading = ref(false)
const returnLoading = ref(false)

const rentForm = reactive({ bikeNo: '' })
const returnForm = reactive({ stationId: null })

const ridingDuration = computed(() => {
  if (!currentOrder.value) return 0
  const start = new Date(currentOrder.value.startTime)
  return Math.floor((Date.now() - start.getTime()) / 60000)
})

const loadUserInfo = async () => {
  const res = await userApi.getInfo()
  userInfo.value = res.data
}

const loadCurrentOrder = async () => {
  try {
    const res = await rentalApi.getCurrent()
    currentOrder.value = res.data
  } catch {
    currentOrder.value = null
  }
}

const loadStations = async () => {
  const res = await stationApi.getList()
  stations.value = res.data
}

const showRentDialog = () => {
  if (currentOrder.value) {
    ElMessage.warning('您有未完成的订单，请先还车')
    return
  }
  rentForm.bikeNo = ''
  rentDialogVisible.value = true
}

const showReturnDialog = () => {
  returnForm.stationId = null
  returnDialogVisible.value = true
}

const handleRent = async () => {
  if (!rentForm.bikeNo) {
    ElMessage.warning('请输入车辆编号')
    return
  }
  rentLoading.value = true
  try {
    await rentalApi.start(rentForm)
    ElMessage.success('租车成功')
    rentDialogVisible.value = false
    await loadCurrentOrder()
  } finally {
    rentLoading.value = false
  }
}

const handleReturn = async () => {
  if (!returnForm.stationId) {
    ElMessage.warning('请选择还车停车点')
    return
  }
  returnLoading.value = true
  try {
    await rentalApi.end({
      orderId: currentOrder.value.id,
      stationId: returnForm.stationId
    })
    ElMessage.success('还车成功')
    returnDialogVisible.value = false
    currentOrder.value = null
    await loadUserInfo()
  } finally {
    returnLoading.value = false
  }
}

onMounted(() => {
  loadUserInfo()
  loadCurrentOrder()
  loadStations()
})
</script>

<style scoped lang="scss">
.home-page {
  max-width: 800px;
  margin: 0 auto;
}

.user-card {
  margin-bottom: 20px;
  
  .user-info {
    display: flex;
    align-items: center;
    gap: 20px;
    
    .info {
      h3 { margin: 0 0 8px; }
      p { margin: 0; color: #666; }
    }
  }
}

.current-order-card {
  margin-bottom: 20px;
  
  .order-info {
    p { margin: 8px 0; }
  }
}

.action-card {
  margin-bottom: 20px;
  
  .action-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    cursor: pointer;
    border-radius: 8px;
    transition: background 0.3s;
    
    &:hover {
      background: #f5f7fa;
    }
    
    .icon {
      font-size: 32px;
      color: #409eff;
      margin-bottom: 10px;
    }
  }
}
</style>
