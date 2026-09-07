<template>
  <DataPage title="缺陷图片" description="图片编号、缺陷编号、文件名、AI标签、置信度和复核结果管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDefectImagePage, addDefectImage, updateDefectImage, deleteDefectImage, confirmDefectImage, rejectDefectImage } from '../api'
const api = { page: getDefectImagePage, add: addDefectImage, update: updateDefectImage, delete: deleteDefectImage }
const columns = [{"prop": "imageNo", "label": "图片编号"}, {"prop": "defectNo", "label": "缺陷编号"}, {"prop": "fileName", "label": "文件名"}, {"prop": "aiLabel", "label": "AI标签"}, {"prop": "confidenceScore", "label": "置信度"}, {"prop": "reviewResult", "label": "复核结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "imageNo", "label": "图片编号"}, {"prop": "defectNo", "label": "缺陷编号"}, {"prop": "fileName", "label": "文件名"}, {"prop": "aiLabel", "label": "AI标签"}, {"prop": "confidenceScore", "label": "置信度", "type": "number"}, {"prop": "reviewResult", "label": "复核结果"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmDefectImage(row.id)
  if (command === 'reject') await rejectDefectImage(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
