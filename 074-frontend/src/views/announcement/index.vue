<template>
  <el-card v-if="isAdmin">
    <div class="bar">
      <el-input v-model="query.title" placeholder="标题" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增公告</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" min-width="220" />
      <el-table-column prop="content" label="内容" min-width="260" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-card v-else>
    <template #header>平台公告</template>
    <el-empty v-if="!publicList.length" description="暂无公告" />
    <el-timeline v-else>
      <el-timeline-item v-for="item in publicList" :key="item.id" :timestamp="item.createTime">
        <b>{{ item.title }}</b>
        <div style="margin-top: 6px">{{ item.content }}</div>
      </el-timeline-item>
    </el-timeline>
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="560px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="100" show-word-limit /></el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="5" maxlength="5000" show-word-limit /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addAnnouncement, deleteAnnouncement, getAnnouncementList, getAnnouncementPage, updateAnnouncement } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, title: '', status: null })
const form = reactive({})
const rules = {
  title: [
    { required: true, message: '请输入公告标题', trigger: 'blur' },
    { max: 100, message: '公告标题不能超过100字符', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入公告内容', trigger: 'blur' },
    { max: 5000, message: '公告内容不能超过5000字符', trigger: 'blur' }
  ]
}

const loadData = async () => {
  if (isAdmin.value) {
    loading.value = true
    try {
      const res = await getAnnouncementPage(query)
      tableData.value = res.data.records
      total.value = res.data.total
    } finally {
      loading.value = false
    }
  } else {
    const res = await getAnnouncementList()
    publicList.value = res.data || []
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, title: '', content: '', status: 1 })
  dialogVisible.value = true
  nextTick(() => formRef.value?.clearValidate())
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
  nextTick(() => formRef.value?.clearValidate())
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateAnnouncement(form)
  } else {
    await addAnnouncement(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteAnnouncement(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
