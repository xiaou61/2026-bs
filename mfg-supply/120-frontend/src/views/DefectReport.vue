<template>
  <DataPage title="缺陷报告" description="设备缺陷、严重等级、缺陷描述、上报人、处理建议和缺陷状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDefectReportPage, addDefectReport, updateDefectReport, deleteDefectReport, confirmDefectReport, closeDefectReport } from '../api'
const api = { page: getDefectReportPage, add: addDefectReport, update: updateDefectReport, delete: deleteDefectReport }
const columns = [{"prop": "defectNo", "label": "缺陷编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "defectDesc", "label": "缺陷描述"}, {"prop": "reporterName", "label": "上报人"}, {"prop": "suggestion", "label": "处理建议"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "defectNo", "label": "缺陷编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "severityLevel", "label": "严重等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "defectDesc", "label": "缺陷描述", "type": "textarea"}, {"prop": "reporterName", "label": "上报人"}, {"prop": "suggestion", "label": "处理建议"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmDefectReport(row.id)
  if (command === 'close') await closeDefectReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
