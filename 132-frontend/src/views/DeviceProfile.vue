<template>
  <DataPage title="器械档案" description="器械编号、名称、型号、科室、责任人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceProfilePage, addDeviceProfile, updateDeviceProfile, deleteDeviceProfile, activateDeviceProfile, finishDeviceProfile } from '../api'
const api = { page: getDeviceProfilePage, add: addDeviceProfile, update: updateDeviceProfile, delete: deleteDeviceProfile }
const columns = [{"prop": "deviceNo", "label": "器械编号"}, {"prop": "deviceName", "label": "器械名称"}, {"prop": "modelName", "label": "型号"}, {"prop": "deptName", "label": "所属科室"}, {"prop": "ownerName", "label": "责任人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deviceNo", "label": "器械编号"}, {"prop": "deviceName", "label": "器械名称"}, {"prop": "modelName", "label": "型号"}, {"prop": "deptName", "label": "所属科室"}, {"prop": "ownerName", "label": "责任人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDeviceProfile(row.id)
  if (command === 'finish') await finishDeviceProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
