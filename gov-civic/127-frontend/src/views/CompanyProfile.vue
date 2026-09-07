<template>
  <DataPage title="企业档案" description="企业编号、名称、行业、区域、联系人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCompanyProfilePage, addCompanyProfile, updateCompanyProfile, deleteCompanyProfile, activateCompanyProfile, finishCompanyProfile } from '../api'
const api = { page: getCompanyProfilePage, add: addCompanyProfile, update: updateCompanyProfile, delete: deleteCompanyProfile }
const columns = [{"prop": "companyNo", "label": "企业编号"}, {"prop": "companyName", "label": "企业名称"}, {"prop": "industryName", "label": "所属行业"}, {"prop": "regionName", "label": "所在区域"}, {"prop": "contactName", "label": "联系人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "companyNo", "label": "企业编号"}, {"prop": "companyName", "label": "企业名称"}, {"prop": "industryName", "label": "所属行业"}, {"prop": "regionName", "label": "所在区域"}, {"prop": "contactName", "label": "联系人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCompanyProfile(row.id)
  if (command === 'finish') await finishCompanyProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
