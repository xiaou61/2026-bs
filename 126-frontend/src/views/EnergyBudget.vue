<template>
  <DataPage title="能耗预算" description="预算编号、家庭、月份、预算电量、预算金额和执行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnergyBudgetPage, addEnergyBudget, updateEnergyBudget, deleteEnergyBudget, enableEnergyBudget, overEnergyBudget } from '../api'
const api = { page: getEnergyBudgetPage, add: addEnergyBudget, update: updateEnergyBudget, delete: deleteEnergyBudget }
const columns = [{"prop": "budgetNo", "label": "预算编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "budgetMonth", "label": "预算月份"}, {"prop": "budgetKwh", "label": "预算电量"}, {"prop": "budgetAmount", "label": "预算金额"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "budgetNo", "label": "预算编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "budgetMonth", "label": "预算月份"}, {"prop": "budgetKwh", "label": "预算电量", "type": "number"}, {"prop": "budgetAmount", "label": "预算金额", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "over", "label": "超预算", "type": "warning"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableEnergyBudget(row.id)
  if (command === 'over') await overEnergyBudget(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
