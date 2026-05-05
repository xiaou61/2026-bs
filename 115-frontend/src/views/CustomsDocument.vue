<template>
  <DataPage title="清关单证" description="发票、箱单、报关委托、物流凭证和审核结果管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCustomsDocumentPage, addCustomsDocument, updateCustomsDocument, deleteCustomsDocument, approveCustomsDocument, rejectCustomsDocument } from '../api'
const api = { page: getCustomsDocumentPage, add: addCustomsDocument, update: updateCustomsDocument, delete: deleteCustomsDocument }
const columns = [{"prop": "documentNo", "label": "单证编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "documentType", "label": "单证类型"}, {"prop": "fileUrl", "label": "文件地址"}, {"prop": "auditUser", "label": "审核人"}, {"prop": "auditResult", "label": "审核结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "documentNo", "label": "单证编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "documentType", "label": "单证类型", "type": "select", "options": [{"label": "INVOICE", "value": "INVOICE"}, {"label": "PACKING_LIST", "value": "PACKING_LIST"}, {"label": "POWER_OF_ATTORNEY", "value": "POWER_OF_ATTORNEY"}, {"label": "LOGISTICS", "value": "LOGISTICS"}]}, {"prop": "fileUrl", "label": "文件地址", "type": "textarea"}, {"prop": "auditUser", "label": "审核人"}, {"prop": "auditResult", "label": "审核结果", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_AUDIT", "value": "WAIT_AUDIT"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "success"}, {"command": "reject", "label": "驳回", "type": "danger"}]
const defaults = {"status": "WAIT_AUDIT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveCustomsDocument(row.id)
  if (command === 'reject') await rejectCustomsDocument(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
