<template>
  <DataPage title="授权目的" description="数据处理目的、业务场景和有效期管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getConsentPurposePage, addConsentPurpose, updateConsentPurpose, deleteConsentPurpose, publishConsentPurpose, archiveConsentPurpose } from '../api'
const api = { page: getConsentPurposePage, add: addConsentPurpose, update: updateConsentPurpose, delete: deleteConsentPurpose }
const columns = [{"prop": "purposeName", "label": "目的名称"}, {"prop": "purposeCode", "label": "目的编码"}, {"prop": "businessScene", "label": "业务场景"}, {"prop": "purposeText", "label": "目的说明"}, {"prop": "validDays", "label": "有效天数"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "purposeName", "label": "目的名称"}, {"prop": "purposeCode", "label": "目的编码"}, {"prop": "businessScene", "label": "业务场景"}, {"prop": "purposeText", "label": "目的说明", "type": "textarea"}, {"prop": "validDays", "label": "有效天数", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}, {"label": "DRAFT", "value": "DRAFT"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishConsentPurpose(row.id)
  if (command === 'archive') await archiveConsentPurpose(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
