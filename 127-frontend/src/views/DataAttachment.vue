<template>
  <DataPage title="佐证附件" description="附件编号、企业、文件名、文件类型、上传人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDataAttachmentPage, addDataAttachment, updateDataAttachment, deleteDataAttachment, submitDataAttachment, approveDataAttachment } from '../api'
const api = { page: getDataAttachmentPage, add: addDataAttachment, update: updateDataAttachment, delete: deleteDataAttachment }
const columns = [{"prop": "attachNo", "label": "附件编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "fileName", "label": "文件名称"}, {"prop": "fileType", "label": "文件类型"}, {"prop": "uploaderName", "label": "上传人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "attachNo", "label": "附件编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "fileName", "label": "文件名称"}, {"prop": "fileType", "label": "文件类型"}, {"prop": "uploaderName", "label": "上传人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitDataAttachment(row.id)
  if (command === 'approve') await approveDataAttachment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
