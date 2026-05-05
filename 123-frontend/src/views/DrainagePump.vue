<template>
  <DataPage title="排涝泵站" description="泵站编号、名称、区域、排水能力、在线泵数和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDrainagePumpPage, addDrainagePump, updateDrainagePump, deleteDrainagePump, runDrainagePump, warnDrainagePump } from '../api'
const api = { page: getDrainagePumpPage, add: addDrainagePump, update: updateDrainagePump, delete: deleteDrainagePump }
const columns = [{"prop": "pumpNo", "label": "泵站编号"}, {"prop": "pumpName", "label": "泵站名称"}, {"prop": "districtName", "label": "行政区"}, {"prop": "capacityValue", "label": "排水能力"}, {"prop": "onlineCount", "label": "在线泵数"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pumpNo", "label": "泵站编号"}, {"prop": "pumpName", "label": "泵站名称"}, {"prop": "districtName", "label": "行政区"}, {"prop": "capacityValue", "label": "排水能力", "type": "number"}, {"prop": "onlineCount", "label": "在线泵数", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "run", "label": "运行", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'run') await runDrainagePump(row.id)
  if (command === 'warn') await warnDrainagePump(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
