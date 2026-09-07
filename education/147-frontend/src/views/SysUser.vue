<template>
  <DataPage
    title="账号权限"
    description="维护平台账号、角色、归属学院或中心和启停状态，支撑心理服务多角色协同管理"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="true"
    :can-edit="true"
    :can-delete="true"
    @row-action="handleAction"
  />
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
  { prop: 'department', label: '归属部门', width: 160 },
  { prop: 'phone', label: '联系电话', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'username', label: '账号' },
  { prop: 'password', label: '密码' },
  {
    prop: 'role',
    label: '角色',
    type: 'select',
    options: [
      { label: '系统管理员', value: 'ADMIN' },
      { label: '心理老师', value: 'TEACHER' },
      { label: '学生来访者', value: 'STUDENT' },
      { label: '学院辅导员', value: 'COUNSELOR' }
    ]
  },
  { prop: 'nickname', label: '姓名' },
  { prop: 'department', label: '归属部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 1 }, { label: '停用', value: 0 }] }
]
const rowActions = [
  { command: 'enable', label: '启用', type: 'success' },
  { command: 'disable', label: '停用', type: 'warning' }
]
const defaults = { role: 'STUDENT', status: 1 }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSysUser(row.id)
  if (command === 'disable') await disableSysUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




