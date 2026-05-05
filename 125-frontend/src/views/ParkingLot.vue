<template>
  <DataPage title="停车场" description="停车场编号、名称、城市、地址、总车位和运营商管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getParkingLotPage, addParkingLot, updateParkingLot, deleteParkingLot, enableParkingLot, disableParkingLot } from '../api'
const api = { page: getParkingLotPage, add: addParkingLot, update: updateParkingLot, delete: deleteParkingLot }
const columns = [{"prop": "lotNo", "label": "停车场编号"}, {"prop": "lotName", "label": "停车场名称"}, {"prop": "cityName", "label": "城市"}, {"prop": "addressName", "label": "地址"}, {"prop": "totalSpace", "label": "总车位"}, {"prop": "operatorName", "label": "运营商"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "lotNo", "label": "停车场编号"}, {"prop": "lotName", "label": "停车场名称"}, {"prop": "cityName", "label": "城市"}, {"prop": "addressName", "label": "地址"}, {"prop": "totalSpace", "label": "总车位", "type": "number"}, {"prop": "operatorName", "label": "运营商"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableParkingLot(row.id)
  if (command === 'disable') await disableParkingLot(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
