<template>
  <DataPage title="签到记录" description="巡查编号、参会人、签到次数、签到方式、会务人员和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPublicNoticePage, addPublicNotice, updatePublicNotice, deletePublicNotice, processPublicNotice, finishPublicNotice } from '../api'
const api = { page: getPublicNoticePage, add: addPublicNotice, update: updatePublicNotice, delete: deletePublicNotice }
const columns = [{"prop": "warningNo", "label": "巡查编号"}, {"prop": "consumableName", "label": "参会人"}, {"prop": "currentQty", "label": "签到次数"}, {"prop": "warningLevel", "label": "签到方式"}, {"prop": "handlerName", "label": "会务人员"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "巡查编号"}, {"prop": "consumableName", "label": "参会人"}, {"prop": "currentQty", "label": "签到次数", "type": "number"}, {"prop": "warningLevel", "label": "签到方式"}, {"prop": "handlerName", "label": "会务人员"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WARNING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPublicNotice(row.id)
  if (command === 'finish') await finishPublicNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>







