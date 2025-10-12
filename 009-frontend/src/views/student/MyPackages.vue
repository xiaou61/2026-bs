<template>
  <div class="packages-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">我的待取快递</span>
      </template>
    </el-page-header>

    <el-card class="content-card" v-loading="loading">
      <el-empty v-if="packageList.length === 0" description="暂无待取快递" />
      
      <el-row :gutter="20" v-else>
        <el-col :span="8" v-for="item in packageList" :key="item.id">
          <el-card class="package-card" shadow="hover">
            <div class="package-info">
              <div class="info-row">
                <el-tag type="success" size="large">{{ item.pickupCode }}</el-tag>
                <el-tag :type="getOverdueType(item)">
                  {{ item.overdueDays > 0 ? `超期${item.overdueDays}天` : '未超期' }}
                </el-tag>
              </div>
              <el-divider />
              <div class="info-item">
                <span class="label">快递公司：</span>
                <span class="value">{{ item.expressCompany }}</span>
              </div>
              <div class="info-item">
                <span class="label">快递单号：</span>
                <span class="value">{{ item.trackingNumber }}</span>
              </div>
              <div class="info-item">
                <span class="label">代收点：</span>
                <span class="value">{{ getStationName(item.stationId) }}</span>
              </div>
              <div class="info-item">
                <span class="label">货架位置：</span>
                <span class="value">{{ item.shelfLocation }}</span>
              </div>
              <div class="info-item">
                <span class="label">入库时间：</span>
                <span class="value">{{ item.inTime }}</span>
              </div>
              <div class="info-item" v-if="item.overdueFee > 0">
                <span class="label">超期费用：</span>
                <span class="value" style="color: red; font-weight: bold">¥{{ item.overdueFee }}</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { getMyPackages } from '@/api/express'
import { getAllStations } from '@/api/station'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const packageList = ref([])
const stationList = ref([])

const getOverdueType = (item) => {
  if (item.overdueDays === 0) return 'success'
  if (item.overdueDays <= 3) return 'warning'
  return 'danger'
}

const getStationName = (stationId) => {
  const station = stationList.value.find(s => s.id === stationId)
  return station ? station.name : '-'
}

const loadData = async () => {
  loading.value = true
  try {
    const [packagesRes, stationsRes] = await Promise.all([
      getMyPackages(),
      getAllStations()
    ])
    packageList.value = packagesRes.data
    stationList.value = stationsRes.data
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.packages-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}

.package-card {
  margin-bottom: 20px;
}

.package-info {
  padding: 10px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.info-item {
  margin: 10px 0;
  display: flex;
  align-items: center;
}

.label {
  color: #909399;
  width: 90px;
}

.value {
  color: #303133;
  flex: 1;
  word-break: break-all;
}
</style>

