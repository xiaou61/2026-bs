<template>
  <DataPage title="数据源配置" description="数据库、文件和接口数据源接入管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDataSourceConfigPage, addDataSourceConfig, updateDataSourceConfig, deleteDataSourceConfig, enableDataSourceConfig, disableDataSourceConfig } from '../api'
const api = { page: getDataSourceConfigPage, add: addDataSourceConfig, update: updateDataSourceConfig, delete: deleteDataSourceConfig }
const columns = [{"prop": "sourceName", "label": "数据源名称"}, {"prop": "sourceCode", "label": "数据源编码"}, {"prop": "sourceType", "label": "类型"}, {"prop": "hostAddress", "label": "地址"}, {"prop": "databaseName", "label": "库名"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sourceName", "label": "数据源名称"}, {"prop": "sourceCode", "label": "数据源编码"}, {"prop": "sourceType", "label": "类型", "type": "select", "options": [{"label": "MYSQL", "value": "MYSQL"}, {"label": "ORACLE", "value": "ORACLE"}, {"label": "CSV", "value": "CSV"}, {"label": "API", "value": "API"}]}, {"prop": "hostAddress", "label": "地址"}, {"prop": "databaseName", "label": "库名"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "PENDING", "value": "PENDING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"sourceType": "MYSQL", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableDataSourceConfig(row.id)
  if (command === 'disable') await disableDataSourceConfig(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
