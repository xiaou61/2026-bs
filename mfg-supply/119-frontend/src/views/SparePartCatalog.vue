<template>
  <DataPage title="备件目录" description="备件编码、备件名称、类别、规格、适配设备和安全库存管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSparePartCatalogPage, addSparePartCatalog, updateSparePartCatalog, deleteSparePartCatalog, enableSparePartCatalog, disableSparePartCatalog } from '../api'
const api = { page: getSparePartCatalogPage, add: addSparePartCatalog, update: updateSparePartCatalog, delete: deleteSparePartCatalog }
const columns = [{"prop": "partNo", "label": "备件编码"}, {"prop": "partName", "label": "备件名称"}, {"prop": "categoryName", "label": "备件类别"}, {"prop": "specModel", "label": "规格型号"}, {"prop": "fitEquipment", "label": "适配设备"}, {"prop": "safeStock", "label": "安全库存"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "partNo", "label": "备件编码"}, {"prop": "partName", "label": "备件名称"}, {"prop": "categoryName", "label": "备件类别", "type": "select", "options": [{"label": "传动件", "value": "传动件"}, {"label": "过滤件", "value": "过滤件"}, {"label": "电气件", "value": "电气件"}, {"label": "密封件", "value": "密封件"}]}, {"prop": "specModel", "label": "规格型号"}, {"prop": "fitEquipment", "label": "适配设备"}, {"prop": "safeStock", "label": "安全库存", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSparePartCatalog(row.id)
  if (command === 'disable') await disableSparePartCatalog(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
