<template><DataPage title="异常告警" description="告警编号、告警学生、告警类型、处置人员、告警时间和告警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExceptionAlert, deleteExceptionAlert, finishExceptionAlert, getExceptionAlertPage, processExceptionAlert, updateExceptionAlert } from '../api'
const api = { page: getExceptionAlertPage, add: addExceptionAlert, update: updateExceptionAlert, delete: deleteExceptionAlert, process: processExceptionAlert, finish: finishExceptionAlert }
const columns = [{ prop: 'recordNo', label: '告警编号' }, { prop: 'recordName', label: '告警学生' }, { prop: 'category', label: '告警类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '告警时间' }, { prop: 'status', label: '告警状态' }, { prop: 'remark', label: '告警说明' }]
const formFields = [{ prop: 'recordNo', label: '告警编号' }, { prop: 'recordName', label: '告警学生' }, { prop: 'category', label: '告警类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '告警时间' }, { prop: 'status', label: '告警状态', type: 'select' }, { prop: 'remark', label: '告警说明', type: 'textarea' }]
</script>
