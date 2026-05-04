<template>
  <DataPage title="邮件模板" description="钓鱼邮件主题、诱导类型和模板状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMailTemplatePage, addMailTemplate, updateMailTemplate, deleteMailTemplate, publishMailTemplate, archiveMailTemplate } from '../api'
const api = { page: getMailTemplatePage, add: addMailTemplate, update: updateMailTemplate, delete: deleteMailTemplate }
const columns = [{"prop": "templateName", "label": "模板名称"}, {"prop": "subjectText", "label": "邮件主题"}, {"prop": "senderName", "label": "发件人"}, {"prop": "baitType", "label": "诱导类型"}, {"prop": "landingUrl", "label": "落地页"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "templateName", "label": "模板名称"}, {"prop": "subjectText", "label": "邮件主题"}, {"prop": "senderName", "label": "发件人"}, {"prop": "baitType", "label": "诱导类型", "type": "select", "options": [{"label": "ACCOUNT", "value": "ACCOUNT"}, {"label": "PAYROLL", "value": "PAYROLL"}, {"label": "VPN", "value": "VPN"}, {"label": "NOTICE", "value": "NOTICE"}, {"label": "FILE", "value": "FILE"}]}, {"prop": "landingUrl", "label": "落地页"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"baitType": "ACCOUNT", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishMailTemplate(row.id)
  if (command === 'archive') await archiveMailTemplate(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
