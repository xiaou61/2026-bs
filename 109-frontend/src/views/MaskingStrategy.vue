<template>
  <DataPage title="脱敏策略" description="敏感类型、脱敏方法和审批角色策略管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaskingStrategyPage, addMaskingStrategy, updateMaskingStrategy, deleteMaskingStrategy, enableMaskingStrategy, disableMaskingStrategy } from '../api'
const api = { page: getMaskingStrategyPage, add: addMaskingStrategy, update: updateMaskingStrategy, delete: deleteMaskingStrategy }
const columns = [{"prop": "strategyName", "label": "策略名称"}, {"prop": "sensitiveType", "label": "敏感类型"}, {"prop": "maskingMethod", "label": "脱敏方法"}, {"prop": "maskingExpression", "label": "脱敏表达式"}, {"prop": "reviewRole", "label": "审批角色"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "strategyName", "label": "策略名称"}, {"prop": "sensitiveType", "label": "敏感类型", "type": "select", "options": [{"label": "ID_CARD", "value": "ID_CARD"}, {"label": "PHONE", "value": "PHONE"}, {"label": "EMAIL", "value": "EMAIL"}, {"label": "BANK_CARD", "value": "BANK_CARD"}, {"label": "ADDRESS", "value": "ADDRESS"}]}, {"prop": "maskingMethod", "label": "脱敏方法", "type": "select", "options": [{"label": "MASK", "value": "MASK"}, {"label": "HASH", "value": "HASH"}, {"label": "TOKEN", "value": "TOKEN"}, {"label": "REMOVE", "value": "REMOVE"}]}, {"prop": "maskingExpression", "label": "脱敏表达式"}, {"prop": "reviewRole", "label": "审批角色", "type": "select", "options": [{"label": "DATA_OWNER", "value": "DATA_OWNER"}, {"label": "AUDITOR", "value": "AUDITOR"}, {"label": "SECURITY", "value": "SECURITY"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"maskingMethod": "MASK", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableMaskingStrategy(row.id)
  if (command === 'disable') await disableMaskingStrategy(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
