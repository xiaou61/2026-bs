<template>
  <DataPage title="统计报表" description="统计编号、科室、统计月份、上报数量和严重例数维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStatisticsReportPage, addStatisticsReport, updateStatisticsReport, deleteStatisticsReport, activateStatisticsReport, finishStatisticsReport } from '../api'
const api = { page: getStatisticsReportPage, add: addStatisticsReport, update: updateStatisticsReport, delete: deleteStatisticsReport }
const columns = [{"prop": "statNo", "label": "统计编号"}, {"prop": "deptName", "label": "科室名称"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "reportCount", "label": "上报数量"}, {"prop": "seriousCount", "label": "严重例数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "统计编号"}, {"prop": "deptName", "label": "科室名称"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "reportCount", "label": "上报数量", "type": "number"}, {"prop": "seriousCount", "label": "严重例数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateStatisticsReport(row.id)
  if (command === 'finish') await finishStatisticsReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
