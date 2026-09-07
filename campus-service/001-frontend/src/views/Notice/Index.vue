<template>
  <div class="notice-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><Bell /></el-icon>
        <span>公告中心</span>
      </div>
      <el-button
        v-if="userInfo?.role !== 'student'"
        type="primary"
        @click="showAddDialog = true"
        class="publish-btn"
      >
        <el-icon><Plus /></el-icon>
        发布公告
      </el-button>
    </div>

    <el-card class="main-card">
      <!-- 搜索栏 -->
      <div class="search-section">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="分类">
            <el-select v-model="searchForm.category" placeholder="请选择分类" clearable class="category-select">
              <el-option label="通知" value="通知" />
              <el-option label="活动" value="活动" />
              <el-option label="系统" value="系统" />
            </el-select>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="searchForm.title" placeholder="请输入标题" clearable class="title-input" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="fetchNoticeList" class="search-btn">
              <el-icon><Search /></el-icon>
              查询
            </el-button>
            <el-button @click="resetSearch" class="reset-btn">
              <el-icon><Refresh /></el-icon>
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 表格 -->
      <div class="table-container">
        <el-table :data="tableData" v-loading="loading" class="custom-table" stripe>
          <el-table-column prop="id" label="ID" width="80" align="center" />
          <el-table-column prop="title" label="标题" min-width="250">
            <template #default="{ row }">
              <el-link type="primary" @click="toDetail(row.id)" class="title-link">
                <el-icon v-if="row.isTop" class="top-icon"><Top /></el-icon>
                {{ row.title }}
              </el-link>
            </template>
          </el-table-column>
          <el-table-column prop="category" label="分类" width="120" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.category === '通知'" type="primary" class="category-tag">通知</el-tag>
              <el-tag v-else-if="row.category === '活动'" type="success" class="category-tag">活动</el-tag>
              <el-tag v-else type="info" class="category-tag">系统</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="authorName" label="发布人" width="120" align="center" />
          <el-table-column prop="viewCount" label="浏览量" width="100" align="center">
            <template #default="{ row }">
              <div class="view-count">
                <el-icon><View /></el-icon>
                <span>{{ row.viewCount }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="发布时间" width="180" align="center" />
          <el-table-column
            v-if="userInfo?.role !== 'student'"
            label="操作"
            width="250"
            fixed="right"
            align="center"
          >
            <template #default="{ row }">
              <el-button
                v-if="userInfo?.role === 'admin'"
                :type="row.isTop ? 'warning' : 'info'"
                size="small"
                @click="handleToggleTop(row.id)"
                class="action-btn top-btn"
              >
                <el-icon><Top /></el-icon>
                {{ row.isTop ? '取消置顶' : '置顶' }}
              </el-button>
              <el-button
                type="primary"
                size="small"
                @click="handleEdit(row)"
                class="action-btn edit-btn"
              >
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button
                type="danger"
                size="small"
                @click="handleDelete(row.id)"
                class="action-btn delete-btn"
              >
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetchNoticeList"
          @current-change="fetchNoticeList"
          class="custom-pagination"
        />
      </div>
    </el-card>

    <!-- 添加/编辑公告对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="editingNotice ? '编辑公告' : '发布公告'"
      width="700px"
      @close="resetForm"
      class="custom-dialog"
    >
      <el-form
        ref="formRef"
        :model="noticeForm"
        :rules="noticeRules"
        label-width="100px"
        class="notice-form"
      >
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="noticeForm.title" placeholder="请输入公告标题" class="custom-input" />
        </el-form-item>
        <el-form-item label="公告分类" prop="category">
          <el-select v-model="noticeForm.category" placeholder="请选择分类" style="width: 100%" class="custom-select">
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
            class="custom-textarea"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showAddDialog = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitting" class="submit-btn">
            <el-icon><Check /></el-icon>
            {{ editingNotice ? '更新公告' : '发布公告' }}
          </el-button>
        </div>
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

/* 页面标题 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding: 0 4px;
}

.page-title {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 24px;
  font-weight: 700;
  color: #2C3E50;
  font-family: var(--font-heading);
}

.page-title .el-icon {
  color: #7B1FA2;
  font-size: 28px;
}

.publish-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(123, 31, 162, 0.3);
  transition: all 0.3s ease;
}

.publish-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(123, 31, 162, 0.4);
}

/* 主卡片 */
.main-card {
  border-radius: 16px !important;
  border: none !important;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08) !important;
}

