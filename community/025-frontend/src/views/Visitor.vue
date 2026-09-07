<template>
  <div class="visitor-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>访客登记</span>
          <el-button type="primary" @click="handleAdd">访客登记</el-button>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="ownerName" label="受访业主" />
        <el-table-column prop="visitorName" label="访客姓名" />
        <el-table-column prop="plateNumber" label="车牌号" />
        <el-table-column prop="visitTime" label="访问时间" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button 
              size="small" 
              type="success" 
              v-if="scope.row.status === 'PENDING'"
              @click="handleStatus(scope.row, 'APPROVED')"
            >批准进入</el-button>
            <el-button 
              size="small" 
              type="info" 
              v-if="scope.row.status === 'APPROVED'"
              @click="handleStatus(scope.row, 'LEFT')"
            >确认离开</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="访客登记">
      <el-form :model="form" label-width="80px">
        <el-form-item label="受访业主ID">
          <el-input v-model="form.ownerId" type="number" />
        </el-form-item>
        <el-form-item label="访客姓名">
          <el-input v-model="form.visitorName" />
        </el-form-item>
        <el-form-item label="车牌号">
          <el-input v-model="form.plateNumber" />
        </el-form-item>
        <el-form-item label="访问时间">
          <el-date-picker v-model="form.visitTime" type="datetime" placeholder="选择日期时间" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const form = reactive({
  ownerId: '',
  visitorName: '',
  plateNumber: '',
  visitTime: ''
})

const loadData = async () => {
  const res = await request.get('/visitor/list')
  if (res.code === '200') {
    tableData.value = res.data
  }
}

const getStatusType = (status) => {
  const map = {
    'PENDING': 'warning',
    'APPROVED': 'success',
    'LEFT': 'info'
  }
  return map[status]
}

const getStatusText = (status) => {
  const map = {
    'PENDING': '待进入',
    'APPROVED': '已进入',
    'LEFT': '已离开'
  }
  return map[status]
}

const handleAdd = () => {
  Object.assign(form, {
    ownerId: '',
    visitorName: '',
    plateNumber: '',
    visitTime: ''
  })
  dialogVisible.value = true
}

const handleStatus = async (row, status) => {
  const res = await request.post(`/visitor/status/${row.id}?status=${status}`)
  if (res.code === '200') {
    ElMessage.success('操作成功')
    loadData()
  }
}

const submitForm = async () => {
  const res = await request.post('/visitor/register', form)
  if (res.code === '200') {
    ElMessage.success('登记成功')
    dialogVisible.value = false
    loadData()
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
