<template>
  <DataPage title="故障记录" description="设备故障、故障类型、影响等级、停机时长、处理人和故障状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFailureRecordPage, addFailureRecord, updateFailureRecord, deleteFailureRecord, handleFailureRecord, closeFailureRecord } from '../api'
const api = { page: getFailureRecordPage, add: addFailureRecord, update: updateFailureRecord, delete: deleteFailureRecord }
const columns = [{"prop": "failureNo", "label": "故障编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "failureType", "label": "故障类型"}, {"prop": "impactLevel", "label": "影响等级"}, {"prop": "downtimeHour", "label": "停机小时"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "failureNo", "label": "故障编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "failureType", "label": "故障类型"}, {"prop": "impactLevel", "label": "影响等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "downtimeHour", "label": "停机小时", "type": "number"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "HANDLING", "value": "HANDLING"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "handle", "label": "处理", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'handle') await handleFailureRecord(row.id)
  if (command === 'close') await closeFailureRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
