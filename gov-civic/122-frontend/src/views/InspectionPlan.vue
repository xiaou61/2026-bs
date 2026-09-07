<template>
  <DataPage title="巡检计划" description="计划编号、项目、日期、安全员、区域和重点风险管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionPlanPage, addInspectionPlan, updateInspectionPlan, deleteInspectionPlan, publishInspectionPlan, closeInspectionPlan } from '../api'
const api = { page: getInspectionPlanPage, add: addInspectionPlan, update: updateInspectionPlan, delete: deleteInspectionPlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "planDate", "label": "计划日期"}, {"prop": "inspectorName", "label": "安全员"}, {"prop": "areaName", "label": "巡检区域"}, {"prop": "riskFocus", "label": "重点风险"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "planDate", "label": "计划日期"}, {"prop": "inspectorName", "label": "安全员"}, {"prop": "areaName", "label": "巡检区域"}, {"prop": "riskFocus", "label": "重点风险"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishInspectionPlan(row.id)
  if (command === 'close') await closeInspectionPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
