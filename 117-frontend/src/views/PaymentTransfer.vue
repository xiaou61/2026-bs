<template>
  <DataPage title="打款记录" description="商户打款单、结算单、银行账号、打款金额、打款时间和状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPaymentTransferPage, addPaymentTransfer, updatePaymentTransfer, deletePaymentTransfer, submitPaymentTransfer, successPaymentTransfer, failPaymentTransfer } from '../api'
const api = { page: getPaymentTransferPage, add: addPaymentTransfer, update: updatePaymentTransfer, delete: deletePaymentTransfer }
const columns = [{"prop": "transferNo", "label": "打款编号"}, {"prop": "settlementNo", "label": "结算单号"}, {"prop": "merchantName", "label": "商户"}, {"prop": "transferAmount", "label": "打款金额"}, {"prop": "bankAccount", "label": "银行账号"}, {"prop": "transferTime", "label": "打款时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "transferNo", "label": "打款编号"}, {"prop": "settlementNo", "label": "结算单号"}, {"prop": "merchantName", "label": "商户"}, {"prop": "transferAmount", "label": "打款金额", "type": "number"}, {"prop": "bankAccount", "label": "银行账号"}, {"prop": "transferTime", "label": "打款时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_SUBMIT", "value": "WAIT_SUBMIT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "SUCCESS", "value": "SUCCESS"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "success", "label": "成功", "type": "success"}, {"command": "fail", "label": "失败", "type": "danger"}]
const defaults = {"status": "WAIT_SUBMIT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitPaymentTransfer(row.id)
  if (command === 'success') await successPaymentTransfer(row.id)
  if (command === 'fail') await failPaymentTransfer(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
