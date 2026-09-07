<template><DataPage title="事项预约" description="预约编号、预约事项、预约类型、申请人员、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAppointmentBooking, deleteAppointmentBooking, finishAppointmentBooking, getAppointmentBookingPage, processAppointmentBooking, updateAppointmentBooking } from '../api'
const api = { page: getAppointmentBookingPage, add: addAppointmentBooking, update: updateAppointmentBooking, delete: deleteAppointmentBooking, process: processAppointmentBooking, finish: finishAppointmentBooking }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>
