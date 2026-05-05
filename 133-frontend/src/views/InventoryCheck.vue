<template>
  <DataPage title="库存盘点" description="盘点编号、实验室、耗材名称、账面数量、实盘数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInventoryCheckPage, addInventoryCheck, updateInventoryCheck, deleteInventoryCheck, processInventoryCheck, finishInventoryCheck } from '../api'
const api = { page: getInventoryCheckPage, add: addInventoryCheck, update: updateInventoryCheck, delete: deleteInventoryCheck }
const columns = [{"prop": "checkNo", "label": "盘点编号"}, {"prop": "labName", "label": "实验室"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "bookQty", "label": "账面数量"}, {"prop": "actualQty", "label": "实盘数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "checkNo", "label": "盘点编号"}, {"prop": "labName", "label": "实验室"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "bookQty", "label": "账面数量", "type": "number"}, {"prop": "actualQty", "label": "实盘数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "PROCESSING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processInventoryCheck(row.id)
  if (command === 'finish') await finishInventoryCheck(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
