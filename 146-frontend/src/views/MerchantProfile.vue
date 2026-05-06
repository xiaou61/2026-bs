<template>
  <DataPage title="学生档案" description="学号、学生姓名、专业名称、班级名称、志愿数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMerchantProfilePage, addMerchantProfile, updateMerchantProfile, deleteMerchantProfile, processMerchantProfile, finishMerchantProfile } from '../api'
const api = { page: getMerchantProfilePage, add: addMerchantProfile, update: updateMerchantProfile, delete: deleteMerchantProfile }
const columns = [{"prop": "budgetNo", "label": "学号"}, {"prop": "projectNo", "label": "学生姓名"}, {"prop": "categoryName", "label": "专业名称"}, {"prop": "budgetAmount", "label": "班级名称"}, {"prop": "usedAmount", "label": "志愿数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "budgetNo", "label": "学号"}, {"prop": "projectNo", "label": "学生姓名"}, {"prop": "categoryName", "label": "专业名称"}, {"prop": "budgetAmount", "label": "班级名称", "type": "number"}, {"prop": "usedAmount", "label": "志愿数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processMerchantProfile(row.id)
  if (command === 'finish') await finishMerchantProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>







