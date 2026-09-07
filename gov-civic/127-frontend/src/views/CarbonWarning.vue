<template>
  <DataPage title="碳排预警" description="碳排预警、企业、预警等级、触发原因和处理人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCarbonWarningPage, addCarbonWarning, updateCarbonWarning, deleteCarbonWarning, processCarbonWarning, finishCarbonWarning } from '../api'
const api = { page: getCarbonWarningPage, add: addCarbonWarning, update: updateCarbonWarning, delete: deleteCarbonWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processCarbonWarning(row.id)
  if (command === 'finish') await finishCarbonWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
