<template>
  <DataPage title="授权范围" description="授权记录关联的数据项、范围类型和必要性管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAuthorizationScopePage, addAuthorizationScope, updateAuthorizationScope, deleteAuthorizationScope } from '../api'
const api = { page: getAuthorizationScopePage, add: addAuthorizationScope, update: updateAuthorizationScope, delete: deleteAuthorizationScope }
const columns = [{"prop": "authNo", "label": "授权编号"}, {"prop": "itemName", "label": "数据项"}, {"prop": "scopeType", "label": "范围类型"}, {"prop": "requiredFlag", "label": "是否必要"}, {"prop": "descriptionText", "label": "说明"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "authNo", "label": "授权编号"}, {"prop": "itemName", "label": "数据项"}, {"prop": "scopeType", "label": "范围类型", "type": "select", "options": [{"label": "READ", "value": "READ"}, {"label": "PROCESS", "value": "PROCESS"}, {"label": "SHARE", "value": "SHARE"}, {"label": "EXPORT", "value": "EXPORT"}]}, {"prop": "requiredFlag", "label": "是否必要", "type": "select", "options": [{"label": "YES", "value": "YES"}, {"label": "NO", "value": "NO"}]}, {"prop": "descriptionText", "label": "说明", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = []
const defaults = {"scopeType": "READ", "status": "ACTIVE"}
const handleAction = () => {}
</script>
