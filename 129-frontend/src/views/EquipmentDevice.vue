<template>
  <DataPage title="养殖设备" description="设备编号、池塘、设备名称、设备类型和安装位置维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEquipmentDevicePage, addEquipmentDevice, updateEquipmentDevice, deleteEquipmentDevice, activateEquipmentDevice, finishEquipmentDevice } from '../api'
const api = { page: getEquipmentDevicePage, add: addEquipmentDevice, update: updateEquipmentDevice, delete: deleteEquipmentDevice }
const columns = [{"prop": "deviceNo", "label": "设备编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "installPosition", "label": "安装位置"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deviceNo", "label": "设备编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "installPosition", "label": "安装位置"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEquipmentDevice(row.id)
  if (command === 'finish') await finishEquipmentDevice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
