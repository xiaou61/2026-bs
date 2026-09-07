<template>
  <DataPage title="准入申请" description="设备准入申请、申请原因和审批状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccessApplicationPage, addAccessApplication, updateAccessApplication, deleteAccessApplication, submitAccessApplication, approveAccessApplication, rejectAccessApplication } from '../api'
const api = { page: getAccessApplicationPage, add: addAccessApplication, update: updateAccessApplication, delete: deleteAccessApplication }
const columns = [{"prop": "applicationNo", "label": "申请编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "resourceName", "label": "资源"}, {"prop": "reasonText", "label": "原因"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "applicationNo", "label": "申请编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "resourceName", "label": "资源"}, {"prop": "reasonText", "label": "原因", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "primary"}, {"command": "reject", "label": "驳回", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAccessApplication(row.id)
  if (command === 'approve') await approveAccessApplication(row.id)
  if (command === 'reject') await rejectAccessApplication(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
