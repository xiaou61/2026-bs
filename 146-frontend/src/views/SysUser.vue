<template>
  <DataPage
    title="账号权限"
    description="维护系统管理员、抽检员、商户负责人和监管审核员账号权限"
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
import { addSysUser, deleteSysUser, disableSysUser, enableSysUser, getSysUserPage, updateSysUser } from '../api'

const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const roleOptions = [
  { label: '系统管理员', value: 'ADMIN' },
  { label: '抽检员', value: 'INSPECTOR' },
  { label: '商户负责人', value: 'MERCHANT' },
  { label: '监管审核员', value: 'REVIEWER' }
]
const columns = [
  { prop: 'username', label: '账号' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色' },
  { prop: 'department', label: '部门' },
  { prop: 'phone', label: '电话' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'username', label: '账号' },
  { prop: 'password', label: '密码' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色', type: 'select', options: roleOptions },
  { prop: 'department', label: '部门' },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态', type: 'number' }
]
const rowActions = [
  { command: 'enable', label: '启用', type: 'success' },
  { command: 'disable', label: '停用', type: 'warning' }
]
const defaults = { password: '123456', role: 'INSPECTOR', status: 1 }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSysUser(row.id)
  if (command === 'disable') await disableSysUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
