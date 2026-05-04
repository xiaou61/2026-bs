<template>
  <DataPage title="目标名单" description="演练目标员工、发送状态和风险标记管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCampaignTargetPage, addCampaignTarget, updateCampaignTarget, deleteCampaignTarget } from '../api'
const api = { page: getCampaignTargetPage, add: addCampaignTarget, update: updateCampaignTarget, delete: deleteCampaignTarget }
const columns = [{"prop": "campaignName", "label": "活动名称"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "email", "label": "邮箱"}, {"prop": "departmentName", "label": "部门"}, {"prop": "riskTag", "label": "风险标签"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "campaignName", "label": "活动名称"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "email", "label": "邮箱"}, {"prop": "departmentName", "label": "部门"}, {"prop": "riskTag", "label": "风险标签", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_SEND", "value": "WAIT_SEND"}, {"label": "SENT", "value": "SENT"}, {"label": "CLICKED", "value": "CLICKED"}, {"label": "TRAINED", "value": "TRAINED"}]}]
const rowActions = []
const defaults = {"status": "WAIT_SEND"}
const handleAction = () => {}
</script>
