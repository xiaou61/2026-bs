<template>
  <DataPage title="异常预警" description="预警编号、家庭、设备、预警等级、原因和处理人管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAbnormalAlertPage, addAbnormalAlert, updateAbnormalAlert, deleteAbnormalAlert, assignAbnormalAlert, closeAbnormalAlert } from '../api'
const api = { page: getAbnormalAlertPage, add: addAbnormalAlert, update: updateAbnormalAlert, delete: deleteAbnormalAlert }
const columns = [{"prop": "alertNo", "label": "预警编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "alertLevel", "label": "预警等级"}, {"prop": "alertReason", "label": "预警原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "alertNo", "label": "预警编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "alertLevel", "label": "预警等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "alertReason", "label": "预警原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignAbnormalAlert(row.id)
  if (command === 'close') await closeAbnormalAlert(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
