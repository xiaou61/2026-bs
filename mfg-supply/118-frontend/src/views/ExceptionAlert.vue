<template>
  <DataPage title="异常告警" description="任务、车辆、库位、告警等级、告警内容、处理人和告警状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExceptionAlertPage, addExceptionAlert, updateExceptionAlert, deleteExceptionAlert, handleExceptionAlert, closeExceptionAlert } from '../api'
const api = { page: getExceptionAlertPage, add: addExceptionAlert, update: updateExceptionAlert, delete: deleteExceptionAlert }
const columns = [{"prop": "alertNo", "label": "告警编号"}, {"prop": "agvNo", "label": "AGV编号"}, {"prop": "locationNo", "label": "库位编码"}, {"prop": "alertLevel", "label": "告警等级"}, {"prop": "alertContent", "label": "告警内容"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "alertNo", "label": "告警编号"}, {"prop": "agvNo", "label": "AGV编号"}, {"prop": "locationNo", "label": "库位编码"}, {"prop": "alertLevel", "label": "告警等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "alertContent", "label": "告警内容", "type": "textarea"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "HANDLING", "value": "HANDLING"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "handle", "label": "处理", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'handle') await handleExceptionAlert(row.id)
  if (command === 'close') await closeExceptionAlert(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
