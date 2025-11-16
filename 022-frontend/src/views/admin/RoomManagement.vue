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
      <el-table-column prop="openTime" label="开放时间" width="180">
        <template #default="{ row }">
          {{ row.openTime }} - {{ row.closeTime }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="handleStatusChange(row)" />
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
      <el-form ref="roomForm" :model="formData" :rules="rules" label-width="100px">
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
import { ref, reactive, onMounted } from 'vue'
import { getStudyRooms, createStudyRoom, updateStudyRoom, deleteStudyRoom } from '@/api/studyRoom'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const rooms = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const roomForm = ref()
const formData = reactive({
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

const loadRooms = async () => {
  loading.value = true
  try {
    const res = await getStudyRooms()
    rooms.value = res.data
  } catch (error) {
    ElMessage.error('加载自习室列表失败')
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增自习室'
  Object.assign(formData, {
    roomName: '',
    floorNumber: 1,
    capacity: 50,
    openTime: '08:00',
    closeTime: '22:00',
    description: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑自习室'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  const valid = await roomForm.value.validate()
  if (!valid) return
  
  try {
    if (formData.id) {
      await updateStudyRoom(formData.id, formData)
      ElMessage.success('更新成功')
    } else {
      await createStudyRoom(formData)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadRooms()
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

const handleStatusChange = async (row) => {
  try {
    await updateStudyRoom(row.id, { status: row.status })
    ElMessage.success('状态更新成功')
  } catch (error) {
    ElMessage.error('状态更新失败')
    row.status = row.status === 1 ? 0 : 1
  }
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定要删除该自习室吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  
  try {
    await deleteStudyRoom(row.id)
    ElMessage.success('删除成功')
    loadRooms()
  } catch (error) {
    ElMessage.error('删除失败')
  }
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
    
    h2 {
      margin: 0;
      font-size: 20px;
      color: #333;
    }
  }
}
</style>