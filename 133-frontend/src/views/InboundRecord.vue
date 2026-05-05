<template>
  <DataPage title="入库记录" description="入库编号、订单编号、耗材名称、入库数量、入库人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInboundRecordPage, addInboundRecord, updateInboundRecord, deleteInboundRecord, submitInboundRecord, approveInboundRecord } from '../api'
const api = { page: getInboundRecordPage, add: addInboundRecord, update: updateInboundRecord, delete: deleteInboundRecord }
const columns = [{"prop": "inboundNo", "label": "入库编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "inboundQty", "label": "入库数量"}, {"prop": "operatorName", "label": "入库人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "inboundNo", "label": "入库编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "inboundQty", "label": "入库数量", "type": "number"}, {"prop": "operatorName", "label": "入库人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitInboundRecord(row.id)
  if (command === 'approve') await approveInboundRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
