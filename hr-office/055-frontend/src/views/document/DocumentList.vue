<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-input v-model="query.keyword" placeholder="文档名称" clearable style="width: 200px;" @keyup.enter="loadData" />
          <el-select v-model="query.category" placeholder="分类" clearable style="width: 120px;">
            <el-option label="制度文件" value="system" />
            <el-option label="技术文档" value="tech" />
            <el-option label="培训资料" value="train" />
            <el-option label="其他" value="other" />
          </el-select>
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-upload :action="uploadUrl" :headers="uploadHeaders" :show-file-list="false" :on-success="handleUploadSuccess">
          <el-button type="primary">上传文档</el-button>
        </el-upload>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="name" label="文档名称" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="size" label="大小" width="100">
          <template #default="{ row }">{{ formatSize(row.size) }}</template>
        </el-table-column>
        <el-table-column prop="category" label="分类" width="100">
          <template #default="{ row }">{{ getCategoryText(row.category) }}</template>
        </el-table-column>
        <el-table-column prop="uploaderName" label="上传者" width="100" />
        <el-table-column prop="downloadCount" label="下载次数" width="100" />
        <el-table-column prop="createTime" label="上传时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleDownload(row)">下载</el-button>
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="编辑文档" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="文档名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="form.category" style="width: 100%;">
            <el-option label="制度文件" value="system" />
            <el-option label="技术文档" value="tech" />
            <el-option label="培训资料" value="train" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        <el-form-item label="共享">
          <el-switch v-model="form.isShared" :active-value="1" :inactive-value="0" />
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDocumentList, updateDocument, deleteDocument } from '../../api'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const uploadUrl = '/api/document/upload'
const uploadHeaders = { Authorization: localStorage.getItem('token') }

const query = reactive({
  keyword: '',
  category: '',
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  id: null,
  name: '',
  category: 'other',
  isShared: 0
})

const rules = {
  name: [{ required: true, message: '请输入文档名称', trigger: 'blur' }]
}

const getCategoryText = (category) => {
  const map = { system: '制度文件', tech: '技术文档', train: '培训资料', other: '其他' }
  return map[category] || '其他'
}

const formatSize = (size) => {
  if (!size) return '-'
  if (size < 1024) return size + 'B'
  if (size < 1024 * 1024) return (size / 1024).toFixed(1) + 'KB'
  return (size / 1024 / 1024).toFixed(1) + 'MB'
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDocumentList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    ElMessage.success('上传成功')
    loadData()
  } else {
    ElMessage.error(res.message || '上传失败')
  }
}

const handleDownload = (row) => {
  window.open(`/api/document/download/${row.id}`)
}

const handleEdit = (row) => {
  Object.assign(form, { id: row.id, name: row.name, category: row.category, isShared: row.isShared })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await updateDocument(form)
  ElMessage.success('修改成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该文档吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteDocument(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}
.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}
.search {
  display: flex;
  gap: 10px;
}
</style>
