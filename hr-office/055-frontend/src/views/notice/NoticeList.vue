<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-input v-model="query.keyword" placeholder="公告标题" clearable style="width: 200px;" @keyup.enter="loadData" />
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-button type="primary" @click="handleAdd" v-if="!isEmployee">发布公告</el-button>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="title" label="公告标题" />
        <el-table-column prop="publisherName" label="发布人" width="100" />
        <el-table-column prop="isTop" label="置顶" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isTop" type="danger">是</el-tag>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已发布' : '草稿' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleView(row)">查看</el-button>
            <template v-if="!isEmployee">
              <el-button link type="success" @click="handlePublish(row)" v-if="row.status === 0">发布</el-button>
              <el-button link type="primary" @click="handleEdit(row)" v-if="row.status === 0">编辑</el-button>
              <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="isView ? '公告详情' : (form.id ? '编辑公告' : '发布公告')" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" :disabled="isView">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="6" />
        </el-form-item>
        <el-form-item label="置顶" v-if="!isView">
          <el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer v-if="!isView">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getNoticeList, getPublishedNotices, addNotice, updateNotice, deleteNotice, publishNotice, readNotice } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isEmployee = computed(() => userStore.isEmployee)
const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const isView = ref(false)
const formRef = ref()

const query = reactive({
  keyword: '',
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  id: null,
  title: '',
  content: '',
  isTop: 0
})

const rules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const api = isEmployee.value ? getPublishedNotices : getNoticeList
    const res = await api(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, title: '', content: '', isTop: 0 })
}

const handleAdd = () => {
  resetForm()
  isView.value = false
  dialogVisible.value = true
}

const handleView = async (row) => {
  if (isEmployee.value) {
    await readNotice(row.id)
  }
  resetForm()
  Object.assign(form, row)
  isView.value = true
  dialogVisible.value = true
}

const handleEdit = (row) => {
  resetForm()
  Object.assign(form, row)
  isView.value = false
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateNotice(form)
    ElMessage.success('修改成功')
  } else {
    await addNotice(form)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handlePublish = (row) => {
  ElMessageBox.confirm('确定发布该公告吗？', '提示', { type: 'warning' }).then(async () => {
    await publishNotice(row.id)
    ElMessage.success('发布成功')
    loadData()
  })
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该公告吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteNotice(row.id)
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
