<template>
  <DataPage title="实验室" description="实验室编号、名称、楼栋位置、负责人、联系电话和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLabRoomPage, addLabRoom, updateLabRoom, deleteLabRoom, activateLabRoom, finishLabRoom } from '../api'
const api = { page: getLabRoomPage, add: addLabRoom, update: updateLabRoom, delete: deleteLabRoom }
const columns = [{"prop": "labNo", "label": "实验室编号"}, {"prop": "labName", "label": "实验室名称"}, {"prop": "buildingName", "label": "楼栋位置"}, {"prop": "managerName", "label": "负责人"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "labNo", "label": "实验室编号"}, {"prop": "labName", "label": "实验室名称"}, {"prop": "buildingName", "label": "楼栋位置"}, {"prop": "managerName", "label": "负责人"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateLabRoom(row.id)
  if (command === 'finish') await finishLabRoom(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
