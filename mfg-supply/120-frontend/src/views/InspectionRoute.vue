<template>
  <DataPage title="巡检路线" description="巡检路线、所属楼宇、点位数量、预计耗时、负责人和路线状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionRoutePage, addInspectionRoute, updateInspectionRoute, deleteInspectionRoute, enableInspectionRoute, disableInspectionRoute } from '../api'
const api = { page: getInspectionRoutePage, add: addInspectionRoute, update: updateInspectionRoute, delete: deleteInspectionRoute }
const columns = [{"prop": "routeNo", "label": "路线编号"}, {"prop": "routeName", "label": "路线名称"}, {"prop": "buildingName", "label": "所属楼宇"}, {"prop": "pointCount", "label": "点位数量"}, {"prop": "estimateMinutes", "label": "预计分钟"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "routeNo", "label": "路线编号"}, {"prop": "routeName", "label": "路线名称"}, {"prop": "buildingName", "label": "所属楼宇"}, {"prop": "pointCount", "label": "点位数量", "type": "number"}, {"prop": "estimateMinutes", "label": "预计分钟", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableInspectionRoute(row.id)
  if (command === 'disable') await disableInspectionRoute(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
