<template><DataPage title="预约诊疗" description="预约编号、预约患者、预约类型、预约医生、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'PATIENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAppointmentOrder, deleteAppointmentOrder, finishAppointmentOrder, getAppointmentOrderPage, processAppointmentOrder, updateAppointmentOrder } from '../api'
const api = { page: getAppointmentOrderPage, add: addAppointmentOrder, update: updateAppointmentOrder, delete: deleteAppointmentOrder, process: processAppointmentOrder, finish: finishAppointmentOrder }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约患者' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约医生' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约患者' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约医生' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>
