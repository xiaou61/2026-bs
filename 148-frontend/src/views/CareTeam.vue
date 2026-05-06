<template>
  <DataPage title="双选审核" description="记录编号、护理人员编号、审核节点、审核意见、服务人员和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCareTeamPage, addCareTeam, updateCareTeam, deleteCareTeam, submitCareTeam, approveCareTeam } from '../api'
const api = { page: getCareTeamPage, add: addCareTeam, update: updateCareTeam, delete: deleteCareTeam }
const columns = [{"prop": "invoiceNo", "label": "记录编号"}, {"prop": "claimNo", "label": "护理人员编号"}, {"prop": "invoiceType", "label": "审核节点"}, {"prop": "invoiceAmount", "label": "审核意见"}, {"prop": "issuerName", "label": "服务人员"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "invoiceNo", "label": "记录编号"}, {"prop": "claimNo", "label": "护理人员编号"}, {"prop": "invoiceType", "label": "审核节点"}, {"prop": "invoiceAmount", "label": "审核意见", "type": "number"}, {"prop": "issuerName", "label": "服务人员"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCareTeam(row.id)
  if (command === 'approve') await approveCareTeam(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








