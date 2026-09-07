<template>
  <DataPage title="节能建议" description="建议编号、家庭、设备、建议类型、预计节省和发布状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSavingSuggestionPage, addSavingSuggestion, updateSavingSuggestion, deleteSavingSuggestion, publishSavingSuggestion, closeSavingSuggestion } from '../api'
const api = { page: getSavingSuggestionPage, add: addSavingSuggestion, update: updateSavingSuggestion, delete: deleteSavingSuggestion }
const columns = [{"prop": "suggestionNo", "label": "建议编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "suggestionType", "label": "建议类型"}, {"prop": "savingKwh", "label": "预计节省"}, {"prop": "analystName", "label": "分析师"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "suggestionNo", "label": "建议编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "suggestionType", "label": "建议类型"}, {"prop": "savingKwh", "label": "预计节省", "type": "number"}, {"prop": "analystName", "label": "分析师"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishSavingSuggestion(row.id)
  if (command === 'close') await closeSavingSuggestion(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
