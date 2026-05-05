<template>
  <DataPage title="维修工单" description="工单编号、报修编号、维修员、处理方案、截止时间和维修结果管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRepairWorkOrderPage, addRepairWorkOrder, updateRepairWorkOrder, deleteRepairWorkOrder, assignRepairWorkOrder, finishRepairWorkOrder } from '../api'
const api = { page: getRepairWorkOrderPage, add: addRepairWorkOrder, update: updateRepairWorkOrder, delete: deleteRepairWorkOrder }
const columns = [{"prop": "workOrderNo", "label": "工单编号"}, {"prop": "faultNo", "label": "报修编号"}, {"prop": "maintainerName", "label": "维修员"}, {"prop": "solutionText", "label": "处理方案"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "repairResult", "label": "维修结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "workOrderNo", "label": "工单编号"}, {"prop": "faultNo", "label": "报修编号"}, {"prop": "maintainerName", "label": "维修员"}, {"prop": "solutionText", "label": "处理方案"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "repairResult", "label": "维修结果"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "PROCESSING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignRepairWorkOrder(row.id)
  if (command === 'finish') await finishRepairWorkOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
