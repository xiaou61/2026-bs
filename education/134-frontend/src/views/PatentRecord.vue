<template>
  <DataPage title="专利成果" description="专利编号、项目编号、专利名称、申请人、授权时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPatentRecordPage, addPatentRecord, updatePatentRecord, deletePatentRecord, activatePatentRecord, finishPatentRecord } from '../api'
const api = { page: getPatentRecordPage, add: addPatentRecord, update: updatePatentRecord, delete: deletePatentRecord }
const columns = [{"prop": "patentNo", "label": "专利编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "patentName", "label": "专利名称"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "grantTime", "label": "授权时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "patentNo", "label": "专利编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "patentName", "label": "专利名称"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "grantTime", "label": "授权时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePatentRecord(row.id)
  if (command === 'finish') await finishPatentRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
