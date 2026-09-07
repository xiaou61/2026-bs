<template>
  <DataPage title="病害诊断" description="诊断编号、作物批次、病害名称、建议方案和诊断状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDiseaseDiagnosisPage, addDiseaseDiagnosis, updateDiseaseDiagnosis, deleteDiseaseDiagnosis, submitDiseaseDiagnosis, approveDiseaseDiagnosis } from '../api'
const api = { page: getDiseaseDiagnosisPage, add: addDiseaseDiagnosis, update: updateDiseaseDiagnosis, delete: deleteDiseaseDiagnosis }
const columns = [{"prop": "diagnosisNo", "label": "诊断编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "diseaseName", "label": "病害名称"}, {"prop": "suggestionText", "label": "建议方案"}, {"prop": "expertName", "label": "专家"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "diagnosisNo", "label": "诊断编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "diseaseName", "label": "病害名称"}, {"prop": "suggestionText", "label": "建议方案"}, {"prop": "expertName", "label": "专家"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitDiseaseDiagnosis(row.id)
  if (command === 'approve') await approveDiseaseDiagnosis(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
