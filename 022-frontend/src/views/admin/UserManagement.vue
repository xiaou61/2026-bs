<template>
  <div class="user-management">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-input v-model="keyword" placeholder="搜索姓名/学号/院系" style="width: 260px" @keyup.enter="loadUsers" />
    </div>

    <el-table :data="users" stripe v-loading="loading">
      <el-table-column prop="username" label="账号" width="140" />
      <el-table-column prop="realName" label="姓名" width="100" />
      <el-table-column prop="department" label="院系" />
      <el-table-column prop="grade" label="年级" width="100" />
      <el-table-column prop="phone" label="手机号" width="140" />
      <el-table-column prop="creditScore" label="信用分" width="100" />
      <el-table-column prop="role" label="角色" width="100" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-switch
            v-model="row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140">
        <template #default="{ row }">
          <el-button size="small" @click="openCreditDialog(row)">调整信用分</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="total > pageSize"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="handlePageChange"
      style="margin-top: 20px; text-align: center;"
    />

    <el-dialog v-model="creditDialogVisible" title="调整信用分" width="420px">
      <el-form :model="creditForm" label-width="90px">
        <el-form-item label="用户">
          <el-input :model-value="creditForm.realName" disabled />
        </el-form-item>
        <el-form-item label="变动分值">
          <el-input-number v-model="creditForm.scoreChange" :min="-20" :max="20" />
        </el-form-item>
        <el-form-item label="变动原因">
          <el-input v-model="creditForm.changeReason" type="textarea" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="creditDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdjustCredit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { adjustCreditScore, getUserList, updateUserStatus } from '@/api/user'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const users = ref([])
const keyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const creditDialogVisible = ref(false)

const creditForm = reactive({
  id: null,
  realName: '',
  scoreChange: 0,
  changeReason: '管理员调整'
})

const loadUsers = async () => {
  loading.value = true
  try {
    const res = await getUserList({
      current: currentPage.value,
      size: pageSize.value,
      keyword: keyword.value || undefined
    })
    users.value = res.data.records || []
    total.value = res.data.total || users.value.length
  } finally {
    loading.value = false
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadUsers()
}

const handleStatusChange = async (row) => {
  try {
    await updateUserStatus(row.id, row.status)
    ElMessage.success('用户状态已更新')
  } catch (error) {
    row.status = row.status === 1 ? 0 : 1
    ElMessage.error(error.message || '更新失败')
  }
}

const openCreditDialog = (row) => {
  creditForm.id = row.id
  creditForm.realName = row.realName
  creditForm.scoreChange = 0
  creditForm.changeReason = '管理员调整'
  creditDialogVisible.value = true
}

const handleAdjustCredit = async () => {
  await adjustCreditScore(creditForm.id, {
    scoreChange: creditForm.scoreChange,
    changeReason: creditForm.changeReason
  })
  ElMessage.success('信用分已调整')
  creditDialogVisible.value = false
  loadUsers()
}

onMounted(() => {
  loadUsers()
})
</script>

<style lang="scss" scoped>
.user-management {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
}
</style>
