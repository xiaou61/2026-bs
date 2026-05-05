<template>
  <DataPage title="养殖池塘" description="池塘编号、名称、养殖区域、水面面积和负责人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPondProfilePage, addPondProfile, updatePondProfile, deletePondProfile, activatePondProfile, finishPondProfile } from '../api'
const api = { page: getPondProfilePage, add: addPondProfile, update: updatePondProfile, delete: deletePondProfile }
const columns = [{"prop": "pondNo", "label": "池塘编号"}, {"prop": "pondName", "label": "池塘名称"}, {"prop": "farmArea", "label": "养殖区域"}, {"prop": "waterArea", "label": "水面面积"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pondNo", "label": "池塘编号"}, {"prop": "pondName", "label": "池塘名称"}, {"prop": "farmArea", "label": "养殖区域"}, {"prop": "waterArea", "label": "水面面积", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePondProfile(row.id)
  if (command === 'finish') await finishPondProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
