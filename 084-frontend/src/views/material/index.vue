<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="资料标题" clearable style="width: 220px" />
        <el-select v-model="query.categoryId" placeholder="分类" clearable style="width: 180px">
          <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.auditStatus" placeholder="审核状态" clearable style="width: 140px">
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已驳回" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="title" label="标题" min-width="220" />
        <el-table-column prop="categoryId" label="分类">
          <template #default="{ row }">{{ categoryName(row.categoryId) }}</template>
        </el-table-column>
        <el-table-column prop="auditStatus" label="审核" width="100">
          <template #default="{ row }">
            <el-tag :type="auditType(row.auditStatus)">{{ auditText(row.auditStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishStatus" label="发布" width="90">
          <template #default="{ row }">
            <el-tag :type="row.publishStatus === 1 ? 'success' : 'info'">{{ row.publishStatus === 1 ? '上架' : '下架' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="downloadCount" label="下载" width="80" />
        <el-table-column label="操作" width="280">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" @click="handlePublish(row, 1)">上架</el-button>
            <el-button link type="warning" @click="handlePublish(row, 0)">下架</el-button>
            <el-button link type="primary" @click="handleFavorite(row)">收藏</el-button>
            <el-button link type="info" @click="handleDownload(row)">下载</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑资料' : '新增资料'" width="700px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="grade"><el-input v-model="form.grade" /></el-form-item>
        <el-form-item label="学科" prop="subject"><el-input v-model="form.subject" /></el-form-item>
        <el-form-item label="关键词" prop="keyword"><el-input v-model="form.keyword" /></el-form-item>
        <el-form-item label="文件名" prop="fileName"><el-input v-model="form.fileName" /></el-form-item>
        <el-form-item label="路径" prop="filePath"><el-input v-model="form.filePath" /></el-form-item>
        <el-form-item label="大小" prop="fileSize"><el-input-number v-model="form.fileSize" :min="0" /></el-form-item>
        <el-form-item label="类型" prop="fileType"><el-input v-model="form.fileType" /></el-form-item>
        <el-form-item label="摘要" prop="summary"><el-input v-model="form.summary" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  addFavorite,
  addMaterial,
  deleteMaterial,
  downloadMaterial,
  getCategoryEnabled,
  getMaterialDetail,
  getMaterialList,
  publishMaterial,
  updateMaterial
} from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const categoryOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, title: '', categoryId: null, auditStatus: null, publishStatus: null })
const form = reactive({
  id: null,
  title: '',
  summary: '',
  categoryId: null,
  grade: '',
  subject: '',
  keyword: '',
  fileName: '',
  filePath: '',
  fileSize: 0,
  fileType: ''
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const categoryName = (id) => {
  const target = categoryOptions.value.find(item => item.id === id)
  return target ? target.name : '-'
}

const auditText = (status) => (status === 1 ? '已通过' : status === 2 ? '已驳回' : '待审核')
const auditType = (status) => (status === 1 ? 'success' : status === 2 ? 'danger' : 'warning')

const loadCategory = async () => {
  const res = await getCategoryEnabled()
  categoryOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMaterialList(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    title: '',
    summary: '',
    categoryId: null,
    grade: '',
    subject: '',
    keyword: '',
    fileName: '',
    filePath: '',
    fileSize: 0,
    fileType: ''
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  const res = await getMaterialDetail(row.id)
  Object.assign(form, res.data.material || row)
  Object.assign(form, res.data.file || {})
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) await updateMaterial(form)
  else await addMaterial(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handlePublish = async (row, status) => {
  await publishMaterial(row.id, status)
  ElMessage.success('状态已更新')
  loadData()
}

const handleDownload = async (row) => {
  await downloadMaterial(row.id)
  ElMessage.success('下载记录已添加')
  loadData()
}

const handleFavorite = async (row) => {
  await addFavorite(row.id)
  ElMessage.success('收藏成功')
}

const handleDelete = async (id) => {
  await deleteMaterial(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadCategory()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
</style>
