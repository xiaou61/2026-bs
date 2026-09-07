<template>
  <DataPage title="入库记录" description="备件入库单、供应商、入库数量、质检结果和入库状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSparePartInboundPage, addSparePartInbound, updateSparePartInbound, deleteSparePartInbound, checkSparePartInbound, finishSparePartInbound } from '../api'
const api = { page: getSparePartInboundPage, add: addSparePartInbound, update: updateSparePartInbound, delete: deleteSparePartInbound }
const columns = [{"prop": "inboundNo", "label": "入库单号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "inboundQty", "label": "入库数量"}, {"prop": "qualityResult", "label": "质检结果"}, {"prop": "inboundTime", "label": "入库时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "inboundNo", "label": "入库单号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "inboundQty", "label": "入库数量", "type": "number"}, {"prop": "qualityResult", "label": "质检结果", "type": "select", "options": [{"label": "合格", "value": "合格"}, {"label": "待检", "value": "待检"}, {"label": "不合格", "value": "不合格"}]}, {"prop": "inboundTime", "label": "入库时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "CHECKED", "value": "CHECKED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "check", "label": "质检", "type": "primary"}, {"command": "finish", "label": "入库", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'check') await checkSparePartInbound(row.id)
  if (command === 'finish') await finishSparePartInbound(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
