<template>
  <DataPage title="诱导屏幕" description="屏幕编号、停车场、位置、显示内容、刷新时间和在线状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getGuidanceScreenPage, addGuidanceScreen, updateGuidanceScreen, deleteGuidanceScreen, onlineGuidanceScreen, warnGuidanceScreen } from '../api'
const api = { page: getGuidanceScreenPage, add: addGuidanceScreen, update: updateGuidanceScreen, delete: deleteGuidanceScreen }
const columns = [{"prop": "screenNo", "label": "屏幕编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "positionName", "label": "位置"}, {"prop": "displayText", "label": "显示内容"}, {"prop": "refreshTime", "label": "刷新时间"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "screenNo", "label": "屏幕编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "positionName", "label": "位置"}, {"prop": "displayText", "label": "显示内容"}, {"prop": "refreshTime", "label": "刷新时间"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "online", "label": "上线", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineGuidanceScreen(row.id)
  if (command === 'warn') await warnGuidanceScreen(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
