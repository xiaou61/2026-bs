<template>
  <DataPage title="巡检记录" description="巡检记录、任务、点位、巡检结果、读数、巡检时间和复核状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionRecordPage, addInspectionRecord, updateInspectionRecord, deleteInspectionRecord, reviewInspectionRecord, rejectInspectionRecord } from '../api'
const api = { page: getInspectionRecordPage, add: addInspectionRecord, update: updateInspectionRecord, delete: deleteInspectionRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "resultType", "label": "巡检结果"}, {"prop": "meterValue", "label": "读数"}, {"prop": "inspectTime", "label": "巡检时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "resultType", "label": "巡检结果", "type": "select", "options": [{"label": "正常", "value": "正常"}, {"label": "异常", "value": "异常"}, {"label": "待确认", "value": "待确认"}]}, {"prop": "meterValue", "label": "读数", "type": "number"}, {"prop": "inspectTime", "label": "巡检时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_REVIEW", "value": "WAIT_REVIEW"}, {"label": "REVIEWED", "value": "REVIEWED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "review", "label": "复核", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "WAIT_REVIEW"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'review') await reviewInspectionRecord(row.id)
  if (command === 'reject') await rejectInspectionRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
