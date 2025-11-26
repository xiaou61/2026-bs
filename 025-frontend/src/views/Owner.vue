<template>
  <div class="owner-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>业主管理</span>
          <el-button type="primary" @click="handleAdd">新增业主</el-button>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="building" label="楼栋" />
        <el-table-column prop="unit" label="单元" />
        <el-table-column prop="room" label="房号" />
        <el-table-column prop="checkInTime" label="入住时间" />
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
        <el-form-item label="关联用户ID">
          <el-input v-model="form.userId" type="number" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="楼栋">
          <el-input v-model="form.building" />
        </el-form-item>
        <el-form-item label="单元">
          <el-input v-model="form.unit" />
        </el-form-item>
        <el-form-item label="房号">
          <el-input v-model="form.room" />
        </el-form-item>
        <el-form-item label="入住时间">
          <el-date-picker v-model="form.checkInTime" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
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
const dialogTitle = ref('新增业主')
const form = reactive({
  id: null,
  userId: '',
  name: '',
  phone: '',
  building: '',
  unit: '',
  room: '',
  checkInTime: ''
})

const loadData = async () => {
  const res = await request.get('/owner/list')
  if (res.code === '200') {
    tableData.value = res.data
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增业主'
  Object.assign(form, {
    id: null,
    userId: '',
    name: '',
    phone: '',
    building: '',
    unit: '',
    room: '',
    checkInTime: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑业主'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除?', '提示', {
    type: 'warning'
  }).then(async () => {
    const res = await request.delete(`/owner/${row.id}`)
    if (res.code === '200') {
      ElMessage.success('删除成功')
      loadData()
    }
  })
}

const submitForm = async () => {
  const url = form.id ? '/owner/update' : '/owner/add'
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
