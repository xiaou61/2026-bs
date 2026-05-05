<template>
  <DataPage title="库存台账" description="库存编号、耗材名称、实验室、当前库存、锁定数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStockItemPage, addStockItem, updateStockItem, deleteStockItem, processStockItem, finishStockItem } from '../api'
const api = { page: getStockItemPage, add: addStockItem, update: updateStockItem, delete: deleteStockItem }
const columns = [{"prop": "stockNo", "label": "库存编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "labName", "label": "实验室"}, {"prop": "currentQty", "label": "当前库存"}, {"prop": "lockedQty", "label": "锁定数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stockNo", "label": "库存编号"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "labName", "label": "实验室"}, {"prop": "currentQty", "label": "当前库存", "type": "number"}, {"prop": "lockedQty", "label": "锁定数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processStockItem(row.id)
  if (command === 'finish') await finishStockItem(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
