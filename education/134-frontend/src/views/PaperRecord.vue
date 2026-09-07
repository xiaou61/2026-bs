<template>
  <DataPage title="论文成果" description="论文编号、项目编号、论文题目、期刊名称、发表时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPaperRecordPage, addPaperRecord, updatePaperRecord, deletePaperRecord, activatePaperRecord, finishPaperRecord } from '../api'
const api = { page: getPaperRecordPage, add: addPaperRecord, update: updatePaperRecord, delete: deletePaperRecord }
const columns = [{"prop": "paperNo", "label": "论文编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "paperTitle", "label": "论文题目"}, {"prop": "journalName", "label": "期刊名称"}, {"prop": "publishTime", "label": "发表时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "paperNo", "label": "论文编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "paperTitle", "label": "论文题目"}, {"prop": "journalName", "label": "期刊名称"}, {"prop": "publishTime", "label": "发表时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePaperRecord(row.id)
  if (command === 'finish') await finishPaperRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
