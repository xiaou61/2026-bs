<template>
  <DataPage title="设备资产" description="设备编号、设备名称、产线、关键等级、投运日期和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEquipmentAssetPage, addEquipmentAsset, updateEquipmentAsset, deleteEquipmentAsset, runEquipmentAsset, stopEquipmentAsset } from '../api'
const api = { page: getEquipmentAssetPage, add: addEquipmentAsset, update: updateEquipmentAsset, delete: deleteEquipmentAsset }
const columns = [{"prop": "equipmentNo", "label": "设备编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "lineName", "label": "所属产线"}, {"prop": "assetLevel", "label": "关键等级"}, {"prop": "onlineDate", "label": "投运日期"}, {"prop": "runtimeHours", "label": "运行小时"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "equipmentNo", "label": "设备编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "lineName", "label": "所属产线"}, {"prop": "assetLevel", "label": "关键等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "onlineDate", "label": "投运日期"}, {"prop": "runtimeHours", "label": "运行小时", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "RUNNING", "value": "RUNNING"}, {"label": "STOPPED", "value": "STOPPED"}, {"label": "MAINTAINING", "value": "MAINTAINING"}]}]
const rowActions = [{"command": "run", "label": "运行", "type": "success"}, {"command": "stop", "label": "停机", "type": "danger"}]
const defaults = {"status": "RUNNING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'run') await runEquipmentAsset(row.id)
  if (command === 'stop') await stopEquipmentAsset(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
