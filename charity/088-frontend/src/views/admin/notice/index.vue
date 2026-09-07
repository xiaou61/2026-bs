<template>
  <div class="page-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-button type="success" @click="handleAdd">新增公告</el-button>
      </div>
      <el-table :data="list">
        <el-table-column prop="title" label="标题" min-width="180" />
        <el-table-column prop="type" label="类型" min-width="100" />
        <el-table-column prop="publishTime" label="发布时间" min-width="160" />
        <el-table-column prop="content" label="内容" min-width="240" show-overflow-tooltip />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="form.id ? '编辑公告' : '新增公告'" width="620px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.type" /></el-form-item>
        <el-form-item label="发布时间"><el-date-picker v-model="form.publishTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addNotice, deleteNotice, getNoticeList, updateNotice } from '../../../api'

const list = ref([])
const visible = ref(false)
const form = reactive({})

const loadData = async () => {
  const res = await getNoticeList({ pageNum: 1, pageSize: 30 })
  list.value = res.data?.list || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, title: '', type: '公告', publishTime: '', content: '', status: 1 })
  visible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row })
  visible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateNotice(form)
  } else {
    await addNotice(form)
  }
  ElMessage.success('保存成功')
  visible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteNotice(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}
</style>
