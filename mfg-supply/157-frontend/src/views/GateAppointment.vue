<template><DataPage title="入场预约" description="预约编号、预约车辆、预约类型、预约人、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGateAppointment, deleteGateAppointment, finishGateAppointment, getGateAppointmentPage, processGateAppointment, updateGateAppointment } from '../api'
const api = { page: getGateAppointmentPage, add: addGateAppointment, update: updateGateAppointment, delete: deleteGateAppointment, process: processGateAppointment, finish: finishGateAppointment }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约车辆' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约车辆' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>
