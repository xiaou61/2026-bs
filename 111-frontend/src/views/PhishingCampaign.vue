<template>
  <DataPage title="演练活动" description="钓鱼邮件演练计划、目标范围和执行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPhishingCampaignPage, addPhishingCampaign, updatePhishingCampaign, deletePhishingCampaign, submitPhishingCampaign, startPhishingCampaign, finishPhishingCampaign } from '../api'
const api = { page: getPhishingCampaignPage, add: addPhishingCampaign, update: updatePhishingCampaign, delete: deletePhishingCampaign }
const columns = [{"prop": "campaignName", "label": "活动名称"}, {"prop": "campaignNo", "label": "活动编号"}, {"prop": "templateName", "label": "邮件模板"}, {"prop": "targetScope", "label": "目标范围"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "plannedTime", "label": "计划时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "campaignName", "label": "活动名称"}, {"prop": "campaignNo", "label": "活动编号"}, {"prop": "templateName", "label": "邮件模板"}, {"prop": "targetScope", "label": "目标范围", "type": "textarea"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "plannedTime", "label": "计划时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "RUNNING", "value": "RUNNING"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "start", "label": "启动", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitPhishingCampaign(row.id)
  if (command === 'start') await startPhishingCampaign(row.id)
  if (command === 'finish') await finishPhishingCampaign(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
