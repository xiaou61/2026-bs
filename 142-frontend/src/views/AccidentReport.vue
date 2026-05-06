<template>
  <DataPage title="双选审核" description="审核编号、报案编号、审核节点、审核意见、审核人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccidentReportPage, addAccidentReport, updateAccidentReport, deleteAccidentReport, submitAccidentReport, approveAccidentReport } from '../api'
const api = { page: getAccidentReportPage, add: addAccidentReport, update: updateAccidentReport, delete: deleteAccidentReport }
const columns = [{"prop": "invoiceNo", "label": "审核编号"}, {"prop": "claimNo", "label": "报案编号"}, {"prop": "invoiceType", "label": "审核节点"}, {"prop": "invoiceAmount", "label": "审核意见"}, {"prop": "issuerName", "label": "审核人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "invoiceNo", "label": "审核编号"}, {"prop": "claimNo", "label": "报案编号"}, {"prop": "invoiceType", "label": "审核节点"}, {"prop": "invoiceAmount", "label": "审核意见", "type": "number"}, {"prop": "issuerName", "label": "审核人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAccidentReport(row.id)
  if (command === 'approve') await approveAccidentReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>





