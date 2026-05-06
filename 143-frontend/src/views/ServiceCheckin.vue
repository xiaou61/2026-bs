<template>
  <DataPage title="审稿分配" description="分配编号、投稿编号、审稿专家、分配意见、分配时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getServiceCheckinPage, addServiceCheckin, updateServiceCheckin, deleteServiceCheckin, submitServiceCheckin, approveServiceCheckin } from '../api'
const api = { page: getServiceCheckinPage, add: addServiceCheckin, update: updateServiceCheckin, delete: deleteServiceCheckin }
const columns = [{"prop": "approvalNo", "label": "分配编号"}, {"prop": "requestNo", "label": "投稿编号"}, {"prop": "approverName", "label": "审稿专家"}, {"prop": "approvalOpinion", "label": "分配意见"}, {"prop": "approvalTime", "label": "分配时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "approvalNo", "label": "分配编号"}, {"prop": "requestNo", "label": "投稿编号"}, {"prop": "approverName", "label": "审稿专家"}, {"prop": "approvalOpinion", "label": "分配意见", "type": "textarea"}, {"prop": "approvalTime", "label": "分配时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitServiceCheckin(row.id)
  if (command === 'approve') await approveServiceCheckin(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>






