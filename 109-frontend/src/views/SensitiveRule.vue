<template>
  <DataPage title="敏感规则" description="敏感类型、匹配模式和风险等级规则管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSensitiveRulePage, addSensitiveRule, updateSensitiveRule, deleteSensitiveRule, enableSensitiveRule, disableSensitiveRule } from '../api'
const api = { page: getSensitiveRulePage, add: addSensitiveRule, update: updateSensitiveRule, delete: deleteSensitiveRule }
const columns = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "ruleType", "label": "规则类型"}, {"prop": "matchPattern", "label": "匹配模式"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "maskingType", "label": "脱敏类型"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "ruleType", "label": "规则类型", "type": "select", "options": [{"label": "ID_CARD", "value": "ID_CARD"}, {"label": "PHONE", "value": "PHONE"}, {"label": "EMAIL", "value": "EMAIL"}, {"label": "BANK_CARD", "value": "BANK_CARD"}, {"label": "ADDRESS", "value": "ADDRESS"}]}, {"prop": "matchPattern", "label": "匹配模式"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "maskingType", "label": "脱敏类型", "type": "select", "options": [{"label": "MASK", "value": "MASK"}, {"label": "HASH", "value": "HASH"}, {"label": "TOKEN", "value": "TOKEN"}, {"label": "REMOVE", "value": "REMOVE"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"riskLevel": "HIGH", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSensitiveRule(row.id)
  if (command === 'disable') await disableSensitiveRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
