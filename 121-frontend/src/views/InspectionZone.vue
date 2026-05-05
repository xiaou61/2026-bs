<template>
  <DataPage title="巡检区域" description="区域编号、区域名称、城市、风险等级和负责人管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionZonePage, addInspectionZone, updateInspectionZone, deleteInspectionZone, enableInspectionZone, disableInspectionZone } from '../api'
const api = { page: getInspectionZonePage, add: addInspectionZone, update: updateInspectionZone, delete: deleteInspectionZone }
const columns = [{"prop": "zoneNo", "label": "区域编号"}, {"prop": "zoneName", "label": "区域名称"}, {"prop": "cityName", "label": "城市"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "zoneNo", "label": "区域编号"}, {"prop": "zoneName", "label": "区域名称"}, {"prop": "cityName", "label": "城市"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableInspectionZone(row.id)
  if (command === 'disable') await disableInspectionZone(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
