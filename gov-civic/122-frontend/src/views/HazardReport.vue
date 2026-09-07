<template>
  <DataPage title="隐患上报" description="隐患编号、项目、类型、等级、上报人和上报时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getHazardReportPage, addHazardReport, updateHazardReport, deleteHazardReport, confirmHazardReport, closeHazardReport } from '../api'
const api = { page: getHazardReportPage, add: addHazardReport, update: updateHazardReport, delete: deleteHazardReport }
const columns = [{"prop": "hazardNo", "label": "隐患编号"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "hazardType", "label": "隐患类型"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "reporterName", "label": "上报人"}, {"prop": "reportTime", "label": "上报时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "hazardNo", "label": "隐患编号"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "hazardType", "label": "隐患类型"}, {"prop": "severityLevel", "label": "严重等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "reporterName", "label": "上报人"}, {"prop": "reportTime", "label": "上报时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmHazardReport(row.id)
  if (command === 'close') await closeHazardReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
