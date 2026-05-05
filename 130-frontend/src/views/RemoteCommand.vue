<template>
  <DataPage title="远程指令" description="指令编号、设备编号、指令类型、参数、下发人和执行状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRemoteCommandPage, addRemoteCommand, updateRemoteCommand, deleteRemoteCommand, processRemoteCommand, finishRemoteCommand } from '../api'
const api = { page: getRemoteCommandPage, add: addRemoteCommand, update: updateRemoteCommand, delete: deleteRemoteCommand }
const columns = [{"prop": "commandNo", "label": "指令编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "commandType", "label": "指令类型"}, {"prop": "paramText", "label": "指令参数"}, {"prop": "operatorName", "label": "下发人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "commandNo", "label": "指令编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "commandType", "label": "指令类型"}, {"prop": "paramText", "label": "指令参数"}, {"prop": "operatorName", "label": "下发人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processRemoteCommand(row.id)
  if (command === 'finish') await finishRemoteCommand(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
