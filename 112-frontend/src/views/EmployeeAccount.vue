<template>
  <DataPage title="员工账号" description="员工身份、账号状态和多因素认证状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEmployeeAccountPage, addEmployeeAccount, updateEmployeeAccount, deleteEmployeeAccount, enableEmployeeAccount, disableEmployeeAccount } from '../api'
const api = { page: getEmployeeAccountPage, add: addEmployeeAccount, update: updateEmployeeAccount, delete: deleteEmployeeAccount }
const columns = [{"prop": "employeeName", "label": "员工姓名"}, {"prop": "employeeNo", "label": "员工编号"}, {"prop": "accountName", "label": "账号名"}, {"prop": "departmentName", "label": "部门"}, {"prop": "mfaStatus", "label": "MFA状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "employeeName", "label": "员工姓名"}, {"prop": "employeeNo", "label": "员工编号"}, {"prop": "accountName", "label": "账号名"}, {"prop": "departmentName", "label": "部门"}, {"prop": "mfaStatus", "label": "MFA状态", "type": "select", "options": [{"label": "ENABLED", "value": "ENABLED"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "EXPIRED", "value": "EXPIRED"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "LEFT", "value": "LEFT"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"mfaStatus": "ENABLED", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableEmployeeAccount(row.id)
  if (command === 'disable') await disableEmployeeAccount(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
