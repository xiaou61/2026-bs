<template>
  <div class="room-management">
    <div class="page-header">
      <h2>自习室管理</h2>
      <el-button type="primary" @click="handleAdd">新增自习室</el-button>
    </div>

    <el-table :data="rooms" stripe v-loading="loading">
      <el-table-column prop="roomName" label="自习室名称" />
      <el-table-column prop="floorNumber" label="楼层" width="80" />
      <el-table-column prop="capacity" label="容纳人数" width="100" />
      <el-table-column label="开放时间" width="180">
        <template #default="{ row }">
          {{ row.openTime }} - {{ row.closeTime }}
        </template>
      </el-table-column>
      <el-table-column prop="availableSeats" label="空闲座位" width="100" />
      <el-table-column prop="occupiedSeats" label="占用座位" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-switch
            v-model="row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="roomFormRef" :model="formData" :rules="rules" label-width="100px">
        <el-form-item label="自习室名称" prop="roomName">
          <el-input v-model="formData.roomName" />
        </el-form-item>
        <el-form-item label="楼层" prop="floorNumber">
          <el-input-number v-model="formData.floorNumber" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="容纳人数" prop="capacity">
          <el-input-number v-model="formData.capacity" :min="1" :max="500" />
        </el-form-item>
        <el-form-item label="开放时间" prop="openTime">
          <el-time-select v-model="formData.openTime" start="06:00" step="00:30" end="22:00" placeholder="开放时间" />
        </el-form-item>
        <el-form-item label="关闭时间" prop="closeTime">
          <el-time-select v-model="formData.closeTime" start="06:00" step="00:30" end="23:00" placeholder="关闭时间" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="formData.description" type="textarea" rows="3" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  createStudyRoom,
  deleteStudyRoom,
  getStudyRoomPage,
  updateStudyRoom,
  updateStudyRoomStatus
} from '@/api/studyRoom'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const rooms = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const roomFormRef = ref()
const formData = reactive({
  id: null,
  roomName: '',
  floorNumber: 1,
  capacity: 50,
  openTime: '08:00',
  closeTime: '22:00',
  description: ''
})

const rules = {
  roomName: [{ required: true, message: '请输入自习室名称', trigger: 'blur' }],
  floorNumber: [{ required: true, message: '请选择楼层', trigger: 'change' }],
  capacity: [{ required: true, message: '请输入容纳人数', trigger: 'change' }],
  openTime: [{ required: true, message: '请选择开放时间', trigger: 'change' }],
  closeTime: [{ required: true, message: '请选择关闭时间', trigger: 'change' }]
}

const normalizeTime = (value) => (value || '').slice(0, 5)

const loadRooms = async () => {
  loading.value = true
  try {
    const res = await getStudyRoomPage({ current: 1, size: 100 })
    rooms.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(formData, {
    id: null,
    roomName: '',
    floorNumber: 1,
    capacity: 50,
    openTime: '08:00',
    closeTime: '22:00',
    description: ''
  })
}

const handleAdd = () => {
  dialogTitle.value = '新增自习室'
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑自习室'
  Object.assign(formData, {
    id: row.id,
    roomName: row.roomName,
    floorNumber: row.floorNumber,
    capacity: row.capacity,
    openTime: normalizeTime(row.openTime),
    closeTime: normalizeTime(row.closeTime),
    description: row.description
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await roomFormRef.value.validate()
  const payload = {
    roomName: formData.roomName,
    floorNumber: formData.floorNumber,
    capacity: formData.capacity,
    openTime: formData.openTime,
    closeTime: formData.closeTime,
    description: formData.description,
    status: 1
  }

  if (formData.id) {
    await updateStudyRoom(formData.id, payload)
    ElMessage.success('更新成功')
  } else {
    await createStudyRoom(payload)
    ElMessage.success('创建成功')
  }

  dialogVisible.value = false
  loadRooms()
}

const handleStatusChange = async (row) => {
  try {
    await updateStudyRoomStatus(row.id, row.status)
    ElMessage.success('状态更新成功')
  } catch (error) {
    row.status = row.status === 1 ? 0 : 1
    ElMessage.error(error.message || '状态更新失败')
  }
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定要删除该自习室吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })

  await deleteStudyRoom(row.id)
  ElMessage.success('删除成功')
  loadRooms()
}

onMounted(() => {
  loadRooms()
})
</script>

<style lang="scss" scoped>
.room-management {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
}
</style>
