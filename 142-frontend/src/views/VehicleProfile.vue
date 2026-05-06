<template>
  <DataPage title="导师档案" description="导师编号、导师姓名、所属学院、研究方向、可带人数和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getVehicleProfilePage, addVehicleProfile, updateVehicleProfile, deleteVehicleProfile, activateVehicleProfile, finishVehicleProfile } from '../api'
const api = { page: getVehicleProfilePage, add: addVehicleProfile, update: updateVehicleProfile, delete: deleteVehicleProfile }
const columns = [{"prop": "categoryNo", "label": "导师编号"}, {"prop": "categoryName", "label": "导师姓名"}, {"prop": "usageScope", "label": "所属学院"}, {"prop": "controlMode", "label": "研究方向"}, {"prop": "managerName", "label": "可带人数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "categoryNo", "label": "导师编号"}, {"prop": "categoryName", "label": "导师姓名"}, {"prop": "usageScope", "label": "所属学院"}, {"prop": "controlMode", "label": "研究方向"}, {"prop": "managerName", "label": "可带人数"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateVehicleProfile(row.id)
  if (command === 'finish') await finishVehicleProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>





