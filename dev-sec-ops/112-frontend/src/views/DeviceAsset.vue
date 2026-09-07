<template>
  <DataPage title="设备资产" description="终端设备、系统版本、归属人和准入状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceAssetPage, addDeviceAsset, updateDeviceAsset, deleteDeviceAsset, approveDeviceAsset, blockDeviceAsset, retireDeviceAsset } from '../api'
const api = { page: getDeviceAssetPage, add: addDeviceAsset, update: updateDeviceAsset, delete: deleteDeviceAsset }
const columns = [{"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "osVersion", "label": "系统版本"}, {"prop": "ownerName", "label": "归属人"}, {"prop": "departmentName", "label": "部门"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "deviceType", "label": "设备类型", "type": "select", "options": [{"label": "LAPTOP", "value": "LAPTOP"}, {"label": "DESKTOP", "value": "DESKTOP"}, {"label": "MOBILE", "value": "MOBILE"}, {"label": "SERVER", "value": "SERVER"}]}, {"prop": "osVersion", "label": "系统版本"}, {"prop": "ownerName", "label": "归属人"}, {"prop": "departmentName", "label": "部门"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "BLOCKED", "value": "BLOCKED"}, {"label": "RETIRED", "value": "RETIRED"}]}]
const rowActions = [{"command": "approve", "label": "准入", "type": "primary"}, {"command": "block", "label": "阻断", "type": "primary"}, {"command": "retire", "label": "退役", "type": "primary"}]
const defaults = {"deviceType": "LAPTOP", "status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveDeviceAsset(row.id)
  if (command === 'block') await blockDeviceAsset(row.id)
  if (command === 'retire') await retireDeviceAsset(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
