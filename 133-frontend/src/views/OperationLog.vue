<template>
  <DataPage title="操作日志" description="关键模块操作人、动作、对象、详情和结果记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOperationLogPage, addOperationLog, updateOperationLog, deleteOperationLog } from '../api'
const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog }
const columns = [{"prop": "operatorName", "label": "操作人"}, {"prop": "moduleName", "label": "模块名称"}, {"prop": "actionType", "label": "动作类型"}, {"prop": "targetName", "label": "操作对象"}, {"prop": "detailInfo", "label": "操作详情"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "operatorName", "label": "操作人"}, {"prop": "moduleName", "label": "模块名称"}, {"prop": "actionType", "label": "动作类型"}, {"prop": "targetName", "label": "操作对象"}, {"prop": "detailInfo", "label": "操作详情", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = []
const defaults = {"status": "SUCCESS"}
const handleAction = async ({ command, row, refresh }) => {

  ElMessage.success('操作成功')
  refresh()
}
</script>
