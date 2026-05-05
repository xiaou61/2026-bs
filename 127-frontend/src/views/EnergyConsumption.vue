<template>
  <DataPage title="能源消耗" description="能源消耗记录、企业、能源类型、消耗量和单位维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnergyConsumptionPage, addEnergyConsumption, updateEnergyConsumption, deleteEnergyConsumption, submitEnergyConsumption, approveEnergyConsumption } from '../api'
const api = { page: getEnergyConsumptionPage, add: addEnergyConsumption, update: updateEnergyConsumption, delete: deleteEnergyConsumption }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "energyType", "label": "能源类型"}, {"prop": "amountValue", "label": "消耗量"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "energyType", "label": "能源类型"}, {"prop": "amountValue", "label": "消耗量", "type": "number"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitEnergyConsumption(row.id)
  if (command === 'approve') await approveEnergyConsumption(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
