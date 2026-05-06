<template>
  <DataPage title="指导记录" description="预警编号、课题编号、指导主题、指导时间、诊室编号和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getQueueCallPage, addQueueCall, updateQueueCall, deleteQueueCall, processQueueCall, finishQueueCall } from '../api'
const api = { page: getQueueCallPage, add: addQueueCall, update: updateQueueCall, delete: deleteQueueCall }
const columns = [{"prop": "statNo", "label": "预警编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "statMonth", "label": "指导主题"}, {"prop": "claimCount", "label": "指导时间"}, {"prop": "achievementCount", "label": "诊室编号"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "预警编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "statMonth", "label": "指导主题"}, {"prop": "claimCount", "label": "指导时间", "type": "number"}, {"prop": "achievementCount", "label": "诊室编号", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processQueueCall(row.id)
  if (command === 'finish') await finishQueueCall(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>









