<template>
  <DataPage title="冷链仓点" description="冷链仓库、分拨中心和门店节点备案管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWarehouseNodePage, addWarehouseNode, updateWarehouseNode, deleteWarehouseNode, enableWarehouseNode, disableWarehouseNode } from '../api'
const api = { page: getWarehouseNodePage, add: addWarehouseNode, update: updateWarehouseNode, delete: deleteWarehouseNode }
const columns = [{"prop": "nodeName", "label": "仓点名称"}, {"prop": "nodeCode", "label": "仓点编码"}, {"prop": "nodeType", "label": "节点类型"}, {"prop": "regionName", "label": "区域"}, {"prop": "managerName", "label": "负责人"}, {"prop": "capacityTon", "label": "容量吨"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "nodeName", "label": "仓点名称"}, {"prop": "nodeCode", "label": "仓点编码"}, {"prop": "nodeType", "label": "节点类型", "type": "select", "options": [{"label": "WAREHOUSE", "value": "WAREHOUSE"}, {"label": "HUB", "value": "HUB"}, {"label": "STORE", "value": "STORE"}, {"label": "FACTORY", "value": "FACTORY"}]}, {"prop": "regionName", "label": "区域"}, {"prop": "managerName", "label": "负责人"}, {"prop": "capacityTon", "label": "容量吨", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableWarehouseNode(row.id)
  if (command === 'disable') await disableWarehouseNode(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
