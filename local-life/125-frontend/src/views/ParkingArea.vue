<template>
  <DataPage title="停车区域" description="区域编号、停车场、区域名称、楼层、车位数量和开放状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getParkingAreaPage, addParkingArea, updateParkingArea, deleteParkingArea, enableParkingArea, disableParkingArea } from '../api'
const api = { page: getParkingAreaPage, add: addParkingArea, update: updateParkingArea, delete: deleteParkingArea }
const columns = [{"prop": "areaNo", "label": "区域编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "areaName", "label": "区域名称"}, {"prop": "floorName", "label": "楼层"}, {"prop": "spaceCount", "label": "车位数"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "areaNo", "label": "区域编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "areaName", "label": "区域名称"}, {"prop": "floorName", "label": "楼层"}, {"prop": "spaceCount", "label": "车位数", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableParkingArea(row.id)
  if (command === 'disable') await disableParkingArea(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
