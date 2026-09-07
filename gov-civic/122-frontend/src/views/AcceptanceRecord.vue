<template>
  <DataPage title="验收记录" description="验收编号、工单编号、验收人、验收时间、结果和评分管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAcceptanceRecordPage, addAcceptanceRecord, updateAcceptanceRecord, deleteAcceptanceRecord, passAcceptanceRecord, failAcceptanceRecord } from '../api'
const api = { page: getAcceptanceRecordPage, add: addAcceptanceRecord, update: updateAcceptanceRecord, delete: deleteAcceptanceRecord }
const columns = [{"prop": "acceptNo", "label": "验收编号"}, {"prop": "orderNo", "label": "工单编号"}, {"prop": "inspectorName", "label": "验收人"}, {"prop": "acceptTime", "label": "验收时间"}, {"prop": "acceptResult", "label": "验收结果"}, {"prop": "score", "label": "评分"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "acceptNo", "label": "验收编号"}, {"prop": "orderNo", "label": "工单编号"}, {"prop": "inspectorName", "label": "验收人"}, {"prop": "acceptTime", "label": "验收时间"}, {"prop": "acceptResult", "label": "验收结果", "type": "select", "options": [{"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}]}, {"prop": "score", "label": "评分", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "pass", "label": "通过", "type": "success"}, {"command": "fail", "label": "不通过", "type": "danger"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'pass') await passAcceptanceRecord(row.id)
  if (command === 'fail') await failAcceptanceRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
