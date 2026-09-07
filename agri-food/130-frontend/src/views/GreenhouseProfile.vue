<template>
  <DataPage title="温室档案" description="温室编号、名称、基地、面积、负责人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getGreenhouseProfilePage, addGreenhouseProfile, updateGreenhouseProfile, deleteGreenhouseProfile, activateGreenhouseProfile, finishGreenhouseProfile } from '../api'
const api = { page: getGreenhouseProfilePage, add: addGreenhouseProfile, update: updateGreenhouseProfile, delete: deleteGreenhouseProfile }
const columns = [{"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "greenhouseName", "label": "温室名称"}, {"prop": "baseName", "label": "基地名称"}, {"prop": "areaSize", "label": "温室面积"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "greenhouseName", "label": "温室名称"}, {"prop": "baseName", "label": "基地名称"}, {"prop": "areaSize", "label": "温室面积", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateGreenhouseProfile(row.id)
  if (command === 'finish') await finishGreenhouseProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
