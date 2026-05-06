<template>
  <DataPage title="参会报名" description="报名编号、参会主题、参会单位、报名人数、联系人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFundingRecordPage, addFundingRecord, updateFundingRecord, deleteFundingRecord, submitFundingRecord, approveFundingRecord } from '../api'
const api = { page: getFundingRecordPage, add: addFundingRecord, update: updateFundingRecord, delete: deleteFundingRecord }
const columns = [{"prop": "outboundNo", "label": "报名编号"}, {"prop": "consumableName", "label": "参会主题"}, {"prop": "labName", "label": "参会单位"}, {"prop": "outboundQty", "label": "报名人数"}, {"prop": "receiverName", "label": "联系人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "outboundNo", "label": "报名编号"}, {"prop": "consumableName", "label": "参会主题"}, {"prop": "labName", "label": "参会单位"}, {"prop": "outboundQty", "label": "报名人数", "type": "number"}, {"prop": "receiverName", "label": "联系人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitFundingRecord(row.id)
  if (command === 'approve') await approveFundingRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>



