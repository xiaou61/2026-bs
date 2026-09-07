<template>
  <div class="leave-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><Calendar /></el-icon>
        <span>请假管理</span>
      </div>
      <el-button
        v-if="userInfo?.role === 'student'"
        type="primary"
        @click="showApplyDialog = true"
        class="apply-btn"
      >
        <el-icon><Plus /></el-icon>
        申请请假
      </el-button>
    </div>

    <el-card class="main-card">
      <!-- 搜索栏 -->
      <div class="search-section">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable class="status-select">
              <el-option label="待审批" :value="0" />
              <el-option label="已通过" :value="1" />
              <el-option label="已驳回" :value="2" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="fetchLeaveList" class="search-btn">
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
          <el-table-column prop="studentName" label="学生姓名" width="120" align="center" />
          <el-table-column prop="reason" label="请假理由" min-width="200" show-overflow-tooltip />
          <el-table-column prop="startTime" label="开始时间" width="180" align="center" />
          <el-table-column prop="endTime" label="结束时间" width="180" align="center" />
          <el-table-column prop="status" label="状态" width="120" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.status === 0" type="warning" class="status-tag">待审批</el-tag>
              <el-tag v-else-if="row.status === 1" type="success" class="status-tag">已通过</el-tag>
              <el-tag v-else type="danger" class="status-tag">已驳回</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="approverName" label="审批人" width="120" align="center" />
          <el-table-column prop="createTime" label="申请时间" width="180" align="center" />
          <el-table-column
            v-if="userInfo?.role !== 'student'"
            label="操作"
            width="200"
            fixed="right"
            align="center"
          >
            <template #default="{ row }">
              <el-button
                v-if="row.status === 0"
                type="success"
                size="small"
                @click="handleApprove(row.id, 1)"
                class="action-btn approve-btn"
              >
                <el-icon><Check /></el-icon>
                通过
              </el-button>
              <el-button
                v-if="row.status === 0"
                type="danger"
                size="small"
                @click="handleApprove(row.id, 2)"
                class="action-btn reject-btn"
              >
                <el-icon><Close /></el-icon>
                驳回
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
          @size-change="fetchLeaveList"
          @current-change="fetchLeaveList"
          class="custom-pagination"
        />
      </div>
    </el-card>

    <!-- 申请请假对话框 -->
    <el-dialog
      v-model="showApplyDialog"
      title="申请请假"
      width="500px"
      @close="resetApplyForm"
      class="custom-dialog"
    >
      <el-form
        ref="applyFormRef"
        :model="applyForm"
        :rules="applyRules"
        label-width="100px"
        class="apply-form"
      >
        <el-form-item label="请假理由" prop="reason">
          <el-input
            v-model="applyForm.reason"
            type="textarea"
            :rows="4"
            placeholder="请输入请假理由"
            class="custom-textarea"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="applyForm.startTime"
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
            v-model="applyForm.endTime"
            type="datetime"
            placeholder="选择结束时间"
            style="width: 100%"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            class="custom-picker"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showApplyDialog = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleApply" :loading="submitting" class="submit-btn">
            <el-icon><Check /></el-icon>
            提交申请
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { getLeaveList, applyLeave, approveLeave } from '@/api/leave'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const showApplyDialog = ref(false)
const applyFormRef = ref(null)

const searchForm = reactive({
  status: null
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const tableData = ref([])

const applyForm = reactive({
  reason: '',
  startTime: '',
  endTime: ''
})

const applyRules = {
  reason: [{ required: true, message: '请输入请假理由', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }]
}

// 获取请假列表
const fetchLeaveList = async () => {
  loading.value = true
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    }
    const res = await getLeaveList(params)
    if (res.code === 200) {
      tableData.value = res.data.records || []
      pagination.total = res.data.total || 0
    }
  } catch (error) {
    console.error('获取请假列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.status = null
  pagination.current = 1
  fetchLeaveList()
}

// 提交请假申请
const handleApply = async () => {
  if (!applyFormRef.value) return
  
  await applyFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const res = await applyLeave(applyForm)
        if (res.code === 200) {
          ElMessage.success('提交成功')
          showApplyDialog.value = false
          fetchLeaveList()
        }
      } catch (error) {
        console.error('提交失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 审批请假
const handleApprove = async (id, status) => {
  const text = status === 1 ? '通过' : '驳回'
  
  ElMessageBox.confirm(`确定${text}该请假申请吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const res = await approveLeave({ id, status })
      if (res.code === 200) {
        ElMessage.success(`${text}成功`)
        fetchLeaveList()
      }
    } catch (error) {
      console.error('审批失败:', error)
    }
  }).catch(() => {})
}

// 重置申请表单
const resetApplyForm = () => {
  if (applyFormRef.value) {
    applyFormRef.value.resetFields()
  }
}

onMounted(() => {
  fetchLeaveList()
})
</script>

<style scoped>
.leave-container {
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
  color: #2E7D32;
  font-size: 28px;
}

.apply-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
  transition: all 0.3s ease;
}

.apply-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(46, 125, 50, 0.4);
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

.search-btn {
  height: 40px;
  padding: 0 20px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 100%) !important;
  border: none !important;
}

.search-btn:hover {
  background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 100%) !important;
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

.status-tag {
  border-radius: 20px !important;
  padding: 0 16px !important;
  font-weight: 500;
}

.action-btn {
  border-radius: 6px !important;
  font-weight: 500;
  transition: all 0.3s ease;
}

.approve-btn {
  background: linear-gradient(135deg, #67C23A 0%, #85ce61 100%) !important;
  border: none !important;
}

.approve-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(103, 194, 58, 0.4);
}

.reject-btn {
  background: linear-gradient(135deg, #F56C6C 0%, #f78989 100%) !important;
  border: none !important;
}

.reject-btn:hover {
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
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 100%);
  color: white;
}

/* 自定义对话框 */
.custom-dialog :deep(.el-dialog) {
  border-radius: 20px !important;
  overflow: hidden;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #2E7D32 0%, #1565C0 100%);
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

/* 申请表单 */
.apply-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #2C3E50;
}

.custom-textarea :deep(.el-textarea__inner) {
  border-radius: 10px;
  padding: 12px 16px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.custom-textarea :deep(.el-textarea__inner:focus) {
  box-shadow: 0 0 0 3px rgba(46, 125, 50, 0.2);
}

.custom-picker :deep(.el-input__wrapper) {
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
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(46, 125, 50, 0.4);
}

/* 响应式 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
  
  .apply-btn {
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

