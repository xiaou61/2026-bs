<template>
  <DataPage title="采购审批" description="审批编号、申请编号、审批人、审批意见、审批时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPurchaseApprovalPage, addPurchaseApproval, updatePurchaseApproval, deletePurchaseApproval, submitPurchaseApproval, approvePurchaseApproval } from '../api'
const api = { page: getPurchaseApprovalPage, add: addPurchaseApproval, update: updatePurchaseApproval, delete: deletePurchaseApproval }
const columns = [{"prop": "approvalNo", "label": "审批编号"}, {"prop": "requestNo", "label": "申请编号"}, {"prop": "approverName", "label": "审批人"}, {"prop": "approvalOpinion", "label": "审批意见"}, {"prop": "approvalTime", "label": "审批时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "approvalNo", "label": "审批编号"}, {"prop": "requestNo", "label": "申请编号"}, {"prop": "approverName", "label": "审批人"}, {"prop": "approvalOpinion", "label": "审批意见", "type": "textarea"}, {"prop": "approvalTime", "label": "审批时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitPurchaseApproval(row.id)
  if (command === 'approve') await approvePurchaseApproval(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
