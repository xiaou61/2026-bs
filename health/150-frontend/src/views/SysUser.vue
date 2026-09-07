<template>
  <DataPage title="账号权限" description="账号、姓名、角色、科室、联系电话和启停状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser, enableSysUser, disableSysUser } from '../api'

const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const columns = [
  { prop: 'username', label: '账号' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色' },
  { prop: 'department', label: '科室/部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'username', label: '账号' },
  { prop: 'password', label: '密码' },
  { prop: 'nickname', label: '姓名' },
  {
    prop: 'role',
    label: '角色',
    type: 'select',
    options: [
      { label: '系统管理员', value: 'ADMIN' },
      { label: '门诊医生', value: 'DOCTOR' },
      { label: '检查技师', value: 'TECHNICIAN' },
      { label: '患者本人', value: 'PATIENT' }
    ]
  },
  { prop: 'department', label: '科室/部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'email', label: '邮箱' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '启用', value: 1 },
      { label: '停用', value: 0 }
    ]
  }
]
const rowActions = [
  { command: 'enable', label: '启用', type: 'success' },
  { command: 'disable', label: '停用', type: 'warning' }
]
const defaults = { role: 'PATIENT', status: 1 }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSysUser(row.id)
  if (command === 'disable') await disableSysUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
