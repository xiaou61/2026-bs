<template>
  <DataPage title="数据集目录" description="数据表、业务域和安全等级目录管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDataSetCatalogPage, addDataSetCatalog, updateDataSetCatalog, deleteDataSetCatalog, publishDataSetCatalog, archiveDataSetCatalog } from '../api'
const api = { page: getDataSetCatalogPage, add: addDataSetCatalog, update: updateDataSetCatalog, delete: deleteDataSetCatalog }
const columns = [{"prop": "datasetName", "label": "数据集名称"}, {"prop": "datasetCode", "label": "数据集编码"}, {"prop": "sourceName", "label": "数据源"}, {"prop": "tableName", "label": "表名"}, {"prop": "businessDomain", "label": "业务域"}, {"prop": "securityLevel", "label": "安全等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "datasetName", "label": "数据集名称"}, {"prop": "datasetCode", "label": "数据集编码"}, {"prop": "sourceName", "label": "数据源"}, {"prop": "tableName", "label": "表名"}, {"prop": "businessDomain", "label": "业务域"}, {"prop": "securityLevel", "label": "安全等级", "type": "select", "options": [{"label": "L1", "value": "L1"}, {"label": "L2", "value": "L2"}, {"label": "L3", "value": "L3"}, {"label": "L4", "value": "L4"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}, {"label": "DRAFT", "value": "DRAFT"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"securityLevel": "L2", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishDataSetCatalog(row.id)
  if (command === 'archive') await archiveDataSetCatalog(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
