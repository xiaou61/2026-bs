<template>
  <div class="parking-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>停车位管理</span>
          <el-button type="primary" @click="handleAdd">新增车位</el-button>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="spotNumber" label="车位号" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="ownerName" label="关联业主" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle">
      <el-form :model="form" label-width="80px">
        <el-form-item label="车位号">
          <el-input v-model="form.spotNumber" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option label="空闲" value="FREE" />
            <el-option label="已售" value="SOLD" />
            <el-option label="已租" value="RENTED" />
          </el-select>
        </el-form-item>
        <el-form-item label="关联业主ID" v-if="form.status !== 'FREE'">
          <el-input v-model="form.ownerId" type="number" />
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
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增车位')
const form = reactive({
  id: null,
  spotNumber: '',
  status: 'FREE',
  ownerId: ''
})

const loadData = async () => {
  const res = await request.get('/parking/list')
  if (res.code === '200') {
    tableData.value = res.data
  }
}

const getStatusType = (status) => {
  const map = {
    'FREE': 'success',
    'SOLD': 'danger',
    'RENTED': 'warning'
  }
  return map[status]
}

const getStatusText = (status) => {
  const map = {
    'FREE': '空闲',
    'SOLD': '已售',
    'RENTED': '已租'
  }
  return map[status]
}

const handleAdd = () => {
  dialogTitle.value = '新增车位'
  Object.assign(form, {
    id: null,
    spotNumber: '',
    status: 'FREE',
    ownerId: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑车位'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除?', '提示', {
    type: 'warning'
  }).then(async () => {
    const res = await request.delete(`/parking/${row.id}`)
    if (res.code === '200') {
      ElMessage.success('删除成功')
      loadData()
    }
  })
}

const submitForm = async () => {
  const url = form.id ? '/parking/update' : '/parking/add'
  const res = await request.post(url, form)
  if (res.code === '200') {
    ElMessage.success('操作成功')
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
