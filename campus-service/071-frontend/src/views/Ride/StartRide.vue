<template>
  <div>
    <el-card>
      <template #header>选择站点和单车</template>
      <el-form label-width="100px" style="max-width:600px">
        <el-form-item label="选择站点">
          <el-select v-model="selectedStation" placeholder="请选择站点" style="width:100%" filterable @change="loadBikes">
            <el-option v-for="s in stations" :key="s.id" :label="`${s.name} (可用: ${s.currentCount}辆)`" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="选择单车" v-if="bikes.length > 0">
          <el-select v-model="selectedBike" placeholder="请选择单车" style="width:100%">
            <el-option v-for="b in bikes" :key="b.id" :label="`${b.bikeNo} - ${b.type === 1 ? '普通单车' : '电动单车'} ${b.type === 2 ? '(电量:' + b.batteryLevel + '%)' : ''}`" :value="b.id" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="bikes.length === 0 && selectedStation"><el-empty description="该站点暂无可用单车" :image-size="80" /></el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" @click="handleStart" :disabled="!selectedBike" :loading="loading">开始骑行</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getAllStations, getAvailableBikes, startRide } from '../../api'

const router = useRouter()
const stations = ref([])
const bikes = ref([])
const selectedStation = ref(null)
const selectedBike = ref(null)
const loading = ref(false)

onMounted(async () => { const res = await getAllStations(); stations.value = res.data })

const loadBikes = async (stationId) => {
  selectedBike.value = null
  const res = await getAvailableBikes(stationId)
  bikes.value = res.data
}

const handleStart = async () => {
  loading.value = true
  try {
    await startRide({ bikeId: selectedBike.value, stationId: selectedStation.value })
    ElMessage.success('骑行开始！')
    router.push('/ride/status')
  } finally { loading.value = false }
}
</script>
