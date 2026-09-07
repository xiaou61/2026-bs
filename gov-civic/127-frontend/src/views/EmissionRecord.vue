<template>
  <DataPage title="排放记录" description="排放编号、企业、范围类型、碳排放量和来源维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEmissionRecordPage, addEmissionRecord, updateEmissionRecord, deleteEmissionRecord, submitEmissionRecord, approveEmissionRecord } from '../api'
const api = { page: getEmissionRecordPage, add: addEmissionRecord, update: updateEmissionRecord, delete: deleteEmissionRecord }
const columns = [{"prop": "emissionNo", "label": "排放编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "scopeType", "label": "范围类型"}, {"prop": "carbonAmount", "label": "碳排放量"}, {"prop": "sourceName", "label": "排放来源"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "emissionNo", "label": "排放编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "scopeType", "label": "范围类型"}, {"prop": "carbonAmount", "label": "碳排放量", "type": "number"}, {"prop": "sourceName", "label": "排放来源"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitEmissionRecord(row.id)
  if (command === 'approve') await approveEmissionRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
