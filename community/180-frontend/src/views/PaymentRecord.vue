<template><DataPage title="支付记录" description="支付编号、费用登记、支付类型、支付业主、支付时间和支付状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'FINANCE', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPaymentRecord, deletePaymentRecord, finishPaymentRecord, getPaymentRecordPage, processPaymentRecord, updatePaymentRecord } from '../api'
const api = { page: getPaymentRecordPage, add: addPaymentRecord, update: updatePaymentRecord, delete: deletePaymentRecord, process: processPaymentRecord, finish: finishPaymentRecord }
const columns = [{ prop: 'recordNo', label: '支付编号' }, { prop: 'recordName', label: '费用登记' }, { prop: 'category', label: '支付类型' }, { prop: 'ownerName', label: '支付业主' }, { prop: 'planTime', label: '支付时间' }, { prop: 'status', label: '支付状态' }, { prop: 'remark', label: '支付说明' }]
const formFields = [{ prop: 'recordNo', label: '支付编号' }, { prop: 'recordName', label: '费用登记' }, { prop: 'category', label: '支付类型' }, { prop: 'ownerName', label: '支付业主' }, { prop: 'planTime', label: '支付时间' }, { prop: 'status', label: '支付状态', type: 'select' }, { prop: 'remark', label: '支付说明', type: 'textarea' }]
</script>
