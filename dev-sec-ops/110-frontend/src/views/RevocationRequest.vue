<template>
  <DataPage title="撤销申请" description="个人授权撤销申请、原因和处理状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRevocationRequestPage, addRevocationRequest, updateRevocationRequest, deleteRevocationRequest, approveRevocationRequest, rejectRevocationRequest, finishRevocationRequest } from '../api'
const api = { page: getRevocationRequestPage, add: addRevocationRequest, update: updateRevocationRequest, delete: deleteRevocationRequest }
const columns = [{"prop": "requestNo", "label": "撤销编号"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "authNo", "label": "授权编号"}, {"prop": "reasonText", "label": "撤销原因"}, {"prop": "requestTime", "label": "申请时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "撤销编号"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "authNo", "label": "授权编号"}, {"prop": "reasonText", "label": "撤销原因", "type": "textarea"}, {"prop": "requestTime", "label": "申请时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "primary"}, {"command": "reject", "label": "驳回", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveRevocationRequest(row.id)
  if (command === 'reject') await rejectRevocationRequest(row.id)
  if (command === 'finish') await finishRevocationRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
