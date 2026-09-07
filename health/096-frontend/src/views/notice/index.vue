<template>
  <div class="page-container">
    <el-card v-if="isAdmin">
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="搜索公告标题" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 160px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadAdminData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增公告</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="内容" min-width="280" show-overflow-tooltip />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">{{ row.status === 1 ? '启用' : '停用' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除该公告吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-else>
      <template #header>医院公告</template>
      <el-timeline>
        <el-timeline-item v-for="item in publicList" :key="item.id" :timestamp="item.createTime">
          <b>{{ item.title }}</b>
          <div style="margin-top: 6px">{{ item.content }}</div>
        </el-timeline-item>
      </el-timeline>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="620px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
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
import { ElMessage } from 'element-plus'
import { addNotice, deleteNotice, getNoticePage, getPublicNotices, updateNotice } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const dialogVisible = ref(false)
const tableData = ref([])
const publicList = ref([])
const query = reactive({ keyword: '', status: undefined })
const form = reactive({ id: null, title: '', content: '', sort: 0, status: 1 })

const loadAdminData = async () => {
  loading.value = true
  try {
    const res = await getNoticePage(query)
    tableData.value = res.data.list || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getPublicNotices()
  publicList.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, title: '', content: '', sort: 0, status: 1 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateNotice(form)
  } else {
    await addNotice(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadAdminData()
}

const handleDelete = async (id) => {
  await deleteNotice(id)
  ElMessage.success('删除成功')
  loadAdminData()
}

onMounted(() => {
  if (isAdmin.value) {
    loadAdminData()
  } else {
    loadPublicData()
  }
})
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
