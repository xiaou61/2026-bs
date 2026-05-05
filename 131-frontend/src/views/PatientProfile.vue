<template>
  <DataPage title="患者档案" description="患者编号、姓名、性别、年龄、联系电话和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPatientProfilePage, addPatientProfile, updatePatientProfile, deletePatientProfile, activatePatientProfile, finishPatientProfile } from '../api'
const api = { page: getPatientProfilePage, add: addPatientProfile, update: updatePatientProfile, delete: deletePatientProfile }
const columns = [{"prop": "patientNo", "label": "患者编号"}, {"prop": "patientName", "label": "患者姓名"}, {"prop": "genderName", "label": "性别"}, {"prop": "ageValue", "label": "年龄"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "patientNo", "label": "患者编号"}, {"prop": "patientName", "label": "患者姓名"}, {"prop": "genderName", "label": "性别"}, {"prop": "ageValue", "label": "年龄", "type": "number"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePatientProfile(row.id)
  if (command === 'finish') await finishPatientProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
