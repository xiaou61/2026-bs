<template>
  <DataPage title="征稿通知" description="通知编号、通知标题、发布人、截稿日期、投稿指引和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCallForPaperPage, addCallForPaper, updateCallForPaper, deleteCallForPaper, activateCallForPaper, finishCallForPaper } from '../api'
const api = { page: getCallForPaperPage, add: addCallForPaper, update: updateCallForPaper, delete: deleteCallForPaper }
const columns = [{"prop": "supplierNo", "label": "通知编号"}, {"prop": "supplierName", "label": "供应商通知标题"}, {"prop": "contactName", "label": "发布人"}, {"prop": "phoneNumber", "label": "截稿日期"}, {"prop": "qualificationLevel", "label": "投稿指引"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "supplierNo", "label": "通知编号"}, {"prop": "supplierName", "label": "供应商通知标题"}, {"prop": "contactName", "label": "发布人"}, {"prop": "phoneNumber", "label": "截稿日期"}, {"prop": "qualificationLevel", "label": "投稿指引"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCallForPaper(row.id)
  if (command === 'finish') await finishCallForPaper(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>


