<template>
  <DataPage title="商户结算" description="商户结算单、周期、核销金额、平台佣金、应结金额和结算状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMerchantSettlementPage, addMerchantSettlement, updateMerchantSettlement, deleteMerchantSettlement, calculateMerchantSettlement, settleMerchantSettlement } from '../api'
const api = { page: getMerchantSettlementPage, add: addMerchantSettlement, update: updateMerchantSettlement, delete: deleteMerchantSettlement }
const columns = [{"prop": "settlementNo", "label": "结算单号"}, {"prop": "merchantName", "label": "商户"}, {"prop": "settlementCycle", "label": "结算周期"}, {"prop": "verifyAmount", "label": "核销金额"}, {"prop": "commissionAmount", "label": "平台佣金"}, {"prop": "payableAmount", "label": "应结金额"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "settlementNo", "label": "结算单号"}, {"prop": "merchantName", "label": "商户"}, {"prop": "settlementCycle", "label": "结算周期"}, {"prop": "verifyAmount", "label": "核销金额", "type": "number"}, {"prop": "commissionAmount", "label": "平台佣金", "type": "number"}, {"prop": "payableAmount", "label": "应结金额", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CALCULATE", "value": "WAIT_CALCULATE"}, {"label": "CALCULATED", "value": "CALCULATED"}, {"label": "SETTLED", "value": "SETTLED"}]}]
const rowActions = [{"command": "calculate", "label": "核算", "type": "primary"}, {"command": "settle", "label": "结算", "type": "success"}]
const defaults = {"status": "WAIT_CALCULATE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'calculate') await calculateMerchantSettlement(row.id)
  if (command === 'settle') await settleMerchantSettlement(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
