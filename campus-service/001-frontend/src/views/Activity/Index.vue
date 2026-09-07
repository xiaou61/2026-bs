<template>
  <div class="activity-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><Calendar /></el-icon>
        <span>活动中心</span>
      </div>
      <el-button
        v-if="userInfo?.role !== 'student'"
        type="primary"
        @click="showAddDialog = true"
        class="publish-btn"
      >
        <el-icon><Plus /></el-icon>
        发布活动
      </el-button>
    </div>

    <el-card class="main-card">
      <!-- 搜索栏 -->
      <div class="search-section">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable class="status-select">
              <el-option label="未开始" value="未开始" />
              <el-option label="进行中" value="进行中" />
              <el-option label="已结束" value="已结束" />
            </el-select>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="searchForm.title" placeholder="请输入标题" clearable class="title-input" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="fetchActivityList" class="search-btn">
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
          <el-table-column prop="title" label="活动标题" min-width="250">
            <template #default="{ row }">
              <el-link type="primary" @click="toDetail(row.id)" class="title-link">
                {{ row.title }}
              </el-link>
            </template>
          </el-table-column>
          <el-table-column prop="location" label="地点" width="150" align="center">
            <template #default="{ row }">
              <div class="location-info">
                <el-icon><Location /></el-icon>
                <span>{{ row.location }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="startTime" label="开始时间" width="180" align="center" />
          <el-table-column prop="endTime" label="结束时间" width="180" align="center" />
          <el-table-column prop="maxParticipants" label="限制人数" width="100" align="center" />
          <el-table-column prop="currentParticipants" label="已报名" width="100" align="center">
            <template #default="{ row }">
              <div class="participants-info">
                <el-icon><User /></el-icon>
                <span class="current">{{ row.currentParticipants || 0 }}</span>
                <span class="separator">/</span>
                <span class="max">{{ row.maxParticipants }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="120" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.status === '未开始'" type="info" class="status-tag">未开始</el-tag>
              <el-tag v-else-if="row.status === '进行中'" type="success" class="status-tag">进行中</el-tag>
              <el-tag v-else type="warning" class="status-tag">已结束</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            v-if="userInfo?.role !== 'student'"
            label="操作"
            width="200"
            fixed="right"
            align="center"
          >
            <template #default="{ row }">
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
          @size-change="fetchActivityList"
          @current-change="fetchActivityList"
          class="custom-pagination"
        />
      </div>
    </el-card>

    <!-- 添加/编辑活动对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="editingActivity ? '编辑活动' : '发布活动'"
      width="700px"
      @close="resetForm"
      class="custom-dialog"
    >
      <el-form
        ref="formRef"
        :model="activityForm"
        :rules="activityRules"
        label-width="100px"
        class="activity-form"
      >
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="activityForm.title" placeholder="请输入活动标题" class="custom-input" />
        </el-form-item>
        <el-form-item label="活动地点" prop="location">
          <el-input v-model="activityForm.location" placeholder="请输入活动地点" class="custom-input" />
        </el-form-item>
        <el-form-item label="活动描述" prop="description">
          <el-input
            v-model="activityForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入活动描述"
            class="custom-textarea"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="activityForm.startTime"
            type="datetime"
            placeholder="选择开始时间"
            style="width: 100%"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            class="custom-picker"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="activityForm.endTime"
            type="datetime"
            placeholder="选择结束时间"
            style="width: 100%"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            class="custom-picker"
          />
        </el-form-item>
        <el-form-item label="限制人数" prop="maxParticipants">
          <el-input-number
            v-model="activityForm.maxParticipants"
            :min="1"
            :max="10000"
            style="width: 100%"
            class="custom-number"
          />
        </el-form-item>
        <el-form-item label="活动状态" prop="status">
          <el-select v-model="activityForm.status" placeholder="请选择状态" style="width: 100%" class="custom-select">
            <el-option label="未开始" value="未开始" />
            <el-option label="进行中" value="进行中" />
            <el-option label="已结束" value="已结束" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showAddDialog = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitting" class="submit-btn">
            <el-icon><Check /></el-icon>
            {{ editingActivity ? '更新活动' : '发布活动' }}
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
import { getActivityList, addActivity, updateActivity, deleteActivity } from '@/api/activity'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const showAddDialog = ref(false)
const formRef = ref(null)
const editingActivity = ref(null)

const searchForm = reactive({
  status: '',
  title: ''
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const tableData = ref([])

const activityForm = reactive({
  title: '',
  location: '',
  description: '',
  startTime: '',
  endTime: '',
  maxParticipants: 100,
  status: '未开始'
})

const activityRules = {
  title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
  location: [{ required: true, message: '请输入活动地点', trigger: 'blur' }],
  description: [{ required: true, message: '请输入活动描述', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }],
  maxParticipants: [{ required: true, message: '请输入限制人数', trigger: 'blur' }],
  status: [{ required: true, message: '请选择活动状态', trigger: 'change' }]
}

// 获取活动列表
const fetchActivityList = async () => {
  loading.value = true
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    }
    const res = await getActivityList(params)
    if (res.code === 200) {
      tableData.value = res.data.records || []
      pagination.total = res.data.total || 0
    }
  } catch (error) {
    console.error('获取活动列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.status = ''
  searchForm.title = ''
  pagination.current = 1
  fetchActivityList()
}

// 提交活动
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        let res
        if (editingActivity.value) {
          res = await updateActivity({
            id: editingActivity.value.id,
            ...activityForm
          })
        } else {
          res = await addActivity(activityForm)
        }
        
        if (res.code === 200) {
          ElMessage.success(editingActivity.value ? '更新成功' : '发布成功')
          showAddDialog.value = false
          fetchActivityList()
        }
      } catch (error) {
        console.error('操作失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 编辑活动
const handleEdit = (row) => {
  editingActivity.value = row
  Object.assign(activityForm, {
    title: row.title,
    location: row.location,
    description: row.description,
    startTime: row.startTime,
    endTime: row.endTime,
    maxParticipants: row.maxParticipants,
    status: row.status
  })
  showAddDialog.value = true
}

// 删除活动
const handleDelete = async (id) => {
  ElMessageBox.confirm('确定删除该活动吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const res = await deleteActivity(id)
      if (res.code === 200) {
        ElMessage.success('删除成功')
        fetchActivityList()
      }
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

// 查看详情
const toDetail = (id) => {
  router.push(`/activity/${id}`)
}

// 重置表单
const resetForm = () => {
  editingActivity.value = null
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

onMounted(() => {
  fetchActivityList()
})
</script>

<style scoped>
.activity-container {
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
  color: #FF6F00;
  font-size: 28px;
}

.publish-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(255, 111, 0, 0.3);
  transition: all 0.3s ease;
}

.publish-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(255, 111, 0, 0.4);
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

.status-select {
  width: 160px;
}

.status-select :deep(.el-input__wrapper) {
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
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%) !important;
  border: none !important;
}

.search-btn:hover {
  background: linear-gradient(135deg, #E65100 0%, #FF8F00 100%) !important;
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
}

.location-info {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  color: #606266;
}

.location-info .el-icon {
  color: #FF6F00;
  font-size: 14px;
}

.participants-info {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
}

.participants-info .el-icon {
  color: #FF6F00;
  font-size: 14px;
}

.participants-info .current {
  color: #FF6F00;
  font-weight: 600;
}

.participants-info .separator {
  color: #C0C4CC;
}

.participants-info .max {
  color: #909399;
}

.status-tag {
  border-radius: 20px !important;
  padding: 0 16px !important;
  font-weight: 500;
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
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%);
  color: white;
}

/* 自定义对话框 */
.custom-dialog :deep(.el-dialog) {
  border-radius: 20px !important;
  overflow: hidden;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%);
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

/* 活动表单 */
.activity-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #2C3E50;
}

.custom-input :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.custom-textarea :deep(.el-textarea__inner) {
  border-radius: 10px;
  padding: 12px 16px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.custom-textarea :deep(.el-textarea__inner:focus) {
  box-shadow: 0 0 0 3px rgba(255, 111, 0, 0.2);
}

.custom-picker :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.custom-number :deep(.el-input-number__decrease),
.custom-number :deep(.el-input-number__increase) {
  border-radius: 8px;
}

.custom-select :deep(.el-input__wrapper) {
  border-radius: 8px;
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
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(255, 111, 0, 0.3);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(255, 111, 0, 0.4);
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

