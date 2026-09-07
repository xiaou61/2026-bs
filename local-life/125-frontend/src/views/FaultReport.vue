<template>
  <DataPage title="故障报修" description="报修编号、对象、故障类型、严重等级、上报人和上报时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFaultReportPage, addFaultReport, updateFaultReport, deleteFaultReport, confirmFaultReport, closeFaultReport } from '../api'
const api = { page: getFaultReportPage, add: addFaultReport, update: updateFaultReport, delete: deleteFaultReport }
const columns = [{"prop": "faultNo", "label": "报修编号"}, {"prop": "targetName", "label": "故障对象"}, {"prop": "faultType", "label": "故障类型"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "reporterName", "label": "上报人"}, {"prop": "reportTime", "label": "上报时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "faultNo", "label": "报修编号"}, {"prop": "targetName", "label": "故障对象"}, {"prop": "faultType", "label": "故障类型"}, {"prop": "severityLevel", "label": "严重等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "reporterName", "label": "上报人"}, {"prop": "reportTime", "label": "上报时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmFaultReport(row.id)
  if (command === 'close') await closeFaultReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
