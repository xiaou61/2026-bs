<template>
  <DataPage title="航线规划" description="航线编号、名称、区域、里程、航点数量和风险等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFlightRoutePage, addFlightRoute, updateFlightRoute, deleteFlightRoute, publishFlightRoute, disableFlightRoute } from '../api'
const api = { page: getFlightRoutePage, add: addFlightRoute, update: updateFlightRoute, delete: deleteFlightRoute }
const columns = [{"prop": "routeNo", "label": "航线编号"}, {"prop": "routeName", "label": "航线名称"}, {"prop": "zoneName", "label": "巡检区域"}, {"prop": "distanceKm", "label": "里程km"}, {"prop": "waypointCount", "label": "航点数"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "routeNo", "label": "航线编号"}, {"prop": "routeName", "label": "航线名称"}, {"prop": "zoneName", "label": "巡检区域"}, {"prop": "distanceKm", "label": "里程km", "type": "number"}, {"prop": "waypointCount", "label": "航点数", "type": "number"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishFlightRoute(row.id)
  if (command === 'disable') await disableFlightRoute(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
