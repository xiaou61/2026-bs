<template>
  <DataPage title="碳配额" description="碳配额编号、企业、年度、配额量和已使用量维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCarbonQuotaPage, addCarbonQuota, updateCarbonQuota, deleteCarbonQuota, activateCarbonQuota, finishCarbonQuota } from '../api'
const api = { page: getCarbonQuotaPage, add: addCarbonQuota, update: updateCarbonQuota, delete: deleteCarbonQuota }
const columns = [{"prop": "quotaNo", "label": "配额编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "quotaYear", "label": "配额年度"}, {"prop": "quotaAmount", "label": "配额量"}, {"prop": "usedAmount", "label": "已使用量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "quotaNo", "label": "配额编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "quotaYear", "label": "配额年度", "type": "number"}, {"prop": "quotaAmount", "label": "配额量", "type": "number"}, {"prop": "usedAmount", "label": "已使用量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCarbonQuota(row.id)
  if (command === 'finish') await finishCarbonQuota(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
