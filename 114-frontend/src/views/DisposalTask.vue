<template>
  <DataPage title="处置任务" description="异常告警派单、处理人、处理措施和闭环状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDisposalTaskPage, addDisposalTask, updateDisposalTask, deleteDisposalTask, startDisposalTask, finishDisposalTask, closeDisposalTask } from '../api'
const api = { page: getDisposalTaskPage, add: addDisposalTask, update: updateDisposalTask, delete: deleteDisposalTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "alertNo", "label": "告警编号"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "measureText", "label": "处置措施"}, {"prop": "finishTime", "label": "完成时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "alertNo", "label": "告警编号"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "measureText", "label": "处置措施", "type": "textarea"}, {"prop": "finishTime", "label": "完成时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_HANDLE", "value": "WAIT_HANDLE"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"status": "WAIT_HANDLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startDisposalTask(row.id)
  if (command === 'finish') await finishDisposalTask(row.id)
  if (command === 'close') await closeDisposalTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
