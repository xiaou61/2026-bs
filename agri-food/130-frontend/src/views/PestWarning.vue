<template>
  <DataPage title="虫害预警" description="虫害预警、温室、虫害类型、等级和处理人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPestWarningPage, addPestWarning, updatePestWarning, deletePestWarning, processPestWarning, finishPestWarning } from '../api'
const api = { page: getPestWarningPage, add: addPestWarning, update: updatePestWarning, delete: deletePestWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "pestType", "label": "虫害类型"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "pestType", "label": "虫害类型"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPestWarning(row.id)
  if (command === 'finish') await finishPestWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
