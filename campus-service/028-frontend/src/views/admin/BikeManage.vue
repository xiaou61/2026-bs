<template>
  <div class="bike-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>车辆管理</span>
          <div>
            <el-select v-model="filterStatus" placeholder="状态" clearable style="width: 120px; margin-right: 10px" @change="loadBikes">
              <el-option label="可用" :value="0" />
              <el-option label="使用中" :value="1" />
              <el-option label="维修中" :value="2" />
              <el-option label="已报废" :value="3" />
            </el-select>
            <el-button type="primary" @click="handleAdd">添加车辆</el-button>
          </div>
        </div>
      </template>
      <el-table :data="bikeList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="bikeNo" label="车辆编号" />
        <el-table-column prop="stationName" label="所属站点" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="batteryLevel" label="电量" width="120">
          <template #default="{ row }">
            <el-progress :percentage="row.batteryLevel" :color="batteryColor(row.batteryLevel)" />
          </template>
        </el-table-column>
        <el-table-column prop="totalMileage" label="总里程(km)" width="100" />
        <el-table-column prop="createTime" label="投放时间" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" v-model:current-page="currentPage" @current-change="loadBikes" class="mt-20" />
    </el-card>
    
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑车辆' : '添加车辆'">
      <el-form :model="bikeForm" label-width="100px">
        <el-form-item label="车辆编号" required>
          <el-input v-model="bikeForm.bikeNo" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="所属站点" required>
          <el-select v-model="bikeForm.stationId" style="width: 100%">
            <el-option v-for="s in stationList" :key="s.id" :label="s.name" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="bikeForm.status">
            <el-option label="可用" :value="0" />
            <el-option label="使用中" :value="1" />
            <el-option label="维修中" :value="2" />
            <el-option label="已报废" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="电量">
          <el-slider v-model="bikeForm.batteryLevel" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitBike">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { adminApi, stationApi } from '@/api'

const bikeList = ref([])
const stationList = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const filterStatus = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const bikeForm = reactive({ id: null, bikeNo: '', stationId: null, status: 0, batteryLevel: 100 })

const statusText = { 0: '可用', 1: '使用中', 2: '维修中', 3: '已报废' }
const statusType = { 0: 'success', 1: 'primary', 2: 'warning', 3: 'info' }

const batteryColor = (level) => level > 50 ? '#67c23a' : level > 20 ? '#e6a23c' : '#f56c6c'

const loadBikes = async () => {
  loading.value = true
  try {
    const res = await adminApi.getBikeList({ page: currentPage.value, status: filterStatus.value })
    bikeList.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadStations = async () => {
  const res = await stationApi.getList()
  stationList.value = res.data || []
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(bikeForm, { id: null, bikeNo: '', stationId: null, status: 0, batteryLevel: 100 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(bikeForm, row)
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该车辆?', '提示', { type: 'warning' })
  await adminApi.deleteBike(row.id)
  ElMessage.success('删除成功')
  loadBikes()
}

const submitBike = async () => {
  if (isEdit.value) {
    await adminApi.updateBike(bikeForm)
  } else {
    await adminApi.addBike(bikeForm)
  }
  ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
  dialogVisible.value = false
  loadBikes()
}

onMounted(() => {
  loadBikes()
  loadStations()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
