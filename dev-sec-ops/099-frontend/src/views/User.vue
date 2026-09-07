<template>
  <DataPage
    title="用户管理"
    description="管理平台管理员、审核员和创作者账号。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ role: 'CREATOR', status: 1, password: '123456' }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addUser, deleteUser, getUserPage, updateUser } from '../api'

const roleMap = {
  ADMIN: { label: '管理员', type: 'danger' },
  AUDITOR: { label: '审核员', type: 'success' },
  CREATOR: { label: '创作者', type: 'warning' }
}

const statusMap = {
  0: { label: '禁用', type: 'info' },
  1: { label: '启用', type: 'success' }
}

const api = { page: getUserPage, add: addUser, update: updateUser, delete: deleteUser }
const roleOptions = [
  { label: '管理员', value: 'ADMIN' },
  { label: '审核员', value: 'AUDITOR' },
  { label: '创作者', value: 'CREATOR' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '禁用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '用户名/昵称/手机' },
  { type: 'select', prop: 'role', label: '角色', options: roleOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'username', label: '用户名' },
  { prop: 'nickname', label: '昵称' },
  { prop: 'role', label: '角色', map: roleMap },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱', minWidth: 180 },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'createTime', label: '创建时间', minWidth: 170 }
]
const formFields = [
  { prop: 'username', label: '用户名', required: true },
  { prop: 'password', label: '密码', password: true, required: true },
  { prop: 'nickname', label: '昵称', required: true },
  { prop: 'role', label: '角色', type: 'select', options: roleOptions, required: true },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
