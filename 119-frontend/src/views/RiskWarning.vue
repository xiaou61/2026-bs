<template>
  <DataPage title="风险预警" description="备件风险预警、设备、预警等级、预警原因、处理人和预警状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskWarningPage, addRiskWarning, updateRiskWarning, deleteRiskWarning, assignRiskWarning, closeRiskWarning } from '../api'
const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "partName", "label": "备件名称"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "reasonText", "label": "预警原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "partName", "label": "备件名称"}, {"prop": "warningLevel", "label": "预警等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "reasonText", "label": "预警原因", "type": "textarea"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "assign", "label": "派单", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignRiskWarning(row.id)
  if (command === 'close') await closeRiskWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
