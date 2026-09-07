<template>
  <DataPage title="云账号" description="云厂商账号、预算额度和负责人管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCloudAccountPage, addCloudAccount, updateCloudAccount, deleteCloudAccount, activateCloudAccount, disableCloudAccount } from '../api'
const api = { page: getCloudAccountPage, add: addCloudAccount, update: updateCloudAccount, delete: deleteCloudAccount }
const columns = [{"prop": "accountName", "label": "账号名称"}, {"prop": "accountCode", "label": "账号编码"}, {"prop": "provider", "label": "云厂商"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "accessMode", "label": "接入方式"}, {"prop": "monthlyBudget", "label": "月预算"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "accountName", "label": "账号名称"}, {"prop": "accountCode", "label": "账号编码"}, {"prop": "provider", "label": "云厂商", "type": "select", "options": [{"label": "ALIYUN", "value": "ALIYUN"}, {"label": "TENCENT", "value": "TENCENT"}, {"label": "HUAWEI", "value": "HUAWEI"}, {"label": "AWS", "value": "AWS"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "accessMode", "label": "接入方式", "type": "select", "options": [{"label": "API", "value": "API"}, {"label": "BILL_FILE", "value": "BILL_FILE"}, {"label": "MANUAL", "value": "MANUAL"}]}, {"prop": "monthlyBudget", "label": "月预算", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "PENDING", "value": "PENDING"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"provider": "ALIYUN", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCloudAccount(row.id)
  if (command === 'disable') await disableCloudAccount(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
