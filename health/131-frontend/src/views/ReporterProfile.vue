<template>
  <DataPage title="上报人档案" description="上报人编号、姓名、机构、联系电话和角色维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReporterProfilePage, addReporterProfile, updateReporterProfile, deleteReporterProfile, activateReporterProfile, finishReporterProfile } from '../api'
const api = { page: getReporterProfilePage, add: addReporterProfile, update: updateReporterProfile, delete: deleteReporterProfile }
const columns = [{"prop": "reporterNo", "label": "上报人编号"}, {"prop": "reporterName", "label": "上报人姓名"}, {"prop": "organizationName", "label": "所属机构"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "roleType", "label": "角色类型"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reporterNo", "label": "上报人编号"}, {"prop": "reporterName", "label": "上报人姓名"}, {"prop": "organizationName", "label": "所属机构"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "roleType", "label": "角色类型"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateReporterProfile(row.id)
  if (command === 'finish') await finishReporterProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
