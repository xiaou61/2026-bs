<template>
  <div class="user-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-input v-model="searchKeyword" placeholder="搜索用户" style="width: 200px" clearable @change="loadUsers" />
        </div>
      </template>
      <el-table :data="userList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="realName" label="真实姓名" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="studentId" label="学号/工号" />
        <el-table-column prop="creditScore" label="信用分" width="100">
          <template #default="{ row }">
            <el-tag :type="row.creditScore >= 80 ? 'success' : row.creditScore >= 60 ? 'warning' : 'danger'">
              {{ row.creditScore }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="authStatus" label="认证状态" width="100">
          <template #default="{ row }">
            <el-tag :type="authStatusType[row.authStatus]">{{ authStatusText[row.authStatus] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="handleStatusChange(row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleAudit(row)" v-if="row.authStatus === 1">审核</el-button>
            <el-button size="small" @click="handleCredit(row)">调整信用</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" v-model:current-page="currentPage" @current-change="loadUsers" class="mt-20" />
    </el-card>
    
    <el-dialog v-model="creditDialogVisible" title="调整信用分">
      <el-form :model="creditForm">
        <el-form-item label="当前信用分">{{ creditForm.currentScore }}</el-form-item>
        <el-form-item label="调整值">
          <el-input-number v-model="creditForm.adjustValue" :min="-100" :max="100" />
        </el-form-item>
        <el-form-item label="调整原因">
          <el-input v-model="creditForm.reason" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="creditDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCreditAdjust">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { adminApi } from '@/api'

const userList = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const searchKeyword = ref('')
const creditDialogVisible = ref(false)
const creditForm = reactive({ userId: null, currentScore: 0, adjustValue: 0, reason: '' })

const authStatusText = { 0: '未认证', 1: '待审核', 2: '已认证', 3: '认证失败' }
const authStatusType = { 0: 'info', 1: 'warning', 2: 'success', 3: 'danger' }

const loadUsers = async () => {
  loading.value = true
  try {
    const res = await adminApi.getUserList({ page: currentPage.value, keyword: searchKeyword.value })
    userList.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleStatusChange = async (row) => {
  await adminApi.updateUserStatus(row.id, row.status)
  ElMessage.success('状态更新成功')
}

const handleAudit = async (row) => {
  const action = await ElMessageBox.confirm('是否通过该用户的实名认证?', '认证审核', {
    confirmButtonText: '通过',
    cancelButtonText: '拒绝',
    distinguishCancelAndClose: true
  }).catch(action => action)
  
  const status = action === 'confirm' ? 2 : 3
  await adminApi.auditUser(row.id, status)
  ElMessage.success('审核完成')
  loadUsers()
}

const handleCredit = (row) => {
  creditForm.userId = row.id
  creditForm.currentScore = row.creditScore
  creditForm.adjustValue = 0
  creditForm.reason = ''
  creditDialogVisible.value = true
}

const submitCreditAdjust = async () => {
  await adminApi.adjustCredit(creditForm.userId, creditForm.adjustValue, creditForm.reason)
  ElMessage.success('调整成功')
  creditDialogVisible.value = false
  loadUsers()
}

onMounted(() => loadUsers())
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
