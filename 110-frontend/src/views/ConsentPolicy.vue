<template>
  <DataPage title="授权策略" description="授权目的、数据范围、默认权限和版本管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getConsentPolicyPage, addConsentPolicy, updateConsentPolicy, deleteConsentPolicy, submitConsentPolicy, approveConsentPolicy, archiveConsentPolicy } from '../api'
const api = { page: getConsentPolicyPage, add: addConsentPolicy, update: updateConsentPolicy, delete: deleteConsentPolicy }
const columns = [{"prop": "policyName", "label": "策略名称"}, {"prop": "policyCode", "label": "策略编码"}, {"prop": "purposeName", "label": "授权目的"}, {"prop": "dataScope", "label": "数据范围"}, {"prop": "policyVersion", "label": "版本"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "policyName", "label": "策略名称"}, {"prop": "policyCode", "label": "策略编码"}, {"prop": "purposeName", "label": "授权目的"}, {"prop": "dataScope", "label": "数据范围", "type": "textarea"}, {"prop": "policyVersion", "label": "版本"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitConsentPolicy(row.id)
  if (command === 'approve') await approveConsentPolicy(row.id)
  if (command === 'archive') await archiveConsentPolicy(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
