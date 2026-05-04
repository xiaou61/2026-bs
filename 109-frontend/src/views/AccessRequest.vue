<template>
  <DataPage title="访问申请" description="敏感数据访问申请、用途、有效期和审批状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccessRequestPage, addAccessRequest, updateAccessRequest, deleteAccessRequest, submitAccessRequest, approveAccessRequest, rejectAccessRequest, revokeAccessRequest } from '../api'
const api = { page: getAccessRequestPage, add: addAccessRequest, update: updateAccessRequest, delete: deleteAccessRequest }
const columns = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "requesterName", "label": "申请人"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "purposeText", "label": "用途"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "requesterName", "label": "申请人"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "purposeText", "label": "用途", "type": "textarea"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "REVOKED", "value": "REVOKED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "primary"}, {"command": "reject", "label": "驳回", "type": "primary"}, {"command": "revoke", "label": "撤销", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAccessRequest(row.id)
  if (command === 'approve') await approveAccessRequest(row.id)
  if (command === 'reject') await rejectAccessRequest(row.id)
  if (command === 'revoke') await revokeAccessRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
