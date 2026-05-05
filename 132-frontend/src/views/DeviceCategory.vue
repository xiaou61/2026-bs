<template>
  <DataPage title="器械分类" description="分类编号、分类名称、风险等级、消毒方式和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceCategoryPage, addDeviceCategory, updateDeviceCategory, deleteDeviceCategory, activateDeviceCategory, finishDeviceCategory } from '../api'
const api = { page: getDeviceCategoryPage, add: addDeviceCategory, update: updateDeviceCategory, delete: deleteDeviceCategory }
const columns = [{"prop": "categoryNo", "label": "分类编号"}, {"prop": "categoryName", "label": "分类名称"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "sterilizeMethod", "label": "消毒方式"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "categoryNo", "label": "分类编号"}, {"prop": "categoryName", "label": "分类名称"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "sterilizeMethod", "label": "消毒方式"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDeviceCategory(row.id)
  if (command === 'finish') await finishDeviceCategory(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
