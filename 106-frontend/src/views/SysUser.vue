<template>
  <DataPage title="账号权限" description="账号权限维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ role: 'OPS', status: 1, password: '123456' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const columns = [{ prop: 'username', label: '账号' }, { prop: 'nickname', label: '姓名' }, { prop: 'role', label: '角色' }, { prop: 'teamName', label: '团队' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'username', label: '账号' }, { prop: 'password', label: '密码' }, { prop: 'nickname', label: '姓名' }, { prop: 'role', label: '角色', type: 'select', options: [{ label: 'ADMIN', value: 'ADMIN' }, { label: 'RELEASE_MANAGER', value: 'RELEASE_MANAGER' }, { label: 'OPS', value: 'OPS' }, { label: 'AUDITOR', value: 'AUDITOR' }] }, { prop: 'teamName', label: '团队' }, { prop: 'phone', label: '电话' }, { prop: 'email', label: '邮箱' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 1 }, { label: '停用', value: 0 }] }]
const rowActions = []
const handleAction = () => {}
</script>
