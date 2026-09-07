<template>
  <DataPage title="订单对账" description="账单对账任务、订单数量、差异金额、核对结果和异常闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReconciliationTaskPage, addReconciliationTask, updateReconciliationTask, deleteReconciliationTask, executeReconciliationTask, passReconciliationTask, exceptionReconciliationTask } from '../api'
const api = { page: getReconciliationTaskPage, add: addReconciliationTask, update: updateReconciliationTask, delete: deleteReconciliationTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "billNo", "label": "账单编号"}, {"prop": "orderCount", "label": "订单数"}, {"prop": "differenceAmount", "label": "差异金额"}, {"prop": "checkResult", "label": "核对结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "billNo", "label": "账单编号"}, {"prop": "orderCount", "label": "订单数", "type": "number"}, {"prop": "differenceAmount", "label": "差异金额", "type": "number"}, {"prop": "checkResult", "label": "核对结果", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CHECK", "value": "WAIT_CHECK"}, {"label": "CHECKING", "value": "CHECKING"}, {"label": "MATCHED", "value": "MATCHED"}, {"label": "DIFFERENCE", "value": "DIFFERENCE"}]}]
const rowActions = [{"command": "execute", "label": "执行", "type": "primary"}, {"command": "pass", "label": "匹配", "type": "success"}, {"command": "exception", "label": "差异", "type": "danger"}]
const defaults = {"orderCount": 0, "status": "WAIT_CHECK"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'execute') await executeReconciliationTask(row.id)
  if (command === 'pass') await passReconciliationTask(row.id)
  if (command === 'exception') await exceptionReconciliationTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
