<template>
  <DataPage title="充电站点" description="站点编号、名称、城市、地址、运营商和开放状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getChargingStationPage, addChargingStation, updateChargingStation, deleteChargingStation, enableChargingStation, disableChargingStation } from '../api'
const api = { page: getChargingStationPage, add: addChargingStation, update: updateChargingStation, delete: deleteChargingStation }
const columns = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "cityName", "label": "城市"}, {"prop": "addressName", "label": "地址"}, {"prop": "operatorName", "label": "运营商"}, {"prop": "openTime", "label": "开放时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "cityName", "label": "城市"}, {"prop": "addressName", "label": "地址"}, {"prop": "operatorName", "label": "运营商"}, {"prop": "openTime", "label": "开放时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableChargingStation(row.id)
  if (command === 'disable') await disableChargingStation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
