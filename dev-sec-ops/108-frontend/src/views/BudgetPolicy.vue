<template>
  <DataPage title="预算策略" description="空间预算、告警比例和周期策略管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBudgetPolicyPage, addBudgetPolicy, updateBudgetPolicy, deleteBudgetPolicy, enableBudgetPolicy, archiveBudgetPolicy } from '../api'
const api = { page: getBudgetPolicyPage, add: addBudgetPolicy, update: updateBudgetPolicy, delete: deleteBudgetPolicy }
const columns = [{"prop": "policyName", "label": "策略名称"}, {"prop": "namespaceName", "label": "空间名称"}, {"prop": "budgetAmount", "label": "预算金额"}, {"prop": "alertPercent", "label": "告警比例"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "cycleType", "label": "周期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "policyName", "label": "策略名称"}, {"prop": "namespaceName", "label": "空间名称"}, {"prop": "budgetAmount", "label": "预算金额", "type": "number"}, {"prop": "alertPercent", "label": "告警比例", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "cycleType", "label": "周期", "type": "select", "options": [{"label": "MONTH", "value": "MONTH"}, {"label": "QUARTER", "value": "QUARTER"}, {"label": "YEAR", "value": "YEAR"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}, {"label": "PAUSED", "value": "PAUSED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"cycleType": "MONTH", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableBudgetPolicy(row.id)
  if (command === 'archive') await archiveBudgetPolicy(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
