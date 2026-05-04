<template>
  <DataPage title="员工档案" description="演练对象员工信息、部门和风险等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEmployeeProfilePage, addEmployeeProfile, updateEmployeeProfile, deleteEmployeeProfile, activateEmployeeProfile, freezeEmployeeProfile } from '../api'
const api = { page: getEmployeeProfilePage, add: addEmployeeProfile, update: updateEmployeeProfile, delete: deleteEmployeeProfile }
const columns = [{"prop": "employeeName", "label": "员工姓名"}, {"prop": "employeeNo", "label": "员工编号"}, {"prop": "email", "label": "邮箱"}, {"prop": "departmentName", "label": "部门"}, {"prop": "positionName", "label": "岗位"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "employeeName", "label": "员工姓名"}, {"prop": "employeeNo", "label": "员工编号"}, {"prop": "email", "label": "邮箱"}, {"prop": "departmentName", "label": "部门"}, {"prop": "positionName", "label": "岗位"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "FROZEN", "value": "FROZEN"}, {"label": "LEFT", "value": "LEFT"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "primary"}, {"command": "freeze", "label": "冻结", "type": "primary"}]
const defaults = {"riskLevel": "MEDIUM", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEmployeeProfile(row.id)
  if (command === 'freeze') await freezeEmployeeProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
