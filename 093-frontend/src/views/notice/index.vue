<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar" v-if="isManager">
        <el-input v-model="query.title" placeholder="公告标题" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增公告</el-button>
      </div>

      <el-timeline v-if="!isManager">
        <el-timeline-item v-for="item in publicList" :key="item.id" :timestamp="item.createTime">
          <div class="notice-title">{{ item.title }}</div>
          <div class="notice-content">{{ item.content }}</div>
        </el-timeline-item>
      </el-timeline>

      <el-table v-else :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="公告标题" min-width="180" />
        <el-table-column prop="noticeType" label="公告类型" min-width="120" />
        <el-table-column prop="publisherName" label="发布人" min-width="100" />
        <el-table-column prop="status" label="状态" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="发布时间" min-width="180" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="公告类型">
          <el-select v-model="form.noticeType" style="width: 100%">
            <el-option label="SYSTEM" value="SYSTEM" />
            <el-option label="PROMOTION" value="PROMOTION" />
          </el-select>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="5" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
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
import { deleteNotice, getNoticeList, getPublicNoticeList, saveNotice } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((userStore.user?.role || '').toUpperCase()))

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  title: '',
  status: null
})
const form = reactive({
  id: null,
  title: '',
  noticeType: 'SYSTEM',
  content: '',
  status: 1
})
const rules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getNoticeList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getPublicNoticeList()
  publicList.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    title: '',
    noticeType: 'SYSTEM',
    content: '',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveNotice(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteNotice(id)
  ElMessage.success('删除成功')
  loadManagerData()
}

onMounted(() => {
  if (isManager.value) {
    loadManagerData()
  } else {
    loadPublicData()
  }
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.notice-title {
  font-weight: 600;
  color: #17324d;
}

.notice-content {
  margin-top: 6px;
  color: #475467;
  line-height: 1.8;
}
</style>
