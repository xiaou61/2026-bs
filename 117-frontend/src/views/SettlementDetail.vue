<template>
  <DataPage title="结算明细" description="结算单明细、核销编号、券名称、消费金额、补贴金额和确认状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSettlementDetailPage, addSettlementDetail, updateSettlementDetail, deleteSettlementDetail, confirmSettlementDetail, adjustSettlementDetail } from '../api'
const api = { page: getSettlementDetailPage, add: addSettlementDetail, update: updateSettlementDetail, delete: deleteSettlementDetail }
const columns = [{"prop": "detailNo", "label": "明细编号"}, {"prop": "settlementNo", "label": "结算单号"}, {"prop": "verifyNo", "label": "核销编号"}, {"prop": "couponName", "label": "券名称"}, {"prop": "consumeAmount", "label": "消费金额"}, {"prop": "subsidyAmount", "label": "补贴金额"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "detailNo", "label": "明细编号"}, {"prop": "settlementNo", "label": "结算单号"}, {"prop": "verifyNo", "label": "核销编号"}, {"prop": "couponName", "label": "券名称"}, {"prop": "consumeAmount", "label": "消费金额", "type": "number"}, {"prop": "subsidyAmount", "label": "补贴金额", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CONFIRM", "value": "WAIT_CONFIRM"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "ADJUSTED", "value": "ADJUSTED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "adjust", "label": "调整", "type": "warning"}]
const defaults = {"status": "WAIT_CONFIRM"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmSettlementDetail(row.id)
  if (command === 'adjust') await adjustSettlementDetail(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
