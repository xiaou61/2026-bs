<template>
  <DataPage title="账号权限" description="维护管理员、法务、申请人和审批人的账号信息、角色归属与启停状态" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser, enableSysUser, disableSysUser } from '../api'

const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const formatStatus = (_, __, value) => Number(value) === 1 ? '启用' : '停用'
const columns = [
  { prop: 'username', label: '登录账号' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色' },
  { prop: 'department', label: '所属部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'status', label: '状态', formatter: formatStatus }
]
const formFields = [
  { prop: 'username', label: '登录账号' },
  { prop: 'password', label: '登录密码（留空不改）' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色', type: 'select', options: [{ label: '管理员', value: 'ADMIN' }, { label: '法务专员', value: 'LEGAL' }, { label: '业务申请人', value: 'APPLICANT' }, { label: '审批负责人', value: 'APPROVER' }] },
  { prop: 'department', label: '所属部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'email', label: '邮箱地址' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 1 }, { label: '停用', value: 0 }] }
]
const rowActions = [
  { command: 'enable', label: '启用', type: 'success' },
  { command: 'disable', label: '停用', type: 'warning' }
]
const defaults = { role: 'APPLICANT', status: 1 }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') {
    await enableSysUser(row.id)
  }
  if (command === 'disable') {
    await disableSysUser(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
