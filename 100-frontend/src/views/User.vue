<template>
  <DataPage
    title="用户管理"
    description="管理管理员、教师、学生和复核员账号。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ role: 'STUDENT', status: 1, password: '123456' }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addUser, deleteUser, getUserPage, updateUser } from '../api'

const roleMap = {
  ADMIN: { label: '管理员', type: 'danger' },
  TEACHER: { label: '教师', type: 'success' },
  STUDENT: { label: '学生', type: 'warning' },
  REVIEWER: { label: '复核员', type: 'primary' }
}
const statusMap = {
  0: { label: '禁用', type: 'info' },
  1: { label: '启用', type: 'success' }
}
const api = { page: getUserPage, add: addUser, update: updateUser, delete: deleteUser }
const roleOptions = [
  { label: '管理员', value: 'ADMIN' },
  { label: '教师', value: 'TEACHER' },
  { label: '学生', value: 'STUDENT' },
  { label: '复核员', value: 'REVIEWER' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '禁用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '用户名/昵称/部门' },
  { type: 'select', prop: 'role', label: '角色', options: roleOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'username', label: '用户名' },
  { prop: 'nickname', label: '昵称' },
  { prop: 'role', label: '角色', map: roleMap },
  { prop: 'department', label: '部门', minWidth: 150 },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱', minWidth: 180 },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'username', label: '用户名', required: true },
  { prop: 'password', label: '密码', password: true, required: true },
  { prop: 'nickname', label: '昵称', required: true },
  { prop: 'role', label: '角色', type: 'select', options: roleOptions, required: true },
  { prop: 'department', label: '部门' },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
