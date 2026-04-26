<template>
  <div class="page-container">
    <el-card v-if="isAdmin">
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="资讯标题" clearable />
        <el-select v-model="query.status" placeholder="发布状态" clearable>
          <el-option label="已发布" :value="1" />
          <el-option label="已下线" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadAdminData">查询</el-button>
        <el-button type="success" @click="handleAdd">发布资讯</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column label="类型" min-width="120">
          <template #default="{ row }">
            <el-tag :type="typeTagMap[row.noticeType] || 'info'">{{ typeTextMap[row.noticeType] || row.noticeType || '资讯' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="authorName" label="发布人" min-width="120" />
        <el-table-column label="状态" min-width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '已发布' : '已下线' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" min-width="180" />
        <el-table-column label="内容摘要" min-width="280" show-overflow-tooltip>
          <template #default="{ row }">{{ row.content || '-' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除这条资讯吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <div v-else class="news-list" v-loading="loading">
      <el-card v-for="item in publicList" :key="item.id" class="news-card" shadow="hover">
        <div class="card-head">
          <el-tag :type="typeTagMap[item.noticeType] || 'info'">{{ typeTextMap[item.noticeType] || item.noticeType || '资讯' }}</el-tag>
          <span class="publish-time">{{ item.publishTime || '-' }}</span>
        </div>
        <h3 class="news-title">{{ item.title }}</h3>
        <p class="news-content">{{ item.content }}</p>
        <div class="news-footer">
          <span>发布人：{{ item.authorName || '联赛官方' }}</span>
        </div>
      </el-card>
      <el-empty v-if="!publicList.length && !loading" description="暂无资讯公告" />
    </div>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑资讯' : '发布资讯'" width="680px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="资讯标题" prop="title">
          <el-input v-model="form.title" maxlength="80" show-word-limit />
        </el-form-item>
        <el-form-item label="资讯类型" prop="noticeType">
          <el-select v-model="form.noticeType" style="width: 100%">
            <el-option label="联赛公告" value="NOTICE" />
            <el-option label="赛事快讯" value="MATCH" />
            <el-option label="球队动态" value="CLUB" />
          </el-select>
        </el-form-item>
        <el-form-item label="发布状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">已发布</el-radio>
            <el-radio :label="0">已下线</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="资讯内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="8" maxlength="500" show-word-limit />
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
import { deleteNews, getNewsList, getPublicNewsList, saveNews } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => (userStore.user?.role || '').toUpperCase() === 'ADMIN')

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
  noticeType: 'NOTICE',
  content: '',
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入资讯标题', trigger: 'blur' }],
  noticeType: [{ required: true, message: '请选择资讯类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入资讯内容', trigger: 'blur' }]
}

const typeTextMap = {
  NOTICE: '联赛公告',
  MATCH: '赛事快讯',
  CLUB: '球队动态'
}

const typeTagMap = {
  NOTICE: 'primary',
  MATCH: 'success',
  CLUB: 'warning'
}

const loadAdminData = async () => {
  loading.value = true
  try {
    const res = await getNewsList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  loading.value = true
  try {
    const res = await getPublicNewsList()
    publicList.value = res.data || []
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    title: '',
    noticeType: 'NOTICE',
    content: '',
    status: 1
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, {
    id: row.id,
    title: row.title,
    noticeType: row.noticeType || 'NOTICE',
    content: row.content,
    status: row.status
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveNews(form)
  ElMessage.success('资讯保存成功')
  dialogVisible.value = false
  loadAdminData()
}

const handleDelete = async id => {
  await deleteNews(id)
  ElMessage.success('资讯已删除')
  loadAdminData()
}

onMounted(() => {
  if (isAdmin.value) {
    loadAdminData()
    return
  }
  loadPublicData()
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

.news-list {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 16px;
}

.news-card {
  border-radius: 16px;
}

.card-head,
.news-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.publish-time,
.news-footer {
  color: #667085;
  font-size: 13px;
}

.news-title {
  margin: 18px 0 12px;
  font-size: 20px;
  color: #17324d;
}

.news-content {
  margin: 0;
  min-height: 90px;
  color: #475467;
  line-height: 1.8;
  white-space: pre-wrap;
}
</style>