.main-card :deep(.el-card__body) {
  padding: 24px;
}

/* 搜索区域 */
.search-section {
  margin-bottom: 24px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 12px;
}

.search-form {
  margin-bottom: 0;
}

.search-form :deep(.el-form-item) {
  margin-bottom: 0;
}

.search-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #606266;
}

.category-select {
  width: 160px;
}

.category-select :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.title-input {
  width: 200px;
}

.title-input :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.search-btn {
  height: 40px;
  padding: 0 20px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%) !important;
  border: none !important;
}

.search-btn:hover {
  background: linear-gradient(135deg, #6A1B9A 0%, #9C27B0 100%) !important;
}

.reset-btn {
  height: 40px;
  padding: 0 20px;
  font-weight: 500;
  border-radius: 8px;
  margin-left: 12px;
}

/* 表格容器 */
.table-container {
  margin-bottom: 24px;
}

/* 自定义表格 */
.custom-table {
  border-radius: 12px !important;
  overflow: hidden;
}

.custom-table :deep(.el-table__header) {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.custom-table :deep(.el-table__header th) {
  color: #2C3E50;
  font-weight: 600;
  border-bottom: 2px solid #dee2e6;
  padding: 16px 12px;
}

.custom-table :deep(.el-table__row) {
  transition: all 0.3s ease;
}

.custom-table :deep(.el-table__row:hover) {
  background: #f8f9fa;
}

.custom-table :deep(.el-table__row--striped) {
  background: #fafbfc;
}

.custom-table :deep(.el-table__row--striped:hover) {
  background: #f8f9fa;
}

.title-link {
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.top-icon {
  color: #E6A23C;
  font-size: 16px;
}

.category-tag {
  border-radius: 20px !important;
  padding: 0 12px !important;
  font-weight: 500;
}

.view-count {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  color: #909399;
}

.view-count .el-icon {
  font-size: 14px;
}

.action-btn {
  border-radius: 6px !important;
  font-weight: 500;
  transition: all 0.3s ease;
  margin-left: 8px;
}

.action-btn:first-child {
  margin-left: 0;
}

.top-btn {
  background: linear-gradient(135deg, #E6A23C 0%, #F0C78A 100%) !important;
  border: none !important;
}

.top-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(230, 162, 60, 0.4);
}

.edit-btn {
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%) !important;
  border: none !important;
}

.edit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
}

.delete-btn {
  background: linear-gradient(135deg, #F56C6C 0%, #f78989 100%) !important;
  border: none !important;
}

.delete-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(245, 108, 108, 0.4);
}

/* 分页容器 */
.pagination-container {
  display: flex;
  justify-content: flex-end;
  padding: 16px 0;
}

.custom-pagination :deep(.el-pagination__total) {
  font-weight: 500;
  color: #606266;
}

.custom-pagination :deep(.el-pager li) {
  border-radius: 6px;
  font-weight: 500;
}

.custom-pagination :deep(.el-pager li.is-active) {
  background: linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%);
  color: white;
}

/* 自定义对话框 */
.custom-dialog :deep(.el-dialog) {
  border-radius: 20px !important;
  overflow: hidden;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%);
  padding: 20px 24px;
  margin: 0;
}

.custom-dialog :deep(.el-dialog__title) {
  color: white;
  font-weight: 600;
}

.custom-dialog :deep(.el-dialog__headerbtn .el-dialog__close) {
  color: white;
}

.custom-dialog :deep(.el-dialog__body) {
  padding: 24px;
}

/* 公告表单 */
.notice-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #2C3E50;
}

.custom-input :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.custom-select :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.custom-textarea :deep(.el-textarea__inner) {
  border-radius: 10px;
  padding: 12px 16px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.custom-textarea :deep(.el-textarea__inner:focus) {
  box-shadow: 0 0 0 3px rgba(123, 31, 162, 0.2);
}

/* 对话框底部 */
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.cancel-btn {
  height: 40px;
  padding: 0 24px;
  font-weight: 500;
  border-radius: 8px;
}

.submit-btn {
  height: 40px;
  padding: 0 24px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(123, 31, 162, 0.3);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(123, 31, 162, 0.4);
}

/* 响应式 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
  
  .publish-btn {
    width: 100%;
  }
  
  .search-section {
    padding: 16px;
  }
  
  .search-form :deep(.el-form-item) {
    margin-bottom: 12px;
  }
  
  .search-form :deep(.el-form-item:last-child) {
    margin-bottom: 0;
  }
}
</style>

