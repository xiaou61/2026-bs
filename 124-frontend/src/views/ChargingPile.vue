<template>
  <DataPage title="充电桩位" description="桩位编号、所属站点、功率、接口类型、费率和桩位状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getChargingPilePage, addChargingPile, updateChargingPile, deleteChargingPile, availableChargingPile, disableChargingPile } from '../api'
const api = { page: getChargingPilePage, add: addChargingPile, update: updateChargingPile, delete: deleteChargingPile }
const columns = [{"prop": "pileNo", "label": "桩位编号"}, {"prop": "stationName", "label": "所属站点"}, {"prop": "powerKw", "label": "功率kW"}, {"prop": "connectorType", "label": "接口类型"}, {"prop": "pricePerKwh", "label": "电价"}, {"prop": "positionName", "label": "位置"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pileNo", "label": "桩位编号"}, {"prop": "stationName", "label": "所属站点"}, {"prop": "powerKw", "label": "功率kW", "type": "number"}, {"prop": "connectorType", "label": "接口类型"}, {"prop": "pricePerKwh", "label": "电价", "type": "number"}, {"prop": "positionName", "label": "位置"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "available", "label": "空闲", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "AVAILABLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'available') await availableChargingPile(row.id)
  if (command === 'disable') await disableChargingPile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
