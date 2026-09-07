<template><DataPage title="异常追溯" description="异常编号、异常对象、异常类型、处置人、发现时间和异常状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExceptionTrace, deleteExceptionTrace, finishExceptionTrace, getExceptionTracePage, processExceptionTrace, updateExceptionTrace } from '../api'
const api = { page: getExceptionTracePage, add: addExceptionTrace, update: updateExceptionTrace, delete: deleteExceptionTrace, process: processExceptionTrace, finish: finishExceptionTrace }
const columns = [{ prop: 'recordNo', label: '异常编号' }, { prop: 'recordName', label: '异常对象' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '处置人' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '异常状态' }, { prop: 'remark', label: '异常说明' }]
const formFields = [{ prop: 'recordNo', label: '异常编号' }, { prop: 'recordName', label: '异常对象' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '处置人' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '异常状态', type: 'select' }, { prop: 'remark', label: '异常说明', type: 'textarea' }]
</script>
