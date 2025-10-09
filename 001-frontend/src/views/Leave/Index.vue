<template>
  <div class="leave-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>请假管理</span>
          <el-button
            v-if="userInfo?.role === 'student'"
            type="primary"
            @click="showApplyDialog = true"
          >
            <el-icon><Plus /></el-icon>
            申请请假
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择" clearable>
            <el-option label="待审批" :value="0" />
            <el-option label="已通过" :value="1" />
            <el-option label="已驳回" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchLeaveList">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="studentName" label="学生姓名" width="120" />
        <el-table-column prop="reason" label="请假理由" min-width="150" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="warning">待审批</el-tag>
            <el-tag v-else-if="row.status === 1" type="success">已通过</el-tag>
            <el-tag v-else type="danger">已驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="approverName" label="审批人" width="120" />
        <el-table-column prop="createTime" label="申请时间" width="180" />
        <el-table-column
          v-if="userInfo?.role !== 'student'"
          label="操作"
          width="200"
          fixed="right"
        >
          <template #default="{ row }">
            <el-button
              v-if="row.status === 0"
              type="success"
              size="small"
              @click="handleApprove(row.id, 1)"
            >
              通过
            </el-button>
            <el-button
              v-if="row.status === 0"
              type="danger"
              size="small"
              @click="handleApprove(row.id, 2)"
            >
              驳回
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
        @size-change="fetchLeaveList"
        @current-change="fetchLeaveList"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- 申请请假对话框 -->
    <el-dialog
      v-model="showApplyDialog"
      title="申请请假"
      width="500px"
      @close="resetApplyForm"
    >
      <el-form
        ref="applyFormRef"
        :model="applyForm"
        :rules="applyRules"
        label-width="100px"
      >
        <el-form-item label="请假理由" prop="reason">
          <el-input
            v-model="applyForm.reason"
            type="textarea"
            :rows="3"
            placeholder="请输入请假理由"
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
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showApplyDialog = false">取消</el-button>
        <el-button type="primary" @click="handleApply" :loading="submitting">
          提交
        </el-button>
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

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>

