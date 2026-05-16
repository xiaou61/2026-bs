<template><DataPage title="访客预约" description="预约编号、来访事由、访问区域、被访员工、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'EMPLOYEE', 'VISITOR', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitAppointment, deleteVisitAppointment, finishVisitAppointment, getVisitAppointmentPage, processVisitAppointment, updateVisitAppointment } from '../api'
const api = { page: getVisitAppointmentPage, add: addVisitAppointment, update: updateVisitAppointment, delete: deleteVisitAppointment, process: processVisitAppointment, finish: finishVisitAppointment }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '来访事由' }, { prop: 'category', label: '访问区域' }, { prop: 'ownerName', label: '被访员工' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '来访事由' }, { prop: 'category', label: '访问区域' }, { prop: 'ownerName', label: '被访员工' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>
