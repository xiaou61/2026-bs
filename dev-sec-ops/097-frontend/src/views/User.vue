<template>
  <DataPage
    title="用户管理"
    description="维护管理员、提示词工程师和评审员账号。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ role: 'ENGINEER', status: 1 }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addUser, deleteUser, getUserPage, updateUser } from '../api'

const roleOptions = [
  { label: '管理员', value: 'ADMIN' },
  { label: '工程师', value: 'ENGINEER' },
  { label: '评审员', value: 'REVIEWER' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '禁用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '禁用', type: 'danger' }
}
const roleMap = {
  ADMIN: { label: '管理员', type: 'danger' },
  ENGINEER: { label: '工程师', type: 'success' },
  REVIEWER: { label: '评审员', type: 'warning' }
}
const api = { page: getUserPage, add: addUser, update: updateUser, delete: deleteUser }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '用户名或昵称' },
  { prop: 'role', label: '角色', type: 'select', options: roleOptions },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'username', label: '用户名' },
  { prop: 'nickname', label: '昵称' },
  { prop: 'role', label: '角色', map: roleMap },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱', minWidth: 170 },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'username', label: '用户名', required: true },
  { prop: 'password', label: '密码', password: true, placeholder: '新增默认 123456' },
  { prop: 'nickname', label: '昵称', required: true },
  { prop: 'role', label: '角色', type: 'select', required: true, options: roleOptions },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
