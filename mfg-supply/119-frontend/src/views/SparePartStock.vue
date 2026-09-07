<template>
  <DataPage title="备件库存" description="备件库存、仓库、批次、可用数量、冻结数量和库存状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSparePartStockPage, addSparePartStock, updateSparePartStock, deleteSparePartStock, freezeSparePartStock, releaseSparePartStock } from '../api'
const api = { page: getSparePartStockPage, add: addSparePartStock, update: updateSparePartStock, delete: deleteSparePartStock }
const columns = [{"prop": "stockNo", "label": "库存编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "warehouseName", "label": "仓库"}, {"prop": "batchNo", "label": "批次号"}, {"prop": "availableQty", "label": "可用数量"}, {"prop": "frozenQty", "label": "冻结数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stockNo", "label": "库存编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "warehouseName", "label": "仓库"}, {"prop": "batchNo", "label": "批次号"}, {"prop": "availableQty", "label": "可用数量", "type": "number"}, {"prop": "frozenQty", "label": "冻结数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "FROZEN", "value": "FROZEN"}, {"label": "SHORTAGE", "value": "SHORTAGE"}]}]
const rowActions = [{"command": "freeze", "label": "冻结", "type": "warning"}, {"command": "release", "label": "释放", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'freeze') await freezeSparePartStock(row.id)
  if (command === 'release') await releaseSparePartStock(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
