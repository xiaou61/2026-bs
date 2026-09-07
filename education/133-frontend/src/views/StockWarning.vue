<template>
  <DataPage title="库存预警" description="预警编号、耗材名称、当前库存、预警级别、处理人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStockWarningPage, addStockWarning, updateStockWarning, deleteStockWarning, processStockWarning, finishStockWarning } from '../api'
const api = { page: getStockWarningPage, add: addStockWarning, update: updateStockWarning, delete: deleteStockWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "currentQty", "label": "当前库存"}, {"prop": "warningLevel", "label": "预警级别"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "currentQty", "label": "当前库存", "type": "number"}, {"prop": "warningLevel", "label": "预警级别"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WARNING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processStockWarning(row.id)
  if (command === 'finish') await finishStockWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
