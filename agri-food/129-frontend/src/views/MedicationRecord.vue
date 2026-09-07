<template>
  <DataPage title="用药记录" description="用药编号、池塘、药品名称、用量和使用日期维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMedicationRecordPage, addMedicationRecord, updateMedicationRecord, deleteMedicationRecord, submitMedicationRecord, approveMedicationRecord } from '../api'
const api = { page: getMedicationRecordPage, add: addMedicationRecord, update: updateMedicationRecord, delete: deleteMedicationRecord }
const columns = [{"prop": "medNo", "label": "用药编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "medicineName", "label": "药品名称"}, {"prop": "useAmount", "label": "使用量"}, {"prop": "useDate", "label": "使用日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "medNo", "label": "用药编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "medicineName", "label": "药品名称"}, {"prop": "useAmount", "label": "使用量", "type": "number"}, {"prop": "useDate", "label": "使用日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitMedicationRecord(row.id)
  if (command === 'approve') await approveMedicationRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
