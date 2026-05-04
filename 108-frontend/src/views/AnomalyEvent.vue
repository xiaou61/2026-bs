<template>
  <DataPage title="成本异常" description="成本突增、异常资源和处置状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAnomalyEventPage, addAnomalyEvent, updateAnomalyEvent, deleteAnomalyEvent, confirmAnomalyEvent, resolveAnomalyEvent, closeAnomalyEvent } from '../api'
const api = { page: getAnomalyEventPage, add: addAnomalyEvent, update: updateAnomalyEvent, delete: deleteAnomalyEvent }
const columns = [{"prop": "eventNo", "label": "异常编号"}, {"prop": "accountName", "label": "云账号"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "anomalyType", "label": "异常类型"}, {"prop": "costAmount", "label": "异常金额"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "eventNo", "label": "异常编号"}, {"prop": "accountName", "label": "云账号"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "anomalyType", "label": "异常类型", "type": "select", "options": [{"label": "SPIKE", "value": "SPIKE"}, {"label": "NEW_RESOURCE", "value": "NEW_RESOURCE"}, {"label": "BUDGET_OVER", "value": "BUDGET_OVER"}, {"label": "IDLE_COST", "value": "IDLE_COST"}]}, {"prop": "costAmount", "label": "异常金额", "type": "number"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "RESOLVED", "value": "RESOLVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "resolve", "label": "解决", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"anomalyType": "SPIKE", "status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmAnomalyEvent(row.id)
  if (command === 'resolve') await resolveAnomalyEvent(row.id)
  if (command === 'close') await closeAnomalyEvent(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
