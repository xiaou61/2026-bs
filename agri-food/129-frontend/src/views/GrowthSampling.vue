<template>
  <DataPage title="生长采样" description="采样编号、批次、采样日期、均重和记录人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getGrowthSamplingPage, addGrowthSampling, updateGrowthSampling, deleteGrowthSampling, submitGrowthSampling, approveGrowthSampling } from '../api'
const api = { page: getGrowthSamplingPage, add: addGrowthSampling, update: updateGrowthSampling, delete: deleteGrowthSampling }
const columns = [{"prop": "sampleNo", "label": "采样编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "sampleDate", "label": "采样日期"}, {"prop": "avgWeight", "label": "平均重量"}, {"prop": "recorderName", "label": "记录人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sampleNo", "label": "采样编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "sampleDate", "label": "采样日期"}, {"prop": "avgWeight", "label": "平均重量", "type": "number"}, {"prop": "recorderName", "label": "记录人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitGrowthSampling(row.id)
  if (command === 'approve') await approveGrowthSampling(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
