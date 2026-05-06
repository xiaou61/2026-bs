<template>
  <DataPage title="双选审核" description="结果编号、商户负责人编号、审核节点、审核意见、检测员和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAgencyProfilePage, addAgencyProfile, updateAgencyProfile, deleteAgencyProfile, submitAgencyProfile, approveAgencyProfile } from '../api'
const api = { page: getAgencyProfilePage, add: addAgencyProfile, update: updateAgencyProfile, delete: deleteAgencyProfile }
const columns = [{"prop": "invoiceNo", "label": "结果编号"}, {"prop": "claimNo", "label": "商户负责人编号"}, {"prop": "invoiceType", "label": "审核节点"}, {"prop": "invoiceAmount", "label": "审核意见"}, {"prop": "issuerName", "label": "检测员"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "invoiceNo", "label": "结果编号"}, {"prop": "claimNo", "label": "商户负责人编号"}, {"prop": "invoiceType", "label": "审核节点"}, {"prop": "invoiceAmount", "label": "审核意见", "type": "number"}, {"prop": "issuerName", "label": "检测员"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAgencyProfile(row.id)
  if (command === 'approve') await approveAgencyProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>







