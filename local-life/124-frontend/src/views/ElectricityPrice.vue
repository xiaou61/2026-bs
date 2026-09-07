<template>
  <DataPage title="电价策略" description="策略编号、站点、时段类型、电价、服务费和生效日期管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getElectricityPricePage, addElectricityPrice, updateElectricityPrice, deleteElectricityPrice, enableElectricityPrice, disableElectricityPrice } from '../api'
const api = { page: getElectricityPricePage, add: addElectricityPrice, update: updateElectricityPrice, delete: deleteElectricityPrice }
const columns = [{"prop": "priceNo", "label": "策略编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "periodType", "label": "时段类型"}, {"prop": "electricPrice", "label": "电价"}, {"prop": "serviceFee", "label": "服务费"}, {"prop": "effectiveDate", "label": "生效日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "priceNo", "label": "策略编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "periodType", "label": "时段类型", "type": "select", "options": [{"label": "峰时", "value": "峰时"}, {"label": "平时", "value": "平时"}, {"label": "谷时", "value": "谷时"}]}, {"prop": "electricPrice", "label": "电价", "type": "number"}, {"prop": "serviceFee", "label": "服务费", "type": "number"}, {"prop": "effectiveDate", "label": "生效日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableElectricityPrice(row.id)
  if (command === 'disable') await disableElectricityPrice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
