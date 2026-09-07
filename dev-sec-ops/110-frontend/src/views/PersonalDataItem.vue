<template>
  <DataPage title="个人数据项" description="个人信息分类、敏感等级和保存期限管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPersonalDataItemPage, addPersonalDataItem, updatePersonalDataItem, deletePersonalDataItem } from '../api'
const api = { page: getPersonalDataItemPage, add: addPersonalDataItem, update: updatePersonalDataItem, delete: deletePersonalDataItem }
const columns = [{"prop": "itemName", "label": "数据项名称"}, {"prop": "itemCode", "label": "数据项编码"}, {"prop": "categoryName", "label": "分类"}, {"prop": "sensitiveLevel", "label": "敏感等级"}, {"prop": "retentionDays", "label": "保存天数"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "itemName", "label": "数据项名称"}, {"prop": "itemCode", "label": "数据项编码"}, {"prop": "categoryName", "label": "分类"}, {"prop": "sensitiveLevel", "label": "敏感等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "retentionDays", "label": "保存天数", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = []
const defaults = {"sensitiveLevel": "HIGH", "status": "ACTIVE"}
const handleAction = () => {}
</script>
