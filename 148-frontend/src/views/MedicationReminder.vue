<template>
  <DataPage title="开题答辩" description="答辩编号、课题编号、答辩主题、答辩小组、答辩时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMedicationReminderPage, addMedicationReminder, updateMedicationReminder, deleteMedicationReminder, activateMedicationReminder, finishMedicationReminder } from '../api'
const api = { page: getMedicationReminderPage, add: addMedicationReminder, update: updateMedicationReminder, delete: deleteMedicationReminder }
const columns = [{"prop": "paperNo", "label": "答辩编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "paperTitle", "label": "答辩主题"}, {"prop": "journalName", "label": "答辩小组"}, {"prop": "publishTime", "label": "答辩时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "paperNo", "label": "答辩编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "paperTitle", "label": "答辩主题"}, {"prop": "journalName", "label": "答辩小组"}, {"prop": "publishTime", "label": "答辩时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateMedicationReminder(row.id)
  if (command === 'finish') await finishMedicationReminder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








