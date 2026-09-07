<template><DataPage title="账号权限" description="入住管理员、护理主管、护理人员、家属用户、院长账号与状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN']" :delete-roles="['ADMIN']" :status-options="statusOptions" :default-status="1" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSysUser, deleteSysUser, disableSysUser, enableSysUser, getSysUserPage, updateSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser, process: disableSysUser, finish: enableSysUser }
const statusOptions = [{ label: '启用', value: 1 }, { label: '停用', value: 0 }]
const columns = [{ prop: 'username', label: '账号' }, { prop: 'nickname', label: '姓名' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'username', label: '账号' }, { prop: 'password', label: '密码' }, { prop: 'nickname', label: '姓名' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'email', label: '邮箱' }]
</script>
