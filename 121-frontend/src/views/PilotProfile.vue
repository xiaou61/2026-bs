<template>
  <DataPage title="飞手档案" description="飞手编号、姓名、执照等级、所属队伍和累计飞行小时管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPilotProfilePage, addPilotProfile, updatePilotProfile, deletePilotProfile, enablePilotProfile, disablePilotProfile } from '../api'
const api = { page: getPilotProfilePage, add: addPilotProfile, update: updatePilotProfile, delete: deletePilotProfile }
const columns = [{"prop": "pilotNo", "label": "飞手编号"}, {"prop": "pilotName", "label": "飞手姓名"}, {"prop": "licenseLevel", "label": "执照等级"}, {"prop": "teamName", "label": "所属队伍"}, {"prop": "totalHours", "label": "累计小时"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pilotNo", "label": "飞手编号"}, {"prop": "pilotName", "label": "飞手姓名"}, {"prop": "licenseLevel", "label": "执照等级", "type": "select", "options": [{"label": "AOPA高级", "value": "AOPA高级"}, {"label": "AOPA中级", "value": "AOPA中级"}, {"label": "UTC认证", "value": "UTC认证"}]}, {"prop": "teamName", "label": "所属队伍"}, {"prop": "totalHours", "label": "累计小时", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enablePilotProfile(row.id)
  if (command === 'disable') await disablePilotProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
