<template>
  <DataPage title="维修工单" description="维修工单、缺陷、设备、工程师、截止时间、处理结果和工单状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWorkOrderPage, addWorkOrder, updateWorkOrder, deleteWorkOrder, acceptWorkOrder, finishWorkOrder } from '../api'
const api = { page: getWorkOrderPage, add: addWorkOrder, update: updateWorkOrder, delete: deleteWorkOrder }
const columns = [{"prop": "workOrderNo", "label": "工单编号"}, {"prop": "defectNo", "label": "缺陷编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "engineerName", "label": "工程师"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "handleResult", "label": "处理结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "workOrderNo", "label": "工单编号"}, {"prop": "defectNo", "label": "缺陷编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "engineerName", "label": "工程师"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "handleResult", "label": "处理结果", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_HANDLE", "value": "WAIT_HANDLE"}, {"label": "ACCEPTED", "value": "ACCEPTED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "accept", "label": "接单", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WAIT_HANDLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'accept') await acceptWorkOrder(row.id)
  if (command === 'finish') await finishWorkOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
