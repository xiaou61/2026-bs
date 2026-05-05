<template>
  <DataPage title="核销记录" description="券码核销、门店、收银员、消费金额、核销时间和核销状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getVerificationRecordPage, addVerificationRecord, updateVerificationRecord, deleteVerificationRecord, confirmVerificationRecord, revokeVerificationRecord } from '../api'
const api = { page: getVerificationRecordPage, add: addVerificationRecord, update: updateVerificationRecord, delete: deleteVerificationRecord }
const columns = [{"prop": "verifyNo", "label": "核销编号"}, {"prop": "couponNo", "label": "券码"}, {"prop": "storeName", "label": "门店"}, {"prop": "cashierName", "label": "收银员"}, {"prop": "consumeAmount", "label": "消费金额"}, {"prop": "discountAmount", "label": "优惠金额"}, {"prop": "verifyTime", "label": "核销时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "verifyNo", "label": "核销编号"}, {"prop": "couponNo", "label": "券码"}, {"prop": "storeName", "label": "门店"}, {"prop": "cashierName", "label": "收银员"}, {"prop": "consumeAmount", "label": "消费金额", "type": "number"}, {"prop": "discountAmount", "label": "优惠金额", "type": "number"}, {"prop": "verifyTime", "label": "核销时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CONFIRM", "value": "WAIT_CONFIRM"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "REVOKED", "value": "REVOKED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "revoke", "label": "撤销", "type": "danger"}]
const defaults = {"status": "WAIT_CONFIRM"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmVerificationRecord(row.id)
  if (command === 'revoke') await revokeVerificationRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
