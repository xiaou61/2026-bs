<template><DataPage title="缴费记录" description="缴费编号、分摊费用、缴费类型、缴费人员、缴费时间和缴费状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'FINANCE', 'LEADER', 'PARTICIPANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPaymentRecord, deletePaymentRecord, finishPaymentRecord, getPaymentRecordPage, processPaymentRecord, updatePaymentRecord } from '../api'
const api = { page: getPaymentRecordPage, add: addPaymentRecord, update: updatePaymentRecord, delete: deletePaymentRecord, process: processPaymentRecord, finish: finishPaymentRecord }
const columns = [{ prop: 'recordNo', label: '缴费编号' }, { prop: 'recordName', label: '分摊费用' }, { prop: 'category', label: '缴费类型' }, { prop: 'ownerName', label: '缴费人员' }, { prop: 'planTime', label: '缴费时间' }, { prop: 'status', label: '缴费状态' }, { prop: 'remark', label: '缴费说明' }]
const formFields = [{ prop: 'recordNo', label: '缴费编号' }, { prop: 'recordName', label: '分摊费用' }, { prop: 'category', label: '缴费类型' }, { prop: 'ownerName', label: '缴费人员' }, { prop: 'planTime', label: '缴费时间' }, { prop: 'status', label: '缴费状态', type: 'select' }, { prop: 'remark', label: '缴费说明', type: 'textarea' }]
</script>
