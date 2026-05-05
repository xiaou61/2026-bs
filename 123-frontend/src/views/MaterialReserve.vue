<template>
  <DataPage title="物资储备" description="物资编号、名称、类型、库存、安全库存和仓库位置管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaterialReservePage, addMaterialReserve, updateMaterialReserve, deleteMaterialReserve, enableMaterialReserve, warnMaterialReserve } from '../api'
const api = { page: getMaterialReservePage, add: addMaterialReserve, update: updateMaterialReserve, delete: deleteMaterialReserve }
const columns = [{"prop": "materialNo", "label": "物资编号"}, {"prop": "materialName", "label": "物资名称"}, {"prop": "materialType", "label": "物资类型"}, {"prop": "stockQty", "label": "库存"}, {"prop": "safeQty", "label": "安全库存"}, {"prop": "warehouseName", "label": "仓库"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "materialNo", "label": "物资编号"}, {"prop": "materialName", "label": "物资名称"}, {"prop": "materialType", "label": "物资类型"}, {"prop": "stockQty", "label": "库存", "type": "number"}, {"prop": "safeQty", "label": "安全库存", "type": "number"}, {"prop": "warehouseName", "label": "仓库"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableMaterialReserve(row.id)
  if (command === 'warn') await warnMaterialReserve(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
