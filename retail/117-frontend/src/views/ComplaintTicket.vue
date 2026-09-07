<template>
  <DataPage title="申诉工单" description="用户或商户申诉、问题类型、处理人、解决方案和工单状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getComplaintTicketPage, addComplaintTicket, updateComplaintTicket, deleteComplaintTicket, assignComplaintTicket, resolveComplaintTicket, closeComplaintTicket } from '../api'
const api = { page: getComplaintTicketPage, add: addComplaintTicket, update: updateComplaintTicket, delete: deleteComplaintTicket }
const columns = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "consumerName", "label": "用户"}, {"prop": "merchantName", "label": "商户"}, {"prop": "issueType", "label": "问题类型"}, {"prop": "detailText", "label": "问题描述"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "consumerName", "label": "用户"}, {"prop": "merchantName", "label": "商户"}, {"prop": "issueType", "label": "问题类型", "type": "select", "options": [{"label": "核销争议", "value": "核销争议"}, {"label": "退款申诉", "value": "退款申诉"}, {"label": "商户结算", "value": "商户结算"}, {"label": "活动规则", "value": "活动规则"}]}, {"prop": "detailText", "label": "问题描述", "type": "textarea"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "RESOLVED", "value": "RESOLVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "assign", "label": "派单", "type": "primary"}, {"command": "resolve", "label": "解决", "type": "success"}, {"command": "close", "label": "关闭", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignComplaintTicket(row.id)
  if (command === 'resolve') await resolveComplaintTicket(row.id)
  if (command === 'close') await closeComplaintTicket(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
