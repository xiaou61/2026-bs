<template>
  <DataPage title="二维码追踪" description="追踪编号、二维码、器械、当前位置和流转状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getQrTracePage, addQrTrace, updateQrTrace, deleteQrTrace, activateQrTrace, finishQrTrace } from '../api'
const api = { page: getQrTracePage, add: addQrTrace, update: updateQrTrace, delete: deleteQrTrace }
const columns = [{"prop": "traceNo", "label": "追踪编号"}, {"prop": "qrCode", "label": "二维码"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "currentLocation", "label": "当前位置"}, {"prop": "flowStatus", "label": "流转状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "traceNo", "label": "追踪编号"}, {"prop": "qrCode", "label": "二维码"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "currentLocation", "label": "当前位置"}, {"prop": "flowStatus", "label": "流转状态"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateQrTrace(row.id)
  if (command === 'finish') await finishQrTrace(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
