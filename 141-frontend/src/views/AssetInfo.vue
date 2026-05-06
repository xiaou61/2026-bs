<template>
  <DataPage title="会议信息" description="会议编号、会议名称、会议主题、主办单位、会场数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAssetInfoPage, addAssetInfo, updateAssetInfo, deleteAssetInfo, activateAssetInfo, finishAssetInfo } from '../api'
const api = { page: getAssetInfoPage, add: addAssetInfo, update: updateAssetInfo, delete: deleteAssetInfo }
const columns = [{"prop": "consumableNo", "label": "会议编号"}, {"prop": "consumableName", "label": "会议名称"}, {"prop": "specModel", "label": "会议主题"}, {"prop": "unitName", "label": "主办单位"}, {"prop": "safeStock", "label": "会场数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "consumableNo", "label": "会议编号"}, {"prop": "consumableName", "label": "会议名称"}, {"prop": "specModel", "label": "会议主题"}, {"prop": "unitName", "label": "主办单位"}, {"prop": "safeStock", "label": "会场数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateAssetInfo(row.id)
  if (command === 'finish') await finishAssetInfo(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>





