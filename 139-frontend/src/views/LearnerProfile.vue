<template>
  <DataPage title="作者档案" description="作者编号、作者姓名、所属单位、联系邮箱、联系电话和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLearnerProfilePage, addLearnerProfile, updateLearnerProfile, deleteLearnerProfile, activateLearnerProfile, finishLearnerProfile } from '../api'
const api = { page: getLearnerProfilePage, add: addLearnerProfile, update: updateLearnerProfile, delete: deleteLearnerProfile }
const columns = [{"prop": "labNo", "label": "作者编号"}, {"prop": "labName", "label": "作者姓名"}, {"prop": "buildingName", "label": "所属单位"}, {"prop": "managerName", "label": "联系邮箱"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "labNo", "label": "作者编号"}, {"prop": "labName", "label": "作者姓名"}, {"prop": "buildingName", "label": "所属单位"}, {"prop": "managerName", "label": "联系邮箱"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateLearnerProfile(row.id)
  if (command === 'finish') await finishLearnerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




