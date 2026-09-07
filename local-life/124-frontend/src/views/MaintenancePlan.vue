<template>
  <DataPage title="保养计划" description="计划编号、站点、桩位、保养类型、负责人和计划时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenancePlanPage, addMaintenancePlan, updateMaintenancePlan, deleteMaintenancePlan, publishMaintenancePlan, finishMaintenancePlan } from '../api'
const api = { page: getMaintenancePlanPage, add: addMaintenancePlan, update: updateMaintenancePlan, delete: deleteMaintenancePlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "pileNo", "label": "桩位编号"}, {"prop": "maintenanceType", "label": "保养类型"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "pileNo", "label": "桩位编号"}, {"prop": "maintenanceType", "label": "保养类型"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishMaintenancePlan(row.id)
  if (command === 'finish') await finishMaintenancePlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
