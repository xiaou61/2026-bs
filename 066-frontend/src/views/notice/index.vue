<template>
  <el-card v-if="isAdmin">
    <div class="bar">
      <el-input v-model="query.title" placeholder="公告标题" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="发布" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增公告</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="title" label="标题" min-width="220" />
      <el-table-column prop="content" label="内容" min-width="360" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '发布' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="160">
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

  <div v-else class="list-wrap">
    <el-empty v-if="!listData.length" description="暂无公告" />
    <el-card v-for="item in listData" :key="item.id" class="item-card">
      <div class="item-head">
        <h3>{{ item.title }}</h3>
        <span>{{ item.createTime }}</span>
      </div>
      <div class="item-content">{{ item.content }}</div>
    </el-card>
  </div>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="620px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="100" /></el-form-item>
      <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="6" maxlength="2000" /></el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addNotice, deleteNotice, getNoticeList, getNoticePage, updateNotice } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const listData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, title: '', status: null })
const form = reactive({})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const loadData = async () => {
  if (!isAdmin.value) {
    return
  }
  loading.value = true
  try {
    const res = await getNoticePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadList = async () => {
  const res = await getNoticeList()
  listData.value = res.data || []
}

const openAdd = () => {
  Object.assign(form, { id: null, title: '', content: '', status: 1 })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateNotice(form)
  } else {
    await addNotice(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
  loadList()
}

const handleDelete = async (id) => {
  await deleteNotice(id)
  ElMessage.success('删除成功')
  loadData()
  loadList()
}

onMounted(async () => {
  await loadList()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}

.list-wrap {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.item-card {
  border-radius: 8px;
}

.item-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.item-head h3 {
  margin: 0;
  font-size: 16px;
}

.item-head span {
  color: #64748b;
  font-size: 13px;
}

.item-content {
  color: #334155;
  white-space: pre-wrap;
  line-height: 1.7;
}
</style>
