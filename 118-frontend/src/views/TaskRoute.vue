<template>
  <DataPage title="路径规划" description="任务路径、起止点、里程、预计耗时、拥堵等级和路径状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTaskRoutePage, addTaskRoute, updateTaskRoute, deleteTaskRoute, enableTaskRoute, disableTaskRoute } from '../api'
const api = { page: getTaskRoutePage, add: addTaskRoute, update: updateTaskRoute, delete: deleteTaskRoute }
const columns = [{"prop": "routeNo", "label": "路径编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "startPoint", "label": "起点"}, {"prop": "endPoint", "label": "终点"}, {"prop": "distanceMeter", "label": "路径距离"}, {"prop": "estimateSeconds", "label": "预计秒数"}, {"prop": "congestionLevel", "label": "拥堵等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "routeNo", "label": "路径编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "startPoint", "label": "起点"}, {"prop": "endPoint", "label": "终点"}, {"prop": "distanceMeter", "label": "路径距离", "type": "number"}, {"prop": "estimateSeconds", "label": "预计秒数", "type": "number"}, {"prop": "congestionLevel", "label": "拥堵等级", "type": "select", "options": [{"label": "低", "value": "低"}, {"label": "中", "value": "中"}, {"label": "高", "value": "高"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableTaskRoute(row.id)
  if (command === 'disable') await disableTaskRoute(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
