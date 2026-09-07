<template>
  <DataPage title="作物批次" description="作物批次、温室、作物名称、定植日期和负责人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCropBatchPage, addCropBatch, updateCropBatch, deleteCropBatch, activateCropBatch, finishCropBatch } from '../api'
const api = { page: getCropBatchPage, add: addCropBatch, update: updateCropBatch, delete: deleteCropBatch }
const columns = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "cropName", "label": "作物名称"}, {"prop": "plantDate", "label": "定植日期"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "cropName", "label": "作物名称"}, {"prop": "plantDate", "label": "定植日期"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCropBatch(row.id)
  if (command === 'finish') await finishCropBatch(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
