<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.username" placeholder="用户名" style="width: 180px" clearable />
      <el-select v-model="query.role" placeholder="角色" style="width: 130px" clearable>
        <el-option label="管理员" value="ADMIN" />
        <el-option label="教师" value="TEACHER" />
        <el-option label="学生" value="STUDENT" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="禁用" :value="0" />
      </el-select>
      <el-input v-model="query.college" placeholder="学院" style="width: 180px" clearable />
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增用户</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="username" label="用户名" width="140" />
      <el-table-column prop="nickname" label="昵称" width="120" />
      <el-table-column prop="role" label="角色" width="100" />
      <el-table-column prop="college" label="学院" min-width="130" />
      <el-table-column prop="major" label="专业" min-width="130" />
      <el-table-column prop="grade" label="年级" width="90" />
      <el-table-column prop="phone" label="电话" width="120" />
      <el-table-column prop="email" label="邮箱" min-width="180" show-overflow-tooltip />
      <el-table-column label="状态" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="240">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button v-if="row.status === 1" link type="warning" @click="changeStatus(row, 0)">禁用</el-button>
          <el-button v-else link type="success" @click="changeStatus(row, 1)">启用</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑用户' : '新增用户'" width="620px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="用户名" prop="username"><el-input v-model="form.username" maxlength="50" /></el-form-item>
      <el-form-item label="密码" :prop="form.id ? '' : 'password'"><el-input v-model="form.password" type="password" show-password placeholder="编辑时可留空" maxlength="100" /></el-form-item>
      <el-form-item label="昵称" prop="nickname"><el-input v-model="form.nickname" maxlength="50" /></el-form-item>
      <el-form-item label="角色" prop="role">
        <el-select v-model="form.role" style="width: 100%">
          <el-option label="管理员" value="ADMIN" />
          <el-option label="教师" value="TEACHER" />
          <el-option label="学生" value="STUDENT" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" style="width: 100%">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="学院"><el-input v-model="form.college" maxlength="100" /></el-form-item>
      <el-form-item label="专业"><el-input v-model="form.major" maxlength="100" /></el-form-item>
      <el-form-item label="年级"><el-input v-model="form.grade" maxlength="20" /></el-form-item>
      <el-form-item label="电话"><el-input v-model="form.phone" maxlength="20" /></el-form-item>
      <el-form-item label="邮箱"><el-input v-model="form.email" maxlength="100" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addUser, deleteUser, getUserPage, updateUser, updateUserStatus } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, username: '', role: '', status: null, college: '' })
const form = reactive({})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getUserPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, username: '', password: '123456', nickname: '', role: 'STUDENT', status: 1, college: '', major: '', grade: '', phone: '', email: '' })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, { ...row, password: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.id) {
    await formRef.value.validateField(['username', 'nickname', 'role'])
    await updateUser(form)
  } else {
    await formRef.value.validate()
    await addUser(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const changeStatus = async (row, status) => {
  await updateUserStatus({ id: row.id, status })
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
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
