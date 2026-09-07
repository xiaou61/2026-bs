<template>
  <DataPage title="库存物料" description="物料SKU、批次、库位、数量、周转等级和库存状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInventoryItemPage, addInventoryItem, updateInventoryItem, deleteInventoryItem, freezeInventoryItem, releaseInventoryItem } from '../api'
const api = { page: getInventoryItemPage, add: addInventoryItem, update: updateInventoryItem, delete: deleteInventoryItem }
const columns = [{"prop": "skuNo", "label": "SKU编号"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "batchNo", "label": "批次号"}, {"prop": "locationNo", "label": "库位编码"}, {"prop": "quantity", "label": "库存数量"}, {"prop": "turnoverLevel", "label": "周转等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "skuNo", "label": "SKU编号"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "batchNo", "label": "批次号"}, {"prop": "locationNo", "label": "库位编码"}, {"prop": "quantity", "label": "库存数量", "type": "number"}, {"prop": "turnoverLevel", "label": "周转等级", "type": "select", "options": [{"label": "A类", "value": "A类"}, {"label": "B类", "value": "B类"}, {"label": "C类", "value": "C类"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "FROZEN", "value": "FROZEN"}, {"label": "SHORTAGE", "value": "SHORTAGE"}]}]
const rowActions = [{"command": "freeze", "label": "冻结", "type": "warning"}, {"command": "release", "label": "释放", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'freeze') await freezeInventoryItem(row.id)
  if (command === 'release') await releaseInventoryItem(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
