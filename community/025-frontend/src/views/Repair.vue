<template>
  <div class="repair-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>在线报修</span>
          <el-button type="primary" @click="handleAdd">提交报修</el-button>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="ownerName" label="业主姓名" />
        <el-table-column prop="content" label="报修内容" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="提交时间" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button 
              size="small" 
              type="primary" 
              v-if="scope.row.status === 'PENDING'"
              @click="handleStatus(scope.row, 'PROCESSING')"
            >开始处理</el-button>
            <el-button 
              size="small" 
              type="success" 
              v-if="scope.row.status === 'PROCESSING'"
              @click="handleStatus(scope.row, 'COMPLETED')"
            >完成</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="提交报修">
      <el-form :model="form" label-width="80px">
        <el-form-item label="业主ID">
          <el-input v-model="form.ownerId" type="number" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" />
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
  content: ''
})

const loadData = async () => {
  const res = await request.get('/repair/list')
  if (res.code === '200') {
    tableData.value = res.data
  }
}

const getStatusType = (status) => {
  const map = {
    'PENDING': 'warning',
    'PROCESSING': 'primary',
    'COMPLETED': 'success'
  }
  return map[status]
}

const getStatusText = (status) => {
  const map = {
    'PENDING': '待处理',
    'PROCESSING': '处理中',
    'COMPLETED': '已完成'
  }
  return map[status]
}

const handleAdd = () => {
  Object.assign(form, {
    ownerId: '',
    content: ''
  })
  dialogVisible.value = true
}

const handleStatus = async (row, status) => {
  const res = await request.post(`/repair/status/${row.id}?status=${status}`)
  if (res.code === '200') {
    ElMessage.success('操作成功')
    loadData()
  }
}

const submitForm = async () => {
  const res = await request.post('/repair/submit', form)
  if (res.code === '200') {
    ElMessage.success('提交成功')
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
