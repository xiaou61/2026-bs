<template>
  <DataPage title="售后工单" description="售后工单、订单、问题类型、客服、处理方案和工单状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAfterSaleTicketPage, addAfterSaleTicket, updateAfterSaleTicket, deleteAfterSaleTicket, assignAfterSaleTicket, resolveAfterSaleTicket, closeAfterSaleTicket } from '../api'
const api = { page: getAfterSaleTicketPage, add: addAfterSaleTicket, update: updateAfterSaleTicket, delete: deleteAfterSaleTicket }
const columns = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "customerName", "label": "客户"}, {"prop": "issueType", "label": "问题类型"}, {"prop": "serviceUser", "label": "客服"}, {"prop": "solutionText", "label": "处理方案"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "customerName", "label": "客户"}, {"prop": "issueType", "label": "问题类型", "type": "select", "options": [{"label": "仅退款", "value": "仅退款"}, {"label": "退货退款", "value": "退货退款"}, {"label": "物流异常", "value": "物流异常"}, {"label": "质量问题", "value": "质量问题"}]}, {"prop": "serviceUser", "label": "客服"}, {"prop": "solutionText", "label": "处理方案", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "RESOLVED", "value": "RESOLVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "assign", "label": "派单", "type": "primary"}, {"command": "resolve", "label": "解决", "type": "success"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignAfterSaleTicket(row.id)
  if (command === 'resolve') await resolveAfterSaleTicket(row.id)
  if (command === 'close') await closeAfterSaleTicket(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
