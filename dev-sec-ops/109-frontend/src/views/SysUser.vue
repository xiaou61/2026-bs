<template>
  <DataPage title="账号权限" description="账号权限维护与数据脱敏审计闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const columns = [{"prop": "username", "label": "账号"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色"}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "电话"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "username", "label": "账号"}, {"prop": "password", "label": "密码"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色", "type": "select", "options": [{"label": "ADMIN", "value": "ADMIN"}, {"label": "SECURITY", "value": "SECURITY"}, {"label": "DATA_OWNER", "value": "DATA_OWNER"}, {"label": "AUDITOR", "value": "AUDITOR"}]}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "电话"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "启用", "value": 1}, {"label": "停用", "value": 0}]}]
const rowActions = []
const defaults = {"role": "SECURITY", "status": 1, "password": "123456"}
const handleAction = () => {}
</script>
