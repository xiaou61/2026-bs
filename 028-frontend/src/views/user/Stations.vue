<template>
  <div class="stations-page">
    <el-card>
      <template #header>校园停车点</template>
      <el-row :gutter="20">
        <el-col v-for="station in stations" :key="station.id" :xs="24" :sm="12" :md="8" :lg="6">
          <div class="station-card" @click="showStationDetail(station)">
            <div class="station-icon">
              <el-icon><Location /></el-icon>
            </div>
            <h4>{{ station.stationName }}</h4>
            <p class="address">{{ station.address }}</p>
            <div class="status">
              <el-tag :type="station.currentCount > 0 ? 'success' : 'danger'" size="small">
                可用 {{ station.currentCount }} / {{ station.capacity }}
              </el-tag>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 停车点详情弹窗 -->
    <el-dialog v-model="dialogVisible" :title="currentStation?.stationName" width="500px">
      <div v-if="currentStation" class="station-detail">
        <p><strong>地址:</strong> {{ currentStation.address }}</p>
        <p><strong>容量:</strong> {{ currentStation.capacity }} 辆</p>
        <p><strong>当前车辆:</strong> {{ currentStation.currentCount }} 辆</p>
        <p><strong>描述:</strong> {{ currentStation.description || '暂无描述' }}</p>
        <div class="bikes-list" v-if="bikes.length > 0">
          <h4>可用车辆</h4>
          <el-table :data="bikes" size="small">
            <el-table-column prop="bikeNo" label="车辆编号" />
            <el-table-column prop="bikeType" label="类型">
              <template #default="{ row }">
                {{ row.bikeType === 1 ? '普通单车' : '电动助力车' }}
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无可用车辆" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { stationApi } from '@/api'

const stations = ref([])
const currentStation = ref(null)
const bikes = ref([])
const dialogVisible = ref(false)

const loadStations = async () => {
  const res = await stationApi.getList()
  stations.value = res.data
}

const showStationDetail = async (station) => {
  currentStation.value = station
  const res = await stationApi.getBikes(station.id)
  bikes.value = res.data
  dialogVisible.value = true
}

onMounted(() => {
  loadStations()
})
</script>

<style scoped lang="scss">
.station-card {
  background: #fff;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
  
  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transform: translateY(-2px);
  }
  
  .station-icon {
    font-size: 32px;
    color: #409eff;
    margin-bottom: 10px;
  }
  
  h4 {
    margin: 0 0 8px;
    color: #333;
  }
  
  .address {
    font-size: 12px;
    color: #999;
    margin: 0 0 10px;
  }
}

.station-detail {
  p {
    margin: 10px 0;
  }
  
  .bikes-list {
    margin-top: 20px;
    
    h4 {
      margin-bottom: 10px;
    }
  }
}
</style>
