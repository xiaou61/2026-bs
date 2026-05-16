<template><DataPage title="转运联单" description="联单编号、危废批次、转运类型、调度人员、计划时间和联单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT', 'ENTERPRISE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTransferOrder, deleteTransferOrder, finishTransferOrder, getTransferOrderPage, processTransferOrder, updateTransferOrder } from '../api'
const api = { page: getTransferOrderPage, add: addTransferOrder, update: updateTransferOrder, delete: deleteTransferOrder, process: processTransferOrder, finish: finishTransferOrder }
const columns = [{ prop: 'recordNo', label: '联单编号' }, { prop: 'recordName', label: '危废批次' }, { prop: 'category', label: '转运类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '联单状态' }, { prop: 'remark', label: '联单说明' }]
const formFields = [{ prop: 'recordNo', label: '联单编号' }, { prop: 'recordName', label: '危废批次' }, { prop: 'category', label: '转运类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '联单状态', type: 'select' }, { prop: 'remark', label: '联单说明', type: 'textarea' }]
</script>
