<template>
  <DataPage title="预警规则" description="预警规则、指标名称、阈值、等级和启用状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAlertRulePage, addAlertRule, updateAlertRule, deleteAlertRule, activateAlertRule, finishAlertRule } from '../api'
const api = { page: getAlertRulePage, add: addAlertRule, update: updateAlertRule, delete: deleteAlertRule }
const columns = [{"prop": "ruleNo", "label": "规则编号"}, {"prop": "ruleName", "label": "规则名称"}, {"prop": "metricName", "label": "指标名称"}, {"prop": "thresholdValue", "label": "阈值"}, {"prop": "alertLevel", "label": "预警等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleNo", "label": "规则编号"}, {"prop": "ruleName", "label": "规则名称"}, {"prop": "metricName", "label": "指标名称"}, {"prop": "thresholdValue", "label": "阈值", "type": "number"}, {"prop": "alertLevel", "label": "预警等级"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateAlertRule(row.id)
  if (command === 'finish') await finishAlertRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
