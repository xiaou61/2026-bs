<template>
  <DataPage title="授权记录" description="个人授权签署、来源渠道和有效期状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAuthorizationRecordPage, addAuthorizationRecord, updateAuthorizationRecord, deleteAuthorizationRecord, activateAuthorizationRecord, expireAuthorizationRecord, revokeAuthorizationRecord } from '../api'
const api = { page: getAuthorizationRecordPage, add: addAuthorizationRecord, update: updateAuthorizationRecord, delete: deleteAuthorizationRecord }
const columns = [{"prop": "authNo", "label": "授权编号"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "purposeName", "label": "授权目的"}, {"prop": "channelName", "label": "渠道"}, {"prop": "grantTime", "label": "授权时间"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "authNo", "label": "授权编号"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "purposeName", "label": "授权目的"}, {"prop": "channelName", "label": "渠道", "type": "select", "options": [{"label": "APP", "value": "APP"}, {"label": "WEB", "value": "WEB"}, {"label": "MINIAPP", "value": "MINIAPP"}, {"label": "OFFLINE", "value": "OFFLINE"}]}, {"prop": "grantTime", "label": "授权时间"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "EXPIRED", "value": "EXPIRED"}, {"label": "REVOKED", "value": "REVOKED"}]}]
const rowActions = [{"command": "activate", "label": "生效", "type": "primary"}, {"command": "expire", "label": "过期", "type": "primary"}, {"command": "revoke", "label": "撤销", "type": "primary"}]
const defaults = {"channelName": "APP", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateAuthorizationRecord(row.id)
  if (command === 'expire') await expireAuthorizationRecord(row.id)
  if (command === 'revoke') await revokeAuthorizationRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
