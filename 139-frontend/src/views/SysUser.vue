<template>
  <DataPage title="账号权限" description="用户账号、角色、部门、联系方式和启停状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser, enableSysUser, disableSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const columns = [{"prop": "username", "label": "账号"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色"}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "username", "label": "账号"}, {"prop": "password", "label": "密码"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色"}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "电话"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态", "type": "number"}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "warning"}]
const defaults = {"status": 1}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSysUser(row.id)
  if (command === 'disable') await disableSysUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>



