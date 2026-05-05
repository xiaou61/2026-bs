<template>
  <DataPage title="整改工单" description="工单编号、隐患编号、责任班组、截止时间、整改要求和验收状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRectificationOrderPage, addRectificationOrder, updateRectificationOrder, deleteRectificationOrder, submitRectificationOrder, finishRectificationOrder } from '../api'
const api = { page: getRectificationOrderPage, add: addRectificationOrder, update: updateRectificationOrder, delete: deleteRectificationOrder }
const columns = [{"prop": "orderNo", "label": "工单编号"}, {"prop": "hazardNo", "label": "隐患编号"}, {"prop": "responsibleTeam", "label": "责任班组"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "rectifyRequirement", "label": "整改要求"}, {"prop": "verifyStatus", "label": "验收状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "工单编号"}, {"prop": "hazardNo", "label": "隐患编号"}, {"prop": "responsibleTeam", "label": "责任班组"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "rectifyRequirement", "label": "整改要求"}, {"prop": "verifyStatus", "label": "验收状态"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "PROCESSING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitRectificationOrder(row.id)
  if (command === 'finish') await finishRectificationOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
