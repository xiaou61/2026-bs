<template>
  <div class="notice-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>社区公告</span>
          <el-button type="primary" @click="handleAdd">发布公告</el-button>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="发布时间" />
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
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="4" />
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
const dialogTitle = ref('发布公告')
const form = reactive({
  id: null,
  title: '',
  content: ''
})

const loadData = async () => {
  const res = await request.get('/notice/list')
  if (res.code === '200') {
    tableData.value = res.data
  }
}

const handleAdd = () => {
  dialogTitle.value = '发布公告'
  Object.assign(form, {
    id: null,
    title: '',
    content: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑公告'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除?', '提示', {
    type: 'warning'
  }).then(async () => {
    const res = await request.delete(`/notice/${row.id}`)
    if (res.code === '200') {
      ElMessage.success('删除成功')
      loadData()
    }
  })
}

const submitForm = async () => {
  const url = form.id ? '/notice/update' : '/notice/add'
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
