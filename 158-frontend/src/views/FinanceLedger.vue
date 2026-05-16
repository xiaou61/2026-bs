<template><DataPage title="财务流水" description="流水编号、流水事项、收支类型、经办人、发生时间和流水状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'FINANCE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFinanceLedger, deleteFinanceLedger, finishFinanceLedger, getFinanceLedgerPage, processFinanceLedger, updateFinanceLedger } from '../api'
const api = { page: getFinanceLedgerPage, add: addFinanceLedger, update: updateFinanceLedger, delete: deleteFinanceLedger, process: processFinanceLedger, finish: finishFinanceLedger }
const columns = [{ prop: 'recordNo', label: '流水编号' }, { prop: 'recordName', label: '流水事项' }, { prop: 'category', label: '收支类型' }, { prop: 'ownerName', label: '经办人' }, { prop: 'planTime', label: '发生时间' }, { prop: 'status', label: '流水状态' }, { prop: 'remark', label: '流水说明' }]
const formFields = [{ prop: 'recordNo', label: '流水编号' }, { prop: 'recordName', label: '流水事项' }, { prop: 'category', label: '收支类型' }, { prop: 'ownerName', label: '经办人' }, { prop: 'planTime', label: '发生时间' }, { prop: 'status', label: '流水状态', type: 'select' }, { prop: 'remark', label: '流水说明', type: 'textarea' }]
</script>
