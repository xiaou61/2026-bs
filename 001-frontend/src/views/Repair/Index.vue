<template>
  <div class="repair-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>报修管理</span>
          <el-button
            v-if="userInfo?.role === 'student'"
            type="primary"
            @click="showSubmitDialog = true"
          >
            <el-icon><Plus /></el-icon>
            提交报修
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择" clearable>
            <el-option label="未处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已完成" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="searchForm.type" placeholder="报修类型" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchRepairList">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="studentName" label="报修人" width="120" />
        <el-table-column prop="type" label="报修类型" width="120" />
        <el-table-column prop="location" label="位置" width="150" />
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="warning">未处理</el-tag>
            <el-tag v-else-if="row.status === 1" type="primary">处理中</el-tag>
            <el-tag v-else type="success">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="报修时间" width="180" />
        <el-table-column
          v-if="userInfo?.role === 'admin'"
          label="操作"
          width="200"
          fixed="right"
        >
          <template #default="{ row }">
            <el-button
              v-if="row.status === 0"
              type="primary"
              size="small"
              @click="handleStatus(row.id, 1)"
            >
              处理中
            </el-button>
            <el-button
              v-if="row.status === 1"
              type="success"
              size="small"
              @click="handleStatus(row.id, 2)"
            >
              完成
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
        @size-change="fetchRepairList"
        @current-change="fetchRepairList"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <!-- 提交报修对话框 -->
    <el-dialog
      v-model="showSubmitDialog"
      title="提交报修"
      width="500px"
      @close="resetSubmitForm"
    >
      <el-form
        ref="submitFormRef"
        :model="submitForm"
        :rules="submitRules"
        label-width="100px"
      >
        <el-form-item label="报修类型" prop="type">
          <el-select v-model="submitForm.type" placeholder="请选择" style="width: 100%">
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
          />
        </el-form-item>
        <el-form-item label="问题描述" prop="description">
          <el-input
            v-model="submitForm.description"
            type="textarea"
            :rows="4"
            placeholder="请详细描述问题"
          />
        </el-form-item>
        <el-form-item label="图片" prop="imageUrl">
          <el-input
            v-model="submitForm.imageUrl"
            placeholder="图片URL（可选）"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showSubmitDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          提交
        </el-button>
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

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>

