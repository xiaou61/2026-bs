<template>
  <DataPage title="责任追溯" description="异常责任节点、责任方、影响范围和追责状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getResponsibilityTracePage, addResponsibilityTrace, updateResponsibilityTrace, deleteResponsibilityTrace, reviewResponsibilityTrace, confirmResponsibilityTrace, closeResponsibilityTrace } from '../api'
const api = { page: getResponsibilityTracePage, add: addResponsibilityTrace, update: updateResponsibilityTrace, delete: deleteResponsibilityTrace }
const columns = [{"prop": "traceNo", "label": "追溯编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "responsibleParty", "label": "责任方"}, {"prop": "nodeName", "label": "责任节点"}, {"prop": "reasonText", "label": "原因"}, {"prop": "impactScope", "label": "影响范围"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "traceNo", "label": "追溯编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "responsibleParty", "label": "责任方"}, {"prop": "nodeName", "label": "责任节点"}, {"prop": "reasonText", "label": "原因", "type": "textarea"}, {"prop": "impactScope", "label": "影响范围"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_REVIEW", "value": "WAIT_REVIEW"}, {"label": "REVIEWED", "value": "REVIEWED"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "review", "label": "复核", "type": "primary"}, {"command": "confirm", "label": "确认", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"status": "WAIT_REVIEW"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'review') await reviewResponsibilityTrace(row.id)
  if (command === 'confirm') await confirmResponsibilityTrace(row.id)
  if (command === 'close') await closeResponsibilityTrace(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
