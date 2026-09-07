<template>
  <DataPage title="出库领用" description="备件出库单、设备、领用人、领用数量、用途和出库状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSparePartOutboundPage, addSparePartOutbound, updateSparePartOutbound, deleteSparePartOutbound, approveSparePartOutbound, issueSparePartOutbound } from '../api'
const api = { page: getSparePartOutboundPage, add: addSparePartOutbound, update: updateSparePartOutbound, delete: deleteSparePartOutbound }
const columns = [{"prop": "outboundNo", "label": "出库单号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "equipmentName", "label": "适用设备"}, {"prop": "receiverName", "label": "领用人"}, {"prop": "outboundQty", "label": "领用数量"}, {"prop": "purposeText", "label": "用途"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "outboundNo", "label": "出库单号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "equipmentName", "label": "适用设备"}, {"prop": "receiverName", "label": "领用人"}, {"prop": "outboundQty", "label": "领用数量", "type": "number"}, {"prop": "purposeText", "label": "用途", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "ISSUED", "value": "ISSUED"}]}]
const rowActions = [{"command": "approve", "label": "审批", "type": "primary"}, {"command": "issue", "label": "出库", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveSparePartOutbound(row.id)
  if (command === 'issue') await issueSparePartOutbound(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
