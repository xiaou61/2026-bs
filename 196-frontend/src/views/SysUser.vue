<template><DataPage title="账号权限" description="账号、角色、部门、联系方式和状态维护" :api="api" :columns="columns" :form-fields="formFields" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSysUser, deleteSysUser, disableSysUser, enableSysUser, getSysUserPage, updateSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser, process: disableSysUser, finish: enableSysUser }
const columns = [{ prop: 'username', label: '账号' }, { prop: 'nickname', label: '昵称' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'username', label: '账号' }, { prop: 'password', label: '密码' }, { prop: 'nickname', label: '昵称' }, { prop: 'role', label: '角色' }, { prop: 'department', label: '部门' }, { prop: 'phone', label: '电话' }, { prop: 'email', label: '邮箱' }]
</script>
