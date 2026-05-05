<template>
  <DataPage title="清关申报" description="清关申报单、口岸、申报类型、金额、风险等级和放行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getClearanceDeclarationPage, addClearanceDeclaration, updateClearanceDeclaration, deleteClearanceDeclaration, submitClearanceDeclaration, releaseClearanceDeclaration, rejectClearanceDeclaration } from '../api'
const api = { page: getClearanceDeclarationPage, add: addClearanceDeclaration, update: updateClearanceDeclaration, delete: deleteClearanceDeclaration }
const columns = [{"prop": "declarationNo", "label": "申报单号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "customsPort", "label": "清关口岸"}, {"prop": "declareType", "label": "申报类型"}, {"prop": "declareAmount", "label": "申报金额"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "declarationNo", "label": "申报单号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "customsPort", "label": "清关口岸"}, {"prop": "declareType", "label": "申报类型", "type": "select", "options": [{"label": "B2C", "value": "B2C"}, {"label": "B2B", "value": "B2B"}, {"label": "RETURN", "value": "RETURN"}]}, {"prop": "declareAmount", "label": "申报金额", "type": "number"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "RELEASED", "value": "RELEASED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "release", "label": "放行", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitClearanceDeclaration(row.id)
  if (command === 'release') await releaseClearanceDeclaration(row.id)
  if (command === 'reject') await rejectClearanceDeclaration(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
