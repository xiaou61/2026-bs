<template>
  <DataPage title="成本分摊" description="业务线成本分摊、归属人和月度金额管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCostAllocationPage, addCostAllocation, updateCostAllocation, deleteCostAllocation, submitCostAllocation, approveCostAllocation, rejectCostAllocation } from '../api'
const api = { page: getCostAllocationPage, add: addCostAllocation, update: updateCostAllocation, delete: deleteCostAllocation }
const columns = [{"prop": "allocationNo", "label": "分摊编号"}, {"prop": "namespaceName", "label": "空间名称"}, {"prop": "businessLine", "label": "业务线"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "allocatedAmount", "label": "分摊金额"}, {"prop": "allocationMonth", "label": "分摊月份"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "allocationNo", "label": "分摊编号"}, {"prop": "namespaceName", "label": "空间名称"}, {"prop": "businessLine", "label": "业务线"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "allocatedAmount", "label": "分摊金额", "type": "number"}, {"prop": "allocationMonth", "label": "分摊月份"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "primary"}, {"command": "reject", "label": "驳回", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCostAllocation(row.id)
  if (command === 'approve') await approveCostAllocation(row.id)
  if (command === 'reject') await rejectCostAllocation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
