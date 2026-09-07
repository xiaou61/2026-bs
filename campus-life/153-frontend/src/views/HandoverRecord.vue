<template><DataPage title="交接确认" description="交接编号、关联订单、交接方式、确认人、交接时间和确认状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SELLER', 'BUYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHandoverRecord, deleteHandoverRecord, finishHandoverRecord, getHandoverRecordPage, processHandoverRecord, updateHandoverRecord } from '../api'
const api = { page: getHandoverRecordPage, add: addHandoverRecord, update: updateHandoverRecord, delete: deleteHandoverRecord, process: processHandoverRecord, finish: finishHandoverRecord }
const columns = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '交接方式' }, { prop: 'ownerName', label: '确认人' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '确认状态' }, { prop: 'remark', label: '交接说明' }]
const formFields = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '交接方式' }, { prop: 'ownerName', label: '确认人' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '确认状态', type: 'select' }, { prop: 'remark', label: '交接说明', type: 'textarea' }]
</script>
