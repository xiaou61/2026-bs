<template>
  <DataPage title="节点通知" description="通知编号、课题编号、节点类型、提醒内容、接收人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAlertNotificationPage, addAlertNotification, updateAlertNotification, deleteAlertNotification, processAlertNotification, finishAlertNotification } from '../api'
const api = { page: getAlertNotificationPage, add: addAlertNotification, update: updateAlertNotification, delete: deleteAlertNotification }
const columns = [{"prop": "warningNo", "label": "通知编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "riskLevel", "label": "节点类型"}, {"prop": "triggerReason", "label": "提醒内容"}, {"prop": "handlerName", "label": "接收人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "通知编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "riskLevel", "label": "节点类型"}, {"prop": "triggerReason", "label": "提醒内容", "type": "textarea"}, {"prop": "handlerName", "label": "接收人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WARNING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processAlertNotification(row.id)
  if (command === 'finish') await finishAlertNotification(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>



