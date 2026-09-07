<template>
  <DataPage title="用电设备" description="设备编号、家庭、设备名称、设备类型、额定功率和启用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getElectricDevicePage, addElectricDevice, updateElectricDevice, deleteElectricDevice, enableElectricDevice, disableElectricDevice } from '../api'
const api = { page: getElectricDevicePage, add: addElectricDevice, update: updateElectricDevice, delete: deleteElectricDevice }
const columns = [{"prop": "deviceNo", "label": "设备编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "ratedPower", "label": "额定功率"}, {"prop": "roomName", "label": "房间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deviceNo", "label": "设备编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceType", "label": "设备类型", "type": "select", "options": [{"label": "空调", "value": "空调"}, {"label": "热水器", "value": "热水器"}, {"label": "照明", "value": "照明"}, {"label": "厨房电器", "value": "厨房电器"}, {"label": "其他", "value": "其他"}]}, {"prop": "ratedPower", "label": "额定功率", "type": "number"}, {"prop": "roomName", "label": "房间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableElectricDevice(row.id)
  if (command === 'disable') await disableElectricDevice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
