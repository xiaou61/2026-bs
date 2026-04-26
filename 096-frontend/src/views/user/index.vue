<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.role" placeholder="角色" clearable style="width: 140px">
          <el-option label="管理员" value="ADMIN" />
          <el-option label="患者" value="PATIENT" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-input v-model="query.keyword" placeholder="用户名/昵称/手机号" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增用户</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="role" label="角色">
          <template #default="{ row }">{{ roleMap[row.role] || row.role }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-switch :model-value="row.status === 1" @change="toggleStatus(row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除该用户吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 16px"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑用户' : '新增用户'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="用户名" prop="username"><el-input v-model="form.username" :disabled="!!form.id" /></el-form-item>
        <el-form-item label="密码" :prop="form.id ? '' : 'password'"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-form-item label="昵称" prop="nickname"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="form.role">
            <el-radio label="ADMIN">管理员</el-radio>
            <el-radio label="PATIENT">患者</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { addUser, deleteUser, getUserPage, updateUser, updateUserStatus } from '../../api'

const roleMap = { ADMIN: '管理员', PATIENT: '患者', DOCTOR: '医生' }
const loading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, role: '', status: undefined, keyword: '' })
const form = reactive({ id: null, username: '', password: '', nickname: '', role: 'PATIENT', phone: '', email: '', status: 1 })

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }]
}

const resetForm = () => {
  Object.assign(form, { id: null, username: '', password: '', nickname: '', role: 'PATIENT', phone: '', email: '', status: 1 })
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getUserPage(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row, password: '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateUser(form)
  } else {
    await addUser(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const toggleStatus = async (row) => {
  await updateUserStatus({ id: row.id, status: row.status === 1 ? 0 : 1 })
  ElMessage.success('状态已更新')
  loadData()
}

const handleDelete = async (id) => {
  await deleteUser(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
