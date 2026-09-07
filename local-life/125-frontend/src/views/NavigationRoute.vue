<template>
  <DataPage title="导航路线" description="路线编号、停车场、入口、目标区域、距离和拥堵等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getNavigationRoutePage, addNavigationRoute, updateNavigationRoute, deleteNavigationRoute, publishNavigationRoute, disableNavigationRoute } from '../api'
const api = { page: getNavigationRoutePage, add: addNavigationRoute, update: updateNavigationRoute, delete: deleteNavigationRoute }
const columns = [{"prop": "routeNo", "label": "路线编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "entryName", "label": "入口"}, {"prop": "targetArea", "label": "目标区域"}, {"prop": "distanceMeter", "label": "距离米"}, {"prop": "congestionLevel", "label": "拥堵等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "routeNo", "label": "路线编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "entryName", "label": "入口"}, {"prop": "targetArea", "label": "目标区域"}, {"prop": "distanceMeter", "label": "距离米", "type": "number"}, {"prop": "congestionLevel", "label": "拥堵等级", "type": "select", "options": [{"label": "低", "value": "低"}, {"label": "中", "value": "中"}, {"label": "高", "value": "高"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishNavigationRoute(row.id)
  if (command === 'disable') await disableNavigationRoute(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
