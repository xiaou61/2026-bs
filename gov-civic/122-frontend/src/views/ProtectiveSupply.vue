<template>
  <DataPage title="防护用品" description="用品编号、名称、类型、库存、安全库存和仓库管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProtectiveSupplyPage, addProtectiveSupply, updateProtectiveSupply, deleteProtectiveSupply, enableProtectiveSupply, warnProtectiveSupply } from '../api'
const api = { page: getProtectiveSupplyPage, add: addProtectiveSupply, update: updateProtectiveSupply, delete: deleteProtectiveSupply }
const columns = [{"prop": "supplyNo", "label": "用品编号"}, {"prop": "supplyName", "label": "用品名称"}, {"prop": "supplyType", "label": "用品类型"}, {"prop": "stockQty", "label": "库存"}, {"prop": "safeQty", "label": "安全库存"}, {"prop": "warehouseName", "label": "仓库"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "supplyNo", "label": "用品编号"}, {"prop": "supplyName", "label": "用品名称"}, {"prop": "supplyType", "label": "用品类型"}, {"prop": "stockQty", "label": "库存", "type": "number"}, {"prop": "safeQty", "label": "安全库存", "type": "number"}, {"prop": "warehouseName", "label": "仓库"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableProtectiveSupply(row.id)
  if (command === 'warn') await warnProtectiveSupply(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
