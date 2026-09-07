<template>
  <DataPage title="账号权限" description="管理员、商户、收银员、财务四角色账号和启停状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser, enableSysUser, disableSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const columns = [{"prop": "username", "label": "用户名"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色"}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "手机"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "username", "label": "用户名"}, {"prop": "password", "label": "密码"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色", "type": "select", "options": [{"label": "ADMIN", "value": "ADMIN"}, {"label": "MERCHANT", "value": "MERCHANT"}, {"label": "CASHIER", "value": "CASHIER"}, {"label": "FINANCE", "value": "FINANCE"}]}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "手机"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "1", "value": 1}, {"label": "0", "value": 0}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"role": "MERCHANT", "status": 1}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSysUser(row.id)
  if (command === 'disable') await disableSysUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
