<template>
  <DataPage title="流通节点" description="仓储、加工、批发、零售等流通节点备案管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTraceNodePage, addTraceNode, updateTraceNode, deleteTraceNode, enableTraceNode, disableTraceNode } from '../api'
const api = { page: getTraceNodePage, add: addTraceNode, update: updateTraceNode, delete: deleteTraceNode }
const columns = [{"prop": "nodeName", "label": "节点名称"}, {"prop": "nodeCode", "label": "节点编码"}, {"prop": "nodeType", "label": "节点类型"}, {"prop": "regionName", "label": "区域"}, {"prop": "contactName", "label": "联系人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "nodeName", "label": "节点名称"}, {"prop": "nodeCode", "label": "节点编码"}, {"prop": "nodeType", "label": "节点类型", "type": "select", "options": [{"label": "WAREHOUSE", "value": "WAREHOUSE"}, {"label": "PROCESS", "value": "PROCESS"}, {"label": "WHOLESALE", "value": "WHOLESALE"}, {"label": "RETAIL", "value": "RETAIL"}]}, {"prop": "regionName", "label": "区域"}, {"prop": "contactName", "label": "联系人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableTraceNode(row.id)
  if (command === 'disable') await disableTraceNode(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
