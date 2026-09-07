<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.keyword" placeholder="标题关键词" style="width: 220px" clearable />
      <el-select v-model="query.categoryId" placeholder="分类" style="width: 160px" clearable>
        <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="草稿" :value="0" />
        <el-option label="已发布" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">写文章</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="title" label="标题" min-width="220" />
      <el-table-column prop="categoryName" label="分类" width="120" />
      <el-table-column v-if="isAdmin" prop="authorName" label="作者" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已发布' : '草稿' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="置顶" width="90">
        <template #default="{ row }"><el-tag :type="row.isTop === 1 ? 'warning' : ''">{{ row.isTop === 1 ? '是' : '否' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="viewCount" label="浏览" width="90" />
      <el-table-column prop="commentCount" label="评论" width="90" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
      <el-table-column label="操作" width="320">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-button link type="primary" @click="changeStatus(row)">{{ row.status === 1 ? '转草稿' : '发布' }}</el-button>
          <el-button v-if="isAdmin" link type="warning" @click="changeTop(row)">{{ row.isTop === 1 ? '取消置顶' : '置顶' }}</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑文章' : '新增文章'" width="760px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="150" /></el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="form.categoryId" style="width: 100%">
          <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="标签">
        <el-select v-model="form.tagIds" multiple clearable style="width: 100%">
          <el-option v-for="item in tags" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="摘要"><el-input v-model="form.summary" maxlength="300" /></el-form-item>
      <el-form-item label="封面地址"><el-input v-model="form.cover" maxlength="255" /></el-form-item>
      <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="8" /></el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">草稿</el-radio>
          <el-radio :label="1">发布</el-radio>
        </el-radio-group>
      </el-form-item>
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
import { addPost, deletePost, getCategoryList, getPostPage, getTagList, updatePost, updatePostStatus, updatePostTop } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const tags = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', categoryId: null, status: null })
const form = reactive({})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const loadBase = async () => {
  const [cRes, tRes] = await Promise.all([getCategoryList(), getTagList()])
  categories.value = cRes.data || []
  tags.value = tRes.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPostPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, title: '', categoryId: null, tagIds: [], summary: '', cover: '', content: '', status: 0 })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadBase()
  Object.assign(form, {
    id: row.id,
    title: row.title,
    categoryId: row.categoryId,
    tagIds: row.tagIds || [],
    summary: row.summary || '',
    cover: row.cover || '',
    content: row.content || '',
    status: row.status
  })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updatePost(form)
  } else {
    await addPost(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const changeStatus = async (row) => {
  await updatePostStatus({ id: row.id, status: row.status === 1 ? 0 : 1 })
  ElMessage.success('状态已更新')
  loadData()
}

const changeTop = async (row) => {
  await updatePostTop({ id: row.id, isTop: row.isTop === 1 ? 0 : 1 })
  ElMessage.success('置顶状态已更新')
  loadData()
}

const handleDelete = async (id) => {
  await deletePost(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
