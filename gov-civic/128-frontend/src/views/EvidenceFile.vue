<template>
  <DataPage title="佐证材料" description="佐证材料、文件名称、文件类型、上传人和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEvidenceFilePage, addEvidenceFile, updateEvidenceFile, deleteEvidenceFile, submitEvidenceFile, approveEvidenceFile } from '../api'
const api = { page: getEvidenceFilePage, add: addEvidenceFile, update: updateEvidenceFile, delete: deleteEvidenceFile }
const columns = [{"prop": "evidenceNo", "label": "材料编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "fileName", "label": "文件名称"}, {"prop": "fileType", "label": "文件类型"}, {"prop": "uploaderName", "label": "上传人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "evidenceNo", "label": "材料编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "fileName", "label": "文件名称"}, {"prop": "fileType", "label": "文件类型"}, {"prop": "uploaderName", "label": "上传人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitEvidenceFile(row.id)
  if (command === 'approve') await approveEvidenceFile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
