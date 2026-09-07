<template>
  <DataPage title="设备检查" description="检查编号、设备名称、设备类型、项目、检查结果和检查人管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEquipmentCheckPage, addEquipmentCheck, updateEquipmentCheck, deleteEquipmentCheck, passEquipmentCheck, warnEquipmentCheck } from '../api'
const api = { page: getEquipmentCheckPage, add: addEquipmentCheck, update: updateEquipmentCheck, delete: deleteEquipmentCheck }
const columns = [{"prop": "checkNo", "label": "检查编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "equipmentType", "label": "设备类型"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "checkResult", "label": "检查结果"}, {"prop": "inspectorName", "label": "检查人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "checkNo", "label": "检查编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "equipmentType", "label": "设备类型"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "checkResult", "label": "检查结果", "type": "select", "options": [{"label": "PASS", "value": "PASS"}, {"label": "WARNING", "value": "WARNING"}, {"label": "FAIL", "value": "FAIL"}]}, {"prop": "inspectorName", "label": "检查人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "pass", "label": "合格", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'pass') await passEquipmentCheck(row.id)
  if (command === 'warn') await warnEquipmentCheck(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
