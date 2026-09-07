<template><DataPage title="签收回执" description="回执编号、配送任务、签收类型、签收人员、签收时间和回执状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'COURIER', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSignReceipt, deleteSignReceipt, finishSignReceipt, getSignReceiptPage, processSignReceipt, updateSignReceipt } from '../api'
const api = { page: getSignReceiptPage, add: addSignReceipt, update: updateSignReceipt, delete: deleteSignReceipt, process: processSignReceipt, finish: finishSignReceipt }
const columns = [{ prop: 'recordNo', label: '回执编号' }, { prop: 'recordName', label: '配送任务' }, { prop: 'category', label: '签收类型' }, { prop: 'ownerName', label: '签收人员' }, { prop: 'planTime', label: '签收时间' }, { prop: 'status', label: '回执状态' }, { prop: 'remark', label: '回执说明' }]
const formFields = [{ prop: 'recordNo', label: '回执编号' }, { prop: 'recordName', label: '配送任务' }, { prop: 'category', label: '签收类型' }, { prop: 'ownerName', label: '签收人员' }, { prop: 'planTime', label: '签收时间' }, { prop: 'status', label: '回执状态', type: 'select' }, { prop: 'remark', label: '回执说明', type: 'textarea' }]
</script>
