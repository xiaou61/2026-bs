<template>
  <div class="history-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">取件历史</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="trackingNumber" label="快递单号" width="180" />
        <el-table-column prop="expressCompany" label="快递公司" width="120" />
        <el-table-column prop="pickupCode" label="取件码" width="100" />
        <el-table-column label="代收点" width="150">
          <template #default="{ row }">
            {{ getStationName(row.stationId) }}
          </template>
        </el-table-column>
        <el-table-column prop="inTime" label="入库时间" width="180" />
        <el-table-column prop="outTime" label="取件时间" width="180" />
        <el-table-column label="超期天数" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.overdueDays === 0" type="success">未超期</el-tag>
            <el-tag v-else type="warning">{{ row.overdueDays }}天</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="超期费用" width="100">
          <template #default="{ row }">
            <span :style="{ color: row.overdueFee > 0 ? 'red' : 'green' }">
              ¥{{ row.overdueFee }}
            </span>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMyHistory } from '@/api/express'
import { getAllStations } from '@/api/station'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const stationList = ref([])

const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

const getStationName = (stationId) => {
  const station = stationList.value.find(s => s.id === stationId)
  return station ? station.name : '-'
}

const loadData = async () => {
  loading.value = true
  try {
    const [historyRes, stationsRes] = await Promise.all([
      getMyHistory({ page: pagination.page, size: pagination.size }),
      getAllStations()
    ])
    tableData.value = historyRes.data.records
    pagination.total = historyRes.data.total
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
.history-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}
</style>

