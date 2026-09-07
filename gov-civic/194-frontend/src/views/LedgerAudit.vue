<template><DataPage title="台账审计" description="审计编号、审计对象、审计类型、审计人员、审计时间和审计状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLedgerAudit, deleteLedgerAudit, finishLedgerAudit, getLedgerAuditPage, processLedgerAudit, updateLedgerAudit } from '../api'
const api = { page: getLedgerAuditPage, add: addLedgerAudit, update: updateLedgerAudit, delete: deleteLedgerAudit, process: processLedgerAudit, finish: finishLedgerAudit }
const columns = [{ prop: 'recordNo', label: '审计编号' }, { prop: 'recordName', label: '审计对象' }, { prop: 'category', label: '审计类型' }, { prop: 'ownerName', label: '审计人员' }, { prop: 'planTime', label: '审计时间' }, { prop: 'status', label: '审计状态' }, { prop: 'remark', label: '审计说明' }]
const formFields = [{ prop: 'recordNo', label: '审计编号' }, { prop: 'recordName', label: '审计对象' }, { prop: 'category', label: '审计类型' }, { prop: 'ownerName', label: '审计人员' }, { prop: 'planTime', label: '审计时间' }, { prop: 'status', label: '审计状态', type: 'select' }, { prop: 'remark', label: '审计说明', type: 'textarea' }]
</script>
