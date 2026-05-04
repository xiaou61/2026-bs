<template>
  <DataPage title="账号权限" description="多角色账号权限维护与溯源监管闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSysUserPage, addSysUser, updateSysUser, deleteSysUser } from '../api'
const api = { page: getSysUserPage, add: addSysUser, update: updateSysUser, delete: deleteSysUser }
const columns = [{"prop": "username", "label": "账号"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色"}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "电话"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "username", "label": "账号"}, {"prop": "password", "label": "密码"}, {"prop": "nickname", "label": "姓名"}, {"prop": "role", "label": "角色", "type": "select", "options": [{"label": "ADMIN", "value": "ADMIN"}, {"label": "REGULATOR", "value": "REGULATOR"}, {"label": "FARMER", "value": "FARMER"}, {"label": "INSPECTOR", "value": "INSPECTOR"}]}, {"prop": "department", "label": "部门"}, {"prop": "phone", "label": "电话"}, {"prop": "email", "label": "邮箱"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "启用", "value": 1}, {"label": "停用", "value": 0}]}]
const rowActions = []
const defaults = {"role": "REGULATOR", "status": 1, "password": "123456"}
const handleAction = () => {}
</script>
