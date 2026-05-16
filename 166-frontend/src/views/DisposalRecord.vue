<template><DataPage title="处置记录" description="处置编号、处置对象、处置类型、处置人员、处置时间和处置状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDisposalRecord, deleteDisposalRecord, finishDisposalRecord, getDisposalRecordPage, processDisposalRecord, updateDisposalRecord } from '../api'
const api = { page: getDisposalRecordPage, add: addDisposalRecord, update: updateDisposalRecord, delete: deleteDisposalRecord, process: processDisposalRecord, finish: finishDisposalRecord }
const columns = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '处置对象' }, { prop: 'category', label: '处置类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态' }, { prop: 'remark', label: '处置说明' }]
const formFields = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '处置对象' }, { prop: 'category', label: '处置类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态', type: 'select' }, { prop: 'remark', label: '处置说明', type: 'textarea' }]
</script>
