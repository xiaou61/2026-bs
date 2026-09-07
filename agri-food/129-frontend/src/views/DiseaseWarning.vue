<template>
  <DataPage title="病害预警" description="预警编号、池塘、预警等级、症状和处理人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDiseaseWarningPage, addDiseaseWarning, updateDiseaseWarning, deleteDiseaseWarning, processDiseaseWarning, finishDiseaseWarning } from '../api'
const api = { page: getDiseaseWarningPage, add: addDiseaseWarning, update: updateDiseaseWarning, delete: deleteDiseaseWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "symptomText", "label": "症状描述"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "symptomText", "label": "症状描述"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processDiseaseWarning(row.id)
  if (command === 'finish') await finishDiseaseWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
