<template>
  <div class="user-manage-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">用户管理</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-form :inline="true" :model="queryForm">
        <el-form-item label="角色">
          <el-select v-model="queryForm.role" placeholder="全部" clearable style="width: 150px">
            <el-option label="学生" value="STUDENT" />
            <el-option label="快递员" value="COURIER" />
            <el-option label="代收点管理员" value="STATION_ADMIN" />
            <el-option label="系统管理员" value="ADMIN" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="用户名/姓名/手机号" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" style="margin-top: 20px">
        <el-table-column prop="studentId" label="学号/工号" width="120" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="realName" label="真实姓名" width="120" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column label="角色" width="150">
          <template #default="{ row }">
            <el-tag :type="getRoleType(row.role)">{{ getRoleName(row.role) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEditRole(row)">修改角色</el-button>
            <el-button
              :type="row.status === 1 ? 'warning' : 'success'"
              size="small"
              @click="handleToggleStatus(row)"
            >
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>

    <el-dialog v-model="showRoleDialog" title="修改角色" width="400px">
      <el-form label-width="80px">
        <el-form-item label="用户">
          {{ selectedUser?.realName }} ({{ selectedUser?.username }})
        </el-form-item>
        <el-form-item label="当前角色">
          <el-tag :type="getRoleType(selectedUser?.role)">
            {{ getRoleName(selectedUser?.role) }}
          </el-tag>
        </el-form-item>
        <el-form-item label="新角色">
          <el-select v-model="newRole" style="width: 100%">
            <el-option label="学生" value="STUDENT" />
            <el-option label="快递员" value="COURIER" />
            <el-option label="代收点管理员" value="STATION_ADMIN" />
            <el-option label="系统管理员" value="ADMIN" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRoleDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdateRole" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, updateUserRole, updateUserStatus } from '@/api/user'

const loading = ref(false)
const submitLoading = ref(false)
const showRoleDialog = ref(false)
const tableData = ref([])
const selectedUser = ref(null)
const newRole = ref('')

const queryForm = reactive({
  role: '',
  keyword: ''
})

const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

const getRoleName = (role) => {
  const map = {
    'ADMIN': '系统管理员',
    'STATION_ADMIN': '代收点管理员',
    'COURIER': '快递员',
    'STUDENT': '学生'
  }
  return map[role] || role
}

const getRoleType = (role) => {
  const map = {
    'ADMIN': 'danger',
    'STATION_ADMIN': 'warning',
    'COURIER': 'success',
    'STUDENT': 'info'
  }
  return map[role] || 'info'
}

const handleReset = () => {
  queryForm.role = ''
  queryForm.keyword = ''
  pagination.page = 1
  loadData()
}

const handleEditRole = (row) => {
  selectedUser.value = row
  newRole.value = row.role
  showRoleDialog.value = true
}

const handleUpdateRole = async () => {
  if (newRole.value === selectedUser.value.role) {
    ElMessage.warning('角色未改变')
    return
  }
  
  submitLoading.value = true
  try {
    await updateUserRole(selectedUser.value.id, newRole.value)
    ElMessage.success('修改成功')
    showRoleDialog.value = false
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    submitLoading.value = false
  }
}

const handleToggleStatus = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  const action = newStatus === 1 ? '启用' : '禁用'
  
  try {
    await ElMessageBox.confirm(`确定要${action}该用户吗？`, '提示', { type: 'warning' })
    await updateUserStatus(row.id, newStatus)
    ElMessage.success(`${action}成功`)
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      size: pagination.size,
      ...queryForm
    }
    const res = await getUserList(params)
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.user-manage-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}
</style>

