<template><DataPage title="违约记录" description="违约编号、关联订单、违约类型、责任人、记录时间和处置状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'ARBITER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBreachRecord, deleteBreachRecord, finishBreachRecord, getBreachRecordPage, processBreachRecord, updateBreachRecord } from '../api'
const api = { page: getBreachRecordPage, add: addBreachRecord, update: updateBreachRecord, delete: deleteBreachRecord, process: processBreachRecord, finish: finishBreachRecord }
const columns = [{ prop: 'recordNo', label: '违约编号' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '违约类型' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '处置状态' }, { prop: 'remark', label: '违约说明' }]
const formFields = [{ prop: 'recordNo', label: '违约编号' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '违约类型' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '处置状态', type: 'select' }, { prop: 'remark', label: '违约说明', type: 'textarea' }]
</script>
