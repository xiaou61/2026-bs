<template>
  <DataPage title="减排措施" description="减排措施、投资金额、预期减排量和执行状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReductionMeasurePage, addReductionMeasure, updateReductionMeasure, deleteReductionMeasure, processReductionMeasure, finishReductionMeasure } from '../api'
const api = { page: getReductionMeasurePage, add: addReductionMeasure, update: updateReductionMeasure, delete: deleteReductionMeasure }
const columns = [{"prop": "measureNo", "label": "措施编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "measureName", "label": "措施名称"}, {"prop": "investmentAmount", "label": "投资金额"}, {"prop": "expectedReduction", "label": "预期减排量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "measureNo", "label": "措施编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "measureName", "label": "措施名称"}, {"prop": "investmentAmount", "label": "投资金额", "type": "number"}, {"prop": "expectedReduction", "label": "预期减排量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processReductionMeasure(row.id)
  if (command === 'finish') await finishReductionMeasure(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
