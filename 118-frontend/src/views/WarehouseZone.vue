<template>
  <DataPage title="仓库区域" description="仓库区域、温区类型、巷道数量、负责人和启用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWarehouseZonePage, addWarehouseZone, updateWarehouseZone, deleteWarehouseZone, enableWarehouseZone, disableWarehouseZone } from '../api'
const api = { page: getWarehouseZonePage, add: addWarehouseZone, update: updateWarehouseZone, delete: deleteWarehouseZone }
const columns = [{"prop": "zoneNo", "label": "区域编号"}, {"prop": "zoneName", "label": "区域名称"}, {"prop": "temperatureType", "label": "温区类型"}, {"prop": "aisleCount", "label": "巷道数"}, {"prop": "managerName", "label": "负责人"}, {"prop": "capacityRate", "label": "容量占用率"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "zoneNo", "label": "区域编号"}, {"prop": "zoneName", "label": "区域名称"}, {"prop": "temperatureType", "label": "温区类型", "type": "select", "options": [{"label": "常温", "value": "常温"}, {"label": "冷藏", "value": "冷藏"}, {"label": "恒温", "value": "恒温"}, {"label": "危险品", "value": "危险品"}]}, {"prop": "aisleCount", "label": "巷道数", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "capacityRate", "label": "容量占用率", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableWarehouseZone(row.id)
  if (command === 'disable') await disableWarehouseZone(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
