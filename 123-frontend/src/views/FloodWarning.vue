<template>
  <DataPage title="内涝预警" description="预警编号、点位、等级、触发原因、处理人和触发时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFloodWarningPage, addFloodWarning, updateFloodWarning, deleteFloodWarning, assignFloodWarning, closeFloodWarning } from '../api'
const api = { page: getFloodWarningPage, add: addFloodWarning, update: updateFloodWarning, delete: deleteFloodWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "warningLevel", "label": "预警等级"}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "triggerTime", "label": "触发时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "warningLevel", "label": "预警等级", "type": "select", "options": [{"label": "蓝色", "value": "蓝色"}, {"label": "黄色", "value": "黄色"}, {"label": "橙色", "value": "橙色"}, {"label": "红色", "value": "红色"}]}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "triggerTime", "label": "触发时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignFloodWarning(row.id)
  if (command === 'close') await closeFloodWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
