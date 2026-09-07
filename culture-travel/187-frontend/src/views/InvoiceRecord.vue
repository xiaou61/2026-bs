<template><DataPage title="发票记录" description="发票编号、费用项目、发票类型、财务人员、开票时间和发票状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'FINANCE', 'LEADER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInvoiceRecord, deleteInvoiceRecord, finishInvoiceRecord, getInvoiceRecordPage, processInvoiceRecord, updateInvoiceRecord } from '../api'
const api = { page: getInvoiceRecordPage, add: addInvoiceRecord, update: updateInvoiceRecord, delete: deleteInvoiceRecord, process: processInvoiceRecord, finish: finishInvoiceRecord }
const columns = [{ prop: 'recordNo', label: '发票编号' }, { prop: 'recordName', label: '费用项目' }, { prop: 'category', label: '发票类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '开票时间' }, { prop: 'status', label: '发票状态' }, { prop: 'remark', label: '发票说明' }]
const formFields = [{ prop: 'recordNo', label: '发票编号' }, { prop: 'recordName', label: '费用项目' }, { prop: 'category', label: '发票类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '开票时间' }, { prop: 'status', label: '发票状态', type: 'select' }, { prop: 'remark', label: '发票说明', type: 'textarea' }]
</script>
