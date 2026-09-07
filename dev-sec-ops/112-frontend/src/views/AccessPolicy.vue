<template>
  <DataPage title="访问策略" description="应用资源访问策略、适用范围和启用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccessPolicyPage, addAccessPolicy, updateAccessPolicy, deleteAccessPolicy, enableAccessPolicy, disableAccessPolicy } from '../api'
const api = { page: getAccessPolicyPage, add: addAccessPolicy, update: updateAccessPolicy, delete: deleteAccessPolicy }
const columns = [{"prop": "policyName", "label": "策略名称"}, {"prop": "policyCode", "label": "策略编码"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "conditionText", "label": "访问条件"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "policyName", "label": "策略名称"}, {"prop": "policyCode", "label": "策略编码"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "conditionText", "label": "访问条件", "type": "textarea"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableAccessPolicy(row.id)
  if (command === 'disable') await disableAccessPolicy(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
