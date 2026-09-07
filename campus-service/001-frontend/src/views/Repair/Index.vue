<template>
  <div class="repair-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><Tools /></el-icon>
        <span>报修管理</span>
      </div>
      <el-button
        v-if="userInfo?.role === 'student'"
        type="primary"
        @click="showSubmitDialog = true"
        class="submit-btn"
      >
        <el-icon><Plus /></el-icon>
        提交报修
      </el-button>
    </div>

    <el-card class="main-card">
      <!-- 搜索栏 -->
      <div class="search-section">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable class="status-select">
              <el-option label="未处理" :value="0" />
              <el-option label="处理中" :value="1" />
              <el-option label="已完成" :value="2" />
            </el-select>
          </el-form-item>
          <el-form-item label="类型">
            <el-input v-model="searchForm.type" placeholder="报修类型" clearable class="type-input" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="fetchRepairList" class="search-btn">
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
          <el-table-column prop="studentName" label="报修人" width="120" align="center" />
          <el-table-column prop="type" label="报修类型" width="120" align="center">
            <template #default="{ row }">
              <el-tag class="type-tag" effect="plain">{{ row.type }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="location" label="位置" width="150" align="center" />
          <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
          <el-table-column prop="status" label="状态" width="120" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.status === 0" type="warning" class="status-tag">未处理</el-tag>
              <el-tag v-else-if="row.status === 1" type="primary" class="status-tag">处理中</el-tag>
              <el-tag v-else type="success" class="status-tag">已完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="报修时间" width="180" align="center" />
          <el-table-column
            v-if="userInfo?.role === 'admin'"
            label="操作"
            width="200"
            fixed="right"
            align="center"
          >
            <template #default="{ row }">
              <el-button
                v-if="row.status === 0"
                type="primary"
                size="small"
                @click="handleStatus(row.id, 1)"
                class="action-btn process-btn"
              >
                <el-icon><Edit /></el-icon>
                处理中
              </el-button>
              <el-button
                v-if="row.status === 1"
                type="success"
                size="small"
                @click="handleStatus(row.id, 2)"
                class="action-btn complete-btn"
              >
                <el-icon><Check /></el-icon>
                完成
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
          @size-change="fetchRepairList"
          @current-change="fetchRepairList"
          class="custom-pagination"
        />
      </div>
    </el-card>

    <!-- 提交报修对话框 -->
    <el-dialog
      v-model="showSubmitDialog"
      title="提交报修"
      width="500px"
      @close="resetSubmitForm"
      class="custom-dialog"
    >
      <el-form
        ref="submitFormRef"
        :model="submitForm"
        :rules="submitRules"
        label-width="100px"
        class="submit-form"
      >
        <el-form-item label="报修类型" prop="type">
          <el-select v-model="submitForm.type" placeholder="请选择报修类型" style="width: 100%" class="custom-select">
            <el-option label="宿舍设施" value="宿舍设施" />
            <el-option label="教室设备" value="教室设备" />
            <el-option label="水电维修" value="水电维修" />
            <el-option label="网络问题" value="网络问题" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input
            v-model="submitForm.location"
            placeholder="请输入具体位置，如：1号楼201室"
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="问题描述" prop="description">
          <el-input
            v-model="submitForm.description"
            type="textarea"
            :rows="4"
            placeholder="请详细描述问题"
            class="custom-textarea"
          />
        </el-form-item>
        <el-form-item label="图片" prop="imageUrl">
          <el-input
            v-model="submitForm.imageUrl"
            placeholder="图片URL（可选）"
            class="custom-input"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showSubmitDialog = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitting" class="submit-btn">
            <el-icon><Check /></el-icon>
            提交报修
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { getRepairList, submitRepair, handleRepair } from '@/api/repair'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const showSubmitDialog = ref(false)
const submitFormRef = ref(null)

const searchForm = reactive({
  status: null,
  type: ''
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const tableData = ref([])

const submitForm = reactive({
  type: '',
  location: '',
  description: '',
  imageUrl: ''
})

const submitRules = {
  type: [{ required: true, message: '请选择报修类型', trigger: 'change' }],
  location: [{ required: true, message: '请输入位置', trigger: 'blur' }],
  description: [{ required: true, message: '请输入问题描述', trigger: 'blur' }]
}

// 获取报修列表
const fetchRepairList = async () => {
  loading.value = true
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    }
    const res = await getRepairList(params)
    if (res.code === 200) {
      tableData.value = res.data.records || []
      pagination.total = res.data.total || 0
    }
  } catch (error) {
    console.error('获取报修列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.status = null
  searchForm.type = ''
  pagination.current = 1
  fetchRepairList()
}

// 提交报修
const handleSubmit = async () => {
  if (!submitFormRef.value) return
  
  await submitFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const res = await submitRepair(submitForm)
        if (res.code === 200) {
          ElMessage.success('提交成功')
          showSubmitDialog.value = false
          fetchRepairList()
        }
      } catch (error) {
        console.error('提交失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 处理报修状态
const handleStatus = async (id, status) => {
  const text = status === 1 ? '设置为处理中' : '标记为已完成'
  
  ElMessageBox.confirm(`确定${text}吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const res = await handleRepair({ id, status })
      if (res.code === 200) {
        ElMessage.success('操作成功')
        fetchRepairList()
      }
    } catch (error) {
      console.error('操作失败:', error)
    }
  }).catch(() => {})
}

// 重置提交表单
const resetSubmitForm = () => {
  if (submitFormRef.value) {
    submitFormRef.value.resetFields()
  }
}

onMounted(() => {
  fetchRepairList()
})
</script>

<style scoped>
.repair-container {
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
  color: #1565C0;
  font-size: 28px;
}

.submit-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #1565C0 0%, #42A5F5 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(21, 101, 192, 0.3);
  transition: all 0.3s ease;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(21, 101, 192, 0.4);
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

.type-input {
  width: 160px;
}

.type-input :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.search-btn {
  height: 40px;
  padding: 0 20px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #1565C0 0%, #42A5F5 100%) !important;
  border: none !important;
}

.search-btn:hover {
  background: linear-gradient(135deg, #0D47A1 0%, #1565C0 100%) !important;
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

.type-tag {
  border-radius: 20px !important;
  padding: 0 12px !important;
  font-weight: 500;
  background: #E3F2FD !important;
  color: #1565C0 !important;
  border: 1px solid #BBDEFB !important;
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

.process-btn {
  background: linear-gradient(135deg, #42A5F5 0%, #64B5F6 100%) !important;
  border: none !important;
}

.process-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(66, 165, 245, 0.4);
}

.complete-btn {
  background: linear-gradient(135deg, #67C23A 0%, #85ce61 100%) !important;
  border: none !important;
}

.complete-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(103, 194, 58, 0.4);
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
  background: linear-gradient(135deg, #1565C0 0%, #42A5F5 100%);
  color: white;
}

/* 自定义对话框 */
.custom-dialog :deep(.el-dialog) {
  border-radius: 20px !important;
  overflow: hidden;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #1565C0 0%, #42A5F5 100%);
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

/* 提交表单 */
.submit-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #2C3E50;
}

.custom-select :deep(.el-input__wrapper) {
  border-radius: 8px;
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
  box-shadow: 0 0 0 3px rgba(21, 101, 192, 0.2);
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
  background: linear-gradient(135deg, #1565C0 0%, #42A5F5 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(21, 101, 192, 0.3);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(21, 101, 192, 0.4);
}

/* 响应式 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
  
  .submit-btn {
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

