<template>
  <DataPage title="采购申请" description="备件采购申请、需求数量、预算金额、申请人、审批人和采购状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProcurementRequestPage, addProcurementRequest, updateProcurementRequest, deleteProcurementRequest, approveProcurementRequest, rejectProcurementRequest } from '../api'
const api = { page: getProcurementRequestPage, add: addProcurementRequest, update: updateProcurementRequest, delete: deleteProcurementRequest }
const columns = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "requestQty", "label": "需求数量"}, {"prop": "budgetAmount", "label": "预算金额"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "approverName", "label": "审批人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "requestQty", "label": "需求数量", "type": "number"}, {"prop": "budgetAmount", "label": "预算金额", "type": "number"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "approverName", "label": "审批人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveProcurementRequest(row.id)
  if (command === 'reject') await rejectProcurementRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
