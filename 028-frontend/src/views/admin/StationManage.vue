<template>
  <div class="station-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>站点管理</span>
          <el-button type="primary" @click="handleAdd">添加站点</el-button>
        </div>
      </template>
      <el-table :data="stationList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="站点名称" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="capacity" label="容量" width="80" />
        <el-table-column prop="currentCount" label="当前车辆" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '运营中' : '已关闭' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" v-model:current-page="currentPage" @current-change="loadStations" class="mt-20" />
    </el-card>
    
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑站点' : '添加站点'">
      <el-form :model="stationForm" label-width="100px">
        <el-form-item label="站点名称" required>
          <el-input v-model="stationForm.name" />
        </el-form-item>
        <el-form-item label="地址" required>
          <el-input v-model="stationForm.address" />
        </el-form-item>
        <el-form-item label="经度">
          <el-input-number v-model="stationForm.longitude" :precision="6" />
        </el-form-item>
        <el-form-item label="纬度">
          <el-input-number v-model="stationForm.latitude" :precision="6" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="stationForm.capacity" :min="1" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="stationForm.status">
            <el-radio :label="1">运营中</el-radio>
            <el-radio :label="0">已关闭</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStation">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { adminApi } from '@/api'

const stationList = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const dialogVisible = ref(false)
const isEdit = ref(false)
const stationForm = reactive({ id: null, name: '', address: '', longitude: 0, latitude: 0, capacity: 20, status: 1 })

const loadStations = async () => {
  loading.value = true
  try {
    const res = await adminApi.getStationList({ page: currentPage.value })
    stationList.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(stationForm, { id: null, name: '', address: '', longitude: 0, latitude: 0, capacity: 20, status: 1 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(stationForm, row)
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该站点?', '提示', { type: 'warning' })
  await adminApi.deleteStation(row.id)
  ElMessage.success('删除成功')
  loadStations()
}

const submitStation = async () => {
  if (isEdit.value) {
    await adminApi.updateStation(stationForm)
  } else {
    await adminApi.addStation(stationForm)
  }
  ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
  dialogVisible.value = false
  loadStations()
}

onMounted(() => loadStations())
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
