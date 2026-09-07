<template>
  <DataPage title="异常告警" description="温控异常、设备离线、路线偏离和告警处置状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExceptionAlertPage, addExceptionAlert, updateExceptionAlert, deleteExceptionAlert, ackExceptionAlert, resolveExceptionAlert, closeExceptionAlert } from '../api'
const api = { page: getExceptionAlertPage, add: addExceptionAlert, update: updateExceptionAlert, delete: deleteExceptionAlert }
const columns = [{"prop": "alertNo", "label": "告警编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "alertType", "label": "告警类型"}, {"prop": "cargoName", "label": "货品"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "alertTime", "label": "告警时间"}, {"prop": "detailText", "label": "详情"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "alertNo", "label": "告警编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "alertType", "label": "告警类型", "type": "select", "options": [{"label": "TEMP", "value": "TEMP"}, {"label": "HUMIDITY", "value": "HUMIDITY"}, {"label": "OFFLINE", "value": "OFFLINE"}, {"label": "ROUTE", "value": "ROUTE"}]}, {"prop": "cargoName", "label": "货品"}, {"prop": "severityLevel", "label": "严重等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "alertTime", "label": "告警时间"}, {"prop": "detailText", "label": "详情", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ACKED", "value": "ACKED"}, {"label": "RESOLVED", "value": "RESOLVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "ack", "label": "确认", "type": "primary"}, {"command": "resolve", "label": "解决", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"severityLevel": "MEDIUM", "status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'ack') await ackExceptionAlert(row.id)
  if (command === 'resolve') await resolveExceptionAlert(row.id)
  if (command === 'close') await closeExceptionAlert(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
