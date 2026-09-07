<template>
  <DataPage title="字段血缘" description="字段来源、目标路径和安全等级血缘管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFieldLineagePage, addFieldLineage, updateFieldLineage, deleteFieldLineage } from '../api'
const api = { page: getFieldLineagePage, add: addFieldLineage, update: updateFieldLineage, delete: deleteFieldLineage }
const columns = [{"prop": "fieldName", "label": "字段名"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "sourcePath", "label": "来源路径"}, {"prop": "targetPath", "label": "目标路径"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "securityLevel", "label": "安全等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "fieldName", "label": "字段名"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "sourcePath", "label": "来源路径"}, {"prop": "targetPath", "label": "目标路径"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "securityLevel", "label": "安全等级", "type": "select", "options": [{"label": "L1", "value": "L1"}, {"label": "L2", "value": "L2"}, {"label": "L3", "value": "L3"}, {"label": "L4", "value": "L4"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = []
const defaults = {"securityLevel": "L3", "status": "ACTIVE"}
const handleAction = () => {}
</script>
