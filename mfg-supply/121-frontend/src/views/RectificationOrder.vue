<template>
  <DataPage title="整改工单" description="工单编号、缺陷编号、责任班组、截止时间、整改措施和验收结果管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRectificationOrderPage, addRectificationOrder, updateRectificationOrder, deleteRectificationOrder, submitRectificationOrder, closeRectificationOrder } from '../api'
const api = { page: getRectificationOrderPage, add: addRectificationOrder, update: updateRectificationOrder, delete: deleteRectificationOrder }
const columns = [{"prop": "orderNo", "label": "工单编号"}, {"prop": "defectNo", "label": "缺陷编号"}, {"prop": "responsibleTeam", "label": "责任班组"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "rectifyMeasure", "label": "整改措施"}, {"prop": "verifyResult", "label": "验收结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "工单编号"}, {"prop": "defectNo", "label": "缺陷编号"}, {"prop": "responsibleTeam", "label": "责任班组"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "rectifyMeasure", "label": "整改措施"}, {"prop": "verifyResult", "label": "验收结果"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "PROCESSING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitRectificationOrder(row.id)
  if (command === 'close') await closeRectificationOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
