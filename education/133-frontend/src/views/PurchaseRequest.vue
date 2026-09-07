<template>
  <DataPage title="采购申请" description="申请编号、耗材名称、申请数量、申请人、申请时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPurchaseRequestPage, addPurchaseRequest, updatePurchaseRequest, deletePurchaseRequest, submitPurchaseRequest, approvePurchaseRequest } from '../api'
const api = { page: getPurchaseRequestPage, add: addPurchaseRequest, update: updatePurchaseRequest, delete: deletePurchaseRequest }
const columns = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "requestQty", "label": "申请数量"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "requestTime", "label": "申请时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "requestQty", "label": "申请数量", "type": "number"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "requestTime", "label": "申请时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitPurchaseRequest(row.id)
  if (command === 'approve') await approvePurchaseRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
