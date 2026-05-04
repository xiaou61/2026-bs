<template>
  <DataPage title="农资投入" description="种子、肥料、农药等投入品来源和审核状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInputMaterialPage, addInputMaterial, updateInputMaterial, deleteInputMaterial, approveInputMaterial, rejectInputMaterial } from '../api'
const api = { page: getInputMaterialPage, add: addInputMaterial, update: updateInputMaterial, delete: deleteInputMaterial }
const columns = [{"prop": "materialName", "label": "农资名称"}, {"prop": "materialNo", "label": "农资编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "useAmount", "label": "使用量"}, {"prop": "auditStatus", "label": "审核状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "materialName", "label": "农资名称"}, {"prop": "materialNo", "label": "农资编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "useAmount", "label": "使用量", "type": "number"}, {"prop": "auditStatus", "label": "审核状态", "type": "select", "options": [{"label": "WAIT_AUDIT", "value": "WAIT_AUDIT"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "RECORDED", "value": "RECORDED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "primary"}, {"command": "reject", "label": "驳回", "type": "primary"}]
const defaults = {"auditStatus": "WAIT_AUDIT", "status": "RECORDED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveInputMaterial(row.id)
  if (command === 'reject') await rejectInputMaterial(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
