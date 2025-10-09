<template>
  <div class="notice-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>公告中心</span>
          <el-button
            v-if="userInfo?.role !== 'student'"
            type="primary"
            @click="showAddDialog = true"
          >
            <el-icon><Plus /></el-icon>
            发布公告
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="分类">
          <el-select v-model="searchForm.category" placeholder="请选择" clearable>
            <el-option label="通知" value="通知" />
            <el-option label="活动" value="活动" />
            <el-option label="系统" value="系统" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="searchForm.title" placeholder="请输入标题" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchNoticeList">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="200">
          <template #default="{ row }">
            <el-link type="primary" @click="toDetail(row.id)">
              <el-icon v-if="row.isTop"><Top /></el-icon>
              {{ row.title }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="category" label="分类" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.category === '通知'" type="primary">{{ row.category }}</el-tag>
            <el-tag v-else-if="row.category === '活动'" type="success">{{ row.category }}</el-tag>
            <el-tag v-else type="info">{{ row.category }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="authorName" label="发布人" width="120" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column
          v-if="userInfo?.role !== 'student'"
          label="操作"
          width="250"
          fixed="right"
        >
          <template #default="{ row }">
            <el-button
              v-if="userInfo?.role === 'admin'"
              :type="row.isTop ? 'warning' : 'info'"
              size="small"
              @click="handleToggleTop(row.id)"
            >
              {{ row.isTop ? '取消置顶' : '置顶' }}
            </el-button>
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="fetchNoticeList"
        @current-change="fetchNoticeList"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- 添加/编辑公告对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="editingNotice ? '编辑公告' : '发布公告'"
      width="700px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="noticeForm"
        :rules="noticeRules"
        label-width="100px"
      >
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="noticeForm.title" placeholder="请输入公告标题" />
        </el-form-item>
        <el-form-item label="公告分类" prop="category">
          <el-select v-model="noticeForm.category" placeholder="请选择" style="width: 100%">
            <el-option label="通知" value="通知" />
            <el-option label="活动" value="活动" />
            <el-option label="系统" value="系统" />
          </el-select>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input
            v-model="noticeForm.content"
            type="textarea"
            :rows="8"
            placeholder="请输入公告内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          {{ editingNotice ? '更新' : '发布' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getNoticeList, addNotice, updateNotice, deleteNotice, toggleTopNotice } from '@/api/notice'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const showAddDialog = ref(false)
const formRef = ref(null)
const editingNotice = ref(null)

const searchForm = reactive({
  category: '',
  title: ''
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const tableData = ref([])

const noticeForm = reactive({
  title: '',
  category: '',
  content: ''
})

const noticeRules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
  category: [{ required: true, message: '请选择公告分类', trigger: 'change' }],
  content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
}

// 获取公告列表
const fetchNoticeList = async () => {
  loading.value = true
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    }
    const res = await getNoticeList(params)
    if (res.code === 200) {
      tableData.value = res.data.records || []
      pagination.total = res.data.total || 0
    }
  } catch (error) {
    console.error('获取公告列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.category = ''
  searchForm.title = ''
  pagination.current = 1
  fetchNoticeList()
}

// 提交公告
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        let res
        if (editingNotice.value) {
          res = await updateNotice({
            id: editingNotice.value.id,
            ...noticeForm
          })
        } else {
          res = await addNotice(noticeForm)
        }
        
        if (res.code === 200) {
          ElMessage.success(editingNotice.value ? '更新成功' : '发布成功')
          showAddDialog.value = false
          fetchNoticeList()
        }
      } catch (error) {
        console.error('操作失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 编辑公告
const handleEdit = (row) => {
  editingNotice.value = row
  noticeForm.title = row.title
  noticeForm.category = row.category
  noticeForm.content = row.content
  showAddDialog.value = true
}

// 删除公告
const handleDelete = async (id) => {
  ElMessageBox.confirm('确定删除该公告吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const res = await deleteNotice(id)
      if (res.code === 200) {
        ElMessage.success('删除成功')
        fetchNoticeList()
      }
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

// 置顶公告
const handleToggleTop = async (id) => {
  try {
    const res = await toggleTopNotice(id)
    if (res.code === 200) {
      ElMessage.success('操作成功')
      fetchNoticeList()
    }
  } catch (error) {
    console.error('操作失败:', error)
  }
}

// 查看详情
const toDetail = (id) => {
  router.push(`/notice/${id}`)
}

// 重置表单
const resetForm = () => {
  editingNotice.value = null
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

onMounted(() => {
  fetchNoticeList()
})
</script>

<style scoped>
.notice-container {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>

