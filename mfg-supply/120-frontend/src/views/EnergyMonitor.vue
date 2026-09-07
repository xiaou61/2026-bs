<template>
  <DataPage title="能耗监测" description="楼宇能耗、设备能耗、能耗类型、统计周期、同比变化和能耗状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnergyMonitorPage, addEnergyMonitor, updateEnergyMonitor, deleteEnergyMonitor, warnEnergyMonitor, closeEnergyMonitor } from '../api'
const api = { page: getEnergyMonitorPage, add: addEnergyMonitor, update: updateEnergyMonitor, delete: deleteEnergyMonitor }
const columns = [{"prop": "energyNo", "label": "能耗编号"}, {"prop": "buildingName", "label": "楼宇"}, {"prop": "deviceName", "label": "设备"}, {"prop": "energyType", "label": "能耗类型"}, {"prop": "periodName", "label": "统计周期"}, {"prop": "energyValue", "label": "能耗值"}, {"prop": "changeRate", "label": "同比变化"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "energyNo", "label": "能耗编号"}, {"prop": "buildingName", "label": "楼宇"}, {"prop": "deviceName", "label": "设备"}, {"prop": "energyType", "label": "能耗类型", "type": "select", "options": [{"label": "用电", "value": "用电"}, {"label": "用水", "value": "用水"}, {"label": "燃气", "value": "燃气"}]}, {"prop": "periodName", "label": "统计周期"}, {"prop": "energyValue", "label": "能耗值", "type": "number"}, {"prop": "changeRate", "label": "同比变化", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnEnergyMonitor(row.id)
  if (command === 'close') await closeEnergyMonitor(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
