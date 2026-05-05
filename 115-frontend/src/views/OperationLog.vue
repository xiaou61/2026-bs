<template>
  <DataPage title="操作日志" description="关键模块操作人、动作、对象、详情、IP地址和处理状态审计" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOperationLogPage, addOperationLog, updateOperationLog, deleteOperationLog } from '../api'
const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog }
const columns = [{"prop": "operatorName", "label": "操作人"}, {"prop": "moduleName", "label": "模块"}, {"prop": "actionType", "label": "动作"}, {"prop": "targetName", "label": "对象"}, {"prop": "detail", "label": "详情"}, {"prop": "ipAddress", "label": "IP地址"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "operatorName", "label": "操作人"}, {"prop": "moduleName", "label": "模块"}, {"prop": "actionType", "label": "动作"}, {"prop": "targetName", "label": "对象"}, {"prop": "detail", "label": "详情", "type": "textarea"}, {"prop": "ipAddress", "label": "IP地址"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "SUCCESS", "value": "SUCCESS"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = []
const defaults = {"status": "SUCCESS"}
const handleAction = async ({ command, row, refresh }) => {
  return refresh()
  ElMessage.success('操作成功')
  refresh()
}
</script>
