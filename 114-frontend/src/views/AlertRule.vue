<template>
  <DataPage title="告警规则" description="温度、湿度、离线、路线偏离等预警规则管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAlertRulePage, addAlertRule, updateAlertRule, deleteAlertRule, enableAlertRule, disableAlertRule } from '../api'
const api = { page: getAlertRulePage, add: addAlertRule, update: updateAlertRule, delete: deleteAlertRule }
const columns = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "ruleCode", "label": "规则编码"}, {"prop": "ruleType", "label": "规则类型"}, {"prop": "thresholdValue", "label": "阈值"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "ruleCode", "label": "规则编码"}, {"prop": "ruleType", "label": "规则类型", "type": "select", "options": [{"label": "TEMP", "value": "TEMP"}, {"label": "HUMIDITY", "value": "HUMIDITY"}, {"label": "OFFLINE", "value": "OFFLINE"}, {"label": "ROUTE", "value": "ROUTE"}]}, {"prop": "thresholdValue", "label": "阈值", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableAlertRule(row.id)
  if (command === 'disable') await disableAlertRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
