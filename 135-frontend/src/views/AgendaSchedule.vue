<template>
  <DataPage title="会议日程" description="日程编号、日程主题、开始时间、日程类型、主持人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAgendaSchedulePage, addAgendaSchedule, updateAgendaSchedule, deleteAgendaSchedule, activateAgendaSchedule, finishAgendaSchedule } from '../api'
const api = { page: getAgendaSchedulePage, add: addAgendaSchedule, update: updateAgendaSchedule, delete: deleteAgendaSchedule }
const columns = [{"prop": "ruleNo", "label": "日程编号"}, {"prop": "categoryName", "label": "日程主题"}, {"prop": "minStock", "label": "开始顺序"}, {"prop": "warningLevel", "label": "日程类型"}, {"prop": "noticeTarget", "label": "主持人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleNo", "label": "日程编号"}, {"prop": "categoryName", "label": "日程主题"}, {"prop": "minStock", "label": "开始顺序", "type": "number"}, {"prop": "warningLevel", "label": "日程类型"}, {"prop": "noticeTarget", "label": "主持人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateAgendaSchedule(row.id)
  if (command === 'finish') await finishAgendaSchedule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>


