<template>
  <DataPage title="随访计划" description="计划编号、上报编号、随访日期、随访方式和负责人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFollowupPlanPage, addFollowupPlan, updateFollowupPlan, deleteFollowupPlan, processFollowupPlan, finishFollowupPlan } from '../api'
const api = { page: getFollowupPlanPage, add: addFollowupPlan, update: updateFollowupPlan, delete: deleteFollowupPlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "followDate", "label": "随访日期"}, {"prop": "followMethod", "label": "随访方式"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "followDate", "label": "随访日期"}, {"prop": "followMethod", "label": "随访方式"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processFollowupPlan(row.id)
  if (command === 'finish') await finishFollowupPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
