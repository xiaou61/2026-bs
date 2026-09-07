<template>
  <div class="seat-management">
    <div class="page-header">
      <h2>座位管理</h2>
      <div class="actions">
        <el-select v-model="selectedRoomId" placeholder="选择自习室" style="width: 220px" @change="loadSeats">
          <el-option v-for="room in rooms" :key="room.id" :label="room.roomName" :value="room.id" />
        </el-select>
        <el-button type="primary" @click="openSingleDialog">新增座位</el-button>
        <el-button type="success" @click="openBatchDialog">批量创建</el-button>
      </div>
    </div>

    <el-table :data="seats" stripe v-loading="loading">
      <el-table-column prop="seatNumber" label="座位编号" width="120" />
      <el-table-column prop="seatType" label="座位类型" width="120">
        <template #default="{ row }">{{ seatTypeMap[row.seatType] }}</template>
      </el-table-column>
      <el-table-column prop="seatStatus" label="状态" width="120">
        <template #default="{ row }">
          <el-select v-model="row.seatStatus" size="small" @change="handleStatusChange(row)">
            <el-option label="空闲" :value="1" />
            <el-option label="占用" :value="2" />
            <el-option label="维修" :value="3" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="xCoordinate" label="X 坐标" width="100" />
      <el-table-column prop="yCoordinate" label="Y 坐标" width="100" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="singleDialogVisible" :title="formData.id ? '编辑座位' : '新增座位'" width="480px">
      <el-form :model="formData" label-width="90px">
        <el-form-item label="自习室">
          <el-select v-model="formData.roomId" placeholder="选择自习室" style="width: 100%">
            <el-option v-for="room in rooms" :key="room.id" :label="room.roomName" :value="room.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="座位编号">
          <el-input v-model="formData.seatNumber" />
        </el-form-item>
        <el-form-item label="座位类型">
          <el-select v-model="formData.seatType" style="width: 100%">
            <el-option label="普通座位" :value="1" />
            <el-option label="电源座位" :value="2" />
            <el-option label="静音区" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="X 坐标">
          <el-input-number v-model="formData.xCoordinate" :min="0" :step="0.1" />
        </el-form-item>
        <el-form-item label="Y 坐标">
          <el-input-number v-model="formData.yCoordinate" :min="0" :step="0.1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="singleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSaveSeat">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="batchDialogVisible" title="批量创建座位" width="480px">
      <el-form :model="batchForm" label-width="90px">
        <el-form-item label="自习室">
          <el-select v-model="batchForm.roomId" placeholder="选择自习室" style="width: 100%">
            <el-option v-for="room in rooms" :key="room.id" :label="room.roomName" :value="room.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="编号前缀">
          <el-input v-model="batchForm.prefix" />
        </el-form-item>
        <el-form-item label="起始编号">
          <el-input-number v-model="batchForm.startNumber" :min="1" />
        </el-form-item>
        <el-form-item label="结束编号">
          <el-input-number v-model="batchForm.endNumber" :min="1" />
        </el-form-item>
        <el-form-item label="座位类型">
          <el-select v-model="batchForm.seatType" style="width: 100%">
            <el-option label="普通座位" :value="1" />
            <el-option label="电源座位" :value="2" />
            <el-option label="静音区" :value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="batchDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleBatchCreate">批量创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  batchCreateSeats,
  createSeat,
  deleteSeat,
  getSeatPage,
  updateSeat,
  updateSeatStatus
} from '@/api/seat'
import { getStudyRoomPage } from '@/api/studyRoom'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const rooms = ref([])
const seats = ref([])
const selectedRoomId = ref(null)
const singleDialogVisible = ref(false)
const batchDialogVisible = ref(false)

const seatTypeMap = {
  1: '普通座位',
  2: '电源座位',
  3: '静音区'
}

const formData = reactive({
  id: null,
  roomId: null,
  seatNumber: '',
  seatType: 1,
  xCoordinate: 0,
  yCoordinate: 0
})

const batchForm = reactive({
  roomId: null,
  prefix: 'A',
  startNumber: 1,
  endNumber: 10,
  seatType: 1
})

const loadRooms = async () => {
  const res = await getStudyRoomPage({ current: 1, size: 100 })
  rooms.value = res.data.records || []
  if (!selectedRoomId.value && rooms.value.length) {
    selectedRoomId.value = rooms.value[0].id
  }
}

const loadSeats = async () => {
  if (!selectedRoomId.value) return
  loading.value = true
  try {
    const res = await getSeatPage({ current: 1, size: 200, roomId: selectedRoomId.value })
    seats.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const openSingleDialog = () => {
  Object.assign(formData, {
    id: null,
    roomId: selectedRoomId.value,
    seatNumber: '',
    seatType: 1,
    xCoordinate: 0,
    yCoordinate: 0
  })
  singleDialogVisible.value = true
}

const openEditDialog = (row) => {
  Object.assign(formData, {
    id: row.id,
    roomId: row.roomId,
    seatNumber: row.seatNumber,
    seatType: row.seatType,
    xCoordinate: Number(row.xCoordinate || 0),
    yCoordinate: Number(row.yCoordinate || 0)
  })
  singleDialogVisible.value = true
}

const openBatchDialog = () => {
  Object.assign(batchForm, {
    roomId: selectedRoomId.value,
    prefix: 'A',
    startNumber: 1,
    endNumber: 10,
    seatType: 1
  })
  batchDialogVisible.value = true
}

const handleSaveSeat = async () => {
  const payload = {
    roomId: formData.roomId,
    seatNumber: formData.seatNumber,
    seatType: formData.seatType,
    xCoordinate: formData.xCoordinate,
    yCoordinate: formData.yCoordinate
  }

  if (formData.id) {
    await updateSeat(formData.id, payload)
    ElMessage.success('座位已更新')
  } else {
    await createSeat(payload)
    ElMessage.success('座位已创建')
  }

  singleDialogVisible.value = false
  loadSeats()
}

const handleBatchCreate = async () => {
  await batchCreateSeats({ ...batchForm })
  ElMessage.success('批量创建成功')
  batchDialogVisible.value = false
  loadSeats()
}

const handleStatusChange = async (row) => {
  try {
    await updateSeatStatus(row.id, row.seatStatus)
    ElMessage.success('状态已更新')
  } catch (error) {
    ElMessage.error(error.message || '状态更新失败')
    loadSeats()
  }
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该座位吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteSeat(row.id)
  ElMessage.success('删除成功')
  loadSeats()
}

onMounted(async () => {
  await loadRooms()
  await loadSeats()
})
</script>

<style lang="scss" scoped>
.seat-management {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }

  .actions {
    display: flex;
    gap: 12px;
  }
}
</style>
