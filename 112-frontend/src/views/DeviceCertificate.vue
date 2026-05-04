<template>
  <DataPage title="设备证书" description="设备证书编号、签发时间、到期时间和吊销状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceCertificatePage, addDeviceCertificate, updateDeviceCertificate, deleteDeviceCertificate, revokeDeviceCertificate, renewDeviceCertificate } from '../api'
const api = { page: getDeviceCertificatePage, add: addDeviceCertificate, update: updateDeviceCertificate, delete: deleteDeviceCertificate }
const columns = [{"prop": "certNo", "label": "证书编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "issuerName", "label": "签发方"}, {"prop": "issuedTime", "label": "签发时间"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "certNo", "label": "证书编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "issuerName", "label": "签发方"}, {"prop": "issuedTime", "label": "签发时间"}, {"prop": "expireTime", "label": "到期时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "VALID", "value": "VALID"}, {"label": "REVOKED", "value": "REVOKED"}, {"label": "EXPIRED", "value": "EXPIRED"}]}]
const rowActions = [{"command": "revoke", "label": "吊销", "type": "primary"}, {"command": "renew", "label": "续期", "type": "primary"}]
const defaults = {"status": "VALID"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'revoke') await revokeDeviceCertificate(row.id)
  if (command === 'renew') await renewDeviceCertificate(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
