<template>
  <DataPage title="维保计划" description="维保计划、设备、备件、建议动作、计划时间、负责人和计划状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenancePlanPage, addMaintenancePlan, updateMaintenancePlan, deleteMaintenancePlan, startMaintenancePlan, finishMaintenancePlan } from '../api'
const api = { page: getMaintenancePlanPage, add: addMaintenancePlan, update: updateMaintenancePlan, delete: deleteMaintenancePlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "partName", "label": "备件名称"}, {"prop": "suggestAction", "label": "建议动作"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "partName", "label": "备件名称"}, {"prop": "suggestAction", "label": "建议动作", "type": "select", "options": [{"label": "提前更换", "value": "提前更换"}, {"label": "点检观察", "value": "点检观察"}, {"label": "库存补货", "value": "库存补货"}]}, {"prop": "planTime", "label": "计划时间"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_EXECUTE", "value": "WAIT_EXECUTE"}, {"label": "EXECUTING", "value": "EXECUTING"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WAIT_EXECUTE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startMaintenancePlan(row.id)
  if (command === 'finish') await finishMaintenancePlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
