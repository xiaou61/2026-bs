<template>
  <DataPage title="账号权限" description="维护系统管理员、社区居民、志愿者和站点管理员账号，以及部门、联系方式和启停状态" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser, enableSysUser, disableSysUser } from '../api'

const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'username', label: '账号' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色' },
  { prop: 'department', label: '所属部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'username', label: '账号' },
  { prop: 'password', label: '密码' },
  { prop: 'nickname', label: '姓名' },
  { prop: 'role', label: '角色', type: 'select', options: [{ label: '系统管理员', value: 'ADMIN' }, { label: '社区居民', value: 'RESIDENT' }, { label: '志愿者', value: 'VOLUNTEER' }, { label: '站点管理员', value: 'MANAGER' }] },
  { prop: 'department', label: '所属部门' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 1 }, { label: '停用', value: 0 }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'enable', label: '启用', type: 'success' }, { command: 'disable', label: '停用', type: 'warning' }] : [])
const defaults = { status: 1, role: 'RESIDENT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSysUser(row.id)
  if (command === 'disable') await disableSysUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
