<template>
  <DataPage title="安全员档案" description="安全员编号、姓名、证书、所属组、电话和专长管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSafetyInspectorPage, addSafetyInspector, updateSafetyInspector, deleteSafetyInspector, enableSafetyInspector, disableSafetyInspector } from '../api'
const api = { page: getSafetyInspectorPage, add: addSafetyInspector, update: updateSafetyInspector, delete: deleteSafetyInspector }
const columns = [{"prop": "inspectorNo", "label": "安全员编号"}, {"prop": "inspectorName", "label": "安全员姓名"}, {"prop": "certificateNo", "label": "证书编号"}, {"prop": "teamName", "label": "所属组"}, {"prop": "phone", "label": "电话"}, {"prop": "specialty", "label": "专长"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "inspectorNo", "label": "安全员编号"}, {"prop": "inspectorName", "label": "安全员姓名"}, {"prop": "certificateNo", "label": "证书编号"}, {"prop": "teamName", "label": "所属组"}, {"prop": "phone", "label": "电话"}, {"prop": "specialty", "label": "专长"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableSafetyInspector(row.id)
  if (command === 'disable') await disableSafetyInspector(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
