<template>
  <DataPage title="冷链货品" description="冷链货品批次、品类、保鲜温区和出库状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProductCargoPage, addProductCargo, updateProductCargo, deleteProductCargo, shipProductCargo, arriveProductCargo } from '../api'
const api = { page: getProductCargoPage, add: addProductCargo, update: updateProductCargo, delete: deleteProductCargo }
const columns = [{"prop": "cargoName", "label": "货品名称"}, {"prop": "cargoNo", "label": "货品批次"}, {"prop": "categoryName", "label": "品类"}, {"prop": "ownerName", "label": "货主"}, {"prop": "temperatureRange", "label": "保鲜温区"}, {"prop": "cargoWeight", "label": "重量kg"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "cargoName", "label": "货品名称"}, {"prop": "cargoNo", "label": "货品批次"}, {"prop": "categoryName", "label": "品类"}, {"prop": "ownerName", "label": "货主"}, {"prop": "temperatureRange", "label": "保鲜温区"}, {"prop": "cargoWeight", "label": "重量kg", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_SHIP", "value": "WAIT_SHIP"}, {"label": "SHIPPED", "value": "SHIPPED"}, {"label": "ARRIVED", "value": "ARRIVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "ship", "label": "出库", "type": "primary"}, {"command": "arrive", "label": "入库", "type": "primary"}]
const defaults = {"status": "WAIT_SHIP"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'ship') await shipProductCargo(row.id)
  if (command === 'arrive') await arriveProductCargo(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
