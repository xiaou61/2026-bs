<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="公告标题" clearable style="width: 200px" />
        <el-select v-if="isAdmin" v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isAdmin" type="success" @click="handleAdd">新增公告</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="contentText" label="内容" min-width="320" show-overflow-tooltip />
        <el-table-column prop="publishTime" label="发布时间" min-width="170" />
        <el-table-column v-if="isAdmin" label="状态" width="90">
          <template #default="{ row }">
            <el-switch :model-value="row.status === 1" @change="val => handleStatusChange(row, val)" />
          </template>
        </el-table-column>
        <el-table-column v-if="isAdmin" label="操作" width="160">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pager" v-if="isAdmin">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="公告标题" prop="title"><el-input v-model="form.title" maxlength="100" /></el-form-item>
        <el-form-item label="公告内容" prop="contentText"><el-input v-model="form.contentText" type="textarea" :rows="6" maxlength="2000" show-word-limit /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addNotice, deleteNotice, getNoticeList, getNoticePage, updateNotice, updateNoticeStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, title: '', status: null })
const form = reactive({ id: null, title: '', contentText: '', status: 1 })
const rules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
  contentText: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    if (isAdmin.value) {
      const res = await getNoticePage(query)
      tableData.value = res.data.records || []
      total.value = res.data.total || 0
    } else {
      const res = await getNoticeList()
      tableData.value = res.data || []
      total.value = tableData.value.length
    }
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, title: '', contentText: '', status: 1 })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateNotice(form)
  } else {
    await addNotice(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定删除公告 ${row.title} 吗？`, '提示', { type: 'warning' })
  await deleteNotice(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleStatusChange = async (row, checked) => {
  await updateNoticeStatus({ id: row.id, status: checked ? 1 : 0 })
  row.status = checked ? 1 : 0
  ElMessage.success('状态已更新')
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
