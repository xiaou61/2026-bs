<template>
  <div>
    <el-card v-if="order">
      <template #header>骑行中</template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="车辆编号">{{ order.bikeNo }}</el-descriptions-item>
        <el-descriptions-item label="出发站点">{{ order.startStationName }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ order.startTime }}</el-descriptions-item>
        <el-descriptions-item label="骑行时长">{{ rideDuration }} 分钟</el-descriptions-item>
      </el-descriptions>
      <div style="margin-top:20px">
        <el-form label-width="100px" style="max-width:500px">
          <el-form-item label="还车站点">
            <el-select v-model="endStationId" placeholder="请选择还车站点" style="width:100%" filterable>
              <el-option v-for="s in stations" :key="s.id" :label="s.name" :value="s.id" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" size="large" @click="handleEnd" :disabled="!endStationId" :loading="loading">结束骑行</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card v-else>
      <el-empty description="当前没有进行中的骑行">
        <el-button type="primary" @click="$router.push('/ride/start')">去骑行</el-button>
      </el-empty>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { getCurrentRide, endRide, getAllStations } from '../../api'

const order = ref(null)
const stations = ref([])
const endStationId = ref(null)
const loading = ref(false)
const now = ref(Date.now())
let timer = null

const rideDuration = computed(() => {
  if (!order.value) return 0
  return Math.max(1, Math.floor((now.value - new Date(order.value.startTime).getTime()) / 60000))
})

onMounted(async () => {
  const res = await getCurrentRide()
  order.value = res.data
  const stRes = await getAllStations()
  stations.value = stRes.data
  timer = setInterval(() => { now.value = Date.now() }, 1000)
})

onUnmounted(() => { if (timer) clearInterval(timer) })

const handleEnd = async () => {
  loading.value = true
  try {
    const res = await endRide({ endStationId: endStationId.value })
    ElMessage.success(`骑行结束！费用: ¥${res.data.amount}`)
    order.value = null
  } finally { loading.value = false }
}
</script>
