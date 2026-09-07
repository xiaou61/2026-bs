<template>
  <DataPage title="经费预算" description="预算编号、项目编号、预算科目、预算金额、已用金额和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBudgetAllocationPage, addBudgetAllocation, updateBudgetAllocation, deleteBudgetAllocation, processBudgetAllocation, finishBudgetAllocation } from '../api'
const api = { page: getBudgetAllocationPage, add: addBudgetAllocation, update: updateBudgetAllocation, delete: deleteBudgetAllocation }
const columns = [{"prop": "budgetNo", "label": "预算编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "categoryName", "label": "预算科目"}, {"prop": "budgetAmount", "label": "预算金额"}, {"prop": "usedAmount", "label": "已用金额"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "budgetNo", "label": "预算编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "categoryName", "label": "预算科目"}, {"prop": "budgetAmount", "label": "预算金额", "type": "number"}, {"prop": "usedAmount", "label": "已用金额", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processBudgetAllocation(row.id)
  if (command === 'finish') await finishBudgetAllocation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
