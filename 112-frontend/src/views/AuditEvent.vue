<template>
  <DataPage title="审计事件" description="准入、阻断、访问和策略命中审计事件记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAuditEventPage, addAuditEvent, updateAuditEvent, deleteAuditEvent, ackAuditEvent, closeAuditEvent } from '../api'
const api = { page: getAuditEventPage, add: addAuditEvent, update: updateAuditEvent, delete: deleteAuditEvent }
const columns = [{"prop": "eventNo", "label": "事件编号"}, {"prop": "eventType", "label": "事件类型"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "detailText", "label": "详情"}, {"prop": "eventTime", "label": "事件时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "eventNo", "label": "事件编号"}, {"prop": "eventType", "label": "事件类型", "type": "select", "options": [{"label": "ADMISSION", "value": "ADMISSION"}, {"label": "BLOCK", "value": "BLOCK"}, {"label": "SESSION", "value": "SESSION"}, {"label": "POLICY_HIT", "value": "POLICY_HIT"}]}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "detailText", "label": "详情", "type": "textarea"}, {"prop": "eventTime", "label": "事件时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ACKED", "value": "ACKED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "ack", "label": "确认", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'ack') await ackAuditEvent(row.id)
  if (command === 'close') await closeAuditEvent(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
