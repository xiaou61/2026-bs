<template>
  <DataPage
    ref="pageRef"
    title="用户管理"
    description="维护管理员、HR、候选人和面试官账号。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ role: 'CANDIDATE', status: 1, password: '123456' }"
    
  />
</template>

<script setup>
import { ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addUser, deleteUser, getUserPage, updateUser } from '../api'

const pageRef = ref()

const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, HR: { label: 'HR', type: 'success' }, CANDIDATE: { label: '候选人', type: 'warning' }, INTERVIEWER: { label: '面试官', type: 'primary' } }
const statusMap = { 0: { label: '禁用', type: 'info' }, 1: { label: '启用', type: 'success' } }
const api = { page: getUserPage, add: addUser, update: updateUser, delete: deleteUser }
const roleOptions = [{ label: '管理员', value: 'ADMIN' }, { label: 'HR', value: 'HR' }, { label: '候选人', value: 'CANDIDATE' }, { label: '面试官', value: 'INTERVIEWER' }]
const statusOptions = [{ label: '启用', value: 1 }, { label: '禁用', value: 0 }]

const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'role', label: '角色', options: roleOptions }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'username', label: '用户名' }, { prop: 'nickname', label: '昵称' }, { prop: 'role', label: '角色', map: roleMap }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '手机' }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = [{ prop: 'username', label: '用户名', required: true }, { prop: 'password', label: '密码', password: true, required: true }, { prop: 'nickname', label: '昵称', required: true }, { prop: 'role', label: '角色', type: 'select', options: roleOptions, required: true }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '手机' }, { prop: 'email', label: '邮箱' }, { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }]
</script>
