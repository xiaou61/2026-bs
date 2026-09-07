<template>
  <DataPage title="录用通知" description="通知编号、稿件编号、论文题目、录用结果、通知人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAcceptanceNoticePage, addAcceptanceNotice, updateAcceptanceNotice, deleteAcceptanceNotice, submitAcceptanceNotice, approveAcceptanceNotice } from '../api'
const api = { page: getAcceptanceNoticePage, add: addAcceptanceNotice, update: updateAcceptanceNotice, delete: deleteAcceptanceNotice }
const columns = [{"prop": "inboundNo", "label": "通知编号"}, {"prop": "orderNo", "label": "稿件编号"}, {"prop": "consumableName", "label": "论文题目"}, {"prop": "inboundQty", "label": "录用结果"}, {"prop": "operatorName", "label": "通知人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "inboundNo", "label": "通知编号"}, {"prop": "orderNo", "label": "稿件编号"}, {"prop": "consumableName", "label": "论文题目"}, {"prop": "inboundQty", "label": "录用结果", "type": "number"}, {"prop": "operatorName", "label": "通知人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAcceptanceNotice(row.id)
  if (command === 'approve') await approveAcceptanceNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>


