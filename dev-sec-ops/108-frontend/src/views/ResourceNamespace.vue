<template>
  <DataPage title="资源命名空间" description="业务线、环境和资源归属空间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getResourceNamespacePage, addResourceNamespace, updateResourceNamespace, deleteResourceNamespace, activateResourceNamespace, freezeResourceNamespace } from '../api'
const api = { page: getResourceNamespacePage, add: addResourceNamespace, update: updateResourceNamespace, delete: deleteResourceNamespace }
const columns = [{"prop": "namespaceName", "label": "空间名称"}, {"prop": "namespaceCode", "label": "空间编码"}, {"prop": "accountName", "label": "云账号"}, {"prop": "businessLine", "label": "业务线"}, {"prop": "environmentName", "label": "环境"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "namespaceName", "label": "空间名称"}, {"prop": "namespaceCode", "label": "空间编码"}, {"prop": "accountName", "label": "云账号"}, {"prop": "businessLine", "label": "业务线"}, {"prop": "environmentName", "label": "环境", "type": "select", "options": [{"label": "生产", "value": "生产"}, {"label": "预发", "value": "预发"}, {"label": "测试", "value": "测试"}, {"label": "开发", "value": "开发"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "FROZEN", "value": "FROZEN"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "primary"}, {"command": "freeze", "label": "冻结", "type": "primary"}]
const defaults = {"environmentName": "生产", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateResourceNamespace(row.id)
  if (command === 'freeze') await freezeResourceNamespace(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
