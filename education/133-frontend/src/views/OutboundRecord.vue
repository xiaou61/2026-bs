<template>
  <DataPage title="领用记录" description="领用编号、耗材名称、领用实验室、领用数量、领用人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOutboundRecordPage, addOutboundRecord, updateOutboundRecord, deleteOutboundRecord, submitOutboundRecord, approveOutboundRecord } from '../api'
const api = { page: getOutboundRecordPage, add: addOutboundRecord, update: updateOutboundRecord, delete: deleteOutboundRecord }
const columns = [{"prop": "outboundNo", "label": "领用编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "labName", "label": "领用实验室"}, {"prop": "outboundQty", "label": "领用数量"}, {"prop": "receiverName", "label": "领用人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "outboundNo", "label": "领用编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "labName", "label": "领用实验室"}, {"prop": "outboundQty", "label": "领用数量", "type": "number"}, {"prop": "receiverName", "label": "领用人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitOutboundRecord(row.id)
  if (command === 'approve') await approveOutboundRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
