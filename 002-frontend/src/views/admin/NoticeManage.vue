<template>
  <div>
    <h2 style="margin-bottom: 20px">公告管理</h2>

    <el-form :inline="true" style="margin-bottom: 20px">
      <el-form-item>
        <el-button type="success" @click="handleAdd">发布公告</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="noticeList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="title" label="公告标题" width="250" />
      <el-table-column prop="content" label="公告内容" show-overflow-tooltip />
      <el-table-column prop="type" label="类型" width="120" />
      <el-table-column prop="publisherName" label="发布人" width="120" />
      <el-table-column prop="createdTime" label="发布时间" width="180" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 1" type="success">已发布</el-tag>
          <el-tag v-else type="info">已下线</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="公告类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择" style="width: 200px">
            <el-option label="系统公告" value="system" />
            <el-option label="课程公告" value="course" />
            <el-option label="成绩公告" value="grade" />
            <el-option label="通知" value="notice" />
          </el-select>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="8" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">发布</el-radio>
            <el-radio :label="0">下线</el-radio>
          </el-radio-group>
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
import { getNoticeList, createNotice, updateNotice, deleteNotice } from '@/api/notice'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const noticeList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)

const form = reactive({
  id: null,
  title: '',
  content: '',
  type: '',
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
  type: [{ required: true, message: '请选择公告类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
}

const loadNotices = async () => {
  loading.value = true
  try {
    const res = await getNoticeList()
    noticeList.value = res.data
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  dialogTitle.value = '发布公告'
  Object.assign(form, {
    id: null,
    title: '',
    content: '',
    type: '',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑公告'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateNotice(form.id, form)
          ElMessage.success('更新成功')
        } else {
          await createNotice(form)
          ElMessage.success('发布成功')
        }
        dialogVisible.value = false
        loadNotices()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该公告吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteNotice(row.id)
    ElMessage.success('删除成功')
    loadNotices()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadNotices()
})
</script>

