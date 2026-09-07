<template>
  <DataPage title="访问授权" description="审批后的访问令牌、数据范围和有效期管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccessGrantPage, addAccessGrant, updateAccessGrant, deleteAccessGrant, activateAccessGrant, revokeAccessGrant } from '../api'
const api = { page: getAccessGrantPage, add: addAccessGrant, update: updateAccessGrant, delete: deleteAccessGrant }
const columns = [{"prop": "grantNo", "label": "授权令牌"}, {"prop": "applicationNo", "label": "申请编号"}, {"prop": "granteeName", "label": "被授权人"}, {"prop": "dataScope", "label": "数据范围"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "grantNo", "label": "授权令牌"}, {"prop": "applicationNo", "label": "申请编号"}, {"prop": "granteeName", "label": "被授权人"}, {"prop": "dataScope", "label": "数据范围", "type": "textarea"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "REVOKED", "value": "REVOKED"}, {"label": "EXPIRED", "value": "EXPIRED"}]}]
const rowActions = [{"command": "activate", "label": "生效", "type": "primary"}, {"command": "revoke", "label": "撤销", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateAccessGrant(row.id)
  if (command === 'revoke') await revokeAccessGrant(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
