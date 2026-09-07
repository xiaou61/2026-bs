<template>
  <DataPage title="风险预警" description="预警编号、风险对象、等级、原因、处理人和触发时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskWarningPage, addRiskWarning, updateRiskWarning, deleteRiskWarning, assignRiskWarning, closeRiskWarning } from '../api'
const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "riskObject", "label": "风险对象"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "warningReason", "label": "预警原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "triggerTime", "label": "触发时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "riskObject", "label": "风险对象"}, {"prop": "warningLevel", "label": "预警等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "warningReason", "label": "预警原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "triggerTime", "label": "触发时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignRiskWarning(row.id)
  if (command === 'close') await closeRiskWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
