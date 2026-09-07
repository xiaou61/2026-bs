<template>
  <DataPage title="披露模板" description="披露模板编号、名称、适用行业、版本和负责人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDisclosureTemplatePage, addDisclosureTemplate, updateDisclosureTemplate, deleteDisclosureTemplate, activateDisclosureTemplate, finishDisclosureTemplate } from '../api'
const api = { page: getDisclosureTemplatePage, add: addDisclosureTemplate, update: updateDisclosureTemplate, delete: deleteDisclosureTemplate }
const columns = [{"prop": "templateNo", "label": "模板编号"}, {"prop": "templateName", "label": "模板名称"}, {"prop": "industryName", "label": "适用行业"}, {"prop": "versionName", "label": "版本"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "templateNo", "label": "模板编号"}, {"prop": "templateName", "label": "模板名称"}, {"prop": "industryName", "label": "适用行业"}, {"prop": "versionName", "label": "版本"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDisclosureTemplate(row.id)
  if (command === 'finish') await finishDisclosureTemplate(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
