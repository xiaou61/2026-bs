<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>用户管理</span><el-button type="primary" @click="handleAdd">新增用户</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="用户名"><el-input v-model="query.username" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="角色"><el-select v-model="query.role" placeholder="请选择" clearable><el-option label="管理员" value="admin" /><el-option label="HR" value="hr" /><el-option label="员工" value="employee" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="role" label="角色" width="100">
        <template #default="{ row }"><el-tag :type="{ admin: 'danger', hr: 'warning', employee: '' }[row.role]">{{ { admin: '管理员', hr: 'HR', employee: '员工' }[row.role] }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="warning" link @click="handleReset(row.id)">重置密码</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑用户' : '新增用户'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="用户名" prop="username"><el-input v-model="form.username" :disabled="!!form.id" /></el-form-item>
        <el-form-item label="密码" :prop="form.id ? '' : 'password'"><el-input v-model="form.password" type="password" :placeholder="form.id ? '不修改请留空' : '请输入密码'" /></el-form-item>
        <el-form-item label="姓名" prop="name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="角色" prop="role"><el-select v-model="form.role" style="width: 100%"><el-option label="管理员" value="admin" /><el-option label="HR" value="hr" /><el-option label="员工" value="employee" /></el-select></el-form-item>
        <el-form-item label="关联员工" v-if="form.role === 'employee'"><el-select v-model="form.employeeId" filterable clearable style="width: 100%"><el-option v-for="e in employees" :key="e.id" :label="e.name + ' (' + e.empNo + ')'" :value="e.id" /></el-select></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :label="1">启用</el-radio><el-radio :label="0">禁用</el-radio></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { userApi, employeeApi } from '../api'

const query = ref({ username: '', role: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const employees = ref([])
const rules = { username: [{ required: true, message: '请输入用户名', trigger: 'blur' }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }], name: [{ required: true, message: '请输入姓名', trigger: 'blur' }], role: [{ required: true, message: '请选择角色', trigger: 'change' }] }

const loadData = async () => { const res = await userApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadEmployees = async () => { employees.value = await employeeApi.getAll() }
const handleAdd = () => { form.value = { status: 1, role: 'employee' }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row, password: '' }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await userApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handleReset = async (id) => { await ElMessageBox.confirm('确定重置密码为123456?', '提示'); await userApi.resetPassword({ id, newPassword: '123456' }); ElMessage.success('重置成功') }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await userApi.update(form.value) } else { await userApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => { loadData(); loadEmployees() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
