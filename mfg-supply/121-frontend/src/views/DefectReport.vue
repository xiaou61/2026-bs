<template>
  <DataPage title="缺陷报告" description="缺陷编号、区域、类型、等级、上报人和照片数量管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDefectReportPage, addDefectReport, updateDefectReport, deleteDefectReport, confirmDefectReport, closeDefectReport } from '../api'
const api = { page: getDefectReportPage, add: addDefectReport, update: updateDefectReport, delete: deleteDefectReport }
const columns = [{"prop": "defectNo", "label": "缺陷编号"}, {"prop": "zoneName", "label": "巡检区域"}, {"prop": "defectType", "label": "缺陷类型"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "reporterName", "label": "上报人"}, {"prop": "photoCount", "label": "照片数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "defectNo", "label": "缺陷编号"}, {"prop": "zoneName", "label": "巡检区域"}, {"prop": "defectType", "label": "缺陷类型"}, {"prop": "severityLevel", "label": "严重等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "reporterName", "label": "上报人"}, {"prop": "photoCount", "label": "照片数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmDefectReport(row.id)
  if (command === 'close') await closeDefectReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
