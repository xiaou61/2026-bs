<template>
  <div class="user-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </div>
      <el-button
        type="primary"
        @click="showAddDialog = true"
        class="add-btn"
      >
        <el-icon><Plus /></el-icon>
        添加用户
      </el-button>
    </div>

    <el-card class="main-card">
      <!-- 搜索栏 -->
      <div class="search-section">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="角色">
            <el-select v-model="searchForm.role" placeholder="请选择角色" clearable class="role-select">
              <el-option label="管理员" value="admin" />
              <el-option label="教师" value="teacher" />
              <el-option label="学生" value="student" />
            </el-select>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="searchForm.username" placeholder="请输入用户名" clearable class="username-input" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="fetchUserList" class="search-btn">
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
          <el-table-column prop="username" label="用户名" width="150" align="center">
            <template #default="{ row }">
              <div class="username-info">
                <el-avatar :size="28" class="user-avatar">{{ row.username?.charAt(0) }}</el-avatar>
                <span>{{ row.username }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="realName" label="真实姓名" width="120" align="center" />
          <el-table-column prop="role" label="角色" width="120" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.role === 'admin'" type="danger" class="role-tag">管理员</el-tag>
              <el-tag v-else-if="row.role === 'teacher'" type="warning" class="role-tag">教师</el-tag>
              <el-tag v-else type="primary" class="role-tag">学生</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="手机号" width="130" align="center" />
          <el-table-column prop="email" label="邮箱" min-width="200" align="center" />
          <el-table-column prop="createTime" label="创建时间" width="180" align="center" />
          <el-table-column label="操作" width="200" fixed="right" align="center">
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
                :disabled="row.id === userInfo?.id"
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
          @size-change="fetchUserList"
          @current-change="fetchUserList"
          class="custom-pagination"
        />
      </div>
    </el-card>

    <!-- 添加/编辑用户对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="editingUser ? '编辑用户' : '添加用户'"
      width="500px"
      @close="resetForm"
      class="custom-dialog"
    >
      <el-form
        ref="formRef"
        :model="userForm"
        :rules="userRules"
        label-width="100px"
        class="user-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="userForm.username"
            placeholder="请输入用户名"
            :disabled="!!editingUser"
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!editingUser">
          <el-input
            v-model="userForm.password"
            type="password"
            placeholder="请输入密码"
            show-password
            class="custom-input"
          />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="userForm.realName" placeholder="请输入真实姓名" class="custom-input" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" placeholder="请选择角色" style="width: 100%" class="custom-select">
            <el-option label="管理员" value="admin" />
            <el-option label="教师" value="teacher" />
            <el-option label="学生" value="student" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userForm.phone" placeholder="请输入手机号" class="custom-input" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱" class="custom-input" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="showAddDialog = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitting" class="submit-btn">
            <el-icon><Check /></el-icon>
            {{ editingUser ? '更新用户' : '添加用户' }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { getUserList, addUser, updateUser, deleteUser } from '@/api/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const showAddDialog = ref(false)
const formRef = ref(null)
const editingUser = ref(null)

const searchForm = reactive({
  role: '',
  username: ''
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const tableData = ref([])

const userForm = reactive({
  username: '',
  password: '',
  realName: '',
  role: 'student',
  phone: '',
  email: ''
})

const userRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3到20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
}

// 获取用户列表
const fetchUserList = async () => {
  loading.value = true
  try {
    const params = {
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    }
    const res = await getUserList(params)
    if (res.code === 200) {
      tableData.value = res.data.records || []
      pagination.total = res.data.total || 0
    }
  } catch (error) {
    console.error('获取用户列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.role = ''
  searchForm.username = ''
  pagination.current = 1
  fetchUserList()
}

// 提交用户
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        let res
        if (editingUser.value) {
          res = await updateUser({
            id: editingUser.value.id,
            realName: userForm.realName,
            role: userForm.role,
            phone: userForm.phone,
            email: userForm.email
          })
        } else {
          res = await addUser(userForm)
        }
        
        if (res.code === 200) {
          ElMessage.success(editingUser.value ? '更新成功' : '添加成功')
          showAddDialog.value = false
          fetchUserList()
        }
      } catch (error) {
        console.error('操作失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 编辑用户
const handleEdit = (row) => {
  editingUser.value = row
  Object.assign(userForm, {
    username: row.username,
    realName: row.realName,
    role: row.role,
    phone: row.phone,
    email: row.email
  })
  showAddDialog.value = true
}

// 删除用户
const handleDelete = async (id) => {
  ElMessageBox.confirm('确定删除该用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const res = await deleteUser(id)
      if (res.code === 200) {
        ElMessage.success('删除成功')
        fetchUserList()
      }
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

// 重置表单
const resetForm = () => {
  editingUser.value = null
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

onMounted(() => {
  fetchUserList()
})
</script>

<style scoped>
.user-container {
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
  color: #409EFF;
  font-size: 28px;
}

.add-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  transition: all 0.3s ease;
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(64, 158, 255, 0.4);
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

.role-select {
  width: 160px;
}

.role-select :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.username-input {
  width: 200px;
}

.username-input :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.search-btn {
  height: 40px;
  padding: 0 20px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%) !important;
  border: none !important;
}

.search-btn:hover {
  background: linear-gradient(135deg, #337ecc 0%, #409EFF 100%) !important;
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

.username-info {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.user-avatar {
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%);
  color: white;
  font-weight: 600;
  font-size: 12px;
}

.role-tag {
  border-radius: 20px !important;
  padding: 0 12px !important;
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

.delete-btn:disabled {
  background: #C0C4CC !important;
  box-shadow: none;
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
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%);
  color: white;
}

/* 自定义对话框 */
.custom-dialog :deep(.el-dialog) {
  border-radius: 20px !important;
  overflow: hidden;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%);
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

/* 用户表单 */
.user-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #2C3E50;
}

.custom-input :deep(.el-input__wrapper) {
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
  background: linear-gradient(135deg, #409EFF 0%, #66B1FF 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.4);
}

/* 响应式 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
  
  .add-btn {
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

