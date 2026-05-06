<template>
  <DataPage title="会场安排" description="会场编号、会场名称、对应议题、座位数量、签到数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCompetencyProfilePage, addCompetencyProfile, updateCompetencyProfile, deleteCompetencyProfile, processCompetencyProfile, finishCompetencyProfile } from '../api'
const api = { page: getCompetencyProfilePage, add: addCompetencyProfile, update: updateCompetencyProfile, delete: deleteCompetencyProfile }
const columns = [{"prop": "checkNo", "label": "会场编号"}, {"prop": "labName", "label": "会场名称"}, {"prop": "consumableName", "label": "对应议题"}, {"prop": "bookQty", "label": "座位数量"}, {"prop": "actualQty", "label": "签到数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "checkNo", "label": "会场编号"}, {"prop": "labName", "label": "会场名称"}, {"prop": "consumableName", "label": "对应议题"}, {"prop": "bookQty", "label": "座位数量", "type": "number"}, {"prop": "actualQty", "label": "签到数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "PROCESSING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processCompetencyProfile(row.id)
  if (command === 'finish') await finishCompetencyProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




