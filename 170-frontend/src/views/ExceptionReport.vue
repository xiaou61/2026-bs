<template><DataPage title="异常上报" description="异常编号、异常老人、异常类型、上报人员、上报时间和异常状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExceptionReport, deleteExceptionReport, finishExceptionReport, getExceptionReportPage, processExceptionReport, updateExceptionReport } from '../api'
const api = { page: getExceptionReportPage, add: addExceptionReport, update: updateExceptionReport, delete: deleteExceptionReport, process: processExceptionReport, finish: finishExceptionReport }
const columns = [{ prop: 'recordNo', label: '异常编号' }, { prop: 'recordName', label: '异常老人' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '异常状态' }, { prop: 'remark', label: '异常说明' }]
const formFields = [{ prop: 'recordNo', label: '异常编号' }, { prop: 'recordName', label: '异常老人' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '异常状态', type: 'select' }, { prop: 'remark', label: '异常说明', type: 'textarea' }]
</script>
