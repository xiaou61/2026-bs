<template>
  <DataPage title="预警规则" description="规则编号、规则名称、指标类型、阈值、预警等级和启用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWarningRulePage, addWarningRule, updateWarningRule, deleteWarningRule, enableWarningRule, disableWarningRule } from '../api'
const api = { page: getWarningRulePage, add: addWarningRule, update: updateWarningRule, delete: deleteWarningRule }
const columns = [{"prop": "ruleNo", "label": "规则编号"}, {"prop": "ruleName", "label": "规则名称"}, {"prop": "metricType", "label": "指标类型"}, {"prop": "thresholdValue", "label": "阈值"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleNo", "label": "规则编号"}, {"prop": "ruleName", "label": "规则名称"}, {"prop": "metricType", "label": "指标类型"}, {"prop": "thresholdValue", "label": "阈值", "type": "number"}, {"prop": "warningLevel", "label": "预警等级", "type": "select", "options": [{"label": "蓝色", "value": "蓝色"}, {"label": "黄色", "value": "黄色"}, {"label": "橙色", "value": "橙色"}, {"label": "红色", "value": "红色"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableWarningRule(row.id)
  if (command === 'disable') await disableWarningRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
