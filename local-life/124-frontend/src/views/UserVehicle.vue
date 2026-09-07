<template>
  <DataPage title="用户车辆" description="车辆编号、车主、车牌、电池容量、车型和绑定状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getUserVehiclePage, addUserVehicle, updateUserVehicle, deleteUserVehicle, enableUserVehicle, disableUserVehicle } from '../api'
const api = { page: getUserVehiclePage, add: addUserVehicle, update: updateUserVehicle, delete: deleteUserVehicle }
const columns = [{"prop": "vehicleNo", "label": "车辆编号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "plateNo", "label": "车牌"}, {"prop": "batteryCapacity", "label": "电池容量"}, {"prop": "vehicleModel", "label": "车型"}, {"prop": "phone", "label": "手机号"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "vehicleNo", "label": "车辆编号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "plateNo", "label": "车牌"}, {"prop": "batteryCapacity", "label": "电池容量", "type": "number"}, {"prop": "vehicleModel", "label": "车型"}, {"prop": "phone", "label": "手机号"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableUserVehicle(row.id)
  if (command === 'disable') await disableUserVehicle(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
