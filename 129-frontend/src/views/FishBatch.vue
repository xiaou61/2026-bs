<template>
  <DataPage title="鱼苗批次" description="批次编号、池塘、鱼种、投苗数量和投苗日期维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFishBatchPage, addFishBatch, updateFishBatch, deleteFishBatch, activateFishBatch, finishFishBatch } from '../api'
const api = { page: getFishBatchPage, add: addFishBatch, update: updateFishBatch, delete: deleteFishBatch }
const columns = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "speciesName", "label": "鱼种"}, {"prop": "seedCount", "label": "投苗数量"}, {"prop": "stockDate", "label": "投苗日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "speciesName", "label": "鱼种"}, {"prop": "seedCount", "label": "投苗数量", "type": "number"}, {"prop": "stockDate", "label": "投苗日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFishBatch(row.id)
  if (command === 'finish') await finishFishBatch(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
