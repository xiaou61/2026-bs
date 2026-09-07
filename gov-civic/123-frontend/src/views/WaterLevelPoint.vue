<template>
  <DataPage title="水位点位" description="点位编号、名称、行政区、河道/道路、警戒水位和状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWaterLevelPointPage, addWaterLevelPoint, updateWaterLevelPoint, deleteWaterLevelPoint, enableWaterLevelPoint, disableWaterLevelPoint } from '../api'
const api = { page: getWaterLevelPointPage, add: addWaterLevelPoint, update: updateWaterLevelPoint, delete: deleteWaterLevelPoint }
const columns = [{"prop": "pointNo", "label": "点位编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "districtName", "label": "行政区"}, {"prop": "locationName", "label": "位置"}, {"prop": "warningLevel", "label": "警戒水位"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pointNo", "label": "点位编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "districtName", "label": "行政区"}, {"prop": "locationName", "label": "位置"}, {"prop": "warningLevel", "label": "警戒水位", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableWaterLevelPoint(row.id)
  if (command === 'disable') await disableWaterLevelPoint(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
