<template>
  <DataPage title="结算账单" description="商家结算账单、原币金额、折算人民币、结算状态和对账管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSettlementBillPage, addSettlementBill, updateSettlementBill, deleteSettlementBill, calculateSettlementBill, settleSettlementBill } from '../api'
const api = { page: getSettlementBillPage, add: addSettlementBill, update: updateSettlementBill, delete: deleteSettlementBill }
const columns = [{"prop": "billNo", "label": "账单编号"}, {"prop": "merchantName", "label": "商家"}, {"prop": "currencyCode", "label": "币种"}, {"prop": "originalAmount", "label": "原币金额"}, {"prop": "cnyAmount", "label": "人民币金额"}, {"prop": "settlementStatus", "label": "结算状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "billNo", "label": "账单编号"}, {"prop": "merchantName", "label": "商家"}, {"prop": "currencyCode", "label": "币种", "type": "select", "options": [{"label": "USD", "value": "USD"}, {"label": "EUR", "value": "EUR"}, {"label": "JPY", "value": "JPY"}, {"label": "GBP", "value": "GBP"}]}, {"prop": "originalAmount", "label": "原币金额", "type": "number"}, {"prop": "cnyAmount", "label": "人民币金额", "type": "number"}, {"prop": "settlementStatus", "label": "结算状态", "type": "select", "options": [{"label": "WAIT_CALCULATE", "value": "WAIT_CALCULATE"}, {"label": "CALCULATED", "value": "CALCULATED"}, {"label": "SETTLED", "value": "SETTLED"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CALCULATE", "value": "WAIT_CALCULATE"}, {"label": "CALCULATED", "value": "CALCULATED"}, {"label": "SETTLED", "value": "SETTLED"}]}]
const rowActions = [{"command": "calculate", "label": "核算", "type": "primary"}, {"command": "settle", "label": "结算", "type": "success"}]
const defaults = {"currencyCode": "USD", "settlementStatus": "WAIT_CALCULATE", "status": "WAIT_CALCULATE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'calculate') await calculateSettlementBill(row.id)
  if (command === 'settle') await settleSettlementBill(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
