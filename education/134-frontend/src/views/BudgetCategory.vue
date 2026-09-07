<template>
  <DataPage title="预算科目" description="科目编号、科目名称、经费用途、控制方式、负责人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBudgetCategoryPage, addBudgetCategory, updateBudgetCategory, deleteBudgetCategory, activateBudgetCategory, finishBudgetCategory } from '../api'
const api = { page: getBudgetCategoryPage, add: addBudgetCategory, update: updateBudgetCategory, delete: deleteBudgetCategory }
const columns = [{"prop": "categoryNo", "label": "科目编号"}, {"prop": "categoryName", "label": "科目名称"}, {"prop": "usageScope", "label": "经费用途"}, {"prop": "controlMode", "label": "控制方式"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "categoryNo", "label": "科目编号"}, {"prop": "categoryName", "label": "科目名称"}, {"prop": "usageScope", "label": "经费用途"}, {"prop": "controlMode", "label": "控制方式"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateBudgetCategory(row.id)
  if (command === 'finish') await finishBudgetCategory(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
