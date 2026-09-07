<template>
  <DataPage title="身份源" description="统一身份源、同步方式和连接状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getIdentityProviderPage, addIdentityProvider, updateIdentityProvider, deleteIdentityProvider, connectIdentityProvider, disableIdentityProvider } from '../api'
const api = { page: getIdentityProviderPage, add: addIdentityProvider, update: updateIdentityProvider, delete: deleteIdentityProvider }
const columns = [{"prop": "providerName", "label": "身份源名称"}, {"prop": "providerCode", "label": "身份源编码"}, {"prop": "syncMode", "label": "同步方式"}, {"prop": "endpointUrl", "label": "地址"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "providerName", "label": "身份源名称"}, {"prop": "providerCode", "label": "身份源编码"}, {"prop": "syncMode", "label": "同步方式", "type": "select", "options": [{"label": "API", "value": "API"}, {"label": "LDAP", "value": "LDAP"}, {"label": "SAML", "value": "SAML"}, {"label": "OIDC", "value": "OIDC"}]}, {"prop": "endpointUrl", "label": "地址"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "connect", "label": "连接", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"syncMode": "API", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'connect') await connectIdentityProvider(row.id)
  if (command === 'disable') await disableIdentityProvider(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
